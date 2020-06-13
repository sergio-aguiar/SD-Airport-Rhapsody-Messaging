package HybridServerSide.Stubs;

import ClientSide.ClientCom.ClientCom;
import Communication.Message;
import Communication.MessageException;
import HybridServerSide.Interfaces.ATEforDTE;
import genclass.GenericIO;

/**
 * ArrivalTerminalExitStubStub: Hybrid-Server-side ArrivalTerminalExit remote procedure calling stub.
 * @author sergioaguiar
 * @author marcomacedo
 */
public class ArrivalTerminalExitStub implements ATEforDTE {
    /**
     *  Server's host name.
     */
    private String serverHostName;
    /**
     *  Server's listening port.
     */
    private int serverHostPort;
    /**
     * Constructor: ArrivalTerminalExitStub
     * @param hostName Server's host name.
     * @param hostPort Server's listening port.
     */
    public ArrivalTerminalExitStub(String hostName, int hostPort) {
        this.serverHostName = hostName;
        this.serverHostPort = hostPort;
    }
    /**
     * Allows the DepartureTerminalEntrance instance to know the number of passengers awaiting at the
     * ArrivalTerminalExit.
     *
     * @return the number of waiting passengers at the ArrivalTerminalExit.
     */
    @Override
    public int getWaitingPassengers() {
        ClientCom clientCom = new ClientCom (serverHostName, serverHostPort);
        Message inMessage;
        Message outMessage = null;

        while (!clientCom.open()) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                GenericIO.writelnString("Thread " + Thread.currentThread().getName()
                        + ": ATEStub: getWaitingPassengers: " + e.toString());
            }
        }

        try {
            outMessage = new Message(Message.MessageType.ATE_GET_WAITING_PASSENGERS.getMessageCode(), false, true);
        } catch(MessageException e) {
            GenericIO.writelnString("Thread " + Thread.currentThread().getName()
                    + ": ATEStub: getWaitingPassengers: " + e.toString());
        }

        clientCom.writeObject(outMessage);
        inMessage = (Message) clientCom.readObject();

        if(inMessage.getMessageType() != Message.MessageType.ATE_GET_WAITING_PASSENGERS.getMessageCode()) {
            GenericIO.writelnString("Thread " + Thread.currentThread().getName()
                    + ": ATEStub: getWaitingPassengers: Incorrect message type!");
            GenericIO.writelnString(inMessage.toString());
            System.exit(1);
        }

        if(inMessage.isThereNoReturnInfo()) {
            GenericIO.writelnString("Thread " + Thread.currentThread().getName()
                    + ": ATEStub: getWaitingPassengers: The return value is missing!");
            GenericIO.writelnString(inMessage.toString());
            System.exit(1);
        }
        clientCom.close();

        return (int) inMessage.getReturnInfo();
    }
    /**
     * Allows the DepartureTerminalEntrance instance to signal all waiting passengers at the ArrivalTerminalExit.
     */
    @Override
    public void signalWaitingPassengers() {
        ClientCom clientCom = new ClientCom (serverHostName, serverHostPort);
        Message inMessage;
        Message outMessage = null;

        while (!clientCom.open()) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                GenericIO.writelnString("Thread " + Thread.currentThread().getName()
                        + ": ATEStub: signalWaitingPassengers: " + e.toString());
            }
        }

        try {
            outMessage = new Message(Message.MessageType.ATE_SIGNAL_WAITING_PASSENGERS.getMessageCode(), false, true);
        } catch(MessageException e) {
            GenericIO.writelnString("Thread " + Thread.currentThread().getName()
                    + ": ATEStub: signalWaitingPassengers: " + e.toString());
        }

        clientCom.writeObject(outMessage);
        inMessage = (Message) clientCom.readObject();

        if(inMessage.getMessageType() != Message.MessageType.ATE_SIGNAL_WAITING_PASSENGERS.getMessageCode()) {
            GenericIO.writelnString("Thread " + Thread.currentThread().getName()
                    + ": ATEStub: signalWaitingPassengers: Incorrect message type!");
            GenericIO.writelnString(inMessage.toString());
            System.exit(1);
        }
        clientCom.close();
    }
}
