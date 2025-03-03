package me.nimodev.mediaCommands;

import lombok.Getter;
import me.nimodev.mediaCommands.commands.MediaCommandsCommand;
import me.nimodev.mediaCommands.commands.StreamCommand;
import me.nimodev.mediaCommands.commands.VideoCommand;
import me.nimodev.mediaCommands.utils.chat.CC;
import me.nimodev.mediaCommands.utils.command.CommandFramework;
import org.bukkit.plugin.java.JavaPlugin;
@Getter
public final class MediaCommands extends JavaPlugin {
    @Getter
    private static MediaCommands instance;
    private CommandFramework commandFramework;
    private ConfigurationFile config;
    @Override
    public void onEnable() {
        instance = this;
        commandFramework = new CommandFramework(this);
        commandFramework.registerHelp();
        config = new ConfigurationFile(this, "config.yml");
        new StreamCommand();
        new VideoCommand();
        new MediaCommandsCommand();
        CC.log("&aPlugin enabled!");
    }

    @Override
    public void onDisable() {
        CC.log("&cPlugin disabled!");
    }

    public void reload(){
        this.config = new ConfigurationFile(this, "config.yml");
    }
}
