package net.runelite.osrsbb.internal.listeners;

import net.runelite.osrsbb.internal.PassiveScriptHandler;
import net.runelite.osrsbb.script.PassiveScript;

public interface PassiveScriptListener {
    public void scriptStarted(PassiveScriptHandler handler, PassiveScript script);

    public void scriptStopped(PassiveScriptHandler handler, PassiveScript script);
}
