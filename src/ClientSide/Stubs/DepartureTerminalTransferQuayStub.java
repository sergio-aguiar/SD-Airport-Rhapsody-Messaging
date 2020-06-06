package ClientSide.Stubs;

import ClientSide.ClientCom.ClientCom;
import ClientSide.Interfaces.DTTQBusDriver;
import ClientSide.Interfaces.DTTQPassenger;
import Communication.Message;
import Communication.MessageException;
import genclass.GenericIO;

public class DepartureTerminalTransferQuayStub implements DTTQPassenger, DTTQBusDriver {

    private String serverHostName;
    private int serverHostPort;

    public DepartureTerminalTransferQuayStub(String hostName, int hostPort) {
        this.serverHostName = hostName;
        this.serverHostPort = hostPort;
    }

    /**
     * The bus driver parks the Bus and let's teh passengers off.
     *
     * @param passengersThatArrived The number of passengers that arrived aboard the bus.
     * @param flightNumber          The bus driver's estimated flight number.
     * @return The current flight number.
     */
    @Override
    public int parkTheBusAndLetPassOff(int passengersThatArrived, int flightNumber) {
        ClientCom clientCom = new ClientCom (serverHostName, serverHostPort);
        Message inMessage;
        Message outMessage = null;

        while (!clientCom.open()) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                System.out.println("Thread " + Thread.currentThread().getName() + ": DTTQStub: parkTheBusAndLetPassOff: " + e.toString());
            }
        }

        try {
            outMessage = new Message(Message.MessageType.BD_DTTQ_PARK_THE_BUS_AND_LET_PASS_OFF.getMessageCode(), false, passengersThatArrived, flightNumber);
        } catch(MessageException e) {
            System.out.println("Thread " + Thread.currentThread().getName() + ": DTTQStub: parkTheBusAndLetPassOff: " + e.toString());
        }

        clientCom.writeObject(outMessage);
        inMessage = (Message) clientCom.readObject();

        if(inMessage.getMessageType() != Message.MessageType.BD_DTTQ_PARK_THE_BUS_AND_LET_PASS_OFF.getMessageCode()) {
            GenericIO.writelnString("Thread " + Thread.currentThread().getName() + ": DTTQStub: parkTheBusAndLetPassOff: Incorrect message type!");
            GenericIO.writelnString(inMessage.toString());
            System.exit(1);
        }

        if(inMessage.isThereNoReturnInfo()) {
            GenericIO.writelnString("Thread " + Thread.currentThread().getName() + ": DTTQStub: parkTheBusAndLetPassOff: The return value is missing!");
            GenericIO.writelnString(inMessage.toString());
            System.exit(1);
        }
        clientCom.close();

        return (int) inMessage.getReturnInfo();
    }

    /**
     * The bus driver drives towards the Arrival Terminal Transfer Quay.
     *
     */
    @Override
    public void goToArrivalTerminal() {
        ClientCom clientCom = new ClientCom (serverHostName, serverHostPort);
        Message inMessage;
        Message outMessage = null;

        while (!clientCom.open()) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                System.out.println("Thread " + Thread.currentThread().getName() + ": DTTQStub: goToArrivalTerminal: " + e.toString());
            }
        }

        try {
            outMessage = new Message(Message.MessageType.BD_DTTQ_GO_TO_ARRIVAL_TERMINAL.getMessageCode(), false);
        } catch(MessageException e) {
            System.out.println("Thread " + Thread.currentThread().getName() + ": DTTQStub: goToArrivalTerminal: " + e.toString());
        }

        clientCom.writeObject(outMessage);
        inMessage = (Message) clientCom.readObject();

        if(inMessage.getMessageType() != Message.MessageType.BD_DTTQ_GO_TO_ARRIVAL_TERMINAL.getMessageCode()) {
            GenericIO.writelnString("Thread " + Thread.currentThread().getName() + ": DTTQStub: goToArrivalTerminal: Incorrect message type!");
            GenericIO.writelnString(inMessage.toString());
            System.exit(1);
        }
        clientCom.close();
    }

    /**
     * The passenger leaves the bus and signals the bus driver if he's the last one to do so.
     *
     * @param pid  The passenger's ID.
     * @param seat The passenger's bus seat.
     */
    @Override
    public void leaveTheBus(int pid, int seat) {
        ClientCom clientCom = new ClientCom (serverHostName, serverHostPort);
        Message inMessage;
        Message outMessage = null;

        while (!clientCom.open()) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                System.out.println("Thread " + Thread.currentThread().getName() + ": DTTQStub: leaveTheBus: " + e.toString());
            }
        }

        try {
            outMessage = new Message(Message.MessageType.PA_DTTQ_LEAVE_THE_BUS.getMessageCode(), pid, seat);
        } catch(MessageException e) {
            System.out.println("Thread " + Thread.currentThread().getName() + ": DTTQStub: leaveTheBus: " + e.toString());
        }

        clientCom.writeObject(outMessage);
        inMessage = (Message) clientCom.readObject();

        if(inMessage.getMessageType() != Message.MessageType.PA_DTTQ_LEAVE_THE_BUS.getMessageCode()) {
            GenericIO.writelnString("Thread " + Thread.currentThread().getName() + ": DTTQStub: leaveTheBus: Incorrect message type!");
            GenericIO.writelnString(inMessage.toString());
            System.exit(1);
        }
        clientCom.close();
    }
}
