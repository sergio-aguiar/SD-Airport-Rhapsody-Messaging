package ClientSide.Interfaces;
/**
 * Porter's Arrival Lounge Interface.
 * 
 * @author sergioaguiar
 * @author marcomacedo
 */
public interface ALPorter {
    /**
     * The porter checks whether he is still needed in the future. If so, he awaits the next flight. He stops otherwise.
     * @return true if the porter isn't needed anymore and false otherwise.
     */
    public boolean takeARest();
    /**
     * The porter tries to collect a bag from the plane's hold.
     * @return the bag's owner ID as a String, or an empty String if the plane is empty of bags.
     */
    public String tryToCollectABag();

}
