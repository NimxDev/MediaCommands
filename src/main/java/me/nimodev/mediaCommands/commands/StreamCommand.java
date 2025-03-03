package me.nimodev.mediaCommands.commands;

import me.nimodev.mediaCommands.ConfigurationFile;
import me.nimodev.mediaCommands.MediaCommands;
import me.nimodev.mediaCommands.utils.CooldownUtil;
import me.nimodev.mediaCommands.utils.chat.CC;
import me.nimodev.mediaCommands.utils.command.BaseCommand;
import me.nimodev.mediaCommands.utils.command.Command;
import me.nimodev.mediaCommands.utils.command.CommandArgs;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class StreamCommand extends BaseCommand {

    public StreamCommand() {
        super();
    }

    @Override
    @Command(name = "stream", aliases = {"twitch", "kick"}, permission = "media.stream", inGameOnly = true)
    public void onCommand(CommandArgs arg) {
        Player player = arg.getPlayer();
        String[] args = arg.getArgs();
        ConfigurationFile config = MediaCommands.getInstance().getConfig();
        if(arg.getArgs().length == 0){
            for(String s : config.getStringList("stream-usage")){
                player.sendMessage(CC.translate(s));
            }
        } else {
            if (arg.getArgs().length == 1) {
                if (CooldownUtil.hasCooldown("Stream", player)) {
                    player.sendMessage(CC.translate("&cYou are still on cooldown for " + CooldownUtil.getCooldown("Stream", player)));
                    return;
                }
                if (args[0].startsWith("http://") || args[0].startsWith("https://")) {
                    if (args[0].contains("youtube.com/watch?v=") || args[0].contains("youtube.com/live") || args[0].contains("twitch.tv/") || args[0].contains("kick.com/")) {
                        for (Player players : Bukkit.getServer().getOnlinePlayers()) {
                            for (String msg : config.getStringList("stream")) {
                                if (msg.contains("%stream%")) {
                                    TextComponent component = new TextComponent(CC.translate(config.getString("button")));
                                    component.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new TextComponent[]{(new TextComponent(CC.translate(config.getString("hoover"))))}));
                                    component.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, args[0]));
                                    players.spigot().sendMessage(component);
                                } else {
                                    String platform = "";
                                    if (args[0].contains("twitch.tv/")) {
                                        platform = config.getString("twitch");
                                    } else if (args[0].contains("youtube.com/")) {
                                        platform = config.getString("youtube");
                                    } else if (args[0].contains("kick.com/")) {
                                        platform = config.getString("kick");
                                    }
                                    players.sendMessage(CC.translate(msg.replace("%player%", player.getName()).replace("%platform%", platform)));
                                }
                            }
                        }
                        if (!player.hasPermission("media.bypass"))
                            CooldownUtil.setCooldown("Stream", player, (config.getInt("cooldown")));
                    } else {
                        player.sendMessage(CC.translate("&cInvalid Link, Make sure the link looks like this: \"https://twitch.tv/user\", \"https://youtube.com/watch?v=\" or \"https://kick.com/user\""));
                    }

                } else {
                    player.sendMessage(CC.translate("&cInvalid Link, Make sure the link looks like this: \"https://twitch.tv/user\", \"https://youtube.com/watch?v=\" or \"https://kick.com/user\""));
                }
            } else {
                for (String s : config.getStringList("stream-usage")) {
                    player.sendMessage(CC.translate(s));
                }
            }
        }

    }
}
