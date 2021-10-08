package com.kingOf0.drop.listener

import com.kingOf0.drop.manager.DropManager.kdrops
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDeathEvent

class EntityListener : Listener {

    @EventHandler
    fun onDeath(event: EntityDeathEvent) {
        for (drop in kdrops) {
            if (drop.check(event.entity)) {
                drop.drop(event.entity, event.entity.killer)
            }
        }
    }

}