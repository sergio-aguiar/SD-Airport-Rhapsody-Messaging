package HybridServerSide.BaggageReclaimOffice;

import ClientSide.Interfaces.BROPassenger;
import HybridServerSide.Stubs.RepositoryStub;
import genclass.GenericIO;

import java.util.concurrent.locks.ReentrantLock;

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
     * The class's RepositoryStub instance.
     */
    private final RepositoryStub repositoryStub;
    /**
     * Constructor: BaggageReclaimOffice.
     * @param repositoryStub The class's RepositoryStub instance.
     */
    public BaggageReclaimOffice(RepositoryStub repositoryStub) {
        this.reentrantLock = new ReentrantLock(true);
        this.repositoryStub = repositoryStub;
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
            GenericIO.writelnString("BRO: reportMissingBags: " + e.toString());
            System.exit(1);
        } finally {
            this.reentrantLock.unlock();
        }
    }
}
