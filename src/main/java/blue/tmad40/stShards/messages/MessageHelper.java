
package blue.tmad40.stShards.messages;

import blue.tmad40.stShards.Main;
import blue.tmad40.stShards.files.Config;
import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.List;


public class MessageHelper {

	// Returns a colored string from the config.yml file
	public static String colorConfigString(String input) {

		return ChatColor.translateAlternateColorCodes('&', Main.getInstance().getConfig().getString(input));

	}

	// Returns a colored list of strings from the config.yml file
	public static List<String> colorConfigListStrings(String input) {

		List<String> output = new ArrayList<>();

		for (String s : Main.getInstance().getConfig().getStringList(input)) {

			output.add(ChatColor.translateAlternateColorCodes('&', s));

		}

		return output;

	}

	// Returns a colored string from the messages.yml file
	public static String colorMessagesString(String input) {

		return ChatColor.translateAlternateColorCodes('&', Config.getInstance().messages.getString(input));

	}

	public static String sendReloadMessage() {

		String message = colorMessagesString("prefix") + colorMessagesString("messages.generic.reload");

		return message;

	}

	public static String sendNoPermsMessage() {

		String message = colorMessagesString("prefix") + colorMessagesString("messages.generic.noPerms");

		return message;

	}

	public static String sendNoConsoleMessage() {

		String message = colorMessagesString("prefix") + colorMessagesString("messages.generic.noConsole");

		return message;

	}

	public static String sendNoArgsMessage(String correctArgs) {

		String message = colorMessagesString("prefix") + colorMessagesString("messages.generic.noArgs");

		message.replace("%args%", correctArgs);

		return message;

	}

	public static String sendInvalidArgsMessage(String givenArg, String correctArgs) {

		String message = colorMessagesString("prefix") + colorMessagesString("messages.generic.invalidArg");

		message.replace("%arg%", givenArg).replace("%args%", correctArgs);

		return message;

	}

}