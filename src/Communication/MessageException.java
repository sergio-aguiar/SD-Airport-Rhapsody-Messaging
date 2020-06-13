package Communication;

/**
 * MessageException: An exception class used to alert that a message related error has occurred.
 * @author sergioaguiar
 * @author marcomacedo
 */
public class MessageException extends Exception {
    /**
     * The message that generated this exception.
     */
    private Message errorMessage;
    /**
     * Constructor: MessageException.
     * @param errorMessage The error message regarding the exception in question.
     */
    public MessageException(String errorMessage) {
        super(errorMessage);
    }
    /**
     * Constructor: MessageException.
     * @param errorMessage The error message regarding the exception in question.
     * @param message The message that generated this exception.
     */
    public MessageException(String errorMessage, Message message) {
        super(errorMessage);
        this.errorMessage = message;
    }
    /**
     * Getter method for errorMessage.
     * @return The message that generated this exception.
     */
    public Message getErrorMessage() {
        return this.errorMessage;
    }
}
