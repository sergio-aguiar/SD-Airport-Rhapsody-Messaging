package ClientSide.Porter;

import ClientSide.Interfaces.ALPorter;
import ClientSide.Interfaces.BCPPorter;
import ClientSide.Interfaces.TSAPorter;
import ClientSide.Passenger.PassengerThread;

/**
 * Porter Thread: execute's the Porter's life-cycle.
 * @author sergioaguiar
 * @author marcomacedo
 */
public class PorterThread extends Thread {
    /**
     * Enumerate with the Porter states.
     */
    public enum PorterStates {
        WAITING_FOR_A_PLANE_TO_LAND("WPTL"),
        AT_THE_PLANES_HOLD("APLH"),
        AT_THE_LUGGAGE_BELT_CONVEYOR("ALCB"),
        AT_THE_STOREROOM("ASTR");
        /**
         * Enum's descriptive String.
         */
        String description;
        /**
         * PorterStates constructor.
         * @param description Enum's descriptive String.
         */
        PorterStates(String description) {
            this.description = description;
        }
        /**
         * Enum's toString override
         */
        @Override
        public String toString(){
            return this.description;
        }
    }
    /**
     * Current Bag's Data.
     */
    private String[] bagData;
    /**
     * Porter's ID.
     */
    private final int pid;
    /**
     * Instance of the Porter's the Arrival Lounge interface.
     */
    private final ALPorter alPorter;
    /**
     * Instance of the Porter's Baggage Collection Point interface.
     */
    private final BCPPorter bcpPorter;
    /**
     * Instance of the Porter's Temporary Storage Area interface.
     */
    private final TSAPorter tsaPorter;
    /**
     * Constructor: Porter
     * @param pid Porter's ID.'
     * @param al Porter's Arrival Lounge interface.
     * @param bcp Porter's Baggage Collection Point interface.
     * @param tsa Porter's Temporary Storage Area interface.
     */
     public PorterThread(int pid, ALPorter al, BCPPorter bcp, TSAPorter tsa) {
        this.pid = pid;
        this.bagData = new String[2];
        this.alPorter = al;
        this.bcpPorter = bcp;
        this.tsaPorter = tsa;
    }
    /**
     * Executes the Porter's life-cycle.
     */
   @Override
    public void run() {
        while(!this.alPorter.takeARest()) {
            String tmpBag = this.alPorter.tryToCollectABag();
            while(!tmpBag.equals("")) {
                this.bagData = tmpBag.split(",");
                if(this.bagData[1].equals(PassengerThread.PassengerAndBagSituations.FDT.toString()))
                    this.bcpPorter.carryItToAppropriateStore(Integer.parseInt(this.bagData[0]));
                else this.tsaPorter.carryItToAppropriateStore(Integer.parseInt(this.bagData[0]));
                tmpBag = this.alPorter.tryToCollectABag();
            }
            this.bcpPorter.noMoreBagsToCollect(this.pid);
        }
    }
}
