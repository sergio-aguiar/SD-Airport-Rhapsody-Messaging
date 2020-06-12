package HybridServerSide.ArrivalLounge;

import ClientSide.Extras.Bag;
import ClientSide.Interfaces.ALPassenger;
import ClientSide.Interfaces.ALPorter;
import HybridServerSide.Repository.Repository;
import HybridServerSide.Stubs.RepositoryStub;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Stack;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Arrival Lounge: Where the Passenger arrives and the Porter awaits a plane to begin working.
 * Used by PORTER and PASSENGER.
 * @author sergiaguiar
 * @author marcomacedo
 */
public class ArrivalLounge implements ALPassenger, ALPorter {
    /**
     * The class's ReentrantLock instance.
     */
    private final ReentrantLock reentrantLock;
    /**
     * The Condition instance where the porter awaits for every passenger to leave the plane.
     */
    private final Condition porterCondition;
    /**
     * The total amount of passengers throughout every planned flight.
     */
    private int maxCrossFlightPassengers;
    /**
     * A count for the amount of passengers throughout every planned flight who have made it past where the bus driver is needed.
     */
    private int crossFlightPassengerCount;
    /**
     * Total number of passengers per flight.
     */
    private int totalPassengers;
    /**
     * Number of Passengers that arrived at airport on the current flight.
     */
    private int passengersThatArrived;
    /**
     * The current flight number.
     */
    private int flightNumber;
    /**
     * Total number of flights.
     */
    private int totalFlights;
    /**
     * Array that contains the bags of each passenger per flight.
     */
    private Bag[][][] luggagePerFlight;
    /**
     * Stack that contains the bags currently in the plane.
     */
    private Stack<Bag> bagsInThePlane;
    /**
     * The class's Repository instance.
     */

    /**
     * The class's FIle instance.
     */
    private File logFile;
    /**
     * The class's BufferedWriter instance.
     */
    private BufferedWriter writer;

    private final RepositoryStub repositoryStub;
    /**
     * ArrivalLounge constructor.
     * @param repositoryStub A reference to a RepositoryStub object.
     * @param totalPassengers Total number of passengers per flight.
     * @param totalFlights Total number of flights.
     * @param luggagePerFlight Array that contains the bags of each passenger per flight.
     */
    public ArrivalLounge(RepositoryStub repositoryStub, int totalPassengers, int totalFlights, Bag[][][] luggagePerFlight) {
        this.reentrantLock = new ReentrantLock();
        this.porterCondition = this.reentrantLock.newCondition();
        this.maxCrossFlightPassengers = totalFlights * totalPassengers;
        this.crossFlightPassengerCount = 0;
        this.totalPassengers = totalPassengers;
        this.totalFlights = totalFlights;
        this.passengersThatArrived = 0;
        this.flightNumber = 0;
        this.luggagePerFlight = luggagePerFlight;
        this.bagsInThePlane = new Stack<>();
        this.bagArrayToStack(0);
        this.repositoryStub = repositoryStub;
    }
    /**
     * ArrivalLounge constructor.
     */
    public ArrivalLounge(RepositoryStub repositoryStub) {
        this.reentrantLock = new ReentrantLock();
        this.porterCondition = this.reentrantLock.newCondition();
        this.maxCrossFlightPassengers = 0;
        this.crossFlightPassengerCount = 0;
        this.passengersThatArrived = 0;
        this.flightNumber = 0;
        this.bagsInThePlane = new Stack<>();
        this.repositoryStub = repositoryStub;
        
        try {
            this.logStart();
        } catch(Exception e) {
            this.log(e.toString());
        }
    }

    public void setInitialState(int totalPassengers, int totalFlights, Bag[][][] luggagePerFlight) {
        this.reentrantLock.lock();
        try {
            this.maxCrossFlightPassengers = totalFlights * totalPassengers;
            this.totalPassengers = totalPassengers;
            this.totalFlights = totalFlights;
            this.luggagePerFlight = luggagePerFlight;
            this.bagArrayToStack(0);
        } catch (Exception e) {
            this.log("AL: setInitialState: " + e.toString());
        } finally {
            this.reentrantLock.unlock();
        }
    }

    /**
     * Function that fills the plane's bag Stack depending on the current flight number.
     * @param flightNumber Flight number.
     */
    private void bagArrayToStack(int flightNumber) {
        for(int i = 0; i < this.luggagePerFlight[flightNumber].length; i++)
            for(int j = 0; j < this.luggagePerFlight[flightNumber][i].length; j++)
                if(this.luggagePerFlight[flightNumber][i][j] != null)
                    this.bagsInThePlane.push(this.luggagePerFlight[flightNumber][i][j]);
    }
    /**
     * Function that allows for a transition to a new flight (new plane landing simulation).
     */
    public void prepareForNextFlight() {
        this.reentrantLock.lock();
        try {
            this.passengersThatArrived = 0;
            this.flightNumber++;
            this.bagArrayToStack(this.flightNumber);
        } catch (Exception e) {
            this.log("AL: prepareForNextFlight: " + e.toString());
        } finally {
            this.reentrantLock.unlock();
        }
    }
    /**
     * Function that verifies if any more passengers in the future need the bus driver's services.
     * @return true if no future passengers need the bus driver's services and false otherwise.
     */
    public boolean passengersNoLongerNeedTheBus() {
        boolean maxReached = false;
        this.reentrantLock.lock();
        try {
            maxReached = this.maxCrossFlightPassengers == this.crossFlightPassengerCount;
        } catch (Exception e) {
            this.log("AL: incrementCrossFlightPassengerCount: " + e.toString());
        } finally {
            this.reentrantLock.unlock();
        }
        if(this.maxCrossFlightPassengers == 0) maxReached = false;
        return maxReached;
    }
    /**
     * Function that increments the count for the number of passengers throughout every planned flight who have made it past where the bus driver is needed.
     */
    public void incrementCrossFlightPassengerCount() {
        this.reentrantLock.lock();
        try {
            this.crossFlightPassengerCount++;
        } catch (Exception e) {
            this.log("AL: incrementCrossFlightPassengerCount: " + e.toString());
        } finally {
            this.reentrantLock.unlock();
        }
    }
    /**
     * The porter checks whether he is still needed in the future. If so, he awaits the next flight. He stops otherwise.
     * @return true if the porter isn't needed anymore and false otherwise.
     */
    @Override
    public boolean takeARest() {
        boolean done = (this.flightNumber == this.totalFlights - 1 && this.bagsInThePlane.size() == 0);
        this.reentrantLock.lock();
        try {
            this.repositoryStub.porterInitiated();
            if(!done) this.porterCondition.await();
        } catch (Exception e) {
            this.log("AL: takeARest: " + e.toString());
        } finally {
            this.reentrantLock.unlock();
        }
        return done;
    }
    /**
     * Passenger's first action when arriving from the plane. Signal's the porter if the final passenger to arrive.
     * @param pid Passenger id.
     * @param situation A string representation of the passenger's travel situation.
     */
    @Override
    public void whatShouldIDo(int pid, String situation) {
        this.reentrantLock.lock();
        try {
            this.repositoryStub.passengerInitiated(pid);
            this.passengersThatArrived++;
            if(situation.equals("FDT")) this.incrementCrossFlightPassengerCount();
            if(this.passengersThatArrived == this.totalPassengers) this.porterCondition.signal();
        } catch (Exception e) {
            this.log("AL: whatShouldIDo: " + e.toString());
        } finally {
            this.reentrantLock.unlock();
        }
    }
    /**
     * The porter tries to collect a bag from the plane's hold.
     * @return the bag's owner ID as a String, or an empty String if the plane is empty of bags.
     */
    @Override
    public String tryToCollectABag() {
        String returnVal = "";
        this.reentrantLock.lock();
        try {
            if(!this.bagsInThePlane.isEmpty()) {
                returnVal = this.bagsInThePlane.pop().toString();
                this.repositoryStub.porterTryCollectingBagFromPlane(true);
            }
            this.repositoryStub.porterTryCollectingBagFromPlane(false);
        } catch (Exception e) {
            this.log("AL: tryToCollectABag: " + e.toString());
        } finally {
            this.reentrantLock.unlock();
        }
        return returnVal;
    }
    /**
     * The Passenger decides to move towards the Baggage Collection Point to collect a bag.
     * @param pid The passenger's ID.
     */
    @Override
    public void goCollectABag(int pid) {
        this.reentrantLock.lock();
        try {
            this.repositoryStub.passengerGoingToCollectABag(pid);
        } catch (Exception e) {
            this.log("AL: goCollectABag: " + e.toString());
        } finally {
            this.reentrantLock.unlock();
        }
    }

    private void logStart() throws IOException {
        // open data stream to log file
        this.logFile = new File("logFile_AL_" + System.nanoTime() + ".txt");
        this.writer = new BufferedWriter(new FileWriter(this.logFile));
    }
    /**
     * Function that closes the BufferedWriter instance.
     */
    private void close() {
        try {
            this.writer.close();
        } catch (IOException ex) {
            Logger.getLogger(Repository.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    /**
     * Function that writes the current info onto the log file.
     */
    private void log(String logString) {
        this.reentrantLock.lock();
        try {
            this.writer.write((logString + "\n"));
            this.writer.flush();
        } catch (Exception e) {
            this.log("AL: log: " + e.toString());
        } finally {
            this.reentrantLock.unlock();
        }
    }
}
