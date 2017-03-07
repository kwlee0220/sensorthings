package iot.restful;

import iot.SensingTarget;

import java.util.stream.Stream;

import javax.ws.rs.core.UriInfo;


/**
 * 
 * @author Kang-Woo Lee (ETRI)
 */
public class FeatureOfInterest extends STNode {
	private final SensingTarget m_foi;
	
	public FeatureOfInterest(SensingTarget foi, UriInfo uriInfo) {
		super(foi.getId(), "FeatureOfInterest", uriInfo);
		
		m_foi = foi;
		
		setAttribute("Description", "feature of interest");
		setAttribute("Geometry", m_foi.getGeometry());
		addLink("Observations");
	}

	@Override
	public Stream<STTuple> navigate(String link) {
		if ( link.equalsIgnoreCase("Observations") ) {
			return m_foi.streamOfSensorValueLog()
						.map(log -> new Observation(log, m_uriInfo));
		}

		return super.navigate(link);
	}
}
