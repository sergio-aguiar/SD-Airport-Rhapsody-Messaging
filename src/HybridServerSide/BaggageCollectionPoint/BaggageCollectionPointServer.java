package HybridServerSide.BaggageCollectionPoint;

import HybridServerSide.ArrivalTerminalTransferQuay.ArrivalTerminalTransferQuay;
import HybridServerSide.ArrivalTerminalTransferQuay.ArrivalTerminalTransferQuayInterface;
import HybridServerSide.ArrivalTerminalTransferQuay.ArrivalTerminalTransferQuayProxy;
import HybridServerSide.ServerCom.ServerCom;
import genclass.GenericIO;

public class BaggageCollectionPointServer {

    private  static final int serverPort = 4003;

    public static void main(String[] args) {

        BaggageCollectionPoint baggageCollectionPoint;
        BaggageCollectionPointInterface baggageCollectionPointInterface;
        BaggageCollectionPointProxy baggageCollectionPointProxy;

        ServerCom serverCom;
        ServerCom serverComL;

        int totalPassengers;

        serverCom = new ServerCom(serverPort);
        serverCom.start();

        // baggageCollectionPoint = new BaggageCollectionPoint();
        // baggageCollectionPointInterface = new BaggageCollectionPointInterface(baggageCollectionPoint);

        GenericIO.writelnString("BaggageCollectionPointServer now listening!");

        while(true) {
            serverComL = serverCom.accept();
            // baggageCollectionPointProxy = new BaggageCollectionPointProxy(serverComL, baggageCollectionPointInterface);
        }
    }

}
