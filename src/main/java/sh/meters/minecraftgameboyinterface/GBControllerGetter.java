package sh.meters.minecraftgameboyinterface;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Collections;

public class GBControllerGetter implements CommandExecutor {
    GBGroupManager groupManager;

    public GBControllerGetter(GBGroupManager groupManager) {
        this.groupManager = groupManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;

            if (groupManager.has(player)) {
                ItemStack controller = new ItemStack(Material.NETHER_STAR);
                ItemMeta meta = controller.getItemMeta();

                assert meta != null;
                meta.setDisplayName(ChatColor.RESET + "" + ChatColor.YELLOW + "Gameboy Controller");
                meta.setLore(Collections.singletonList("Used to control player DPad rotation"));
                controller.setItemMeta(meta);

                player.getInventory().addItem(controller);

                return true;
            }
            else {
                player.sendMessage(ChatColor.DARK_RED + "Not in group");
                return false;
            }
        }
        return false;
    }
}