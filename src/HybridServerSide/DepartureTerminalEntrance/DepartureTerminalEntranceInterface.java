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

        switch(inMessage.getMessageType()) {
            case 13:
            case 28:
                break;
            default:
                throw new MessageException("Invalid message type.");
        }

        switch(inMessage.getMessageType()) {
            case 13:
                departureTerminalEntrance.prepareNextLeg(inMessage.getPassengerID());
                outMessage = new Message(Message.MessageType.PA_DTE_PREPARE_NEXT_LEG.getMessageCode(), null);
                break;
            case 28:
                departureTerminalEntrance.prepareForNextFlight();
                outMessage = new Message(Message.MessageType.DTE_PREPARE_FOR_NEXT_FLIGHT.getMessageCode(), null);

        }

        return (outMessage);
    }

}
