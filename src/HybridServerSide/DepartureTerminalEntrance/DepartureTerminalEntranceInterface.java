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
            case 22:
            case 23:
            case 28:
                break;
            case 59:
                if(inMessage.isThereNoFirstArgument())
                    throw new MessageException("Argument \"totalPassengers\" not supplied.", inMessage);
                if((int) inMessage.getFirstArgument() < 1)
                    throw new MessageException("Argument \"totalPassengers\" was given an incorrect value.", inMessage);
                break;
            default:
                throw new MessageException("Invalid message type: " + inMessage.getMessageType());
        }

        switch(inMessage.getMessageType()) {
            case 13:
                departureTerminalEntrance.prepareNextLeg(inMessage.getPassengerID());
                outMessage = new Message(Message.MessageType.PA_DTE_PREPARE_NEXT_LEG.getMessageCode(), null);
                break;
            case 22:
                int result22 = departureTerminalEntrance.getWaitingPassengers();
                outMessage = new Message(Message.MessageType.DTE_GET_WAITING_PASSENGERS.getMessageCode(), (Object) result22);
                break;
            case 23:
                departureTerminalEntrance.signalWaitingPassengers();
                outMessage = new Message(Message.MessageType.DTE_SIGNAL_WAITING_PASSENGERS.getMessageCode(), null);
                break;
            case 28:
                departureTerminalEntrance.prepareForNextFlight();
                outMessage = new Message(Message.MessageType.DTE_PREPARE_FOR_NEXT_FLIGHT.getMessageCode(), null);
                break;
            case 59:
                departureTerminalEntrance.setInitialState((int) inMessage.getFirstArgument());
                outMessage = new Message(Message.MessageType.DTE_SET_INITIAL_STATE.getMessageCode(), null);
        }

        return (outMessage);
    }

}
