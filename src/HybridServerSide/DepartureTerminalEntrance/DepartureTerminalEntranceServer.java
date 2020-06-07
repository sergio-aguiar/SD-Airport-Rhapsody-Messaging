package HybridServerSide.DepartureTerminalEntrance;

import HybridServerSide.BaggageCollectionPoint.BaggageCollectionPoint;
import HybridServerSide.BaggageCollectionPoint.BaggageCollectionPointInterface;
import HybridServerSide.BaggageCollectionPoint.BaggageCollectionPointProxy;
import HybridServerSide.ServerCom.ServerCom;
import genclass.GenericIO;

public class DepartureTerminalEntranceServer {

    private  static final int serverPort = 4005;

    public static void main(String[] args) {

        DepartureTerminalEntrance departureTerminalEntrance;
        DepartureTerminalEntranceInterface departureTerminalEntranceInterface;
        DepartureTerminalEntranceProxy departureTerminalEntranceProxy;

        ServerCom serverCom;
        ServerCom serverComL;

        int totalPassengers;

        serverCom = new ServerCom(serverPort);
        serverCom.start();

        // departureTerminalEntrance = new DepartureTerminalEntrance();
        // departureTerminalEntranceInterface = new DepartureTerminalEntranceInterface(departureTerminalEntrance);

        GenericIO.writelnString("DepartureTerminalEntranceServer now listening!");

        while(true) {
            serverComL = serverCom.accept();
            // departureTerminalEntranceProxy = new DepartureTerminalEntranceProxy(serverComL, departureTerminalEntranceInterface);
        }
    }

}
