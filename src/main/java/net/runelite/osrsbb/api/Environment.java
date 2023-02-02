package net.runelite.osrsbb.api;

import net.runelite.osrsbb.script.Random;
import net.runelite.osrsbb.script.ScriptManifest;
import net.runelite.osrsbb.util.ScreenshotUtil;

import java.awt.image.BufferedImage;

/**
 * Bot environment related operations.
 */
public class Environment extends MethodProvider {
    public static final int INPUT_MOUSE = 1;
    public static final int INPUT_KEYBOARD = 2;

    public Environment(MethodContext ctx) {
        super(ctx);
    }

    /**
     * Controls the available means of user input when user input is disabled.
     *
     * Disable all: <code>setUserInput(0);</code>
     * Enable keyboard only:
     * <code>setUserInput(Environment.INPUT_KEYBOARD);</code>
     * Enable mouse and keyboard:
     * <code>setUserInput(Environment.INPUT_MOUSE | Environment.INPUT_KEYBOARD);</code>
     *
     * @param mask flags indicating which types of input to allow
     */
    public void setUserInput(int mask) {
        methods.badLite.getScriptHandler().updateInput(methods.badLite, mask);
    }

    /**
     * Takes and saves a screenshot.
     *
     * @param hideUsername <code>true</code> to cover the player's username; otherwise
     *                     <code>false</code>
     */
    public void saveScreenshot(boolean hideUsername) {
        ScreenshotUtil.saveScreenshot(methods.badLite, hideUsername);
    }

    /**
     * Takes a screenshot.
     *
     * @param hideUsername <code>true</code> to cover the player's username; otherwise
     *                     <code>false</code>
     * @return The screen capture image.
     */
    public BufferedImage takeScreenshot(boolean hideUsername) {
        return ScreenshotUtil.takeScreenshot(methods.badLite, hideUsername);
    }

    /**
     * Enables a random event solver.
     *
     * @param name the anti-random's (manifest) name (case insensitive)
     * @return <code>true</code> if random was found and set to enabled; otherwise
     *         <code>false</code>
     */
    public boolean enableRandom(String name) {
        for (final Random random : methods.badLite.getScriptHandler().getRandoms()) {
            if (random.getClass().getAnnotation(ScriptManifest.class).name().toLowerCase().equals(name.toLowerCase())) {
                if (!random.isEnabled()) {
                    random.setEnabled(true);
                }
                return true;
            }
        }
        return false;
    }

    /**
     * Disables a random event solver.
     *
     * @param name the anti-random's (manifest) name (case insensitive)
     * @return <code>true</code> if random was found and set to disabled; otherwise
     *         <code>false</code>
     */
    public boolean disableRandom(String name) {
        for (final Random random : methods.badLite.getScriptHandler().getRandoms()) {
            if (random.getClass().getAnnotation(ScriptManifest.class).name().toLowerCase().equals(name.toLowerCase())) {
                if (random.isEnabled()) {
                    random.setEnabled(false);
                }
                return true;
            }
        }
        return false;
    }

    /**
     * Enables all random event solvers.
     */
    public void enableRandoms() {
        for (final Random random : methods.badLite.getScriptHandler().getRandoms()) {
            if (!random.isEnabled()) {
                random.setEnabled(true);
            }
        }
    }

    /**
     * Disables all randoms.
     *
     */
    public void disableRandoms() {
        for (final Random random : methods.badLite.getScriptHandler().getRandoms()) {
            if (random.isEnabled()) {
                random.setEnabled(false);
            }
        }
    }
}
