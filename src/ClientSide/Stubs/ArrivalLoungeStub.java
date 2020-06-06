package ClientSide.Stubs;

import ClientSide.Interfaces.ALPassenger;
import ClientSide.Interfaces.ALPorter;

public class ArrivalLoungeStub implements ALPassenger, ALPorter {

    private String serverHostName;
    private int serverHostPort;

    public ArrivalLoungeStub(String hostName, int hostPort) {
        this.serverHostName = hostName;
        this.serverHostPort = hostPort;
    }

    /**
     * Passenger's first action when arriving from the plane. Signal's the porter if the final passenger to arrive.
     *
     * @param pid       The passenger's ID.
     * @param situation A string representation of the passenger's travel situation.
     */
    @Override
    public void whatShouldIDo(int pid, String situation) {

    }

    /**
     * The Passenger decides to move towards the Baggage Collection Point to collect a bag.
     *
     * @param pid The passenger's ID.
     */
    @Override
    public void goCollectABag(int pid) {

    }

    /**
     * The porter checks whether he is still needed in the future. If so, he awaits the next flight. He stops otherwise.
     *
     * @param pid The porter's ID.
     * @return true if the porter isn't needed anymore and false otherwise.
     */
    @Override
    public boolean takeARest(int pid) {
        return false;
    }

    /**
     * The porter tries to collect a bag from the plane's hold.
     *
     * @param pid The Porter's ID.
     * @return the bag's owner ID as a String, or an empty String if the plane is empty of bags.
     */
    @Override
    public String tryToCollectABag(int pid) {
        return null;
    }
}
