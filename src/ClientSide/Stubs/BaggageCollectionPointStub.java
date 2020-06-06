package ClientSide.Stubs;

import ClientSide.Interfaces.BCPPassenger;
import ClientSide.Interfaces.BCPPorter;

public class BaggageCollectionPointStub implements BCPPassenger, BCPPorter {

    private String serverHostName;
    private int serverHostPort;

    public BaggageCollectionPointStub(String hostName, int hostPort) {
        this.serverHostName = hostName;
        this.serverHostPort = hostPort;
    }

    /**
     * The Passenger tries to collect a bag from the conveyor.
     *
     * @param pid The passenger's ID.
     * @return true if passenger succeeded in collecting a bag and false otherwise.
     */
    @Override
    public boolean goCollectABag(int pid) {
        return false;
    }

    /**
     * The Porter carries their held bag to the Baggage Collection Point.
     *
     * @param pid   The porter's ID.
     * @param bagID The porter's held bag's owner's ID.
     */
    @Override
    public void carryItToAppropriateStore(int pid, int bagID) {

    }

    /**
     * The porter announces that there are no more bags in the plane by signalling every waiting passenger.
     *
     * @param pid The passenger's ID.
     */
    @Override
    public void noMoreBagsToCollect(int pid) {

    }
}
