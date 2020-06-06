package ClientSide.Stubs;

import ClientSide.Interfaces.DTEPassenger;

public class DepartureTerminalEntranceStub implements DTEPassenger {

    private String serverHostName;
    private int serverHostPort;

    public DepartureTerminalEntranceStub(String hostName, int hostPort) {
        this.serverHostName = hostName;
        this.serverHostPort = hostPort;
    }

    /**
     * The passenger checks if they is the last to make it to their destination inside the airport. If so, they signal all others to leave together. Otherwise, they wait for the last one to signal them.
     *
     * @param pid The passenger's ID.
     */
    @Override
    public void prepareNextLeg(int pid) {

    }
}
