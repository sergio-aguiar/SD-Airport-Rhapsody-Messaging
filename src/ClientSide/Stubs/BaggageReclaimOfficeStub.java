package ClientSide.Stubs;

import ClientSide.Interfaces.BROPassenger;

public class BaggageReclaimOfficeStub implements BROPassenger {

    private String serverHostName;
    private int serverHostPort;

    public BaggageReclaimOfficeStub(String hostName, int hostPort) {
        this.serverHostName = hostName;
        this.serverHostPort = hostPort;
    }

    /**
     * Passengers report how many missing bags they have.
     *
     * @param pid         The passenger's ID.
     * @param missingBags The passenger's amount of missing bags.
     */
    @Override
    public void reportMissingBags(int pid, int missingBags) {

    }
}
