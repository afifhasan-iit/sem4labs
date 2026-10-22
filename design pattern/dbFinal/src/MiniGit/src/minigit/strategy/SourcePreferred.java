package minigit.strategy;

import minigit.composite.Files;

// Strategy Pattern
public class SourcePreferred implements MergeStrategy
{
    @Override
    public Files applyStrategy(Files source, Files destination)
    {
        return source;
    }
}
