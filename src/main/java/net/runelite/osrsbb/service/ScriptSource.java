package net.runelite.osrsbb.service;

import net.runelite.osrsbb.script.Script;

import java.util.List;

public interface ScriptSource {
    List<ScriptDefinition> list();

    Script load(ScriptDefinition def) throws ServiceException;
}
