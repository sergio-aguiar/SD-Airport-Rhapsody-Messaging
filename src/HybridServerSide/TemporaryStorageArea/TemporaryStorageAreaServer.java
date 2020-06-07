package HybridServerSide.TemporaryStorageArea;

import ClientSide.Extras.Bag;
import ClientSide.Passenger.PassengerThread;
import HybridServerSide.Repository.Repository;
import HybridServerSide.Repository.RepositoryInterface;
import HybridServerSide.Repository.RepositoryProxy;
import HybridServerSide.ServerCom.ServerCom;
import genclass.GenericIO;

public class TemporaryStorageAreaServer {

    private  static final int serverPort = 4007;

    public static void main(String[] args) {

        TemporaryStorageArea temporaryStorageArea;
        TemporaryStorageAreaInterface temporaryStorageAreaInterface;
        TemporaryStorageAreaProxy temporaryStorageAreaProxy;

        ServerCom serverCom;
        ServerCom serverComL;

        serverCom = new ServerCom(serverPort);
        serverCom.start();

        // temporaryStorageArea = new TemporaryStorageArea();
        // temporaryStorageAreaInterface = new TemporaryStorageAreaInterface(temporaryStorageArea);

        GenericIO.writelnString("TemporaryStorageAreaServer now listening!");

        while(true) {
            serverComL = serverCom.accept();
            // temporaryStorageAreaProxy = new TemporaryStorageAreaProxy(serverComL, temporaryStorageAreaProxy;
        }
    }

}
