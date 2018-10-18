package ClientSide;

import ServerSide.ClientThread;

import javax.sound.sampled.ReverbType;
import java.io.IOException;
import java.net.*;
import java.sql.SQLOutput;
import java.util.Scanner;

public class ClientMain {



    private static boolean isAccepted;
    private Socket socket;
    private static SendMessages sendMessages;
    private static RecieveMessages recieveMessages;
    private static Heartbeat heartbeat;
    private static ThreadLock threadLock;
    private static Thread sendThread;
    private static Thread recieveThread;
    private static Thread hearbeatThread;

    public static void main(String[] args)

    {
        try
        {
            isAccepted = false;
            Socket socket = new Socket("127.0.0.1", 3333);

             sendMessages = new SendMessages(socket, threadLock);
             recieveMessages = new RecieveMessages(socket, threadLock);
             heartbeat = new Heartbeat(sendMessages);

            sendThread = new Thread(sendMessages);
            sendThread.start();


            recieveThread = new Thread(recieveMessages);
            recieveThread.start();


            hearbeatThread = new Thread (heartbeat);
            hearbeatThread.start();

        }
        catch(IOException e)
        {
            System.out.println("Fatal Connection error");
        }

    }

    public synchronized void closeConnection()
    {


    }

    public synchronized static boolean getIsAccepted()
    {
        return isAccepted;
    }

    public synchronized static void changeIsAccepted(Boolean value)
    {
        isAccepted = value;
    }



    public synchronized static void changeBool(Boolean value){
        isAccepted = value;
    }

    public synchronized static boolean getBool(){
        return isAccepted;
    }


}
