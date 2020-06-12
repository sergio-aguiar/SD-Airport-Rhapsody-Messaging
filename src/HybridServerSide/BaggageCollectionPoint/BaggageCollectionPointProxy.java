package HybridServerSide.BaggageCollectionPoint;

import Communication.Message;
import Communication.MessageException;
import HybridServerSide.ArrivalLounge.ArrivalLoungeInterface;
import HybridServerSide.ServerCom.ServerCom;
import genclass.GenericIO;

import java.util.concurrent.locks.ReentrantLock;

public class BaggageCollectionPointProxy extends Thread {
    /**
     * The class's ReentrantLock instance.
     */
    private static final ReentrantLock reentrantLock = new ReentrantLock();

    private static int nProxy = 0;
    private ServerCom serverCom;
    private BaggageCollectionPointInterface baggageCollectionPointInterface;

    public BaggageCollectionPointProxy(ServerCom serverCom, BaggageCollectionPointInterface baggageCollectionPointInterface) {
        super("BGPProxy_" + getProxyID());
        this.serverCom = serverCom;
        this.baggageCollectionPointInterface = baggageCollectionPointInterface;
    }

    @Override
    public void run()
    {
        Message inMessage = null;
        Message outMessage = null;

        inMessage = (Message) serverCom.readObject();
        try {
            outMessage = baggageCollectionPointInterface.processAndReply(inMessage);
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
        Class<HybridServerSide.BaggageCollectionPoint.BaggageCollectionPointProxy> proxy = null;
        int proxyID = -1;

        try {
            proxy = (Class<HybridServerSide.BaggageCollectionPoint.BaggageCollectionPointProxy>) Class.forName("HybridServerSide.BaggageCollectionPoint.BaggageCollectionPointProxy");
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
            System.out.println("BCPProxy: getProxyID: " + e.toString());
        } finally {
            reentrantLock.unlock();
        }

        return proxyID;
    }

    public ServerCom getServerCom() {
        return serverCom;
    }
}
