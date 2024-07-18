package kanban.exceptions;

public class NotFoundTaskException extends RuntimeException {
    public NotFoundTaskException(String message) {
        super(message);
    }

    public String getMessage() {
        return super.getMessage();
    }
}
