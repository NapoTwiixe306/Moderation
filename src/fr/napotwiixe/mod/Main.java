package fr.napotwiixe.mod;

import fr.napotwiixe.mod.commands.Commands;
import fr.napotwiixe.mod.manager.EventsManager;
import fr.napotwiixe.mod.manager.PlayerManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class Main extends JavaPlugin {

    private static Main Instance;

    public ArrayList<UUID> moderateurs = new ArrayList<UUID>();
    public HashMap<UUID, PlayerManager> players = new HashMap<>();

    @Override
    public void onEnable() {
    Instance = this;
    new EventsManager().registers();
    getCommand("mod").setExecutor(new Commands());
    getCommand("report").setExecutor(new Commands());
    }

    public static Main getInstance() {
        return Instance;
    }
}
