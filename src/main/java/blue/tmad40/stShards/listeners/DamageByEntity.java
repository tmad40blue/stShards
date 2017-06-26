
package blue.tmad40.stShards.listeners;

import blue.tmad40.stShards.Main;
import blue.tmad40.stShards.utilities.ItemHelper;
import org.bukkit.Material;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;


public class DamageByEntity implements Listener {

    @EventHandler
    public void onDamageByEntityEvent(EntityDamageByEntityEvent event) {

        if (event.getDamager() instanceof Player) {

            Player attacker = (Player) event.getDamager();

            if (attacker.getInventory().getItemInMainHand() != null && attacker.getInventory().getItemInMainHand().hasItemMeta() && attacker.getInventory().getItemInMainHand().getItemMeta().hasLore()
                    && ItemHelper.canUseShards(attacker.getInventory().getItemInMainHand())) {

                ItemStack weapon = attacker.getInventory().getItemInMainHand();
                String weaponTier = ItemHelper.getTierFromItemLore(weapon);

                if (weaponTier != null) {
                    double damageModifier = Main.getInstance().getConfig().getDouble("stats.items." + ItemHelper.getItemGroup(weapon) + ".damageIncrease." + weaponTier);
                    event.setDamage(event.getDamage() + (damageModifier * event.getDamage()));
                }

            }
        }

        if (event.getDamager() instanceof Arrow) {

            if (Main.getInstance().shotArrows.get(event.getDamager()) != null) {
                double damageModifier = Main.getInstance().getConfig().getDouble(
                        "stats.items." + ItemHelper.getItemGroup(new ItemStack(Material.BOW)) + ".damageIncrease." + Main.getInstance().shotArrows.remove(event.getDamager()));
                event.setDamage(event.getDamage() + (damageModifier * event.getDamage()));
            }

        }

        if (event.getEntity() instanceof Player) {

            Player victim = (Player) event.getEntity();

            double damageModifier = 0;

            for (ItemStack armor : victim.getInventory().getArmorContents()) {

                if (armor == null) {
                    return;
                }

                if (armor.getType() == Material.AIR) {
                    return;
                }

                if (armor.getType() != null && armor.hasItemMeta() && armor.getItemMeta().hasLore() && ItemHelper.canUseShards(armor)) {
                    String armorTier = ItemHelper.getTierFromItemLore(armor);
                    if (armorTier != null) {
                        damageModifier = damageModifier
                                + Main.getInstance().getConfig().getDouble("stats.items." + ItemHelper.getItemGroup(armor) + ".damageReduction." + armorTier);
                    }
                }
            }

            event.setDamage(event.getDamage() - (damageModifier * event.getDamage()));

        }
    }
}
