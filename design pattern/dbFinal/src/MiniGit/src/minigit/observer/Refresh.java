package minigit.observer;

// Observer Pattern
public class Refresh implements Observer
{
    @Override
    public void onCommit(String message)
    {
        System.out.println("[Refresh] Repository refreshed after commit: " + message);
    }
}
