package HybridServerSide.ArrivalTerminalExit;

import HybridServerSide.ArrivalLounge.ArrivalLounge;
import HybridServerSide.ArrivalLounge.ArrivalLoungeInterface;
import HybridServerSide.ArrivalLounge.ArrivalLoungeProxy;
import HybridServerSide.ServerCom.ServerCom;
import genclass.GenericIO;

public class ArrivalTerminalExitServer {

    private  static final int serverPort = 4001;

    public static void main(String[] args) {

        ArrivalTerminalExit arrivalTerminalExit;
        ArrivalTerminalExitInterface arrivalTerminalExitInterface;
        ArrivalTerminalExitProxy arrivalTerminalExitProxy;

        ServerCom serverCom;
        ServerCom serverComL;

        int totalPassengers;

        serverCom = new ServerCom(serverPort);
        serverCom.start();

        // arrivalTerminalExit = new ArrivalTerminalExit();
        // arrivalTerminalExitInterface = new ArrivalTerminalExitInterface(arrivalTerminalExit);

        GenericIO.writelnString("ArrivalTerminalExitServer now listening!");

        while(true) {
            serverComL = serverCom.accept();
            // arrivalTerminalExitProxy = new ArrivalTerminalExitProxy(serverComL, arrivalTerminalExitInterface);
        }
    }

}
