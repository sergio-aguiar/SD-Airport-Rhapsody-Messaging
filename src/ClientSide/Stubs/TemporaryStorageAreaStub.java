package ClientSide.Stubs;

import ClientSide.Interfaces.TSAPorter;

public class TemporaryStorageAreaStub implements TSAPorter {

    private String serverHostName;
    private int serverHostPort;

    public TemporaryStorageAreaStub(String hostName, int hostPort) {
        this.serverHostName = hostName;
        this.serverHostPort = hostPort;
    }

    /**
     * The Porter carries their held bag to the Temporary Storage Area.
     *
     * @param pid   The porter's ID.
     * @param bagID The porter's held bag's owner's ID.
     */
    @Override
    public void carryItToAppropriateStore(int pid, int bagID) {

    }
}
