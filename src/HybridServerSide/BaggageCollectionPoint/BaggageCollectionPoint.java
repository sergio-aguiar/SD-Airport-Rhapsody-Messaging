package HybridServerSide.BaggageCollectionPoint;

import ClientSide.Interfaces.BCPPassenger;
import ClientSide.Interfaces.BCPPorter;
import HybridServerSide.Repository.Repository;
import HybridServerSide.Stubs.RepositoryStub;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;

/**Baggage Collection Point: Where passengers pick up their luggage and where the porter leaves final destination luggage.
 * Used by PORTER and PASSENGER.
 * @author sergiaguiar
 * @author marcomacedo
 */
public class BaggageCollectionPoint implements BCPPassenger, BCPPorter {
    /**
     * The class's ReentrantLock instance.
     */
    private final ReentrantLock reentrantLock;
    /**
     * Array of Condition instances (one for each passenger) where each passenger waits for one of their bags to be put onto the conveyor belt.
     */
    private Condition[] passengerLuggageConditions;
    /**
     * Array that contains the amount of luggage waiting to be collected by each passenger.
     */
    private int[] passengerLuggageNumber;
    /**
     * Attribute that states whether there are any more bags on the conveyor belt or not.
     */
    private boolean noMoreBags;
    /**
     * Arraylist of bags on the conveyor belt.
     */
    private final ArrayList<Integer> bcpBags;
    /**
     * The class's Repository instance.
     */
    private final RepositoryStub repositoryStub;

    /**
     * The class's FIle instance.
     */
    private File logFile;
    /**
     * The class's BufferedWriter instance.
     */
    private BufferedWriter writer;

    /**
     * BaggageCollectionPoint constructor.
     * @param repositoryStub A reference to a repositoryStub object.
     * @param totalPassengers Total number of passengers per flight.
     */
    public BaggageCollectionPoint(RepositoryStub repositoryStub, int totalPassengers) {
        this.reentrantLock = new ReentrantLock(true);
        this.passengerLuggageConditions = new Condition[totalPassengers];
        for(int c = 0; c < totalPassengers; c++) this.passengerLuggageConditions[c] = this.reentrantLock.newCondition();
        this.passengerLuggageNumber = new int[totalPassengers];
        this.noMoreBags = false;
        Arrays.fill(this.passengerLuggageNumber, 0);
        this.bcpBags = new ArrayList<>();
        this.repositoryStub = repositoryStub;
    }

    /**
     * BaggageCollectionPoint constructor.
     * @param repositoryStub A reference to a repositoryStub object.
     */
    public BaggageCollectionPoint(RepositoryStub repositoryStub) {
        this.reentrantLock = new ReentrantLock(true);
        this.noMoreBags = false;
        this.bcpBags = new ArrayList<>();
        this.repositoryStub = repositoryStub;

        try {
            this.logStart();
        } catch(Exception e) {
            this.log(e.toString());
        }
    }

    public void setInitialState(int totalPassengers) {
        this.reentrantLock.lock();
        try {
            this.passengerLuggageConditions = new Condition[totalPassengers];
            for(int c = 0; c < totalPassengers; c++) this.passengerLuggageConditions[c] = this.reentrantLock.newCondition();
            this.passengerLuggageNumber = new int[totalPassengers];
            Arrays.fill(this.passengerLuggageNumber, 0);
        } catch (Exception e) {
            this.log("BCP: setInitialState: " + e.toString());
        } finally {
            this.reentrantLock.unlock();
        }
    }

    /**
     * Function that checks whether a passenger's bag is on the conveyor belt.
     * @param pid The passenger's ID.
     * @return true if a bag belonging to the passenger is on the conveyor belt and false otherwise.
     */
    private boolean isPassengerBagInCollectionPoint(int pid) {
        for(Integer bag : this.bcpBags) if(bag == pid) return true;
        return false;
    }
    /**
     * The passenger claims a bag from the conveyor belt.
     * @param pid The passenger's ID.
     */
    private void claimBagFromBaggageCollectionPoint(int pid) {
        for(Integer bag : this.bcpBags)
            if(bag == pid) {
                this.bcpBags.remove(bag);
                break;
            }
    }
    /**
     * Function that allows for a transition to a new flight (new plane landing simulation).
     */
    public void prepareForNextFlight() {
        Arrays.fill(this.passengerLuggageNumber, 0);
    }
    /**
     * The Passenger tries to collect a bag from the conveyor.
     * @param pid The passenger's ID.
     * @return true if passenger succeeded in collecting a bag and false otherwise.
     */
    @Override
    public boolean goCollectABag(int pid) {
        boolean success = false;
        this.reentrantLock.lock();
        try {
            if(!this.noMoreBags) {
                if (this.passengerLuggageNumber[pid] == 0) this.passengerLuggageConditions[pid].await();
                if (this.isPassengerBagInCollectionPoint(pid)) {
                    this.claimBagFromBaggageCollectionPoint(pid);
                    this.repositoryStub.passengerCollectingABag(pid);
                    this.passengerLuggageNumber[pid]--;
                    success = true;
                }
            }
        } catch (Exception e) {
            this.log("BCP: goCollectABag: " + e.toString());
        } finally {
            this.reentrantLock.unlock();
        }
        return success;
    }
    /**
     * The Porter carries their held bag to the Baggage Collection Point.
     * @param bagID The porter's held bag's owner's ID.
     */
    @Override
    public void carryItToAppropriateStore(int bagID) {
        this.reentrantLock.lock();
        try {
            this.repositoryStub.porterCarryBagToBaggageCollectionPoint();
            this.bcpBags.add(bagID);
            this.passengerLuggageNumber[bagID]++;
            this.passengerLuggageConditions[bagID].signal();
        } catch (Exception e) {
            this.log("BCP: carryItToAppropriateStore: " + e.toString());
        } finally {
            this.reentrantLock.unlock();
        }
    }
    /**
     * The porter announces that there are no more bags in the plane by signalling every waiting passenger.
     * @param pid The passenger's ID.
     */
    @Override
    public void noMoreBagsToCollect(int pid) {
        this.reentrantLock.lock();
        try {
            for(Condition c : this.passengerLuggageConditions) c.signal();
            this.noMoreBags = true;
            this.repositoryStub.porterAnnouncingNoMoreBagsToCollect();
        } catch (Exception e) {
            this.log("BCP: noMoreBagsToCollect: " + e.toString());
        } finally {
            this.reentrantLock.unlock();
        }
    }

    private void logStart() throws IOException {
        // open data stream to log file
        this.logFile = new File("logFile_BCP_" + System.nanoTime() + ".txt");
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
            this.log("BCP: log: " + e.toString());
        } finally {
            this.reentrantLock.unlock();
        }
    }
}
