package bzh.strawberry.strawhub.listeners.entity;

import bzh.strawberry.strawhub.StrawHub;
import bzh.strawberry.strawhub.ast.MyEntityPlayer;
import bzh.strawberry.api.util.Reflection;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;

/*
This file CustomEntityListener is part of a project StrawHub.
It was created on 14/05/2021 at 22:50 by Uicias.
This file as the whole project shouldn't be modify by others without the express permission from StrawHub author(s).
Also this comment shouldn't get remove from the file. (see Licence.md)
*/
public class CustomEntityListener implements Listener {

    public CustomEntityListener() {
        StrawHub.INSTANCE.getLogger().info("Registered CustomEntityListener");
        Bukkit.getPluginManager().registerEvents(this, StrawHub.INSTANCE);
    }

    @EventHandler
    public void onPlayerHitNPC(EntityDamageByEntityEvent event) {

        if (event.getDamager() instanceof Player) {
            Player o = (Player) event.getDamager();
            o.sendRawMessage("SALUT MA SALOPE");
        }

        if (Reflection.getHandle(event.getEntity()) instanceof MyEntityPlayer && event.getDamager() instanceof Player) {
            MyEntityPlayer npc = (MyEntityPlayer) Reflection.getHandle(event.getEntity());
            npc.onInteract(false, (Player) event.getDamager());
        }
    }

    @EventHandler
    public void onPlayerInteractNPC(PlayerInteractEntityEvent event)
    {
        if (Reflection.getHandle(event.getRightClicked()) instanceof MyEntityPlayer)
        {
            MyEntityPlayer npc = (MyEntityPlayer) Reflection.getHandle(event.getRightClicked());
            npc.onInteract(true, event.getPlayer());
        }
    }

}
