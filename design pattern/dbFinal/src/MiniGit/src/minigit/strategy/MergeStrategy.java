package minigit.strategy;

import minigit.composite.Files;

// Strategy Pattern
public interface MergeStrategy
{
    Files applyStrategy(Files source, Files destination);
}
