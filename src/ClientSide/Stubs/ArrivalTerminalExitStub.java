package ClientSide.Stubs;

import ClientSide.ClientCom.ClientCom;
import ClientSide.Interfaces.ATEPassenger;
import Communication.Message;
import Communication.MessageException;
import genclass.GenericIO;

public class ArrivalTerminalExitStub implements ATEPassenger {

    private String serverHostName;
    private int serverHostPort;

    public ArrivalTerminalExitStub(String hostName, int hostPort) {
        this.serverHostName = hostName;
        this.serverHostPort = hostPort;
    }

    /**
     * The passenger checks if they is the last to make it to their destination inside the airport. If so, they signal all others to leave together. Otherwise, they wait for the last one to signal them.
     *
     * @param pid The passenger's ID.
     */
    @Override
    public void goHome(int pid) {
        ClientCom clientCom = new ClientCom (serverHostName, serverHostPort);
        Message inMessage;
        Message outMessage = null;

        while (!clientCom.open()) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                System.out.println("Thread " + Thread.currentThread().getName() + ": ATEStub: goHome: " + e.toString());
            }
        }

        try {
            outMessage = new Message(Message.MessageType.PA_ATE_GO_HOME.getMessageCode(), pid);
        } catch(MessageException e) {
            System.out.println("Thread " + Thread.currentThread().getName() + ": ATEStub: goHome: " + e.toString());
        }

        clientCom.writeObject(outMessage);
        inMessage = (Message) clientCom.readObject();

        if(inMessage.getMessageType() != Message.MessageType.PA_ATE_GO_HOME.getMessageCode()) {
            GenericIO.writelnString("Thread " + Thread.currentThread().getName() + ": ATEStub: goHome: Incorrect message type!");
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
                System.out.println("Thread " + Thread.currentThread().getName() + ": ATEStub: prepareForNextFlight: " + e.toString());
            }
        }

        try {
            outMessage = new Message(Message.MessageType.ATE_PREPARE_FOR_NEXT_FLIGHT.getMessageCode(), false, true);
        } catch(MessageException e) {
            System.out.println("Thread " + Thread.currentThread().getName() + ": ATEStub: prepareForNextFlight: " + e.toString());
        }

        clientCom.writeObject(outMessage);
        inMessage = (Message) clientCom.readObject();

        if(inMessage.getMessageType() != Message.MessageType.ATE_PREPARE_FOR_NEXT_FLIGHT.getMessageCode()) {
            GenericIO.writelnString("Thread " + Thread.currentThread().getName() + ": ATEStub: prepareForNextFlight: Incorrect message type!");
            GenericIO.writelnString(inMessage.toString());
            System.exit(1);
        }
        clientCom.close();
    }

    public void setInitialState(int totalPassengers) {
        ClientCom clientCom = new ClientCom (serverHostName, serverHostPort);
        Message inMessage;
        Message outMessage = null;

        while (!clientCom.open()) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                System.out.println("Thread " + Thread.currentThread().getName() + ": ATEStub: setInitialState: " + e.toString());
            }
        }

        try {
            outMessage = new Message(Message.MessageType.ATE_SET_INITIAL_STATE.getMessageCode(), (Object) totalPassengers, null, null);
        } catch(MessageException e) {
            System.out.println("Thread " + Thread.currentThread().getName() + ": ATEStub: setInitialState: " + e.toString());
        }

        clientCom.writeObject(outMessage);
        inMessage = (Message) clientCom.readObject();

        if(inMessage.getMessageType() != Message.MessageType.ATE_SET_INITIAL_STATE.getMessageCode()) {
            GenericIO.writelnString("Thread " + Thread.currentThread().getName() + ": ATEStub: setInitialState: Incorrect message type!");
            GenericIO.writelnString(inMessage.toString());
            System.exit(1);
        }
        clientCom.close();
    }
}
