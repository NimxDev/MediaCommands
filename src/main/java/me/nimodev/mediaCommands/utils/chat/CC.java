package me.nimodev.mediaCommands.utils.chat;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class CC {
    public static String line(String color){
        return translate(color+"&m------------------------");
    }
    public static String translate(String source) {
        Matcher matcher = Pattern.compile("#[a-fA-F\\d]{6}").matcher(source);
        StringBuffer buffer = new StringBuffer();

        while (matcher.find()) {
            matcher.appendReplacement(buffer, net.md_5.bungee.api.ChatColor.of(matcher.group()).toString());
        }

        return ChatColor.translateAlternateColorCodes('&', matcher.appendTail(buffer).toString());
    }

    public static List<String> translate(List<String> source) {
        return source.stream().map(CC::translate).collect(Collectors.toList());
    }

    public static void print(String msg){
        Bukkit.getConsoleSender().sendMessage(CC.translate(msg));
    }

    public static void log(String message) {
        Bukkit.getConsoleSender().sendMessage(CC.translate("&c[MediaCommands] &7"+message));
    }
}
