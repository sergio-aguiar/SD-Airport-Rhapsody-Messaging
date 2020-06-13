package HybridServerSide.DepartureTerminalEntrance;

import HybridServerSide.ServerCom.ServerCom;
import HybridServerSide.Stubs.ArrivalTerminalExitStub;
import HybridServerSide.Stubs.RepositoryStub;
import genclass.GenericIO;

import java.net.SocketTimeoutException;

/**
 * DepartureTerminalEntranceServer: The DepartureTerminalEntrance's server's main class.
 * @author sergioaguiar
 * @author marcomacedo
 */
public class DepartureTerminalEntranceServer {
    /**
     * Variable that states whether the server is functional or not.
     */
    public static  boolean running;
    /**
     * The current server's listening port.
     */
    private  static final int serverPort = 4005;

    public static void main(String[] args) {

        DepartureTerminalEntrance departureTerminalEntrance;
        DepartureTerminalEntranceInterface departureTerminalEntranceInterface;
        DepartureTerminalEntranceProxy departureTerminalEntranceProxy;

        RepositoryStub repositoryStub;
        ArrivalTerminalExitStub arrivalTerminalExitStub;

        ServerCom serverCom;
        ServerCom serverComL;

        serverCom = new ServerCom(serverPort);
        serverCom.start();

        repositoryStub = new RepositoryStub("localhost", 4008);
        arrivalTerminalExitStub = new ArrivalTerminalExitStub("localhost", 4001);

        departureTerminalEntrance = new DepartureTerminalEntrance(repositoryStub, arrivalTerminalExitStub);
        departureTerminalEntranceInterface = new DepartureTerminalEntranceInterface(departureTerminalEntrance);

        GenericIO.writelnString("DepartureTerminalEntranceServer now listening!");

        running = true;
        while(running) {
            try {
                serverComL = serverCom.accept();
                departureTerminalEntranceProxy = new DepartureTerminalEntranceProxy(serverComL,
                        departureTerminalEntranceInterface);
                departureTerminalEntranceProxy.start();
            } catch (SocketTimeoutException ignored) {
            }
        }

        serverCom.end();
        GenericIO.writelnString("DepartureTerminalEntranceServer stopped.");
    }

}
