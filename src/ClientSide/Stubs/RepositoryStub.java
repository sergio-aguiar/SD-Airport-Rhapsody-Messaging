package ClientSide.Stubs;

import ClientSide.ClientCom.ClientCom;
import ClientSide.Extras.Bag;
import ClientSide.Passenger.PassengerThread;
import Communication.Message;
import Communication.MessageException;
import genclass.GenericIO;

public class RepositoryStub {

    private String serverHostName;
    private int serverHostPort;

    public RepositoryStub(String hostName, int hostPort) {
        this.serverHostName = hostName;
        this.serverHostPort = hostPort;
    }

    public void prepareForNextFlight(int numberOfPassengerLuggageAtThePlane, PassengerThread.PassengerAndBagSituations[]
            passengerSituations) {

        ClientCom clientCom = new ClientCom (serverHostName, serverHostPort);
        Message inMessage;
        Message outMessage = null;

        while (!clientCom.open()) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                System.out.println("Thread " + Thread.currentThread().getName() + ": RepositoryStub: prepareForNextFlight: " + e.toString());
            }
        }

        try {
            outMessage = new Message(Message.MessageType.REP_PREPARE_FOR_NEXT_FLIGHT.getMessageCode(), false, true, numberOfPassengerLuggageAtThePlane, passengerSituations);
        } catch(MessageException e) {
            System.out.println("Thread " + Thread.currentThread().getName() + ": RepositoryStub: prepareForNextFlight: " + e.toString());
        }

        clientCom.writeObject(outMessage);
        inMessage = (Message) clientCom.readObject();

        if(inMessage.getMessageType() != Message.MessageType.REP_PREPARE_FOR_NEXT_FLIGHT.getMessageCode()) {
            GenericIO.writelnString("Thread " + Thread.currentThread().getName() + ": RepositoryStub: prepareForNextFlight: Incorrect message type!");
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
                System.out.println("Thread " + Thread.currentThread().getName() + ": RepositoryStub: finalReport: " + e.toString());
            }
        }

        try {
            outMessage = new Message(Message.MessageType.REP_FINAL_REPORT.getMessageCode(), false, true);
        } catch(MessageException e) {
            System.out.println("Thread " + Thread.currentThread().getName() + ": RepositoryStub: finalReport: " + e.toString());
        }

        clientCom.writeObject(outMessage);
        inMessage = (Message) clientCom.readObject();

        if(inMessage.getMessageType() != Message.MessageType.REP_FINAL_REPORT.getMessageCode()) {
            GenericIO.writelnString("Thread " + Thread.currentThread().getName() + ": RepositoryStub: finalReport: Incorrect message type!");
            GenericIO.writelnString(inMessage.toString());
            System.exit(1);
        }
        clientCom.close();
    }


    public void setInitialState(int flightNumber, int numberOfPassengerLuggageAtThePlane, int busSeatNumber, int totalPassengers,
                                PassengerThread.PassengerAndBagSituations[] passengerSituations, int[] passengerLuggageAtStart,
                                Bag[][][] luggagePerFlight) {

        ClientCom clientCom = new ClientCom (serverHostName, serverHostPort);
        Message inMessage;
        Message outMessage = null;

        while (!clientCom.open()) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                System.out.println("Thread " + Thread.currentThread().getName() + ": REPStub: setInitialState: " + e.toString());
            }
        }

        try {
            outMessage = new Message(Message.MessageType.REP_SET_INITIAL_STATE.getMessageCode(), flightNumber, numberOfPassengerLuggageAtThePlane, busSeatNumber, totalPassengers, passengerSituations, passengerLuggageAtStart, luggagePerFlight);
        } catch(MessageException e) {
            System.out.println("Thread " + Thread.currentThread().getName() + ": REPStub: setInitialState: " + e.toString());
        }

        clientCom.writeObject(outMessage);
        inMessage = (Message) clientCom.readObject();

        if(inMessage.getMessageType() != Message.MessageType.REP_SET_INITIAL_STATE.getMessageCode()) {
            GenericIO.writelnString("Thread " + Thread.currentThread().getName() + ": REPStub: setInitialState: Incorrect message type!");
            GenericIO.writelnString(inMessage.toString());
            System.exit(1);
        }
        clientCom.close();
    }
}
