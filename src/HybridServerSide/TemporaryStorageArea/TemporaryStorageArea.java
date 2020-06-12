package HybridServerSide.TemporaryStorageArea;

import ClientSide.Interfaces.TSAPorter;
import HybridServerSide.Repository.Repository;
import HybridServerSide.Stubs.RepositoryStub;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Temporary Storage Area: Where the porter takes bags currently in transit.
 * Used by PORTER.
 * @author sergiaguiar
 * @author marcomacedo
 */
public class TemporaryStorageArea implements TSAPorter {
    /**
     * The class's ReentrantLock instance.
     */
    private final ReentrantLock reentrantLock;
    /**
     * ArrayList that contains all the bags currently in temporary storage.
     */
    private final ArrayList<Integer> tsaBags;
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
     * TemporaryStorageArea constructor.
     * @param repositoryStub A reference to a repository object.
     */
    public TemporaryStorageArea(RepositoryStub repositoryStub) {
        this.reentrantLock = new ReentrantLock(true);
        this.tsaBags = new ArrayList<>();
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
        this.tsaBags.clear();
    }
    /**
     * The Porter carries their held bag to the Temporary Storage Area.
     * @param bagID The porter's held bag's owner's ID.
     */
    @Override
    public void carryItToAppropriateStore(int bagID) {
        this.reentrantLock.lock();
        try {
            this.repositoryStub.porterCarryBagToTemporaryStorageArea();
            this.tsaBags.add(bagID);
        } catch (Exception e) {
            this.log("TSA: carryItToAppropriateStore: " + e.toString());
        } finally {
            this.reentrantLock.unlock();
        }
    }

    private void logStart() throws IOException {
        // open data stream to log file
        this.logFile = new File("logFile_TSA_" + System.nanoTime() + ".txt");
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
            this.log("TSA: log: " + e.toString());
        } finally {
            this.reentrantLock.unlock();
        }
    }
}
