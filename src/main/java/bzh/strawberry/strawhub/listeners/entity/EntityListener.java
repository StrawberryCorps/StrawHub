package bzh.strawberry.strawhub.listeners.entity;

import bzh.strawberry.strawhub.StrawHub;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import net.minecraft.server.v1_16_R3.EntityPlayer;
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

        System.out.println("INTERACTS");

        Player player = event.getPlayer();
        if (!(event.getRightClicked() instanceof EntityPlayer))
            return;
        EntityPlayer entity = (EntityPlayer) event.getRightClicked();
        event.setCancelled(true);

        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF("Connect");

        if (entity.getCustomName().getString().toLowerCase().contains("decimation"))
            out.writeUTF("Decimation");
        else if (entity.getCustomName().getString().toLowerCase().contains("survie"))
            out.writeUTF("Decimation");
        else if (entity.getCustomName().getString().toLowerCase().contains("auth"))
            out.writeUTF("Login");
        else
            return;

        player.sendPluginMessage(StrawHub.INSTANCE, "BungeeCord", out.toByteArray());

    }

    //todo add listener pour putain de NPC

    @EventHandler(priority = EventPriority.LOWEST)
    public void onSpawn(EntitySpawnEvent event) {
        //cancel le spawn de créature sauf player ou armorstand
        if (!(event.getEntity() instanceof Player || event.getEntity() instanceof ArmorStand))
            event.setCancelled(true);
    }

}
