package fourtyseven.paperplugin.Listener;

import fourtyseven.paperplugin.Commands.NPCCommands.CreateNPCCommand;
import fourtyseven.paperplugin.NPC.CustomEvent.PacketReader;
import fourtyseven.paperplugin.PaperPlugin;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        Player player = event.getPlayer();
        //Plot System
        PaperPlugin.getInstance().buildingPermissions.createBuildingPermissionRegistration(player);

        //Geld System
        PaperPlugin.getInstance().cashManagement.createCashAccount(player);
        PaperPlugin.getInstance().accountManagement.createBankAccount(player);

        PacketReader reader = new PacketReader();
        reader.inject(player);

        if(CreateNPCCommand.getNPCs().isEmpty()){
            return;
        }else{
            CreateNPCCommand.addJoinPacket(player);
        }
    }
}
