package net.runelite.osrsbb.wrappers.common;

import net.runelite.osrsbb.wrappers.RSTile;
import net.runelite.osrsbb.wrappers.subwrap.WalkerTile;

import net.runelite.api.coords.WorldPoint;

/**
 * An interface for position related information regarding sub-classes
 * (Tile position, position of animation, and adjusting the camera to the object)
 */
public interface Positionable {
    /*
    int getWorldX();

    int getWorldY();

    int getPlane();

    default WorldPoint getWorldLocation()
    {
        return new WorldPoint(getWorldX(), getWorldY(), getPlane());
    }
    */

    //////////////////////
    // OLD Positionable
    //////////////////////

    /**
     * Gets the tile position of the entities animation (where it *appears* to be)
     * @return the animable tile position of the entity if it exists, else null
     */
    //RSTile getAnimablePosition();

    /**
     * Gets the tile position of the entity as provided by the client
     * @return the tile position of the entity if it exists, else null
     */
    WalkerTile getLocation();

    /**
     * Turns the camera to face the entity
     * @return <code>True</code> if the camera has been turned to face the entity
     */
    boolean turnTo();

}
