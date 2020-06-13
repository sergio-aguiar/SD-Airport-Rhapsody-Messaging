package HybridServerSide.BaggageReclaimOffice;

import Communication.Message;
import Communication.MessageException;
import HybridServerSide.ServerCom.ServerCom;
import genclass.GenericIO;

import java.util.concurrent.locks.ReentrantLock;

/**
 * BaggageReclaimOfficeProxy: BaggageReclaimOffice server proxy.
 * Made from a class example.
 * @author sergioaguiar
 * @author marcomacedo
 */
public class BaggageReclaimOfficeProxy extends Thread {
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
     * The class's BaggageReclaimOfficeInterface instance.
     */
    private BaggageReclaimOfficeInterface baggageReclaimOfficeInterface;
    /**
     * Constructor: BaggageReclaimOffice.
     * @param serverCom  Proxy's server connection.
     * @param baggageReclaimOfficeInterface The class's BaggageReclaimOfficeInterface instance.
     */
    public BaggageReclaimOfficeProxy(ServerCom serverCom, BaggageReclaimOfficeInterface baggageReclaimOfficeInterface) {
        super("BROProxy_" + getProxyID());
        this.serverCom = serverCom;
        this.baggageReclaimOfficeInterface = baggageReclaimOfficeInterface;
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
            outMessage = baggageReclaimOfficeInterface.processAndReply(inMessage);
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
        Class<HybridServerSide.BaggageReclaimOffice.BaggageReclaimOfficeProxy> proxy = null;
        int proxyID = -1;

        try {
            proxy = (Class<HybridServerSide.BaggageReclaimOffice.BaggageReclaimOfficeProxy>)
                    Class.forName("HybridServerSide.BaggageReclaimOffice.BaggageReclaimOfficeProxy");
        } catch(ClassNotFoundException e) {
            GenericIO.writelnString("Data type BaggageReclaimOfficeProxy was not found!");
            e.printStackTrace();
            System.exit(1);
        }

        reentrantLock.lock();
        try {
            proxyID = nProxy;
            nProxy += 1;
        } catch (Exception e) {
            GenericIO.writelnString("BROProxy: getProxyID: " + e.toString());
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
