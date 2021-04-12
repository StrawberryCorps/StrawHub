package bzh.strawberry.strawhub;

import bzh.strawberry.strawhub.listeners.entity.EntityListener;
import bzh.strawberry.strawhub.listeners.players.PlayerListener;
import bzh.strawberry.strawhub.manager.HubPlayer;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Collections;
import java.util.List;

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

        getLogger().info("Chargement des listeners...");
        getServer().getPluginManager().registerEvents(new PlayerListener(), this);
        getServer().getPluginManager().registerEvents(new EntityListener(), this);
        getLogger().info("Chargement des listeners... FIN");

        getLogger().info("Chargement du Hub effectué en "+(System.currentTimeMillis() - tick)+" ms.");
        getLogger().info("######################## [Survie - " + getDescription().getVersion() + "] #################################");
    }

    /**
     * Renvoie la liste des joueurs du hub sous la forme
     * d'une liste NON modifiable
     *
     * @return {@link List} of {@link HubPlayer}
     */

    public List<HubPlayer> getHubPlayers() {
        return Collections.unmodifiableList(hubPlayers);
    }

}
