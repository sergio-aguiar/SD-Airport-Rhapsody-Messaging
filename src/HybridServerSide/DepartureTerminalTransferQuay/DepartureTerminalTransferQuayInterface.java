package HybridServerSide.DepartureTerminalTransferQuay;

import Communication.Message;
import Communication.MessageException;

/**
 * DepartureTerminalTransferQuayInterface: DepartureTerminalTransferQuay message processing and replying.
 * @author sergioaguiar
 * @author marcomacedo
 */
public class DepartureTerminalTransferQuayInterface {
    /**
     * A reference to the server's DepartureTerminalTransferQuay.
     */
    private DepartureTerminalTransferQuay departureTerminalTransferQuay;
    /**
     * Constructor: DepartureTerminalTransferQuay.
     * @param departureTerminalTransferQuay A reference to the server's DepartureTerminalTransferQuay.
     */
    public DepartureTerminalTransferQuayInterface(DepartureTerminalTransferQuay departureTerminalTransferQuay) {
        this.departureTerminalTransferQuay = departureTerminalTransferQuay;
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
            case 4:
                if(inMessage.isThereNoFirstArgument())
                    throw new MessageException("Argument \"passengersThatArrived\" not supplied.", inMessage);
                if(((int) inMessage.getFirstArgument()) < 1)
                    throw new MessageException("Argument \"passengersThatArrived\" was given an incorrect value.",
                            inMessage);
                if(inMessage.isThereNoSecondArgument())
                    throw new MessageException("Argument \"flightNumber\" not supplied.", inMessage);
                if(((int) inMessage.getSecondArgument()) < 0)
                    throw new MessageException("Argument \"flightNumber\" was given an incorrect value.", inMessage);
                break;
            case 5:
            case 29:
            case 61:
                break;
            case 14:
                if(inMessage.isThereNoFirstArgument())
                    throw new MessageException("Argument \"seat\" not supplied.", inMessage);
                if(((int) inMessage.getFirstArgument()) < 0)
                    throw new MessageException("Argument \"seat\" was given an incorrect value.", inMessage);
                break;
            default:
                throw new MessageException("Invalid message type: " + inMessage.getMessageType());
        }

        switch(inMessage.getMessageType()) {
            case 4:
                int result4 = departureTerminalTransferQuay.parkTheBusAndLetPassOff((int) inMessage.getFirstArgument(),
                        (int) inMessage.getSecondArgument());
                outMessage = new Message(Message.MessageType.BD_DTTQ_PARK_THE_BUS_AND_LET_PASS_OFF.getMessageCode(),
                        (Object) result4);
                break;
            case 5:
                departureTerminalTransferQuay.goToArrivalTerminal();
                outMessage = new Message(Message.MessageType.BD_DTTQ_GO_TO_ARRIVAL_TERMINAL.getMessageCode(), null);
                break;
            case 14:
                departureTerminalTransferQuay.leaveTheBus(inMessage.getPassengerID(),
                        (int) inMessage.getFirstArgument());
                outMessage = new Message(Message.MessageType.PA_DTTQ_LEAVE_THE_BUS.getMessageCode(), null);
                break;
            case 29:
                departureTerminalTransferQuay.prepareForNextFlight();
                outMessage = new Message(Message.MessageType.DTTQ_PREPARE_FOR_NEXT_FLIGHT.getMessageCode(), null);
                break;
            case 61:
                DepartureTerminalTransferQuayServer.running = false;
                (((DepartureTerminalTransferQuayProxy) (Thread.currentThread ())).getServerCom()).setTimeout(10);
                outMessage = new Message (Message.MessageType.EVERYTHING_FINISHED.getMessageCode(), null);
        }
        return (outMessage);
    }

}
