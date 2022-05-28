package fr.napotwiixe.mod.commands;

import fr.napotwiixe.mod.Main;
import fr.napotwiixe.mod.manager.PlayerManager;
import fr.napotwiixe.mod.utils.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class Commands implements CommandExecutor {
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
                PlayerManager pm = PlayerManager.getFromPlayer(player);

                Main.getInstace().moderateurs.remove(player.getUniqueId());
                player.getInventory().clear();
                player.sendMessage("§cVous n'êtes plus en /mod");
                pm.giveInventory();
                pm.destroy();
                return false;
            }
            PlayerManager pm = new PlayerManager(player);
            pm.init();

            Main.getInstace().moderateurs.add(player.getUniqueId());
            player.sendMessage("§aVous êtes en /mod");
            pm.saveInventory();
        }

        if(label.equalsIgnoreCase("report")){
            if(args.length != 1){
                player.sendMessage("§cVeuillez saisir le pseudo d'un joueur !");
                return false;
            }

            String targetName = args[0];

            if(Bukkit.getPlayer(targetName) == null){
                player.sendMessage("§cCe joueur n'est pas connecté ou n'existe pas !");
                return false;
            }

            Player target = Bukkit.getPlayer(targetName);

            Inventory inv = Bukkit.createInventory(null, 18, "§bReport: §c" + target.getName());

            inv.setItem(0, new ItemBuilder(Material.IRON_SWORD).setName("§cForceField").toItemStack());
            inv.setItem(1, new ItemBuilder(Material.BOW).setName("§cSpamBow").toItemStack());

            player.openInventory(inv);
        }

        return false;
    }
}
