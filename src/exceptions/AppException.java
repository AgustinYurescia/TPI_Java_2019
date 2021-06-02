package exceptions;

public class AppException extends Exception{

	private static final long serialVersionUID = 1L;

	public AppException() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public AppException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

	public AppException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public AppException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public AppException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

}

