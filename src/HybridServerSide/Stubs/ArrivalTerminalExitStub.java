package HybridServerSide.Stubs;

import HybridServerSide.Interfaces.ATEforDTE;

public class ArrivalTerminalExitStub implements ATEforDTE {

    private String serverHostName;
    private int serverHostPort;

    public ArrivalTerminalExitStub(String hostName, int hostPort) {
        this.serverHostName = hostName;
        this.serverHostPort = hostPort;
    }

    /**
     * Allows the DepartureTerminalEntrance instance to know the number of passengers awaiting at the ArrivalTerminalExit.
     *
     * @return the number of waiting passengers at the ArrivalTerminalExit.
     */
    @Override
    public int getWaitingPassengers() {
        return 0;
    }

    /**
     * Allows the DepartureTerminalEntrance instance to signal all waiting passengers at the ArrivalTerminalExit.
     */
    @Override
    public void signalWaitingPassengers() {

    }
}
