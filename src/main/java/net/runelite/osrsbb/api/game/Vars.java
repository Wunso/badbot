package net.runelite.osrsbb.api.game;

import net.runelite.osrsbb.api.MethodContext;
import net.runelite.osrsbb.api.MethodProvider;
import net.runelite.client.callback.ClientThread;

public class Vars extends MethodProvider {
    public Vars(MethodContext ctx) {
        super(ctx);
    }

    public static int getBit(int id) {
        return methods.client.getVarbitValue(methods.client.getVarps(), id);
    }
    public static int getVarp(int id) { return methods.client.getVarpValue(id); }
}
