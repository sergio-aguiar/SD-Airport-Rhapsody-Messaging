package HybridServerSide.BaggageReclaimOffice;

import Communication.Message;
import Communication.MessageException;
import genclass.GenericIO;

/**
 * BaggageReclaimOfficeInterface: BaggageReclaimOffice message processing and replying.
 * @author sergioaguiar
 * @author marcomacedo
 */
public class BaggageReclaimOfficeInterface {
    /**
     * A reference to the server's BaggageReclaimOffice.
     */
    private BaggageReclaimOffice baggageReclaimOffice;
    /**
     * Constructor: BaggageReclaimOffice.
     * @param baggageReclaimOffice A reference to the server's BaggageReclaimOffice.
     */
    public BaggageReclaimOfficeInterface(BaggageReclaimOffice baggageReclaimOffice) {
        this.baggageReclaimOffice = baggageReclaimOffice;
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


        GenericIO.writelnString("[Out]: " + outMessage.toString());
        return (outMessage);
    }

}
