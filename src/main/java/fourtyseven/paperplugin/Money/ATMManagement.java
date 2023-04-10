package fourtyseven.paperplugin.Money;

import fourtyseven.paperplugin.PaperPlugin;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ATMManagement {

    public void openATMMenue(Player player, String id){
        List<String> informationLore = new ArrayList<String>(2) ;
        informationLore.add(0, ChatColor.WHITE + "Kontostand: " + PaperPlugin.getInstance().accountManagement.getBalance(id) + " EURO");
        informationLore.add(1, ChatColor.WHITE + "Kontoinhaber: " + Bukkit.getPlayer(UUID.fromString(id)).getDisplayName());

        Inventory inventory = Bukkit.createInventory(null, 27, "Bankautomat");

        ItemStack addMoney = new ItemStack(Material.SLIME_BLOCK);
        ItemStack withdrawMoney = new ItemStack(Material.REDSTONE_BLOCK);
        ItemStack information = new ItemStack(Material.PAPER);

        ItemMeta meta_slime = addMoney.getItemMeta();
        ItemMeta meta_redstone = withdrawMoney.getItemMeta();
        ItemMeta meta_information = information.getItemMeta();

        meta_slime.setDisplayName("Einzahlen");
        meta_redstone.setDisplayName("Auszahlen");
        //meta_paper.setDisplayName("Kontostand: " + PaperPlugin.getInstance().accountManagement.getBalance(id.substring(2)));
        meta_information.setDisplayName("Informationen");
        meta_information.setLore(informationLore);

        addMoney.setItemMeta(meta_slime);
        withdrawMoney.setItemMeta(meta_redstone);
        information.setItemMeta(meta_information);

        inventory.setItem(11, addMoney);
        inventory.setItem(15, withdrawMoney);
        inventory.setItem(8, information);

        player.openInventory(inventory);
    }
}
