package HybridServerSide.BaggageReclaimOffice;

import Communication.Message;
import Communication.MessageException;
import HybridServerSide.ArrivalLounge.ArrivalLounge;

public class BaggageReclaimOfficeInterface {

    private BaggageReclaimOffice baggageReclaimOffice;

    public BaggageReclaimOfficeInterface(BaggageReclaimOffice baggageReclaimOffice) {
        this.baggageReclaimOffice = baggageReclaimOffice;
    }

    public Message processAndReply(Message inMessage) throws MessageException
    {
        Message outMessage = null;

        if(inMessage.getMessageType() == 12) {
            if (inMessage.isThereNoFirstArgument())
                throw new MessageException("Argument \"missingBags\" not supplied.", inMessage);
            if (((int) inMessage.getFirstArgument()) < 0)
                throw new MessageException("Argument \"missingBags\" was given an incorrect value.", inMessage);
        } else {
            throw new MessageException("Invalid message type.");
        }

        if(inMessage.getMessageType() == 12) {
            baggageReclaimOffice.reportMissingBags(inMessage.getPassengerID(), (int) inMessage.getFirstArgument());
            outMessage = new Message(Message.MessageType.PA_BRO_REPORT_MISSING_BAGS.getMessageCode(), null);
        }

        return (outMessage);
    }

}
