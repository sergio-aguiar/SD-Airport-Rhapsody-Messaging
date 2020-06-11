package HybridServerSide.ArrivalLounge;

import ClientSide.Extras.Bag;
import HybridServerSide.ServerCom.ServerCom;
import HybridServerSide.Stubs.RepositoryStub;
import genclass.GenericIO;

public class ArrivalLoungeServer {

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

        boolean running = true;
        while(running) {
            try {
                serverComL = serverCom.accept();
                arrivalLoungeProxy = new ArrivalLoungeProxy(serverComL, arrivalLoungeInterface);
                arrivalLoungeProxy.start();
            } catch (Exception e) {
                System.out.println(e.toString());
            }
        }

        serverCom.end();
        System.out.println("ALServer stopped.");
    }

}
