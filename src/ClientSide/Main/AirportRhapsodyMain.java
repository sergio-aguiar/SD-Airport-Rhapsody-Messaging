package ClientSide.Main;

import ClientSide.BusDriver.BusDriverThread;
import ClientSide.Passenger.PassengerThread;
import ClientSide.Porter.PorterThread;
import ClientSide.Extras.Bag;
import ClientSide.Stubs.*;
import HybridServerSide.ArrivalLounge.ArrivalLounge;
import genclass.GenericIO;

import java.io.IOException;
import java.util.Arrays;
import java.util.Random;
/**
 * AirportRhapsodyMain : The project's main file.
 *
 * @author sergiaguiar
 * @author marcomacedo
 */
public class AirportRhapsodyMain {
    /**
     * The number of plane landings.
     */
    private static final int k = 5;
    /**
     * The number of passengers.
     */
    private static final int n = 6;
    /**
     * The maximum number of luggage a passenger can carry.
     */
    private static final int m = 2;
    /**
     * The number of seats on the bus.
     */
    private static final int t = 3;
    /**
     * The probability of a passenger currently being at their final destination.
     */
    private static final double probFDT = 0.5;
    /**
     * The array that contains every flight's passenger threads.
     */
    private static PassengerThread[][] passengers = new PassengerThread[k][n];
    /**
     * The array that contains every flight's passenger's flight situations.
     */
    private static final PassengerThread.PassengerAndBagSituations[][] passengerSituations
            = new PassengerThread.PassengerAndBagSituations[k][n];
    /**
     * The array that contains every flight's passenger's total amount of luggage.
     */
    private static final int[][] passengerLuggage = new int[k][n];
    /**
     * The array that contains every flight's passenger's total amount of luggage after some went missing.
     */
    private static final int[][] passengerLuggageAfterMissing = new int[k][n];
    /**
     * The array that contains every flight's passenger's bags.
     */
    private static Bag[][][] passengerBags = new Bag[k][n][m];
    /**
     * The array that contains every flight's amount of bags.
     */
    private static final int[] totalLuggagePerFlight = new int[k];

    private static final String serverHostName = "localhost";
    private static int ALServerPort = 4000;
    private static int ATEServerPort = 4001;
    private static int ATTQServerPort = 4002;
    private static int BCPServerPort = 4003;
    private static int BROServerPort = 4004;
    private static int DTEServerPort = 4005;
    private static int DTTQServerPort = 4006;
    private static int TSAServerPort = 4007;
    private static int REPServerPort = 4008;

    private static ArrivalLoungeStub arrivalLoungeStub;
    private static ArrivalTerminalExitStub arrivalTerminalExitStub;
    private static ArrivalTerminalTransferQuayStub arrivalTerminalTransferQuayStub;
    private static BaggageCollectionPointStub baggageCollectionPointStub;
    private static BaggageReclaimOfficeStub baggageReclaimOfficeStub;
    private static DepartureTerminalEntranceStub departureTerminalEntranceStub;
    private static DepartureTerminalTransferQuayStub departureTerminalTransferQuayStub;
    private static TemporaryStorageAreaStub temporaryStorageAreaStub;
    private static RepositoryStub repositoryStub;

    /**
     * The class's main function.
     * @param args The function's command line arguments (not used).
     */
    public static void main(String[] args) {

        repositoryStub = new RepositoryStub(serverHostName, REPServerPort);
        arrivalLoungeStub = new ArrivalLoungeStub(serverHostName, ALServerPort);
        arrivalTerminalExitStub = new ArrivalTerminalExitStub(serverHostName, ATEServerPort);
        arrivalTerminalTransferQuayStub = new ArrivalTerminalTransferQuayStub(serverHostName, ATTQServerPort);
        baggageCollectionPointStub = new BaggageCollectionPointStub(serverHostName, BCPServerPort);
        baggageReclaimOfficeStub = new BaggageReclaimOfficeStub(serverHostName, BROServerPort);
        departureTerminalEntranceStub = new DepartureTerminalEntranceStub(serverHostName, DTEServerPort);
        departureTerminalTransferQuayStub = new DepartureTerminalTransferQuayStub(serverHostName, DTTQServerPort);
        temporaryStorageAreaStub = new TemporaryStorageAreaStub(serverHostName, TSAServerPort);

        Arrays.fill(totalLuggagePerFlight, 0);
        generateStartingData();

/*        ArrivalLounge arrivalLoungeMonitor = new ArrivalLounge(repositoryMonitor, n, k, passengerBags);
        ArrivalTerminalExit arrivalTerminalExitMonitor = new ArrivalTerminalExit(repositoryMonitor, n);
        ArrivalTerminalTransferQuay arrivalTerminalTransferQuayMonitor = new ArrivalTerminalTransferQuay(repositoryMonitor, n, t, arrivalLoungeMonitor);
        BaggageCollectionPoint baggageCollectionPointMonitor = new BaggageCollectionPoint(repositoryMonitor, n);
        BaggageReclaimOffice baggageReclaimOfficeMonitor = new BaggageReclaimOffice(repositoryMonitor);
        DepartureTerminalEntrance departureTerminalEntranceMonitor = new DepartureTerminalEntrance(repositoryMonitor, n);
        DepartureTerminalTransferQuay departureTerminalTransferQuayMonitor = new DepartureTerminalTransferQuay(repositoryMonitor);
        TemporaryStorageArea temporaryStorageAreaMonitor = new TemporaryStorageArea(repositoryMonitor);

        arrivalTerminalExitMonitor.setDte(departureTerminalEntranceMonitor);
        departureTerminalEntranceMonitor.setAte(arrivalTerminalExitMonitor);*/

        BusDriverThread busDriver = new BusDriverThread(0, arrivalTerminalTransferQuayStub, departureTerminalTransferQuayStub);
        PorterThread porter = new PorterThread(0, arrivalLoungeStub, baggageCollectionPointStub, temporaryStorageAreaStub);

        for(int f = 0; f < k; f++) {
            for(int passenger = 0; passenger < n; passenger++) {
                passengers[f][passenger] = new PassengerThread(passenger, passengerLuggage[f][passenger],
                        passengerSituations[f][passenger], arrivalLoungeStub, arrivalTerminalExitStub,
                        arrivalTerminalTransferQuayStub, baggageCollectionPointStub, departureTerminalEntranceStub,
                        departureTerminalTransferQuayStub, baggageReclaimOfficeStub);
            }
        }

        busDriver.start();
        porter.start();

        for(int flight = 0; flight < k; flight++) {
            if(flight != 0) {
                repositoryStub.prepareForNextFlight(totalLuggagePerFlight[flight], passengerSituations[flight]);
                arrivalLoungeStub.prepareForNextFlight();
                arrivalTerminalExitStub.prepareForNextFlight();
                arrivalTerminalTransferQuayStub.prepareForNextFlight();
                baggageCollectionPointStub.prepareForNextFlight();
                departureTerminalEntranceStub.prepareForNextFlight();
                departureTerminalTransferQuayStub.prepareForNextFlight();
                temporaryStorageAreaStub.prepareForNextFlight();
            }

            for(PassengerThread passengerThread : passengers[flight]) passengerThread.start();

            try {
                for (PassengerThread passengerThread : passengers[flight]) passengerThread.join();
            } catch(InterruptedException e) {
                System.err.println("Main: Interrupted - joins1: " + e.toString());
            }
        }

        try {
            busDriver.join();
            porter.join();
        } catch(InterruptedException e) {
            System.out.println("Main: Interrupted - joins2: " + e.toString());
        }

        repositoryStub.finalReport();
    }
    /**
     * Function that pseudo-randomly generates the initial case for every flight.
     */
    private static void generateStartingData() {
        Random random = new Random();
        for(int flight = 0; flight < k; flight++) {
            for(int passenger = 0; passenger < n; passenger++) {
                passengerSituations[flight][passenger] = (random.nextDouble() < probFDT)
                        ? PassengerThread.PassengerAndBagSituations.FDT : PassengerThread.PassengerAndBagSituations.TRT;
                passengerLuggage[flight][passenger] = random.nextInt(m + 1);
                int bound = (passengerLuggage[flight][passenger] == 0) ? 1 : passengerLuggage[flight][passenger];
                passengerLuggageAfterMissing[flight][passenger] = passengerLuggage[flight][passenger]
                        - random.nextInt(bound);
                totalLuggagePerFlight[flight] += passengerLuggageAfterMissing[flight][passenger];

                for(int bag = 0; bag < passengerLuggageAfterMissing[flight][passenger]; bag++) {
                    passengerBags[flight][passenger][bag] = new Bag(passenger, passengerSituations[flight][passenger]);
                }
            }
        }
    }
}
