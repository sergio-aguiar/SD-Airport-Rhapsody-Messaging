package ClientSide.Extras;

import ClientSide.Passenger.PassengerThread;

import java.io.Serializable;

/**
 * Bag: A serializable object to express a passenger's luggage data.
 *
 * @author marcomacedo
 * @author sergioaguiar
 */
public class Bag implements Serializable {
    /**
     *  Serial number for serialization identification purposes.
     */
    private static final long serialVersionUID = 4444L;
    /**
     *  Bag owner's passenger ID.
     */
    private final int passengerID;
    /**
     * Bag's travel situation.
     */
    private PassengerThread.PassengerAndBagSituations bagSituation;
    /**
     * Bag constructor.
     * @param passengerID Bag owner's passenger ID.
     * @param bagSituation Bag's travel situation.
     */
    public Bag(int passengerID, PassengerThread.PassengerAndBagSituations bagSituation) {
        this.passengerID = passengerID;
        this.bagSituation = bagSituation;
    }
    /**
     * Getter for bagSituation.
     * @return Bag's travel situation.
     */
    public PassengerThread.PassengerAndBagSituations getBagSituation() {
        return this.bagSituation;
    }
    /**
     * Bag's toString override. Used by the porter to more quickly identify bag destinations.
     * @return A string representation for a bag.
     */
    @Override
    public String toString() {
        return this.passengerID + "," + bagSituation.toString();
    }
}
