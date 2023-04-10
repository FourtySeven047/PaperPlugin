package fourtyseven.paperplugin.Listener.ATMListener;

import fourtyseven.paperplugin.Money.ATMManagement;
import fourtyseven.paperplugin.PaperPlugin;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ClickInATMListener implements Listener {

    public boolean getChatData = false;
    public float einzahlZahl = 0;
    public List<UUID> playerArray  = new ArrayList<>();
    public List<String> inaccuratePlayerArray  = new ArrayList<>();
    public List<String> idArray  = new ArrayList<>();
    public List<String> playerToAddMoney  = new ArrayList<>();
    public ATMManagement atmManagement = new ATMManagement();

    @EventHandler
    public void onCLick(InventoryClickEvent event){

        Inventory inventory = event.getClickedInventory();
        InventoryView invenToryView = event.getView();
        HumanEntity player = event.getWhoClicked();
        UUID uuid = player.getUniqueId();

        if(invenToryView.getTitle() == "Bankautomat"){
            event.setCancelled(true);

            if(event.getSlot() == 11){
                ItemStack itemStack = player.getInventory().getItemInMainHand();
                ItemMeta itemMeta = itemStack.getItemMeta();


                List<String> metaLore = new ArrayList<>();
                metaLore = itemMeta.getLore();
                String id = metaLore.get(0);

                playerArray.clear();
                inaccuratePlayerArray.clear();
                idArray.clear();

                getChatData = true;
                player.sendMessage("&aGebe ein, wie viel Geld du einzahlen möchtest: ");
                player.closeInventory();
                playerArray.add(player.getUniqueId());
                inaccuratePlayerArray.add(id);
                inaccuratePlayerArray.add("add");
                idArray.add(id);
            }

            if(event.getSlot() == 15){
                ItemStack itemStack = player.getInventory().getItemInMainHand();
                ItemMeta itemMeta = itemStack.getItemMeta();


                List<String> metaLore = new ArrayList<>();
                metaLore = itemMeta.getLore();
                String id = metaLore.get(0);

                getChatData = true;

                playerArray.clear();
                inaccuratePlayerArray.clear();
                idArray.clear();

                player.sendMessage("&aGebe ein, wie viel Geld du abheben möchtest: ");
                player.closeInventory();
                playerArray.add(player.getUniqueId());
                inaccuratePlayerArray.add(id);
                inaccuratePlayerArray.add("subtract");
                idArray.add(id);

            }
        }
    }
    @EventHandler
    public void onChat(PlayerChatEvent event){
        System.out.println("Player Message: " + event.getMessage());

        if(playerArray.contains(event.getPlayer().getUniqueId())){

            if(inaccuratePlayerArray.get(1) == "add"){

                if(Float.parseFloat(event.getMessage()) > 0){
                    event.setCancelled(true);
                    if(Float.parseFloat(event.getMessage()) > PaperPlugin.getInstance().cashManagement.getCashBetrag(event.getPlayer().getUniqueId().toString()))
                    {
                        event.getPlayer().sendMessage("Du hast dafuer zu wenig Bargeld bei dir!");
                        playerArray.clear();
                        inaccuratePlayerArray.clear();
                        idArray.clear();
                        return;
                    }
                    //Muss nochmal getestet werden.
                    float addValue = Float.parseFloat(event.getMessage());
                    PaperPlugin.getInstance().accountManagement.changeBalance(inaccuratePlayerArray.get(0).substring(2), Float.parseFloat(event.getMessage()));
                    PaperPlugin.getInstance().cashManagement.addCash(event.getPlayer().getUniqueId().toString(), addValue *= -1);
                    atmManagement.openATMMenue(event.getPlayer(), inaccuratePlayerArray.get(0).substring(2));
                    playerArray.clear();
                    inaccuratePlayerArray.clear();
                    idArray.clear();
                    event.getPlayer().sendMessage(ChatColor.GREEN +"Du hast erfolgreich " + ChatColor.YELLOW + Float.parseFloat(event.getMessage()) + ChatColor.GREEN +" Euro eingezahlt!");
                    event.getPlayer().playSound(event.getPlayer().getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 0.5f, 1f);
                    return;
                }
                else{
                    event.getPlayer().sendMessage("Du musst eine gueltige Zahl angeben!");
                    event.setCancelled(true);

                    playerArray.clear();
                    inaccuratePlayerArray.clear();
                    idArray.clear();

                    return;
                }

            }

            if(inaccuratePlayerArray.get(1) == "subtract"){

                if(Float.parseFloat(event.getMessage()) > 0){
                    event.setCancelled(true);
                    float subtractValue = Float.parseFloat(event.getMessage());
                    if(subtractValue > PaperPlugin.getInstance().cashManagement.getCashBetrag(playerArray.get(0).toString())){
                        event.getPlayer().sendMessage("Du hast dafuer nicht ausreichend Geld auf deinem Konto!");
                        playerArray.clear();
                        inaccuratePlayerArray.clear();
                        idArray.clear();
                        return;
                    }
                    PaperPlugin.getInstance().cashManagement.addCash(event.getPlayer().getUniqueId().toString(), subtractValue);
                    PaperPlugin.getInstance().accountManagement.changeBalance(inaccuratePlayerArray.get(0).substring(2), subtractValue *= -1);
                    atmManagement.openATMMenue(event.getPlayer(), inaccuratePlayerArray.get(0).substring(2));
                    playerArray.clear();
                    inaccuratePlayerArray.clear();
                    idArray.clear();
                    event.getPlayer().sendMessage(ChatColor.GREEN +"Du hast erfolgreich " + ChatColor.YELLOW + Float.parseFloat(event.getMessage()) + ChatColor.GREEN +" Euro abgehoben!");
                    event.getPlayer().playSound(event.getPlayer().getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 0.5f, 1f);
                    return;
                }
                else{
                    event.getPlayer().sendMessage("Du musst eine gueltige Zahl angeben!");
                    event.setCancelled(true);
                    return;
                }
            }

            if(inaccuratePlayerArray.get(1) == "topplayer"){
                System.out.println("Hallo");

                if(playerToAddMoney.isEmpty()){
                    System.out.println("Player to Add Money is Empty. Returning...");
                    return;
                }

                if(Float.parseFloat(event.getMessage()) > 0){
                    event.setCancelled(true);
                    if(Float.parseFloat(event.getMessage()) > PaperPlugin.getInstance().cashManagement.getCashBetrag(event.getPlayer().getUniqueId().toString()))
                    {
                        event.getPlayer().sendMessage("Du hast dafuer zu wenig Bargeld bei dir!");
                        playerArray.clear();
                        inaccuratePlayerArray.clear();
                        idArray.clear();
                        playerToAddMoney.clear();
                        return;
                    }
                    //Muss nochmal getestet werden.
                    float addValue = Float.parseFloat(event.getMessage());
                    PaperPlugin.getInstance().cashManagement.addCash(event.getPlayer().getUniqueId().toString(), addValue *= -1);
                    PaperPlugin.getInstance().cashManagement.addCash(playerToAddMoney.get(0), Float.parseFloat(event.getMessage()));
                    //MinecraftPlugin.getInstance().moneyData.addCash(event.getPlayer().getUniqueId().toString(), addValue *= -1);
                    playerArray.clear();
                    inaccuratePlayerArray.clear();
                    idArray.clear();
                    playerToAddMoney.clear();
                    System.out.println("Added Cash to Player");
                    return;
                }
                else{
                    event.getPlayer().sendMessage("Du musst eine gueltige Zahl angeben!");
                    event.setCancelled(true);

                    playerArray.clear();
                    inaccuratePlayerArray.clear();
                    idArray.clear();
                    playerToAddMoney.clear();

                    return;
                }
            }
        }
    }
}
