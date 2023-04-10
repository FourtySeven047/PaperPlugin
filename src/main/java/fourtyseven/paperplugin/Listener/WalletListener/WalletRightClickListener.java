package fourtyseven.paperplugin.Listener.WalletListener;

import fourtyseven.paperplugin.PaperPlugin;
import fourtyseven.paperplugin.Wallet.Wallet;
import net.minecraft.server.v1_12_R1.ChatMessage;
import net.minecraft.server.v1_12_R1.EntityPlayer;
import net.minecraft.server.v1_12_R1.PacketPlayOutOpenWindow;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class WalletRightClickListener implements Listener {

    @EventHandler
    public void onRightClick(PlayerInteractEvent event){

        Action action = event.getAction();
        Player player = event.getPlayer();
        Short durability = 50;
        //
        if(action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK) {
            if(player.getInventory().getItemInMainHand().getType() == Material.IRON_SWORD) {
                System.out.println("Right Click on Sword");
                if(player.getInventory().getItemInMainHand().getDurability() == 2){
                    System.out.println("Right CLick on the Geldboerse lamp");
                    Wallet wallet = PaperPlugin.getInstance().getWalletManager().getWallet(player.getUniqueId());
                    Inventory inventory = wallet.getInventory();
                    //player.openInventory(wallet.getInventory());
                    ItemStack itemStack = new ItemStack(Material.IRON_SWORD, 1);
                    ItemMeta meta = itemStack.getItemMeta();
                    meta.setUnbreakable(true);
                    meta.setDisplayName(" ");
                    itemStack.setItemMeta(meta);
                    itemStack.setDurability(durability);
                    inventory.setItem(1, itemStack);
                    inventory.setItem(0, itemStack);
                    inventory.setItem(7, itemStack);
                    inventory.setItem(8, itemStack);
                    player.openInventory(inventory);
                    updateInventoryName(player, "Wallet | " + String.valueOf(PaperPlugin.getInstance().cashManagement.getCashBetrag(player.getUniqueId().toString())) + " EURO");

                }
            }
        }
    }

    public void updateInventoryName(Player p, String title){
        EntityPlayer ep = ((CraftPlayer)p).getHandle();
        PacketPlayOutOpenWindow packet = new PacketPlayOutOpenWindow(ep.activeContainer.windowId, "minecraft:chest", new ChatMessage(title), p.getOpenInventory().getTopInventory().getSize());
        ep.playerConnection.sendPacket(packet);
        ep.updateInventory(ep.activeContainer);
    }
}
