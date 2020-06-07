package HybridServerSide.ArrivalTerminalTransferQuay;

import HybridServerSide.ArrivalTerminalExit.ArrivalTerminalExit;
import HybridServerSide.ArrivalTerminalExit.ArrivalTerminalExitInterface;
import HybridServerSide.ArrivalTerminalExit.ArrivalTerminalExitProxy;
import HybridServerSide.ServerCom.ServerCom;
import genclass.GenericIO;

public class ArrivalTerminalTransferQuayServer {

    private  static final int serverPort = 4002;

    public static void main(String[] args) {

        ArrivalTerminalTransferQuay arrivalTerminalTransferQuay;
        ArrivalTerminalTransferQuayInterface arrivalTerminalTransferQuayInterface;
        ArrivalTerminalTransferQuayProxy arrivalTerminalTransferQuayProxy;

        ServerCom serverCom;
        ServerCom serverComL;

        int totalPassengers;
        int busSeatNumber;

        serverCom = new ServerCom(serverPort);
        serverCom.start();

        // arrivalTerminalTransferQuay = new ArrivalTerminalTransferQuay();
        // arrivalTerminalTransferQuayInterface = new ArrivalTerminalTransferQuayInterface(arrivalTerminalTransferQuay);

        GenericIO.writelnString("ArrivalTerminalTransferQuayServer now listening!");

        while(true) {
            serverComL = serverCom.accept();
            // arrivalTerminalTransferQuayProxy = new ArrivalTerminalTransferQuayProxy(serverComL, arrivalTerminalTransferQuayInterface);
        }
    }

}
