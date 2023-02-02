package net.runelite.osrsbb.util;

import net.runelite.api.GameState;
import net.runelite.osrsbb.api.Game;
import net.runelite.osrsbb.api.MethodContext;
import net.runelite.osrsbb.api.MethodProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.function.BooleanSupplier;

public class Time extends MethodProvider {

    Time(final MethodContext ctx) {
        super(ctx);
    }
    private static final Logger logger = LoggerFactory.getLogger(Time.class);
    private static final int DEFAULT_POLLING_RATE = 10;

    public static boolean sleep(long ms)
    {
        if (methods.client.isClientThread())
        {
            logger.debug("Tried to sleep on client thread!");
            return false;
        }

        try
        {
            Thread.sleep(ms);
            return true;
        }
        catch (InterruptedException e)
        {
            logger.debug("Sleep interrupted");
        }

        return false;
    }

    public static boolean sleep(int min, int max)
    {
        return sleep(Rand.nextInt(min, max));
    }

    public static boolean sleepUntil(BooleanSupplier supplier, BooleanSupplier resetSupplier, int pollingRate, int timeOut)
    {
        if (methods.client.isClientThread())
        {
            logger.debug("Tried to sleepUntil on client thread!");
            return false;
        }

        long start = System.currentTimeMillis();
        while (!supplier.getAsBoolean())
        {
            if (System.currentTimeMillis() > start + timeOut)
            {
                return false;
            }

            if (resetSupplier.getAsBoolean())
            {
                start = System.currentTimeMillis();
            }

            if (!sleep(pollingRate))
            {
                return false;
            }
        }

        return true;
    }

    public static boolean sleepUntil(BooleanSupplier supplier, BooleanSupplier resetSupplier, int timeOut)
    {
        return sleepUntil(supplier, resetSupplier, DEFAULT_POLLING_RATE, timeOut);
    }

    public static boolean sleepUntil(BooleanSupplier supplier, int pollingRate, int timeOut)
    {
        return sleepUntil(supplier, () -> false, pollingRate, timeOut);
    }

    public static boolean sleepUntil(BooleanSupplier supplier, int timeOut)
    {
        return sleepUntil(supplier, DEFAULT_POLLING_RATE, timeOut);
    }

    public static boolean sleepTicks(int ticks)
    {
        if (methods.client.isClientThread())
        {
            logger.debug("Tried to sleep on client thread!");
            return false;
        }

        if (Game.getClientState() == GameState.LOGIN_SCREEN || Game.getClientState() == GameState.LOGIN_SCREEN_AUTHENTICATOR)
        {
            return false;
        }

        for (int i = 0; i < ticks; i++)
        {
            long start = methods.client.getTickCount();

            while (methods.client.getTickCount() == start)
            {
                try
                {
                    Thread.sleep(DEFAULT_POLLING_RATE);
                }
                catch (InterruptedException e)
                {
                    logger.debug("Sleep interrupted");
                    return false;
                }
            }
        }

        return true;
    }

    public static boolean sleepTick()
    {
        return sleepTicks(1);
    }

    public static boolean sleepTicksUntil(BooleanSupplier supplier, int ticks)
    {
        if (methods.client.isClientThread())
        {
            logger.debug("Tried to sleep on client thread!");
            return false;
        }

        if (Game.getClientState() == GameState.LOGIN_SCREEN || Game.getClientState() == GameState.LOGIN_SCREEN_AUTHENTICATOR)
        {
            return false;
        }

        for (int i = 0; i < ticks; i++)
        {
            if (supplier.getAsBoolean())
            {
                return true;
            }

            if (!sleepTick())
            {
                return false;
            }
        }

        return false;
    }

    public static String format(Duration duration)
    {
        long secs = Math.abs(duration.getSeconds());
        return String.format("%02d:%02d:%02d", secs / 3600L, secs % 3600L / 60L, secs % 60L);
    }
}
