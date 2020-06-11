package ClientSide.Interfaces;

/**
 * Porter's Baggage Collection Point Interface.
 * 
 * @author sergioaguiar
 * @author marcomacedo
 */
public interface BCPPorter {
	/**
     * The Porter carries their held bag to the Baggage Collection Point.
     * @param bagID The porter's held bag's owner's ID.
     */
    public void carryItToAppropriateStore(int bagID);
	/**
     * The porter announces that there are no more bags in the plane by signalling every waiting passenger.
     * @param pid The passenger's ID.
     */
    public void noMoreBagsToCollect(int pid);
}
