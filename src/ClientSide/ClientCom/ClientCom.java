package ClientSide.ClientCom;

import genclass.GenericIO;

import java.io.*;
import java.net.*;

// BASED OFF THE ONE GIVEN TO US
public class ClientCom {

    private Socket commSocket;

    private String serverHostName;
    private int serverHostPort;

    private ObjectInputStream inputStream;
    private ObjectOutputStream outputStream;

    public ClientCom(String hostName, int hostPort) {
        this.serverHostName = hostName;
        this.serverHostPort = hostPort;
    }

    public boolean open()
    {
        boolean success = true;
        SocketAddress serverAddress = new InetSocketAddress(serverHostName, serverHostPort);

        try {
            commSocket = new Socket();
            commSocket.connect(serverAddress);
        } catch(UnknownHostException e) {
            GenericIO.writelnString(Thread.currentThread().getName() + " - the target server is an unknown host: " + serverHostName + "!");
            e.printStackTrace();
            System.exit(1);
        } catch(NoRouteToHostException e) {
            GenericIO.writelnString(Thread.currentThread().getName() + " - the target server is unreachable: " + serverHostName + "!");
            e.printStackTrace();
            System.exit(1);
        } catch(ConnectException e) {
            GenericIO.writelnString(Thread.currentThread().getName() + " - the server did not respond at: " + serverHostName + "." + serverHostPort + "!");
            if (e.getMessage().equals("Connection refused"))
                success = false;
            else {
                GenericIO.writelnString(e.getMessage() + "!");
                e.printStackTrace();
                System.exit(1);
            }
        } catch(SocketTimeoutException e) {
            GenericIO.writelnString(Thread.currentThread().getName() + " - connection timed out while attempting to reach: " + serverHostName + "." + serverHostPort + "!");
            success = false;
        } catch(IOException e) {
            GenericIO.writelnString(Thread.currentThread().getName() + " - unidentified error while connecting to: " + serverHostName + "." + serverHostPort + "!");
            e.printStackTrace();
            System.exit(1);
        }

        if(!success) return (success);

        try {
            outputStream = new ObjectOutputStream(commSocket.getOutputStream ());
        } catch(IOException e) {
            GenericIO.writelnString(Thread.currentThread().getName() + " - could not open the socket's output stream!");
            e.printStackTrace();
            System.exit(1);
        }

        try {
            inputStream = new ObjectInputStream(commSocket.getInputStream ());
        } catch(IOException e) {
            GenericIO.writelnString(Thread.currentThread ().getName () + " - could not open the socket's input stream!");
            e.printStackTrace();
            System.exit(1);
        }

        return (success);
    }

    public void close()
    {
        try {
            inputStream.close();
        } catch(IOException e) {
            GenericIO.writelnString(Thread.currentThread().getName() + " - could not close the socket's input stream!");
            e.printStackTrace();
            System.exit(1);
        }

        try {
            outputStream.close();
        } catch(IOException e) {
            GenericIO.writelnString(Thread.currentThread().getName() + " - could not close the socket's output stream!");
            e.printStackTrace();
            System.exit(1);
        }

        try {
            commSocket.close();
        } catch(IOException e) {
            GenericIO.writelnString(Thread.currentThread().getName() + " - could not close the communication socket!");
            e.printStackTrace();
            System.exit(1);
        }
    }

    public Object readObject()
    {
        Object fromServer = null;

        try {
            fromServer = inputStream.readObject();
        } catch(InvalidClassException e) {
            GenericIO.writelnString(Thread.currentThread().getName() + " - could not deserialize the read object!");
            e.printStackTrace();
            System.exit(1);
        } catch(IOException e) {
            GenericIO.writelnString(Thread.currentThread().getName() + " - error reading an object from the socket's input stream!");
            e.printStackTrace();
            System.exit(1);
        } catch(ClassNotFoundException e) {
            GenericIO.writelnString(Thread.currentThread().getName() + " - the read object's data type is unknown!");
            e.printStackTrace();
            System.exit(1);
        }

        return fromServer;
    }

    public void writeObject(Object toServer)
    {
        try {
            outputStream.writeObject(toServer);
        }
        catch(InvalidClassException e) {
            GenericIO.writelnString(Thread.currentThread().getName() + " - the object being written could not be serialized!");
            e.printStackTrace();
            System.exit(1);
        } catch(NotSerializableException e) {
            GenericIO.writelnString(Thread.currentThread().getName() + " - the object being written belongs to a non-serializable data type!");
            e.printStackTrace();
            System.exit(1);
        } catch(IOException e) {
            GenericIO.writelnString(Thread.currentThread().getName() + " - error writing an object into the socket's output stream!");
            e.printStackTrace();
            System.exit(1);
        }
    }
}
