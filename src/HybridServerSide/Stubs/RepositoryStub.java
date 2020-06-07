package HybridServerSide.Stubs;

import ClientSide.ClientCom.ClientCom;
import Communication.Message;
import Communication.MessageException;
import genclass.GenericIO;

public class RepositoryStub {

    private String serverHostName;
    private int serverHostPort;

    public RepositoryStub(String hostName, int hostPort) {
        this.serverHostName = hostName;
        this.serverHostPort = hostPort;
    }

    public void porterInitiated() {
        ClientCom clientCom = new ClientCom (serverHostName, serverHostPort);
        Message inMessage;
        Message outMessage = null;

        while (!clientCom.open()) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                System.out.println("Thread " + Thread.currentThread().getName() + ": REPStub: porterInitiated: " + e.toString());
            }
        }

        try {
            outMessage = new Message(Message.MessageType.REP_PORTER_INITIATED.getMessageCode(), false, true);
        } catch(MessageException e) {
            System.out.println("Thread " + Thread.currentThread().getName() + ": REPStub: porterInitiated: " + e.toString());
        }

        clientCom.writeObject(outMessage);
        inMessage = (Message) clientCom.readObject();

        if(inMessage.getMessageType() != Message.MessageType.REP_PORTER_INITIATED.getMessageCode()) {
            GenericIO.writelnString("Thread " + Thread.currentThread().getName() + ": REPStub: porterInitiated: Incorrect message type!");
            GenericIO.writelnString(inMessage.toString());
            System.exit(1);
        }
        clientCom.close();
    }

    public void passengerInitiated(int pid) {
        ClientCom clientCom = new ClientCom (serverHostName, serverHostPort);
        Message inMessage;
        Message outMessage = null;

        while (!clientCom.open()) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                System.out.println("Thread " + Thread.currentThread().getName() + ": REPStub: passengerInitiated: " + e.toString());
            }
        }

        try {
            outMessage = new Message(Message.MessageType.REP_PASSENGER_INITIATED.getMessageCode(), false, true, (Object) pid);
        } catch(MessageException e) {
            System.out.println("Thread " + Thread.currentThread().getName() + ": REPStub: passengerInitiated: " + e.toString());
        }

        clientCom.writeObject(outMessage);
        inMessage = (Message) clientCom.readObject();

        if(inMessage.getMessageType() != Message.MessageType.REP_PASSENGER_INITIATED.getMessageCode()) {
            GenericIO.writelnString("Thread " + Thread.currentThread().getName() + ": REPStub: passengerInitiated: Incorrect message type!");
            GenericIO.writelnString(inMessage.toString());
            System.exit(1);
        }
        clientCom.close();
    }

    public void porterTryCollectingBagFromPlane(boolean success) {
        ClientCom clientCom = new ClientCom (serverHostName, serverHostPort);
        Message inMessage;
        Message outMessage = null;

        while (!clientCom.open()) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                System.out.println("Thread " + Thread.currentThread().getName() + ": REPStub: porterTryCollectingBagFromPlane: " + e.toString());
            }
        }

        try {
            outMessage = new Message(Message.MessageType.REP_PORTER_TRY_COLLECTING_BAG_FROM_PLANE.getMessageCode(), false, true, (Object) success);
        } catch(MessageException e) {
            System.out.println("Thread " + Thread.currentThread().getName() + ": REPStub: porterTryCollectingBagFromPlane: " + e.toString());
        }

        clientCom.writeObject(outMessage);
        inMessage = (Message) clientCom.readObject();

        if(inMessage.getMessageType() != Message.MessageType.REP_PORTER_TRY_COLLECTING_BAG_FROM_PLANE.getMessageCode()) {
            GenericIO.writelnString("Thread " + Thread.currentThread().getName() + ": REPStub: porterTryCollectingBagFromPlane: Incorrect message type!");
            GenericIO.writelnString(inMessage.toString());
            System.exit(1);
        }
        clientCom.close();
    }

    public void passengerGoingToCollectABag(int pid) {
        ClientCom clientCom = new ClientCom (serverHostName, serverHostPort);
        Message inMessage;
        Message outMessage = null;

        while (!clientCom.open()) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                System.out.println("Thread " + Thread.currentThread().getName() + ": REPStub: passengerGoingToCollectABag: " + e.toString());
            }
        }

        try {
            outMessage = new Message(Message.MessageType.REP_PASSENGER_GOING_TO_COLLECT_A_BAG.getMessageCode(), false, true, (Object) pid);
        } catch(MessageException e) {
            System.out.println("Thread " + Thread.currentThread().getName() + ": REPStub: passengerGoingToCollectABag: " + e.toString());
        }

        clientCom.writeObject(outMessage);
        inMessage = (Message) clientCom.readObject();

        if(inMessage.getMessageType() != Message.MessageType.REP_PASSENGER_GOING_TO_COLLECT_A_BAG.getMessageCode()) {
            GenericIO.writelnString("Thread " + Thread.currentThread().getName() + ": REPStub: passengerGoingToCollectABag: Incorrect message type!");
            GenericIO.writelnString(inMessage.toString());
            System.exit(1);
        }
        clientCom.close();
    }

    public void passengerEnteringTheBus(int pid, int seat) {
        ClientCom clientCom = new ClientCom (serverHostName, serverHostPort);
        Message inMessage;
        Message outMessage = null;

        while (!clientCom.open()) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                System.out.println("Thread " + Thread.currentThread().getName() + ": REPStub: passengerEnteringTheBus: " + e.toString());
            }
        }

        try {
            outMessage = new Message(Message.MessageType.REP_PASSENGER_ENTERING_THE_BUG.getMessageCode(), false, true, pid, seat);
        } catch(MessageException e) {
            System.out.println("Thread " + Thread.currentThread().getName() + ": REPStub: passengerEnteringTheBus: " + e.toString());
        }

        clientCom.writeObject(outMessage);
        inMessage = (Message) clientCom.readObject();

        if(inMessage.getMessageType() != Message.MessageType.REP_PASSENGER_ENTERING_THE_BUG.getMessageCode()) {
            GenericIO.writelnString("Thread " + Thread.currentThread().getName() + ": REPStub: passengerEnteringTheBus: Incorrect message type!");
            GenericIO.writelnString(inMessage.toString());
            System.exit(1);
        }
        clientCom.close();
    }

    public void passengerGoingHome(int pid) {
        ClientCom clientCom = new ClientCom (serverHostName, serverHostPort);
        Message inMessage;
        Message outMessage = null;

        while (!clientCom.open()) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                System.out.println("Thread " + Thread.currentThread().getName() + ": REPStub: passengerGoingHome: " + e.toString());
            }
        }

        try {
            outMessage = new Message(Message.MessageType.REP_PASSENGER_GOING_HOME.getMessageCode(), false, true, (Object) pid);
        } catch(MessageException e) {
            System.out.println("Thread " + Thread.currentThread().getName() + ": REPStub: passengerGoingHome: " + e.toString());
        }

        clientCom.writeObject(outMessage);
        inMessage = (Message) clientCom.readObject();

        if(inMessage.getMessageType() != Message.MessageType.REP_PASSENGER_GOING_HOME.getMessageCode()) {
            GenericIO.writelnString("Thread " + Thread.currentThread().getName() + ": REPStub: passengerGoingHome: Incorrect message type!");
            GenericIO.writelnString(inMessage.toString());
            System.exit(1);
        }
        clientCom.close();
    }

    public void busDriverInitiated() {
        ClientCom clientCom = new ClientCom (serverHostName, serverHostPort);
        Message inMessage;
        Message outMessage = null;

        while (!clientCom.open()) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                System.out.println("Thread " + Thread.currentThread().getName() + ": REPStub: busDriverInitiated: " + e.toString());
            }
        }

        try {
            outMessage = new Message(Message.MessageType.REP_BUS_DRIVER_INITIATED.getMessageCode(), false, true);
        } catch(MessageException e) {
            System.out.println("Thread " + Thread.currentThread().getName() + ": REPStub: busDriverInitiated: " + e.toString());
        }

        clientCom.writeObject(outMessage);
        inMessage = (Message) clientCom.readObject();

        if(inMessage.getMessageType() != Message.MessageType.REP_BUS_DRIVER_INITIATED.getMessageCode()) {
            GenericIO.writelnString("Thread " + Thread.currentThread().getName() + ": REPStub: busDriverInitiated: Incorrect message type!");
            GenericIO.writelnString(inMessage.toString());
            System.exit(1);
        }
        clientCom.close();
    }

    public void busDriverParkingTheBus() {
        ClientCom clientCom = new ClientCom (serverHostName, serverHostPort);
        Message inMessage;
        Message outMessage = null;

        while (!clientCom.open()) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                System.out.println("Thread " + Thread.currentThread().getName() + ": REPStub: busDriverParkingTheBus: " + e.toString());
            }
        }

        try {
            outMessage = new Message(Message.MessageType.REP_BUS_DRIVER_PARKING_THE_BUS.getMessageCode(), false, true);
        } catch(MessageException e) {
            System.out.println("Thread " + Thread.currentThread().getName() + ": REPStub: busDriverParkingTheBus: " + e.toString());
        }

        clientCom.writeObject(outMessage);
        inMessage = (Message) clientCom.readObject();

        if(inMessage.getMessageType() != Message.MessageType.REP_BUS_DRIVER_PARKING_THE_BUS.getMessageCode()) {
            GenericIO.writelnString("Thread " + Thread.currentThread().getName() + ": REPStub: busDriverParkingTheBus: Incorrect message type!");
            GenericIO.writelnString(inMessage.toString());
            System.exit(1);
        }
        clientCom.close();
    }

    public void passengerGettingIntoTheWaitingQueue(int pid, int position) {
        ClientCom clientCom = new ClientCom (serverHostName, serverHostPort);
        Message inMessage;
        Message outMessage = null;

        while (!clientCom.open()) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                System.out.println("Thread " + Thread.currentThread().getName() + ": REPStub: passengerGettingIntoTheWaitingQueue: " + e.toString());
            }
        }

        try {
            outMessage = new Message(Message.MessageType.REP_PASSENGER_GETTING_INTO_THE_WAITING_QUEUE.getMessageCode(), false, true, pid, position);
        } catch(MessageException e) {
            System.out.println("Thread " + Thread.currentThread().getName() + ": REPStub: passengerGettingIntoTheWaitingQueue: " + e.toString());
        }

        clientCom.writeObject(outMessage);
        inMessage = (Message) clientCom.readObject();

        if(inMessage.getMessageType() != Message.MessageType.REP_PASSENGER_GETTING_INTO_THE_WAITING_QUEUE.getMessageCode()) {
            GenericIO.writelnString("Thread " + Thread.currentThread().getName() + ": REPStub: passengerGettingIntoTheWaitingQueue: Incorrect message type!");
            GenericIO.writelnString(inMessage.toString());
            System.exit(1);
        }
        clientCom.close();
    }

    public void passengerTakingABus(int pid) {
        ClientCom clientCom = new ClientCom (serverHostName, serverHostPort);
        Message inMessage;
        Message outMessage = null;

        while (!clientCom.open()) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                System.out.println("Thread " + Thread.currentThread().getName() + ": REPStub: passengerTakingABus: " + e.toString());
            }
        }

        try {
            outMessage = new Message(Message.MessageType.REP_PASSENGER_GETTING_INTO_THE_WAITING_QUEUE.getMessageCode(), false, true, (Object) pid);
        } catch(MessageException e) {
            System.out.println("Thread " + Thread.currentThread().getName() + ": REPStub: passengerTakingABus: " + e.toString());
        }

        clientCom.writeObject(outMessage);
        inMessage = (Message) clientCom.readObject();

        if(inMessage.getMessageType() != Message.MessageType.REP_PASSENGER_GETTING_INTO_THE_WAITING_QUEUE.getMessageCode()) {
            GenericIO.writelnString("Thread " + Thread.currentThread().getName() + ": REPStub: passengerTakingABus: Incorrect message type!");
            GenericIO.writelnString(inMessage.toString());
            System.exit(1);
        }
        clientCom.close();
    }

    public void busDriverGoingToDepartureTerminal() {
        ClientCom clientCom = new ClientCom (serverHostName, serverHostPort);
        Message inMessage;
        Message outMessage = null;

        while (!clientCom.open()) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                System.out.println("Thread " + Thread.currentThread().getName() + ": REPStub: busDriverGoingToDepartureTerminal: " + e.toString());
            }
        }

        try {
            outMessage = new Message(Message.MessageType.REP_BUS_DRIVER_GOING_TO_DEPARTURE_TERMINAL.getMessageCode(), false, true);
        } catch(MessageException e) {
            System.out.println("Thread " + Thread.currentThread().getName() + ": REPStub: busDriverGoingToDepartureTerminal: " + e.toString());
        }

        clientCom.writeObject(outMessage);
        inMessage = (Message) clientCom.readObject();

        if(inMessage.getMessageType() != Message.MessageType.REP_BUS_DRIVER_GOING_TO_DEPARTURE_TERMINAL.getMessageCode()) {
            GenericIO.writelnString("Thread " + Thread.currentThread().getName() + ": REPStub: busDriverGoingToDepartureTerminal: Incorrect message type!");
            GenericIO.writelnString(inMessage.toString());
            System.exit(1);
        }
        clientCom.close();
    }

    public void passengerCollectingABag(int pid) {
        ClientCom clientCom = new ClientCom (serverHostName, serverHostPort);
        Message inMessage;
        Message outMessage = null;

        while (!clientCom.open()) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                System.out.println("Thread " + Thread.currentThread().getName() + ": REPStub: passengerCollectingABag: " + e.toString());
            }
        }

        try {
            outMessage = new Message(Message.MessageType.REP_PASSENGER_COLLECTING_A_BAG.getMessageCode(), false, true, (Object) pid);
        } catch(MessageException e) {
            System.out.println("Thread " + Thread.currentThread().getName() + ": REPStub: passengerCollectingABag: " + e.toString());
        }

        clientCom.writeObject(outMessage);
        inMessage = (Message) clientCom.readObject();

        if(inMessage.getMessageType() != Message.MessageType.REP_PASSENGER_COLLECTING_A_BAG.getMessageCode()) {
            GenericIO.writelnString("Thread " + Thread.currentThread().getName() + ": REPStub: passengerCollectingABag: Incorrect message type!");
            GenericIO.writelnString(inMessage.toString());
            System.exit(1);
        }
        clientCom.close();
    }

    public void porterCarryBagToBaggageCollectionPoint() {
        ClientCom clientCom = new ClientCom (serverHostName, serverHostPort);
        Message inMessage;
        Message outMessage = null;

        while (!clientCom.open()) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                System.out.println("Thread " + Thread.currentThread().getName() + ": REPStub: porterCarryBagToBaggageCollectionPoint: " + e.toString());
            }
        }

        try {
            outMessage = new Message(Message.MessageType.REP_PORTER_CARRY_BAG_TO_BAGGAGE_COLLECTION_POINT.getMessageCode(), false, true);
        } catch(MessageException e) {
            System.out.println("Thread " + Thread.currentThread().getName() + ": REPStub: porterCarryBagToBaggageCollectionPoint: " + e.toString());
        }

        clientCom.writeObject(outMessage);
        inMessage = (Message) clientCom.readObject();

        if(inMessage.getMessageType() != Message.MessageType.REP_PORTER_CARRY_BAG_TO_BAGGAGE_COLLECTION_POINT.getMessageCode()) {
            GenericIO.writelnString("Thread " + Thread.currentThread().getName() + ": REPStub: porterCarryBagToBaggageCollectionPoint: Incorrect message type!");
            GenericIO.writelnString(inMessage.toString());
            System.exit(1);
        }
        clientCom.close();
    }

    public void porterAnnouncingNoMoreBagsToCollect() {
        ClientCom clientCom = new ClientCom (serverHostName, serverHostPort);
        Message inMessage;
        Message outMessage = null;

        while (!clientCom.open()) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                System.out.println("Thread " + Thread.currentThread().getName() + ": REPStub: porterAnnouncingNoMoreBagsToCollect: " + e.toString());
            }
        }

        try {
            outMessage = new Message(Message.MessageType.REP_PORTER_ANNOUNCING_NO_MORE_BAGS_TO_COLLECT.getMessageCode(), false, true);
        } catch(MessageException e) {
            System.out.println("Thread " + Thread.currentThread().getName() + ": REPStub: porterAnnouncingNoMoreBagsToCollect: " + e.toString());
        }

        clientCom.writeObject(outMessage);
        inMessage = (Message) clientCom.readObject();

        if(inMessage.getMessageType() != Message.MessageType.REP_PORTER_ANNOUNCING_NO_MORE_BAGS_TO_COLLECT.getMessageCode()) {
            GenericIO.writelnString("Thread " + Thread.currentThread().getName() + ": REPStub: porterAnnouncingNoMoreBagsToCollect: Incorrect message type!");
            GenericIO.writelnString(inMessage.toString());
            System.exit(1);
        }
        clientCom.close();
    }

    public void passengerReportingMissingBags(int pid, int missingBags) {
        ClientCom clientCom = new ClientCom (serverHostName, serverHostPort);
        Message inMessage;
        Message outMessage = null;

        while (!clientCom.open()) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                System.out.println("Thread " + Thread.currentThread().getName() + ": REPStub: passengerReportingMissingBags: " + e.toString());
            }
        }

        try {
            outMessage = new Message(Message.MessageType.REP_PASSENGER_REPORTING_MISSING_BAGS.getMessageCode(), false, true, pid, missingBags);
        } catch(MessageException e) {
            System.out.println("Thread " + Thread.currentThread().getName() + ": REPStub: passengerReportingMissingBags: " + e.toString());
        }

        clientCom.writeObject(outMessage);
        inMessage = (Message) clientCom.readObject();

        if(inMessage.getMessageType() != Message.MessageType.REP_PASSENGER_REPORTING_MISSING_BAGS.getMessageCode()) {
            GenericIO.writelnString("Thread " + Thread.currentThread().getName() + ": REPStub: passengerReportingMissingBags: Incorrect message type!");
            GenericIO.writelnString(inMessage.toString());
            System.exit(1);
        }
        clientCom.close();
    }

    public void passengerPreparingNextLeg(int pid) {
        ClientCom clientCom = new ClientCom (serverHostName, serverHostPort);
        Message inMessage;
        Message outMessage = null;

        while (!clientCom.open()) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                System.out.println("Thread " + Thread.currentThread().getName() + ": REPStub: passengerPreparingNextLeg: " + e.toString());
            }
        }

        try {
            outMessage = new Message(Message.MessageType.REP_PASSENGER_PREPARING_NEXT_LEG.getMessageCode(), false, true, (Object) pid);
        } catch(MessageException e) {
            System.out.println("Thread " + Thread.currentThread().getName() + ": REPStub: passengerPreparingNextLeg: " + e.toString());
        }

        clientCom.writeObject(outMessage);
        inMessage = (Message) clientCom.readObject();

        if(inMessage.getMessageType() != Message.MessageType.REP_PASSENGER_PREPARING_NEXT_LEG.getMessageCode()) {
            GenericIO.writelnString("Thread " + Thread.currentThread().getName() + ": REPStub: passengerPreparingNextLeg: Incorrect message type!");
            GenericIO.writelnString(inMessage.toString());
            System.exit(1);
        }
        clientCom.close();
    }

    public void busDriverGoingToArrivalTerminal() {
        ClientCom clientCom = new ClientCom (serverHostName, serverHostPort);
        Message inMessage;
        Message outMessage = null;

        while (!clientCom.open()) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                System.out.println("Thread " + Thread.currentThread().getName() + ": REPStub: busDriverGoingToArrivalTerminal: " + e.toString());
            }
        }

        try {
            outMessage = new Message(Message.MessageType.REP_BUS_DRIVER_GOING_TO_ARRIVAL_TERMINAL.getMessageCode(), false, true);
        } catch(MessageException e) {
            System.out.println("Thread " + Thread.currentThread().getName() + ": REPStub: busDriverGoingToArrivalTerminal: " + e.toString());
        }

        clientCom.writeObject(outMessage);
        inMessage = (Message) clientCom.readObject();

        if(inMessage.getMessageType() != Message.MessageType.REP_BUS_DRIVER_GOING_TO_ARRIVAL_TERMINAL.getMessageCode()) {
            GenericIO.writelnString("Thread " + Thread.currentThread().getName() + ": REPStub: busDriverGoingToArrivalTerminal: Incorrect message type!");
            GenericIO.writelnString(inMessage.toString());
            System.exit(1);
        }
        clientCom.close();
    }

    public void passengerLeavingTheBus(int pid, int seat) {
        ClientCom clientCom = new ClientCom (serverHostName, serverHostPort);
        Message inMessage;
        Message outMessage = null;

        while (!clientCom.open()) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                System.out.println("Thread " + Thread.currentThread().getName() + ": REPStub: passengerLeavingTheBus: " + e.toString());
            }
        }

        try {
            outMessage = new Message(Message.MessageType.REP_PASSENGER_LEAVING_THE_BUS.getMessageCode(), false, true, pid, seat);
        } catch(MessageException e) {
            System.out.println("Thread " + Thread.currentThread().getName() + ": REPStub: passengerLeavingTheBus: " + e.toString());
        }

        clientCom.writeObject(outMessage);
        inMessage = (Message) clientCom.readObject();

        if(inMessage.getMessageType() != Message.MessageType.REP_PASSENGER_LEAVING_THE_BUS.getMessageCode()) {
            GenericIO.writelnString("Thread " + Thread.currentThread().getName() + ": REPStub: passengerLeavingTheBus: Incorrect message type!");
            GenericIO.writelnString(inMessage.toString());
            System.exit(1);
        }
        clientCom.close();
    }

    public void busDriverParkingTheBusAndLettingPassengersOff() {
        ClientCom clientCom = new ClientCom (serverHostName, serverHostPort);
        Message inMessage;
        Message outMessage = null;

        while (!clientCom.open()) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                System.out.println("Thread " + Thread.currentThread().getName() + ": REPStub: busDriverParkingTheBusAndLettingPassengersOff: " + e.toString());
            }
        }

        try {
            outMessage = new Message(Message.MessageType.REP_BUS_DRIVER_PARKING_THE_BUS_AND_LETTING_PASS_OFF.getMessageCode(), false, true);
        } catch(MessageException e) {
            System.out.println("Thread " + Thread.currentThread().getName() + ": REPStub: busDriverParkingTheBusAndLettingPassengersOff: " + e.toString());
        }

        clientCom.writeObject(outMessage);
        inMessage = (Message) clientCom.readObject();

        if(inMessage.getMessageType() != Message.MessageType.REP_BUS_DRIVER_PARKING_THE_BUS_AND_LETTING_PASS_OFF.getMessageCode()) {
            GenericIO.writelnString("Thread " + Thread.currentThread().getName() + ": REPStub: busDriverParkingTheBusAndLettingPassengersOff: Incorrect message type!");
            GenericIO.writelnString(inMessage.toString());
            System.exit(1);
        }
        clientCom.close();
    }

    public void porterCarryBagToTemporaryStorageArea() {
        ClientCom clientCom = new ClientCom (serverHostName, serverHostPort);
        Message inMessage;
        Message outMessage = null;

        while (!clientCom.open()) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                System.out.println("Thread " + Thread.currentThread().getName() + ": REPStub: porterCarryBagToTemporaryStorageArea: " + e.toString());
            }
        }

        try {
            outMessage = new Message(Message.MessageType.REP_PORTER_CARRY_BAG_TO_TEMPORARY_STORAGE_AREA.getMessageCode(), false, true);
        } catch(MessageException e) {
            System.out.println("Thread " + Thread.currentThread().getName() + ": REPStub: porterCarryBagToTemporaryStorageArea: " + e.toString());
        }

        clientCom.writeObject(outMessage);
        inMessage = (Message) clientCom.readObject();

        if(inMessage.getMessageType() != Message.MessageType.REP_PORTER_CARRY_BAG_TO_TEMPORARY_STORAGE_AREA.getMessageCode()) {
            GenericIO.writelnString("Thread " + Thread.currentThread().getName() + ": REPStub: porterCarryBagToTemporaryStorageArea: Incorrect message type!");
            GenericIO.writelnString(inMessage.toString());
            System.exit(1);
        }
        clientCom.close();
    }
}
