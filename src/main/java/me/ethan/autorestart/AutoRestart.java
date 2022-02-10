package me.ethan.autorestart;

import lombok.Getter;
import lombok.Setter;
import me.ethan.autorestart.commands.AutoRestartCommand;
import me.ethan.autorestart.task.RestartTask;
import me.ethan.autorestart.task.ShutdownTask;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static me.ethan.autorestart.utils.TimeUtils.getTime;

public final class AutoRestart extends JavaPlugin {

    @Getter private static AutoRestart instance;
    @Getter @Setter private ShutdownTask shutdownTask = null;

    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();
        registerRestartTimer();
        this.initialize();
    }

    @Override
    public void onDisable() {
        instance = null;
    }

    private void initialize() {
        getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
        getCommand("ar").setExecutor(new AutoRestartCommand(this));
    }

    //When the time reached in the #getTime() method it will execute the ShutdownTask, at the set time.
    private void registerRestartTimer() {
        final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        scheduler.schedule(new RestartTask(), getTime(), MILLISECONDS);
    }

}
