package net.runelite.osrsbb.wrappers;

import net.runelite.api.Actor;
import net.runelite.api.NPC;
import net.runelite.cache.definitions.NpcDefinition;
import net.runelite.osrsbb.api.MethodContext;
import net.runelite.osrsbb.wrappers.common.CacheProvider;

import java.io.File;
import java.lang.ref.SoftReference;
import java.util.HashMap;

public class RSNPC extends RSCharacter implements CacheProvider<NpcDefinition> {
    private static HashMap<Integer, NpcDefinition> npcDefinitionCache;
    private static HashMap<Integer, File> npcFileCache;
    private final SoftReference<NPC> npc;
    private final NpcDefinition def;

    public RSNPC(final MethodContext ctx, final NPC npc) {
        super(ctx);
        this.npc =  new SoftReference<>(npc);
        this.def = (npc.getId() != -1) ? (NpcDefinition) createDefinition(npc.getId()) : null;
    }

    @Override
    public Actor getAccessor() {
        return npc.get();
    }

    @Override
    public Actor getInteracting() {
        return getAccessor().getInteracting();
    }

    public String[] getActions() {
        NpcDefinition def = getDef();

        if (def != null) {
            return def.getActions();
        }
        return new String[0];
    }

    public int getID() {
        NpcDefinition def = getDef();
        if (def != null) {
            return def.getId();
        }
        return -1;
    }

    @Override
    public String getName() {
        NpcDefinition def = getDef();
        if (def != null) {
            return def.getName();
        }
        return "";
    }

    @Override
    public int getLevel() {
        NPC c = npc.get();
        if (c == null) {
            return -1;
        } else {
            return c.getCombatLevel();
        }
    }

    /**
     * @return <code>true</code> if RSNPC is interacting with RSPlayer; otherwise
     *         <code>false</code>.
     */
    @Override
    public boolean isInteractingWithLocalPlayer() {
        RSNPC npc = this;
        return npc.getInteracting() != null
                && npc.getInteracting().equals(methods.players.getMyPlayer().getAccessor());
    }

    NpcDefinition getDef() {
        return this.def;
    }

    public RSTile getPosition() {
        return getLocation();
    }
}
