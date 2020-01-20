package sh.meters.minecraftgameboyinterface;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.inventory.EquipmentSlot;

import java.util.Objects;

public class GBInputEventListener implements Listener {
    float offsetAngle = 0;
    GBGroupManager groupManager;

    public GBInputEventListener(GBGroupManager groupManager) {
        this.groupManager = groupManager;
    }

    private void rotate(Player player) {
        if (offsetAngle == 0) {
            player.sendMessage("Forwards is now " + ChatColor.YELLOW + "LEFT");
            this.offsetAngle = 90;
        }
        else if (offsetAngle == 90) {
            player.sendMessage("Forwards is now " + ChatColor.YELLOW + "DOWN");
            this.offsetAngle = 180;
        }
        else if (offsetAngle == 180) {
            player.sendMessage("Forwards is now " + ChatColor.YELLOW + "RIGHT");
            this.offsetAngle = 270;
        }
        else if (offsetAngle == 270) {
            player.sendMessage("Forwards is now " + ChatColor.YELLOW + "UP");
            this.offsetAngle = 0;
        }
        System.out.println("Rotated");
    }

    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        Player player = e.getPlayer();

        if (this.groupManager.has(player)) {
            int movement_x = 0;
            int movement_y = 0;

            if (e.getFrom().getBlockX() != Objects.requireNonNull(e.getTo()).getBlockX()) {
                movement_x = (e.getFrom().getX() < e.getTo().getX()) ? 1 : -1;
            } else if (e.getFrom().getBlockZ() != e.getTo().getBlockZ()) {
                movement_y = (e.getFrom().getZ() < e.getTo().getZ()) ? -1 : 1;
            }

            if (movement_x != 0 || movement_y != 0) {
                float angle = Math.abs(player.getLocation().getYaw());
                angle = (angle + this.offsetAngle) % 360;

                if (angle < 45 || angle >= 315) { // facing forwards
                    // do nothing
                    movement_x = -movement_x;
                    movement_y = -movement_y;
                } else if (angle < 135 && angle >= 45) { // facing right
                    Integer _tmp = movement_y;

                    movement_y = -movement_x;
                    movement_x = _tmp;
                } else if (angle < 225 && angle >= 135) { // facing back
                    // do nothing
                } else if (angle < 315 && angle >= 225) { // facing left
                    int _tmp = movement_x;

                    movement_x = -movement_y;
                    movement_y = _tmp;
                }

                if (movement_x > 0) {
                    System.out.println("Moved RIGHT");
                    MinecraftGameboyInterface.makeRequest("press-right");
                } else if (movement_x < 0) {
                    System.out.println("Moved LEFT");
                    MinecraftGameboyInterface.makeRequest("press-left");
                } else if (movement_y > 0) {
                    System.out.println("Moved UP");
                    MinecraftGameboyInterface.makeRequest("press-up");
                } else if (movement_y < 0) {
                    System.out.println("Moved DOWN");
                    MinecraftGameboyInterface.makeRequest("press-down");
                }
            }
        }
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        Player player = e.getPlayer();

        if (this.groupManager.has(player)) {
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
    }

    @EventHandler
    public void onItemDrop(PlayerDropItemEvent e) {
        if (this.groupManager.has(e.getPlayer())) {
            if (Objects.requireNonNull(e.getItemDrop().getItemStack().getItemMeta()).getDisplayName().equals("§r§eGameboy Controller")) {
                this.rotate(e.getPlayer());
                e.setCancelled(true);
            } else {
                System.out.println("Pressed START");
                MinecraftGameboyInterface.makeRequest("press-start");
            }
        }
    }

    @EventHandler
    public void onCrouch(PlayerToggleSneakEvent e) {
        if (this.groupManager.has(e.getPlayer())) {
            if (e.isSneaking()) {
                System.out.println("Pressed SELECT");
                MinecraftGameboyInterface.makeRequest("press-select");
            }
        }
    }
}
