package fourtyseven.paperplugin.NPC.CustomEvent;

import fourtyseven.paperplugin.Commands.NPCCommands.CreateNPCCommand;
import fourtyseven.paperplugin.PaperPlugin;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import net.minecraft.server.v1_12_R1.EntityPlayer;
import net.minecraft.server.v1_12_R1.Packet;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

import io.netty.channel.Channel;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class PacketReader {

    Channel channel;
    public static Map<UUID, Channel> channels = new HashMap<UUID, Channel>();

    public void inject(Player player){
        CraftPlayer craftPlayer = (CraftPlayer) player;
        channel = craftPlayer.getHandle().playerConnection.networkManager.channel;
        channels.put(player.getUniqueId(), channel);

        if(channel.pipeline().get("PacketInjector") != null){
            return;
        }
        channel.pipeline().addAfter("decoder", "PacketInjector", new MessageToMessageDecoder<Packet<?>>() {
            @Override
            protected void decode(ChannelHandlerContext channel, Packet<?> packet, List<Object> arg) throws Exception {
                arg.add(packet);
                readPacket(player, packet);
            }
        });
    }

    public void uninject(Player player){
        channel = channels.get(player.getUniqueId());
        if(channel.pipeline().get("PacketInjector") != null){
            channel.pipeline().remove("PacketInjector");
        }
    }

    public void readPacket(Player player, Packet<?> packet){

        System.out.println("PACKET >> " + packet);

        if(packet.getClass().getSimpleName().equalsIgnoreCase("PacketPlayInUseEntity")){
            if(getValue(packet, "action").toString().equalsIgnoreCase("ATTACK")){
                return;
            }
            if(getValue(packet, "d").toString().equalsIgnoreCase("OFF_HAND")){
                return;
            }
            if(getValue(packet, "action").toString().equalsIgnoreCase("INTERACT_AT")){
                return;
            }
            int id = (int) getValue(packet, "a");

            if(getValue(packet, "action").toString().equalsIgnoreCase("INTERACT")){
                for (EntityPlayer npc : CreateNPCCommand.getNPCs()){
                    if(npc.getId() == id){
                        Bukkit.getScheduler().scheduleSyncDelayedTask(PaperPlugin.getPlugin(PaperPlugin.class), new Runnable() {
                            @Override
                            public void run() {
                                RightClickNPC clickEvent = new RightClickNPC(player, npc);
                                //Bukkit.getServer().getPluginManager().callEvent(clickEvent);
                                clickEvent.callEvent();
                            }
                        }, 0);
                    }
                }
            }
        }
    }

    private Object getValue(Object instance, String name){
        Object result = null;
        try {
            Field field = instance.getClass().getDeclaredField(name);
            field.setAccessible(true);
            result = field.get(instance);
            field.setAccessible(false);
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }
}