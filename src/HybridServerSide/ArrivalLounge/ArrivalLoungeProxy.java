package HybridServerSide.ArrivalLounge;

import Communication.Message;
import Communication.MessageException;
import HybridServerSide.ServerCom.ServerCom;
import genclass.GenericIO;

import java.util.concurrent.locks.ReentrantLock;

public class ArrivalLoungeProxy extends Thread {
    /**
     * The class's ReentrantLock instance.
     */
    private static final ReentrantLock reentrantLock = new ReentrantLock();

    private static int nProxy = 0;
    private ServerCom serverCom;
    private ArrivalLoungeInterface arrivalLoungeInterface;

    public ArrivalLoungeProxy(ServerCom serverCom, ArrivalLoungeInterface arrivalLoungeInterface) {
        super("ALProxy_" + getProxyID());
        this.serverCom = serverCom;
        this.arrivalLoungeInterface = arrivalLoungeInterface;
    }

    @Override
    public void run()
    {
        Message inMessage = null;
        Message outMessage = null;

        inMessage = (Message) serverCom.readObject();
        try {
            outMessage = arrivalLoungeInterface.processAndReply(inMessage);
        } catch(MessageException e) {
            GenericIO.writelnString("Thread " + getName() + ": " + e.getMessage() + "!");
            GenericIO.writelnString(e.getErrorMessage().toString ());
            System.exit(1);
        }
        serverCom.writeObject(outMessage);
        serverCom.close();
    }

    private static int getProxyID()
    {
        Class<ArrivalLoungeProxy> proxy = null;
        int proxyID = -1;

        try {
            proxy = (Class<ArrivalLoungeProxy>) Class.forName("HybridServerSide.ArrivalLounge.ArrivalLoungeProxy");
        } catch(ClassNotFoundException e) {
            GenericIO.writelnString("Data type ArrivalLoungeProxy was not found!");
            e.printStackTrace();
            System.exit(1);
        }

        reentrantLock.lock();
        try {
            proxyID = nProxy;
            nProxy += 1;
        } catch (Exception e) {
            System.out.println("ALProxy: getProxyID: " + e.toString());
        } finally {
            reentrantLock.unlock();
        }

        return proxyID;
    }

}
