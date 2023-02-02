package net.runelite.osrsbb.api;

import com.sun.jdi.Locatable;

public interface SceneEntity extends Locatable, Identifiable, Interactable, EntityNameable {
    long getTag();
}
