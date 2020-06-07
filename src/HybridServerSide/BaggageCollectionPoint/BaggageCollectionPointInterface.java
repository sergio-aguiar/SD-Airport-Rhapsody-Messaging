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
            case 11:
            case 27:
                break;
            case 17:
                if(inMessage.isThereNoFirstArgument())
                    throw new MessageException("Argument \"bagID\" not supplied.", inMessage);
                if(((int) inMessage.getFirstArgument()) < 0)
                    throw new MessageException("Argument \"bagID\" was given an incorrect value.", inMessage);
                break;
            case 18:
                if(inMessage.isThereNoFirstArgument())
                    throw new MessageException("Argument \"pid\" not supplied.", inMessage);
                if(((int) inMessage.getFirstArgument()) < 0)
                    throw new MessageException("Argument \"pid\" was given an incorrect value.", inMessage);
                break;
            default:
                throw new MessageException("Invalid message type.");
        }

        switch(inMessage.getMessageType()) {
            case 11:
                boolean result11 = baggageCollectionPoint.goCollectABag(inMessage.getPassengerID());
                outMessage = new Message(Message.MessageType.PA_BCP_GO_COLLECT_A_BAG.getMessageCode(), result11);
                break;
            case 17:
                baggageCollectionPoint.carryItToAppropriateStore((int) inMessage.getFirstArgument());
                outMessage = new Message(Message.MessageType.PO_BCP_CARRY_IT_TO_APPROPRIATE_STORE.getMessageCode(), null);
                break;
            case 18:
                baggageCollectionPoint.noMoreBagsToCollect((int) inMessage.getFirstArgument());
                outMessage = new Message(Message.MessageType.PO_BCP_NO_MORE_BAGS_TO_COLLECT.getMessageCode(), null);
                break;
            case 27:
                baggageCollectionPoint.prepareForNextFlight();
                outMessage = new Message(Message.MessageType.BCP_PREPARE_FOR_NEXT_FLIGHT.getMessageCode(), null);
        }

        return (outMessage);
    }

}
