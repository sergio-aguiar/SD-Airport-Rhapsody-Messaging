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
        PO_TSA_CARRY_IT_TO_APPROPRIATE_STORE(19);

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
    private int currentIteration;
    private Object firstArgument;
    private Object secondArgument;
    private Object returnInfo;

    public Message(int messageType, int passengerID) throws MessageException {
        if(messageType < 0 || messageType > 19)
            throw new MessageException("Non-existent message type.");
        if(passengerID < 0)
            throw new MessageException("Invalid passengerID given.");
        if(messageType < 6 || messageType > 14)
            throw new MessageException("Invalid message type for a Passenger message.");

        this.messageType = messageType;
        this.passengerID = passengerID;
        this.isPorterMessage = false;
        this.currentIteration = -1;
        this.firstArgument = null;
        this.secondArgument = null;
        this.returnInfo = null;
    }

    public Message(int messageType, int passengerID, Object firstArgument) throws MessageException {
        if(messageType < 0 || messageType > 19)
            throw new MessageException("Non-existent message type.");
        if(passengerID < 0)
            throw new MessageException("Invalid passengerID given.");
        if(messageType < 6 || messageType > 14)
            throw new MessageException("Invalid message type for a Passenger message.");

        this.messageType = messageType;
        this.passengerID = passengerID;
        this.isPorterMessage = false;
        this.currentIteration = -1;
        this.firstArgument = firstArgument;
        this.secondArgument = null;
        this.returnInfo = null;
    }

    public Message(int messageType, int passengerID, Object firstArgument, Object secondArgument) throws MessageException {
        if(messageType < 0 || messageType > 19)
            throw new MessageException("Non-existent message type.");
        if(passengerID < 0)
            throw new MessageException("Invalid passengerID given.");
        if(messageType < 6 || messageType > 14)
            throw new MessageException("Invalid message type for a Passenger message.");

        this.messageType = messageType;
        this.passengerID = passengerID;
        this.isPorterMessage = false;
        this.currentIteration = -1;
        this.firstArgument = firstArgument;
        this.secondArgument = secondArgument;
        this.returnInfo = null;
    }

    public Message(int messageType, boolean isPorterMessage) throws MessageException {
        if(messageType < 0 || messageType > 19)
            throw new MessageException("Non-existent message type.");
        if(!isPorterMessage && messageType > 5)
            throw new MessageException("Invalid message type for a Bus Driver message.");
        if(isPorterMessage && messageType < 15)
            throw new MessageException("Invalid message type for a Porter message.");

        this.messageType = messageType;
        this.passengerID = -1;
        this.isPorterMessage = isPorterMessage;
        this.currentIteration = -1;
        this.firstArgument = null;
        this.secondArgument = null;
        this.returnInfo = null;
    }

    public Message(int messageType, boolean isPorterMessage, Object firstArgument) throws MessageException {
        if(messageType < 0 || messageType > 19)
            throw new MessageException("Non-existent message type.");
        if(!isPorterMessage && messageType > 5)
            throw new MessageException("Invalid message type for a Bus Driver message.");
        if(isPorterMessage && messageType < 15)
            throw new MessageException("Invalid message type for a Porter message.");

        this.messageType = messageType;
        this.passengerID = -1;
        this.isPorterMessage = isPorterMessage;
        this.currentIteration = -1;
        this.firstArgument = firstArgument;
        this.secondArgument = null;
        this.returnInfo = null;
    }

    public Message(int messageType, boolean isPorterMessage, Object firstArgument, Object secondArgument) throws MessageException {
        if(messageType < 0 || messageType > 19)
            throw new MessageException("Non-existent message type.");
        if(!isPorterMessage && messageType > 5)
            throw new MessageException("Invalid message type for a Bus Driver message.");
        if(isPorterMessage && messageType < 15)
            throw new MessageException("Invalid message type for a Porter message.");

        this.messageType = messageType;
        this.passengerID = -1;
        this.isPorterMessage = isPorterMessage;
        this.currentIteration = -1;
        this.firstArgument = firstArgument;
        this.secondArgument = secondArgument;
        this.returnInfo = null;
    }

    public Message(int messageType, int passengerID, int currentIteration) throws MessageException {
        if(currentIteration < 0)
            throw new MessageException("Invalid iteration value. The value must not be negative.");
        if(messageType < 0 || messageType > 19)
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
        if(messageType < 0 || messageType > 19)
            throw new MessageException("Non-existent message type.");
        if(!isPorterMessage && messageType > 5)
            throw new MessageException("Invalid message type for a Bus Driver message.");
        if(isPorterMessage && messageType < 15)
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
        if(messageType < 0 || messageType > 19)
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
                "messageType=" + messageType +
                ", passengerID=" + passengerID +
                ", isPorterMessage=" + isPorterMessage +
                ", currentIteration=" + currentIteration +
                ", returnInfo=" + returnInfo +
                '}';
    }
}
