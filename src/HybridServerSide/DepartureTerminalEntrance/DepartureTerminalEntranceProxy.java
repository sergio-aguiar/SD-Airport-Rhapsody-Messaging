package HybridServerSide.DepartureTerminalEntrance;

import Communication.Message;
import Communication.MessageException;
import HybridServerSide.ServerCom.ServerCom;
import genclass.GenericIO;

import java.util.concurrent.locks.ReentrantLock;

/**
 * DepartureTerminalEntranceProxy: DepartureTerminalEntrance server proxy.
 * Made from a class example.
 * @author sergioaguiar
 * @author marcomacedo
 */
public class DepartureTerminalEntranceProxy extends Thread {
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
     * The class's DepartureTerminalEntranceInterface instance.
     */
    private DepartureTerminalEntranceInterface departureTerminalEntranceInterface;
    /**
     * Constructor: DepartureTerminalEntrance.
     * @param serverCom Proxy's server connection.
     * @param departureTerminalEntranceInterface The class's DepartureTerminalEntranceInterface instance.
     */
    public DepartureTerminalEntranceProxy(ServerCom serverCom,
                                          DepartureTerminalEntranceInterface departureTerminalEntranceInterface) {
        super("DTEProxy_" + getProxyID());
        this.serverCom = serverCom;
        this.departureTerminalEntranceInterface = departureTerminalEntranceInterface;
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
            outMessage = departureTerminalEntranceInterface.processAndReply(inMessage);
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
        Class<HybridServerSide.DepartureTerminalEntrance.DepartureTerminalEntranceProxy> proxy = null;
        int proxyID = -1;

        try {
            proxy = (Class<HybridServerSide.DepartureTerminalEntrance.DepartureTerminalEntranceProxy>)
                    Class.forName("HybridServerSide.DepartureTerminalEntrance.DepartureTerminalEntranceProxy");
        } catch(ClassNotFoundException e) {
            GenericIO.writelnString("Data type DepartureTerminalEntranceProxy was not found!");
            e.printStackTrace();
            System.exit(1);
        }

        reentrantLock.lock();
        try {
            proxyID = nProxy;
            nProxy += 1;
        } catch (Exception e) {
            GenericIO.writelnString("DTEProxy: getProxyID: " + e.toString());
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
