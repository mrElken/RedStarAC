package elken.anticheat.redstarac.other;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

import static elken.anticheat.redstarac.RedStarAC.*;

public class PlayersData_File {
    private File file;
    private FileConfiguration config;

    public PlayersData_File(String name) {
        file = new File(getInstance().getDataFolder(), name);
        try {
            if (!file.exists() && !file.createNewFile()) throw new IOException();
        } catch (IOException e) {
            throw new RuntimeException("Ошибка создания checks.yml", e);
        }
        config = YamlConfiguration.loadConfiguration(file);
    }

    public FileConfiguration getConfig() {
        return config;
    }
    public void save() {
        try {
            config.save(file);
        } catch (IOException e) {
            throw new RuntimeException("Ошибка сохранения параметров (checks.yml)");
        }
    }
}
