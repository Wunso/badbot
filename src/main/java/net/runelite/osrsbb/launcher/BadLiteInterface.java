package net.runelite.osrsbb.launcher;

public interface BadLiteInterface {
    void runScript(String account, String scriptName);

    void stopScript();

    void launch(String[] args) throws Exception;

    void init(boolean startClientBare) throws Exception;
}
