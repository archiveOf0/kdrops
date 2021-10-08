package com.kingOf0.drop.base

import org.bukkit.entity.LivingEntity

interface IRequirement {

    fun check(livingEntity: LivingEntity): Boolean

}