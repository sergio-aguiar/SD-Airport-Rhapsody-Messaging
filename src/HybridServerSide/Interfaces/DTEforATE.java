package HybridServerSide.Interfaces;

public interface DTEforATE {

    /**
     * Allows the ArrivalTerminalExit instance to know the number of passengers awaiting at the DepartureTerminalEntrance.
     * @return the number of waiting passengers at the ArrivalTerminalExit.
     */
    public int getWaitingPassengers();

    /**
     * Allows the ArrivalTerminalExit instance to signal all waiting passengers at the DepartureTerminalEntrance.
     */
    public void signalWaitingPassengers();
}
