package com.kingOf0.drop.base.requirement

import com.kingOf0.drop.util.checkString
import org.bukkit.configuration.ConfigurationSection
import org.bukkit.entity.LivingEntity

class NameRequirement(section: ConfigurationSection) : ARequirement(section) {

    private val name: String

    init {
        name = section.checkString("name")
    }

    override fun check(livingEntity: LivingEntity): Boolean {
        return livingEntity.customName == name
    }


}