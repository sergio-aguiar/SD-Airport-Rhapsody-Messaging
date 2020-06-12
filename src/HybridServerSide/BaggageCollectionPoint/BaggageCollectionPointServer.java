package HybridServerSide.BaggageCollectionPoint;

import HybridServerSide.ArrivalTerminalTransferQuay.ArrivalTerminalTransferQuay;
import HybridServerSide.ArrivalTerminalTransferQuay.ArrivalTerminalTransferQuayInterface;
import HybridServerSide.ArrivalTerminalTransferQuay.ArrivalTerminalTransferQuayProxy;
import HybridServerSide.ServerCom.ServerCom;
import HybridServerSide.Stubs.RepositoryStub;
import genclass.GenericIO;

public class BaggageCollectionPointServer {

    public static  boolean running;
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
                baggageCollectionPointProxy = new BaggageCollectionPointProxy(serverComL, baggageCollectionPointInterface);
                baggageCollectionPointProxy.start();
            } catch (Exception e) {
                System.out.println(e.toString());
            }
        }

        serverCom.end();
        System.out.println("BCPServer stopped.");
    }

}
