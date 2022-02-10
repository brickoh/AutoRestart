package me.ethan.autorestart.task;

import me.ethan.autorestart.AutoRestart;
import java.util.TimerTask;

public class RestartTask extends TimerTask {

    @Override
    public void run() {
        if (AutoRestart.getInstance().getShutdownTask() == null) {
            AutoRestart.getInstance().setShutdownTask(new ShutdownTask(AutoRestart.getInstance(), 60));
            AutoRestart.getInstance().getShutdownTask().runTaskTimer(AutoRestart.getInstance(), 20L, 20L);
        }
    }
}
