package HybridServerSide.ArrivalLounge;

import ClientSide.Extras.Bag;
import HybridServerSide.ServerCom.ServerCom;
import genclass.GenericIO;

public class ArrivalLoungeServer {

    private  static final int serverPort = 4000;

    public static void main(String[] args) {

        ArrivalLounge arrivalLounge;
        ArrivalLoungeInterface arrivalLoungeInterface;
        ArrivalLoungeProxy arrivalLoungeProxy;

        ServerCom serverCom;
        ServerCom serverComL;

        int totalPassengers;
        int totalFlights;
        Bag[][][] luggagePerFlight;

        serverCom = new ServerCom(serverPort);
        serverCom.start();

        // arrivalLounge = new ArrivalLounge();
        //arrivalLoungeInterface = new ArrivalLoungeInterface(arrivalLounge);

        GenericIO.writelnString("ArrivalLoungeServer now listening!");

        while(true) {
            serverComL = serverCom.accept();
            // arrivalLoungeProxy = new ArrivalLoungeProxy(serverCom, arrivalLoungeInterface);
        }
    }

}
