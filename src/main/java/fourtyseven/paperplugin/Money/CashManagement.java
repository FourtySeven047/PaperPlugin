package fourtyseven.paperplugin.Money;

import fourtyseven.paperplugin.PaperPlugin;
import org.bukkit.entity.Player;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class CashManagement {

    private PaperPlugin plugin;
    public CashManagement(PaperPlugin plugin){
        this.plugin = plugin;
    }

    public void createCashTable(){
        PreparedStatement ps;
        try {
            ps = plugin.SQL.getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS playercash " +
                    "(PLAYER VARCHAR(100),UUID VARCHAR(100),CASH FLOAT(15,4),PRIMARY KEY (PLAYER))");
            ps.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void createCashAccount(Player player){
        try {
            UUID uuid = player.getUniqueId();

            if(!existsCash(uuid)){
                PreparedStatement ps2 = plugin.SQL.getConnection().prepareStatement("INSERT IGNORE INTO playercash" +
                        " (PLAYER,UUID,CASH) VALUES (?,?,?)");
                ps2.setString(1, player.getName());
                ps2.setString(2, uuid.toString());
                ps2.setFloat(3, 450);
                ps2.executeUpdate();

                return;
            }

        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public boolean existsCash(UUID uuid){
        try {
            PreparedStatement ps = plugin.SQL.getConnection().prepareStatement("SELECT * FROM playercash WHERE UUID=?");
            ps.setString(1, uuid.toString());

            ResultSet result = ps.executeQuery();
            if(result.next()){
                return true;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    public void addCash(String uuid, float money) {
        try {
            PreparedStatement ps = plugin.SQL.getConnection().prepareStatement("UPDATE playercash SET CASH=? WHERE UUID=?");
            ps.setFloat(1, getCashBetrag(uuid) + money);
            ps.setString(2, uuid.toString());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public float getCashBetrag(String uuid){

        try {
            PreparedStatement ps = plugin.SQL.getConnection().prepareStatement("SELECT CASH FROM playercash WHERE UUID=?");
            ps.setString(1, uuid);
            ResultSet rs = ps.executeQuery();
            float points = 0;
            if(rs.next()){
                points = rs.getFloat("CASH");
                return points;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
