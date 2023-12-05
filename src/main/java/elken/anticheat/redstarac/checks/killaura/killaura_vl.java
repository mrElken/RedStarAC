package elken.anticheat.redstarac.checks.killaura;

import org.bukkit.entity.Player;

import java.util.Timer;
import java.util.TimerTask;

import static elken.anticheat.redstarac.checks.killaura.KillauraA.VL_KillAuraA;

public class killaura_vl {
    public static int getVL(Player p) {
        if (VL_KillAuraA.get(p) == null) {
            setVL(p, 0);
        }
        return VL_KillAuraA.get(p);
    }

    public static void setVL(Player p, int sVL) {
        if (sVL < 0) {
            sVL = 0;
        }
        VL_KillAuraA.put(p, sVL);
    }

    public static void addVL(Player p, int aVL) {
        setVL(p, getVL(p) + aVL);
    }

    public static void resetVL(Player p) {
        setVL(p, 0);
    }

    public static void cancelVL(Player p, int cVL, long Delay) {

        TimerTask task = new TimerTask() {
            public void run() {
                int VL1 = getVL(p) - cVL;
                setVL(p, VL1);
            }
        };
        Timer timer = new Timer("Timer");
        long delay = Delay * 1000L;
        timer.schedule(task, delay);
    }
}
