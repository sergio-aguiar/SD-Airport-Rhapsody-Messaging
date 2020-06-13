package HybridServerSide.TemporaryStorageArea;

import Communication.Message;
import Communication.MessageException;

/**
 * TemporaryStorageAreaInterface: TemporaryStorageArea message processing and replying.
 * @author sergioaguiar
 * @author marcomacedo
 */
public class TemporaryStorageAreaInterface {
    /**
     * A reference to the server's TemporaryStorageArea.
     */
    private TemporaryStorageArea temporaryStorageArea;
    /**
     * Constructor: TemporaryStorageArea.
     * @param temporaryStorageArea A reference to the server's TemporaryStorageArea.
     */
    public TemporaryStorageAreaInterface(TemporaryStorageArea temporaryStorageArea) {
        this.temporaryStorageArea = temporaryStorageArea;
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
            case 19:
                if (inMessage.isThereNoFirstArgument())
                    throw new MessageException("Argument \"bagID\" not supplied.", inMessage);
                if (((int) inMessage.getFirstArgument()) < 0)
                    throw new MessageException("Argument \"bagID\" was given an incorrect value.", inMessage);
                break;
            case 30:
            case 61:
                break;
            default:
                throw new MessageException("Invalid message type: " + inMessage.getMessageType());
        }

        switch(inMessage.getMessageType()) {
            case 19:
                temporaryStorageArea.carryItToAppropriateStore((int) inMessage.getFirstArgument());
                outMessage = new Message(Message.MessageType.PO_TSA_CARRY_IT_TO_APPROPRIATE_STORE.getMessageCode(),
                        null);
                break;
            case 30:
                temporaryStorageArea.prepareForNextFlight();
                outMessage = new Message(Message.MessageType.TSA_PREPARE_FOR_NEXT_FLIGHT.getMessageCode(), null);
                break;
            case 61:
                TemporaryStorageAreaServer.running = false;
                (((TemporaryStorageAreaProxy) (Thread.currentThread ())).getServerCom()).setTimeout(10);
                outMessage = new Message (Message.MessageType.EVERYTHING_FINISHED.getMessageCode(), null);
        }
        return (outMessage);
    }

}
