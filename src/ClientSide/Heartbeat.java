package ClientSide;



public class Heartbeat implements Runnable
{
    private SendMessages send;

    public Heartbeat(SendMessages send)
    {
        this.send = send;
    }

    @Override
    public void run()
    {
        while (true)
        {
            while(ClientMain.getBool())
            {
                synchronized (this)
                {
                    try
                    {
                     wait(60*1000);
                    }
                    catch (InterruptedException e)
                    {
                        e.printStackTrace();
                    }
                }
                send.send("IMAV");
            }
        }


    }
}