package com.kingOf0.drop.base.action

import com.kingOf0.drop.util.checkItem
import org.bukkit.configuration.ConfigurationSection
import org.bukkit.entity.LivingEntity
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.util.Vector

class GiveAction(section: ConfigurationSection) : AAction(section) {

    private val item: ItemStack

    init {
        item = section.checkItem("item")
    }

    override fun run(livingEntity: LivingEntity, killer: Player?) {
        killer?.apply {
            for (drop in inventory.addItem(item).values) {
                world.dropItem(location, drop).velocity = Vector()
            }
        }
    }
}