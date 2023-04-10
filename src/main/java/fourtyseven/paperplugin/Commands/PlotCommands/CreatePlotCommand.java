package fourtyseven.paperplugin.Commands.PlotCommands;

import fourtyseven.paperplugin.PaperPlugin;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CreatePlotCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args){

        if(args.length != 5){
            sender.sendMessage("Wrong Usage! Usage: /createplot plotId min:x min:z max:x max:z");
            return false;
        }
        Player player = Bukkit.getPlayer(sender.getName());

        int plotID = Integer.valueOf(args[0]);
        int minX = Integer.valueOf(args[1]);
        int minZ = Integer.valueOf(args[2]);
        int maxX = Integer.valueOf(args[3]);
        int maxZ = Integer.valueOf(args[4]);

        if(minX > maxX){
            player.sendMessage(ChatColor.RED + "Du hast den Plot falsch deklariert! MinX ist groesser als MaxX. Suche dir eine andere Ecke!");
            return false;
        }else{
            player.sendMessage(ChatColor.GREEN + "X ist in Ordnung!");
        }
        if(minZ > maxZ){
            player.sendMessage(ChatColor.RED + "Du hast den Plot falsch deklariert! MinZ ist groeser als MaxZ. Suche dir eine andere Ecke!");
            return false;
        }else{
            player.sendMessage(ChatColor.GREEN + "Z ist in Ordnung! Plot wird erstellt...");
        }

        PaperPlugin.getInstance().plotManagementSQL.createPlot(plotID, minX, minZ, maxX, maxZ, player);

        return true;
    }
}
