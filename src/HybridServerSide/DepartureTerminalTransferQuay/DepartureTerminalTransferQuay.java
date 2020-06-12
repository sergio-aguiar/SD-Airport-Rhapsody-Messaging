package HybridServerSide.DepartureTerminalTransferQuay;

import ClientSide.Interfaces.DTTQBusDriver;
import ClientSide.Interfaces.DTTQPassenger;
import HybridServerSide.Repository.Repository;
import HybridServerSide.Stubs.RepositoryStub;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Departure Terminal Transfer Quay: Where the bus driver takes the passengers on his bus and where the passengers go to be able to reach the Departure Terminal Entrance.
 * Used by PASSENGER and BUS DRIVER.
 * @author sergiaguiar
 * @author marcomacedo
 */
public class DepartureTerminalTransferQuay implements DTTQPassenger, DTTQBusDriver {
    /**
     * The class's ReentrantLock instance.
     */
    private final ReentrantLock reentrantLock;
    /**
     * The Condition instance where the passengers wait for the bus driver to signal that they have made it to the Departure Terminal Transfer Quay.
     */
    private final Condition passengerCondition;
    /**
     * The Condition instance where the bus driver waits for every passenger to leave the bus.
     */
    private final Condition busDriverCondition;
    /**
     * Total number of passengers that arrived on the bus.
     */
    private int passengersThatArrived;
    /**
     * Total number of passengers that have left the bus.
     */
    private int passengersThatLeftTheBus;
    /**
     * Attribute that states whether the passengers can leave the bus or not.
     */
    private boolean canLeaveTheBus;
    /**
     * The current flight number.
     */
    private int flightNumber;
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
     * DepartureTerminalTransferQuay constructor.
     * @param repositoryStub A reference to a repository object.
     */
    public DepartureTerminalTransferQuay(RepositoryStub repositoryStub) {
        this.reentrantLock = new ReentrantLock(true);
        this.passengerCondition = this.reentrantLock.newCondition();
        this.busDriverCondition = this.reentrantLock.newCondition();
        this.passengersThatArrived = 0;
        this.passengersThatLeftTheBus = 0;
        this.canLeaveTheBus = false;
        this.flightNumber = 0;
        this.repositoryStub = repositoryStub;

        try {
            this.logStart();
        } catch(Exception e) {
            this.log(e.toString());
        }
    }
    /**
     * Function that allows for a transition to a new flight (new plane landing simulation).
     */
    public void prepareForNextFlight() {
        this.reentrantLock.lock();
        try {
            this.passengersThatArrived = 0;
            this.passengersThatLeftTheBus = 0;
            this.canLeaveTheBus = false;
            this.flightNumber++;
        } catch (Exception e) {
            this.log("DTTQ: prepareForNextFlight: " + e.toString());
        } finally {
            this.reentrantLock.unlock();
        }
    }
    /**
     * The bus driver drives towards the Arrival Terminal Transfer Quay.
     */
    @Override
    public void goToArrivalTerminal() {
        this.reentrantLock.lock();
        try {
            this.passengersThatArrived = 0;
            this.passengersThatLeftTheBus = 0;
            this.repositoryStub.busDriverGoingToArrivalTerminal();
        } catch(Exception e) {
            this.log("DTTQ: goToArrivalTerminal: " + e.toString());
        } finally {
            this.reentrantLock.unlock();
        }
    }
    /**
     * The passenger leaves the bus and signals the bus driver if he's the last one to do so.
     * @param pid The passenger's ID.
     * @param seat The passenger's bus seat.
     */
    @Override
    public void leaveTheBus(int pid, int seat) {
        this.log("PASSENGER " + pid + " MADE IT TO THE START OF LEAVE THE BUS!");
        this.reentrantLock.lock();
        try {
            this.log("PASSENGER " + pid + " MADE IT PAST THE LOCK!");
            if(!this.canLeaveTheBus) this.passengerCondition.await();
            this.log("PASSENGER " + pid + " MADE IT PAST THE AWAIT CONDITION!");
            this.passengersThatLeftTheBus++;
            if(this.passengersThatLeftTheBus == this.passengersThatArrived) {
                this.log("PASSENGER " + pid + " IS ABOUT TO SIGNAL THE BUS DRIVER!");
                this.busDriverCondition.signal();
            }
            this.repositoryStub.passengerLeavingTheBus(pid, seat);
            this.log("PASSENGER " + pid + " MADE IT TO THE END OF THE METHOD!");
        } catch(Exception e) {
            this.log("DTTQ: leaveTheBus: " + e.toString());
        } finally {
            this.reentrantLock.unlock();
        }
        this.log("PASSENGER " + pid + " IS DONE WITH LEAVING THE BUS!");
    }
    /**
     * The bus driver parks the Bus and let's teh passengers off.
     * @param passengersThatArrived The number of passengers that arrived aboard the bus.
     * @param flightNumber The bus driver's estimated flight number.
     * @return The current flight number.
     */
    @Override
    public int parkTheBusAndLetPassOff(int passengersThatArrived, int flightNumber) {
        this.log("passengersThatArrived: " + passengersThatArrived);
        this.log("this.flightN: " + this.flightNumber + ", flightN: " + flightNumber);
        this.reentrantLock.lock();
        try {
            this.repositoryStub.busDriverParkingTheBusAndLettingPassengersOff();
            this.log("PTBALPO CHECKPOINT 1");
            this.passengersThatArrived = passengersThatArrived;
            this.log("PTBALPO CHECKPOINT 2");
            this.passengerCondition.signalAll();
            this.log("PTBALPO CHECKPOINT 3");
            this.canLeaveTheBus = true;
            this.log("PTBALPO CHECKPOINT 4");
            if(this.passengersThatLeftTheBus < this.passengersThatArrived && this.flightNumber == flightNumber) this.busDriverCondition.await();
            this.log("PTBALPO CHECKPOINT 5");
        } catch(Exception e) {
            this.log("DTTQ: parkTheBusAndLetPassOff: " + e.toString());
        } finally {
            this.reentrantLock.unlock();
        }
        this.log("PTBALPO CHECKPOINT 6");
        return this.flightNumber;
    }

    private void logStart() throws IOException {
        // open data stream to log file
        this.logFile = new File("logFile_DTTQ_" + System.nanoTime() + ".txt");
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
            this.log("DTTQ: log: " + e.toString());
        } finally {
            this.reentrantLock.unlock();
        }
    }
}
