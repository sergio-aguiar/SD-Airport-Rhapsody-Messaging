package HybridServerSide.DepartureTerminalEntrance;

import HybridServerSide.BaggageCollectionPoint.BaggageCollectionPoint;
import HybridServerSide.BaggageCollectionPoint.BaggageCollectionPointInterface;
import HybridServerSide.BaggageCollectionPoint.BaggageCollectionPointProxy;
import HybridServerSide.ServerCom.ServerCom;
import HybridServerSide.Stubs.ArrivalTerminalExitStub;
import HybridServerSide.Stubs.RepositoryStub;
import genclass.GenericIO;

public class DepartureTerminalEntranceServer {

    private  static final int serverPort = 4005;

    public static void main(String[] args) {

        DepartureTerminalEntrance departureTerminalEntrance;
        DepartureTerminalEntranceInterface departureTerminalEntranceInterface;
        DepartureTerminalEntranceProxy departureTerminalEntranceProxy;

        RepositoryStub repositoryStub;
        ArrivalTerminalExitStub arrivalTerminalExitStub;

        ServerCom serverCom;
        ServerCom serverComL;

        serverCom = new ServerCom(serverPort);
        serverCom.start();

        repositoryStub = new RepositoryStub("localhost", 4008);
        arrivalTerminalExitStub = new ArrivalTerminalExitStub("localhost", 4001);

        departureTerminalEntrance = new DepartureTerminalEntrance(repositoryStub, arrivalTerminalExitStub);
        departureTerminalEntranceInterface = new DepartureTerminalEntranceInterface(departureTerminalEntrance);

        GenericIO.writelnString("DepartureTerminalEntranceServer now listening!");

        boolean running = true;
        while(running) {
            try {
                serverComL = serverCom.accept();
                departureTerminalEntranceProxy = new DepartureTerminalEntranceProxy(serverComL, departureTerminalEntranceInterface);
                departureTerminalEntranceProxy.start();
            } catch (Exception e) {
                System.out.println(e.toString());
            }
        }

        serverCom.end();
        System.out.println("DTEServer stopped.");
    }

}
