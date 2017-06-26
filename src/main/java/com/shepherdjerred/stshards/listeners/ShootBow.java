
package com.shepherdjerred.stshards.listeners;

import com.shepherdjerred.stshards.Main;
import com.shepherdjerred.stshards.utilities.ItemHelper;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.inventory.ItemStack;


public class ShootBow implements Listener {

	@EventHandler
	public void onDamageByEntityEvent(EntityShootBowEvent event) {

		if (event.getEntity() instanceof Player && event.getBow().hasItemMeta() && event.getBow().getItemMeta().hasLore() && ItemHelper.canUseShards(event.getBow())) {

			ItemStack bow = event.getBow();
			String bowTier = ItemHelper.getTierFromItemLore(bow);

			if (bowTier != null) {
				Main.getInstance().shotArrows.put((Arrow) event.getProjectile(), bowTier);
			}

		}
	}

}
