
package com.shepherdjerred.stshards.listeners;

import com.shepherdjerred.stshards.Main;
import com.shepherdjerred.stshards.messages.MessageHelper;
import com.shepherdjerred.stshards.utilities.ItemHelper;
import com.shepherdjerred.stshards.utilities.LocationHelper;
import org.apache.commons.lang3.text.WordUtils;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;


public class Interact implements Listener {

	@EventHandler
	public void onInteractEvent(PlayerInteractEvent event) {

		/*
		 * Check for the following, in order
		 * - Action was right-clicking a block
		 * - The player has an item in their hand
		 */

		if (event.getAction() == Action.RIGHT_CLICK_BLOCK && event.getPlayer().getInventory().getItemInMainHand() != null) {

			Block block = event.getClickedBlock();
			Player player = event.getPlayer();
			ItemStack handItem = player.getInventory().getItemInMainHand();
			ItemMeta handMeta = handItem.getItemMeta();

			if (player.hasPermission("stShards.use")) {

				if (ItemHelper.canUseShards(handItem) && block.getLocation().equals(LocationHelper.getConfigLocation("upgrades.location"))) {

					// TODO Check for rain
					// TODO Allow multiple locations?
					// TODO Allow different shard quantity requirements per tier
					// TODO Add break/fail chances

					String nextTierString;
					int nextTierNumber = 0;

					if (handItem.hasItemMeta() && handItem.getItemMeta().hasLore()) {

						String currentTierString = ItemHelper.getTierFromItemLore(handItem);

						if (currentTierString != null) {

							int currentTierNumber = Integer.parseInt(currentTierString);

							nextTierNumber = currentTierNumber + 1;
						}

					} else {

						nextTierNumber = 1;

					}

					nextTierString = String.valueOf(nextTierNumber);

					if (event.getPlayer().getInventory().containsAtLeast(ItemHelper.getShardItemStack(), 1)) {

						boolean atMax = nextTierNumber > Main.getInstance().getConfig().getConfigurationSection("upgrades.tiers").getKeys(false).size();

						if (!atMax) {

							handMeta.setLore(ItemHelper.getShardTierLore(nextTierString));

							handItem.setItemMeta(handMeta);

							if (Main.getInstance().getConfig().getBoolean("upgrades.tiers." + nextTierString + ".glow")) {

								ItemHelper.addGlow(handItem);

							}

							if (Main.getInstance().getConfig().getBoolean("upgrades.tiers." + nextTierString + ".lightning")) {

								for (int c = 0; c < nextTierNumber; c++) {

									int delay = c * 20;

									new BukkitRunnable() {

										@Override
										public void run() {

											player.getWorld().strikeLightningEffect(player.getLocation());
											player.getWorld().strikeLightningEffect(block.getLocation());

										}

									}.runTaskLater(Main.getInstance(), delay);

								}

							}

							if (Main.getInstance().getConfig().getBoolean("upgrades.tiers." + nextTierString + ".broadcast")) {

								Bukkit.broadcastMessage(MessageHelper.colorMessagesString("messages.upgrading.broadcast").replace("%player%", player.getName())
										.replace("%item%", WordUtils.capitalizeFully(handItem.getType().toString().replace("_", " "))).replace("%tier%", nextTierString));

							}

							ItemStack removeItem = ItemHelper.getShardItemStack();
							removeItem.setAmount(1);

							player.getInventory().removeItem(removeItem);

						}
					}
				}

			}

		}
	}
}
