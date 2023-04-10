package fourtyseven.paperplugin.Wallet;

import fourtyseven.paperplugin.Config;
import fourtyseven.paperplugin.PaperPlugin;

import java.io.IOException;
import java.util.*;


public class WalletManager {

    private final Map<UUID, fourtyseven.paperplugin.Wallet.Wallet> map;

    public WalletManager(){
        map = new HashMap<>();

        load();
    }

    public fourtyseven.paperplugin.Wallet.Wallet getWallet(UUID uuid){

        if(map.containsKey(uuid)){
            return map.get(uuid);
        }

        fourtyseven.paperplugin.Wallet.Wallet wallet = new fourtyseven.paperplugin.Wallet.Wallet(uuid);

        map.put(uuid, wallet);

        return wallet;
    }

    public void setWallet(UUID uuid, fourtyseven.paperplugin.Wallet.Wallet wallet){
        map.put(uuid, wallet);
    }

    private void load(){
        Config config = PaperPlugin.getInstance().getConfiguration();

        List<String> uuids = config.getConfig().getStringList("wallets");

        uuids.forEach(s -> {
            UUID  uuid = UUID.fromString(s);

            String base64 = config.getConfig().getString("wallet." + s);

            try {
                map.put(uuid, new Wallet(uuid, base64));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }



    public void save(){
        Config config = PaperPlugin.getInstance().getConfiguration();

        List<String> uuids = new ArrayList<>();

        for (UUID uuid : map.keySet()) {
            uuids.add(uuid.toString());
        }

        config.getConfig().set("wallets", uuids);
        map.forEach((uuid, backpack) -> {
            config.getConfig().set("wallet." + uuid.toString(), backpack.toBase64());
        });
    }
}