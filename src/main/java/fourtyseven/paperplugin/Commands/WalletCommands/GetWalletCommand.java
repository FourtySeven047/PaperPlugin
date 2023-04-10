package fourtyseven.paperplugin.Commands.WalletCommands;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class GetWalletCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args){


        if(!(sender instanceof Player)){
            return true;
        }


        Player player = (Player) sender;
        /*

        Wallet wallet = PaperPlugin.getInstance().getWalletManager().getWallet(player.getUniqueId());

        player.openInventory(wallet.getInventory());

         */
        Short durability = 2;

        ItemStack wallet = new ItemStack(Material.IRON_SWORD);
        ItemMeta walletMeta = wallet.getItemMeta();

        walletMeta.setDisplayName("Geldbörse");
        walletMeta.setUnbreakable(true);
        wallet.setItemMeta(walletMeta);
        wallet.setDurability(durability);

        player.getInventory().addItem(wallet);

        return true;
    }
}
