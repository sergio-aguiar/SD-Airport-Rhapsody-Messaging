package HybridServerSide.ArrivalTerminalExit;

import HybridServerSide.ArrivalLounge.ArrivalLounge;
import HybridServerSide.ArrivalLounge.ArrivalLoungeInterface;
import HybridServerSide.ArrivalLounge.ArrivalLoungeProxy;
import HybridServerSide.ServerCom.ServerCom;
import HybridServerSide.Stubs.DepartureTerminalEntranceStub;
import HybridServerSide.Stubs.RepositoryStub;
import genclass.GenericIO;

public class ArrivalTerminalExitServer {

    private  static final int serverPort = 4001;

    public static void main(String[] args) {

        ArrivalTerminalExit arrivalTerminalExit;
        ArrivalTerminalExitInterface arrivalTerminalExitInterface;
        ArrivalTerminalExitProxy arrivalTerminalExitProxy;

        RepositoryStub repositoryStub;
        DepartureTerminalEntranceStub departureTerminalEntranceStub;

        ServerCom serverCom;
        ServerCom serverComL;

        serverCom = new ServerCom(serverPort);
        serverCom.start();

        repositoryStub = new RepositoryStub("localhost", 4008);
        departureTerminalEntranceStub = new DepartureTerminalEntranceStub("localhost", 4005);

        arrivalTerminalExit = new ArrivalTerminalExit(repositoryStub, departureTerminalEntranceStub);
        arrivalTerminalExitInterface = new ArrivalTerminalExitInterface(arrivalTerminalExit);

        GenericIO.writelnString("ArrivalTerminalExitServer now listening!");

        boolean running = true;
        while(running) {
            try{
                serverComL = serverCom.accept();
                arrivalTerminalExitProxy = new ArrivalTerminalExitProxy(serverComL, arrivalTerminalExitInterface);
                arrivalTerminalExitProxy.start();
            } catch (Exception e) {
                System.out.println(e.toString());
            }
        }

        serverCom.end();
        System.out.println("ATEServer stopped.");
    }

}
