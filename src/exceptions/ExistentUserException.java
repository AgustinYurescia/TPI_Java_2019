package exceptions;

public class ExistentUserException extends AppException {

	private static final long serialVersionUID = 1L;

	public ExistentUserException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ExistentUserException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

	public ExistentUserException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public ExistentUserException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public ExistentUserException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

}
