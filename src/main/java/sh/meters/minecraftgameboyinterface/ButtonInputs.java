package sh.meters.minecraftgameboyinterface;


import jdk.internal.jline.internal.Urls;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class ButtonInputs implements Listener {
    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        Player player = e.getPlayer();

        if (e.getHand() == EquipmentSlot.HAND) {
            if (e.getAction() == Action.LEFT_CLICK_AIR || e.getAction() == Action.LEFT_CLICK_BLOCK) {
                System.out.println("Pressed A");
                MinecraftGameboyInterface.makeRequest("press-a");
            }

            if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
                System.out.println("Pressed B");
                MinecraftGameboyInterface.makeRequest("press-b");
            }
        }
    }

    @EventHandler
    public void onItemDropped(PlayerDropItemEvent e) {
        System.out.println("Pressed START");
        MinecraftGameboyInterface.makeRequest("press-start");
    }

    @EventHandler
    public void onCrouch(PlayerToggleSneakEvent e) {
        if (e.isSneaking() == true) {
            System.out.println("Pressed SELECT");
            MinecraftGameboyInterface.makeRequest("press-select");
        }
    }
}
