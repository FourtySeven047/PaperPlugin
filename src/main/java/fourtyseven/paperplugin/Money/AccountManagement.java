package fourtyseven.paperplugin.Money;

import fourtyseven.paperplugin.PaperPlugin;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class AccountManagement implements CommandExecutor {

    public PaperPlugin plugin;
    public AccountManagement(PaperPlugin plugin) {this.plugin = plugin;}

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args){
        if(!(sender instanceof Player)){return true;}

        Player player = (Player) sender;

        if(command.getName().equalsIgnoreCase("createBankAccount")){
            //TODO: Erstelle Bank Account
            createBankAccount(player);

            Inventory inventory =   player.getInventory();

            ItemStack card = new ItemStack(Material.PAPER, 1);
            ItemMeta meta = card.getItemMeta();

            List<String> kontoId = new ArrayList<>();
            kontoId.add("§0" + player.getUniqueId().toString());
            meta.setLore(kontoId);

            meta.setDisplayName("Bankkarte von " + player.getName());

            card.setItemMeta(meta);

            inventory.addItem(card);
        }

        if(command.getName().equalsIgnoreCase("addMoney")){
            //TODO: Fuege Geld hinzu
            if(args == null) {
                System.out.println("Ist 0 Du Opfer");
                return false;
            }
            changeBalance(player.getUniqueId().toString(), Integer.valueOf(args[0]).floatValue());
        }

        return true;
    }

    public void createTable(){
        PreparedStatement ps;
        try {
            ps = plugin.SQL.getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS bankAccounts " +
                    "(OWNER VARCHAR(100),ID VARCHAR(100),PIN INT(100),BETRAG FLOAT(15,4),PRIMARY KEY (OWNER))");
            ps.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void createBankAccount(Player player){
        try {
            UUID uuid = player.getUniqueId();

            if(!exists(uuid.toString())){
                int pin = 1234;
                PreparedStatement ps2 = plugin.SQL.getConnection().prepareStatement("INSERT IGNORE INTO bankAccounts" +
                        " (OWNER,ID,PIN,BETRAG) VALUES (?,?,?,?)");
                ps2.setString(1, player.getName());
                ps2.setString(2, uuid.toString());
                ps2.setString(3, "1234");
                ps2.setFloat(4, 100);
                ps2.executeUpdate();

                return;
            }

        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public boolean exists(String id){
        try {
            PreparedStatement ps = plugin.SQL.getConnection().prepareStatement("SELECT * FROM bankAccounts WHERE ID=?");
            ps.setString(1, id);

            ResultSet result = ps.executeQuery();
            if(result.next()){
                return true;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    public void changeBalanceUnfilterdID(String id, float money) {
        try {
            PreparedStatement ps = plugin.SQL.getConnection().prepareStatement("UPDATE bankAccounts SET BETRAG=? WHERE ID=?");
            ps.setFloat(1, getBalance(id.substring(2)) + money);
            ps.setString(2, id.substring(2));
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void changeBalance(String id, float money) {
        try {
            PreparedStatement ps = plugin.SQL.getConnection().prepareStatement("UPDATE bankAccounts SET BETRAG=? WHERE ID=?");
            ps.setFloat(1, getBalance(id) + money);
            ps.setString(2, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public float getBalance(String id){

        try {
            PreparedStatement ps = plugin.SQL.getConnection().prepareStatement("SELECT BETRAG FROM bankAccounts WHERE ID=?");
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            float points = 0;
            if(rs.next()){
                points = rs.getFloat("BETRAG");
                return points;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
