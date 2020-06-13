package HybridServerSide.ArrivalTerminalExit;

import Communication.Message;
import Communication.MessageException;
import HybridServerSide.ServerCom.ServerCom;
import genclass.GenericIO;

import java.util.concurrent.locks.ReentrantLock;

/**
 * ArrivalTerminalExitProxy: ArrivalTerminalExit server proxy.
 * Made from a class example.
 * @author sergioaguiar
 * @author marcomacedo
 */
public class ArrivalTerminalExitProxy extends Thread {
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
     *  The class'sArrivalTerminalExitInterface instance.
     */
    private ArrivalTerminalExitInterface arrivalTerminalExitInterface;
    /**
     * Constructor: ArrivalTerminalExitProxy.
     * @param serverCom Proxy's server connection.
     * @param arrivalTerminalExitInterface The class'sArrivalTerminalExitInterface instance.
     */
    public ArrivalTerminalExitProxy(ServerCom serverCom, ArrivalTerminalExitInterface arrivalTerminalExitInterface) {
        super("ATEProxy_" + getProxyID());
        this.serverCom = serverCom;
        this.arrivalTerminalExitInterface = arrivalTerminalExitInterface;
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
            outMessage = arrivalTerminalExitInterface.processAndReply(inMessage);
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
        Class<HybridServerSide.ArrivalTerminalExit.ArrivalTerminalExitProxy> proxy = null;
        int proxyID = -1;

        try {
            proxy = (Class<HybridServerSide.ArrivalTerminalExit.ArrivalTerminalExitProxy>)
                    Class.forName("HybridServerSide.ArrivalTerminalExit.ArrivalTerminalExitProxy");
        } catch(ClassNotFoundException e) {
            GenericIO.writelnString("Data type ArrivalTerminalExitProxy was not found!");
            e.printStackTrace();
            System.exit(1);
        }

        reentrantLock.lock();
        try {
            proxyID = nProxy;
            nProxy += 1;
        } catch (Exception e) {
            GenericIO.writelnString("ATEProxy: getProxyID: " + e.toString());
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
