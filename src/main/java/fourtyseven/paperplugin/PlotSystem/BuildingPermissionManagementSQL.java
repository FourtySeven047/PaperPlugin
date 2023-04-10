package fourtyseven.paperplugin.PlotSystem;

import fourtyseven.paperplugin.PaperPlugin;
import org.bukkit.entity.Player;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class BuildingPermissionManagementSQL {

    private PaperPlugin plugin;
    public BuildingPermissionManagementSQL(PaperPlugin plugin) {this.plugin = plugin;}

    public void createTable(){
        PreparedStatement ps;
        try {
            ps = plugin.SQL.getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS buildingPermissions " +
                    "(UUID VARCHAR(100),PLOTIDS VARCHAR(100),PRIMARY KEY (UUID))");
            ps.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void createBuildingPermissionRegistration(Player player){
        try {
            UUID uuid = player.getUniqueId();

            if(!existsRegistration(uuid)){
                Object[] arrayToSend = {0};
                String defaultString = Arrays.toString(arrayToSend);
                System.out.println(defaultString);


                PreparedStatement ps = plugin.SQL.getConnection().prepareStatement("INSERT IGNORE INTO buildingPermissions" +
                        " (UUID,PLOTIDS) VALUES (?,?)");
                ps.setString(1, uuid.toString());
                ps.setString(2, defaultString);

                ps.executeUpdate();

                return;
            }

        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public boolean existsRegistration(UUID uuid){
        try {
            PreparedStatement ps = plugin.SQL.getConnection().prepareStatement("SELECT * FROM buildingPermissions WHERE UUID=?");
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

    public void addPermission(int plotID, Player player){
        //Builders at the beginning is only the owner.
        try {
            UUID uuid = player.getUniqueId();

            String arrayString = getArrayFromDatabase(uuid.toString());

            List<String> list = Arrays.asList(arrayString.substring(1, arrayString.length()-1).split(", "));

            Object[] arrayToEdit = list.toArray();
            Object[] arrayToSend = new Object[arrayToEdit.length +1];

            for (int i = 0; i < arrayToEdit.length; i++) {
                arrayToSend[i] = arrayToEdit[i];
            }
            arrayToSend[arrayToSend.length-1] = Integer.toString(plotID);

            System.out.println(arrayToEdit);
            System.out.println(Arrays.toString(arrayToSend));
            System.out.println(Arrays.toString(arrayToSend));
            //list.add(Integer.toString(plotID));

            String stringToSend = Arrays.toString(arrayToSend);


            PreparedStatement ps = plugin.SQL.getConnection().prepareStatement("UPDATE buildingPermissions SET PLOTIDS=? WHERE UUID=?");
            ps.setString(1, stringToSend);
            ps.setString(2, uuid.toString());
            ps.executeUpdate();

            return;
            //}

        }catch (SQLException e){
            e.printStackTrace();

        }
    }

    public boolean hasPermission(String uuid, int plotId) {

        String arrayString = getArrayFromDatabase(uuid);
        if(arrayString == null) {
            return false;
        }

        return false;
    }

    public String getArrayFromDatabase(String uuid) {

        try {
            PreparedStatement ps = plugin.SQL.getConnection().prepareStatement("SELECT PLOTIDS FROM buildingPermissions WHERE UUID=?");
            ps.setString(1, uuid.toString());
            ResultSet rs = ps.executeQuery();
            String output = "";
            if(rs.next()){
                output = rs.getString("PLOTIDS");
                return output;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

}
