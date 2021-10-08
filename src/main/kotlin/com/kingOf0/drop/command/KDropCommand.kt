package com.kingOf0.drop.command

import com.cryptomorin.xseries.XItemStack
import com.kingOf0.drop.KDrops
import com.kingOf0.drop.file.ConfigFile
import org.bukkit.Material
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.scheduler.BukkitRunnable
import java.util.*

class KDropCommand(private val main: KDrops, private val config: ConfigFile, private val paste: ConfigFile) : CommandExecutor {

    override fun onCommand(commandSender: CommandSender, p1: Command?, p2: String?, args: Array<out String>): Boolean {
        if (args.isEmpty()) {
            if (!commandSender.hasPermission("kdrops.reload")) {
                config.getString("notEnoughPermission", null)?.let {
                    commandSender.sendMessage(it)
                }
                return true
            }
            main.reload()
            config.getString("reloaded", null)?.let {
                commandSender.sendMessage(it)
            }
            return true
        } else if (args.size == 1) {
            if (args[0] == "paste") {
                if (!commandSender.hasPermission("kdrops.paste")) {
                    config.getString("notEnoughPermission", null)?.let {
                        commandSender.sendMessage(it)
                    }
                    return true
                }
                if (commandSender !is Player) {
                    config.getString("noConsole", null)?.let {
                        commandSender.sendMessage(it)
                    }
                    return true
                }
                val item = commandSender.itemInHand
                if (item == null || item.type == Material.AIR) {
                    config.getString("emptyHand", null)?.let {
                        commandSender.sendMessage(it)
                    }
                    return true
                }
                object : BukkitRunnable() {
                    override fun run() {
                        paste.set("pasted", null)
                        XItemStack.serialize(item, paste.createSection("pasted"))
                        paste.save()
                        config.getString("pasted", null)?.let {
                            commandSender.sendMessage(it)
                        }
                    }
                }.runTaskAsynchronously(main)
                return true
            }
        }
        config.getStringList("help")?.let {
            commandSender.sendMessage(it.toTypedArray())
        }

        return true
    }

}