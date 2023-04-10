package fourtyseven.paperplugin.Listener;

import fourtyseven.paperplugin.PaperPlugin;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class BlockBreakEvent implements Listener {

    @EventHandler
    public void onBlockBreakEvent(org.bukkit.event.block.BlockBreakEvent event){
        // TODO: WICHTIGE INFORMATION: DIESER TEIL IST DEM PLOT SYSTEM ZUGEWIESEN
        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();
        String arrayString = PaperPlugin.getInstance().buildingPermissions.getArrayFromDatabase(uuid.toString());

        List<String> list = Arrays.asList(arrayString.substring(1, arrayString.length()-1).split(", "));

        Object[] arrayToEdit = list.toArray();

        if(player.isOp()){
            return;
        }

        if(arrayString == "[0]"){
            event.setCancelled(true);
            return;
        }

        for (int i = 1; i < arrayToEdit.length; i++) {
            Block block = event.getBlock();

            //TODO: Alles ist viel einfacher, wenn ich in dem Command einfach minX minZ und maxX maxZ abfrage und abspeichere.

            String plotIdString = arrayToEdit[i].toString();
            int plotId = Integer.parseInt(plotIdString);

            /*
            int size = PaperPlugin.getInstance().plotManagementSQL.getSize(plotId);
            int centerX = PaperPlugin.getInstance().plotManagementSQL.getCenterX(plotId);
            int centerZ = PaperPlugin.getInstance().plotManagementSQL.getCenterZ(plotId);
            */

            int minX = PaperPlugin.getInstance().plotManagementSQL.getMinX(plotId);
            int maxX = PaperPlugin.getInstance().plotManagementSQL.getMaxX(plotId);
            int minZ = PaperPlugin.getInstance().plotManagementSQL.getMinZ(plotId);
            int maxZ = PaperPlugin.getInstance().plotManagementSQL.getMaxZ(plotId);
            System.out.println(minX + "," + maxX + "," + minZ + "," + maxZ);

            int blockX = block.getX();
            int blockZ = block.getZ ();

            System.out.println(plotId);

            Location loc = new Location(player.getWorld(), event.getBlock().getX(), event.getBlock().getY(), event.getBlock().getZ());

            Location minLoc = new Location(player.getWorld(), minX, 0, minZ);
            Location maxLoc = new Location(player.getWorld(), maxX, 255, maxZ);

            /*
            if(loc.getBlockX() <= minLoc.getBlockX() && loc.getBlockX() >= maxLoc.getBlockX()){
                System.out.println("Es ist in X");
            }else{
                System.out.println("es ist nicht in X");
            }

            if(loc.getBlockY() >= minLoc.getBlockY() && loc.getBlockY() <= maxLoc.getBlockY()){
                System.out.println("Es ist in Y");
            }else{
                System.out.println("es ist nicht in Y");
            }

            if(loc.getBlockZ() <= minLoc.getBlockZ() && loc.getBlockZ() >= maxLoc.getBlockZ()){
                System.out.println("es ist in Z");
            }else{
                System.out.println("es ist nicht in Z");
            }

            */
            if(loc.getBlockX() >= minLoc.getBlockX() && loc.getBlockX() <= maxLoc.getBlockX()){
                System.out.println("Ist im Plot bei X");
            }else{
                System.out.println("Ist nicht im Plot bei X du ungeiler du du Knecht");
            }



            if(loc.getBlockX() >= minLoc.getBlockX() && loc.getBlockX() <= maxLoc.getBlockX()
                    && loc.getBlockY() >= minLoc.getBlockY() && loc.getBlockY() <= maxLoc.getBlockY()
                    && loc.getBlockZ() >= minLoc.getBlockZ() && loc.getBlockZ() <= maxLoc.getBlockZ()){

                System.out.println("Es ist im Plot du geiler du");
                event.setCancelled(false);
                return;
            }else{
                System.out.println("es ist nicht im plut du ungeiler du");
                event.setCancelled(true);
            }

            //TODO: PLOT SYSTEM ENDE

        }
    }
}
