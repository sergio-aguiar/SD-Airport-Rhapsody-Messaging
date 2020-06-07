package HybridServerSide.Stubs;

import ClientSide.ClientCom.ClientCom;
import Communication.Message;
import Communication.MessageException;
import HybridServerSide.Interfaces.DTEforATE;
import genclass.GenericIO;

public class DepartureTerminalEntranceStub implements DTEforATE {

    private String serverHostName;
    private int serverHostPort;

    public DepartureTerminalEntranceStub(String hostName, int hostPort) {
        this.serverHostName = hostName;
        this.serverHostPort = hostPort;
    }

    /**
     * Allows the ArrivalTerminalExit instance to know the number of passengers awaiting at the DepartureTerminalEntrance.
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
                System.out.println("Thread " + Thread.currentThread().getName() + ": DTEStub: getWaitingPassengers: " + e.toString());
            }
        }

        try {
            outMessage = new Message(Message.MessageType.DTE_GET_WAITING_PASSENGERS.getMessageCode(), false, true);
        } catch(MessageException e) {
            System.out.println("Thread " + Thread.currentThread().getName() + ": DTEStub: getWaitingPassengers: " + e.toString());
        }

        clientCom.writeObject(outMessage);
        inMessage = (Message) clientCom.readObject();

        if(inMessage.getMessageType() != Message.MessageType.DTE_GET_WAITING_PASSENGERS.getMessageCode()) {
            GenericIO.writelnString("Thread " + Thread.currentThread().getName() + ": DTEStub: getWaitingPassengers: Incorrect message type!");
            GenericIO.writelnString(inMessage.toString());
            System.exit(1);
        }

        if(inMessage.isThereNoReturnInfo()) {
            GenericIO.writelnString("Thread " + Thread.currentThread().getName() + ": DTEStub: getWaitingPassengers: The return value is missing!");
            GenericIO.writelnString(inMessage.toString());
            System.exit(1);
        }
        clientCom.close();

        return (int) inMessage.getReturnInfo();
    }

    /**
     * Allows the ArrivalTerminalExit instance to signal all waiting passengers at the DepartureTerminalEntrance.
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
                System.out.println("Thread " + Thread.currentThread().getName() + ": DTEStub: signalWaitingPassengers: " + e.toString());
            }
        }

        try {
            outMessage = new Message(Message.MessageType.DTE_SIGNAL_WAITING_PASSENGERS.getMessageCode(), false, true);
        } catch(MessageException e) {
            System.out.println("Thread " + Thread.currentThread().getName() + ": DTEStub: signalWaitingPassengers: " + e.toString());
        }

        clientCom.writeObject(outMessage);
        inMessage = (Message) clientCom.readObject();

        if(inMessage.getMessageType() != Message.MessageType.DTE_SIGNAL_WAITING_PASSENGERS.getMessageCode()) {
            GenericIO.writelnString("Thread " + Thread.currentThread().getName() + ": DTEStub: signalWaitingPassengers: Incorrect message type!");
            GenericIO.writelnString(inMessage.toString());
            System.exit(1);
        }
        clientCom.close();
    }
}
