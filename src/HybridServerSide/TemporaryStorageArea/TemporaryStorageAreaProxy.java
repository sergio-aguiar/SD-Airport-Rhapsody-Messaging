package HybridServerSide.TemporaryStorageArea;

import Communication.Message;
import Communication.MessageException;
import HybridServerSide.ArrivalLounge.ArrivalLoungeInterface;
import HybridServerSide.ServerCom.ServerCom;
import genclass.GenericIO;

import java.util.concurrent.locks.ReentrantLock;

public class TemporaryStorageAreaProxy extends Thread {
    /**
     * The class's ReentrantLock instance.
     */
    private static final ReentrantLock reentrantLock = new ReentrantLock();

    private static int nProxy = 0;
    private ServerCom serverCom;
    private TemporaryStorageAreaInterface temporaryStorageAreaInterface;

    public TemporaryStorageAreaProxy(ServerCom serverCom, TemporaryStorageAreaInterface temporaryStorageAreaInterface) {
        super("TSAProxy_" + getProxyID());
        this.serverCom = serverCom;
        this.temporaryStorageAreaInterface = temporaryStorageAreaInterface;
    }

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

    private static int getProxyID()
    {
        Class<HybridServerSide.TemporaryStorageArea.TemporaryStorageAreaProxy> proxy = null;
        int proxyID = -1;

        try {
            proxy = (Class<HybridServerSide.TemporaryStorageArea.TemporaryStorageAreaProxy>) Class.forName("HybridServerSide.TemporaryStorageArea.TemporaryStorageAreaProxy");
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
            System.out.println("TSAProxy: getProxyID: " + e.toString());
        } finally {
            reentrantLock.unlock();
        }

        return proxyID;
    }

}
