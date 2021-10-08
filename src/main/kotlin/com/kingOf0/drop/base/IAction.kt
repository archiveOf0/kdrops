package com.kingOf0.drop.base

import org.bukkit.entity.LivingEntity
import org.bukkit.entity.Player

interface IAction {

    fun run(livingEntity: LivingEntity, killer: Player?)

}