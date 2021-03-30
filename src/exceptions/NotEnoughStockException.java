package exceptions;

public class NotEnoughStockException extends Exception {
	private static final long serialVersionUID = 1L;

	public NotEnoughStockException() {
		super();
	}

	public NotEnoughStockException(String message) {
		super(message);
	}

}
