package ar.edu.iua.sat.business;

public class ExpiredTokenException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ExpiredTokenException() {
	}

	public ExpiredTokenException(String message) {
		super(message);
	}

	public ExpiredTokenException(Throwable cause) {
		super(cause);
	}

	public ExpiredTokenException(String message, Throwable cause) {
		super(message, cause);
	}
}
