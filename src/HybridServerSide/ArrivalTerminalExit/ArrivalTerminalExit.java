package HybridServerSide.ArrivalTerminalExit;

import ClientSide.Interfaces.ATEPassenger;
import HybridServerSide.Interfaces.ATEforDTE;
import HybridServerSide.Stubs.DepartureTerminalEntranceStub;
import HybridServerSide.Stubs.RepositoryStub;
import genclass.GenericIO;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/** Arrival Terminal Exit: Where passengers await the last one to reach their destination within the airport to signal
 * them that they can leave.
 * Used by PASSENGER.
 * @author sergiaguiar
 * @author marcomacedo
 */
public class ArrivalTerminalExit implements ATEPassenger, ATEforDTE {
    /**
     * The class's ReentrantLock instance.
     */
    private final ReentrantLock reentrantLock;
    /**
     * The Condition instance where the passengers wait for the last passenger to arrive at their final destination in
     * the airport.
     */
    private final Condition passengerCondition;
    /**
     * Attribute that states whether all passengers were signalled by the last one to arrive yet.
     */
    private boolean allSignaled;
    /**
     * Total number of passengers per flight.
     */
    private int totalPassengers;
    /**
     * Number of passengers waiting for the last one to arrive at their final destination inside the airport.
     */
    private int waitingPassengers;
    /**
     * The class's instance of the Departure Terminal Entrance.
     */
    private DepartureTerminalEntranceStub dte;
    /**
     * The class's RepositoryStub instance.
     */
    private final RepositoryStub repositoryStub;
    /**
     * Constructor: ArrivalTerminalExit.
     * @param repositoryStub A reference to a RepositoryStub object.
     * @param totalPassengers Total number of passengers per flight.
     */
    public ArrivalTerminalExit(RepositoryStub repositoryStub, int totalPassengers) {
        this.reentrantLock = new ReentrantLock(true);
        this.passengerCondition = this.reentrantLock.newCondition();
        this.allSignaled = false;
        this.totalPassengers = totalPassengers;
        this.repositoryStub = repositoryStub;
    }
    /**
     * Constructor: ArrivalTerminalExit.
     * @param repositoryStub A reference to a RepositoryStub object.
     * @param dte The class's instance of the Departure Terminal Entrance.
     */
    public ArrivalTerminalExit(RepositoryStub repositoryStub, DepartureTerminalEntranceStub dte) {
        this.reentrantLock = new ReentrantLock(true);
        this.passengerCondition = this.reentrantLock.newCondition();
        this.allSignaled = false;
        this.dte = dte;
        this.repositoryStub = repositoryStub;
    }
    /**
     * Function that sets the initial state of a server's data.
     * @param totalPassengers Total number of passengers per flight.
     */
    public void setInitialState(int totalPassengers) {
        this.reentrantLock.lock();
        try {
            this.totalPassengers = totalPassengers;
        } catch (Exception e) {
            GenericIO.writelnString("AL: setInitialState: " + e.toString());
            System.exit(1);
        } finally {
            this.reentrantLock.unlock();
        }
    }
    /**
     * Function that gets the number of passengers waiting for the last one to arrive at their final destination inside
     * the airport.
     * @return The number of passengers waiting for the last one to arrive at their final destination inside the airport.
     */
    @Override
    public int getWaitingPassengers() {
        int tmpWaitingPassengers = 0;
        this.reentrantLock.lock();
        try {
            tmpWaitingPassengers = this.waitingPassengers;
        } catch (Exception e) {
            GenericIO.writelnString("ATE: getWaitingPassengers: " + e.toString());
            System.exit(1);
        } finally {
            this.reentrantLock.unlock();
        }
        return tmpWaitingPassengers;
    }
    /**
     * Function that signals every waiting passenger.
     */
    @Override
    public void signalWaitingPassengers() {
        this.reentrantLock.lock();
        try {
            this.allSignaled = true;
            this.passengerCondition.signalAll();
        } catch (Exception e) {
            GenericIO.writelnString("ATE: signalWaitingPassengers: " + e.toString());
            System.exit(1);
        } finally {
            this.reentrantLock.unlock();
        }
    }
    /**
     * Function that allows for a transition to a new flight (new plane landing simulation).
     */
    public void prepareForNextFlight() {
        this.reentrantLock.lock();
        try {
            this.allSignaled = false;
            this.waitingPassengers = 0;
        } catch (Exception e) {
            GenericIO.writelnString("ATE: prepareForNextFlight: " + e.toString());
            System.exit(1);
        } finally {
            this.reentrantLock.unlock();
        }
    }
    /**
     * The passenger checks if they are the last to make it to their destination inside the airport. If so, they signal
     * all others to leave together. Otherwise, they wait for the last one to signal them.
     * @param pid The passenger's ID.
     */
    @Override
    public void goHome(int pid) {
        this.repositoryStub.passengerGoingHome(pid);

        this.reentrantLock.lock();
        try {
            this.waitingPassengers++;
        } catch (Exception e) {
            GenericIO.writelnString("ATE: goHome1: " + e.toString());
            System.exit(1);
        } finally {
            this.reentrantLock.unlock();
        }

        int dteWaitingPassengers = this.dte.getWaitingPassengers();
        if(this.waitingPassengers + dteWaitingPassengers == this.totalPassengers) this.allSignaled = true;

        this.reentrantLock.lock();
        try {
            if(this.allSignaled) this.passengerCondition.signalAll();
            else this.passengerCondition.await();
        } catch (Exception e) {
            GenericIO.writelnString("ATE: goHome2: " + e.toString());
            System.exit(1);
        } finally {
            this.reentrantLock.unlock();
        }
        if(this.allSignaled) this.dte.signalWaitingPassengers();
    }
}