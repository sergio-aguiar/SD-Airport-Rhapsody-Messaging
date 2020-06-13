package HybridServerSide.Repository;

import HybridServerSide.ServerCom.ServerCom;
import genclass.GenericIO;

import java.io.IOException;
import java.net.SocketTimeoutException;

/**
 * RepositoryServer: The Repository's server's main class.
 * @author sergioaguiar
 * @author marcomacedo
 */
public class RepositoryServer {
    /**
     * Variable that states whether the server is functional or not.
     */
    public static  boolean running;
    /**
     * The current server's listening port.
     */
    private  static final int serverPort = 4008;

    public static void main(String[] args) {

        Repository repository;
        RepositoryInterface repositoryInterface;
        RepositoryProxy repositoryProxy;

        ServerCom serverCom;
        ServerCom serverComL;

        serverCom = new ServerCom(serverPort);
        serverCom.start();

        try {
            repository = new Repository();
            repositoryInterface = new RepositoryInterface(repository);

            GenericIO.writelnString("RepositoryServer now listening!");

            running = true;
            while(running) {
                serverComL = serverCom.accept();
                repositoryProxy = new RepositoryProxy(serverComL, repositoryInterface);
                repositoryProxy.start();
            }
        } catch (SocketTimeoutException ignored) {
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }

        serverCom.end();
        GenericIO.writelnString("RepositoryServer stopped!");
    }

}
