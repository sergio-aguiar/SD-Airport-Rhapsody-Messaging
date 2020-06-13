package HybridServerSide.TemporaryStorageArea;

import ClientSide.Interfaces.TSAPorter;
import HybridServerSide.Stubs.RepositoryStub;
import genclass.GenericIO;

import java.util.ArrayList;
import java.util.concurrent.locks.ReentrantLock;

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
     * The class's RepositoryStub instance.
     */
    private final RepositoryStub repositoryStub;

    /**
     * Constructor: TemporaryStorageArea.
     * @param repositoryStub The class's RepositoryStub instance.
     */
    public TemporaryStorageArea(RepositoryStub repositoryStub) {
        this.reentrantLock = new ReentrantLock(true);
        this.tsaBags = new ArrayList<>();
        this.repositoryStub = repositoryStub;
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
            GenericIO.writelnString("TSA: carryItToAppropriateStore: " + e.toString());
            System.exit(1);
        } finally {
            this.reentrantLock.unlock();
        }
    }
}
