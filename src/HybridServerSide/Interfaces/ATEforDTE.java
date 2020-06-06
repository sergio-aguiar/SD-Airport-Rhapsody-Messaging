package HybridServerSide.Interfaces;

public interface ATEforDTE {

    /**
     * Allows the DepartureTerminalEntrance instance to know the number of passengers awaiting at the ArrivalTerminalExit.
     * @return the number of waiting passengers at the ArrivalTerminalExit.
     */
    public int getWaitingPassengers();

    /**
     * Allows the DepartureTerminalEntrance instance to signal all waiting passengers at the ArrivalTerminalExit.
     */
    public void signalWaitingPassengers();
}
