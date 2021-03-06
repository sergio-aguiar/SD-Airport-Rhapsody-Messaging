package ClientSide.Stubs;

import ClientSide.ClientCom.ClientCom;
import ClientSide.Interfaces.ATTQBusDriver;
import ClientSide.Interfaces.ATTQPassenger;
import Communication.Message;
import Communication.MessageException;
import genclass.GenericIO;

/**
 * ArrivalTerminalTransferQuayStub: Client-side ArrivalTerminalTransferQuay remote procedure calling stub.
 * @author sergioaguiar
 * @author marcomacedo
 */
public class ArrivalTerminalTransferQuayStub implements ATTQPassenger, ATTQBusDriver  {
    /**
     *  Server's host name.
     */
    private String serverHostName;
    /**
     *  Server's listening port.
     */
    private int serverHostPort;

    /**
     * Constructor: ArrivalTerminalTransferQuayStub
     * @param hostName Server's host name.
     * @param hostPort Server's listening port.
     */
    public ArrivalTerminalTransferQuayStub(String hostName, int hostPort) {
        this.serverHostName = hostName;
        this.serverHostPort = hostPort;
    }
    /**
     * The bus driver checks if his services are needed in the future.
     *
     * @return true if the bus driver's services are not needed in the future and false otherwise.
     */
    @Override
    public boolean hasDaysWorkEnded() {
        ClientCom clientCom = new ClientCom (serverHostName, serverHostPort);
        Message inMessage;
        Message outMessage = null;

        while (!clientCom.open()) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                GenericIO.writelnString("Thread " + Thread.currentThread().getName()
                        + ": ATTQStub: hasDaysWorkEnded: " + e.toString());
            }
        }

        try {
            outMessage = new Message(Message.MessageType.BD_ATTQ_HAS_DAYS_WORK_ENDED.getMessageCode(), false);
        } catch(MessageException e) {
            GenericIO.writelnString("Thread " + Thread.currentThread().getName()
                    + ": ATTQStub: hasDaysWorkEnded: " + e.toString());
        }

        clientCom.writeObject(outMessage);
        inMessage = (Message) clientCom.readObject();

        if(inMessage.getMessageType() != Message.MessageType.BD_ATTQ_HAS_DAYS_WORK_ENDED.getMessageCode()) {
            GenericIO.writelnString("Thread " + Thread.currentThread().getName()
                    + ": ATTQStub: hasDaysWorkEnded: Incorrect message type!");
            GenericIO.writelnString(inMessage.toString());
            System.exit(1);
        }

        if(inMessage.isThereNoReturnInfo()) {
            GenericIO.writelnString("Thread " + Thread.currentThread().getName()
                    + ": ATTQStub: hasDaysWorkEnded: The return value is missing!");
            GenericIO.writelnString(inMessage.toString());
            System.exit(1);
        }
        clientCom.close();

        return (boolean) inMessage.getReturnInfo();
    }
    /**
     * The bus driver announces that the bus is boarding after seeing that at least one passenger is in queue.
     *
     * @return false if no other future passengers need the bus driver's services and true otherwise.
     */
    @Override
    public boolean announcingBusBoarding() {
        ClientCom clientCom = new ClientCom (serverHostName, serverHostPort);
        Message inMessage;
        Message outMessage = null;

        while (!clientCom.open()) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                GenericIO.writelnString("Thread " + Thread.currentThread().getName()
                        + ": ATTQStub: announcingBusBoarding: " + e.toString());
            }
        }

        try {
            outMessage = new Message(Message.MessageType.BD_ATTQ_ANNOUNCING_BUS_BOARDING.getMessageCode(), false);
        } catch(MessageException e) {
            GenericIO.writelnString("Thread " + Thread.currentThread().getName()
                    + ": ATTQStub: announcingBusBoarding: " + e.toString());
        }

        clientCom.writeObject(outMessage);
        inMessage = (Message) clientCom.readObject();

        if(inMessage.getMessageType() != Message.MessageType.BD_ATTQ_ANNOUNCING_BUS_BOARDING.getMessageCode()) {
            GenericIO.writelnString("Thread " + Thread.currentThread().getName()
                    + ": ATTQStub: announcingBusBoarding: Incorrect message type!");
            GenericIO.writelnString(inMessage.toString());
            System.exit(1);
        }

        if(inMessage.isThereNoReturnInfo()) {
            GenericIO.writelnString("Thread " + Thread.currentThread().getName()
                    + ": ATTQStub: announcingBusBoarding: The return value is missing!");
            GenericIO.writelnString(inMessage.toString());
            System.exit(1);
        }
        clientCom.close();

        return (boolean) inMessage.getReturnInfo();
    }
    /**
     * The bus driver drives towards the Departure Terminal Transfer Quay.
     *
     * @return the number of passenger being taken inside the bus.
     */
    @Override
    public int goToDepartureTerminal() {
        ClientCom clientCom = new ClientCom (serverHostName, serverHostPort);
        Message inMessage;
        Message outMessage = null;

        while (!clientCom.open()) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                GenericIO.writelnString("Thread " + Thread.currentThread().getName()
                        + ": ATTQStub: goToDepartureTerminal: " + e.toString());
            }
        }

        try {
            outMessage = new Message(Message.MessageType.BD_ATTQ_GO_TO_DEPARTURE_TERMINAL.getMessageCode(), false);
        } catch(MessageException e) {
            GenericIO.writelnString("Thread " + Thread.currentThread().getName()
                    + ": ATTQStub: goToDepartureTerminal: " + e.toString());
        }

        clientCom.writeObject(outMessage);
        inMessage = (Message) clientCom.readObject();

        if(inMessage.getMessageType() != Message.MessageType.BD_ATTQ_GO_TO_DEPARTURE_TERMINAL.getMessageCode()) {
            GenericIO.writelnString("Thread " + Thread.currentThread().getName()
                    + ": ATTQStub: goToDepartureTerminal: Incorrect message type!");
            GenericIO.writelnString(inMessage.toString());
            System.exit(1);
        }

        if(inMessage.isThereNoReturnInfo()) {
            GenericIO.writelnString("Thread " + Thread.currentThread().getName()
                    + ": ATTQStub: goToDepartureTerminal: The return value is missing!");
            GenericIO.writelnString(inMessage.toString());
            System.exit(1);
        }
        clientCom.close();

        return (int) inMessage.getReturnInfo();
    }
    /**
     * The bus driver parks the bus and gets ready for possibly a new trip.
     *
     */
    @Override
    public void parkTheBus() {
        ClientCom clientCom = new ClientCom (serverHostName, serverHostPort);
        Message inMessage;
        Message outMessage = null;

        while (!clientCom.open()) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                GenericIO.writelnString("Thread " + Thread.currentThread().getName()
                        + ": ATTQStub: parkTheBus: " + e.toString());
            }
        }

        try {
            outMessage = new Message(Message.MessageType.BD_ATTQ_PARK_THE_BUS.getMessageCode(), false);
        } catch(MessageException e) {
            GenericIO.writelnString("Thread " + Thread.currentThread().getName()
                    + ": ATTQStub: parkTheBus: " + e.toString());
        }

        clientCom.writeObject(outMessage);
        inMessage = (Message) clientCom.readObject();

        if(inMessage.getMessageType() != Message.MessageType.BD_ATTQ_PARK_THE_BUS.getMessageCode()) {
            GenericIO.writelnString("Thread " + Thread.currentThread().getName()
                    + ": ATTQStub: parkTheBus: Incorrect message type!");
            GenericIO.writelnString(inMessage.toString());
            System.exit(1);
        }
        clientCom.close();
    }
    /**
     * The passenger decides to take the bus and enters the waiting queue.
     *
     * @param pid the passenger's ID.
     */
    @Override
    public void takeABus(int pid) {
        ClientCom clientCom = new ClientCom (serverHostName, serverHostPort);
        Message inMessage;
        Message outMessage = null;

        while (!clientCom.open()) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                GenericIO.writelnString("Thread " + Thread.currentThread().getName()
                        + ": ATTQStub: takeABus: " + e.toString());
            }
        }

        try {
            outMessage = new Message(Message.MessageType.PA_ATTQ_TAKE_A_BUS.getMessageCode(), pid);
        } catch(MessageException e) {
            GenericIO.writelnString("Thread " + Thread.currentThread().getName() + ": ATTQStub: takeABus: " + e.toString());
        }

        clientCom.writeObject(outMessage);
        inMessage = (Message) clientCom.readObject();

        if(inMessage.getMessageType() != Message.MessageType.PA_ATTQ_TAKE_A_BUS.getMessageCode()) {
            GenericIO.writelnString("Thread " + Thread.currentThread().getName()
                    + ": ATTQStub: takeABus: Incorrect message type!");
            GenericIO.writelnString(inMessage.toString());
            System.exit(1);
        }
        clientCom.close();
    }
    /**
     * The Passenger leaves the waiting queue and enters the bus.
     *
     * @param pid The passenger's ID.
     * @return the passenger's seat on the bus.
     */
    @Override
    public int enterTheBus(int pid) {
        ClientCom clientCom = new ClientCom (serverHostName, serverHostPort);
        Message inMessage;
        Message outMessage = null;

        while (!clientCom.open()) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                GenericIO.writelnString("Thread " + Thread.currentThread().getName()
                        + ": ATTQStub: enterTheBus: " + e.toString());
            }
        }

        try {
            outMessage = new Message(Message.MessageType.PA_ATTQ_ENTER_THE_BUS.getMessageCode(), pid);
        } catch(MessageException e) {
            GenericIO.writelnString("Thread " + Thread.currentThread().getName()
                    + ": ATTQStub: enterTheBus: " + e.toString());
        }

        clientCom.writeObject(outMessage);
        inMessage = (Message) clientCom.readObject();

        if(inMessage.getMessageType() != Message.MessageType.PA_ATTQ_ENTER_THE_BUS.getMessageCode()) {
            GenericIO.writelnString("Thread " + Thread.currentThread().getName()
                    + ": ATTQStub: enterTheBus: Incorrect message type!");
            GenericIO.writelnString(inMessage.toString());
            System.exit(1);
        }

        if(inMessage.isThereNoReturnInfo()) {
            GenericIO.writelnString("Thread " + Thread.currentThread().getName()
                    + ": ATTQStub: enterTheBus: The return value is missing!");
            GenericIO.writelnString(inMessage.toString());
            System.exit(1);
        }
        clientCom.close();

        return (int) inMessage.getReturnInfo();
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
                        + ": ATTQStub: prepareForNextFlight: " + e.toString());
            }
        }

        try {
            outMessage = new Message(Message.MessageType.ATTQ_PREPARE_FOR_NEXT_FLIGHT.getMessageCode(), false, true);
        } catch(MessageException e) {
            GenericIO.writelnString("Thread " + Thread.currentThread().getName()
                    + ": ATTQStub: prepareForNextFlight: " + e.toString());
        }

        clientCom.writeObject(outMessage);
        inMessage = (Message) clientCom.readObject();

        if(inMessage.getMessageType() != Message.MessageType.ATTQ_PREPARE_FOR_NEXT_FLIGHT.getMessageCode()) {
            GenericIO.writelnString("Thread " + Thread.currentThread().getName()
                    + ": ATTQStub: prepareForNextFlight: Incorrect message type!");
            GenericIO.writelnString(inMessage.toString());
            System.exit(1);
        }
        clientCom.close();
    }
    /**
     * Function that sets the initial state of a server's data.
     * @param totalPassengers Total number of passengers per flight.
     * @param busSeatNumber Number of seats in the bus.
     */
    public void setInitialState(int totalPassengers, int busSeatNumber) {
        ClientCom clientCom = new ClientCom (serverHostName, serverHostPort);
        Message inMessage;
        Message outMessage = null;

        while (!clientCom.open()) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                GenericIO.writelnString("Thread " + Thread.currentThread().getName()
                        + ": ATTQStub: setInitialState: " + e.toString());
            }
        }

        try {
            outMessage = new Message(Message.MessageType.ATTQ_SET_INITIAL_STATE.getMessageCode(),
                    (Object) totalPassengers, busSeatNumber, null);
        } catch(MessageException e) {
            GenericIO.writelnString("Thread " + Thread.currentThread().getName()
                    + ": ATTQStub: setInitialState: " + e.toString());
        }

        clientCom.writeObject(outMessage);
        inMessage = (Message) clientCom.readObject();

        if(inMessage.getMessageType() != Message.MessageType.ATTQ_SET_INITIAL_STATE.getMessageCode()) {
            GenericIO.writelnString("Thread " + Thread.currentThread().getName()
                    + ": ATTQStub: setInitialState: Incorrect message type!");
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
                        + ": ATTQStub: everythingFinished: " + e.toString());
            }
        }

        try {
            outMessage = new Message(Message.MessageType.EVERYTHING_FINISHED.getMessageCode(), false, true);
        } catch(MessageException e) {
            GenericIO.writelnString("Thread " + Thread.currentThread().getName()
                    + ": ATTQStub: everythingFinished: " + e.toString());
        }

        clientCom.writeObject(outMessage);
        inMessage = (Message) clientCom.readObject();

        if(inMessage.getMessageType() != Message.MessageType.EVERYTHING_FINISHED.getMessageCode()) {
            GenericIO.writelnString("Thread " + Thread.currentThread().getName()
                    + ": ATTQStub: everythingFinished: Incorrect message type!");
            GenericIO.writelnString(inMessage.toString());
            System.exit(1);
        }
        clientCom.close();
    }
}
