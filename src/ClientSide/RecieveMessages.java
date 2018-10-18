package ClientSide;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class RecieveMessages implements Runnable {

    Socket socket;
    String username;
    String recievedMessage;
    BufferedReader input;
    ThreadLock threadLock;

    public RecieveMessages(Socket socket, ThreadLock threadLock) {
        this.socket = socket;
        this.threadLock = threadLock;
    }

    @Override
    public void run()

    {

        try {
            input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            while((recievedMessage = input.readLine()) != null)
            {
                switch (recievedMessage.substring(0,4))
                {
                    case "J_ER":
                        String[] errorMessageArray;
                        errorMessageArray = recievedMessage.split(":");
                        System.out.println("Fejlbeksed type: " + errorMessageArray[0].split(" ")[1] + ": " + errorMessageArray[1]);
                        threadLock.signal();
                        break;

                    case "J_OK":
                        ClientMain.changeBool(true);
                        threadLock.signal();
                        System.out.println("Velkommen");
                        System.out.println("For at forlade, skriv exit ");
                        System.out.println("For at få en liste over alle brugere, skriv list");
                        break;

                    case "DATA":
                        String [] messageFromUser = recievedMessage.split(":");
                        if (messageFromUser[1]!= null )
                        {
                            System.out.println(messageFromUser );
                        }


                }

            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }

    }
}
