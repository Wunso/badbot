/*
package net.runelite.osrsbb.internal.naturalmouse;

import com.github.joonasvali.naturalmouse.api.SystemCalls;
import net.runelite.osrsbb.internal.InputManager;

import java.awt.*;

public class RSBSystemCalls implements SystemCalls  {
    InputManager inputManager;
    public RSBSystemCalls(InputManager inputManager) {
        this.inputManager = inputManager;
    }

    @Override
    public long currentTimeMillis() {
        return System.currentTimeMillis();
    }

    @Override
    public void sleep(long time) throws InterruptedException {
        Thread.sleep(time);
    }

    @Override
    public Dimension getScreenSize() {
        return Toolkit.getDefaultToolkit().getScreenSize();
    }

    @Override
    public void setMousePosition(int x, int y) {
        inputManager.hopMouse(x, y);
    }
}
*/
