package ClientSide;

public class ThreadLock
{

    private final Object lock = new Object();

    public void signal()
    {
        synchronized (lock)
        {
            lock.notify();
        }
    }

    public void await() throws InterruptedException
    {
        synchronized (lock)
        {
            lock.wait();
        }
    }
}

