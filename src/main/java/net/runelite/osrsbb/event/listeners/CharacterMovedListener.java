package net.runelite.osrsbb.event.listeners;

import net.runelite.osrsbb.event.events.CharacterMovedEvent;

import java.util.EventListener;

public interface CharacterMovedListener extends EventListener  {
    public void characterMoved(CharacterMovedEvent e);
}
