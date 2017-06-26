
package blue.tmad40.stShards.commands;

import blue.tmad40.stShards.Main;
import blue.tmad40.stShards.files.Config;
import blue.tmad40.stShards.messages.MessageHelper;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


public class MainCommand implements CommandExecutor {

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (sender instanceof Player) {

            Player player = (Player) sender;

            if (args.length > 0) {

                if (args[0].equalsIgnoreCase("reload")) {
                    if (player.hasPermission("stShards.reload")) {

                        Main.getInstance().reloadConfig();
                        Config.getInstance().loadFiles();

                        sender.sendMessage(MessageHelper.sendReloadMessage());

                        return true;

                    } else {

                        sender.sendMessage(MessageHelper.sendNoPermsMessage());

                        return true;

                    }

                } else if (args[0].equalsIgnoreCase("help")) {
                    if (player.hasPermission("stShards.help")) {

                        // TODO Make this less ugly
                        sender.sendMessage(MessageHelper.colorMessagesString("messages.help.obtaining"));
                        sender.sendMessage(MessageHelper.colorMessagesString("messages.help.using"));
                        sender.sendMessage(MessageHelper.colorMessagesString("messages.help.effect"));

                        return true;

                    } else {

                        sender.sendMessage(MessageHelper.sendNoPermsMessage());

                        return true;

                    }

                } else {

                    sender.sendMessage(MessageHelper.sendInvalidArgsMessage(args[0], "<help, reload>"));

                    return true;

                }
            } else {

                sender.sendMessage(MessageHelper.sendNoArgsMessage("<help, reload>"));

                return true;

            }
        } else {
            if (args.length > 0) {
                if (args[0].equalsIgnoreCase("reload")) {

                    Main.getInstance().reloadConfig();
                    Config.getInstance().loadFiles();

                    Main.getInstance().getLogger().info(MessageHelper.sendReloadMessage());

                    return true;

                } else {

                    Main.getInstance().getLogger().info(MessageHelper.sendInvalidArgsMessage(args[0], "<reload>"));

                    return true;

                }
            } else {

                Main.getInstance().getLogger().info(MessageHelper.sendNoArgsMessage("<help, reload>"));

                return true;

            }
        }


    }
}
