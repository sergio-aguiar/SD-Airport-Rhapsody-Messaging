package HybridServerSide.BaggageReclaimOffice;

import HybridServerSide.BaggageCollectionPoint.BaggageCollectionPoint;
import HybridServerSide.BaggageCollectionPoint.BaggageCollectionPointInterface;
import HybridServerSide.BaggageCollectionPoint.BaggageCollectionPointProxy;
import HybridServerSide.ServerCom.ServerCom;
import genclass.GenericIO;

public class BaggageReclaimOfficeServer {

    private  static final int serverPort = 4004;

    public static void main(String[] args) {

        BaggageReclaimOffice baggageReclaimOffice;
        BaggageReclaimOfficeInterface baggageReclaimOfficeInterface;
        BaggageReclaimOfficeProxy baggageReclaimOfficeProxy;

        ServerCom serverCom;
        ServerCom serverComL;

        serverCom = new ServerCom(serverPort);
        serverCom.start();

        // baggageReclaimOffice = new BaggageReclaimOffice();
        // baggageReclaimOfficeInterface = new BaggageReclaimOfficeInterface(baggageReclaimOffice);

        GenericIO.writelnString("BaggageReclaimOfficeServer now listening!");

        while(true) {
            serverComL = serverCom.accept();
            // baggageReclaimOfficeProxy = new BaggageReclaimOfficeProxy(serverComL, baggageReclaimOfficeInterface);
        }
    }

}
