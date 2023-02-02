package net.runelite.osrsbb.api;

import net.runelite.osrsbb.internal.wrappers.TileFlags;
import net.runelite.osrsbb.wrappers.RSTile;

import java.util.HashMap;

public class Web extends MethodProvider {
    public static final HashMap<RSTile, TileFlags> map = new HashMap<>();
    public static boolean loaded = false;

    Web(final MethodContext ctx) {
        super(ctx);
    }
}
