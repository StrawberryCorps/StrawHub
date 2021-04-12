package bzh.strawberry.strawhub.manager;

import org.bukkit.entity.Player;

/*
This file HubPlayer is part of a project StrawHub.
It was created on 11/04/2021 at 20:07 by Uicias.
This file as the whole project shouldn't be modify by others without the express permission from StrawHub author(s).
Also this comment shouldn't get remove from the file. (see Licence.md)
*/
public class HubPlayer {

    private Player player;

    public HubPlayer(Player p){
        this.player = p;
    }

    public Player getPlayer() {
        return player;
    }

    public void sendMessage(String msg){
        player.sendMessage(msg);
    }

}
