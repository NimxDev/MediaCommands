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

public class VideoCommand extends BaseCommand {

    public VideoCommand() {
        super();
    }
    @Override
    @Command(name = "video", aliases = {"youtube"}, permission = "media.video", inGameOnly = true)
    public void onCommand(CommandArgs arg) {
        Player player = arg.getPlayer();
        String[] args = arg.getArgs();
        ConfigurationFile config = MediaCommands.getInstance().getConfig();
        if(args.length == 0){
            for(String s : config.getStringList("video-usage")){
                player.sendMessage(CC.translate(s));
            }
        }else {
            if (args.length == 1) {
                if(CooldownUtil.hasCooldown("Video", player)){
                    player.sendMessage(CC.translate("&cYou are still on cooldown for "+CooldownUtil.getCooldown("Video", player)));
                    return;
                }
                if(args[0].startsWith("http://") || args[0].startsWith("https://")){
                    if (args[0].contains("youtube.com/watch?v=") || args[0].contains("youtu.be/")) {
                        for(Player players : Bukkit.getServer().getOnlinePlayers()){
                            for(String msg : config.getStringList("video")){
                                if(msg.contains("%video%")){
                                    TextComponent component = new TextComponent(CC.translate(config.getString("button")));
                                    component.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new TextComponent[]{(new TextComponent(CC.translate(config.getString("hoover"))))}));
                                    component.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, args[0]));
                                    players.spigot().sendMessage(component);
                                }else {
                                    players.sendMessage(CC.translate(msg.replace("%player%", player.getName()).replace("%platform%", config.getString("youtube"))));
                                }
                            }
                        }
                        if(!player.hasPermission("media.bypass")) CooldownUtil.setCooldown("Video", player, (config.getInt("cooldown")));
                    }else {
                        player.sendMessage(CC.translate("&cInvalid Link, Make sure the link looks like this: \"https://youtube.com/watch?v=\""));
                    }

                }else {
                    player.sendMessage(CC.translate("&cInvalid Link, Make sure the link looks like this: \"https://youtube.com/watch?v=\""));
                }
            }else{
                for(String s : config.getStringList("video-usage")){
                    player.sendMessage(CC.translate(s));
                }
            }

        }
    }
}
