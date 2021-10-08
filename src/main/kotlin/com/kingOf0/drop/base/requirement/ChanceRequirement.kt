package com.kingOf0.drop.base.requirement

import com.kingOf0.drop.util.checkDouble
import org.bukkit.configuration.ConfigurationSection
import org.bukkit.entity.LivingEntity
import kotlin.random.Random

class ChanceRequirement(section: ConfigurationSection) : ARequirement(section) {

    private val chance: Double

    init {
        chance = section.checkDouble("chance")
    }

    override fun check(livingEntity: LivingEntity): Boolean {
        return Random.nextDouble(100.0) < chance
    }


}