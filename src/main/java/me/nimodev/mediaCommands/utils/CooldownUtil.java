package me.nimodev.mediaCommands.utils;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import lombok.Getter;
import org.bukkit.entity.Player;

import java.util.UUID;

public class CooldownUtil {

    @Getter
    private static final Table<String, UUID, Long> cooldown = HashBasedTable.create();

    public static boolean hasCooldown(String kit, Player player) {
        return cooldown.contains(kit, player.getUniqueId()) && cooldown.get(kit, player.getUniqueId()) > System.currentTimeMillis();
    }

    public static void setCooldown(String kit, Player player, int time) {
        cooldown.put(kit, player.getUniqueId(), System.currentTimeMillis() + (time * 1000L));
    }

    public static String getCooldown(String kit, Player player) {
        return Duration.toRemaining(cooldown.get(kit, player.getUniqueId()) - System.currentTimeMillis());
    }

    public static void removeCooldown(String kit, Player player) {
        cooldown.remove(kit, player.getUniqueId());
    }

    public static Table<String, UUID, Long> getCooldownTable(){
        return cooldown;
    }
}