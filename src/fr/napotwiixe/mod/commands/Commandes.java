package fr.napotwiixe.mod.commands;

import fr.napotwiixe.mod.Main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Commandes implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd,String label, String[] args) {

        if(!(sender instanceof Player)){
            sender.sendMessage("seul un joueurs a le droit d'executer cette commandes");
            return false;
        }
        Player player = (Player) sender;

        if(label.equalsIgnoreCase("mod")){
            if(!player.hasPermission("moderation.mod)")){
                player.sendMessage("§cvous n'avez pas la permission d'executer cette commande!");
                return false;
            }
            if(Main.getInstace().moderateurs.contains(player.getUniqueId())){
                Main.getInstace().moderateurs.remove(player.getUniqueId());
                player.getInventory().clear();
                player.sendMessage("§cVous n'êtes plus en /mod");
                /**
                 *TODO inv
                 */

                return false;
            }

            Main.getInstace().moderateurs.add(player.getUniqueId());
            player.sendMessage("§aVous êtes en /mod");

            /**
             * TODO inv
             */
        }

        return false;
    }
}
