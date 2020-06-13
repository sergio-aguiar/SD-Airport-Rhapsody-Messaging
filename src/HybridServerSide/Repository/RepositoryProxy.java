package HybridServerSide.Repository;

import Communication.Message;
import Communication.MessageException;
import HybridServerSide.ServerCom.ServerCom;
import genclass.GenericIO;

import java.util.concurrent.locks.ReentrantLock;

/**
 * RepositoryProxy: Repository server proxy.
 * Made from a class example.
 * @author sergioaguiar
 * @author marcomacedo
 */
public class RepositoryProxy extends Thread {
    /**
     * The class's ReentrantLock instance.
     */
    private static final ReentrantLock reentrantLock = new ReentrantLock();
    /**
     * Attribute that assists with proxy thread naming.
     */
    private static int nProxy = 0;
    /**
     * Proxy's server connection.
     */
    private ServerCom serverCom;
    /**
     * The class's RepositoryInterface instance.
     */
    private RepositoryInterface repositoryInterface;
    /**
     * Constructor: RepositoryProxy.
     * @param serverCom Proxy's server connection.
     * @param repositoryInterface The class's RepositoryInterface instance.
     */
    public RepositoryProxy(ServerCom serverCom, RepositoryInterface repositoryInterface) {
        super("REPProxy_" + getProxyID());
        this.serverCom = serverCom;
        this.repositoryInterface = repositoryInterface;
    }
    /**
     * This proxy's run (thread) override.
     */
    @Override
    public void run()
    {
        Message inMessage = null;
        Message outMessage = null;

        inMessage = (Message) serverCom.readObject();
        try {
            outMessage = repositoryInterface.processAndReply(inMessage);
        } catch(MessageException e) {
            GenericIO.writelnString("Thread " + getName() + ": " + e.getMessage() + "!");
            e.printStackTrace();
            System.exit(1);
        }
        serverCom.writeObject(outMessage);
        serverCom.close();
    }
    /**
     * Function that returns the next proxy's ID number.
     * @return The next proxy's ID number.
     */
    private static int getProxyID()
    {
        Class<HybridServerSide.Repository.RepositoryProxy> proxy = null;
        int proxyID = -1;

        try {
            proxy = (Class<HybridServerSide.Repository.RepositoryProxy>)
                    Class.forName("HybridServerSide.Repository.RepositoryProxy");
        } catch(ClassNotFoundException e) {
            GenericIO.writelnString("Data type RepositoryProxy was not found!");
            e.printStackTrace();
            System.exit(1);
        }

        reentrantLock.lock();
        try {
            proxyID = nProxy;
            nProxy += 1;
        } catch (Exception e) {
            GenericIO.writelnString("RepositoryProxy: getProxyID: " + e.toString());
        } finally {
            reentrantLock.unlock();
        }

        return proxyID;
    }
    /**
     * Getter method for serverCom.
     * @return Proxy's server connection.
     */
    public ServerCom getServerCom() {
        return serverCom;
    }
}
