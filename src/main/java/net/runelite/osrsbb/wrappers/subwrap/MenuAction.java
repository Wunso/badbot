package net.runelite.osrsbb.wrappers.subwrap;

public class MenuAction {
    private int opcode;
    private int id;
    private int secondary;
    private int tertiary;
    private int quaternary;
    private int mouseX;
    private int mouseY;
    private String action;
    private String target;

    public MenuAction() {
        this.opcode = -1;
        this.id = 0;
        this.secondary = 0;
        this.tertiary = 0;
        this.quaternary = 0;
        this.action = "";
        this.target = "";
        this.mouseX = 0;
        this.mouseY = 0;
    }
}
