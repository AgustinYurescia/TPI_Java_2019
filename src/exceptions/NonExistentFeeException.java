package exceptions;

public class NonExistentFeeException extends AppException{
	
	private static final long serialVersionUID = 1L;

	public  NonExistentFeeException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public NonExistentFeeException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

	public NonExistentFeeException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public NonExistentFeeException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public NonExistentFeeException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

}