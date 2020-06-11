package HybridServerSide.TemporaryStorageArea;

import ClientSide.Extras.Bag;
import ClientSide.Passenger.PassengerThread;
import HybridServerSide.Repository.Repository;
import HybridServerSide.Repository.RepositoryInterface;
import HybridServerSide.Repository.RepositoryProxy;
import HybridServerSide.ServerCom.ServerCom;
import HybridServerSide.Stubs.RepositoryStub;
import genclass.GenericIO;

public class TemporaryStorageAreaServer {

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

        boolean running = true;
        while(running) {
            try {
                serverComL = serverCom.accept();
                temporaryStorageAreaProxy = new TemporaryStorageAreaProxy(serverComL, temporaryStorageAreaInterface);
                temporaryStorageAreaProxy.start();
            } catch (Exception e) {
                System.out.println(e.toString());
            }
        }

        serverCom.end();
        System.out.println("TSAServer stopped.");
    }

}
