package HybridServerSide.Stubs;

import HybridServerSide.Interfaces.DTEforATE;

public class DepartureTerminalEntranceStub implements DTEforATE {

    private String serverHostName;
    private int serverHostPort;

    public DepartureTerminalEntranceStub(String hostName, int hostPort) {
        this.serverHostName = hostName;
        this.serverHostPort = hostPort;
    }

    /**
     * Allows the ArrivalTerminalExit instance to know the number of passengers awaiting at the DepartureTerminalEntrance.
     *
     * @return the number of waiting passengers at the ArrivalTerminalExit.
     */
    @Override
    public int getWaitingPassengers() {
        return 0;
    }

    /**
     * Allows the ArrivalTerminalExit instance to signal all waiting passengers at the DepartureTerminalEntrance.
     */
    @Override
    public void signalWaitingPassengers() {

    }
}
