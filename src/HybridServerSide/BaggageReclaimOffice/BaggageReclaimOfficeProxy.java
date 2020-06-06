package HybridServerSide.BaggageReclaimOffice;

import Communication.Message;
import Communication.MessageException;
import HybridServerSide.ArrivalLounge.ArrivalLoungeInterface;
import HybridServerSide.ServerCom.ServerCom;
import genclass.GenericIO;

import java.util.concurrent.locks.ReentrantLock;

public class BaggageReclaimOfficeProxy extends Thread {
    /**
     * The class's ReentrantLock instance.
     */
    private static final ReentrantLock reentrantLock = new ReentrantLock();

    private static int nProxy = 0;
    private ServerCom serverCom;
    private BaggageReclaimOfficeInterface baggageReclaimOfficeInterface;

    public BaggageReclaimOfficeProxy(ServerCom serverCom, BaggageReclaimOfficeInterface baggageReclaimOfficeInterface) {
        super("BROProxy_" + getProxyID());
        this.serverCom = serverCom;
        this.baggageReclaimOfficeInterface = baggageReclaimOfficeInterface;
    }

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

    private static int getProxyID()
    {
        Class<HybridServerSide.BaggageReclaimOffice.BaggageReclaimOfficeProxy> proxy = null;
        int proxyID = -1;

        try {
            proxy = (Class<HybridServerSide.BaggageReclaimOffice.BaggageReclaimOfficeProxy>) Class.forName("HybridServerSide.BaggageReclaimOffice.BaggageReclaimOfficeProxy");
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
            System.out.println("BROProxy: getProxyID: " + e.toString());
        } finally {
            reentrantLock.unlock();
        }

        return proxyID;
    }

}
