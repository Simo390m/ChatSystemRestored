package ClientSide;

import ServerSide.ClientThread;
import jdk.dynalink.beans.StaticClass;

import javax.sound.sampled.ReverbType;
import java.io.IOException;
import java.net.*;
import java.sql.SQLOutput;
import java.util.Scanner;

public class ClientMain {



    private static boolean isAccepted;
    static Socket socket;
    static RecieveMessages recieveMessages;
    static SendMessages sendMessages;
    private static HeartBeat heartbeat;
    static Thread sendThread;
    private static Thread recieveThread;
    private static Thread hearbeatThread;




    public static void main(String[] args)

    {
        try
        {
            isAccepted = false;
            Socket socket = new Socket("127.0.0.1", 3333);


             sendMessages = new SendMessages(socket);
             recieveMessages = new RecieveMessages(socket);
             heartbeat = new HeartBeat(sendMessages);

            sendThread = new Thread(sendMessages);
            sendThread.start();


            recieveThread = new Thread(recieveMessages);
            recieveThread.start();


           /* hearbeatThread = new Thread (heartbeat);
            hearbeatThread.start();
            */

        }
        catch(IOException e)
        {
            if (!socket.isClosed())
            System.out.println("Fatal Connection error");
            closeConnection();
        }

    }

    public synchronized static void closeConnection()
    {
        try {
            socket.close();
            sendMessages.closeOut();
            recieveMessages.closeIn();
            changeBool(false);
        }catch (IOException e){

        }

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
