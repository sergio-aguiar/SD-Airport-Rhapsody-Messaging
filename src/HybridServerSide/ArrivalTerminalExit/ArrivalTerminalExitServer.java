package HybridServerSide.ArrivalTerminalExit;

import HybridServerSide.ServerCom.ServerCom;
import HybridServerSide.Stubs.DepartureTerminalEntranceStub;
import HybridServerSide.Stubs.RepositoryStub;
import genclass.GenericIO;

import java.net.SocketTimeoutException;

/**
 * ArrivalTerminalExitServer: The ArrivalTerminalExit's server's main class.
 * @author sergioaguiar
 * @author marcomacedo
 */
public class ArrivalTerminalExitServer {
    /**
     * Variable that states whether the server is functional or not.
     */
    public static  boolean running;
    /**
     * The current server's listening port.
     */
    private  static final int serverPort = 4001;

    public static void main(String[] args) {

        ArrivalTerminalExit arrivalTerminalExit;
        ArrivalTerminalExitInterface arrivalTerminalExitInterface;
        ArrivalTerminalExitProxy arrivalTerminalExitProxy;

        RepositoryStub repositoryStub;
        DepartureTerminalEntranceStub departureTerminalEntranceStub;

        ServerCom serverCom;
        ServerCom serverComL;

        serverCom = new ServerCom(serverPort);
        serverCom.start();

        repositoryStub = new RepositoryStub("localhost", 4008);
        departureTerminalEntranceStub = new DepartureTerminalEntranceStub("localhost", 4005);

        arrivalTerminalExit = new ArrivalTerminalExit(repositoryStub, departureTerminalEntranceStub);
        arrivalTerminalExitInterface = new ArrivalTerminalExitInterface(arrivalTerminalExit);

        GenericIO.writelnString("ArrivalTerminalExitServer now listening!");

        running = true;
        while(running) {
            try{
                GenericIO.writelnString("STILL IN LOOP!");
                serverComL = serverCom.accept();
                arrivalTerminalExitProxy = new ArrivalTerminalExitProxy(serverComL, arrivalTerminalExitInterface);
                arrivalTerminalExitProxy.start();
                GenericIO.writelnString("END OF LOOP!");
            } catch (SocketTimeoutException ignored) {
            }
        }

        serverCom.end();
        GenericIO.writelnString("ArrivalTerminalExitServer stopped.");
    }

}
