package HybridServerSide.TemporaryStorageArea;

import Communication.Message;
import Communication.MessageException;
import HybridServerSide.ServerCom.ServerCom;
import genclass.GenericIO;

import java.util.concurrent.locks.ReentrantLock;

/**
 * TemporaryStorageAreaProxy: TemporaryStorageArea server proxy.
 * Made from a class example.
 * @author sergioaguiar
 * @author marcomacedo
 */
public class TemporaryStorageAreaProxy extends Thread {
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
     * The class's TemporaryStorageAreaInterface instance.
     */
    private TemporaryStorageAreaInterface temporaryStorageAreaInterface;
    /**
     * Constructor: TemporaryStorageArea.
     * @param serverCom Proxy's server connection.
     * @param temporaryStorageAreaInterface The class's TemporaryStorageAreaInterface instance.
     */
    public TemporaryStorageAreaProxy(ServerCom serverCom, TemporaryStorageAreaInterface temporaryStorageAreaInterface) {
        super("TSAProxy_" + getProxyID());
        this.serverCom = serverCom;
        this.temporaryStorageAreaInterface = temporaryStorageAreaInterface;
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
            outMessage = temporaryStorageAreaInterface.processAndReply(inMessage);
        } catch(MessageException e) {
            GenericIO.writelnString("Thread " + getName() + ": " + e.getMessage() + "!");
            GenericIO.writelnString(e.getErrorMessage().toString ());
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
        Class<HybridServerSide.TemporaryStorageArea.TemporaryStorageAreaProxy> proxy = null;
        int proxyID = -1;

        try {
            proxy = (Class<HybridServerSide.TemporaryStorageArea.TemporaryStorageAreaProxy>)
                    Class.forName("HybridServerSide.TemporaryStorageArea.TemporaryStorageAreaProxy");
        } catch(ClassNotFoundException e) {
            GenericIO.writelnString("Data type TemporaryStorageAreaProxy was not found!");
            e.printStackTrace();
            System.exit(1);
        }

        reentrantLock.lock();
        try {
            proxyID = nProxy;
            nProxy += 1;
        } catch (Exception e) {
            GenericIO.writelnString("TSAProxy: getProxyID: " + e.toString());
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
