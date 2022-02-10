package me.ethan.autorestart.utils;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import me.ethan.autorestart.AutoRestart;
import org.bukkit.entity.Player;

public class ProxyUtils {

    public static void sendToServer(Player player, String server) {
        try {
            ByteArrayDataOutput out = ByteStreams.newDataOutput();
            out.writeUTF("Connect");
            out.writeUTF(server);

            player.sendPluginMessage(AutoRestart.getInstance(), "BungeeCord", out.toByteArray());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
