package com.kingOf0.drop.base.requirement

import com.kingOf0.drop.util.checkEntityType
import org.bukkit.configuration.ConfigurationSection
import org.bukkit.entity.EntityType
import org.bukkit.entity.LivingEntity

class TypeRequirement(section: ConfigurationSection) : ARequirement(section) {

    private val type: EntityType

    init {
        type = section.checkEntityType("entityType")
    }

    override fun check(livingEntity: LivingEntity): Boolean {
        return livingEntity.type == type
    }


}