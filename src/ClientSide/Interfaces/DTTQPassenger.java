package ClientSide.Interfaces;
/**
 * Passenger's Departure Terminal Transfer Exit Interface.
 * 
 * @author sergioaguiar
 * @author marcomacedo
 */
public interface DTTQPassenger {
    /**
     * The passenger leaves the bus and signals the bus driver if he's the last one to do so.
     * @param pid The passenger's ID.
     * @param seat The passenger's bus seat.
     */
    public void leaveTheBus(int pid, int seat);
}
