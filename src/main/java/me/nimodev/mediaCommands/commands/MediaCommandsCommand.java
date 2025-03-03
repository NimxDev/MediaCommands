package me.nimodev.mediaCommands.commands;

import me.nimodev.mediaCommands.MediaCommands;
import me.nimodev.mediaCommands.utils.chat.CC;
import me.nimodev.mediaCommands.utils.command.BaseCommand;
import me.nimodev.mediaCommands.utils.command.Command;
import me.nimodev.mediaCommands.utils.command.CommandArgs;
import org.bukkit.command.CommandSender;

public class MediaCommandsCommand extends BaseCommand {

    public MediaCommandsCommand() {
        super();
    }
    @Override
    @Command(name = "mediacommands", permission = "mediacommands.admin")
    public void onCommand(CommandArgs args) {
        CommandSender sender = args.getSender();
        if(args.length() == 0) {
            sender.sendMessage(CC.translate("&aUsage:"));
            sender.sendMessage(CC.translate("&f- &2/mediacommands reload"));
        }else {
            if(args.getArgs().length == 1 && args.getArgs(0).equalsIgnoreCase("reload")) {
                MediaCommands.getInstance().reload();
                sender.sendMessage(CC.translate("&aConfiguration reloaded!"));
            }
        }
    }
}
