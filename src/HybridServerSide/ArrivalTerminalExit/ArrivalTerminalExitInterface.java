package HybridServerSide.ArrivalTerminalExit;

import Communication.Message;
import Communication.MessageException;
import HybridServerSide.ArrivalLounge.ArrivalLounge;

public class ArrivalTerminalExitInterface {

    private ArrivalTerminalExit arrivalTerminalExit;

    public ArrivalTerminalExitInterface(ArrivalTerminalExit arrivalTerminalExit) {
        this.arrivalTerminalExit = arrivalTerminalExit;
    }

    public Message processAndReply(Message inMessage) throws MessageException {
        Message outMessage = null;

        switch (inMessage.getMessageType()) {
            case 8:
            case 25:
                break;
            default:
                throw new MessageException("Invalid message type.");
        }

        switch (inMessage.getMessageType()) {
            case 8:
                arrivalTerminalExit.goHome(inMessage.getPassengerID());
                outMessage = new Message(Message.MessageType.PA_ATE_GO_HOME.getMessageCode(), null);
                break;
            case 25:
                arrivalTerminalExit.prepareForNextFlight();
                outMessage = new Message(Message.MessageType.ATE_PREPARE_FOR_NEXT_FLIGHT.getMessageCode(), null);
        }

        return (outMessage);
    }
}
