package HybridServerSide.ArrivalTerminalTransferQuay;

import Communication.Message;
import Communication.MessageException;
import HybridServerSide.ArrivalLounge.ArrivalLounge;

public class ArrivalTerminalTransferQuayInterface {

    private ArrivalTerminalTransferQuay arrivalTerminalTransferQuay;

    public ArrivalTerminalTransferQuayInterface(ArrivalTerminalTransferQuay arrivalTerminalTransferQuay) {
        this.arrivalTerminalTransferQuay = arrivalTerminalTransferQuay;
    }

    public Message processAndReply(Message inMessage) throws MessageException
    {
        Message outMessage = null;

        switch(inMessage.getMessageType()) {
            case 0:
            case 1:
            case 2:
            case 3:
            case 9:
            case 10:
            case 26:
                break;
            default:
                throw new MessageException("Invalid message type.");
        }

        switch(inMessage.getMessageType()) {
            case 0:
                boolean result0 = arrivalTerminalTransferQuay.hasDaysWorkEnded();
                outMessage = new Message(Message.MessageType.BD_ATTQ_HAS_DAYS_WORK_ENDED.getMessageCode(), result0);
                break;
            case 1:
                boolean result1 = arrivalTerminalTransferQuay.announcingBusBoarding();
                outMessage = new Message(Message.MessageType.BD_ATTQ_ANNOUNCING_BUS_BOARDING.getMessageCode(), result1);
                break;
            case 2:
                int result2 = arrivalTerminalTransferQuay.goToDepartureTerminal();
                outMessage = new Message(Message.MessageType.BD_ATTQ_GO_TO_DEPARTURE_TERMINAL.getMessageCode(), result2);
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
                outMessage = new Message(Message.MessageType.PA_ATTQ_ENTER_THE_BUS.getMessageCode(), result10);
                break;
            case 26:
                arrivalTerminalTransferQuay.prepareForNextFlight();
                outMessage = new Message(Message.MessageType.ATTQ_PREPARE_FOR_NEXT_FLIGHT.getMessageCode(), null);
        }

        return (outMessage);
    }

}
