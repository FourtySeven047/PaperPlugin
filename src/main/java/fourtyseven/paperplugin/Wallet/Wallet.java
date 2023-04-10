package fourtyseven.paperplugin.Wallet;

import fourtyseven.paperplugin.Base64;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;

import java.io.IOException;
import java.util.UUID;

public class Wallet {

    private final UUID uuid;
    private final Inventory inventory;

    public Wallet(UUID uuid){
        this.uuid = uuid;
        this.inventory = Bukkit.createInventory(null, 9, "Wallet");
    }

    public Wallet(UUID uuid, String base64) throws IOException {
        this.uuid = uuid;
        this.inventory = Bukkit.createInventory(null, 9, "Wallet");
        this.inventory.setContents(Base64.itemStackArrayFromBase64(base64));
    }

    public UUID getUuid() {

        return uuid;
    }

    public Inventory getInventory(){

        return inventory;
    }

    public String toBase64(){
        return Base64.itemStackArrayToBase64(inventory.getContents());
    }
}
