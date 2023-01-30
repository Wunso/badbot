package net.runelite.osrsbb.wrappers.clientwrapper;

import net.runelite.api.Client;
import net.runelite.api.Rasterizer;
import net.runelite.api.TileFunction;
import net.runelite.api.worldmap.MapElementConfig;
import net.runelite.api.worldmap.WorldMap;
import net.runelite.client.callback.ClientThread;

import java.time.Instant;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.FutureTask;

/*
Wrapper around runelite Client, adds synchronization with ClientThread for methods that require it.
In order to synchronize Widget methods, all returned Widget instances are also wrapped.
Running methods on client thread comes with a performance hit, so only methods that are confirmed to cause problems are synchronized.
*/
public class RSClient extends BaseClientWrapper {
    private final Queue<FutureTask<Object>> taskQueue = new ConcurrentLinkedQueue<>();

    public RSClient(Client client, ClientThread clientThread) {
        super(client);
        clientThread.invoke(() -> {
            final var expirationTime = Instant.now().plusMillis(20);
            if (taskQueue.isEmpty()) return false;
            while (Instant.now().isBefore(expirationTime)) {
                final var task = taskQueue.poll();
                if (task != null) {
                    task.run();
                }
            }
            return false;
        });
    }

    @Override
    public MapElementConfig getMapElementConfig(int id) {
        return null;
    }
    @Override
    public int getCameraYawTarget() {
        return 0;
    }

    @Override
    public int getCameraPitchTarget() {
        return 0;
    }

    @Override
    public void setCameraPitchTarget(int cameraPitchTarget) {

    }
    @Override
    public WorldMap getWorldMap() {
        return null;
    }

    @Override
    public boolean isWidgetSelected() {
        return false;
    }

    @Override
    public void setWidgetSelected(boolean selected) {

    }

    @Override
    public void setMinimapTileDrawer(TileFunction drawTile) {

    }

    @Override
    public Rasterizer getRasterizer() {
        return null;
    }
}
