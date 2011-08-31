package org.spoutcraft.spoutcraftapi.entity;

import org.spoutcraft.spoutcraftapi.inventory.ItemStack;
import org.spoutcraft.spoutcraftapi.inventory.PlayerInventory;

public interface HumanEntity extends LivingEntity, AnimalTamer {

	public String getName();

	public PlayerInventory getInventory();

	public ItemStack getItemInHand();

	public void setItemInHand(ItemStack item);

	public boolean isSleeping();

	public int getSleepTicks();

}
