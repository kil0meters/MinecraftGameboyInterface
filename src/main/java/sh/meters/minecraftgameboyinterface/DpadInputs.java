package sh.meters.minecraftgameboyinterface;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class DpadInputs implements Listener {
    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        Player player = e.getPlayer();

        if (Math.floor(e.getFrom().getX()) != Math.floor(e.getTo().getX())) {
            if (e.getFrom().getX() < e.getTo().getX()) {
                System.out.println("Moved LEFT");
                MinecraftGameboyInterface.makeRequest("press-left");
            }
            else {
                System.out.println("Moved RIGHT");
                MinecraftGameboyInterface.makeRequest("press-right");
            }
        }
        if (Math.floor(e.getFrom().getZ()) != Math.floor(e.getTo().getZ())) {
            if (e.getFrom().getZ() < e.getTo().getZ()) {
                System.out.println("Moved UP");
                MinecraftGameboyInterface.makeRequest("press-up");
            }
            else {
                System.out.println("Moved DOWN");
                MinecraftGameboyInterface.makeRequest("press-down");
            }
        }
    }


}
