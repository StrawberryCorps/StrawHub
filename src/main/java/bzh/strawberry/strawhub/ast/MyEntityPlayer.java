package bzh.strawberry.strawhub.ast;

import bzh.strawberry.strawhub.StrawHub;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import net.minecraft.server.v1_16_R3.*;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_16_R3.CraftServer;
import org.bukkit.craftbukkit.v1_16_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.CreatureSpawnEvent;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.UUID;

/*
This file MyEntityPlayer is part of a project StrawHub.
It was created on 13/05/2021 at 22:37 by Uicias.
This file as the whole project shouldn't be modify by others without the express permission from StrawHub author(s).
Also this comment shouldn't get remove from the file. (see Licence.md)
*/

public class MyEntityPlayer extends EntityPlayer {

    private String skin, name, action;
    private Location location;

    public MyEntityPlayer(MinecraftServer minecraftserver, WorldServer worldserver, GameProfile gameprofile, PlayerInteractManager playerinteractmanager,
                          String action, Location location, String skin, String name) {
        super(minecraftserver, worldserver, gameprofile, playerinteractmanager);
        this.action = action;
        this.location = location;
        this.skin = skin;
        this.name = name;
    }

    public static MyEntityPlayer build(UUID skin_voulu, String name, String action, Location location) {
        MinecraftServer server = ((CraftServer) Bukkit.getServer()).getServer();
        WorldServer world = ((CraftWorld) Bukkit.getWorld("world")).getHandle();
        GameProfile profile = new GameProfile(UUID.randomUUID(), name);

        try {

            HttpsURLConnection connection = (HttpsURLConnection) new URL(String.format("https://sessionserver.mojang.com/session/minecraft/profile/%s?unsigned=false", skin_voulu.toString())).openConnection();
            if (connection.getResponseCode() == HttpsURLConnection.HTTP_OK) {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String lecture, reply = "";
                while((lecture = bufferedReader.readLine()) != null){
                    reply = reply.concat(lecture);
                }

                String skin = reply.split("value")[1].split("\"")[2];
                String signature = reply.split("signature")[1].split("\"")[2];

                profile.getProperties().put("textures", new Property("textures", skin, signature));
            } else {
                StrawHub.INSTANCE.getLogger().warning("Connection could not be opened (Response code " + connection.getResponseCode() + ", " + connection.getResponseMessage() + ")");
            }
        } catch (IOException malformedURLException) {
            malformedURLException.printStackTrace();
        }

        IChatBaseComponent customName = new ChatMessage(name);

        MyEntityPlayer npc = new MyEntityPlayer(server, world, profile, new PlayerInteractManager(world), action, location, profile.getName(), name);
        npc.setLocation(location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch());
        npc.setCustomName(customName);
        npc.setCustomNameVisible(true);

//        world.addEntity(npc, CreatureSpawnEvent.SpawnReason.CUSTOM);

        StrawHub.INSTANCE.getLogger().info("Created : " + npc);
        return npc;
    }

    public String getName() {
        return this.name;
    }

    public void showToPlayer(Player player) {
        PlayerConnection connection = ((CraftPlayer) player).getHandle().playerConnection;
        connection.sendPacket(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER, this));
        connection.sendPacket(new PacketPlayOutNamedEntitySpawn(this));
    }

    @Override
    public String toString() {
        return String.format("[NPC: %s, Skin:%s, Action: %s @%s]", name, skin, action, location.toString());
    }

    public void onInteract(boolean b, Player damager) {

        damager.sendMessage("INTERACTION");

        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF("Connect");
        out.writeUTF(action);
        damager.sendPluginMessage(StrawHub.INSTANCE, "BungeeCord", out.toByteArray());

    }

    @Override
    public void killEntity(){
        super.killEntity();
//        ((CraftWorld) Bukkit.getWorld("world")).getHandle().removeEntity(this);
    }

}
