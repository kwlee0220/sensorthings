package iot.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.log4j.Logger;


/**
 * 
 * @author Kang-Woo Lee (ETRI)
 */
class JDBCStore implements AutoCloseable {
	private static final Logger s_logger = Logger.getLogger("IOT.PLATFORM.JDBC");
	
	private final IoTPlatformImpl m_platform;
	private final String m_dbUrl;
	private final Properties m_connProps;
	private Connection m_conn;

	JDBCSensorNodes m_nodes;
	JDBCSensorNodeLocation m_locations;
	JDBCSensorValueStreams m_streams;
	JDBCMeasurements m_measurements;
	JDBCSensingTargets m_targets;
	JDBCSensors m_sensors;
	JDBCSubscribers m_subscribers;
	JDBCSensorValues m_values;
	
	JDBCStore(IoTPlatformImpl platform, String dbUrl, String user, String password)
		throws SQLException {
		m_platform = platform;
		m_dbUrl = dbUrl;

		if ( s_logger.isInfoEnabled() ) {
			s_logger.info("JDBC: url=" + m_dbUrl);
		}
		
		m_connProps = new Properties();
		m_connProps.setProperty("user", user);
		m_connProps.setProperty("password", password);
		
		m_conn = DriverManager.getConnection(m_dbUrl, m_connProps);
		m_nodes = new JDBCSensorNodes(platform, m_conn);
		m_locations = new JDBCSensorNodeLocation(platform, m_conn);
		m_streams = new JDBCSensorValueStreams(platform, m_conn);
		m_measurements = new JDBCMeasurements(platform, m_conn);
		m_targets = new JDBCSensingTargets(platform, m_conn);
		m_sensors = new JDBCSensors(platform, m_conn);
		m_subscribers = new JDBCSubscribers(platform, m_conn);
		m_values = new JDBCSensorValues(platform, m_conn);
	}
	
	JDBCStore(IoTPlatformImpl platform, String dbUrl, String user) throws SQLException {
		m_platform = platform;
		m_dbUrl = dbUrl;

		if ( s_logger.isInfoEnabled() ) {
			s_logger.info("JDBC: url=" + m_dbUrl);
		}
		
		m_connProps = new Properties();
		m_connProps.setProperty("user", user);
		
		m_conn = DriverManager.getConnection(m_dbUrl, m_connProps);
		m_nodes = new JDBCSensorNodes(platform, m_conn);
		m_locations = new JDBCSensorNodeLocation(platform, m_conn);
		m_streams = new JDBCSensorValueStreams(platform, m_conn);
		m_measurements = new JDBCMeasurements(platform, m_conn);
		m_targets = new JDBCSensingTargets(platform, m_conn);
		m_sensors = new JDBCSensors(platform, m_conn);
		m_subscribers = new JDBCSubscribers(platform, m_conn);
		m_values = new JDBCSensorValues(platform, m_conn);
	}
	
	@Override
	public void close() {
		JdbcUtils.closeQuietly(m_conn);
	}
}
