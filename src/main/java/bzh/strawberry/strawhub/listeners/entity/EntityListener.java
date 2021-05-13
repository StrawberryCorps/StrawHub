package bzh.strawberry.strawhub.listeners.entity;

import bzh.strawberry.strawhub.StrawHub;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;

/*
This file EntityListener is part of a project StrawHub.
It was created on 11/04/2021 at 20:06 by Uicias.
This file as the whole project shouldn't be modify by others without the express permission from StrawHub author(s).
Also this comment shouldn't get remove from the file. (see Licence.md)
*/
public class EntityListener implements Listener {

    @EventHandler(priority = EventPriority.NORMAL)
    public void onInteract(PlayerInteractAtEntityEvent event) {
        Player player = event.getPlayer();
        if (!(event.getRightClicked() instanceof ArmorStand))
            return;
        ArmorStand ast = (ArmorStand) event.getRightClicked();
        event.setCancelled(true);

        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF("Connect");

        if (ast.getCustomName().toLowerCase().contains("decimation"))
            out.writeUTF("Decimation");
        else if (ast.getCustomName().toLowerCase().contains("survie"))
            out.writeUTF("Decimation");
        else if (ast.getCustomName().toLowerCase().contains("auth"))
            out.writeUTF("Login");
        else
            return;

        player.sendPluginMessage(StrawHub.INSTANCE, "BungeeCord", out.toByteArray());

        if (event.getRightClicked() instanceof ArmorStand) {
            ArmorStand armorStand = (ArmorStand) event.getRightClicked();

        }

    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onSpawn(EntitySpawnEvent event) {
        //cancel le spawn de créature sauf player ou armorstand
        if (!(event.getEntity() instanceof Player || event.getEntity() instanceof ArmorStand))
            event.setCancelled(true);
    }

}
