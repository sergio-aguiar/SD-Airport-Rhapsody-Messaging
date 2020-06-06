package ClientSide.Stubs;

import ClientSide.ClientCom.ClientCom;
import ClientSide.Interfaces.TSAPorter;
import Communication.Message;
import Communication.MessageException;
import genclass.GenericIO;

public class TemporaryStorageAreaStub implements TSAPorter {

    private String serverHostName;
    private int serverHostPort;

    public TemporaryStorageAreaStub(String hostName, int hostPort) {
        this.serverHostName = hostName;
        this.serverHostPort = hostPort;
    }

    /**
     * The Porter carries their held bag to the Temporary Storage Area.
     *
     * @param bagID The porter's held bag's owner's ID.
     */
    @Override
    public void carryItToAppropriateStore(int bagID) {
        ClientCom clientCom = new ClientCom (serverHostName, serverHostPort);
        Message inMessage;
        Message outMessage = null;

        while (!clientCom.open()) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                System.out.println("Thread " + Thread.currentThread().getName() + ": TSAQStub: carryItToAppropriateStore: " + e.toString());
            }
        }

        try {
            outMessage = new Message(Message.MessageType.PO_TSA_CARRY_IT_TO_APPROPRIATE_STORE.getMessageCode(), true, bagID);
        } catch(MessageException e) {
            System.out.println("Thread " + Thread.currentThread().getName() + ": TSAQStub: carryItToAppropriateStore: " + e.toString());
        }

        clientCom.writeObject(outMessage);
        inMessage = (Message) clientCom.readObject();

        if(inMessage.getMessageType() != Message.MessageType.PO_TSA_CARRY_IT_TO_APPROPRIATE_STORE.getMessageCode()) {
            GenericIO.writelnString("Thread " + Thread.currentThread().getName() + ": TSAQStub: carryItToAppropriateStore: Incorrect message type!");
            GenericIO.writelnString(inMessage.toString());
            System.exit(1);
        }
        clientCom.close();
    }
}
