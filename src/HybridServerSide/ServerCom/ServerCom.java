package HybridServerSide.ServerCom;

import genclass.GenericIO;

import java.io.*;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

// BASED OFF THE ONE GIVEN TO US
public class ServerCom {

    private ServerSocket listeningSocket = null;
    private Socket commSocket;

    private int serverPortNumber;

    private ObjectInputStream inputStream;
    private ObjectOutputStream outputStream;

    public ServerCom(int portNumber) {
        this.serverPortNumber = portNumber;
    }

    public ServerCom(int portNumber, ServerSocket listeningSocket) {
        this.serverPortNumber = portNumber;
        this.listeningSocket = listeningSocket;
    }

    public void start()
    {
        try {
            listeningSocket = new ServerSocket(serverPortNumber);
        } catch(BindException e) {
            GenericIO.writelnString(Thread.currentThread().getName() + " - could not bind the listening socket to the port: " + serverPortNumber + "!");
            e.printStackTrace();
            System.exit (1);
        } catch(IOException e) {
            GenericIO.writelnString(Thread.currentThread().getName() + " - unknown error when associating the socket to port: " + serverPortNumber + "!");
            e.printStackTrace();
            System.exit(1);
        }
    }

    public void end()
    {
        try {
            listeningSocket.close();
        } catch(IOException e) {
            GenericIO.writelnString(Thread.currentThread().getName() + " - could not close the listening socket!");
            e.printStackTrace();
            System.exit(1);
        }
    }

    public ServerCom accept()
    {
        ServerCom serverCom;

        serverCom = new ServerCom(serverPortNumber, listeningSocket);
        try {
            serverCom.commSocket = listeningSocket.accept();
        } catch(SocketException e) {
            GenericIO.writelnString(Thread.currentThread().getName() + " - the listening socket closed while listening!");
            e.printStackTrace();
            System.exit(1);
        } catch(IOException e) {
            GenericIO.writelnString(Thread.currentThread().getName() + " - could not open a communication channel for the pending request!");
            e.printStackTrace();
            System.exit(1);
        }

        try {
            serverCom.inputStream = new ObjectInputStream(serverCom.commSocket.getInputStream());
        } catch(IOException e) {
            GenericIO.writelnString(Thread.currentThread().getName() + " - could not open the socket's input stream!");
            e.printStackTrace();
            System.exit(1);
        }

        try {
            serverCom.outputStream = new ObjectOutputStream(serverCom.commSocket.getOutputStream ());
        } catch(IOException e) {
            GenericIO.writelnString(Thread.currentThread().getName() + " - could not open the socket's output stream!");
            e.printStackTrace();
            System.exit(1);
        }

        return serverCom;
    }

    public void close()
    {
        try {
            inputStream.close();
        } catch(IOException e) {
            GenericIO.writelnString (Thread.currentThread().getName() + " - could not close the socket's input stream!");
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
        Object fromClient = null;

        try {
            fromClient = inputStream.readObject ();
        } catch(InvalidClassException e) {
            GenericIO.writelnString(Thread.currentThread().getName() + " - could not deserialize the read object!!");
            e.printStackTrace();
            System.exit(1);
        } catch(IOException e) {
            GenericIO.writelnString (Thread.currentThread().getName() + " - error reading an object from the socket's input stream!");
            e.printStackTrace();
            System.exit(1);
        } catch(ClassNotFoundException e) {
            GenericIO.writelnString (Thread.currentThread().getName() + " - the read object's data type is unknown!");
            e.printStackTrace();
            System.exit(1);
        }

        return fromClient;
    }

    public void writeObject(Object toClient)
    {
        try {
            outputStream.writeObject (toClient);
        } catch(InvalidClassException e) {
            GenericIO.writelnString(Thread.currentThread().getName() + " - the object being written could not be serialized!");
            e.printStackTrace();
            System.exit(1);
        } catch(NotSerializableException e) {
            GenericIO.writelnString (Thread.currentThread().getName() + " - the object being written belongs to a non-serializable data type!");
            e.printStackTrace();
            System.exit(1);
        } catch (IOException e) {
            GenericIO.writelnString (Thread.currentThread().getName() + " - error writing an object into the socket's output stream!");
            e.printStackTrace();
            System.exit(1);
        }
    }

    public void setTimeout (int time)
    {
        try {
            listeningSocket.setSoTimeout (time);
        }
        catch (SocketException e) {
            GenericIO.writelnString (Thread.currentThread ().getName () + " - error while establishing a listening timeout!");
            e.printStackTrace ();
            System.exit (1);
        }
    }

}
