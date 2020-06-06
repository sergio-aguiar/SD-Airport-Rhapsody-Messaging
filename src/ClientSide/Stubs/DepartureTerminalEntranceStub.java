package ClientSide.Stubs;

import ClientSide.ClientCom.ClientCom;
import ClientSide.Interfaces.DTEPassenger;
import Communication.Message;
import Communication.MessageException;
import genclass.GenericIO;

public class DepartureTerminalEntranceStub implements DTEPassenger {

    private String serverHostName;
    private int serverHostPort;

    public DepartureTerminalEntranceStub(String hostName, int hostPort) {
        this.serverHostName = hostName;
        this.serverHostPort = hostPort;
    }

    /**
     * The passenger checks if they is the last to make it to their destination inside the airport. If so, they signal all others to leave together. Otherwise, they wait for the last one to signal them.
     *
     * @param pid The passenger's ID.
     */
    @Override
    public void prepareNextLeg(int pid) {
        ClientCom clientCom = new ClientCom (serverHostName, serverHostPort);
        Message inMessage;
        Message outMessage = null;

        while (!clientCom.open()) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                System.out.println("Thread " + Thread.currentThread().getName() + ": DTEQStub: prepareNextLeg: " + e.toString());
            }
        }

        try {
            outMessage = new Message(Message.MessageType.PA_DTE_PREPARE_NEXT_LEG.getMessageCode(), pid);
        } catch(MessageException e) {
            System.out.println("Thread " + Thread.currentThread().getName() + ": DTEQStub: prepareNextLeg: " + e.toString());
        }

        clientCom.writeObject(outMessage);
        inMessage = (Message) clientCom.readObject();

        if(inMessage.getMessageType() != Message.MessageType.PA_DTE_PREPARE_NEXT_LEG.getMessageCode()) {
            GenericIO.writelnString("Thread " + Thread.currentThread().getName() + ": DTEQStub: prepareNextLeg: Incorrect message type!");
            GenericIO.writelnString(inMessage.toString());
            System.exit(1);
        }
        clientCom.close();
    }
}
