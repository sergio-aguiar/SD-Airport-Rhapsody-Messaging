package HybridServerSide.ArrivalTerminalExit;

import Communication.Message;
import Communication.MessageException;
import HybridServerSide.ArrivalLounge.ArrivalLounge;

public class ArrivalTerminalExitInterface {

    private ArrivalTerminalExit arrivalTerminalExit;

    public ArrivalTerminalExitInterface(ArrivalTerminalExit arrivalTerminalExit) {
        this.arrivalTerminalExit = arrivalTerminalExit;
    }

    public Message processAndReply(Message inMessage) throws MessageException
    {
        Message outMessage = null;

        if (inMessage.getMessageType() != 8)
            throw new MessageException("Invalid message type.");

        if (inMessage.getMessageType() == 8) {
            arrivalTerminalExit.goHome(inMessage.getPassengerID());
            outMessage = new Message(Message.MessageType.PA_ATE_GO_HOME.getMessageCode(), null);
        }

        return (outMessage);
    }

}
