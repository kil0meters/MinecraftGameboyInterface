package sh.meters.minecraftgameboyinterface;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Vector;

import static org.bukkit.Bukkit.getPlayer;

public class GBGroupManager implements CommandExecutor {
    Vector<Player> members = new Vector();

    public boolean has(Player player) {
        return this.members.contains(player);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;

            if (player.isOp()) {
                if (args == null) {
                    player.sendMessage(ChatColor.DARK_RED + "Expected 2 arguments, got 0");
                    return false;
                }
                if ((args.length == 1 && !args[0].equals("list")) || args.length >= 3) {
                    player.sendMessage(ChatColor.DARK_RED + "Expected 2 arguments, got" + args.length);
                    return false;
                }

                switch (args[0]) {
                    case "add":
                        Player newPlayer = getPlayer(args[1]);

                        if (newPlayer == null) {
                            player.sendMessage(ChatColor.DARK_RED + "Player not found");
                            return false;
                        }

                        if (!this.members.contains(newPlayer)) {
                            this.members.add(newPlayer);
                            player.sendMessage("Added player to group");
                            return true;
                        } else {
                            player.sendMessage(ChatColor.DARK_RED + "Player is already in group");
                            return false;
                        }
                    case "remove":
                        newPlayer = getPlayer(args[1]);

                        if (this.members.contains(newPlayer)) {
                            this.members.remove(newPlayer);
                            player.sendMessage("Removed player from group");
                            return true;
                        } else {
                            player.sendMessage(ChatColor.DARK_RED + "Player is not in group");
                            return false;
                        }
                    case "list":
                        player.sendMessage(ChatColor.GOLD + "Members");
                        for (Player member : this.members) {
                            player.sendMessage(ChatColor.GREEN + "-" + ChatColor.GREEN + member.getDisplayName());
                        }
                        break;
                    default:
                        player.sendMessage(ChatColor.DARK_RED + String.format("Unknown subcommand %s", args[1]));
                        return false;
                }
            }
            else {
                player.sendMessage(ChatColor.DARK_RED + "Permission denied");
                return false;
            }
        }
        return false;
    }
}
