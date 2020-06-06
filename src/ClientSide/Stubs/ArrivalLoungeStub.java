package ClientSide.Stubs;

import ClientSide.ClientCom.ClientCom;
import ClientSide.Interfaces.ALPassenger;
import ClientSide.Interfaces.ALPorter;
import Communication.Message;
import Communication.MessageException;
import genclass.GenericIO;

public class ArrivalLoungeStub implements ALPassenger, ALPorter {

    private String serverHostName;
    private int serverHostPort;

    public ArrivalLoungeStub(String hostName, int hostPort) {
        this.serverHostName = hostName;
        this.serverHostPort = hostPort;
    }

    /**
     * Passenger's first action when arriving from the plane. Signal's the porter if the final passenger to arrive.
     *
     * @param pid       The passenger's ID.
     * @param situation A string representation of the passenger's travel situation.
     */
    @Override
    public void whatShouldIDo(int pid, String situation) {
        ClientCom clientCom = new ClientCom (serverHostName, serverHostPort);
        Message inMessage;
        Message outMessage = null;

        while (!clientCom.open()) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                System.out.println("Thread " + Thread.currentThread().getName() + ": ALStub: whatShouldIDo: " + e.toString());
            }
        }

        try {
            outMessage = new Message(Message.MessageType.PA_AL_WHAT_SHOULD_I_DO.getMessageCode(), pid, situation);
        } catch(MessageException e) {
            System.out.println("Thread " + Thread.currentThread().getName() + ": ALStub: whatShouldIDo: " + e.toString());
        }

        clientCom.writeObject(outMessage);
        inMessage = (Message) clientCom.readObject();

        if(inMessage.getMessageType() != Message.MessageType.PA_AL_WHAT_SHOULD_I_DO.getMessageCode()) {
            GenericIO.writelnString("Thread " + Thread.currentThread().getName() + ": ALStub: whatShouldIDo: Incorrect message type!");
            GenericIO.writelnString(inMessage.toString());
            System.exit(1);
        }
        clientCom.close();
    }

    /**
     * The Passenger decides to move towards the Baggage Collection Point to collect a bag.
     *
     * @param pid The passenger's ID.
     */
    @Override
    public void goCollectABag(int pid) {
        ClientCom clientCom = new ClientCom (serverHostName, serverHostPort);
        Message inMessage;
        Message outMessage = null;

        while (!clientCom.open()) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                System.out.println("Thread " + Thread.currentThread().getName() + ": ALStub: goCollectABag: " + e.toString());
            }
        }

        try {
            outMessage = new Message(Message.MessageType.PA_AL_GO_COLLECT_A_BAG.getMessageCode(), pid);
        } catch(MessageException e) {
            System.out.println("Thread " + Thread.currentThread().getName() + ": ALStub: goCollectABag: " + e.toString());
        }

        clientCom.writeObject(outMessage);
        inMessage = (Message) clientCom.readObject();

        if(inMessage.getMessageType() != Message.MessageType.PA_AL_GO_COLLECT_A_BAG.getMessageCode()) {
            GenericIO.writelnString("Thread " + Thread.currentThread().getName() + ": ALStub: goCollectABag: Incorrect message type!");
            GenericIO.writelnString(inMessage.toString());
            System.exit(1);
        }
        clientCom.close();
    }

    /**
     * The porter checks whether he is still needed in the future. If so, he awaits the next flight. He stops otherwise.
     *
     * @return true if the porter isn't needed anymore and false otherwise.
     */
    @Override
    public boolean takeARest() {
        ClientCom clientCom = new ClientCom (serverHostName, serverHostPort);
        Message inMessage;
        Message outMessage = null;

        while (!clientCom.open()) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                System.out.println("Thread " + Thread.currentThread().getName() + ": ALStub: takeARest: " + e.toString());
            }
        }

        try {
            outMessage = new Message(Message.MessageType.PO_AL_TAKE_A_REST.getMessageCode(), true);
        } catch(MessageException e) {
            System.out.println("Thread " + Thread.currentThread().getName() + ": ALStub: takeARest: " + e.toString());
        }

        clientCom.writeObject(outMessage);
        inMessage = (Message) clientCom.readObject();

        if(inMessage.getMessageType() != Message.MessageType.PO_AL_TAKE_A_REST.getMessageCode()) {
            GenericIO.writelnString("Thread " + Thread.currentThread().getName() + ": ALStub: takeARest: Incorrect message type!");
            GenericIO.writelnString(inMessage.toString());
            System.exit(1);
        }

        if(inMessage.isThereNoReturnInfo()) {
            GenericIO.writelnString("Thread " + Thread.currentThread().getName() + ": ALStub: takeARest: The return value is missing!");
            GenericIO.writelnString(inMessage.toString());
            System.exit(1);
        }
        clientCom.close();

        return (boolean) inMessage.getReturnInfo();
    }

    /**
     * The porter tries to collect a bag from the plane's hold.
     *
     * @return the bag's owner ID as a String, or an empty String if the plane is empty of bags.
     */
    @Override
    public String tryToCollectABag() {
        ClientCom clientCom = new ClientCom (serverHostName, serverHostPort);
        Message inMessage;
        Message outMessage = null;

        while (!clientCom.open()) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                System.out.println("Thread " + Thread.currentThread().getName() + ": ALStub: tryToCollectABag: " + e.toString());
            }
        }

        try {
            outMessage = new Message(Message.MessageType.PO_AL_TRY_TO_COLLECT_A_BAG.getMessageCode(), true);
        } catch(MessageException e) {
            System.out.println("Thread " + Thread.currentThread().getName() + ": ALStub: tryToCollectABag: " + e.toString());
        }

        clientCom.writeObject(outMessage);
        inMessage = (Message) clientCom.readObject();

        if(inMessage.getMessageType() != Message.MessageType.PO_AL_TRY_TO_COLLECT_A_BAG.getMessageCode()) {
            GenericIO.writelnString("Thread " + Thread.currentThread().getName() + ": ALStub: tryToCollectABag: Incorrect message type!");
            GenericIO.writelnString(inMessage.toString());
            System.exit(1);
        }

        if(inMessage.isThereNoReturnInfo()) {
            GenericIO.writelnString("Thread " + Thread.currentThread().getName() + ": ALStub: tryToCollectABag: The return value is missing!");
            GenericIO.writelnString(inMessage.toString());
            System.exit(1);
        }
        clientCom.close();

        return (String) inMessage.getReturnInfo();
    }
}
