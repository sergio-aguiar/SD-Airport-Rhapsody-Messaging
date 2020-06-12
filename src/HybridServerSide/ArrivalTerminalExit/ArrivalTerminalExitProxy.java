package HybridServerSide.ArrivalTerminalExit;

import Communication.Message;
import Communication.MessageException;
import HybridServerSide.ServerCom.ServerCom;
import genclass.GenericIO;

import java.util.concurrent.locks.ReentrantLock;

public class ArrivalTerminalExitProxy extends Thread {
    /**
     * The class's ReentrantLock instance.
     */
    private static final ReentrantLock reentrantLock = new ReentrantLock();

    private static int nProxy = 0;
    private ServerCom serverCom;
    private ArrivalTerminalExitInterface arrivalTerminalExitInterface;

    public ArrivalTerminalExitProxy(ServerCom serverCom, ArrivalTerminalExitInterface arrivalTerminalExitInterface) {
        super("ATEProxy_" + getProxyID());
        this.serverCom = serverCom;
        this.arrivalTerminalExitInterface = arrivalTerminalExitInterface;
    }

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
        System.out.println("BEFORE WRITE!");
        serverCom.writeObject(outMessage);
        System.out.println("AFTER WRITE!");
        serverCom.close();
        System.out.println("AFTER CLOSE!");
    }

    private static int getProxyID()
    {
        Class<HybridServerSide.ArrivalTerminalExit.ArrivalTerminalExitProxy> proxy = null;
        int proxyID = -1;

        try {
            proxy = (Class<HybridServerSide.ArrivalTerminalExit.ArrivalTerminalExitProxy>) Class.forName("HybridServerSide.ArrivalTerminalExit.ArrivalTerminalExitProxy");
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
            System.out.println("ATEProxy: getProxyID: " + e.toString());
        } finally {
            reentrantLock.unlock();
        }

        return proxyID;
    }

    public ServerCom getServerCom() {
        return serverCom;
    }
}
