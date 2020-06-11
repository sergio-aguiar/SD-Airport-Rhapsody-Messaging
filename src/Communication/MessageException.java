package Communication;

public class MessageException extends Exception {

    private Message errorMessage;

    public MessageException(String errorMessage) {
        super(errorMessage);
    }

    public MessageException(String errorMessage, Message message) {
        super(errorMessage);
        this.errorMessage = message;
    }

    public Message getErrorMessage() {
        return this.errorMessage;
    }
}
