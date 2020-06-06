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
                break;
            case 1:
                break;
            default:
                throw new MessageException("Invalid message type.");
        }

        switch(inMessage.getMessageType()) {
            case 0:
                break;
            case 1:
                break;
        }

        return (outMessage);
    }

}
