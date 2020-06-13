package HybridServerSide.ArrivalTerminalTransferQuay;

import Communication.Message;
import Communication.MessageException;
import HybridServerSide.ServerCom.ServerCom;
import genclass.GenericIO;

import java.util.concurrent.locks.ReentrantLock;

/**
 * ArrivalTerminalTransferQuayProxy: ArrivalTerminalTransferQuay server proxy.
 * Made from a class example.
 * @author sergioaguiar
 * @author marcomacedo
 */
public class ArrivalTerminalTransferQuayProxy extends Thread {
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
     * The class's ArrivalTerminalTransferQuayInterface instance.
     */
    private ArrivalTerminalTransferQuayInterface arrivalTerminalTransferQuayInterface;
    /**
     * Constructor: ArrivalTerminalTransferQuayProxy
     * @param serverCom Proxy's server connection.
     * @param arrivalTerminalTransferQuayInterface The class's ArrivalTerminalTransferQuayInterface instance.
     */
    public ArrivalTerminalTransferQuayProxy(ServerCom serverCom,
                                            ArrivalTerminalTransferQuayInterface arrivalTerminalTransferQuayInterface) {
        super("ATTQProxy_" + getProxyID());
        this.serverCom = serverCom;
        this.arrivalTerminalTransferQuayInterface = arrivalTerminalTransferQuayInterface;
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
            outMessage = arrivalTerminalTransferQuayInterface.processAndReply(inMessage);
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
        Class<HybridServerSide.ArrivalTerminalTransferQuay.ArrivalTerminalTransferQuayProxy> proxy = null;
        int proxyID = -1;

        try {
            proxy = (Class<HybridServerSide.ArrivalTerminalTransferQuay.ArrivalTerminalTransferQuayProxy>)
                    Class.forName("HybridServerSide.ArrivalTerminalTransferQuay.ArrivalTerminalTransferQuayProxy");
        } catch(ClassNotFoundException e) {
            GenericIO.writelnString("Data type ArrivalTerminalTransferQuayProxy was not found!");
            e.printStackTrace();
            System.exit(1);
        }

        reentrantLock.lock();
        try {
            proxyID = nProxy;
            nProxy += 1;
        } catch (Exception e) {
            GenericIO.writelnString("ATTQProxy: getProxyID: " + e.toString());
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
