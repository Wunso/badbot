package net.runelite.osrsbb.launcher;

import com.google.inject.Singleton;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
import net.runelite.api.MainBufferProvider;
import net.runelite.client.game.ItemManager;
import net.runelite.osrsbb.event.EventManager;
import net.runelite.osrsbb.event.events.PaintEvent;
import net.runelite.osrsbb.event.events.TextPaintEvent;
import net.runelite.osrsbb.internal.BreakHandler;
import net.runelite.osrsbb.internal.InputManager;
import net.runelite.osrsbb.internal.PassiveScriptHandler;
import net.runelite.osrsbb.internal.ScriptHandler;
import net.runelite.osrsbb.internal.input.Canvas;
import net.runelite.osrsbb.methods.MethodContext;
import net.runelite.client.modified.RuneLite;
import net.runelite.osrsbb.plugin.AccountManager;

import java.applet.Applet;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.EventListener;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.Executors;

@Singleton
@Slf4j
@SuppressWarnings("removal")
public class BadLite extends RuneLite implements BadLiteInterface {
    private String account;
    private InputManager im;
    private ScriptHandler sh;
    private PassiveScriptHandler psh;
    private BreakHandler bh;
    private MethodContext methods;
    private Canvas canvas;
    private Component panel;
    private Image image;
    private PaintEvent paintEvent;
    private TextPaintEvent textPaintEvent;
    private EventManager eventManager;
    private BufferedImage backBuffer;
    private Map<String, EventListener> listeners;

    public String getAccountName() {
        return account;
    }

    public InputManager getInputManager() {
        return im;
    }
    public MethodContext getMethodContext() {
        return methods;
    }
    public Client getClient() {
        return client = injector.getInstance(Client.class);
    }
    public Applet getLoader() {
        return (Applet) this.getClient();
    }
    public ItemManager getItemManager() { return injector.getInstance(ItemManager.class);}
    public EventManager getEventManager() {
        return eventManager;
    }
    public ScriptHandler getScriptHandler() {
        return sh;
    }
    public BreakHandler getBreakHandler() {
        return bh;
    }

    public BufferedImage getBackBuffer() {
        return backBuffer;
    }

    /**
     * Whether or not all anti-randoms are enabled.
     */
    public volatile boolean disableRandoms = false;

    /**
     * Whether or not the login screen anti-random is enabled.
     */
    public volatile boolean disableAutoLogin = false;

    /**
     * Assigns this instance of the RuneLite (Bot) a method context for calling bot api methods
     * as well as assigns bank constants here.
     */
    public void setMethodContext() {
        methods = new MethodContext(this);
        methods.bank.assignConstants();
    }

    /**
     * Gets the canvas object while checking to make sure we don't do this before it has actually
     * loaded
     * @return  The Canvas if the client is loaded otherwise null
     */
    public Canvas getCanvas() {
        if (client == null) {
            return null;
        }
        if (client.getCanvas() == null) {
            return null;
        }
        if (canvas == null) {
            canvas = new Canvas(client.getCanvas());
            return canvas;
        }
        return canvas;
    }

    /**
     * Sets an account for the RuneLite (Bot) instance
     * @param name  The name of the account
     * @return  If the account existed already
     */
    public boolean setAccount(final String name) {
        if (name != null) {
            for (String s : AccountManager.getAccountNames()) {
                if (s.toLowerCase().equals(name.toLowerCase())) {
                    account = name;
                    return true;
                }
            }
        }
        account = null;
        return false;
    }

    /**
     * Grabs the graphics visible on the canvas from the main buffer using the associated provider
     * @param mainBufferProvider    An object that provides the main buffer (canvas info) for this client instance
     * @return  The graphics of the Canvas
     */
    public Graphics getBufferGraphics(MainBufferProvider mainBufferProvider) {
        image = mainBufferProvider.getImage();
        Graphics back = mainBufferProvider.getImage().getGraphics();
        paintEvent.graphics = back;
        textPaintEvent.graphics = back;
        textPaintEvent.idx = 0;
        eventManager.processEvent(paintEvent);
        eventManager.processEvent(textPaintEvent);
        back.dispose();
        back.drawImage(backBuffer, 0, 0, null);
        return back;
    }

    public Component getPanel() {
        return this.panel;
    }
    public void setPanel(Component c) {
        this.panel = c;
    }

    /**
     * Returns the size of the panel that clients should be drawn into. For
     * internal use.
     *
     * @return The client panel size.
     */
    public Dimension getPanelSize() {
        for (BadLiteInterface bot : Application.getBots()) {
            if (bot != null) {
                if (((BadLite) bot).getClient().getClass().getClassLoader() == this.getClient().getClass().getClassLoader()) {
                    return this.getPanel().getSize();
                }
            }
        }
        return null;
    }

    public BadLite() throws Exception {
        im = new InputManager(this);
        psh = new PassiveScriptHandler(this);
        eventManager = new EventManager();
        sh = new ScriptHandler(this);
        bh = new BreakHandler(this);
        paintEvent = new PaintEvent();
        textPaintEvent = new TextPaintEvent();
        listeners = new TreeMap<>();
        eventManager.start();
        Executors.newSingleThreadScheduledExecutor().submit(() -> {
            while(this.getClient() == null){}
            setMethodContext();
            sh.init();
            if (getPanelSize() != null) {
                final Dimension size = getPanelSize();
                backBuffer = new BufferedImage(size.width, size.height, BufferedImage.TYPE_INT_RGB);
                image = new BufferedImage(size.width, size.height, BufferedImage.TYPE_INT_RGB);
            }
        });
    }

    @Override
    public void runScript(String account, String scriptName) {

    }

    @Override
    public void stopScript() { sh.stopScript(); }

    /**
     * The actual method associated with initializing the client-related data. Such as creating the client sizing and
     * binding the plethora of handlers, listeners, and managers to this particular RuneLite instance
     * (outside the injector binding)
     *
     * @param  startClientBare  Whether to launch the client without any additional initialization settings or not
     * @throws Exception        Any exception the client, bot, or RuneLite might throw.
     */
    @Override
    public void init(boolean startClientBare) throws Exception {
        if (startClientBare) {
            this.bareStart();
        }
        else {
            this.start();
        }
    }
}
