package HybridServerSide.Repository;

import Communication.Message;
import Communication.MessageException;
import HybridServerSide.ArrivalLounge.ArrivalLounge;

public class RepositoryInterface {

    private Repository repository;

    public RepositoryInterface(Repository repository) {
        this.repository = repository;
    }

    public Message processAndReply(Message inMessage) throws MessageException
    {
        Message outMessage = null;

        switch(inMessage.getMessageType()) {
            case 0:
                break;
            case 1:
                break;
            default:
                throw new MessageException("Invalid message type.");
        }

        switch(inMessage.getMessageType()) {
            case 0:
                break;
            case 1:
                break;
        }

        return (outMessage);
    }

}
