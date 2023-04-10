package fourtyseven.paperplugin.Listener.NPCListener;

import fourtyseven.paperplugin.NPC.CustomEvent.RightClickNPC;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;


public class RightClickNPCListener implements Listener {

    @EventHandler
    public void onClick(RightClickNPC event){
        Player player = event.getPlayer();
        Inventory inventory = Bukkit.createInventory(null, 27, "Informationen");

        //Kontostand
        ItemStack paper = new ItemStack(Material.PAPER);
        inventory.setItem(3, paper);
        //Get Wallet
        ItemStack iron_sword = new ItemStack(Material.IRON_SWORD);
        inventory.setItem(4, iron_sword);
        //Grundstücks Info
        ItemStack door = new ItemStack(Material.WOOD_DOOR);
        inventory.setItem(5, door);

        player.openInventory(inventory);


    }
}
