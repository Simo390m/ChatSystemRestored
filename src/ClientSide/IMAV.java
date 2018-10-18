package ClientSide;

import com.sun.tools.javac.Main;

public class IMAV implements Runnable
{
    public IMAV() {}

    @Override
    public void run()
    {
     while (true)
     {
         while(ClientMain.getBool())
         {
             System.out.println("Er accepteret");
         }
     }
    }
}
