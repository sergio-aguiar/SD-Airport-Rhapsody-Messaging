package HybridServerSide.ArrivalLounge;

import HybridServerSide.ServerCom.ServerCom;
import HybridServerSide.Stubs.RepositoryStub;
import genclass.GenericIO;

import java.net.SocketTimeoutException;

/**
 * ArrivalLoungeServer: The ArrivalLounge's server's main class.
 * @author sergioaguiar
 * @author marcomacedo
 */
public class ArrivalLoungeServer {
    /**
     * Variable that states whether the server is functional or not.
     */
    public static  boolean running;
    /**
     * The current server's listening port.
     */
    private  static final int serverPort = 4000;

    public static void main(String[] args) {

        ArrivalLounge arrivalLounge;
        ArrivalLoungeInterface arrivalLoungeInterface;
        ArrivalLoungeProxy arrivalLoungeProxy;

        RepositoryStub repositoryStub;

        ServerCom serverCom;
        ServerCom serverComL;

        serverCom = new ServerCom(serverPort);
        serverCom.start();

        repositoryStub = new RepositoryStub("localhost", 4008);

        arrivalLounge = new ArrivalLounge(repositoryStub);
        arrivalLoungeInterface = new ArrivalLoungeInterface(arrivalLounge);

        GenericIO.writelnString("ArrivalLoungeServer now listening!");

        running = true;
        while(running) {
            try {
                serverComL = serverCom.accept();
                arrivalLoungeProxy = new ArrivalLoungeProxy(serverComL, arrivalLoungeInterface);
                arrivalLoungeProxy.start();
            } catch (SocketTimeoutException ignored) {
            }
        }

        serverCom.end();
        GenericIO.writelnString("ArrivalLoungeServer stopped!");
    }

}
