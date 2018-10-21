package ClientSide;

import ServerSide.ClientThread;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class RecieveMessages implements Runnable {

    private Socket socket;
    private String username;
    private String recievedMessage;
    private BufferedReader input;


    public RecieveMessages(Socket socket)
    {
        this.socket = socket;
    }

    @Override
    public synchronized void run()

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
                        ClientMain.sendMessages.resumeThread();
                        break;

                    case "J_OK":
                        ClientMain.changeBool(true);
                        ClientMain.sendMessages.resumeThread();
                        System.out.println("Velkommen");
                        System.out.println("For at forlade, skriv exit ");
                        System.out.println("For at få en liste over alle brugere, skriv list");
                        break;

                    case "DATA":
                        int index = this.findFirstSpace(recievedMessage);
                        String messageFromUser = recievedMessage.substring(index);

                        if (messageFromUser!= null )
                        {
                            System.out.println(username + " " + messageFromUser );
                        }
                        break;

                    case"LIST":
                        String[] listOfUsers = recievedMessage.split(" ")[1].split(":");
                        System.out.println("Folk i chatten");
                        for (int i = 0; i < listOfUsers.length ; i++)
                        {
                            System.out.println(listOfUsers[i]);
                        }
                        break;



                }

            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }


    }
    private int findFirstSpace(String a) {
        int i = 0;
        while(a.charAt(i) != ' ') {
            i++;
        }
        return i+1;
    }
}
