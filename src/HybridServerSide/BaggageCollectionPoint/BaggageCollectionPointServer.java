package HybridServerSide.BaggageCollectionPoint;

import HybridServerSide.ServerCom.ServerCom;
import HybridServerSide.Stubs.RepositoryStub;
import genclass.GenericIO;

import java.net.SocketTimeoutException;


/**
 * BaggageCollectionPointServer: The BaggageCollectionPoint's server's main class.
 * @author sergioaguiar
 * @author marcomacedo
 */
public class BaggageCollectionPointServer {
    /**
     * Variable that states whether the server is functional or not.
     */
    public static  boolean running;
    /**
     * The current server's listening port.
     */
    private  static final int serverPort = 4003;

    public static void main(String[] args) {

        BaggageCollectionPoint baggageCollectionPoint;
        BaggageCollectionPointInterface baggageCollectionPointInterface;
        BaggageCollectionPointProxy baggageCollectionPointProxy;

        RepositoryStub repositoryStub;

        ServerCom serverCom;
        ServerCom serverComL;

        serverCom = new ServerCom(serverPort);
        serverCom.start();

        repositoryStub = new RepositoryStub("localhost", 4008);

        baggageCollectionPoint = new BaggageCollectionPoint(repositoryStub);
        baggageCollectionPointInterface = new BaggageCollectionPointInterface(baggageCollectionPoint);

        GenericIO.writelnString("BaggageCollectionPointServer now listening!");

        running = true;
        while(running) {
            try {
                serverComL = serverCom.accept();
                baggageCollectionPointProxy = new BaggageCollectionPointProxy(serverComL,
                        baggageCollectionPointInterface);
                baggageCollectionPointProxy.start();
            } catch (SocketTimeoutException ignored) {
            }
        }

        serverCom.end();
        GenericIO.writelnString("BaggageCollectionPointServer stopped.");
    }

}
