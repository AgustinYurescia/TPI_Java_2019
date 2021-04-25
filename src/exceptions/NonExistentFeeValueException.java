package exceptions;

public class NonExistentFeeValueException extends AppException{
	
	private static final long serialVersionUID = 1L;

	public  NonExistentFeeValueException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public NonExistentFeeValueException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

	public NonExistentFeeValueException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public NonExistentFeeValueException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public NonExistentFeeValueException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

}
