package ClientSide.Stubs;

import ClientSide.ClientCom.ClientCom;
import ClientSide.Interfaces.BROPassenger;
import Communication.Message;
import Communication.MessageException;
import genclass.GenericIO;

public class BaggageReclaimOfficeStub implements BROPassenger {

    private String serverHostName;
    private int serverHostPort;

    public BaggageReclaimOfficeStub(String hostName, int hostPort) {
        this.serverHostName = hostName;
        this.serverHostPort = hostPort;
    }

    /**
     * Passengers report how many missing bags they have.
     *
     * @param pid         The passenger's ID.
     * @param missingBags The passenger's amount of missing bags.
     */
    @Override
    public void reportMissingBags(int pid, int missingBags) {
        ClientCom clientCom = new ClientCom (serverHostName, serverHostPort);
        Message inMessage;
        Message outMessage = null;

        while (!clientCom.open()) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                System.out.println("Thread " + Thread.currentThread().getName() + ": BROStub: reportMissingBags: " + e.toString());
            }
        }

        try {
            outMessage = new Message(Message.MessageType.PA_BRO_REPORT_MISSING_BAGS.getMessageCode(), pid, missingBags);
        } catch(MessageException e) {
            System.out.println("Thread " + Thread.currentThread().getName() + ": BROStub: reportMissingBags: " + e.toString());
        }

        clientCom.writeObject(outMessage);
        inMessage = (Message) clientCom.readObject();

        if(inMessage.getMessageType() != Message.MessageType.PA_BRO_REPORT_MISSING_BAGS.getMessageCode()) {
            GenericIO.writelnString("Thread " + Thread.currentThread().getName() + ": BROStub: reportMissingBags: Incorrect message type!");
            GenericIO.writelnString(inMessage.toString());
            System.exit(1);
        }
        clientCom.close();
    }
}
