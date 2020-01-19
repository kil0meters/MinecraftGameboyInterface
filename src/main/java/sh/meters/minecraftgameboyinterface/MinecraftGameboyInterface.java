package sh.meters.minecraftgameboyinterface;

import org.bukkit.plugin.java.JavaPlugin;

import java.net.URL;
import java.net.URLConnection;


public final class MinecraftGameboyInterface extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        getLogger().info("Hello world");

        getServer().getPluginManager().registerEvents(new DpadInputs(), this);
        getServer().getPluginManager().registerEvents(new ButtonInputs(), this);
    }

    public static void makeRequest(String apiEndpoint) {
        try {
            URL url = new URL("http://localhost:8080/".concat(apiEndpoint));

            URLConnection conn = url.openConnection();
            conn.getInputStream();
        }
        catch (Exception e) {
            System.out.println(e);
        }
    }
}
