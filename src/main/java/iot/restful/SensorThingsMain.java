package iot.restful;

import iot.InBoundMessageHandler;
import iot.jdbc.IoTPlatformImpl;

import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.Options;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.servlet.ServletContainer;



/**
 * 
 * @author Kang-Woo Lee (ETRI)
 */
public class SensorThingsMain {
	private static final Logger s_logger = Logger.getLogger("IOT.MQTT");
	
	private static final String DEF_MQTT_URL = "tcp://localhost:1883";
	private static final String DEF_JDBC_URL = "jdbc:h2:file:./h2db";
	private static final String DEF_PORT = "8080";
	private static final Options s_options = new Options();
	
	public static final void main(String[] args) throws Exception {
		PropertyConfigurator.configure("./log4j.properties");
    	
    	addArgOption("mqtt_url", "URL", "MQTT server URL", false);
    	addArgOption("jdbc_url", "URL", "JDBC DB URL", false);
    	addArgOption("port", "tcp port", "IoT Server port (default: 8080)", false);
    	
	    CommandLineParser parser = new BasicParser();
	    CommandLine cl = parser.parse(s_options, args);
	    
	    String mqttUrl = cl.getOptionValue("mqtt_url", DEF_MQTT_URL);
	    String jdbcUrl = cl.getOptionValue("jdbc_url", DEF_JDBC_URL);
	    int port = Integer.parseInt(cl.getOptionValue("port", DEF_PORT));
    	
		IoTPlatformImpl platform = new IoTPlatformImpl();
		platform.setJdbcUrl(jdbcUrl);
		platform.initialize();
		SensorThingsContext.setContext(new SensorThingsContext(platform));
		
		try {
			InBoundMessageHandler handler = new InBoundMessageHandler();
			handler.setIoTPlatform(platform);
			handler.setMqttServerUri(mqttUrl);
			handler.setMqttClientId("arduino_server");
			handler.initialize();
		}
		catch ( Exception e ) {
			s_logger.warn("fails to connect to MQTT server: cause=" + e);
		}

		Server jetty = new Server(port);
		
		ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
		context.setContextPath("/");
        jetty.setHandler(context);
        
        ServletHolder stServlet = context.addServlet(ServletContainer.class,
        												"/SensorThings_V1.0/*");
        stServlet.setInitOrder(0);
//		stServlet.setInitParameter("jersey.config.server.provider.classnames",
//										SensorThingsResource.class.getCanonicalName());
        stServlet.setInitParameter("jersey.config.server.provider.packages",
										"iot.restful");
		stServlet.setInitParameter("com.sun.jersey.api.json.POJOMappingFeature",
										"true");
		
		try {
			jetty.start();
			jetty.join();
		}
		finally {
			jetty.destroy();
		}
	}

    private static void addArgOption(String id, String name, String desc, boolean required) {
        OptionBuilder.withArgName(name);
        OptionBuilder.hasArg();
        OptionBuilder.isRequired(required);
        OptionBuilder.withDescription(desc);
        s_options.addOption(OptionBuilder.create(id));
    }
}
