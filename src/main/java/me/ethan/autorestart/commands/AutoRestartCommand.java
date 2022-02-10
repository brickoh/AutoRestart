package me.ethan.autorestart.commands;

import me.ethan.autorestart.AutoRestart;
import me.ethan.autorestart.utils.ChatUtils;
import me.ethan.autorestart.utils.TimeUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class AutoRestartCommand implements CommandExecutor {

    private final AutoRestart auto;

    public AutoRestartCommand(AutoRestart auto) {
        this.auto = auto;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        sender.sendMessage(ChatUtils.format(auto.getConfig().getString("ar-command")
                .replace("{remaining}", TimeUtils.getTimeLeft())));
        return true;
    }


}
