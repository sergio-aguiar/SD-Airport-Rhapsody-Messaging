package HybridServerSide.ArrivalTerminalTransferQuay;

import Communication.Message;
import Communication.MessageException;
import HybridServerSide.ArrivalLounge.ArrivalLoungeInterface;
import HybridServerSide.ServerCom.ServerCom;
import genclass.GenericIO;

import java.util.concurrent.locks.ReentrantLock;

public class ArrivalTerminalTransferQuayProxy extends Thread {
    /**
     * The class's ReentrantLock instance.
     */
    private static final ReentrantLock reentrantLock = new ReentrantLock();

    private static int nProxy = 0;
    private ServerCom serverCom;
    private ArrivalTerminalTransferQuayInterface arrivalTerminalTransferQuayInterface;

    public ArrivalTerminalTransferQuayProxy(ServerCom serverCom, ArrivalTerminalTransferQuayInterface arrivalTerminalTransferQuayInterface) {
        super("ATTQProxy_" + getProxyID());
        this.serverCom = serverCom;
        this.arrivalTerminalTransferQuayInterface = arrivalTerminalTransferQuayInterface;
    }

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

    private static int getProxyID()
    {
        Class<HybridServerSide.ArrivalTerminalTransferQuay.ArrivalTerminalTransferQuayProxy> proxy = null;
        int proxyID = -1;

        try {
            proxy = (Class<HybridServerSide.ArrivalTerminalTransferQuay.ArrivalTerminalTransferQuayProxy>) Class.forName("HybridServerSide.ArrivalTerminalTransferQuay.ArrivalTerminalTransferQuayProxy");
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
            System.out.println("ATTQProxy: getProxyID: " + e.toString());
        } finally {
            reentrantLock.unlock();
        }

        return proxyID;
    }

    public ServerCom getServerCom() {
        return serverCom;
    }
}
