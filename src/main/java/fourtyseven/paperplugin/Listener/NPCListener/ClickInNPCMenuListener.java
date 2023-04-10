package fourtyseven.paperplugin.Listener.NPCListener;

import fourtyseven.paperplugin.PaperPlugin;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class ClickInNPCMenuListener implements Listener {

    @EventHandler
    public void onClickInInventory(InventoryClickEvent event){

        if(event.getClickedInventory().getName() == "Informationen"){
            event.setCancelled(true);

            if(event.getSlot() == 3){
                //Clicked Bank Card
                Player player = (Player) event.getWhoClicked();

                PaperPlugin.getInstance().accountManagement.createBankAccount(player);

                Inventory inventory = player.getInventory();

                ItemStack card = new ItemStack(Material.PAPER, 1);
                ItemMeta meta = card.getItemMeta();

                List<String> kontoId = new ArrayList<>();
                kontoId.add("§0" + player.getUniqueId().toString());
                meta.setLore(kontoId);

                meta.setDisplayName("Bankkarte von " + player.getName());

                card.setItemMeta(meta);

                inventory.addItem(card);
            }

        }
        return;
    }
}
