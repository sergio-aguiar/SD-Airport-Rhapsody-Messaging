package HybridServerSide.ArrivalTerminalTransferQuay;

import HybridServerSide.ServerCom.ServerCom;
import HybridServerSide.Stubs.ArrivalLoungeStub;
import HybridServerSide.Stubs.RepositoryStub;
import genclass.GenericIO;

import java.net.SocketTimeoutException;

/**
 * ArrivalTerminalTransferQuayServer: The ArrivalTerminalTransferQuay's server's main class.
 * @author sergioaguiar
 * @author marcomacedo
 */
public class ArrivalTerminalTransferQuayServer {
    /**
     * Variable that states whether the server is functional or not.
     */
    public static  boolean running;
    /**
     * The current server's listening port.
     */
    private  static final int serverPort = 4002;

    public static void main(String[] args) {

        ArrivalTerminalTransferQuay arrivalTerminalTransferQuay;
        ArrivalTerminalTransferQuayInterface arrivalTerminalTransferQuayInterface;
        ArrivalTerminalTransferQuayProxy arrivalTerminalTransferQuayProxy;

        RepositoryStub repositoryStub;
        ArrivalLoungeStub arrivalLoungeStub;

        ServerCom serverCom;
        ServerCom serverComL;

        repositoryStub = new RepositoryStub("localhost", 4008);
        arrivalLoungeStub = new ArrivalLoungeStub("localhost", 4000);

        serverCom = new ServerCom(serverPort);
        serverCom.start();

        arrivalTerminalTransferQuay = new ArrivalTerminalTransferQuay(repositoryStub, arrivalLoungeStub);
        arrivalTerminalTransferQuayInterface = new ArrivalTerminalTransferQuayInterface(arrivalTerminalTransferQuay);

        GenericIO.writelnString("ArrivalTerminalTransferQuayServer now listening!");

        running = true;
        while(running) {
            try {
                serverComL = serverCom.accept();
                arrivalTerminalTransferQuayProxy = new ArrivalTerminalTransferQuayProxy(serverComL,
                        arrivalTerminalTransferQuayInterface);
                arrivalTerminalTransferQuayProxy.start();
            } catch (SocketTimeoutException ignored) {
            }
        }

        serverCom.end();
        GenericIO.writelnString("ArrivalTerminalTransferQuayServer stopped!");
    }

}
