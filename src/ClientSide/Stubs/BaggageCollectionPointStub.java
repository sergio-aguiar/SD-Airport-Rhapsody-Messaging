package ClientSide.Stubs;

import ClientSide.ClientCom.ClientCom;
import ClientSide.Interfaces.BCPPassenger;
import ClientSide.Interfaces.BCPPorter;
import Communication.Message;
import Communication.MessageException;
import genclass.GenericIO;

/**
 * BaggageCollectionPointStub: Client-side BaggageCollectionPoint remote procedure calling stub.
 * @author sergioaguiar
 * @author marcomacedo
 */
public class BaggageCollectionPointStub implements BCPPassenger, BCPPorter {
    /**
     *  Server's host name.
     */
    private String serverHostName;
    /**
     *  Server's listening port.
     */
    private int serverHostPort;

    /**
     * Constructor: BaggageCollectionPointStub.
     * @param hostName Server's host name.
     * @param hostPort Server's listening port.
     */
    public BaggageCollectionPointStub(String hostName, int hostPort) {
        this.serverHostName = hostName;
        this.serverHostPort = hostPort;
    }
    /**
     * The Passenger tries to collect a bag from the conveyor.
     *
     * @param pid The passenger's ID.
     * @return true if passenger succeeded in collecting a bag and false otherwise.
     */
    @Override
    public boolean goCollectABag(int pid) {
        ClientCom clientCom = new ClientCom (serverHostName, serverHostPort);
        Message inMessage;
        Message outMessage = null;

        while (!clientCom.open()) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                GenericIO.writelnString("Thread " + Thread.currentThread().getName()
                        + ": BCPQStub: goCollectABag: " + e.toString());
            }
        }

        try {
            outMessage = new Message(Message.MessageType.PA_BCP_GO_COLLECT_A_BAG.getMessageCode(), pid);
        } catch(MessageException e) {
            GenericIO.writelnString("Thread " + Thread.currentThread().getName()
                    + ": BCPQStub: goCollectABag: " + e.toString());
        }

        clientCom.writeObject(outMessage);
        inMessage = (Message) clientCom.readObject();

        if(inMessage.getMessageType() != Message.MessageType.PA_BCP_GO_COLLECT_A_BAG.getMessageCode()) {
            GenericIO.writelnString("Thread " + Thread.currentThread().getName()
                    + ": BCPQStub: goCollectABag: Incorrect message type!");
            GenericIO.writelnString(inMessage.toString());
            System.exit(1);
        }

        if(inMessage.isThereNoReturnInfo()) {
            GenericIO.writelnString("Thread " + Thread.currentThread().getName()
                    + ": BCPQStub: goCollectABag: The return value is missing!");
            GenericIO.writelnString(inMessage.toString());
            System.exit(1);
        }
        clientCom.close();

        return (boolean) inMessage.getReturnInfo();
    }
    /**
     * The Porter carries their held bag to the Baggage Collection Point.
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
                GenericIO.writelnString("Thread " + Thread.currentThread().getName()
                        + ": BCPQStub: carryItToAppropriateStore: " + e.toString());
            }
        }

        try {
            outMessage = new Message(Message.MessageType.PO_BCP_CARRY_IT_TO_APPROPRIATE_STORE.getMessageCode(), true,
                    bagID);
        } catch(MessageException e) {
            GenericIO.writelnString("Thread " + Thread.currentThread().getName()
                    + ": BCPQStub: carryItToAppropriateStore: " + e.toString());
        }

        clientCom.writeObject(outMessage);
        inMessage = (Message) clientCom.readObject();

        if(inMessage.getMessageType() != Message.MessageType.PO_BCP_CARRY_IT_TO_APPROPRIATE_STORE.getMessageCode()) {
            GenericIO.writelnString("Thread " + Thread.currentThread().getName()
                    + ": BCPQStub: carryItToAppropriateStore: Incorrect message type!");
            GenericIO.writelnString(inMessage.toString());
            System.exit(1);
        }
        clientCom.close();
    }
    /**
     * The porter announces that there are no more bags in the plane by signalling every waiting passenger.
     *
     * @param pid The passenger's ID.
     */
    @Override
    public void noMoreBagsToCollect(int pid) {
        ClientCom clientCom = new ClientCom (serverHostName, serverHostPort);
        Message inMessage;
        Message outMessage = null;

        while (!clientCom.open()) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                GenericIO.writelnString("Thread " + Thread.currentThread().getName()
                        + ": BCPQStub: noMoreBagsToCollect: " + e.toString());
            }
        }

        try {
            outMessage = new Message(Message.MessageType.PO_BCP_NO_MORE_BAGS_TO_COLLECT.getMessageCode(), true, pid);
        } catch(MessageException e) {
            GenericIO.writelnString("Thread " + Thread.currentThread().getName()
                    + ": BCPQStub: noMoreBagsToCollect: " + e.toString());
        }

        clientCom.writeObject(outMessage);
        inMessage = (Message) clientCom.readObject();

        if(inMessage.getMessageType() != Message.MessageType.PO_BCP_NO_MORE_BAGS_TO_COLLECT.getMessageCode()) {
            GenericIO.writelnString("Thread " + Thread.currentThread().getName()
                    + ": BCPQStub: noMoreBagsToCollect: Incorrect message type!");
            GenericIO.writelnString(inMessage.toString());
            System.exit(1);
        }
        clientCom.close();
    }

    public void prepareForNextFlight() {
        ClientCom clientCom = new ClientCom (serverHostName, serverHostPort);
        Message inMessage;
        Message outMessage = null;

        while (!clientCom.open()) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                GenericIO.writelnString("Thread " + Thread.currentThread().getName()
                        + ": BCPStub: prepareForNextFlight: " + e.toString());
            }
        }

        try {
            outMessage = new Message(Message.MessageType.BCP_PREPARE_FOR_NEXT_FLIGHT.getMessageCode(), false, true);
        } catch(MessageException e) {
            GenericIO.writelnString("Thread " + Thread.currentThread().getName()
                    + ": BCPStub: prepareForNextFlight: " + e.toString());
        }

        clientCom.writeObject(outMessage);
        inMessage = (Message) clientCom.readObject();

        if(inMessage.getMessageType() != Message.MessageType.BCP_PREPARE_FOR_NEXT_FLIGHT.getMessageCode()) {
            GenericIO.writelnString("Thread " + Thread.currentThread().getName()
                    + ": BCPStub: prepareForNextFlight: Incorrect message type!");
            GenericIO.writelnString(inMessage.toString());
            System.exit(1);
        }
        clientCom.close();
    }
    /**
     * Function that sets the initial state of a server's data.
     * @param totalPassengers Total number of passengers per flight.
     */
    public void setInitialState(int totalPassengers) {
        ClientCom clientCom = new ClientCom (serverHostName, serverHostPort);
        Message inMessage;
        Message outMessage = null;

        while (!clientCom.open()) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                GenericIO.writelnString("Thread " + Thread.currentThread().getName()
                        + ": BCPStub: setInitialState: " + e.toString());
            }
        }

        try {
            outMessage = new Message(Message.MessageType.BCP_SET_INITIAL_STATE.getMessageCode(),
                    (Object) totalPassengers, null, null);
        } catch(MessageException e) {
            GenericIO.writelnString("Thread " + Thread.currentThread().getName()
                    + ": BCPStub: setInitialState: " + e.toString());
        }

        clientCom.writeObject(outMessage);
        inMessage = (Message) clientCom.readObject();

        if(inMessage.getMessageType() != Message.MessageType.BCP_SET_INITIAL_STATE.getMessageCode()) {
            GenericIO.writelnString("Thread " + Thread.currentThread().getName()
                    + ": BCPStub: setInitialState: Incorrect message type!");
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
                        + ": BCPStub: everythingFinished: " + e.toString());
            }
        }

        try {
            outMessage = new Message(Message.MessageType.EVERYTHING_FINISHED.getMessageCode(), false, true);
        } catch(MessageException e) {
            GenericIO.writelnString("Thread " + Thread.currentThread().getName()
                    + ": BCPStub: everythingFinished: " + e.toString());
        }

        clientCom.writeObject(outMessage);
        inMessage = (Message) clientCom.readObject();

        if(inMessage.getMessageType() != Message.MessageType.EVERYTHING_FINISHED.getMessageCode()) {
            GenericIO.writelnString("Thread " + Thread.currentThread().getName()
                    + ": BCPStub: everythingFinished: Incorrect message type!");
            GenericIO.writelnString(inMessage.toString());
            System.exit(1);
        }
        clientCom.close();
    }
}
