package HybridServerSide.ArrivalTerminalExit;

import Communication.Message;
import Communication.MessageException;
import genclass.GenericIO;

/**
 * ArrivalTerminalExitInterface: ArrivalTerminalExit message processing and replying.
 * @author sergioaguiar
 * @author marcomacedo
 */
public class ArrivalTerminalExitInterface {
    /**
     * A reference to the server's ArrivalTerminalExit.
     */
    private ArrivalTerminalExit arrivalTerminalExit;

    /**
     * Constructor: ArrivalTerminalExit.
     * @param arrivalTerminalExit A reference to the server's ArrivalTerminalExit.
     */
    public ArrivalTerminalExitInterface(ArrivalTerminalExit arrivalTerminalExit) {
        this.arrivalTerminalExit = arrivalTerminalExit;
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

        switch (inMessage.getMessageType()) {
            case 8:
            case 20:
            case 21:
            case 25:
            case 61:
                break;
            case 56:
                if(inMessage.isThereNoFirstArgument())
                    throw new MessageException("Argument \"totalPassengers\" not supplied.", inMessage);
                if((int) inMessage.getFirstArgument() < 1)
                    throw new MessageException("Argument \"totalPassengers\" was given an incorrect value.", inMessage);
                break;
            default:
                throw new MessageException("Invalid message type: " + inMessage.getMessageType());
        }

        switch (inMessage.getMessageType()) {
            case 8:
                arrivalTerminalExit.goHome(inMessage.getPassengerID());
                outMessage = new Message(Message.MessageType.PA_ATE_GO_HOME.getMessageCode(), null);
                break;
            case 20:
                int result20 = arrivalTerminalExit.getWaitingPassengers();
                outMessage = new Message(Message.MessageType.ATE_GET_WAITING_PASSENGERS.getMessageCode(),
                        (Object) result20);
                break;
            case 21:
                arrivalTerminalExit.signalWaitingPassengers();
                outMessage = new Message(Message.MessageType.ATE_SIGNAL_WAITING_PASSENGERS.getMessageCode(), null);
                break;
            case 25:
                arrivalTerminalExit.prepareForNextFlight();
                outMessage = new Message(Message.MessageType.ATE_PREPARE_FOR_NEXT_FLIGHT.getMessageCode(), null);
                break;
            case 56:
                arrivalTerminalExit.setInitialState((int) inMessage.getFirstArgument());
                outMessage = new Message(Message.MessageType.ATE_SET_INITIAL_STATE.getMessageCode(), null);
                break;
            case 61:
                ArrivalTerminalExitServer.running = false;
                GenericIO.writelnString(Thread.currentThread().getName());
                (((ArrivalTerminalExitProxy) (Thread.currentThread ())).getServerCom()).setTimeout(10);
                outMessage = new Message (Message.MessageType.EVERYTHING_FINISHED.getMessageCode(), null);
        }

        GenericIO.writelnString("[Out]: " + outMessage.toString());
        return (outMessage);
    }
}
