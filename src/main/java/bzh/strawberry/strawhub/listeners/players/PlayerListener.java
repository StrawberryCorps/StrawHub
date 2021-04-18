package bzh.strawberry.strawhub.listeners.players;

import bzh.strawberry.strawhub.StrawHub;
import bzh.strawberry.strawhub.manager.HubPlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

/*
This file PlayerListener is part of a project StrawHub.
It was created on 11/04/2021 at 20:05 by Uicias.
This file as the whole project shouldn't be modify by others without the express permission from StrawHub author(s).
Also this comment shouldn't get remove from the file. (see Licence.md)
*/
public class PlayerListener implements Listener {

    @EventHandler(priority = EventPriority.NORMAL)
    public void onPlayerJoin(PlayerJoinEvent event) {
        event.setJoinMessage(null);
        StrawHub.INSTANCE.getHubPlayers().add(new HubPlayer(event.getPlayer()));

        for (HubPlayer player : StrawHub.INSTANCE.getHubPlayers()) {
            player.sendMessage("§8[§a+§8] §7" + event.getPlayer().getName());
        }

    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerQuit(PlayerQuitEvent event) {
        event.setQuitMessage(null);
        StrawHub.INSTANCE.getHubPlayers().remove(StrawHub.INSTANCE.getHubPlayer(event.getPlayer()));

        for (HubPlayer player : StrawHub.INSTANCE.getHubPlayers()) {
            player.sendMessage("§8[§c-§8] §7" + event.getPlayer().getName());
        }

    }

}
