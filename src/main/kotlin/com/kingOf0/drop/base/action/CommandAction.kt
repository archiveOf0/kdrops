package com.kingOf0.drop.base.action

import com.kingOf0.drop.util.checkString
import org.bukkit.Bukkit
import org.bukkit.configuration.ConfigurationSection
import org.bukkit.entity.LivingEntity
import org.bukkit.entity.Player

class CommandAction(section: ConfigurationSection) : AAction(section) {

    private val command: String

    init {
        command = section.checkString("command")
    }

    override fun run(livingEntity: LivingEntity, killer: Player?) {
        var dispatch = command

        killer?.let {
            dispatch = command.replace("%uuid%", it.uniqueId.toString())
                .replace("%player%", it.name)
        }
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), dispatch)
    }


}