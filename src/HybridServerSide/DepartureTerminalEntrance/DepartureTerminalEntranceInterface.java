package HybridServerSide.DepartureTerminalEntrance;

import Communication.Message;
import Communication.MessageException;
import HybridServerSide.ArrivalLounge.ArrivalLounge;

public class DepartureTerminalEntranceInterface {

    private DepartureTerminalEntrance departureTerminalEntrance;

    public DepartureTerminalEntranceInterface(DepartureTerminalEntrance departureTerminalEntrance) {
        this.departureTerminalEntrance = departureTerminalEntrance;
    }

    public Message processAndReply(Message inMessage) throws MessageException
    {
        Message outMessage = null;

        if(inMessage.getMessageType() != 13) {
            throw new MessageException("Invalid message type.");
        }

        if(inMessage.getMessageType() == 13) {
            departureTerminalEntrance.prepareNextLeg(inMessage.getPassengerID());
            outMessage = new Message(Message.MessageType.PA_DTE_PREPARE_NEXT_LEG.getMessageCode(), null);
        }

        return (outMessage);
    }

}
