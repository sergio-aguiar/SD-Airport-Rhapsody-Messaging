package HybridServerSide.TemporaryStorageArea;

import HybridServerSide.ServerCom.ServerCom;
import HybridServerSide.Stubs.RepositoryStub;
import genclass.GenericIO;

import java.net.SocketTimeoutException;

/**
 * TemporaryStorageAreaServer: The TemporaryStorageArea's server's main class.
 * @author sergioaguiar
 * @author marcomacedo
 */
public class TemporaryStorageAreaServer {
    /**
     * Variable that states whether the server is functional or not.
     */
    public static  boolean running;
    /**
     * The current server's listening port.
     */
    private  static final int serverPort = 4007;

    public static void main(String[] args) {

        TemporaryStorageArea temporaryStorageArea;
        TemporaryStorageAreaInterface temporaryStorageAreaInterface;
        TemporaryStorageAreaProxy temporaryStorageAreaProxy;

        RepositoryStub repositoryStub;

        ServerCom serverCom;
        ServerCom serverComL;

        serverCom = new ServerCom(serverPort);
        serverCom.start();

        repositoryStub = new RepositoryStub("localhost", 4008);

        temporaryStorageArea = new TemporaryStorageArea(repositoryStub);
        temporaryStorageAreaInterface = new TemporaryStorageAreaInterface(temporaryStorageArea);

        GenericIO.writelnString("TemporaryStorageAreaServer now listening!");

        running = true;
        while(running) {
            try {
                serverComL = serverCom.accept();
                temporaryStorageAreaProxy = new TemporaryStorageAreaProxy(serverComL, temporaryStorageAreaInterface);
                temporaryStorageAreaProxy.start();
            } catch (SocketTimeoutException ignored) {
            }
        }

        serverCom.end();
        GenericIO.writelnString("TemporaryStorageAreaServer stopped.");
    }

}
