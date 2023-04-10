package fourtyseven.paperplugin;


import fourtyseven.paperplugin.Commands.NPCCommands.CreateNPCCommand;
import fourtyseven.paperplugin.Commands.PlotCommands.AddPlotPermissionCommand;
import fourtyseven.paperplugin.Commands.PlotCommands.CreatePlotCommand;
import fourtyseven.paperplugin.Commands.WalletCommands.GetWalletCommand;
import fourtyseven.paperplugin.Listener.ATMListener.ClickInATMListener;
import fourtyseven.paperplugin.Listener.ATMListener.OpenATMListener;
import fourtyseven.paperplugin.Listener.BlockBreakEvent;
import fourtyseven.paperplugin.Listener.JoinListener;
import fourtyseven.paperplugin.Listener.NPCListener.ClickInNPCMenuListener;
import fourtyseven.paperplugin.Listener.NPCListener.RightClickNPCListener;
import fourtyseven.paperplugin.Listener.WalletListener.WalletRightClickListener;
import fourtyseven.paperplugin.Money.AccountManagement;
import fourtyseven.paperplugin.Money.CashManagement;
import fourtyseven.paperplugin.NPC.CustomEvent.PacketReader;
import fourtyseven.paperplugin.PlotSystem.BuildingPermissionManagementSQL;
import fourtyseven.paperplugin.PlotSystem.PlotManagementSQL;
import fourtyseven.paperplugin.SQL.MySQL;
import fourtyseven.paperplugin.Wallet.WalletManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import java.sql.SQLException;

public final class PaperPlugin extends JavaPlugin {

    private static PaperPlugin instance;
    public MySQL SQL;


    public BuildingPermissionManagementSQL buildingPermissions;
    public PlotManagementSQL plotManagementSQL;

    public AccountManagement accountManagement;
    public OpenATMListener atmListener;
    public CashManagement cashManagement;
    public ClickInATMListener atmClickListener;


    private Config config;
    private WalletManager walletManager;
    public GetWalletCommand walletCommand;

    @Override
    public void onLoad() {
        instance = this;
        config = new Config();
    }

    @Override
    public void onEnable() {
        //On Startup
        // TODO: Dies ist das Paper Plugin, in welchem NMS implementiert ist. Nicht verwechseln!
        // TODO: Handy System, in welchem man alle verfügbaren Wohnungen/Grundstücke angezeigt bekommt und dort gekauft werden können.

        PluginManager manager = getServer().getPluginManager();
        this.SQL = new MySQL();

        this.buildingPermissions = new BuildingPermissionManagementSQL(this);

        this.plotManagementSQL = new PlotManagementSQL(this);

        this.accountManagement = new AccountManagement(this);
        this.atmListener = new OpenATMListener();
        this.cashManagement = new CashManagement(this);
        this.atmClickListener = new ClickInATMListener();


        ItemStack inventory_grey = new ItemStack(Material.DIAMOND_SWORD);

        walletManager = new WalletManager();


        manager.registerEvents(new BlockBreakEvent(), this);
        manager.registerEvents(new JoinListener(), this);
        manager.registerEvents(new OpenATMListener(), this);
        manager.registerEvents(new ClickInATMListener(), this);
        manager.registerEvents(new WalletRightClickListener(), this);
        manager.registerEvents(new RightClickNPCListener(), this);
        manager.registerEvents(new ClickInNPCMenuListener(), this);

        if(!Bukkit.getOnlinePlayers().isEmpty()){
            for (Player player : Bukkit.getOnlinePlayers()){
                PacketReader reader = new PacketReader();
                reader.inject(player);
            }
        }

        getCommand("createplot").setExecutor(new CreatePlotCommand());
        getCommand("addpermission").setExecutor(new AddPlotPermissionCommand());
        getCommand("createBankAccount").setExecutor(new AccountManagement(this));
        getCommand("addMoney").setExecutor(new AccountManagement(this));
        getCommand("wallet").setExecutor(new GetWalletCommand());
        this.getCommand("npc").setExecutor(new CreateNPCCommand());


        try {
            SQL.connect();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            Bukkit.getLogger().info("Database not connected!");
        }

        if(SQL.isConnected()){
            Bukkit.getLogger().info("Database is connected.");
            //Create Tables

            plotManagementSQL.createTable();
            buildingPermissions.createTable();
            accountManagement.createTable();
            cashManagement.createCashTable();

        }
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        walletManager.save();
        config.save();
        SQL.disconnect();
        config.save();

        if(!Bukkit.getOnlinePlayers().isEmpty()){
            for (Player player : Bukkit.getOnlinePlayers()){
                PacketReader reader = new PacketReader();
                reader.uninject(player);
            }
        }
    }

    public Config getConfiguration() {
        return config;
    }

    public static PaperPlugin getInstance() {
        return instance;
    }

    public WalletManager getWalletManager(){
        return walletManager;
    }
}
