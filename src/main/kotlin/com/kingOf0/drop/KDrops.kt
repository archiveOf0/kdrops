package com.kingOf0.drop

import com.kingOf0.drop.base.IAction
import com.kingOf0.drop.base.IRequirement
import com.kingOf0.drop.base.KDrop
import com.kingOf0.drop.base.action.CommandAction
import com.kingOf0.drop.base.action.DropAction
import com.kingOf0.drop.base.action.GiveAction
import com.kingOf0.drop.base.requirement.ChanceRequirement
import com.kingOf0.drop.base.requirement.NameRequirement
import com.kingOf0.drop.base.requirement.TypeRequirement
import com.kingOf0.drop.command.KDropCommand
import com.kingOf0.drop.file.ConfigFile
import com.kingOf0.drop.listener.EntityListener
import com.kingOf0.drop.manager.ActionManager.loadAction
import com.kingOf0.drop.manager.ActionManager.registerAction
import com.kingOf0.drop.manager.DropManager.kdrops
import com.kingOf0.drop.manager.RequirementManager.loadRequirement
import com.kingOf0.drop.manager.RequirementManager.registerRequirement
import org.bukkit.plugin.java.JavaPlugin
import java.util.logging.Logger

lateinit var LOGGER: Logger
class KDrops : JavaPlugin() {

    private lateinit var configFile: ConfigFile
    private lateinit var dropsFile: ConfigFile

    override fun onLoad() {
        LOGGER = logger

        registerAction("DROP", DropAction::class.java)
        registerAction("GIVE", GiveAction::class.java)
        registerAction("COMMAND", CommandAction::class.java)

        registerRequirement("CHANCE", ChanceRequirement::class.java)
        registerRequirement("NAME", NameRequirement::class.java)
        registerRequirement("TYPE", TypeRequirement::class.java)
    }

    override fun onEnable() {
        logger.info("")
        logger.info(" Thank you for using KDrops! For communication discord: kingOf0!#4055")
        logger.info("")

        configFile = ConfigFile("config", this)
        dropsFile = ConfigFile("drops", this)
        val pasteFile = ConfigFile("paste", this)

        load()

        server.pluginManager.registerEvents(EntityListener(), this)
        getCommand("kdrops")?.setExecutor(KDropCommand(this, configFile, pasteFile))
    }

    fun load() {
        logger.info(" * Loading drops...")
        logger.info("")
        for (dropId in dropsFile.getKeys(false)) {
            val dropSection = dropsFile.getConfigurationSection(dropId)

            val requirements = ArrayList<IRequirement>()
            if (dropSection.isConfigurationSection("requirements")) {
                logger.info(" * Loading requirements...")
                val requirementsSection = dropSection.getConfigurationSection("requirements")
                for (requirementId in requirementsSection.getKeys(false)) {
                    loadRequirement(requirementsSection.getConfigurationSection(requirementId))?.let {
                        requirements += it
                    }
                }
            }

            val actions = ArrayList<IAction>()
            if (dropSection.isConfigurationSection("actions")) {
                logger.info(" * Loading actions...")
                val actionsSection = dropSection.getConfigurationSection("actions")
                for (actionId in actionsSection.getKeys(false)) {
                    loadAction(actionsSection.getConfigurationSection(actionId))?.let {
                        actions += it
                    }
                }
            }

            kdrops.add(KDrop(requirements, actions))
            logger.info("")
            logger.info(" + Loaded Drop | $dropId")
        }
    }

    fun reload() {
        configFile.load()
        dropsFile.load()
        load()
    }

}