import java.util.Timer;
import java.util.TimerTask;

public class Heartbeat extends TimerTask {

    @Override
    public void run() {
        completeTask();
    }

    private void completeTask() {
        try {

            Thread.sleep(60000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String args[]){
        TimerTask heartbeat = new Heartbeat();
        //running timer task as daemon thread
        Timer timer = new Timer(true);
        timer.scheduleAtFixedRate(heartbeat, 0, 60*1000);
        System.out.println("Hearbeat alive");

        try {
            Thread.sleep(1000000000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}