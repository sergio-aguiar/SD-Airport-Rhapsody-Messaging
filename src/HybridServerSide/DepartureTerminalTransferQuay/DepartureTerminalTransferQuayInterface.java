package HybridServerSide.DepartureTerminalTransferQuay;

import Communication.Message;
import Communication.MessageException;
import HybridServerSide.ArrivalLounge.ArrivalLounge;

public class DepartureTerminalTransferQuayInterface {

    private DepartureTerminalTransferQuay departureTerminalTransferQuay;

    public DepartureTerminalTransferQuayInterface(DepartureTerminalTransferQuay departureTerminalTransferQuay) {
        this.departureTerminalTransferQuay = departureTerminalTransferQuay;
    }

    public Message processAndReply(Message inMessage) throws MessageException
    {
        Message outMessage = null;

        switch(inMessage.getMessageType()) {
            case 4:
                if(inMessage.isThereNoFirstArgument())
                    throw new MessageException("Argument \"passengersThatArrived\" not supplied.", inMessage);
                if(((int) inMessage.getFirstArgument()) < 1)
                    throw new MessageException("Argument \"passengersThatArrived\" was given an incorrect value.", inMessage);
                if(inMessage.isThereNoSecondArgument())
                    throw new MessageException("Argument \"flightNumber\" not supplied.", inMessage);
                if(((int) inMessage.getSecondArgument()) < 0)
                    throw new MessageException("Argument \"flightNumber\" was given an incorrect value.", inMessage);
                break;
            case 5:
            case 29:
                break;
            case 14:
                if(inMessage.isThereNoFirstArgument())
                    throw new MessageException("Argument \"seat\" not supplied.", inMessage);
                if(((int) inMessage.getFirstArgument()) < 0)
                    throw new MessageException("Argument \"seat\" was given an incorrect value.", inMessage);
                break;
            default:
                throw new MessageException("Invalid message type.");
        }

        switch(inMessage.getMessageType()) {
            case 4:
                int result4 = departureTerminalTransferQuay.parkTheBusAndLetPassOff((int) inMessage.getFirstArgument(), (int) inMessage.getSecondArgument());
                outMessage = new Message(Message.MessageType.BD_DTTQ_PARK_THE_BUS_AND_LET_PASS_OFF.getMessageCode(), result4);
                break;
            case 5:
                departureTerminalTransferQuay.goToArrivalTerminal();
                outMessage = new Message(Message.MessageType.BD_DTTQ_GO_TO_ARRIVAL_TERMINAL.getMessageCode(), null);
                break;
            case 14:
                departureTerminalTransferQuay.leaveTheBus(inMessage.getPassengerID(), (int) inMessage.getFirstArgument());
                outMessage = new Message(Message.MessageType.PA_DTTQ_LEAVE_THE_BUS.getMessageCode(), null);
                break;
            case 29:
                departureTerminalTransferQuay.prepareForNextFlight();
                outMessage = new Message(Message.MessageType.DTTQ_PREPARE_FOR_NEXT_FLIGHT.getMessageCode(), null);
        }

        return (outMessage);
    }

}
