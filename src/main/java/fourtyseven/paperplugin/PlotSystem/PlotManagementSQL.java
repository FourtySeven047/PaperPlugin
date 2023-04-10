package fourtyseven.paperplugin.PlotSystem;

import fourtyseven.paperplugin.PaperPlugin;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class PlotManagementSQL {

    private PaperPlugin plugin;
    public PlotManagementSQL(PaperPlugin plugin) {this.plugin = plugin;}

    public void createTable(){
        PreparedStatement ps;
        try {
            ps = plugin.SQL.getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS plots " +
                    "(PLOTID INT(100),MINX INT(100),MINZ INT(100),MAXX INT(100),MAXZ INT(100),OWNER VARCHAR(100),BUILDERS VARCHAR(100),PRIMARY KEY (PLOTID))");
            ps.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void createPlot(int plotID, int minX, int minZ, int maxX, int maxZ, Player owner){
        //Builders at the beginning is only the owner.
        try {
            UUID uuid = owner.getUniqueId();

            if(!exists(plotID)){
                PreparedStatement ps = plugin.SQL.getConnection().prepareStatement("INSERT IGNORE INTO plots" +
                        " (PLOTID,MINX,MINZ,MAXX,MAXZ,OWNER,BUILDERS) VALUES (?,?,?,?,?,?,?)");
                ps.setInt(1, plotID);
                ps.setInt(2, minX);
                ps.setInt(3, minZ);
                ps.setInt(4, maxX);
                ps.setInt(5, maxZ);
                ps.setString(6, uuid.toString());
                ps.setString(7, uuid.toString());

                ps.executeUpdate();

                return;
            }
            owner.sendMessage("[!] "+ ChatColor.YELLOW + "Die eingegebene Plot ID existiert bereits! Waehle eine andere.");
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    public boolean exists(int plotID){
        try {
            PreparedStatement ps = plugin.SQL.getConnection().prepareStatement("SELECT * FROM plots WHERE PLOTID=?");
            ps.setInt(1, plotID);

            ResultSet result = ps.executeQuery();
            if(result.next()){
                return true;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }


    public int getMinX(int plotID){
        try {
            PreparedStatement ps = plugin.SQL.getConnection().prepareStatement("SELECT MINX FROM plots WHERE PLOTID=?");
            ps.setInt(1, plotID);
            ResultSet rs = ps.executeQuery();
            int output = 0;
            if(rs.next()){
                output = rs.getInt("MINX");
                return output;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return 0;
    }

    public int getMinZ(int plotID){
        try {
            PreparedStatement ps = plugin.SQL.getConnection().prepareStatement("SELECT MINZ FROM plots WHERE PLOTID=?");
            ps.setInt(1, plotID);
            ResultSet rs = ps.executeQuery();
            int output = 0;
            if(rs.next()){
                output = rs.getInt("MINZ");
                return output;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return 0;
    }

    public int getMaxX(int plotID){
        try {
            PreparedStatement ps = plugin.SQL.getConnection().prepareStatement("SELECT MAXX FROM plots WHERE PLOTID=?");
            ps.setInt(1, plotID);
            ResultSet rs = ps.executeQuery();
            int output = 0;
            if(rs.next()){
                output = rs.getInt("MAXX");
                return output;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return 0;
    }

    public int getMaxZ(int plotID){
        try {
            PreparedStatement ps = plugin.SQL.getConnection().prepareStatement("SELECT MAXZ FROM plots WHERE PLOTID=?");
            ps.setInt(1, plotID);
            ResultSet rs = ps.executeQuery();
            int output = 0;
            if(rs.next()){
                output = rs.getInt("MAXZ");
                return output;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return 0;
    }
}
