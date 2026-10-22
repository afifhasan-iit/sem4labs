package minigit.observer;

// Observer Pattern
public class UI implements Observer
{
    @Override
    public void onCommit(String message)
    {
        System.out.println("[UI] Updated after commit: " + message);
    }
}
