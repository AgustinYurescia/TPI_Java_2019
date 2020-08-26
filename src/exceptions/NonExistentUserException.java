package exceptions;

public class NonExistentUserException extends Exception {
	
	private static final long serialVersionUID = 1L;

	public NonExistentUserException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public NonExistentUserException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

	public NonExistentUserException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public NonExistentUserException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public NonExistentUserException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

}
