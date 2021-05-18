package bzh.strawberry.strawhub;

import bzh.strawberry.strawhub.ast.ServerPicker;
import bzh.strawberry.strawhub.listeners.entity.CustomEntityListener;
import bzh.strawberry.strawhub.listeners.entity.EntityListener;
import bzh.strawberry.strawhub.listeners.players.PlayerListener;
import bzh.strawberry.strawhub.manager.HubPlayer;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

/*
This file StrawHub is part of a project StrawHub.
It was created on 11/04/2021 at 19:40 by Uicias.
This file as the whole project shouldn't be modify by others without the express permission from StrawHub author(s).
Also this comment shouldn't get remove from the file. (see Licence.md)
*/
public class StrawHub extends JavaPlugin {

    List<HubPlayer> hubPlayers;
    public static StrawHub INSTANCE;

    @Override
    public void onEnable() {
        long tick = System.currentTimeMillis();
        getLogger().info("######################## [StrawHub - " + getDescription().getVersion() + "] #################################");
        getLogger().info("Auteurs : " + getDescription().getAuthors());
        getLogger().info("Chargement du Hub....");

        this.getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
        INSTANCE = this;
        this.hubPlayers = new ArrayList<>();
        this.saveDefaultConfig();

        getLogger().info("Chargement des listeners...");
        getServer().getPluginManager().registerEvents(new PlayerListener(), this);
        getServer().getPluginManager().registerEvents(new EntityListener(), this);
        getServer().getPluginManager().registerEvents(new CustomEntityListener(), this);
        getLogger().info("Chargement des listeners... FIN");

        getLogger().info("Ajout des armors stands...");
        getLogger().info("  Created " + ServerPicker.createNpc() + " NPCs");
        getLogger().info("Ajout des armors stands... FIN");

        getLogger().info("Chargement du Hub effectué en "+(System.currentTimeMillis() - tick)+" ms.");
        getLogger().info("######################## [StrawHub - " + getDescription().getVersion() + "] #################################");
    }

    /**
     * @return {@link List} of {@link HubPlayer}
     */

    public List<HubPlayer> getHubPlayers() {
        return this.hubPlayers;
    }

    public HubPlayer getHubPlayer(Player player) {
        return this.hubPlayers.stream().filter(hubPlayer -> hubPlayer.getPlayer() == player).findFirst().orElse(null);
    }

    public Location getSpawnLocation(){
        return new Location(Bukkit.getWorld("world"), 0, 22, 0);
    }

    @Override
    public void onDisable() {
        ServerPicker.killNpc();
    }


}
