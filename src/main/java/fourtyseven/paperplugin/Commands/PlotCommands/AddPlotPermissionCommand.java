package fourtyseven.paperplugin.Commands.PlotCommands;

import fourtyseven.paperplugin.PaperPlugin;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AddPlotPermissionCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args){

        if(args.length == 0){
            sender.sendMessage("Wrong Usage! Usage: /addpermission player plotid");
            return false;
        }

        Player player = Bukkit.getPlayer(args[0]);
        int plotID = Integer.valueOf(args[1]);

        if(!PaperPlugin.getInstance().plotManagementSQL.exists(plotID)){
            player.sendMessage("PlotID does not exist. Send a command with a correct!");
            return false;
        }

        PaperPlugin.getInstance().buildingPermissions.addPermission(plotID, player);


        return true;
    }
}
