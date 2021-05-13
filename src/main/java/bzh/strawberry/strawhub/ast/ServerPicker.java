package bzh.strawberry.strawhub.ast;

import bzh.strawberry.strawhub.StrawHub;
import io.netty.handler.codec.redis.ArrayRedisMessage;
import net.minecraft.server.v1_16_R3.PlayerConnection;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;

/*
This file ServerPicker is part of a project StrawHub.
It was created on 09/05/2021 at 15:22 by Uicias.
This file as the whole project shouldn't be modify by others without the express permission from StrawHub author(s).
Also this comment shouldn't get remove from the file. (see Licence.md)
*/

public class ServerPicker {

    static Plugin plugin = StrawHub.INSTANCE;
    static List<ArmorStand> armorStands;
    private static List<MyEntityPlayer> entityPlayers;

    @SuppressWarnings("deprecated")
    public static void summonArmorStands() {

        armorStands = new ArrayList<>();

        plugin.saveDefaultConfig();

        List<String> serveurs = plugin.getConfig().getStringList("serveurs");
        System.out.println(serveurs);

        ConfigurationSection sec = plugin.getConfig().getConfigurationSection("armorstand");

        World monde = Bukkit.getWorld("world");
        Location location = new Location(monde, 0, 0, 0);

        for (String s : serveurs) {

            String name = plugin.getConfig().getString("armorstand." + s + ".name");
            location.setX(plugin.getConfig().getDouble("armorstand." + s + ".x"));
            location.setY(plugin.getConfig().getDouble("armorstand." + s + ".y"));
            location.setZ(plugin.getConfig().getDouble("armorstand." + s + ".z"));
            location.setYaw((float) plugin.getConfig().getDouble("armorstand." + s + ".yaw"));
            location.setPitch((float) plugin.getConfig().getDouble("armorstand." + s + ".pitch"));

            ArmorStand ast = monde.spawn(location, ArmorStand.class);
            ast.setCustomName(name);
            ast.setCustomNameVisible(true);
            ast.setInvulnerable(true);
            ast.setChestplate(new ItemStack(Material.DIAMOND_CHESTPLATE));

            armorStands.add(ast);

        }
    }

    public static void killArmorStand(){
        for(ArmorStand ast : armorStands)
            ast.remove();
    }

    public static void addNpc(String skin, String name, String action, Location location){
        if(entityPlayers == null)
            entityPlayers = new ArrayList<>();

        entityPlayers.add(MyEntityPlayer.build(skin, name, action, location));
    }

    //action performed in the MyEntityPlayer class to be easier to maintain to next versions
    public static void ShowEntities(Player p){
        entityPlayers.forEach(entity -> entity.showToPlayer(p));
    }

    public static MyEntityPlayer getByName(String name){
        return entityPlayers.stream().filter(ent -> ent.getName().toLowerCase().contains(name)).findFirst().orElse(null);
    }

}
