package ClientSide.Stubs;

import ClientSide.ClientCom.ClientCom;
import ClientSide.Extras.Bag;
import ClientSide.Passenger.PassengerThread;
import Communication.Message;
import Communication.MessageException;
import genclass.GenericIO;

/**
 * RepositoryStub: Client-side Repository remote procedure calling stub.
 * @author sergioaguiar
 * @author marcomacedo
 */
public class RepositoryStub {
    /**
     *  Server's host name.
     */
    private String serverHostName;
    /**
     *  Server's listening port.
     */
    private int serverHostPort;
    /**
     * Constructor: RepositoryStub.
     * @param hostName Server's host name.
     * @param hostPort Server's listening port.
     */
    public RepositoryStub(String hostName, int hostPort) {
        this.serverHostName = hostName;
        this.serverHostPort = hostPort;
    }
    /**
     * Function that allows for a transition to a new flight (new plane landing simulation).
     * @param numberOfPassengerLuggageAtThePlane The current amount of luggage on the plane.
     * @param passengerSituations Array with each passenger's current flight situation.
     */
    public void prepareForNextFlight(int numberOfPassengerLuggageAtThePlane, PassengerThread.PassengerAndBagSituations[]
            passengerSituations) {

        ClientCom clientCom = new ClientCom (serverHostName, serverHostPort);
        Message inMessage;
        Message outMessage = null;

        while (!clientCom.open()) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                GenericIO.writelnString("Thread " + Thread.currentThread().getName()
                        + ": RepositoryStub: prepareForNextFlight: " + e.toString());
            }
        }

        try {
            outMessage = new Message(Message.MessageType.REP_PREPARE_FOR_NEXT_FLIGHT.getMessageCode(), false, true,
                    numberOfPassengerLuggageAtThePlane, passengerSituations);
        } catch(MessageException e) {
            GenericIO.writelnString("Thread " + Thread.currentThread().getName()
                    + ": RepositoryStub: prepareForNextFlight: " + e.toString());
        }

        clientCom.writeObject(outMessage);
        inMessage = (Message) clientCom.readObject();

        if(inMessage.getMessageType() != Message.MessageType.REP_PREPARE_FOR_NEXT_FLIGHT.getMessageCode()) {
            GenericIO.writelnString("Thread " + Thread.currentThread().getName()
                    + ": RepositoryStub: prepareForNextFlight: Incorrect message type!");
            GenericIO.writelnString(inMessage.toString());
            System.exit(1);
        }
        clientCom.close();
    }

    public void finalReport() {
        ClientCom clientCom = new ClientCom (serverHostName, serverHostPort);
        Message inMessage;
        Message outMessage = null;

        while (!clientCom.open()) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                GenericIO.writelnString("Thread " + Thread.currentThread().getName()
                        + ": RepositoryStub: finalReport: " + e.toString());
            }
        }

        try {
            outMessage = new Message(Message.MessageType.REP_FINAL_REPORT.getMessageCode(), false, true);
        } catch(MessageException e) {
            GenericIO.writelnString("Thread " + Thread.currentThread().getName()
                    + ": RepositoryStub: finalReport: " + e.toString());
        }

        clientCom.writeObject(outMessage);
        inMessage = (Message) clientCom.readObject();

        if(inMessage.getMessageType() != Message.MessageType.REP_FINAL_REPORT.getMessageCode()) {
            GenericIO.writelnString("Thread " + Thread.currentThread().getName()
                    + ": RepositoryStub: finalReport: Incorrect message type!");
            GenericIO.writelnString(inMessage.toString());
            System.exit(1);
        }
        clientCom.close();
    }
    /**
     * Function that sets the initial state of a server's data.
     * @param flightNumber The current flight number.
     * @param numberOfPassengerLuggageAtThePlane The current amount of luggage on the plane.
     * @param busSeatNumber The number of bus seats.
     * @param totalPassengers Total number of passengers per flight.
     * @param passengerSituations Array with each passenger's situation.
     * @param passengerLuggageAtStart Array with the total amount of luggage per flight.
     * @param luggagePerFlight Array that contains the bags of each passenger per flight.
     */
    public void setInitialState(int flightNumber, int numberOfPassengerLuggageAtThePlane, int busSeatNumber,
                                int totalPassengers, PassengerThread.PassengerAndBagSituations[] passengerSituations,
                                int[] passengerLuggageAtStart, Bag[][][] luggagePerFlight) {

        ClientCom clientCom = new ClientCom (serverHostName, serverHostPort);
        Message inMessage;
        Message outMessage = null;

        while (!clientCom.open()) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                GenericIO.writelnString("Thread " + Thread.currentThread().getName()
                        + ": REPStub: setInitialState: " + e.toString());
            }
        }

        try {
            outMessage = new Message(Message.MessageType.REP_SET_INITIAL_STATE.getMessageCode(), flightNumber,
                    numberOfPassengerLuggageAtThePlane, busSeatNumber, totalPassengers, passengerSituations,
                    passengerLuggageAtStart, luggagePerFlight);
        } catch(MessageException e) {
            GenericIO.writelnString("Thread " + Thread.currentThread().getName()
                    + ": REPStub: setInitialState: " + e.toString());
        }

        clientCom.writeObject(outMessage);
        inMessage = (Message) clientCom.readObject();

        if(inMessage.getMessageType() != Message.MessageType.REP_SET_INITIAL_STATE.getMessageCode()) {
            GenericIO.writelnString("Thread " + Thread.currentThread().getName()
                    + ": REPStub: setInitialState: Incorrect message type!");
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
                        + ": REPStub: everythingFinished: " + e.toString());
            }
        }

        try {
            outMessage = new Message(Message.MessageType.EVERYTHING_FINISHED.getMessageCode(), false, true);
        } catch(MessageException e) {
            GenericIO.writelnString("Thread " + Thread.currentThread().getName()
                    + ": REPStub: everythingFinished: " + e.toString());
        }

        clientCom.writeObject(outMessage);
        inMessage = (Message) clientCom.readObject();

        if(inMessage.getMessageType() != Message.MessageType.EVERYTHING_FINISHED.getMessageCode()) {
            GenericIO.writelnString("Thread " + Thread.currentThread().getName()
                    + ": REPStub: everythingFinished: Incorrect message type!");
            GenericIO.writelnString(inMessage.toString());
            System.exit(1);
        }
        clientCom.close();
    }
}
