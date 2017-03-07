package iot.restful;

import java.util.Map;


/**
 * 
 * @author Kang-Woo Lee (ETRI)
 */
public interface JsonSerializable {
	public Map<String, Object> serialize();
}
