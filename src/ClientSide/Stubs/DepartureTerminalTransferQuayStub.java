package ClientSide.Stubs;

import ClientSide.ClientCom.ClientCom;
import ClientSide.Interfaces.DTTQBusDriver;
import ClientSide.Interfaces.DTTQPassenger;
import Communication.Message;
import Communication.MessageException;
import genclass.GenericIO;

/**
 * DepartureTerminalTransferQuayStub: Client-side DepartureTerminalTransferQuay remote procedure calling stub.
 * @author sergioaguiar
 * @author marcomacedo
 */

public class DepartureTerminalTransferQuayStub implements DTTQPassenger, DTTQBusDriver {
    /**
     *  Server's host name.
     */
    private String serverHostName;
    /**
     *  Server's listening port.
     */
    private int serverHostPort;
    /**
     * Constructor: DepartureTerminalTransferQuayStub.
     * @param hostName Server's host name.
     * @param hostPort Server's listening port.
     */
    public DepartureTerminalTransferQuayStub(String hostName, int hostPort) {
        this.serverHostName = hostName;
        this.serverHostPort = hostPort;
    }
    /**
     * The bus driver parks the Bus and let's teh passengers off.
     *
     * @param passengersThatArrived The number of passengers that arrived aboard the bus.
     * @param flightNumber The bus driver's estimated flight number.
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
                GenericIO.writelnString("Thread " + Thread.currentThread().getName()
                        + ": DTTQStub: parkTheBusAndLetPassOff: " + e.toString());
            }
        }

        try {
            outMessage = new Message(Message.MessageType.BD_DTTQ_PARK_THE_BUS_AND_LET_PASS_OFF.getMessageCode(), false,
                    (Object) passengersThatArrived, (Object) flightNumber);
        } catch(MessageException e) {
            GenericIO.writelnString("Thread " + Thread.currentThread().getName()
                    + ": DTTQStub: parkTheBusAndLetPassOff: " + e.toString());
        }

        clientCom.writeObject(outMessage);
        inMessage = (Message) clientCom.readObject();

        if(inMessage.getMessageType() != Message.MessageType.BD_DTTQ_PARK_THE_BUS_AND_LET_PASS_OFF.getMessageCode()) {
            GenericIO.writelnString("Thread " + Thread.currentThread().getName()
                    + ": DTTQStub: parkTheBusAndLetPassOff: Incorrect message type!");
            GenericIO.writelnString(inMessage.toString());
            System.exit(1);
        }

        if(inMessage.isThereNoReturnInfo()) {
            GenericIO.writelnString("Thread " + Thread.currentThread().getName()
                    + ": DTTQStub: parkTheBusAndLetPassOff: The return value is missing!");
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
                GenericIO.writelnString("Thread " + Thread.currentThread().getName()
                        + ": DTTQStub: goToArrivalTerminal: " + e.toString());
            }
        }

        try {
            outMessage = new Message(Message.MessageType.BD_DTTQ_GO_TO_ARRIVAL_TERMINAL.getMessageCode(), false);
        } catch(MessageException e) {
            GenericIO.writelnString("Thread " + Thread.currentThread().getName()
                    + ": DTTQStub: goToArrivalTerminal: " + e.toString());
        }

        clientCom.writeObject(outMessage);
        inMessage = (Message) clientCom.readObject();

        if(inMessage.getMessageType() != Message.MessageType.BD_DTTQ_GO_TO_ARRIVAL_TERMINAL.getMessageCode()) {
            GenericIO.writelnString("Thread " + Thread.currentThread().getName()
                    + ": DTTQStub: goToArrivalTerminal: Incorrect message type!");
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
                GenericIO.writelnString("Thread " + Thread.currentThread().getName()
                        + ": DTTQStub: leaveTheBus: " + e.toString());
            }
        }

        try {
            outMessage = new Message(Message.MessageType.PA_DTTQ_LEAVE_THE_BUS.getMessageCode(), pid, seat);
        } catch(MessageException e) {
            GenericIO.writelnString("Thread " + Thread.currentThread().getName()
                    + ": DTTQStub: leaveTheBus: " + e.toString());
        }

        clientCom.writeObject(outMessage);
        inMessage = (Message) clientCom.readObject();

        if(inMessage.getMessageType() != Message.MessageType.PA_DTTQ_LEAVE_THE_BUS.getMessageCode()) {
            GenericIO.writelnString("Thread " + Thread.currentThread().getName()
                    + ": DTTQStub: leaveTheBus: Incorrect message type!");
            GenericIO.writelnString(inMessage.toString());
            System.exit(1);
        }
        clientCom.close();
    }
    /**
     * Function that allows for a transition to a new flight (new plane landing simulation).
     */
    public void prepareForNextFlight() {
        ClientCom clientCom = new ClientCom (serverHostName, serverHostPort);
        Message inMessage;
        Message outMessage = null;

        while (!clientCom.open()) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                GenericIO.writelnString("Thread " + Thread.currentThread().getName()
                        + ": DTTQStub: prepareForNextFlight: " + e.toString());
            }
        }

        try {
            outMessage = new Message(Message.MessageType.DTTQ_PREPARE_FOR_NEXT_FLIGHT.getMessageCode(), false, true);
        } catch(MessageException e) {
            GenericIO.writelnString("Thread " + Thread.currentThread().getName()
                    + ": DTTQStub: prepareForNextFlight: " + e.toString());
        }

        clientCom.writeObject(outMessage);
        inMessage = (Message) clientCom.readObject();

        if(inMessage.getMessageType() != Message.MessageType.DTTQ_PREPARE_FOR_NEXT_FLIGHT.getMessageCode()) {
            GenericIO.writelnString("Thread " + Thread.currentThread().getName()
                    + ": DTTQStub: prepareForNextFlight: Incorrect message type!");
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
                        + ": DTTQStub: everythingFinished: " + e.toString());
            }
        }

        try {
            outMessage = new Message(Message.MessageType.EVERYTHING_FINISHED.getMessageCode(), false, true);
        } catch(MessageException e) {
            GenericIO.writelnString("Thread " + Thread.currentThread().getName()
                    + ": DTTQStub: everythingFinished: " + e.toString());
        }

        clientCom.writeObject(outMessage);
        inMessage = (Message) clientCom.readObject();

        if(inMessage.getMessageType() != Message.MessageType.EVERYTHING_FINISHED.getMessageCode()) {
            GenericIO.writelnString("Thread " + Thread.currentThread().getName()
                    + ": DTTQStub: everythingFinished: Incorrect message type!");
            GenericIO.writelnString(inMessage.toString());
            System.exit(1);
        }
        clientCom.close();
    }
}
