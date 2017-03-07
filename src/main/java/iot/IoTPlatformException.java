package iot;




/**
 * <code>ArduinoException</code>는 CAMUS 관련 최상위 checked 예외 클래스이다.
 * 
 * @author Kang-Woo Lee (ETRI)
 */
public class IoTPlatformException extends RuntimeException {
	private static final long serialVersionUID = -5269470293126226616L;

	public IoTPlatformException(Throwable cause) {
		super(cause);
	}

	public IoTPlatformException(String msg) {
		super(msg);
	}
}