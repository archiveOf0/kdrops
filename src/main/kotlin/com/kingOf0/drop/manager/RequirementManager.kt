package com.kingOf0.drop.manager

import com.kingOf0.drop.LOGGER
import com.kingOf0.drop.base.IRequirement
import com.kingOf0.drop.exception.LoadException
import org.bukkit.configuration.ConfigurationSection
import java.lang.reflect.Constructor
import java.lang.reflect.InvocationTargetException

object RequirementManager {

    private val requirementRegistry = HashMap<String, Constructor<out IRequirement>>()

    fun loadRequirement(section: ConfigurationSection) : IRequirement? {
        val id = section.name
//        LOGGER.info(" * Loading requirement ")
        if (!section.isString("type")) {
            LOGGER.warning(" - $id | There's no 'type' entry at ${section.currentPath}. Requirement skipped!")
            return null
        }

        val raw = section.getString("type")
        val java = requirementRegistry[raw]
        if (java == null) {
            LOGGER.warning(" - $id | Illegal RequirementType '$raw' at ${section.currentPath}. Requirement skipped!")
            return null
        }
        val requirement: IRequirement
        try {
            requirement = java.newInstance(section)
        } catch (e: LoadException) {
            LOGGER.warning(" - $id | ${e.message}")
            return null
        } catch (e: InvocationTargetException) {
            val cause = e.cause
            LOGGER.warning(" - $id | ${if (cause != null) cause.message else e.message}")
            return null
        }
        LOGGER.info(" + $id")
        return requirement
    }

    fun registerRequirement(id: String, clazz: Class<out IRequirement>) {
        requirementRegistry[id] = clazz.getDeclaredConstructor(ConfigurationSection::class.java)
    }


}