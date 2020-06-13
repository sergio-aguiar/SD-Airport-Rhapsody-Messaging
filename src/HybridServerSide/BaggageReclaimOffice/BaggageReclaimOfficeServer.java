package HybridServerSide.BaggageReclaimOffice;

import HybridServerSide.ServerCom.ServerCom;
import HybridServerSide.Stubs.RepositoryStub;
import genclass.GenericIO;

import java.net.SocketTimeoutException;

/**
 * BaggageReclaimOfficeServer: The BaggageReclaimOffice's server's main class.
 * @author sergioaguiar
 * @author marcomacedo
 */
public class BaggageReclaimOfficeServer {
    /**
     * Variable that states whether the server is functional or not.
     */
    public static  boolean running;
    /**
     * The current server's listening port.
     */
    private  static final int serverPort = 4004;

    public static void main(String[] args) {

        BaggageReclaimOffice baggageReclaimOffice;
        BaggageReclaimOfficeInterface baggageReclaimOfficeInterface;
        BaggageReclaimOfficeProxy baggageReclaimOfficeProxy;

        RepositoryStub repositoryStub;

        ServerCom serverCom;
        ServerCom serverComL;

        serverCom = new ServerCom(serverPort);
        serverCom.start();

        repositoryStub = new RepositoryStub("localhost", 4008);

        baggageReclaimOffice = new BaggageReclaimOffice(repositoryStub);
        baggageReclaimOfficeInterface = new BaggageReclaimOfficeInterface(baggageReclaimOffice);

        GenericIO.writelnString("BaggageReclaimOfficeServer now listening!");

        running = true;
        while(running) {
            try {
                serverComL = serverCom.accept();
                baggageReclaimOfficeProxy = new BaggageReclaimOfficeProxy(serverComL, baggageReclaimOfficeInterface);
                baggageReclaimOfficeProxy.start();
            } catch (SocketTimeoutException ignored) {
            }
        }

        serverCom.end();
        GenericIO.writelnString("BaggageReclaimOfficeServer stopped!");
    }

}
