package bzh.strawberry.strawhub.ast;

import bzh.strawberry.strawhub.StrawHub;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import com.mojang.util.UUIDTypeAdapter;
import net.minecraft.server.v1_16_R3.*;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_16_R3.CraftServer;
import org.bukkit.craftbukkit.v1_16_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
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

    public MyEntityPlayer(MinecraftServer minecraftserver, WorldServer worldserver, GameProfile gameprofile, PlayerInteractManager playerinteractmanager, String action, Location location) {
        super(minecraftserver, worldserver, gameprofile, playerinteractmanager);
        this.action = action;
        this.location = location;
    }

    public static MyEntityPlayer build(String skin_voulu, String name, String action, Location location) {
        MinecraftServer server = ((CraftServer) Bukkit.getServer()).getServer();
        WorldServer world = ((CraftWorld) Bukkit.getWorld("world")).getHandle();
        GameProfile profile = new GameProfile(UUID.randomUUID(), skin_voulu);
        Player player = (Player) Bukkit.getOfflinePlayer(skin_voulu);

        try {
            HttpsURLConnection connection = (HttpsURLConnection) new URL(String.format("https://sessionserver.mojang.com/session/minecraft/profile/%s?unsigned=false",
                    UUIDTypeAdapter.fromUUID(player.getUniqueId()))).openConnection();
            if (connection.getResponseCode() == HttpsURLConnection.HTTP_OK) {
                String reply = new BufferedReader(new InputStreamReader(connection.getInputStream())).readLine();
                String skin = reply.split("\"value\":\"")[1].split("\"")[0];
                String signature = reply.split("\"signature\":\"")[1].split("\"")[0];

                profile.getProperties().put("textures", new Property("textures", skin, signature));
            } else {
                StrawHub.INSTANCE.getLogger().warning("Connection could not be opened (Response code " + connection.getResponseCode() + ", " + connection.getResponseMessage() + ")");
            }
        } catch (IOException malformedURLException) {
            malformedURLException.printStackTrace();
        }

        IChatBaseComponent customName = new ChatMessage("name");

        MyEntityPlayer npc = new MyEntityPlayer(server, world, profile, new PlayerInteractManager(world), action, location);
        npc.setLocation(location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch());
        npc.setCustomName(customName);
        npc.setCustomNameVisible(true);

        return npc;
    }

    public String getAction() {
        return this.action;
    }

    public String getName() {
        return this.name;
    }

    public void showToPlayer(Player player) {
        PlayerConnection connection = ((CraftPlayer) player).getHandle().playerConnection;
        connection.sendPacket(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER, this));
        connection.sendPacket(new PacketPlayOutNamedEntitySpawn(this));
    }

}
