package ClientSide.Stubs;

import ClientSide.ClientCom.ClientCom;
import ClientSide.Interfaces.BROPassenger;
import Communication.Message;
import Communication.MessageException;
import genclass.GenericIO;

/**
 * BaggageReclaimOfficeStub: Client-side BaggageReclaimOffice remote procedure calling stub.
 * @author sergioaguiar
 * @author marcomacedo
 */
public class BaggageReclaimOfficeStub implements BROPassenger {
    /**
     *  Server's host name.
     */
    private String serverHostName;
    /**
     *  Server's listening port.
     */
    private int serverHostPort;
    /**
     * Constructor: BaggageReclaimOfficeStub.
     * @param hostName Server's host name.
     * @param hostPort Server's listening port.
     */
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
                GenericIO.writelnString("Thread " + Thread.currentThread().getName()
                        + ": BROStub: reportMissingBags: " + e.toString());
            }
        }

        try {
            outMessage = new Message(Message.MessageType.PA_BRO_REPORT_MISSING_BAGS.getMessageCode(), pid, missingBags);
        } catch(MessageException e) {
            GenericIO.writelnString("Thread " + Thread.currentThread().getName()
                    + ": BROStub: reportMissingBags: " + e.toString());
        }

        clientCom.writeObject(outMessage);
        inMessage = (Message) clientCom.readObject();

        if(inMessage.getMessageType() != Message.MessageType.PA_BRO_REPORT_MISSING_BAGS.getMessageCode()) {
            GenericIO.writelnString("Thread " + Thread.currentThread().getName()
                    + ": BROStub: reportMissingBags: Incorrect message type!");
            GenericIO.writelnString(inMessage.toString());
            System.exit(1);
        }
        clientCom.close();
    }
    /**
     * Function that alerts to close the server when the simulation is done.
     */
    public void everythingFinished() {
        ClientCom clientCom = new ClientCom (serverHostName, serverHostPort);
        Message inMessage;
        Message outMessage = null;

        while (!clientCom.open()) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                GenericIO.writelnString("Thread " + Thread.currentThread().getName()
                        + ": BROStub: everythingFinished: " + e.toString());
            }
        }

        try {
            outMessage = new Message(Message.MessageType.EVERYTHING_FINISHED.getMessageCode(), false, true);
        } catch(MessageException e) {
            GenericIO.writelnString("Thread " + Thread.currentThread().getName()
                    + ": BROStub: everythingFinished: " + e.toString());
        }

        clientCom.writeObject(outMessage);
        inMessage = (Message) clientCom.readObject();

        if(inMessage.getMessageType() != Message.MessageType.EVERYTHING_FINISHED.getMessageCode()) {
            GenericIO.writelnString("Thread " + Thread.currentThread().getName()
                    + ": BROStub: everythingFinished: Incorrect message type!");
            GenericIO.writelnString(inMessage.toString());
            System.exit(1);
        }
        clientCom.close();
    }
}
