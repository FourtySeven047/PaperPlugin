package fourtyseven.paperplugin.Listener;

import fourtyseven.paperplugin.NPC.CustomEvent.PacketReader;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class QuitListener implements Listener {

    @EventHandler
    public void onQuit(PlayerQuitEvent event){
        PacketReader reader = new PacketReader();
        reader.uninject(event.getPlayer());
    }
}
