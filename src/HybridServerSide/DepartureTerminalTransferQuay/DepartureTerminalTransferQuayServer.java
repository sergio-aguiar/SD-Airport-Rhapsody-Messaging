package HybridServerSide.DepartureTerminalTransferQuay;

import HybridServerSide.DepartureTerminalEntrance.DepartureTerminalEntrance;
import HybridServerSide.DepartureTerminalEntrance.DepartureTerminalEntranceInterface;
import HybridServerSide.DepartureTerminalEntrance.DepartureTerminalEntranceProxy;
import HybridServerSide.ServerCom.ServerCom;
import genclass.GenericIO;

public class DepartureTerminalTransferQuayServer {

    private  static final int serverPort = 4006;

    public static void main(String[] args) {

        DepartureTerminalTransferQuay departureTerminalTransferQuay;
        DepartureTerminalTransferQuayInterface departureTerminalTransferQuayInterface;
        DepartureTerminalTransferQuayProxy departureTerminalTransferQuayProxy;

        ServerCom serverCom;
        ServerCom serverComL;

        serverCom = new ServerCom(serverPort);
        serverCom.start();

        // departureTerminalTransferQuay = new DepartureTerminalTransferQuay();
        // departureTerminalTransferQuayInterface = new DepartureTerminalTransferQuayInterface(departureTerminalTransferQuay);

        GenericIO.writelnString("DepartureTerminalTransferQuayServer now listening!");

        while(true) {
            serverComL = serverCom.accept();
            // departureTerminalTransferQuayProxy = new DepartureTerminalTransferQuayProxy(serverComL, departureTerminalTransferQuayInterface);
        }
    }

}
