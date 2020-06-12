package HybridServerSide.BaggageReclaimOffice;

import ClientSide.Interfaces.BROPassenger;
import HybridServerSide.Repository.Repository;
import HybridServerSide.Stubs.RepositoryStub;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;

/**Baggage Reclaim Office: Where passengers report missing bags.
 * Used by PASSENGER.
 * @author sergiaguiar
 * @author marcomacedo
 */
public class BaggageReclaimOffice implements BROPassenger {
    /**
     * The class's ReentrantLock instance.
     */
    private final ReentrantLock reentrantLock;
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
     * BaggageReclaimOffice constructor.
     * @param repositoryStub RepositoryStub.
     */
    public BaggageReclaimOffice(RepositoryStub repositoryStub) {
        this.reentrantLock = new ReentrantLock(true);
        this.repositoryStub = repositoryStub;

        try {
            this.logStart();
        } catch(Exception e) {
            this.log(e.toString());
        }
    }
    /**
     * Passengers report how many missing bags they have.
     * @param pid The passenger's ID.
     * @param missingBags The passenger's amount of missing bags.
     */
    @Override
    public void reportMissingBags(int pid, int missingBags) {
        this.reentrantLock.lock();
        try {
            this.repositoryStub.passengerReportingMissingBags(pid, missingBags);
        } catch (Exception e) {
            this.log("BRO: reportMissingBags: " + e.toString());
        } finally {
            this.reentrantLock.unlock();
        }
    }

    private void logStart() throws IOException {
        // open data stream to log file
        this.logFile = new File("logFile_BRO_" + System.nanoTime() + ".txt");
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
            this.log("BRO: log: " + e.toString());
        } finally {
            this.reentrantLock.unlock();
        }
    }

}
