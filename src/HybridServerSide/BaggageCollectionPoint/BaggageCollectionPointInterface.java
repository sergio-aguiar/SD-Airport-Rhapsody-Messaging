package HybridServerSide.BaggageCollectionPoint;

import Communication.Message;
import Communication.MessageException;
import HybridServerSide.ArrivalLounge.ArrivalLounge;

public class BaggageCollectionPointInterface {

    private BaggageCollectionPoint baggageCollectionPoint;

    public BaggageCollectionPointInterface(BaggageCollectionPoint baggageCollectionPoint) {
        this.baggageCollectionPoint = baggageCollectionPoint;
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