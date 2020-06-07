package HybridServerSide.ArrivalTerminalExit;

import ClientSide.Interfaces.ATEPassenger;
import HybridServerSide.DepartureTerminalEntrance.DepartureTerminalEntrance;
import HybridServerSide.Interfaces.ATEforDTE;
import HybridServerSide.Repository.Repository;
import HybridServerSide.Stubs.RepositoryStub;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
/** Arrival Terminal Exit: Where passengers await the last one to reach their destination within the airport to signal them that they can leave.
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
     * The Condition instance where the passengers wait for the last passenger to arrive at their final destination in the airport.
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
    private DepartureTerminalEntrance dte;
    /**
     * The class's RepositoryStub instance.
     */
    private final RepositoryStub repositoryStub;
    /**
     * ArrivalTerminalExit constructor.
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
     * ArrivalTerminalExit constructor.
     */
    public ArrivalTerminalExit(RepositoryStub repositoryStub) {
        this.reentrantLock = new ReentrantLock(true);
        this.passengerCondition = this.reentrantLock.newCondition();
        this.allSignaled = false;
        this.repositoryStub = repositoryStub;
    }

    public void setInitialState(int totalPassengers) {
        this.totalPassengers = totalPassengers;
    }

    /**
     * Function that sets the reference to a DepartureTerminalEntrance object.
     * @param dte A reference to a DepartureTerminalEntrance object.
     */
    public void setDte(DepartureTerminalEntrance dte) {
        this.dte = dte;
    }
    /**
     * Function that gets the number of passengers waiting for the last one to arrive at their final destination inside the airport.
     * @return The number of passengers waiting for the last one to arrive at their final destination inside the airport.
     */
    @Override
    public int getWaitingPassengers() {
        int tmpWaitingPassengers = 0;
        this.reentrantLock.lock();
        try {
            tmpWaitingPassengers = this.waitingPassengers;
        } catch (Exception e) {
            System.out.print("ATE: getWaitingPassengers: " + e.toString());
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
            System.out.print("ATE: signalWaitingPassengers: " + e.toString());
        } finally {
            this.reentrantLock.unlock();
        }
    }
    /**
     * Function that allows for a transition to a new flight (new plane landing simulation).
     */
    public void prepareForNextFlight() {
        this.allSignaled = false;
        this.waitingPassengers = 0;
    }
    /**
     * The passenger checks if they is the last to make it to their destination inside the airport. If so, they signal all others to leave together. Otherwise, they wait for the last one to signal them.
     * @param pid The passenger's ID.
     */
    @Override
    public void goHome(int pid) {
        this.repositoryStub.passengerGoingHome(pid);

        this.reentrantLock.lock();
        try {
            this.waitingPassengers++;
        } catch (Exception e) {
            System.out.println("ATE: goHome1: " + e.toString());
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
            System.out.println("ATE: goHome2: " + e.toString());
        } finally {
            this.reentrantLock.unlock();
        }
        if(this.allSignaled) this.dte.signalWaitingPassengers();
    }
}