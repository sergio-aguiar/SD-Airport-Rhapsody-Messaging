package HybridServerSide.ArrivalTerminalTransferQuay;

import Communication.Message;
import Communication.MessageException;

/**
 * ArrivalTerminalTransferQuayInterface: ArrivalTerminalTransferQuay message processing and replying.
 * @author sergioaguiar
 * @author marcomacedo
 */
public class ArrivalTerminalTransferQuayInterface {
    /**
     * A reference to the server's ArrivalTerminalTransferQuay.
     */
    private ArrivalTerminalTransferQuay arrivalTerminalTransferQuay;
    /**
     * Constructor: ArrivalTerminalTransferQuay.
     * @param arrivalTerminalTransferQuay A reference to the server's ArrivalTerminalTransferQuay.
     */
    public ArrivalTerminalTransferQuayInterface(ArrivalTerminalTransferQuay arrivalTerminalTransferQuay) {
        this.arrivalTerminalTransferQuay = arrivalTerminalTransferQuay;
    }
    /**
     * Method that processes an inMessage and returns the appropriate response outMessage.
     * @param inMessage The message received.
     * @return The response message based off the message received.
     * @throws MessageException Exception that states why the message object could not be created.
     */
    public Message processAndReply(Message inMessage) throws MessageException {
        Message outMessage = null;

        switch(inMessage.getMessageType()) {
            case 0:
            case 1:
            case 2:
            case 3:
            case 9:
            case 10:
            case 26:
            case 61:
                break;
            case 57:
                if(inMessage.isThereNoFirstArgument())
                    throw new MessageException("Argument \"totalPassengers\" not supplied.", inMessage);
                if((int) inMessage.getFirstArgument() < 1)
                    throw new MessageException("Argument \"totalPassengers\" was given an incorrect value.", inMessage);
                if(inMessage.isThereNoSecondArgument())
                    throw new MessageException("Argument \"busSeats\" not supplied.", inMessage);
                if((int) inMessage.getSecondArgument() < 1)
                    throw new MessageException("Argument \"busSeats\" was given an incorrect value.", inMessage);
                break;
            default:
                throw new MessageException("Invalid message type: " + inMessage.getMessageType());
        }

        switch(inMessage.getMessageType()) {
            case 0:
                boolean result0 = arrivalTerminalTransferQuay.hasDaysWorkEnded();
                outMessage = new Message(Message.MessageType.BD_ATTQ_HAS_DAYS_WORK_ENDED.getMessageCode(), (Object) result0);
                break;
            case 1:
                boolean result1 = arrivalTerminalTransferQuay.announcingBusBoarding();
                outMessage = new Message(Message.MessageType.BD_ATTQ_ANNOUNCING_BUS_BOARDING.getMessageCode(), (Object) result1);
                break;
            case 2:
                int result2 = arrivalTerminalTransferQuay.goToDepartureTerminal();
                outMessage = new Message(Message.MessageType.BD_ATTQ_GO_TO_DEPARTURE_TERMINAL.getMessageCode(), (Object) result2);
                break;
            case 3:
                arrivalTerminalTransferQuay.parkTheBus();
                outMessage = new Message(Message.MessageType.BD_ATTQ_PARK_THE_BUS.getMessageCode(), null);
                break;
            case 9:
                arrivalTerminalTransferQuay.takeABus(inMessage.getPassengerID());
                outMessage = new Message(Message.MessageType.PA_ATTQ_TAKE_A_BUS.getMessageCode(), null);
                break;
            case 10:
                int result10 = arrivalTerminalTransferQuay.enterTheBus(inMessage.getPassengerID());
                outMessage = new Message(Message.MessageType.PA_ATTQ_ENTER_THE_BUS.getMessageCode(), (Object) result10);
                break;
            case 26:
                arrivalTerminalTransferQuay.prepareForNextFlight();
                outMessage = new Message(Message.MessageType.ATTQ_PREPARE_FOR_NEXT_FLIGHT.getMessageCode(), null);
                break;
            case 57:
                arrivalTerminalTransferQuay.setInitialState((int) inMessage.getFirstArgument(), (int) inMessage.getSecondArgument());
                outMessage = new Message(Message.MessageType.ATTQ_SET_INITIAL_STATE.getMessageCode(), null);
                break;
            case 61:
                ArrivalTerminalTransferQuayServer.running = false;
                (((ArrivalTerminalTransferQuayProxy) (Thread.currentThread ())).getServerCom()).setTimeout(10);
                outMessage = new Message (Message.MessageType.EVERYTHING_FINISHED.getMessageCode(), null);
        }
        return (outMessage);
    }

}
