package kanban.exceptions;

public class TaskIntersectionTimeException extends RuntimeException {
    public TaskIntersectionTimeException(String message) {
        super(message);
    }

    public String getMessage() {
        return super.getMessage();
    }
}
