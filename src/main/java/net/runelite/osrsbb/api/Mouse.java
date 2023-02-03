package net.runelite.osrsbb.api;

import net.runelite.api.MenuAction;
import net.runelite.api.Point;
import net.runelite.osrsbb.internal.MouseHandler;

import static net.runelite.osrsbb.api.Methods.sleep;

/**
 * Mouse related operations.
 */
public class Mouse extends MethodProvider {
    private int mouseSpeed = MouseHandler.DEFAULT_MOUSE_SPEED;
    private int minReaction = 50;
    private int maxReaction = 350;

    Mouse(final MethodContext ctx) {
        super(ctx);
    }

    /**
     * Configure reaction speed
     * @param min min mouse 'reaction' speed, -1 will be ignored
     * @param max max mouse 'reaction' speed, -1 will be ignored
     */
    public void setReactionSpeed(int min, int max) {
        if (max != -1) {
            minReaction = min;
        }
        if (max != -1) {
            maxReaction = max;
        }
    }

    /**
     * Drag the mouse from the current position to a certain other position.
     *
     * @param x The x coordinate to drag to.
     * @param y The y coordinate to drag to.
     */
    public void drag(final int x, final int y) {
        methods.inputManager.dragMouse(x, y);
    }

    /**
     * Drag the mouse from the current position to a certain other position.
     *
     * @param p The point to drag to.
     * @see #drag(int, int)
     */
    public void drag(final Point p) {
        drag(p.getX(), p.getY());
    }

    /**
     * Clicks the mouse at its current location.
     *
     * @param leftClick <code>true</code> to left-click, <code>false</code>to right-click.
     */
    public void click(final boolean leftClick) {
        click(leftClick, MouseHandler.DEFAULT_MAX_MOVE_AFTER);
    }

    public synchronized void click(final boolean leftClick,
                                   final int moveAfterDist) {
        methods.inputManager.clickMouse(leftClick);
        if (moveAfterDist > 0) {
            sleep(random(minReaction, maxReaction));
            Point pos = getLocation();
        }
    }

    public synchronized void testMenuActionClick(final Point p) {
        methods.client.interact(
                0,
                MenuAction.WALK.getId(),
                p.getX(),
                p.getY()
        );
    }

    /**
     * Moves the mouse to a given location then clicks.
     *
     * @param x         x coordinate
     * @param y         y coordinate
     * @param leftClick <code>true</code> to left-click, <code>false</code>to right-click.
     */
    public void click(final int x, final int y, final boolean leftClick) {
        click(x, y, 0, 0, leftClick);
    }

    /**
     * Moves the mouse to a given location with given randomness then clicks.
     *
     * @param x         x coordinate
     * @param y         y coordinate
     * @param randX     x randomness (added to x)
     * @param randY     y randomness (added to y)
     * @param leftClick <code>true</code> to left-click, <code>false</code>to right-click.
     */
    public synchronized void click(final int x, final int y, final int randX,
                                   final int randY, final boolean leftClick) {
        sleep(random(minReaction, maxReaction));
        click(leftClick, MouseHandler.DEFAULT_MAX_MOVE_AFTER);
    }

    /**
     * Moves the mouse to a given location with given randomness then clicks,
     * then moves a random distance up to <code>afterOffset</code>.
     *
     * @param x             x coordinate
     * @param y             y coordinate
     * @param randX         x randomness (added to x)
     * @param randY         y randomness (added to y)
     * @param leftClick     <code>true</code> to left-click, <code>false</code>to right-click.
     * @param moveAfterDist The maximum distance in pixels to move on both axes shortly
     *                      after moving to the destination.
     */
    public synchronized void click(final int x, final int y, final int randX,
                                   final int randY, final boolean leftClick, final int moveAfterDist) {
        sleep(random(minReaction, maxReaction));
        click(leftClick, moveAfterDist);
    }

    /**
     * Moves the mouse to a given location then clicks.
     *
     * @param p         The point to click.
     * @param leftClick <code>true</code> to left-click, <code>false</code>to right-click.
     */
    public void click(final Point p, final boolean leftClick) { click(p.getX(), p.getY(), leftClick); }

    public void click(final Point p, final int x, final int y,
                      final boolean leftClick) {
        click(p.getX(), p.getY(), x, y, leftClick);
    }

    /**
     * Moves the mouse to a given location with given randomness then clicks,
     * then moves a random distance up to <code>afterOffset</code>.
     *
     * @param p             The destination Point.
     * @param x             x coordinate
     * @param y             y coordinate
     * @param leftClick     <code>true</code> to left-click, <code>false</code>to right-click.
     * @param moveAfterDist The maximum distance in pixels to move on both axes shortly
     *                      after moving to the destination.
     */
    public void click(final Point p, final int x, final int y,
                      final boolean leftClick, final int moveAfterDist) {
        click(p.getX(), p.getY(), x, y, leftClick, moveAfterDist);
    }

    /**
     * Moves the mouse slightly depending on where it currently is and clicks.
     */
    public void clickSlightly() {
        Point p = new Point(
                (int) (getLocation().getX() + (Math.random() * 50 > 25 ? 1 : -1)
                        * (30 + Math.random() * 90)), (int) (getLocation()
                .getY() + (Math.random() * 50 > 25 ? 1 : -1)
                * (30 + Math.random() * 90)));
        if (p.getX() < 1 || p.getY() < 1 || p.getX() > 761 || p.getY() > 499) {
            clickSlightly();
            return;
        }
        click(p, true);
    }

    /**
     * Gets the mouse speed.
     *
     * @return the current mouse speed.
     * @see #setSpeed(int)
     */
    public int getSpeed() {
        return mouseSpeed;
    }

    /**
     * Changes the mouse speed
     *
     * @param speed The speed to move the mouse at. 4-10 is advised, 1 being the
     *              fastest.
     * @see #getSpeed()
     */
    public void setSpeed(final int speed) {
        mouseSpeed = speed;
    }

    /**
     * @param maxDistance The maximum distance outwards.
     * @return A random x value between the current client location and the max
     *         distance outwards.
     */
    public int getRandomX(final int maxDistance) {
        Point p = getLocation();
        if (p.getX() < 0 || maxDistance <= 0) {
            return -1;
        }
        if (random(0, 2) == 0) {
            return p.getX() - random(0, p.getX() < maxDistance ? p.getX() : maxDistance);
        } else {
            int dist = methods.game.getWidth() - p.getX();
            return p.getX()
                    + random(1, dist < maxDistance && dist > 0 ? dist
                    : maxDistance);
        }
    }

    /**
     * @param maxDistance The maximum distance outwards.
     * @return A random y value between the current client location and the max
     *         distance outwards.
     */
    public int getRandomY(final int maxDistance) {
        Point p = getLocation();
        if (p.getY() < 0 || maxDistance <= 0) {
            return -1;
        }
        if (random(0, 2) == 0) {
            return p.getY() - random(0, p.getY() < maxDistance ? p.getY() : maxDistance);
        } else {
            int dist = methods.game.getHeight() - p.getY();
            return p.getY()
                    + random(1, dist < maxDistance && dist > 0 ? dist
                    : maxDistance);
        }
    }

    /**
     * The location of the bot's mouse; or Point(-1, -1) if off screen.
     *
     * @return A <code>Point</code> containing the bot's mouse's x and y coordinates.
     */
    public Point getLocation() {
        return new Point(methods.virtualMouse.getClientX(), methods.virtualMouse.getClientY());
    }

    /**
     * @return The <code>Point</code> at which the bot's mouse was last clicked.
     */
    public Point getPressLocation() {
        return new Point(methods.virtualMouse.getClientPressX(), methods.virtualMouse.getClientPressY());
    }

    /**
     * @return The system time when the bot's mouse was last pressed.
     */
    public long getPressTime() {
        return methods.virtualMouse.getClientPressTime();
    }

    /**
     * @return <code>true</code> if the bot's mouse is present.
     */
    public boolean isPresent() {
        return methods.virtualMouse.isClientPresent();
    }

    /**
     * @return <code>true</code> if the bot's mouse is pressed.
     */
    public boolean isPressed() {
        return methods.virtualMouse.isClientPressed();
    }
}
