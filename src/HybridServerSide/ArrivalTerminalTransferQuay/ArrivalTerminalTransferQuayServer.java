package HybridServerSide.ArrivalTerminalTransferQuay;

import HybridServerSide.ArrivalTerminalExit.ArrivalTerminalExit;
import HybridServerSide.ArrivalTerminalExit.ArrivalTerminalExitInterface;
import HybridServerSide.ArrivalTerminalExit.ArrivalTerminalExitProxy;
import HybridServerSide.ServerCom.ServerCom;
import HybridServerSide.Stubs.ArrivalLoungeStub;
import HybridServerSide.Stubs.RepositoryStub;
import genclass.GenericIO;

public class ArrivalTerminalTransferQuayServer {

    private  static final int serverPort = 4002;

    public static void main(String[] args) {

        ArrivalTerminalTransferQuay arrivalTerminalTransferQuay;
        ArrivalTerminalTransferQuayInterface arrivalTerminalTransferQuayInterface;
        ArrivalTerminalTransferQuayProxy arrivalTerminalTransferQuayProxy;

        RepositoryStub repositoryStub;
        ArrivalLoungeStub arrivalLoungeStub;

        ServerCom serverCom;
        ServerCom serverComL;

        repositoryStub = new RepositoryStub("localhost", 4008);
        arrivalLoungeStub = new ArrivalLoungeStub("localhost", 4000);

        serverCom = new ServerCom(serverPort);
        serverCom.start();

        arrivalTerminalTransferQuay = new ArrivalTerminalTransferQuay(repositoryStub, arrivalLoungeStub);
        arrivalTerminalTransferQuayInterface = new ArrivalTerminalTransferQuayInterface(arrivalTerminalTransferQuay);

        GenericIO.writelnString("ArrivalTerminalTransferQuayServer now listening!");

        boolean running = true;
        while(running) {
            try {
                serverComL = serverCom.accept();
                arrivalTerminalTransferQuayProxy = new ArrivalTerminalTransferQuayProxy(serverComL, arrivalTerminalTransferQuayInterface);
                arrivalTerminalTransferQuayProxy.start();
            } catch (Exception e) {
                System.out.println(e.toString());
            }
        }

        serverCom.end();
        System.out.println("ATTQServer stopped.");
    }

}
