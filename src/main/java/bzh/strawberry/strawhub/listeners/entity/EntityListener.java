package bzh.strawberry.strawhub.listeners.entity;

import bzh.strawberry.strawhub.StrawHub;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;

/*
This file EntityListener is part of a project StrawHub.
It was created on 11/04/2021 at 20:06 by Uicias.
This file as the whole project shouldn't be modify by others without the express permission from StrawHub author(s).
Also this comment shouldn't get remove from the file. (see Licence.md)
*/
public class EntityListener implements Listener {

    @EventHandler(priority = EventPriority.NORMAL)
    public void onInteract(PlayerInteractAtEntityEvent event){
        Player player = event.getPlayer();
        event.setCancelled(true);

        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF("Connect");
        out.writeUTF("Login");
        player.sendPluginMessage(StrawHub.INSTANCE, "BungeeCord", out.toByteArray());

        if(event.getRightClicked() instanceof ArmorStand){
            ArmorStand armorStand = (ArmorStand) event.getRightClicked();

        }

    }

}
