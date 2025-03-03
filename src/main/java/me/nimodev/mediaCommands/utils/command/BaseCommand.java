package me.nimodev.mediaCommands.utils.command;




import me.nimodev.mediaCommands.MediaCommands;

public abstract class BaseCommand {
    public MediaCommands plugin = MediaCommands.getInstance();

    public BaseCommand() {
        this.plugin.getCommandFramework().registerCommands(this);
    }

    public abstract void onCommand(CommandArgs args);
}
