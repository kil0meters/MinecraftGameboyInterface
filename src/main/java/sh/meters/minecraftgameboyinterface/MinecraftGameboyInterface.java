package sh.meters.minecraftgameboyinterface;

import org.bukkit.plugin.java.JavaPlugin;

import java.net.URL;
import java.net.URLConnection;
import java.util.Objects;


public final class MinecraftGameboyInterface extends JavaPlugin {

    @Override
    public void onEnable() {
        GBGroupManager groupManager = new GBGroupManager();

        getServer().getPluginManager().registerEvents(new GBInputEventListener(groupManager), this);
        Objects.requireNonNull(getCommand("gb-group")).setExecutor(groupManager);
        Objects.requireNonNull(getCommand("gb-controller")).setExecutor(new GBControllerGetter(groupManager));
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
