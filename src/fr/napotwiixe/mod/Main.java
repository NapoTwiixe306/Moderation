package fr.napotwiixe.mod;

import fr.napotwiixe.mod.commands.Commandes;
import fr.napotwiixe.mod.manager.EventsManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.UUID;

public class Main extends JavaPlugin {

    private static Main instace;

    public ArrayList<UUID> moderateurs = new ArrayList<UUID>();

    @Override
    public void onEnable() {
    instace = this;
    new EventsManager().registers();
    getCommand("mod").setExecutor(new Commandes());
    }

    public static Main getInstace() {
        return instace;
    }
}
