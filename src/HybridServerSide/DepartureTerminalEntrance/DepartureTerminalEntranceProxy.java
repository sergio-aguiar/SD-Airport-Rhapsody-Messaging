package HybridServerSide.DepartureTerminalEntrance;

import Communication.Message;
import Communication.MessageException;
import HybridServerSide.ArrivalLounge.ArrivalLoungeInterface;
import HybridServerSide.ServerCom.ServerCom;
import genclass.GenericIO;

import java.util.concurrent.locks.ReentrantLock;

public class DepartureTerminalEntranceProxy extends Thread {
    /**
     * The class's ReentrantLock instance.
     */
    private static final ReentrantLock reentrantLock = new ReentrantLock();

    private static int nProxy = 0;
    private ServerCom serverCom;
    private DepartureTerminalEntranceInterface departureTerminalEntranceInterface;

    public DepartureTerminalEntranceProxy(ServerCom serverCom, DepartureTerminalEntranceInterface departureTerminalEntranceInterface) {
        super("DTEProxy_" + getProxyID());
        this.serverCom = serverCom;
        this.departureTerminalEntranceInterface = departureTerminalEntranceInterface;
    }

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

    private static int getProxyID()
    {
        Class<HybridServerSide.DepartureTerminalEntrance.DepartureTerminalEntranceProxy> proxy = null;
        int proxyID = -1;

        try {
            proxy = (Class<HybridServerSide.DepartureTerminalEntrance.DepartureTerminalEntranceProxy>) Class.forName("HybridServerSide.DepartureTerminalEntrance.DepartureTerminalEntranceProxy");
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
            System.out.println("DTEProxy: getProxyID: " + e.toString());
        } finally {
            reentrantLock.unlock();
        }

        return proxyID;
    }

}
