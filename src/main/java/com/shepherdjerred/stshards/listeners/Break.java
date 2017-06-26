
package com.shepherdjerred.stshards.listeners;

import com.shepherdjerred.stshards.Main;
import com.shepherdjerred.stshards.utilities.ItemHelper;
import com.shepherdjerred.stshards.utilities.NumberHelper;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import java.util.concurrent.ThreadLocalRandom;


public class Break implements Listener {

	@EventHandler
	public void onBreakEvent(BlockBreakEvent event) {

		/*
		 * Check for the following, in order
		 * - The world the block was broken in can drop shards
		 * - The type of block broken can drop shards
		 */

		if (Main.getInstance().getConfig().getConfigurationSection("drops.worlds").getKeys(false).contains(event.getBlock().getWorld().getName())
				&& Main.getInstance().getConfig().getConfigurationSection("drops.worlds." + event.getBlock().getWorld().getName() + ".blocks").getKeys(false)
						.contains(event.getBlock().getType().toString()) && event.getPlayer().hasPermission("stShards.find")) {

			Block block = event.getBlock();
			String blockType = block.getType().toString();
			String blockWorld = block.getWorld().getName();

			// TODO Check if this block is natural or placed

			if (ThreadLocalRandom.current().nextDouble() < Main.getInstance().getConfig().getDouble("drops.worlds." + blockWorld + ".blocks." + blockType + ".chance")) {

				int randomInt = NumberHelper.randomInRange(Main.getInstance().getConfig().getInt("drops.worlds." + blockWorld + ".blocks." + blockType + ".amount.min"),
						Main.getInstance().getConfig().getInt("drops.worlds." + blockWorld + ".blocks." + blockType + ".amount.max"));

				if (randomInt > 0) {

					ItemStack shard = ItemHelper.getShardItemStack();

					shard.setAmount(randomInt);

					block.getWorld().dropItem(block.getLocation(), ItemHelper.addGlow(shard));

				}

			}

		}

	}

}