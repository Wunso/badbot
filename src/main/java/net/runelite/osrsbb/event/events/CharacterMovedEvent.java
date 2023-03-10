package net.runelite.osrsbb.event.events;

import net.runelite.osrsbb.event.EventMulticaster;
import net.runelite.osrsbb.event.listeners.CharacterMovedListener;
import net.runelite.osrsbb.api.MethodContext;

import java.util.EventListener;

/**
 * A character moved event.
 */
public class CharacterMovedEvent extends RSEvent {
    private static final long serialVersionUID = 8883312847545757405L;

    private final MethodContext ctx;
    private final net.runelite.api.Actor character;
    private final int direction;
    private net.runelite.osrsbb.wrappers.RSCharacter wrapped;

    public CharacterMovedEvent(final MethodContext ctx, final net.runelite.api.Actor character, final int direction) {
        this.ctx = ctx;
        this.character = character;
        this.direction = direction;
    }

    @Override
    public void dispatch(final EventListener el) {
        ((CharacterMovedListener) el).characterMoved(this);
    }

    public net.runelite.osrsbb.wrappers.RSCharacter getCharacter() {
        if (wrapped == null) {
            if (character instanceof net.runelite.api.NPC) {
                final net.runelite.api.NPC npc = (net.runelite.api.NPC) character;
                wrapped = new net.runelite.osrsbb.wrappers.RSNPC(ctx, npc);
            } else if (character instanceof net.runelite.api.Player) {
                final net.runelite.api.Player player = (net.runelite.api.Player) character;
                wrapped = new net.runelite.osrsbb.wrappers.RSPlayer(ctx, player);
            }
        }
        return wrapped;
    }

    /**
     * 0 = NW
     * 1 = N
     * 2 = NE
     * 3 = W
     * 4 = E
     * 5 = SW
     * 6 = S
     * 7 = SE
     *
     * @return Returns the direction of the character movement event.
     */
    public int getDirection() {
        return direction;
    }

    @Override
    public long getMask() {
        return EventMulticaster.CHARACTER_MOVED_EVENT;
    }
}
