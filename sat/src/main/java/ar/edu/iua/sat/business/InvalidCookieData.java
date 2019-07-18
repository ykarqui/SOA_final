package ar.edu.iua.sat.business;

public class InvalidCookieData extends Exception {

	private static final long serialVersionUID = 8986629478436749148L;

	public InvalidCookieData() {
	}

	public InvalidCookieData(String message) {
		super(message);
	}

	public InvalidCookieData(Throwable cause) {
		super(cause);
	}

	public InvalidCookieData(String message, Throwable cause) {
		super(message, cause);
	}
}
