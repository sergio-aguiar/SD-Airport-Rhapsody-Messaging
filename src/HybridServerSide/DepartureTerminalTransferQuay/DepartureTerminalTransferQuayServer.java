package HybridServerSide.DepartureTerminalTransferQuay;

import HybridServerSide.ServerCom.ServerCom;
import HybridServerSide.Stubs.RepositoryStub;
import genclass.GenericIO;

import java.net.SocketTimeoutException;

/**
 * DepartureTerminalTransferQuayServer: The DepartureTerminalTransferQuay's server's main class.
 * @author sergioaguiar
 * @author marcomacedo
 */
public class DepartureTerminalTransferQuayServer {
    /**
     * Variable that states whether the server is functional or not.
     */
    public static  boolean running;
    /**
     * The current server's listening port.
     */
    private  static final int serverPort = 4006;

    public static void main(String[] args) {

        DepartureTerminalTransferQuay departureTerminalTransferQuay;
        DepartureTerminalTransferQuayInterface departureTerminalTransferQuayInterface;
        DepartureTerminalTransferQuayProxy departureTerminalTransferQuayProxy;

        RepositoryStub repositoryStub;

        ServerCom serverCom;
        ServerCom serverComL;

        serverCom = new ServerCom(serverPort);
        serverCom.start();

        repositoryStub = new RepositoryStub("localhost", 4008);

        departureTerminalTransferQuay = new DepartureTerminalTransferQuay(repositoryStub);
        departureTerminalTransferQuayInterface =
                new DepartureTerminalTransferQuayInterface(departureTerminalTransferQuay);

        GenericIO.writelnString("DepartureTerminalTransferQuayServer now listening!");

        running = true;
        while(running) {
            try {
                serverComL = serverCom.accept();
                departureTerminalTransferQuayProxy = new DepartureTerminalTransferQuayProxy(serverComL,
                        departureTerminalTransferQuayInterface);
                departureTerminalTransferQuayProxy.start();
            } catch (SocketTimeoutException ignored) {
            }
        }

        serverCom.end();
        GenericIO.writelnString("DepartureTerminalTransferQuayServer stopped!");
    }

}
