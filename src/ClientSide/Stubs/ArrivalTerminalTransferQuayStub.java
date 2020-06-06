package ClientSide.Stubs;

import ClientSide.ClientCom.ClientCom;
import ClientSide.Interfaces.ATTQBusDriver;
import ClientSide.Interfaces.ATTQPassenger;
import Communication.Message;
import Communication.MessageException;
import genclass.GenericIO;

public class ArrivalTerminalTransferQuayStub implements ATTQPassenger, ATTQBusDriver  {

    private String serverHostName;
    private int serverHostPort;

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
                System.out.println("Thread " + Thread.currentThread().getName() + ": ATTQStub: hasDaysWorkEnded: " + e.toString());
            }
        }

        try {
            outMessage = new Message(Message.MessageType.BD_ATTQ_HAS_DAYS_WORK_ENDED.getMessageCode(), false);
        } catch(MessageException e) {
            System.out.println("Thread " + Thread.currentThread().getName() + ": ATTQStub: hasDaysWorkEnded: " + e.toString());
        }

        clientCom.writeObject(outMessage);
        inMessage = (Message) clientCom.readObject();

        if(inMessage.getMessageType() != Message.MessageType.BD_ATTQ_HAS_DAYS_WORK_ENDED.getMessageCode()) {
            GenericIO.writelnString("Thread " + Thread.currentThread().getName() + ": ATTQStub: hasDaysWorkEnded: Incorrect message type!");
            GenericIO.writelnString(inMessage.toString());
            System.exit(1);
        }

        if(inMessage.isThereNoReturnInfo()) {
            GenericIO.writelnString("Thread " + Thread.currentThread().getName() + ": ATTQStub: hasDaysWorkEnded: The return value is missing!");
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
                System.out.println("Thread " + Thread.currentThread().getName() + ": ATTQStub: announcingBusBoarding: " + e.toString());
            }
        }

        try {
            outMessage = new Message(Message.MessageType.BD_ATTQ_ANNOUNCING_BUS_BOARDING.getMessageCode(), false);
        } catch(MessageException e) {
            System.out.println("Thread " + Thread.currentThread().getName() + ": ATTQStub: announcingBusBoarding: " + e.toString());
        }

        clientCom.writeObject(outMessage);
        inMessage = (Message) clientCom.readObject();

        if(inMessage.getMessageType() != Message.MessageType.BD_ATTQ_ANNOUNCING_BUS_BOARDING.getMessageCode()) {
            GenericIO.writelnString("Thread " + Thread.currentThread().getName() + ": ATTQStub: announcingBusBoarding: Incorrect message type!");
            GenericIO.writelnString(inMessage.toString());
            System.exit(1);
        }

        if(inMessage.isThereNoReturnInfo()) {
            GenericIO.writelnString("Thread " + Thread.currentThread().getName() + ": ATTQStub: announcingBusBoarding: The return value is missing!");
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
                System.out.println("Thread " + Thread.currentThread().getName() + ": ATTQStub: goToDepartureTerminal: " + e.toString());
            }
        }

        try {
            outMessage = new Message(Message.MessageType.BD_ATTQ_GO_TO_DEPARTURE_TERMINAL.getMessageCode(), false);
        } catch(MessageException e) {
            System.out.println("Thread " + Thread.currentThread().getName() + ": ATTQStub: goToDepartureTerminal: " + e.toString());
        }

        clientCom.writeObject(outMessage);
        inMessage = (Message) clientCom.readObject();

        if(inMessage.getMessageType() != Message.MessageType.BD_ATTQ_GO_TO_DEPARTURE_TERMINAL.getMessageCode()) {
            GenericIO.writelnString("Thread " + Thread.currentThread().getName() + ": ATTQStub: goToDepartureTerminal: Incorrect message type!");
            GenericIO.writelnString(inMessage.toString());
            System.exit(1);
        }

        if(inMessage.isThereNoReturnInfo()) {
            GenericIO.writelnString("Thread " + Thread.currentThread().getName() + ": ATTQStub: goToDepartureTerminal: The return value is missing!");
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
                System.out.println("Thread " + Thread.currentThread().getName() + ": ATTQStub: parkTheBus: " + e.toString());
            }
        }

        try {
            outMessage = new Message(Message.MessageType.BD_ATTQ_PARK_THE_BUS.getMessageCode(), false);
        } catch(MessageException e) {
            System.out.println("Thread " + Thread.currentThread().getName() + ": ATTQStub: parkTheBus: " + e.toString());
        }

        clientCom.writeObject(outMessage);
        inMessage = (Message) clientCom.readObject();

        if(inMessage.getMessageType() != Message.MessageType.BD_ATTQ_PARK_THE_BUS.getMessageCode()) {
            GenericIO.writelnString("Thread " + Thread.currentThread().getName() + ": ATTQStub: parkTheBus: Incorrect message type!");
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
                System.out.println("Thread " + Thread.currentThread().getName() + ": ATTQStub: takeABus: " + e.toString());
            }
        }

        try {
            outMessage = new Message(Message.MessageType.PA_ATTQ_TAKE_A_BUS.getMessageCode(), pid);
        } catch(MessageException e) {
            System.out.println("Thread " + Thread.currentThread().getName() + ": ATTQStub: takeABus: " + e.toString());
        }

        clientCom.writeObject(outMessage);
        inMessage = (Message) clientCom.readObject();

        if(inMessage.getMessageType() != Message.MessageType.PA_ATTQ_TAKE_A_BUS.getMessageCode()) {
            GenericIO.writelnString("Thread " + Thread.currentThread().getName() + ": ATTQStub: takeABus: Incorrect message type!");
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
                System.out.println("Thread " + Thread.currentThread().getName() + ": ATTQStub: enterTheBus: " + e.toString());
            }
        }

        try {
            outMessage = new Message(Message.MessageType.PA_ATTQ_ENTER_THE_BUS.getMessageCode(), pid);
        } catch(MessageException e) {
            System.out.println("Thread " + Thread.currentThread().getName() + ": ATTQStub: enterTheBus: " + e.toString());
        }

        clientCom.writeObject(outMessage);
        inMessage = (Message) clientCom.readObject();

        if(inMessage.getMessageType() != Message.MessageType.PA_ATTQ_ENTER_THE_BUS.getMessageCode()) {
            GenericIO.writelnString("Thread " + Thread.currentThread().getName() + ": ATTQStub: enterTheBus: Incorrect message type!");
            GenericIO.writelnString(inMessage.toString());
            System.exit(1);
        }

        if(inMessage.isThereNoReturnInfo()) {
            GenericIO.writelnString("Thread " + Thread.currentThread().getName() + ": ATTQStub: enterTheBus: The return value is missing!");
            GenericIO.writelnString(inMessage.toString());
            System.exit(1);
        }
        clientCom.close();

        return (int) inMessage.getReturnInfo();
    }
}
