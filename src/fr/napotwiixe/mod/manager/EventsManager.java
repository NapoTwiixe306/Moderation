package fr.napotwiixe.mod.manager;

import fr.napotwiixe.mod.Main;
import fr.napotwiixe.mod.listeners.InventoryClick;
import fr.napotwiixe.mod.listeners.ModCancels;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;

public class EventsManager {

    public void registers(){
        PluginManager pm = Bukkit.getPluginManager();
        pm.registerEvents(new ModCancels(), Main.getInstance());
    }
}
