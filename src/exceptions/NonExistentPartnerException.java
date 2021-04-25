package exceptions;

public class NonExistentPartnerException extends AppException{
	
	private static final long serialVersionUID = 1L;

	public NonExistentPartnerException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public NonExistentPartnerException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

	public NonExistentPartnerException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public NonExistentPartnerException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public NonExistentPartnerException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

}
