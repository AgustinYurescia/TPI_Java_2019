package exceptions;

public class NonExistentOrderException extends AppException{
	
	private static final long serialVersionUID = 1L;

	public NonExistentOrderException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public NonExistentOrderException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

	public NonExistentOrderException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public NonExistentOrderException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public NonExistentOrderException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

}
