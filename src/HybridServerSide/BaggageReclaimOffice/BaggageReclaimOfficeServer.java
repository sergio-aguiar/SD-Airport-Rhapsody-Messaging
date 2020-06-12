package HybridServerSide.BaggageReclaimOffice;

import HybridServerSide.BaggageCollectionPoint.BaggageCollectionPoint;
import HybridServerSide.BaggageCollectionPoint.BaggageCollectionPointInterface;
import HybridServerSide.BaggageCollectionPoint.BaggageCollectionPointProxy;
import HybridServerSide.ServerCom.ServerCom;
import HybridServerSide.Stubs.RepositoryStub;
import genclass.GenericIO;

public class BaggageReclaimOfficeServer {

    public static  boolean running;
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
            } catch (Exception e) {
                System.out.println(e.toString());
            }
        }

        serverCom.end();
        System.out.println("BROServer stopped.");
    }

}
