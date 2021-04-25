package exceptions;

public class NonExistentCategoryException extends AppException{
	
	private static final long serialVersionUID = 1L;

	public NonExistentCategoryException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public NonExistentCategoryException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

	public NonExistentCategoryException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public NonExistentCategoryException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public NonExistentCategoryException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

}
