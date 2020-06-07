package ClientSide.Extras;

import ClientSide.Passenger.PassengerThread;

import java.io.Serializable;

public class Bag implements Serializable {

    private static final long serialVersionUID = 4444L;

    private final int passengerID;
    private PassengerThread.PassengerAndBagSituations bagSituation;

    public Bag(int passengerID, PassengerThread.PassengerAndBagSituations bagSituation) {
        this.passengerID = passengerID;
        this.bagSituation = bagSituation;
    }

    public int getPassengerID() {
        return this.passengerID;
    }

    public PassengerThread.PassengerAndBagSituations getBagSituation() {
        return this.bagSituation;
    }

    @Override
    public String toString() {
        return String.valueOf(this.passengerID) + "," + bagSituation.toString();
    }
}
