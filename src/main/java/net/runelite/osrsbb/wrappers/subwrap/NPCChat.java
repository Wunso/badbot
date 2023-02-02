package net.runelite.osrsbb.wrappers.subwrap;

import net.runelite.osrsbb.api.Interfaces;
import net.runelite.osrsbb.api.MethodContext;

public class NPCChat extends Interfaces {
    public NPCChat(MethodContext ctx) {
        super(ctx);
    }

    public String getMessage() {
        return "";
    }

    public String getName() {
        return "";
    }

    public String[] getOptions() {
        return new String[]{};
    }

    public boolean selectOption(String option, boolean wait) {
        return false;
    }
}
