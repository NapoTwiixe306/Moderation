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
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if(!(sender instanceof Player)){
            sender.sendMessage("Seul un joueur peut executer cette commande !");
            return false;
        }

        Player player = (Player) sender;

        if(label.equalsIgnoreCase("mod")){
            if(!player.hasPermission("moderation.mod")){
                player.sendMessage("§cVous n'avez pas la permission d'éxecuter cette commande !");
                return false;
            }

            if(PlayerManager.isInModerationMod(player)){
                PlayerManager pm = PlayerManager.getFromPlayer(player);

                Main.getInstance().moderateurs.remove(player.getUniqueId());
                player.getInventory().clear();
                player.sendMessage("§cVous n'êtes maintenant plus en mode modération");
                pm.giveInventory();
                pm.destroy();
                player.setAllowFlight(false);
                player.setFlying(false);
                return false;
            }

            PlayerManager pm = new PlayerManager(player);
            pm.init();

            Main.getInstance().moderateurs.add(player.getUniqueId());
            player.sendMessage("§aVous êtes à présent en mode modération");
            pm.saveInventory();
            player.setAllowFlight(true);
            player.setFlying(true);

            ItemBuilder invSee = new ItemBuilder(Material.PAPER).setName("§eVoir l'inventaire").setLore("§7Clique droit sur un joueur", "§7pour voir son inventaire.");
            ItemBuilder reports = new ItemBuilder(Material.BOOK).setName("§6Voir les signalements").setLore("§7Clique droit sur un joueur", "§7pour voir ses signalements.");
            ItemBuilder freeze = new ItemBuilder(Material.PACKED_ICE).setName("§bFreeze").setLore("§7Clique droit sur un joueur", "§7pour le freeze.");
            ItemBuilder kbTester = new ItemBuilder(Material.STICK).setName("§dTest de recul").setLore("§7Clique gauche sur un joueur", "§7pour tester son recul.");
            ItemBuilder killer = new ItemBuilder(Material.BLAZE_ROD).setName("§cTueur de joueur").setLore("§7Clique droit sur un joueur", "§7pour le tuer.");
            ItemBuilder tpRandom = new ItemBuilder(Material.ARROW).setName("§aTéléportation aléatoire").setLore("§7Clique droit pour se téléporter", "§7aléatoirement sur un joueur.");

            player.getInventory().setItem(0, invSee.toItemStack());
            player.getInventory().setItem(1, reports.toItemStack());
            player.getInventory().setItem(2, freeze.toItemStack());
            player.getInventory().setItem(3, kbTester.toItemStack());
            player.getInventory().setItem(4, killer.toItemStack());
            player.getInventory().setItem(5, tpRandom.toItemStack());
        }

        if(label.equalsIgnoreCase("report")){
            if(args.length != 1){
                player.sendMessage("§cVeuillez saisir le pseudo d'un joueur svp !");
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