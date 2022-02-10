package me.ethan.autorestart.task;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import me.ethan.autorestart.AutoRestart;
import me.ethan.autorestart.utils.ChatUtils;
import me.ethan.autorestart.utils.ProxyUtils;
import org.bukkit.World;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Arrays;
import java.util.List;

public class ShutdownTask extends BukkitRunnable {

    private final AutoRestart auto;
    private final List<Integer> BROADCAST_TIMES = Arrays.asList(60, 45, 30, 15, 10, 5, 4, 3, 2, 1);
    private int seconds;

    public ShutdownTask(AutoRestart auto, int seconds) {
        this.auto = auto;
        this.seconds = seconds;
    }

    @Override
    public void run() {
        //Broadcasting that the server wil shutdown if the seconds contain the numbers in the list.
        if (BROADCAST_TIMES.contains(seconds)) {
            this.auto.getServer().broadcastMessage(ChatUtils.format(
                    auto.getConfig().getString("restart.broadcast")
                            .replace("{remaining}", String.valueOf(seconds))));
        }

        if (this.seconds <= 5) {
            //Saving all data in each world then sending the players to the fallback server
            auto.getServer().getWorlds().forEach(World::save);
            auto.getServer().getOnlinePlayers().forEach(player -> ProxyUtils.sendToServer(player,
                    auto.getConfig().getString("fallback-server")));
        }

        if (this.seconds <= 0) {
            //Executing the restart instance within spigot
            auto.getServer().spigot().restart();
        }
        this.seconds--;
    }
}