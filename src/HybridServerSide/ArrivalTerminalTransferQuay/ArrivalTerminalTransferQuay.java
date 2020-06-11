package HybridServerSide.ArrivalTerminalTransferQuay;

import ClientSide.Interfaces.ATTQBusDriver;
import ClientSide.Interfaces.ATTQPassenger;

import HybridServerSide.ArrivalLounge.ArrivalLounge;
import HybridServerSide.Repository.Repository;
import HybridServerSide.Stubs.ArrivalLoungeStub;
import HybridServerSide.Stubs.RepositoryStub;

import java.util.Arrays;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
/** Arrival Terminal Transfer Quay: Where passengers await the bus to transfer terminals and the bus driver awaits them.
 * Used by PASSENGER and BUS DRIVER.
 * @author sergiaguiar
 * @author marcomacedo
 */
public class ArrivalTerminalTransferQuay implements ATTQPassenger, ATTQBusDriver {
    /**
     * The class's ReentrantLock instance.
     */
    private final ReentrantLock reentrantLock;
    /**
     * The Condition instance where the passengers wait for the bus driver to signal that he bus is now boarding.
     */
    private final Condition busQueueCondition;
    /**
     * The Condition instance where the passengers wait for the bus driver to signal that the bus has left.
     */
    private final Condition busLeavingCondition;
    /**
     * The Condition instance where bus driver awaits for queued passengers and for them to later enter the bus.
     */
    private final Condition busDriverCondition;
     /**
     * Total number of passengers per flight.
     */
    private int totalPassengers;
    /**
     * Number of seats in the bus.
     */
    private int busSeatNumber;
    /**
     * Number of passengers in the bus waiting queue.
     */
    private int queuedPassengers;
    /**
     * Number of passengers in the bus.
     */
    private int passengersInBus;
    /**
     * Number of passengers signaled to board the bus.
     */
    private int passengersSignaled;
    /**
     * Attribute that specifies whether the bus is already boarding or not.
     */
    private boolean busBoarding;
    /**
     * Array with the bus seat positions.
     */
    private String[] busSeats;
    /**
     * Array with the bus waiting queue positions.
     */
    private String[] busWaitingQueue;
    /**
     * The class's ArrivalLounge instance.
     */
    private ArrivalLoungeStub arrivalLoungeStub;
    /**
     * The class's RepositoryStub instance.
     */
    private final RepositoryStub repositoryStub;
    /**
     * Arrival Terminal Transfer Quay constructor.
     * @param repositoryStub repositoryStub.
     * @param totalPassengers Number of total passengers.
     * @param busSeatNumber Bus seat number.
     * @param arrivalLoungeStub The class's ArrivalLounge instance.
     */
    public ArrivalTerminalTransferQuay(RepositoryStub repositoryStub, int totalPassengers, int busSeatNumber, ArrivalLoungeStub arrivalLoungeStub){
        this.reentrantLock = new ReentrantLock(true);
        this.busQueueCondition = this.reentrantLock.newCondition();
        this.busLeavingCondition = this.reentrantLock.newCondition();
        this.busDriverCondition = this.reentrantLock.newCondition();
        this.totalPassengers = totalPassengers;
        this.busSeatNumber = busSeatNumber;
        this.queuedPassengers = 0;
        this.passengersInBus = 0;
        this.passengersSignaled = 0;
        this.busBoarding = false;
        this.busSeats = new String[busSeatNumber];
        this.busWaitingQueue = new String[totalPassengers];
        Arrays.fill(this.busSeats, "-");
        Arrays.fill(this.busWaitingQueue, "-");
        this.arrivalLoungeStub = arrivalLoungeStub;
        this.repositoryStub = repositoryStub;
    }
    /**
     * Arrival Terminal Transfer Quay constructor.
     * @param repositoryStub repositoryStub.
     * @param arrivalLoungeStub The class's ArrivalLounge instance.
     */
    public ArrivalTerminalTransferQuay(RepositoryStub repositoryStub, ArrivalLoungeStub arrivalLoungeStub){
        this.reentrantLock = new ReentrantLock(true);
        this.busQueueCondition = this.reentrantLock.newCondition();
        this.busLeavingCondition = this.reentrantLock.newCondition();
        this.busDriverCondition = this.reentrantLock.newCondition();
        this.queuedPassengers = 0;
        this.passengersInBus = 0;
        this.passengersSignaled = 0;
        this.busBoarding = false;
        this.arrivalLoungeStub = arrivalLoungeStub;
        this.repositoryStub = repositoryStub;
    }

    public void setInitialState(int totalPassengers, int busSeatNumber) {
        this.totalPassengers = totalPassengers;
        this.busSeatNumber = busSeatNumber;
        this.busSeats = new String[busSeatNumber];
        this.busWaitingQueue = new String[totalPassengers];
        Arrays.fill(this.busSeats, "-");
        Arrays.fill(this.busWaitingQueue, "-");
    }

    /**
     * Function that adds passengers to the waiting queue.
     * @param pid The passenger's ID.
     * @param positionInQueue The passenger's position in the queue.
     */
    private void addToWaitingQueue(int pid, int positionInQueue) {
        this.busWaitingQueue[positionInQueue] = String.valueOf(pid);
    }
    /**
     * Function that removes a passenger from the waiting queue.
     * @param pid The passenger's ID.
     */
    private void removeFromWaitingQueue(int pid) {
        String[] tmpQueue = new String[this.totalPassengers];
        int found = -1;
        for(int i = 0; i < this.totalPassengers; i++) {
            if(this.busWaitingQueue[i].equals(String.valueOf(pid))) found = i;
            else {
                if(found != -1) tmpQueue[i - 1] = this.busWaitingQueue[i];
                else tmpQueue[i] = this.busWaitingQueue[i];
            }
        }
        tmpQueue[this.totalPassengers - 1] = "-";
        this.busWaitingQueue = Arrays.copyOf(tmpQueue, this.totalPassengers);
    }
    /**
     * Adding a passenger to the bus.
     * @param pid The passenger's ID.
     * @param seatInBus The passenger's seat on the bus.
     */
    private void addToBusSeats(int pid, int seatInBus) {
        this.busSeats[seatInBus] = String.valueOf(pid);
    }
    /**
     * Function to get a passenger into the bus waiting queue.
     * @param pid The passenger's ID.
     * @return The amount of queued passengers minus 1 (for position usage).
     */
    private int getIntoQueue(int pid) {
        this.addToWaitingQueue(pid, queuedPassengers);
        this.queuedPassengers++;
        return this.queuedPassengers - 1;
    }
    /**
     * Function to get a passenger out of the bus waiting queue.
     * @param pid The passenger's ID.
     */
    private void getOutOfQueue(int pid) {
        this.removeFromWaitingQueue(pid);
        this.queuedPassengers--;
    }
    /**
     * Function that gets the passenger into the bus.
     * @param pid The passenger's ID.
     * @return The amount of passengers in the bus minus 1 (for position usage).
     */
    private int getIntoBus(int pid) {
        this.addToBusSeats(pid, this.passengersInBus);
        this.passengersInBus++;
        return this.passengersInBus - 1;
    }
    /**
     * Function that allows for a transition to a new flight (new plane landing simulation).
     */
    public void prepareForNextFlight() {
        this.queuedPassengers = 0;
        this.passengersInBus = 0;
        this.passengersSignaled = 0;
        this.busBoarding = false;
        Arrays.fill(this.busSeats, "-");
        Arrays.fill(this.busWaitingQueue, "-");
    }
    /**
     * The bus driver announces that the bus is boarding after seeing that at least one passenger is in queue.
     * @return false if no other future passengers need the bus driver's services and true otherwise.
     */
    @Override
    public boolean announcingBusBoarding() {
        this.reentrantLock.lock();
        try {
            do {
                this.busDriverCondition.awaitNanos(100);
                if(arrivalLoungeStub.passengersNoLongerNeedTheBus()) return false;
            } while(this.queuedPassengers == 0);
            this.busBoarding = true;
            for(int i = 0; i < this.busSeatNumber && i < this.queuedPassengers; i++) {
                passengersSignaled++;
                this.busQueueCondition.signal();
            }
            if(this.passengersSignaled > 0) this.busDriverCondition.await();
        } catch (Exception e) {
            System.out.println("ATTQ: announcingBusBoarding: " + e.toString());
        } finally {
            this.reentrantLock.unlock();
        }
        return true;
    }
    /**
     * The Passenger leaves the waiting queue and enters the bus.
     * @param pid The passenger's ID.
     * @return the passenger's seat on the bus.
     */
    @Override
    public int enterTheBus(int pid) {
        int busSeat = -1;
        this.reentrantLock.lock();
        try {
            this.getOutOfQueue(pid);
            busSeat = this.getIntoBus(pid);
            if(this.passengersInBus == this.passengersSignaled) this.busDriverCondition.signal();
            this.repositoryStub.passengerEnteringTheBus(pid, this.passengersInBus - 1);
            this.busLeavingCondition.await();
        } catch (Exception e) {
            System.out.println("ATTQ: enterTheBus: " + e.toString());
        } finally {
            this.reentrantLock.unlock();
        }
        return busSeat;
    }
    /**
     * The bus driver checks if his services are needed in the future.
     * @return true if the bus driver's services are not needed in the future and false otherwise.
     */
    @Override
    public boolean hasDaysWorkEnded() {
        boolean dayEnded = this.arrivalLoungeStub.passengersNoLongerNeedTheBus();
        this.reentrantLock.lock();
        try {
            this.repositoryStub.busDriverInitiated();
        } catch (Exception e) {
            System.out.print("ATTQ: hasDaysWorkEnded: " + e.toString());
        } finally {
            this.reentrantLock.unlock();
        }
        return dayEnded;
    }
    /**
     * The bus driver parks the bus and gets ready for possibly a new trip.
     */
    @Override
    public void parkTheBus() {
        this.reentrantLock.lock();
        try {
            this.passengersInBus = 0;
            this.passengersSignaled = 0;
            this.busBoarding = false;
            this.repositoryStub.busDriverParkingTheBus();
        } catch (Exception e) {
            System.out.print("ATTQ: parkTheBus: " + e.toString());
        } finally {
            this.reentrantLock.unlock();
        }
    }
    /**
     * The passenger decides to take the bus and enters the waiting queue.
     * @param pid the passenger's ID.
     */
    @Override
    public void takeABus(int pid) {
        int queuePosition = -1;
        this.reentrantLock.lock();
        try {
            queuePosition = this.getIntoQueue(pid);
            this.repositoryStub.passengerGettingIntoTheWaitingQueue(pid, queuePosition);
            if(this.queuedPassengers == this.busSeatNumber && !busBoarding) this.busDriverCondition.signal();
            this.repositoryStub.passengerTakingABus(pid);
            this.busQueueCondition.await();
        } catch (Exception e) {
            System.out.print("ATTQ: takeABus: " + e.toString());
        } finally {
            this.reentrantLock.unlock();
        }
    }
    /**
     * The bus driver drives towards the Departure Terminal Transfer Quay.
     * @return the number of passenger being taken inside the bus.
     */
    @Override
    public int goToDepartureTerminal() {
        int busPassengers = 0;
        this.reentrantLock.lock();
        try {
            busPassengers = this.passengersInBus;
            for(int i = 0; i < busPassengers; i++) arrivalLoungeStub.incrementCrossFlightPassengerCount();
            this.repositoryStub.busDriverGoingToDepartureTerminal();
            this.busLeavingCondition.signalAll();
        } catch (Exception e) {
            System.out.println("ATTQ: goToDepartureTerminal: " + e.toString());
        } finally {
            this.reentrantLock.unlock();
        }
        return busPassengers;
    }
}
