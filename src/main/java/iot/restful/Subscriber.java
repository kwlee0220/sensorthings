package iot.restful;

import iot.SensorValueSubscriber;

import java.util.stream.Stream;

import javax.ws.rs.core.UriInfo;


/**
 * 
 * @author Kang-Woo Lee (ETRI)
 */
public class Subscriber extends STNode {
	private final SensorValueSubscriber m_subscriber;
	
	public Subscriber(SensorValueSubscriber subscriber, UriInfo uriInfo) {
		super(subscriber.getId(), "Subscribers", uriInfo);
		
		m_subscriber = subscriber;
		setAttribute("Channel", m_subscriber.getChannel());
		addLink("Datastream");
		addLink("Sensor");
		addLink("FeatureOfInterest");
	}

	@Override
	public Stream<STTuple> navigate(String link) {
		if ( link.equalsIgnoreCase("Datastream") ) {
			return Stream.of(new Datastream(m_subscriber.getSensorValueStream(), m_uriInfo));
		}
		if ( link.equalsIgnoreCase("Sensor") ) {
			return Stream.of(new Sensor(m_subscriber.getSensor(), m_uriInfo));
		}
		if ( link.equalsIgnoreCase("FeatureOfInterest") ) {
			return Stream.of(new FeatureOfInterest(m_subscriber.getSensingTarget(), m_uriInfo));
		}
		
		return super.navigate(link);
	}
}
