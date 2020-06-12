package HybridServerSide.BaggageReclaimOffice;

import Communication.Message;
import Communication.MessageException;
import HybridServerSide.ArrivalLounge.ArrivalLoungeProxy;
import HybridServerSide.ArrivalLounge.ArrivalLoungeServer;

public class BaggageReclaimOfficeInterface {

    private BaggageReclaimOffice baggageReclaimOffice;

    public BaggageReclaimOfficeInterface(BaggageReclaimOffice baggageReclaimOffice) {
        this.baggageReclaimOffice = baggageReclaimOffice;
    }

    public Message processAndReply(Message inMessage) throws MessageException
    {
        System.out.println("In Message: " + inMessage.toString());

        Message outMessage = null;

        switch(inMessage.getMessageType()) {
            case 12:
                if (inMessage.isThereNoFirstArgument())
                    throw new MessageException("Argument \"missingBags\" not supplied.", inMessage);
                if (((int) inMessage.getFirstArgument()) < 0)
                    throw new MessageException("Argument \"missingBags\" was given an incorrect value.", inMessage);
                break;
            case 61:
                break;
            default:
                throw new MessageException("Invalid message type: " + inMessage.getMessageType());
        }

        switch(inMessage.getMessageType()) {
            case 12:
                baggageReclaimOffice.reportMissingBags(inMessage.getPassengerID(), (int) inMessage.getFirstArgument());
                outMessage = new Message(Message.MessageType.PA_BRO_REPORT_MISSING_BAGS.getMessageCode(), null);
                break;
            case 61:
                BaggageReclaimOfficeServer.running = false;
                (((BaggageReclaimOfficeProxy) (Thread.currentThread ())).getServerCom()).setTimeout(10);
                outMessage = new Message (Message.MessageType.EVERYTHING_FINISHED.getMessageCode(), null);
        }


        System.out.println("Out Message: " + outMessage.toString());
        return (outMessage);
    }

}
