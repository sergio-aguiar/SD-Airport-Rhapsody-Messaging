package HybridServerSide.ArrivalLounge;

import ClientSide.Extras.Bag;
import Communication.Message;
import Communication.MessageException;

/**
 * ArrivalLoungeInterface: ArrivalLounge message processing and replying.
 * @author sergioaguiar
 * @author marcomacedo
 */
public class ArrivalLoungeInterface {
    /**
     * A reference to the server's ArrivalLounge.
     */
    private ArrivalLounge arrivalLounge;
    /**
     * Constructor: ArrivalLoungeInterface.
     * @param arrivalLounge A reference to the server's ArrivalLounge.
     */
    public ArrivalLoungeInterface(ArrivalLounge arrivalLounge) {
        this.arrivalLounge = arrivalLounge;
    }
    /**
     * Method that processes an inMessage and returns the appropriate response outMessage.
     * @param inMessage The message received.
     * @return The response message based off the message received.
     * @throws MessageException Exception that states why the message object could not be created.
     */
    public Message processAndReply(Message inMessage) throws MessageException {
        Message outMessage = null;

        switch(inMessage.getMessageType()) {
            case 6:
                if(inMessage.isThereNoFirstArgument())
                    throw new MessageException("Argument \"situation\" not supplied.", inMessage);
                if(!inMessage.getFirstArgument().equals("TRT") && !inMessage.getFirstArgument().equals("FDT"))
                    throw new MessageException("Argument \"situation\" was given an incorrect value.", inMessage);
                break;
            case 7:
            case 15:
            case 16:
            case 24:
            case 53:
            case 54:
            case 61:
                break;
            case 55:
                if(inMessage.isThereNoFirstArgument())
                    throw new MessageException("Argument \"totalPassengers\" not supplied.", inMessage);
                if((int) inMessage.getFirstArgument() < 1)
                    throw new MessageException("Argument \"totalPassengers\" was given an incorrect value.", inMessage);
                if(inMessage.isThereNoSecondArgument())
                    throw new MessageException("Argument \"totalFlights\" not supplied.", inMessage);
                if((int) inMessage.getSecondArgument() < 1)
                    throw new MessageException("Argument \"totalFlights\" was given an incorrect value.", inMessage);
                if(inMessage.isThereNoThirdArgument())
                    throw new MessageException("Argument \"luggagePerFlight\" not supplied.", inMessage);
                break;
            default:
                throw new MessageException("Invalid message type: " + inMessage.getMessageType());
        }

        switch(inMessage.getMessageType()) {
            case 6:
                arrivalLounge.whatShouldIDo(inMessage.getPassengerID(), (String) inMessage.getFirstArgument());
                outMessage = new Message(Message.MessageType.PA_AL_WHAT_SHOULD_I_DO.getMessageCode(), null);
                break;
            case 7:
                arrivalLounge.goCollectABag(inMessage.getPassengerID());
                outMessage = new Message(Message.MessageType.PA_AL_GO_COLLECT_A_BAG.getMessageCode(), null);
                break;
            case 15:
                boolean result15 = arrivalLounge.takeARest();
                outMessage = new Message(Message.MessageType.PO_AL_TAKE_A_REST.getMessageCode(), (Object) result15);
                break;
            case 16:
                String result16 = arrivalLounge.tryToCollectABag();
                outMessage = new Message(Message.MessageType.PO_AL_TRY_TO_COLLECT_A_BAG.getMessageCode(), result16);
                break;
            case 24:
                arrivalLounge.prepareForNextFlight();
                outMessage = new Message(Message.MessageType.AL_PREPARE_FOR_NEXT_FLIGHT.getMessageCode(), null);
                break;
            case 53:
                boolean result53 = arrivalLounge.passengersNoLongerNeedTheBus();
                outMessage = new Message(Message.MessageType.AL_PASSENGERS_NO_LONGER_NEED_THE_BUS.getMessageCode(),
                        (Object) result53);
                break;
            case 54:
                arrivalLounge.incrementCrossFlightPassengerCount();
                outMessage = new Message(Message.MessageType.AL_INCREMENT_CROSS_FLIGHT_PASSENGER_COUNT.getMessageCode(),
                        null);
                break;
            case 55:
                arrivalLounge.setInitialState((int) inMessage.getFirstArgument(), (int) inMessage.getSecondArgument(),
                        (Bag[][][]) inMessage.getThirdArgument());
                outMessage = new Message(Message.MessageType.AL_SET_INITIAL_STATE.getMessageCode(), null);
                break;
            case 61:
                ArrivalLoungeServer.running = false;
                (((ArrivalLoungeProxy) (Thread.currentThread ())).getServerCom()).setTimeout(10);
                outMessage = new Message (Message.MessageType.EVERYTHING_FINISHED.getMessageCode(), null);
        }
        return (outMessage);
    }

}
