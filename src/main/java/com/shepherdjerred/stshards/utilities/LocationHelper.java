
package com.shepherdjerred.stshards.utilities;

import com.shepherdjerred.stshards.Main;
import org.bukkit.Location;
import org.bukkit.World;


public class LocationHelper {

	// Parses a config section from strings to a Location
	public static Location getConfigLocation(String input) {
		
		World world = Main.getInstance().getServer().getWorld(Main.getInstance().getConfig().getString(input + ".world"));
		int x = Main.getInstance().getConfig().getInt(input + ".x");
		int y = Main.getInstance().getConfig().getInt(input + ".y");
		int z = Main.getInstance().getConfig().getInt(input + ".z");

		return new Location(world, x, y, z);

	}

}
