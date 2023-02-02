package net.runelite.osrsbb.api;

import lombok.extern.slf4j.Slf4j;
import net.runelite.api.GameState;
import net.runelite.osrsbb.util.Rand;

import java.time.Duration;
import java.util.function.BooleanSupplier;
@Slf4j
public abstract class MethodProvider {
    public static MethodContext methods = null;
    private static final int DEFAULT_POLLING_RATE = 10;
    public MethodProvider(MethodContext ctx) {
        this.methods = ctx;
    }

    /**
     * Returns a linearly distributed pseudorandom integer.
     *
     * @param min The inclusive lower bound.
     * @param max The exclusive upper bound.
     * @return Random integer min (less than or equal) to n (less than) max.
     */
    public int random(int min, int max) {
        return min + (max == min ? 0 : methods.random.nextInt(max - min));
    }

    /**
     * Returns a normally distributed pseudorandom integer about a mean centered
     * between min and max with a provided standard deviation.
     *
     * @param min The inclusive lower bound.
     * @param max The exclusive upper bound.
     * @param sd  The standard deviation. A higher value will increase the
     *            probability of numbers further from the mean being returned.
     * @return Random integer min (less than or equal to) n (less than) max from the normal distribution
     *         described by the parameters.
     */
    public int random(int min, int max, int sd) {
        int mean = min + (max - min) / 2;
        int rand;
        do {
            rand = (int) (methods.random.nextGaussian() * sd + mean);
        } while (rand < min || rand >= max);
        return rand;
    }

    /**
     * Returns a normally distributed pseudorandom integer with a provided
     * standard deviation about a provided mean.
     *
     * @param min  The inclusive lower bound.
     * @param max  The exclusive upper bound.
     * @param mean The mean (greater than or equal to min and less than max).
     * @param sd   The standard deviation. A higher value will increase the
     *             probability of numbers further from the mean being returned.
     * @return Random integer min (less than or equal) to n (less than) max from the normal distribution
     *         described by the parameters.
     */
    public int random(int min, int max, int mean, int sd) {
        int rand;
        do {
            rand = (int) (methods.random.nextGaussian() * sd + mean);
        } while (rand < min || rand >= max);
        return rand;
    }

    /**
     * Returns a linearly distributed pseudorandom <code>double</code>.
     *
     * @param min The inclusive lower bound.
     * @param max The exclusive upper bound.
     * @return Random min (less than or equal) to n (less than) max.
     */
    public double random(double min, double max) {
        return min + methods.random.nextDouble() * (max - min);
    }

    /**
     * Gets the digit at the index of the number
     * @param number the number to get the digit from
     * @param index the position to check
     *
     * @return the digit in the number
     */
    int nth ( int number, int index ) {
        return (int)(number / Math.pow(10, index)) % 10;
    }

    public static boolean sleep(long ms)
    {
        if (methods.client.isClientThread())
        {
            return false;
        }

        try
        {
            Thread.sleep(ms);
            return true;
        }
        catch (InterruptedException e)
        {
            log.debug("Sleep interrupted");
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
            log.debug("Tried to sleepUntil on client thread!");
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
            log.debug("Tried to sleep on client thread!");
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
                    log.debug("Sleep interrupted");
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
            log.debug("Tried to sleep on client thread!");
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
