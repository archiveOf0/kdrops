package com.kingOf0.drop.manager

import com.kingOf0.drop.LOGGER
import com.kingOf0.drop.base.IAction
import com.kingOf0.drop.exception.LoadException
import org.bukkit.configuration.ConfigurationSection
import java.lang.reflect.Constructor
import java.lang.reflect.InvocationTargetException

object ActionManager {

    private val actionRegistry = HashMap<String, Constructor<out IAction>>()

    fun loadAction(section: ConfigurationSection) : IAction? {
        val id = section.name
//        LOGGER.info(" * Loading Action ")
        if (!section.isString("type")) {
            LOGGER.warning(" - $id | There's no 'type' entry at ${section.currentPath}. Action skipped!")
            return null
        }

        val raw = section.getString("type")
        val java = actionRegistry[raw]
        if (java == null) {
            LOGGER.warning(" - $id | Illegal ActionType '$raw' at ${section.currentPath}. Action skipped!")
            return null
        }
        val action: IAction
        try {
            action = java.newInstance(section)
        } catch (e: LoadException) {
            LOGGER.warning(" - $id | ${e.message}")
            return null
        } catch (e: InvocationTargetException) {
            val cause = e.cause
            LOGGER.warning(" - $id | ${if (cause != null) cause.message else e.message}")
            return null
        }
        LOGGER.info(" + $id")
        return action
    }

    fun registerAction(id: String, clazz: Class<out IAction>) {
        actionRegistry[id] = clazz.getDeclaredConstructor(ConfigurationSection::class.java)
    }


}