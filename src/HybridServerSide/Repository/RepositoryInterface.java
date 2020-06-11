package HybridServerSide.Repository;

import ClientSide.Extras.Bag;
import ClientSide.Passenger.PassengerThread;
import Communication.Message;
import Communication.MessageException;
import HybridServerSide.ArrivalLounge.ArrivalLounge;

public class RepositoryInterface {

    private Repository repository;

    public RepositoryInterface(Repository repository) {
        this.repository = repository;
    }

    public Message processAndReply(Message inMessage) throws MessageException
    {
        Message outMessage = null;

        switch(inMessage.getMessageType()) {
            case 33:
            case 39:
            case 40:
            case 43:
            case 45:
            case 46:
            case 49:
            case 51:
            case 52:
                break;
            case 34:
            case 36:
            case 38:
            case 42:
            case 44:
            case 48:
                if(inMessage.isThereNoFirstArgument())
                    throw new MessageException("Argument \"pid\" not supplied.", inMessage);
                if(((int) inMessage.getFirstArgument()) < 0)
                    throw new MessageException("Argument \"pid\" was given an incorrect value.", inMessage);
                break;
            case 35:
                if(inMessage.isThereNoFirstArgument())
                    throw new MessageException("Argument \"success\" not supplied.", inMessage);
                break;
            case 37:
            case 50:
                if(inMessage.isThereNoFirstArgument())
                    throw new MessageException("Argument \"pid\" not supplied.", inMessage);
                if(((int) inMessage.getFirstArgument()) < 0)
                    throw new MessageException("Argument \"pid\" was given an incorrect value.", inMessage);
                if(inMessage.isThereNoSecondArgument())
                    throw new MessageException("Argument \"seat\" not supplied.", inMessage);
                if(((int) inMessage.getSecondArgument()) < 0)
                    throw new MessageException("Argument \"seat\" was given an incorrect value.", inMessage);
                break;
            case 41:
                if(inMessage.isThereNoFirstArgument())
                    throw new MessageException("Argument \"pid\" not supplied.", inMessage);
                if(((int) inMessage.getFirstArgument()) < 0)
                    throw new MessageException("Argument \"pid\" was given an incorrect value.", inMessage);
                if(inMessage.isThereNoSecondArgument())
                    throw new MessageException("Argument \"position\" not supplied.", inMessage);
                if(((int) inMessage.getSecondArgument()) < 0)
                    throw new MessageException("Argument \"position\" was given an incorrect value.", inMessage);
                break;
            case 47:
                if(inMessage.isThereNoFirstArgument())
                    throw new MessageException("Argument \"pid\" not supplied.", inMessage);
                if(((int) inMessage.getFirstArgument()) < 0)
                    throw new MessageException("Argument \"pid\" was given an incorrect value.", inMessage);
                if(inMessage.isThereNoSecondArgument())
                    throw new MessageException("Argument \"missingBags\" not supplied.", inMessage);
                if(((int) inMessage.getSecondArgument()) < 1)
                    throw new MessageException("Argument \"missingBags\" was given an incorrect value.", inMessage);
                break;
            case 60:
                if(inMessage.isThereNoFirstArgument())
                    throw new MessageException("Argument \"flightNumber\" not supplied.", inMessage);
                if(((int) inMessage.getFirstArgument()) < 0)
                    throw new MessageException("Argument \"flightNumber\" was given an incorrect value.", inMessage);
                if(inMessage.isThereNoSecondArgument())
                    throw new MessageException("Argument \"numberOfPassengerLuggageAtThePlane\" not supplied.", inMessage);
                if(((int) inMessage.getSecondArgument()) < 0)
                    throw new MessageException("Argument \"numberOfPassengerLuggageAtThePlane\" was given an incorrect value.", inMessage);
                if(inMessage.isThereNoThirdArgument())
                    throw new MessageException("Argument \"busSeatNumber\" not supplied.", inMessage);
                if(((int) inMessage.getThirdArgument()) < 1)
                    throw new MessageException("Argument \"busSeatNumber\" was given an incorrect value.", inMessage);
                if(inMessage.isThereNoFourthArgument())
                    throw new MessageException("Argument \"totalPassengers\" not supplied.", inMessage);
                if(((int) inMessage.getFourthArgument()) < 1)
                    throw new MessageException("Argument \"totalPassengers\" was given an incorrect value.", inMessage);
                if(inMessage.isThereNoFifthArgument())
                    throw new MessageException("Argument \"passengerSituations\" not supplied.", inMessage);
                if(inMessage.isThereNoSixthArgument())
                    throw new MessageException("Argument \"passengerLuggageAtStart\" not supplied.", inMessage);
                if(inMessage.isThereNoSeventhArgument())
                    throw new MessageException("Argument \"luggagePerFlight\" not supplied.", inMessage);
                break;
            default:
                throw new MessageException("Invalid message type: " + inMessage.getMessageType());
        }

        switch(inMessage.getMessageType()) {
            case 33:
                repository.porterInitiated();
                outMessage = new Message(Message.MessageType.REP_PORTER_INITIATED.getMessageCode(), null);
                break;
            case 34:
                repository.passengerInitiated((int) inMessage.getFirstArgument());
                outMessage = new Message(Message.MessageType.REP_PASSENGER_INITIATED.getMessageCode(),  null);
                break;
            case 35:
                repository.porterTryCollectingBagFromPlane((boolean) inMessage.getFirstArgument());
                outMessage = new Message(Message.MessageType.REP_PORTER_TRY_COLLECTING_BAG_FROM_PLANE.getMessageCode(),  null);
                break;
            case 36:
                repository.passengerGoingToCollectABag((int) inMessage.getFirstArgument());
                outMessage = new Message(Message.MessageType.REP_PASSENGER_GOING_TO_COLLECT_A_BAG.getMessageCode(),  null);
                break;
            case 37:
                repository.passengerEnteringTheBus((int) inMessage.getFirstArgument(), (int) inMessage.getSecondArgument());
                outMessage = new Message(Message.MessageType.REP_PASSENGER_ENTERING_THE_BUG.getMessageCode(),  null);
                break;
            case 38:
                repository.passengerGoingHome((int) inMessage.getFirstArgument());
                outMessage = new Message(Message.MessageType.REP_PASSENGER_GOING_HOME.getMessageCode(),  null);
                break;
            case 39:
                repository.busDriverInitiated();
                outMessage = new Message(Message.MessageType.REP_BUS_DRIVER_INITIATED.getMessageCode(),  null);
                break;
            case 40:
                repository.busDriverParkingTheBus();
                outMessage = new Message(Message.MessageType.REP_BUS_DRIVER_PARKING_THE_BUS.getMessageCode(),  null);
                break;
            case 41:
                repository.passengerGettingIntoTheWaitingQueue((int) inMessage.getFirstArgument(), (int) inMessage.getSecondArgument());
                outMessage = new Message(Message.MessageType.REP_PASSENGER_GETTING_INTO_THE_WAITING_QUEUE.getMessageCode(),  null);
                break;
            case 42:
                repository.passengerTakingABus((int) inMessage.getFirstArgument());
                outMessage = new Message(Message.MessageType.REP_PASSENGER_TAKING_A_BUS.getMessageCode(),  null);
                break;
            case 43:
                repository.busDriverGoingToDepartureTerminal();
                outMessage = new Message(Message.MessageType.REP_BUS_DRIVER_GOING_TO_DEPARTURE_TERMINAL.getMessageCode(),  null);
                break;
            case 44:
                repository.passengerCollectingABag((int) inMessage.getFirstArgument());
                outMessage = new Message(Message.MessageType.REP_PASSENGER_COLLECTING_A_BAG.getMessageCode(),  null);
                break;
            case 45:
                repository.porterCarryBagToBaggageCollectionPoint();
                outMessage = new Message(Message.MessageType.REP_PORTER_CARRY_BAG_TO_BAGGAGE_COLLECTION_POINT.getMessageCode(),  null);
                break;
            case 46:
                repository.porterAnnouncingNoMoreBagsToCollect();
                outMessage = new Message(Message.MessageType.REP_PORTER_ANNOUNCING_NO_MORE_BAGS_TO_COLLECT.getMessageCode(),  null);
                break;
            case 47:
                repository.passengerReportingMissingBags((int) inMessage.getFirstArgument(), (int) inMessage.getSecondArgument());
                outMessage = new Message(Message.MessageType.REP_PASSENGER_REPORTING_MISSING_BAGS.getMessageCode(),  null);
                break;
            case 48:
                repository.passengerPreparingNextLeg((int) inMessage.getFirstArgument());
                outMessage = new Message(Message.MessageType.REP_PASSENGER_PREPARING_NEXT_LEG.getMessageCode(),  null);
                break;
            case 49:
                repository.busDriverGoingToArrivalTerminal();
                outMessage = new Message(Message.MessageType.REP_BUS_DRIVER_GOING_TO_ARRIVAL_TERMINAL.getMessageCode(),  null);
                break;
            case 50:
                repository.passengerLeavingTheBus((int) inMessage.getFirstArgument(), (int) inMessage.getSecondArgument());
                outMessage = new Message(Message.MessageType.REP_PASSENGER_LEAVING_THE_BUS.getMessageCode(),  null);
                break;
            case 51:
                repository.busDriverParkingTheBusAndLettingPassengersOff();
                outMessage = new Message(Message.MessageType.REP_BUS_DRIVER_PARKING_THE_BUS_AND_LETTING_PASS_OFF.getMessageCode(),  null);
                break;
            case 52:
                repository.porterCarryBagToTemporaryStorageArea();
                outMessage = new Message(Message.MessageType.REP_PORTER_CARRY_BAG_TO_TEMPORARY_STORAGE_AREA.getMessageCode(),  null);
                break;
            case 60:
                try {
                    repository.setInitialState((int) inMessage.getFirstArgument(), (int) inMessage.getSecondArgument(), (int) inMessage.getThirdArgument(), (int) inMessage.getFourthArgument(), (PassengerThread.PassengerAndBagSituations[]) inMessage.getFifthArgument(), (int[]) inMessage.getSixthArgument(), (Bag[][][]) inMessage.getSeventhArgument());
                } catch(Exception e) {
                    System.out.print(e.toString());
                }
                outMessage = new Message(Message.MessageType.REP_SET_INITIAL_STATE.getMessageCode(),  null);
        }

        return (outMessage);
    }

}
