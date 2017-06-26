
package com.shepherdjerred.stshards.files;

import com.shepherdjerred.stshards.Main;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.*;


public class Config {

	private static Config instance;
    public File messagesf;
    public FileConfiguration messages;

	Config() {
		instance = this;
	}

	public static Config getInstance() {
		if (instance == null) {
			instance = new Config();
		}
		return instance;
	}

	// Load & reload files
	public void loadFiles() {

		messagesf = new File(Main.getInstance().getDataFolder(), "messages.yml");

		if (!messagesf.exists()) {
			messagesf.getParentFile().mkdirs();
			copy(Main.getInstance().getResource("messages.yml"), messagesf);
		}

		messages = new YamlConfiguration();

		try {

			messages.load(messagesf);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	// Save files
	public void saveFiles(String input) {
		try {

			if (input.equals("messages")) {
				messages.save(messagesf);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// Copy default files
	public void copy(InputStream in, File file) {

		try {

			OutputStream out = new FileOutputStream(file);
			byte[] buf = new byte[1024];
			int len;
			while ((len = in.read(buf)) > 0) {

				out.write(buf, 0, len);

			}
			out.close();
			in.close();

		} catch (Exception e) {

			e.printStackTrace();

		}

	}

}
