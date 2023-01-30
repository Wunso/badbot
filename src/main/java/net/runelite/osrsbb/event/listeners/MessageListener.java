package net.runelite.osrsbb.event.listeners;

import net.runelite.osrsbb.event.events.MessageEvent;

import java.util.EventListener;

public interface MessageListener extends EventListener {
    abstract void messageReceived(MessageEvent e);
}
