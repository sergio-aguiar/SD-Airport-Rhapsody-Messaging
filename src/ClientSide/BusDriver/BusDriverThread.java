package ClientSide.BusDriver;

import ClientSide.Interfaces.ATTQBusDriver;
import ClientSide.Interfaces.DTTQBusDriver;

/**
* BusDriver Thread: executes the Bus Driver's life-cycle.
* 
* @author marcomacedo
* @author sergioaguiar
*/
public class BusDriverThread extends Thread {
    /**
     * Enumerate with all possible Bus Driver states.
     */
    public enum BusDriverStates {
        PARKING_AT_THE_ARRIVAL_TERMINAL("PKAT"),
        DRIVING_FORWARD("DRFW"),
        PARKING_AT_THE_DEPARTURE_TERMINAL("PKDT"),
        DRIVING_BACKWARD("DRBW");
        /**
         * Enum's descriptive String.
         */
        String description;
        /**
         * BusDriverStates constructor.
         * @param description Enum's descriptive String.
         */
        BusDriverStates(String description) {
            this.description = description;
        }
        /**
         * Enum's toString override
         */
        @Override
        public String toString(){
            return this.description;
        }
    }
    /**
     * Bus Driver's ID.
     */
    private final int bid;
    /**
     * Instance of the Bus Driver's Arrival Terminal Transfer Quay interface.
     */
    private final ATTQBusDriver attqBusDriver;
     /**
     * Instance of the Bus Driver's Departure Terminal Transfer Quay interface.
     */
    private final DTTQBusDriver dttqBusDriver;
	/**
     * Number of passengers being taken in the bus.
     */
    private int passengersBeingTaken;
    /**
     * The currently estimated flight number.
     */
    private int flightNumber;
    /**
     * Constructor: Bus Driver
     * @param bid Bus Driver's ID.
     * @param attq Bus Driver's Arrival Terminal Transfer Quay Interface.
     * @param dttq Bus Driver's Departure Terminal Transfer Quay Interface.
     */
    public BusDriverThread(int bid, ATTQBusDriver attq, DTTQBusDriver dttq) {
        this.bid = bid;
        this.attqBusDriver = attq;
        this.dttqBusDriver = dttq;
        this.passengersBeingTaken = 0;
        this.flightNumber = 0;
    }
	/**
     * Executes the Bus Driver's life-cycle.
     */
    @Override
    public void run() {
        while(!this.attqBusDriver.hasDaysWorkEnded()) {
            if(!this.attqBusDriver.announcingBusBoarding()) break;
            this.passengersBeingTaken = this.attqBusDriver.goToDepartureTerminal();
            this.flightNumber = this.dttqBusDriver.parkTheBusAndLetPassOff(this.passengersBeingTaken, this.flightNumber);
            this.dttqBusDriver.goToArrivalTerminal();
            this.attqBusDriver.parkTheBus();
        }
        System.out.println("BUS DRIVER FINISHED: " + this.bid);
    }
}
