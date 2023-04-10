package fourtyseven.paperplugin.Listener.ATMListener;

import fourtyseven.paperplugin.Money.ATMManagement;
import fourtyseven.paperplugin.PaperPlugin;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class OpenATMListener implements Listener {

    ATMManagement atmManagement = new ATMManagement();


    @EventHandler
    public void openMenu(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Action action = event.getAction();

        if (action == Action.RIGHT_CLICK_BLOCK) {
            //System.out.println("Rechtsklick");
            if (event.getClickedBlock().getType() == Material.TRIPWIRE_HOOK) {
                //System.out.println("Nen Knecht hat nh Tripwire angeklickt");
                if (event.getPlayer().getInventory().getItemInMainHand().getType() == Material.PAPER) {
                    //System.out.println("Und hat nen Papier in der Hand");
                    //Start Bankautomat
                    ItemStack card = new ItemStack(event.getPlayer().getInventory().getItemInMainHand());

                    ItemMeta meta = card.getItemMeta();

                    if (meta.getLore() == null) {
                        return;
                    }

                    List<String> metaLore = new ArrayList<>();
                    metaLore = meta.getLore();
                    String id = metaLore.get(0);

                    if (PaperPlugin.getInstance().accountManagement.exists(id.substring(2))) {
                        System.out.println("Player hat Konto!");

                        atmManagement.openATMMenue(player, id.substring(2));

                        /*
                        this.inventory = Bukkit.createInventory(null, 27, "Bankautomat");

                        ItemStack addMoney = new ItemStack(Material.SLIME_BLOCK);
                        ItemStack withdrawMoney = new ItemStack(Material.REDSTONE_BLOCK);
                        ItemStack moneyStatus = new ItemStack(Material.PAPER);

                        ItemMeta meta_slime = addMoney.getItemMeta();
                        ItemMeta meta_redstone = withdrawMoney.getItemMeta();
                        ItemMeta meta_paper = moneyStatus.getItemMeta();

                         */


                    }
                    //System.out.println(id.substring(2));
                }
            }
        }
    }
}
