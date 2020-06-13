package HybridServerSide.DepartureTerminalEntrance;

import Communication.Message;
import Communication.MessageException;
import genclass.GenericIO;

/**
 * DepartureTerminalEntranceInterface: DepartureTerminalEntrance message processing and replying.
 * @author sergioaguiar
 * @author marcomacedo
 */
public class DepartureTerminalEntranceInterface {
    /**
     * A reference to the server's DepartureTerminalEntrance.
     */
    private DepartureTerminalEntrance departureTerminalEntrance;
    /**
     * Constructor: DepartureTerminalEntranceInterface.
     * @param departureTerminalEntrance A reference to the server's DepartureTerminalEntrance.
     */
    public DepartureTerminalEntranceInterface(DepartureTerminalEntrance departureTerminalEntrance) {
        this.departureTerminalEntrance = departureTerminalEntrance;
    }
    /**
     * Method that processes an inMessage and returns the appropriate response outMessage.
     * @param inMessage The message received.
     * @return The response message based off the message received.
     * @throws MessageException Exception that states why the message object could not be created.
     */
    public Message processAndReply(Message inMessage) throws MessageException {
        GenericIO.writelnString("[In] : " + inMessage.toString());

        Message outMessage = null;

        switch(inMessage.getMessageType()) {
            case 13:
            case 22:
            case 23:
            case 28:
            case 61:
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
                outMessage = new Message(Message.MessageType.DTE_GET_WAITING_PASSENGERS.getMessageCode(),
                        (Object) result22);
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
                break;
            case 61:
                DepartureTerminalEntranceServer.running = false;
                (((DepartureTerminalEntranceProxy) (Thread.currentThread ())).getServerCom()).setTimeout(10);
                outMessage = new Message (Message.MessageType.EVERYTHING_FINISHED.getMessageCode(), null);
        }

        GenericIO.writelnString("[Out]: " + outMessage.toString());
        return (outMessage);
    }

}
