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
        REP_PORTER_CARRY_BAG_TO_TEMPORARY_STORAGE_AREA(52);

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
    private int currentIteration;
    private Object firstArgument;
    private Object secondArgument;
    private Object returnInfo;

    public Message(int messageType, int passengerID) throws MessageException {
        if(messageType < 0 || messageType > 52)
            throw new MessageException("Non-existent message type.");
        if(passengerID < 0)
            throw new MessageException("Invalid passengerID given.");
        if(messageType < 6 || messageType > 14)
            throw new MessageException("Invalid message type for a Passenger message.");

        this.messageType = messageType;
        this.passengerID = passengerID;
        this.isPorterMessage = false;
        this.isAreaRequest = false;
        this.currentIteration = -1;
        this.firstArgument = null;
        this.secondArgument = null;
        this.returnInfo = null;
    }

    public Message(int messageType, int passengerID, Object firstArgument) throws MessageException {
        if(messageType < 0 || messageType > 52)
            throw new MessageException("Non-existent message type.");
        if(passengerID < 0)
            throw new MessageException("Invalid passengerID given.");
        if(messageType < 6 || messageType > 14)
            throw new MessageException("Invalid message type for a Passenger message.");

        this.messageType = messageType;
        this.passengerID = passengerID;
        this.isPorterMessage = false;
        this.isAreaRequest = false;
        this.currentIteration = -1;
        this.firstArgument = firstArgument;
        this.secondArgument = null;
        this.returnInfo = null;
    }

    public Message(int messageType, int passengerID, Object firstArgument, Object secondArgument) throws MessageException {
        if(messageType < 0 || messageType > 52)
            throw new MessageException("Non-existent message type.");
        if(passengerID < 0)
            throw new MessageException("Invalid passengerID given.");
        if(messageType < 6 || messageType > 14)
            throw new MessageException("Invalid message type for a Passenger message.");

        this.messageType = messageType;
        this.passengerID = passengerID;
        this.isPorterMessage = false;
        this.isAreaRequest = false;
        this.currentIteration = -1;
        this.firstArgument = firstArgument;
        this.secondArgument = secondArgument;
        this.returnInfo = null;
    }

    public Message(int messageType, boolean isPorterMessage) throws MessageException {
        if(messageType < 0 || messageType > 52)
            throw new MessageException("Non-existent message type.");
        if(!isPorterMessage && messageType > 5)
            throw new MessageException("Invalid message type for a Bus Driver message.");
        if(isPorterMessage && (messageType < 15 || messageType > 19))
            throw new MessageException("Invalid message type for a Porter message.");

        this.messageType = messageType;
        this.passengerID = -1;
        this.isPorterMessage = isPorterMessage;
        this.isAreaRequest = false;
        this.currentIteration = -1;
        this.firstArgument = null;
        this.secondArgument = null;
        this.returnInfo = null;
    }

    public Message(int messageType, boolean isPorterMessage, Object firstArgument) throws MessageException {
        if(messageType < 0 || messageType > 52)
            throw new MessageException("Non-existent message type.");
        if(!isPorterMessage && messageType > 5)
            throw new MessageException("Invalid message type for a Bus Driver message.");
        if(isPorterMessage && (messageType < 15 || messageType > 19))
            throw new MessageException("Invalid message type for a Porter message.");

        this.messageType = messageType;
        this.passengerID = -1;
        this.isPorterMessage = isPorterMessage;
        this.isAreaRequest = false;
        this.currentIteration = -1;
        this.firstArgument = firstArgument;
        this.secondArgument = null;
        this.returnInfo = null;
    }

    public Message(int messageType, boolean isPorterMessage, Object firstArgument, Object secondArgument) throws MessageException {
        if(messageType < 0 || messageType > 52)
            throw new MessageException("Non-existent message type.");
        if(!isPorterMessage && messageType > 5)
            throw new MessageException("Invalid message type for a Bus Driver message.");
        if(isPorterMessage && (messageType < 15 || messageType > 19))
            throw new MessageException("Invalid message type for a Porter message.");

        this.messageType = messageType;
        this.passengerID = -1;
        this.isPorterMessage = isPorterMessage;
        this.isAreaRequest = false;
        this.currentIteration = -1;
        this.firstArgument = firstArgument;
        this.secondArgument = secondArgument;
        this.returnInfo = null;
    }

    public Message(int messageType, boolean isPorterMessage, boolean isAreaRequest) throws MessageException {
        if(messageType < 0 || messageType > 52)
            throw new MessageException("Non-existent message type.");
        if(isAreaRequest) {
            if(isPorterMessage)
                throw new MessageException("A message can not be both a porter and an area message.");
            if(messageType < 20)
                throw new MessageException("Invalid message type for an area message.");
        } else {
            if(!isPorterMessage && messageType > 5)
                throw new MessageException("Invalid message type for a Bus Driver message.");
            if(isPorterMessage && (messageType < 15 || messageType > 19))
                throw new MessageException("Invalid message type for a Porter message.");
        }

        this.messageType = messageType;
        this.passengerID = -1;
        this.isPorterMessage = isPorterMessage;
        this.isAreaRequest = isAreaRequest;
        this.currentIteration = -1;
        this.firstArgument = null;
        this.secondArgument = null;
        this.returnInfo = null;
    }

    public Message(int messageType, boolean isPorterMessage, boolean isAreaRequest, Object firstArgument) throws MessageException {
        if(messageType < 0 || messageType > 52)
            throw new MessageException("Non-existent message type.");
        if(isAreaRequest) {
            if(isPorterMessage)
                throw new MessageException("A message can not be both a porter and an area message.");
            if(messageType < 20)
                throw new MessageException("Invalid message type for an area message.");
        } else {
            if(!isPorterMessage && messageType > 5)
                throw new MessageException("Invalid message type for a Bus Driver message.");
            if(isPorterMessage && (messageType < 15 || messageType > 19))
                throw new MessageException("Invalid message type for a Porter message.");
        }

        this.messageType = messageType;
        this.passengerID = -1;
        this.isPorterMessage = isPorterMessage;
        this.isAreaRequest = isAreaRequest;
        this.currentIteration = -1;
        this.firstArgument = firstArgument;
        this.secondArgument = null;
        this.returnInfo = null;
    }

    public Message(int messageType, boolean isPorterMessage, boolean isAreaRequest, Object firstArgument, Object secondArgument) throws MessageException {
        if(messageType < 0 || messageType > 52)
            throw new MessageException("Non-existent message type.");
        if(isAreaRequest) {
            if(isPorterMessage)
                throw new MessageException("A message can not be both a porter and an area message.");
            if(messageType < 20)
                throw new MessageException("Invalid message type for an area message.");
        } else {
            if(!isPorterMessage && messageType > 5)
                throw new MessageException("Invalid message type for a Bus Driver message.");
            if(isPorterMessage && (messageType < 15 || messageType > 19))
                throw new MessageException("Invalid message type for a Porter message.");
        }

        this.messageType = messageType;
        this.passengerID = -1;
        this.isPorterMessage = isPorterMessage;
        this.isAreaRequest = isAreaRequest;
        this.currentIteration = -1;
        this.firstArgument = firstArgument;
        this.secondArgument = secondArgument;
        this.returnInfo = null;
    }

    public Message(int messageType, int passengerID, int currentIteration) throws MessageException {
        if(currentIteration < 0)
            throw new MessageException("Invalid iteration value. The value must not be negative.");
        if(messageType < 0 || messageType > 52)
            throw new MessageException("Non-existent message type.");
        if(passengerID < 0)
            throw new MessageException("Invalid passengerID given.");
        if(messageType < 6 || messageType > 14)
            throw new MessageException("Invalid message type for a Passenger message.");

        this.messageType = messageType;
        this.passengerID = passengerID;
        this.isPorterMessage = false;
        this.currentIteration = currentIteration;
        this.firstArgument = null;
        this.secondArgument = null;
        this.returnInfo = null;
    }

    public Message(int messageType, boolean isPorterMessage, int currentIteration) throws MessageException {
        if(currentIteration < 0)
            throw new MessageException("Invalid iteration value. The value must not be negative.");
        if(messageType < 0 || messageType > 52)
            throw new MessageException("Non-existent message type.");
        if(!isPorterMessage && messageType > 5)
            throw new MessageException("Invalid message type for a Bus Driver message.");
        if(isPorterMessage && (messageType < 15 || messageType > 19))
            throw new MessageException("Invalid message type for a Porter message.");

        this.messageType = messageType;
        this.passengerID = -1;
        this.isPorterMessage = isPorterMessage;
        this.currentIteration = currentIteration;
        this.firstArgument = null;
        this.secondArgument = null;
        this.returnInfo = null;
    }

    public Message(int messageType, Object returnInfo) throws MessageException {
        if(messageType < 0 || messageType > 52)
            throw new MessageException("Non-existent message type.");

        this.messageType = messageType;
        this.passengerID = -1;
        this.isPorterMessage = false;
        this.currentIteration = -1;
        this.firstArgument = null;
        this.secondArgument = null;
        this.returnInfo = returnInfo;
    }

    public int getMessageType() {
        return this.messageType;
    }

    public int getPassengerID() {
        return this.passengerID;
    }

    public boolean isPorterMessage() {
        return this.isPorterMessage;
    }

    public boolean isAreaRequest() {
        return this.isAreaRequest;
    }

    public int getCurrentIteration() {
        return this.currentIteration;
    }

    public Object getFirstArgument() {
        return this.firstArgument;
    }

    public Object getSecondArgument() {
        return this.secondArgument;
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

    public boolean isThereNoReturnInfo() {
        return this.returnInfo == null;
    }

    @Override
    public String toString() {
        return "Message{" +
                "messageType=" + this.messageType +
                ", passengerID=" + this.passengerID +
                ", isPorterMessage=" + this.isPorterMessage +
                ", isAreaRequest=" + this.isAreaRequest +
                ", currentIteration=" + this.currentIteration +
                ", firstArgument=" + this.firstArgument +
                ", secondArgument=" + this.secondArgument +
                ", returnInfo=" + this.returnInfo +
                '}';
    }
}
