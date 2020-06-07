package HybridServerSide.ArrivalLounge;

import Communication.Message;
import Communication.MessageException;

public class ArrivalLoungeInterface {

    private ArrivalLounge arrivalLounge;

    public ArrivalLoungeInterface(ArrivalLounge arrivalLounge) {
        this.arrivalLounge = arrivalLounge;
    }

    public Message processAndReply(Message inMessage) throws MessageException
    {
        Message outMessage = null;

        switch(inMessage.getMessageType()) {
            case 6:
                if(inMessage.isThereNoFirstArgument())
                    throw new MessageException("Argument \"situation\" not supplied.", inMessage);
                if(!inMessage.getFirstArgument().equals("TRT") && !inMessage.getFirstArgument().equals("FDT"))
                    throw new MessageException("Argument \"situation\" was given an incorrect value.", inMessage);
                break;
            case 7:
            case 15:
            case 16:
            case 24:
                break;
            default:
                throw new MessageException("Invalid message type.");
        }

        switch(inMessage.getMessageType()) {
            case 6:
                arrivalLounge.whatShouldIDo(inMessage.getPassengerID(), (String) inMessage.getFirstArgument());
                outMessage = new Message(Message.MessageType.PA_AL_WHAT_SHOULD_I_DO.getMessageCode(), null);
                break;
            case 7:
                arrivalLounge.goCollectABag(inMessage.getPassengerID());
                outMessage = new Message(Message.MessageType.PA_AL_GO_COLLECT_A_BAG.getMessageCode(), null);
                break;
            case 15:
                boolean result15 = arrivalLounge.takeARest();
                outMessage = new Message(Message.MessageType.PO_AL_TAKE_A_REST.getMessageCode(), result15);
                break;
            case 16:
                String result16 = arrivalLounge.tryToCollectABag();
                outMessage = new Message(Message.MessageType.PO_AL_TRY_TO_COLLECT_A_BAG.getMessageCode(), result16);
                break;
            case 24:
                arrivalLounge.prepareForNextFlight();
                outMessage = new Message(Message.MessageType.AL_PREPARE_FOR_NEXT_FLIGHT.getMessageCode(), null);
        }

        return (outMessage);
    }

}
