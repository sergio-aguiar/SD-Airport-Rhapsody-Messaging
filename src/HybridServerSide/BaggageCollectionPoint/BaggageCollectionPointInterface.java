package HybridServerSide.BaggageCollectionPoint;

import Communication.Message;
import Communication.MessageException;
import HybridServerSide.ArrivalLounge.ArrivalLoungeProxy;
import HybridServerSide.ArrivalLounge.ArrivalLoungeServer;

public class BaggageCollectionPointInterface {

    private BaggageCollectionPoint baggageCollectionPoint;

    public BaggageCollectionPointInterface(BaggageCollectionPoint baggageCollectionPoint) {
        this.baggageCollectionPoint = baggageCollectionPoint;
    }

    public Message processAndReply(Message inMessage) throws MessageException
    {
        System.out.println("In Message: " + inMessage.toString());

        Message outMessage = null;

        switch(inMessage.getMessageType()) {
            case 11:
            case 27:
            case 61:
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
            case 58:
                if(inMessage.isThereNoFirstArgument())
                    throw new MessageException("Argument \"totalPassengers\" not supplied.", inMessage);
                if(((int) inMessage.getFirstArgument()) < 0)
                    throw new MessageException("Argument \"totalPassengers\" was given an incorrect value.", inMessage);
                break;
            default:
                throw new MessageException("Invalid message type: " + inMessage.getMessageType());
        }

        switch(inMessage.getMessageType()) {
            case 11:
                boolean result11 = baggageCollectionPoint.goCollectABag(inMessage.getPassengerID());
                outMessage = new Message(Message.MessageType.PA_BCP_GO_COLLECT_A_BAG.getMessageCode(), (Object) result11);
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
                break;
            case 58:
                baggageCollectionPoint.setInitialState((int) inMessage.getFirstArgument());
                outMessage = new Message(Message.MessageType.BCP_SET_INITIAL_STATE.getMessageCode(), null);
                break;
            case 61:
                BaggageCollectionPointServer.running = false;
                (((BaggageCollectionPointProxy) (Thread.currentThread ())).getServerCom()).setTimeout(10);
                outMessage = new Message (Message.MessageType.EVERYTHING_FINISHED.getMessageCode(), null);
        }

        System.out.println("Out Message: " + outMessage.toString());
        return (outMessage);
    }

}
