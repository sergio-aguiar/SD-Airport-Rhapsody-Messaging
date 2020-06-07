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

        int flightNumber;
        int numberOfPassengerLuggageAtThePlane;
        int busSeatNumber;
        int totalPassengers;
        PassengerThread.PassengerAndBagSituations[] passengerSituations;
        int[] passengerLuggageAtStart;
        Bag[][][] luggagePerFlight;

        serverCom = new ServerCom(serverPort);
        serverCom.start();

        // repository = new Repository(flightNumber, numberOfPassengerLuggageAtThePlane, busSeatNumber, totalPassengers, passengerSituations, passengerLuggageAtStart, luggagePerFlight);
        // repositoryInterface = new RepositoryInterface(repository);

        GenericIO.writelnString("RepositoryServer now listening!");

        while(true) {
            serverComL = serverCom.accept();
            // repositoryProxy = new RepositoryProxy(serverComL, repositoryInterface);
        }
    }

}
