package iot;

import java.nio.charset.Charset;

import jersey.repackaged.com.google.common.base.Preconditions;
import jersey.repackaged.com.google.common.base.Splitter;
import jersey.repackaged.com.google.common.collect.Iterables;

import org.apache.log4j.Logger;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;


/**
 * 
 * @author Kang-Woo Lee (ETRI)
 */
public class InBoundMessageHandler implements MqttCallback {
	private static final Logger s_logger = Logger.getLogger("IOT.MQTT");
	private static final Charset CHARSET = Charset.forName("utf-8");
	private static final Splitter SPLITTER = Splitter.on('\t').trimResults();
	private static final String MQTT_TOPIC_PREFIX = "sensorthings/#";

	// properties (BEGIN)
	private volatile IoTPlatform m_platform;
	private volatile String m_mqttServerUri;
	private volatile String m_mqttClientId;
	// properties (END)

	private MqttClient m_mqttClient;
	private MqttConnectOptions m_options;
	
	public InBoundMessageHandler() {
	}
	
	public final void setIoTPlatform(IoTPlatform platform) {
		m_platform = platform;
	}
	
	public final void setMqttServerUri(String uri) {
		m_mqttServerUri = uri;
	}
	
	public final void setMqttClientId(String clientId) {
		m_mqttClientId = clientId;
	}

	public void initialize() throws Exception {
		Preconditions.checkNotNull(m_mqttServerUri, "Property 'mqttServerUri' has not bee set: class="
									+ getClass().getName());
		Preconditions.checkNotNull(m_mqttClientId, "Property 'mqttClientId' has not bee set: class="
											+ getClass().getName());
		
		if ( s_logger.isInfoEnabled() ) {
			s_logger.info("MQTT Server: url=" + m_mqttServerUri);
			s_logger.info("MQTT Client: id=" + m_mqttClientId);
		}
		
		try {
			m_options = new MqttConnectOptions();
			m_options.setCleanSession(true);
			
			m_mqttClient = new MqttClient(m_mqttServerUri, m_mqttClientId, new MemoryPersistence());
			m_mqttClient.connect(m_options);
			m_mqttClient.setCallback(this);
			
			m_mqttClient.subscribe(MQTT_TOPIC_PREFIX);
			
			if ( s_logger.isDebugEnabled() ) {
				s_logger.debug("subscribed to MQTT Server: topic=" + MQTT_TOPIC_PREFIX);
			}
		}
		catch ( Exception e ) {
			s_logger.warn("fails to subscribe to MQTT server: cause=" + e);
			
			throw e;
		}
	}

	public void destroy() throws Exception {
		m_mqttClient.disconnect();
	}

	@Override
	public void messageArrived(String topic, MqttMessage msg) throws Exception {
		String msgStr = new String(msg.getPayload(), CHARSET);
		
		String[] fields = Iterables.toArray(SPLITTER.split(msgStr), String.class);
		
		String channel = fields[0];
		if ( s_logger.isDebugEnabled() ) {
			s_logger.debug("recieved MQTT message: channel=" + channel + " value=" + fields[1]);
		}

		try {
			int value = Integer.parseInt(fields[1]);
			
			SensorValueSubscriber sub = m_platform.getSensorValueSubscriberByChannel(channel);
			if ( sub == null ) {
				if ( s_logger.isDebugEnabled() ) {
					s_logger.debug("unknown sensor: channel=" + channel);
				}
				return;
			}
			
			long logId = sub.update(value);
			if ( s_logger.isDebugEnabled() ) {
				s_logger.debug(String.format("updated: %d(%s) %d -> id=%d",
												sub.getId(), sub.getChannel(), value, logId));
			}
		}
		catch ( Exception e ) {
			s_logger.error(String.format(
					"fails to update sensor: channel=%s value=%d, cause=%s",
					channel, fields[1], ""+e));
		}
	}

	@Override public void connectionLost(Throwable cause) { }
	@Override public void deliveryComplete(IMqttDeliveryToken token) { }
}
