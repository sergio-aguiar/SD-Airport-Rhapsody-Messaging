package HybridServerSide.Stubs;

import ClientSide.ClientCom.ClientCom;
import Communication.Message;
import Communication.MessageException;
import genclass.GenericIO;

/**
 * ArrivalLoungeStub: Hybrid-Server-side ArrivalLounge remote procedure calling stub.
 * @author sergioaguiar
 * @author marcomacedo
 */
public class ArrivalLoungeStub {
    /**
     *  Server's host name.
     */
    private String serverHostName;
    /**
     *  Server's listening port.
     */
    private int serverHostPort;
    /**
     * Constructor: ArrivalLoungeStub
     * @param hostName Server's host name.
     * @param hostPort Server's listening port.
     */
    public ArrivalLoungeStub(String hostName, int hostPort) {
        this.serverHostName = hostName;
        this.serverHostPort = hostPort;
    }
    /**
     * Function that verifies if any more passengers in the future need the bus driver's services.
     * @return true if no future passengers need the bus driver's services and false otherwise.
     */
    public boolean passengersNoLongerNeedTheBus() {
        ClientCom clientCom = new ClientCom (serverHostName, serverHostPort);
        Message inMessage;
        Message outMessage = null;

        while (!clientCom.open()) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                GenericIO.writelnString("Thread " + Thread.currentThread().getName()
                        + ": ALStub: passengersNoLongerNeedTheBus: " + e.toString());
            }
        }

        try {
            outMessage = new Message(Message.MessageType.AL_PASSENGERS_NO_LONGER_NEED_THE_BUS.getMessageCode(), false,
                    true);
        } catch(MessageException e) {
            GenericIO.writelnString("Thread " + Thread.currentThread().getName()
                    + ": ALStub: passengersNoLongerNeedTheBus: " + e.toString());
        }

        clientCom.writeObject(outMessage);
        inMessage = (Message) clientCom.readObject();

        if(inMessage.getMessageType() != Message.MessageType.AL_PASSENGERS_NO_LONGER_NEED_THE_BUS.getMessageCode()) {
            GenericIO.writelnString("Thread " + Thread.currentThread().getName()
                    + ": ALStub: passengersNoLongerNeedTheBus: Incorrect message type!");
            GenericIO.writelnString(inMessage.toString());
            System.exit(1);
        }

        if(inMessage.isThereNoReturnInfo()) {
            GenericIO.writelnString("Thread " + Thread.currentThread().getName()
                    + ": ALStub: passengersNoLongerNeedTheBus: The return value is missing!");
            GenericIO.writelnString(inMessage.toString());
            System.exit(1);
        }
        clientCom.close();

        return (boolean) inMessage.getReturnInfo();
    }
    /**
     * Function that increments the count for the number of passengers throughout every planned flight who have made it
     * past where the bus driver is needed.
     */
    public void incrementCrossFlightPassengerCount() {
        ClientCom clientCom = new ClientCom (serverHostName, serverHostPort);
        Message inMessage;
        Message outMessage = null;

        while (!clientCom.open()) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                GenericIO.writelnString("Thread " + Thread.currentThread().getName()
                        + ": ALStub: incrementCrossFlightPassengerCount: " + e.toString());
            }
        }

        try {
            outMessage = new Message(Message.MessageType.AL_INCREMENT_CROSS_FLIGHT_PASSENGER_COUNT.getMessageCode(),
                    false, true);
        } catch(MessageException e) {
            GenericIO.writelnString("Thread " + Thread.currentThread().getName()
                    + ": ALStub: incrementCrossFlightPassengerCount: " + e.toString());
        }

        clientCom.writeObject(outMessage);
        inMessage = (Message) clientCom.readObject();

        if(inMessage.getMessageType() !=
                Message.MessageType.AL_INCREMENT_CROSS_FLIGHT_PASSENGER_COUNT.getMessageCode()) {
            GenericIO.writelnString("Thread " + Thread.currentThread().getName()
                    + ": ALStub: incrementCrossFlightPassengerCount: Incorrect message type!");
            GenericIO.writelnString(inMessage.toString());
            System.exit(1);
        }
        clientCom.close();
    }
}
