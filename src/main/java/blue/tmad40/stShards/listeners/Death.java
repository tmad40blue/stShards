
package blue.tmad40.stShards.listeners;

import blue.tmad40.stShards.utilities.NumberHelper;
import blue.tmad40.stShards.Main;
import blue.tmad40.stShards.utilities.ItemHelper;
import io.netty.util.internal.ThreadLocalRandom;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;


public class Death implements Listener {

	@EventHandler
	public void onEntityDeath(EntityDeathEvent event) {

		/*
		 * Check for the following, in order
		 * - Killer is a player
		 * - The world the mob was killed in can drop shards
		 * - The type of mob killed can drop shards
		 */

		if (Main.getInstance().getConfig().getConfigurationSection("drops.worlds").getKeys(false).contains(event.getEntity().getWorld().getName())
				&& Main.getInstance().getConfig().getConfigurationSection("drops.worlds." + event.getEntity().getWorld().getName() + ".mobs").getKeys(false)
						.contains(event.getEntityType().toString()) && event.getEntity().getKiller() != null && event.getEntity().getKiller().hasPermission("stShards.find")) {

			String entityType = event.getEntity().getType().toString();
			String entityWorld = event.getEntity().getWorld().getName();
			Location entityLocation = event.getEntity().getLocation();

			if (ThreadLocalRandom.current().nextDouble() < Main.getInstance().getConfig().getDouble("drops.worlds." + entityWorld + ".mobs." + entityType + ".chance")) {

				int randomInt = NumberHelper.randomInRange(Main.getInstance().getConfig().getInt("drops.worlds." + entityWorld + ".mobs." + entityType + ".amount.min"),
						Main.getInstance().getConfig().getInt("drops.worlds." + entityWorld + ".mobs." + entityType + ".amount.max"));

				if (randomInt != 0) {

					ItemStack shard = ItemHelper.getShardItemStack();
					
					shard.setAmount(randomInt);

					entityLocation.getWorld().dropItem(entityLocation, ItemHelper.addGlow(shard));

				}
			}

		}

	}

}
