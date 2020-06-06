package HybridServerSide.Repository;

import Communication.Message;
import Communication.MessageException;
import HybridServerSide.ArrivalLounge.ArrivalLoungeInterface;
import HybridServerSide.ServerCom.ServerCom;
import genclass.GenericIO;

import java.util.concurrent.locks.ReentrantLock;

public class RepositoryProxy extends Thread {
    /**
     * The class's ReentrantLock instance.
     */
    private static final ReentrantLock reentrantLock = new ReentrantLock();

    private static int nProxy = 0;
    private ServerCom serverCom;
    private RepositoryInterface repositoryInterface;

    public RepositoryProxy(ServerCom serverCom, RepositoryInterface repositoryInterface) {
        super("ALProxy_" + getProxyID());
        this.serverCom = serverCom;
        this.repositoryInterface = repositoryInterface;
    }

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
            GenericIO.writelnString(e.getErrorMessage().toString ());
            System.exit(1);
        }
        serverCom.writeObject(outMessage);
        serverCom.close();
    }

    private static int getProxyID()
    {
        Class<HybridServerSide.Repository.RepositoryProxy> proxy = null;
        int proxyID = -1;

        try {
            proxy = (Class<HybridServerSide.Repository.RepositoryProxy>) Class.forName("HybridServerSide.Repository.RepositoryProxy");
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
            System.out.println("RepositoryProxy: getProxyID: " + e.toString());
        } finally {
            reentrantLock.unlock();
        }

        return proxyID;
    }

}
