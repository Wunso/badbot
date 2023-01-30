package net.runelite.osrsbb.internal.listeners;

import net.runelite.osrsbb.internal.ScriptHandler;
import net.runelite.osrsbb.launcher.BadLite;
import net.runelite.osrsbb.script.Script;

public interface ScriptListener {
    public void scriptStarted(ScriptHandler handler, Script script);

    public void scriptStopped(ScriptHandler handler, Script script);

    public void scriptResumed(ScriptHandler handler, Script script);

    public void scriptPaused(ScriptHandler handler, Script script);

    public void inputChanged(BadLite bot, int mask);
}
