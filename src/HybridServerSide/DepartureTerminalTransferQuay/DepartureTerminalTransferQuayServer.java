package HybridServerSide.DepartureTerminalTransferQuay;

import HybridServerSide.DepartureTerminalEntrance.DepartureTerminalEntrance;
import HybridServerSide.DepartureTerminalEntrance.DepartureTerminalEntranceInterface;
import HybridServerSide.DepartureTerminalEntrance.DepartureTerminalEntranceProxy;
import HybridServerSide.ServerCom.ServerCom;
import HybridServerSide.Stubs.RepositoryStub;
import genclass.GenericIO;

public class DepartureTerminalTransferQuayServer {

    public static  boolean running;
    private  static final int serverPort = 4006;

    public static void main(String[] args) {

        DepartureTerminalTransferQuay departureTerminalTransferQuay;
        DepartureTerminalTransferQuayInterface departureTerminalTransferQuayInterface;
        DepartureTerminalTransferQuayProxy departureTerminalTransferQuayProxy;

        RepositoryStub repositoryStub;

        ServerCom serverCom;
        ServerCom serverComL;

        serverCom = new ServerCom(serverPort);
        serverCom.start();

        repositoryStub = new RepositoryStub("localhost", 4008);

        departureTerminalTransferQuay = new DepartureTerminalTransferQuay(repositoryStub);
        departureTerminalTransferQuayInterface = new DepartureTerminalTransferQuayInterface(departureTerminalTransferQuay);

        GenericIO.writelnString("DepartureTerminalTransferQuayServer now listening!");

        running = true;
        while(running) {
            try {
                serverComL = serverCom.accept();
                departureTerminalTransferQuayProxy = new DepartureTerminalTransferQuayProxy(serverComL, departureTerminalTransferQuayInterface);
                departureTerminalTransferQuayProxy.start();
            } catch (Exception e) {
                System.out.println(e.toString());
            }
        }

        serverCom.end();
        System.out.println("DTTQServer stopped.");
    }

}
