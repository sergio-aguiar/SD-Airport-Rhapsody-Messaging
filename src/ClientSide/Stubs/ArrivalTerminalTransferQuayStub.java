package ClientSide.Stubs;

import ClientSide.Interfaces.ATTQBusDriver;
import ClientSide.Interfaces.ATTQPassenger;

public class ArrivalTerminalTransferQuayStub implements ATTQPassenger, ATTQBusDriver  {

    private String serverHostName;
    private int serverHostPort;

    public ArrivalTerminalTransferQuayStub(String hostName, int hostPort) {
        this.serverHostName = hostName;
        this.serverHostPort = hostPort;
    }

    /**
     * The bus driver checks if his services are needed in the future.
     *
     * @return true if the bus driver's services are not needed in the future and false otherwise.
     */
    @Override
    public boolean hasDaysWorkEnded() {
        return false;
    }

    /**
     * The bus driver announces that the bus is boarding after seeing that at least one passenger is in queue.
     *
     * @return false if no other future passengers need the bus driver's services and true otherwise.
     */
    @Override
    public boolean announcingBusBoarding() {
        return false;
    }

    /**
     * The bus driver drives towards the Departure Terminal Transfer Quay.
     *
     * @param bid The bus driver's ID.
     * @return the number of passenger being taken inside the bus.
     */
    @Override
    public int goToDepartureTerminal(int bid) {
        return 0;
    }

    /**
     * The bus driver parks the bus and gets ready for possibly a new trip.
     *
     * @param bid The bus driver's ID.
     */
    @Override
    public void parkTheBus(int bid) {

    }

    /**
     * The passenger decides to take the bus and enters the waiting queue.
     *
     * @param pid the passenger's ID.
     */
    @Override
    public void takeABus(int pid) {

    }

    /**
     * The Passenger leaves the waiting queue and enters the bus.
     *
     * @param pid The passenger's ID.
     * @return the passenger's seat on the bus.
     */
    @Override
    public int enterTheBus(int pid) {
        return 0;
    }
}
