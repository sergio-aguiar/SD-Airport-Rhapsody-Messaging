package HybridServerSide.Repository;

import ClientSide.Extras.Bag;
import ClientSide.Passenger.PassengerThread;
import HybridServerSide.DepartureTerminalTransferQuay.DepartureTerminalTransferQuay;
import HybridServerSide.DepartureTerminalTransferQuay.DepartureTerminalTransferQuayInterface;
import HybridServerSide.DepartureTerminalTransferQuay.DepartureTerminalTransferQuayProxy;
import HybridServerSide.ServerCom.ServerCom;
import genclass.GenericIO;

public class RepositoryServer {

    private  static final int serverPort = 4008;

    public static void main(String[] args) {

        Repository repository;
        RepositoryInterface repositoryInterface;
        RepositoryProxy repositoryProxy;

        ServerCom serverCom;
        ServerCom serverComL;

        serverCom = new ServerCom(serverPort);
        serverCom.start();

        try {
            repository = new Repository();
            repositoryInterface = new RepositoryInterface(repository);

            GenericIO.writelnString("RepositoryServer now listening!");

            boolean running = true;
            while(running) {
                serverComL = serverCom.accept();
                repositoryProxy = new RepositoryProxy(serverComL, repositoryInterface);
                repositoryProxy.start();
            }
        } catch(Exception e) {
            System.out.print(e.toString());
        }

        serverCom.end();
        System.out.println("REPServer stopped.");
    }

}
