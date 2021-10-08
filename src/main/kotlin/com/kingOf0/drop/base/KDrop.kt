package com.kingOf0.drop.base

import org.bukkit.entity.LivingEntity
import org.bukkit.entity.Player

class KDrop(
    private val requirements: List<IRequirement>,
    private val actions: List<IAction>,
    ) {

    fun check(livingEntity: LivingEntity) : Boolean {
        for (requirement in requirements) {
            if (!requirement.check(livingEntity))
                return false
        }
        return true
    }

    fun drop(livingEntity: LivingEntity, killer: Player?) {
        actions.forEach { action ->
            action.run(livingEntity, killer)
        }
    }

}
