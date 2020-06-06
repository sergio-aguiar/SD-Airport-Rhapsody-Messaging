package ClientSide.Interfaces;
/**
 * Porter's Temporary Storage Area Interface.
 * 
 * @author sergioaguiar
 * @author marcomacedo
 */
public interface TSAPorter {
	/**
     * The Porter carries their held bag to the Temporary Storage Area.
     * @param bagID The porter's held bag's owner's ID.
     */
    public void carryItToAppropriateStore(int bagID);
}
