package minigit.strategy;

import minigit.composite.Files;

// Strategy Pattern
public class DestinationPreferred implements MergeStrategy
{
    @Override
    public Files applyStrategy(Files source, Files destination)
    {
        return destination;
    }
}
