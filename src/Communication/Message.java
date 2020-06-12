package Communication;

import java.io.Serializable;

public class Message implements Serializable {

    private static final long serialVersionUID = 1001L;

    public enum MessageType {
        BD_ATTQ_HAS_DAYS_WORK_ENDED(0),
        BD_ATTQ_ANNOUNCING_BUS_BOARDING(1),
        BD_ATTQ_GO_TO_DEPARTURE_TERMINAL(2),
        BD_ATTQ_PARK_THE_BUS(3),
        BD_DTTQ_PARK_THE_BUS_AND_LET_PASS_OFF(4),
        BD_DTTQ_GO_TO_ARRIVAL_TERMINAL(5),
        PA_AL_WHAT_SHOULD_I_DO(6),
        PA_AL_GO_COLLECT_A_BAG(7),
        PA_ATE_GO_HOME(8),
        PA_ATTQ_TAKE_A_BUS(9),
        PA_ATTQ_ENTER_THE_BUS(10),
        PA_BCP_GO_COLLECT_A_BAG(11),
        PA_BRO_REPORT_MISSING_BAGS(12),
        PA_DTE_PREPARE_NEXT_LEG(13),
        PA_DTTQ_LEAVE_THE_BUS(14),
        PO_AL_TAKE_A_REST(15),
        PO_AL_TRY_TO_COLLECT_A_BAG(16),
        PO_BCP_CARRY_IT_TO_APPROPRIATE_STORE(17),
        PO_BCP_NO_MORE_BAGS_TO_COLLECT(18),
        PO_TSA_CARRY_IT_TO_APPROPRIATE_STORE(19),
        ATE_GET_WAITING_PASSENGERS(20),
        ATE_SIGNAL_WAITING_PASSENGERS(21),
        DTE_GET_WAITING_PASSENGERS(22),
        DTE_SIGNAL_WAITING_PASSENGERS(23),
        AL_PREPARE_FOR_NEXT_FLIGHT(24),
        ATE_PREPARE_FOR_NEXT_FLIGHT(25),
        ATTQ_PREPARE_FOR_NEXT_FLIGHT(26),
        BCP_PREPARE_FOR_NEXT_FLIGHT(27),
        DTE_PREPARE_FOR_NEXT_FLIGHT(28),
        DTTQ_PREPARE_FOR_NEXT_FLIGHT(29),
        TSA_PREPARE_FOR_NEXT_FLIGHT(30),
        REP_PREPARE_FOR_NEXT_FLIGHT(31),
        REP_FINAL_REPORT(32),
        REP_PORTER_INITIATED(33),
        REP_PASSENGER_INITIATED(34),
        REP_PORTER_TRY_COLLECTING_BAG_FROM_PLANE(35),
        REP_PASSENGER_GOING_TO_COLLECT_A_BAG(36),
        REP_PASSENGER_ENTERING_THE_BUG(37),
        REP_PASSENGER_GOING_HOME(38),
        REP_BUS_DRIVER_INITIATED(39),
        REP_BUS_DRIVER_PARKING_THE_BUS(40),
        REP_PASSENGER_GETTING_INTO_THE_WAITING_QUEUE(41),
        REP_PASSENGER_TAKING_A_BUS(42),
        REP_BUS_DRIVER_GOING_TO_DEPARTURE_TERMINAL(43),
        REP_PASSENGER_COLLECTING_A_BAG(44),
        REP_PORTER_CARRY_BAG_TO_BAGGAGE_COLLECTION_POINT(45),
        REP_PORTER_ANNOUNCING_NO_MORE_BAGS_TO_COLLECT(46),
        REP_PASSENGER_REPORTING_MISSING_BAGS(47),
        REP_PASSENGER_PREPARING_NEXT_LEG(48),
        REP_BUS_DRIVER_GOING_TO_ARRIVAL_TERMINAL(49),
        REP_PASSENGER_LEAVING_THE_BUS(50),
        REP_BUS_DRIVER_PARKING_THE_BUS_AND_LETTING_PASS_OFF(51),
        REP_PORTER_CARRY_BAG_TO_TEMPORARY_STORAGE_AREA(52),
        AL_PASSENGERS_NO_LONGER_NEED_THE_BUS(53),
        AL_INCREMENT_CROSS_FLIGHT_PASSENGER_COUNT(54),
        AL_SET_INITIAL_STATE(55),
        ATE_SET_INITIAL_STATE(56),
        ATTQ_SET_INITIAL_STATE(57),
        BCP_SET_INITIAL_STATE(58),
        DTE_SET_INITIAL_STATE(59),
        REP_SET_INITIAL_STATE(60),
        EVERYTHING_FINISHED(61);

        private int messageCode;

        MessageType(int messageCode) {
            this.messageCode = messageCode;
        }

        public int getMessageCode() {
            return this.messageCode;
        }

        public static String getNameByMessageCode(int messageCode) {
            for(MessageType m : values())
                if(m.getMessageCode() == messageCode) return m.name();
            return "Error";
        }

        @Override
        public String toString() {
            return this.name();
        }
    }

    private int messageType;
    private int passengerID;
    private boolean isPorterMessage;
    private boolean isAreaRequest;
    private Object firstArgument;
    private Object secondArgument;
    private Object thirdArgument;
    private Object fourthArgument;
    private Object fifthArgument;
    private Object sixthArgument;
    private Object seventhArgument;
    private Object returnInfo;

    public Message(int messageType, int passengerID) throws MessageException {
        if(messageType < 0 || messageType > 61)
            throw new MessageException("Non-existent message type: " + messageType);
        if(passengerID < 0)
            throw new MessageException("Invalid passengerID given: " + passengerID);
        if(messageType < 6 || messageType > 14)
            throw new MessageException("Invalid message type for a Passenger message: " + messageType);

        this.messageType = messageType;
        this.passengerID = passengerID;
        this.isPorterMessage = false;
        this.isAreaRequest = false;
        this.firstArgument = null;
        this.secondArgument = null;
        this.thirdArgument = null;
        this.fourthArgument = null;
        this.fifthArgument = null;
        this.sixthArgument = null;
        this.seventhArgument = null;
        this.returnInfo = null;
    }

    public Message(int messageType, int passengerID, Object firstArgument) throws MessageException {
        if(messageType < 0 || messageType > 61)
            throw new MessageException("Non-existent message type: " + messageType);
        if(passengerID < 0)
            throw new MessageException("Invalid passengerID given: " + passengerID);
        if(messageType < 6 || messageType > 14)
            throw new MessageException("Invalid message type for a Passenger message: " + messageType);

        this.messageType = messageType;
        this.passengerID = passengerID;
        this.isPorterMessage = false;
        this.isAreaRequest = false;
        this.firstArgument = firstArgument;
        this.secondArgument = null;
        this.thirdArgument = null;
        this.fourthArgument = null;
        this.fifthArgument = null;
        this.sixthArgument = null;
        this.seventhArgument = null;
        this.returnInfo = null;
    }

    public Message(int messageType, int passengerID, Object firstArgument, Object secondArgument) throws MessageException {
        if(messageType < 0 || messageType > 61)
            throw new MessageException("Non-existent message type: " + messageType);
        if(passengerID < 0)
            throw new MessageException("Invalid passengerID given: " + passengerID);
        if(messageType < 6 || messageType > 14)
            throw new MessageException("Invalid message type for a Passenger message: " + messageType);

        this.messageType = messageType;
        this.passengerID = passengerID;
        this.isPorterMessage = false;
        this.isAreaRequest = false;
        this.firstArgument = firstArgument;
        this.secondArgument = secondArgument;
        this.thirdArgument = null;
        this.fourthArgument = null;
        this.fifthArgument = null;
        this.sixthArgument = null;
        this.seventhArgument = null;
        this.returnInfo = null;
    }

    public Message(int messageType, boolean isPorterMessage) throws MessageException {
        if(messageType < 0 || messageType > 61)
            throw new MessageException("Non-existent message type: " + messageType);
        if(!isPorterMessage && messageType > 5)
            throw new MessageException("Invalid message type for a Bus Driver message: " + messageType);
        if(isPorterMessage && (messageType < 15 || messageType > 19))
            throw new MessageException("Invalid message type for a Porter message: " + messageType);

        this.messageType = messageType;
        this.passengerID = -1;
        this.isPorterMessage = isPorterMessage;
        this.isAreaRequest = false;
        this.firstArgument = null;
        this.secondArgument = null;
        this.thirdArgument = null;
        this.fourthArgument = null;
        this.fifthArgument = null;
        this.sixthArgument = null;
        this.seventhArgument = null;
        this.returnInfo = null;
    }

    public Message(int messageType, boolean isPorterMessage, Object firstArgument) throws MessageException {
        if(messageType < 0 || messageType > 61)
            throw new MessageException("Non-existent message type: " + messageType);
        if(!isPorterMessage && messageType > 5)
            throw new MessageException("Invalid message type for a Bus Driver message: " + messageType);
        if(isPorterMessage && (messageType < 15 || messageType > 19))
            throw new MessageException("Invalid message type for a Porter message: " + messageType);

        this.messageType = messageType;
        this.passengerID = -1;
        this.isPorterMessage = isPorterMessage;
        this.isAreaRequest = false;
        this.firstArgument = firstArgument;
        this.secondArgument = null;
        this.thirdArgument = null;
        this.fourthArgument = null;
        this.fifthArgument = null;
        this.sixthArgument = null;
        this.seventhArgument = null;
        this.returnInfo = null;
    }

    public Message(int messageType, boolean isPorterMessage, Object firstArgument, Object secondArgument) throws MessageException {
        if(messageType < 0 || messageType > 61)
            throw new MessageException("Non-existent message type: " + messageType);
        if(!isPorterMessage && messageType > 5)
            throw new MessageException("Invalid message type for a Bus Driver message: " + messageType);
        if(isPorterMessage && (messageType < 15 || messageType > 19))
            throw new MessageException("Invalid message type for a Porter message: " + messageType);

        this.messageType = messageType;
        this.passengerID = -1;
        this.isPorterMessage = isPorterMessage;
        this.isAreaRequest = false;
        this.firstArgument = firstArgument;
        this.secondArgument = secondArgument;
        this.thirdArgument = null;
        this.fourthArgument = null;
        this.fifthArgument = null;
        this.sixthArgument = null;
        this.seventhArgument = null;
        this.returnInfo = null;
    }

    public Message(int messageType, boolean isPorterMessage, boolean isAreaRequest) throws MessageException {
        if(messageType < 0 || messageType > 61)
            throw new MessageException("Non-existent message type: " + messageType);
        if(isAreaRequest) {
            if(isPorterMessage)
                throw new MessageException("A message can not be both a porter and an area message.");
            if(messageType < 20)
                throw new MessageException("Invalid message type for an area message: " + messageType);
        } else {
            if(!isPorterMessage && messageType > 5)
                throw new MessageException("Invalid message type for a Bus Driver message: " + messageType);
            if(isPorterMessage && (messageType < 15 || messageType > 19))
                throw new MessageException("Invalid message type for a Porter message: " + messageType);
        }

        this.messageType = messageType;
        this.passengerID = -1;
        this.isPorterMessage = isPorterMessage;
        this.isAreaRequest = isAreaRequest;
        this.firstArgument = null;
        this.secondArgument = null;
        this.thirdArgument = null;
        this.fourthArgument = null;
        this.fifthArgument = null;
        this.sixthArgument = null;
        this.seventhArgument = null;
        this.returnInfo = null;
    }

    public Message(int messageType, boolean isPorterMessage, boolean isAreaRequest, Object firstArgument) throws MessageException {
        if(messageType < 0 || messageType > 61)
            throw new MessageException("Non-existent message type: " + messageType);
        if(isAreaRequest) {
            if(isPorterMessage)
                throw new MessageException("A message can not be both a porter and an area message.");
            if(messageType < 20)
                throw new MessageException("Invalid message type for an area message: " + messageType);
        } else {
            if(!isPorterMessage && messageType > 5)
                throw new MessageException("Invalid message type for a Bus Driver message: " + messageType);
            if(isPorterMessage && (messageType < 15 || messageType > 19))
                throw new MessageException("Invalid message type for a Porter message: " + messageType);
        }

        this.messageType = messageType;
        this.passengerID = -1;
        this.isPorterMessage = isPorterMessage;
        this.isAreaRequest = isAreaRequest;
        this.firstArgument = firstArgument;
        this.secondArgument = null;
        this.thirdArgument = null;
        this.fourthArgument = null;
        this.fifthArgument = null;
        this.sixthArgument = null;
        this.seventhArgument = null;
        this.returnInfo = null;
    }

    public Message(int messageType, boolean isPorterMessage, boolean isAreaRequest, Object firstArgument, Object secondArgument) throws MessageException {
        if(messageType < 0 || messageType > 61)
            throw new MessageException("Non-existent message type: " + messageType);
        if(isAreaRequest) {
            if(isPorterMessage)
                throw new MessageException("A message can not be both a porter and an area message.");
            if(messageType < 20)
                throw new MessageException("Invalid message type for an area message: " + messageType);
        } else {
            if(!isPorterMessage && messageType > 5)
                throw new MessageException("Invalid message type for a Bus Driver message: " + messageType);
            if(isPorterMessage && (messageType < 15 || messageType > 19))
                throw new MessageException("Invalid message type for a Porter message: " + messageType);
        }

        this.messageType = messageType;
        this.passengerID = -1;
        this.isPorterMessage = isPorterMessage;
        this.isAreaRequest = isAreaRequest;
        this.firstArgument = firstArgument;
        this.secondArgument = secondArgument;
        this.thirdArgument = null;
        this.fourthArgument = null;
        this.fifthArgument = null;
        this.sixthArgument = null;
        this.seventhArgument = null;
        this.returnInfo = null;
    }

    public Message(int messageType, Object argument1, Object argument2, Object argument3) throws MessageException {
        if(messageType < 0 || messageType > 61)
            throw new MessageException("Non-existent message type.");
        if(messageType < 55)
            throw new MessageException("Invalid message type for an initial state message: " + messageType);

        this.messageType = messageType;
        this.passengerID = -1;
        this.isPorterMessage = false;
        this.isAreaRequest = true;
        this.firstArgument = argument1;
        this.secondArgument = argument2;
        this.thirdArgument = argument3;
        this.fourthArgument = null;
        this.fifthArgument = null;
        this.sixthArgument = null;
        this.seventhArgument = null;
        this.returnInfo = null;
    }

    public Message(int messageType, Object argument1, Object argument2, Object argument3,
                   Object argument4, Object argument5, Object argument6, Object argument7) throws MessageException {
        if(messageType < 0 || messageType > 61)
            throw new MessageException("Non-existent message type.");
        if(messageType != 60)
            throw new MessageException("Invalid message type for a repository initial state message: " + messageType);

        this.messageType = messageType;
        this.passengerID = -1;
        this.isPorterMessage = false;
        this.isAreaRequest = true;
        this.firstArgument = argument1;
        this.secondArgument = argument2;
        this.thirdArgument = argument3;
        this.fourthArgument = argument4;
        this.fifthArgument = argument5;
        this.sixthArgument = argument6;
        this.seventhArgument = argument7;
        this.returnInfo = null;
    }

    public Message(int messageType, Object returnInfo) throws MessageException {
        if(messageType < 0 || messageType > 61)
            throw new MessageException("Non-existent message type: " + messageType);

        this.messageType = messageType;
        this.passengerID = -1;
        this.isPorterMessage = false;
        this.firstArgument = null;
        this.secondArgument = null;
        this.thirdArgument = null;
        this.fourthArgument = null;
        this.fifthArgument = null;
        this.sixthArgument = null;
        this.seventhArgument = null;
        this.returnInfo = returnInfo;
    }

    public int getMessageType() {
        return this.messageType;
    }

    public int getPassengerID() {
        return this.passengerID;
    }

    public Object getFirstArgument() {
        return this.firstArgument;
    }

    public Object getSecondArgument() {
        return this.secondArgument;
    }

    public Object getThirdArgument() {
        return this.thirdArgument;
    }

    public Object getFourthArgument() {
        return this.fourthArgument;
    }

    public Object getFifthArgument() {
        return this.fifthArgument;
    }

    public Object getSixthArgument() {
        return this.sixthArgument;
    }

    public Object getSeventhArgument() {
        return this.seventhArgument;
    }

    public Object getReturnInfo() {
        return this.returnInfo;
    }

    public boolean isThereNoFirstArgument() {
        return this.firstArgument == null;
    }

    public boolean isThereNoSecondArgument() {
        return this.secondArgument == null;
    }

    public boolean isThereNoThirdArgument() {
        return this.thirdArgument == null;
    }

    public boolean isThereNoFourthArgument() {
        return this.fourthArgument == null;
    }

    public boolean isThereNoFifthArgument() {
        return this.fifthArgument == null;
    }

    public boolean isThereNoSixthArgument() {
        return this.sixthArgument == null;
    }

    public boolean isThereNoSeventhArgument() {
        return this.seventhArgument == null;
    }

    public boolean isThereNoReturnInfo() {
        return this.returnInfo == null;
    }

    @Override
    public String toString() {
        String out = "Message{" + "messageType=" + Message.MessageType.getNameByMessageCode(this.messageType);
        if(this.passengerID != -1) out += ", passengerID=" + this.passengerID;
        if(!this.isThereNoFirstArgument()) out += ", firstArgument=" + this.firstArgument;
        if(!this.isThereNoSecondArgument()) out += ", secondArgument=" + this.secondArgument;
        if(!this.isThereNoThirdArgument()) out += ", thirdArgument=" + this.thirdArgument;
        if(!this.isThereNoFourthArgument()) out += ", fourthArgument=" + this.fourthArgument;
        if(!this.isThereNoFifthArgument()) out += ", fifthArgument=" + this.fifthArgument;
        if(!this.isThereNoSixthArgument()) out += ", sixthArgument=" + this.sixthArgument;
        if(!this.isThereNoSeventhArgument()) out += ", seventhArgument=" + this.seventhArgument;
        if(!this.isThereNoReturnInfo()) out += ", return=" + this.returnInfo;
        out += "}";

        return out;
    }
}
