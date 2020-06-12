package HybridServerSide.TemporaryStorageArea;

import Communication.Message;
import Communication.MessageException;
import HybridServerSide.Repository.RepositoryProxy;
import HybridServerSide.Repository.RepositoryServer;

public class TemporaryStorageAreaInterface {

    private TemporaryStorageArea temporaryStorageArea;

    public TemporaryStorageAreaInterface(TemporaryStorageArea temporaryStorageArea) {
        this.temporaryStorageArea = temporaryStorageArea;
    }

    public Message processAndReply(Message inMessage) throws MessageException
    {
        System.out.println("In Message: " + inMessage.toString());

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
                outMessage = new Message(Message.MessageType.PO_TSA_CARRY_IT_TO_APPROPRIATE_STORE.getMessageCode(), null);
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

        System.out.println("Out Message: " + outMessage.toString());
        return (outMessage);
    }

}
