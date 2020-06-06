package ClientSide.Stubs;

import ClientSide.Interfaces.DTTQBusDriver;
import ClientSide.Interfaces.DTTQPassenger;

public class DepartureTerminalTransferQuayStub implements DTTQPassenger, DTTQBusDriver {

    private String serverHostName;
    private int serverHostPort;

    public DepartureTerminalTransferQuayStub(String hostName, int hostPort) {
        this.serverHostName = hostName;
        this.serverHostPort = hostPort;
    }

    /**
     * The bus driver parks the Bus and let's teh passengers off.
     *
     * @param bid                   The bus driver's ID.
     * @param passengersThatArrived The number of passengers that arrived aboard the bus.
     * @param flightNumber          The bus driver's estimated flight number.
     * @return The current flight number.
     */
    @Override
    public int parkTheBusAndLetPassOff(int bid, int passengersThatArrived, int flightNumber) {
        return 0;
    }

    /**
     * The bus driver drives towards the Arrival Terminal Transfer Quay.
     *
     * @param bid The bus driver's ID.
     */
    @Override
    public void goToArrivalTerminal(int bid) {

    }

    /**
     * The passenger leaves the bus and signals the bus driver if he's the last one to do so.
     *
     * @param pid  The passenger's ID.
     * @param seat The passenger's bus seat.
     */
    @Override
    public void leaveTheBus(int pid, int seat) {

    }
}
