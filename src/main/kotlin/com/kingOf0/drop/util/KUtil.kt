package com.kingOf0.drop.util

import com.cryptomorin.xseries.XItemStack
import com.kingOf0.drop.LOGGER
import com.kingOf0.drop.exception.InvalidEntryException
import com.kingOf0.drop.exception.MissingEntryException
import org.bukkit.Material
import org.bukkit.configuration.ConfigurationSection
import org.bukkit.entity.EntityType
import org.bukkit.inventory.ItemStack

fun ConfigurationSection.checkDouble(name: String) : Double {
    if (isSet(name)) {
        if (!isDouble(name)) throw InvalidEntryException("Entry '${currentPath}.$name' isn't double!")
        return getDouble(name)
    } else throw MissingEntryException("Entry '${currentPath}.$name' is missing!")
}

fun ConfigurationSection.checkString(name: String) : String {
    if (isSet(name)) {
        if (!isString(name)) throw InvalidEntryException("Entry '${currentPath}.$name' isn't string!")
        return getString(name)!!
    } else throw MissingEntryException("Entry '${currentPath}.$name' is missing!")
}

fun ConfigurationSection.checkInt(name: String) : Int {
    if (isSet(name)) {
        if (!isInt(name)) throw InvalidEntryException("Entry '${currentPath}.$name' isn't int!")
        return getInt(name)
    } else throw MissingEntryException("Entry '${currentPath}.$name' is missing!")
}

fun ConfigurationSection.checkBoolean(name: String) : Boolean {
    if (isSet(name)) {
        if (!isBoolean(name)) throw InvalidEntryException("Entry '${currentPath}.$name' isn't int!")
        return getBoolean(name)
    } else throw MissingEntryException("Entry '${currentPath}.$name' is missing!")
}

fun ConfigurationSection.checkEntityType(name: String) : EntityType {
    if (isSet(name)) {
        val type: EntityType
        try {
            val raw = getString(name)
            type = EntityType.valueOf(raw)
        } catch (e: IllegalArgumentException) {
            throw InvalidEntryException("Entry '${currentPath}.$name' isn't an EntityType")
        }
        return type
    } else throw MissingEntryException("Entry '${currentPath}.$name' is missing!")
}

fun ConfigurationSection.checkMaterial(name: String) : Material {
    if (isSet(name)) {
        val type: Material
        try {
            val raw = getString(name)
            type = Material.valueOf(raw)
        } catch (e: IllegalArgumentException) {
            throw InvalidEntryException("Entry '${currentPath}.$name' isn't an Material")
        }
        return type
    } else throw MissingEntryException("Entry '${currentPath}.$name' is missing!")
}

fun ConfigurationSection.checkItem(name: String) : ItemStack {
    if (isSet(name)) {
        if (!isConfigurationSection(name)) throw InvalidEntryException("Entry '${currentPath}.$name' isn't a XItemStack Configuration Section!")
        return XItemStack.deserialize(getConfigurationSection(name))
            ?: throw InvalidEntryException("Entry '$currentPath.$name' isn't a XItemStack!")
    } else throw MissingEntryException("Entry '${currentPath}.$name' is missing!")
}

fun ConfigurationSection.getItemList() : ArrayList<ItemStack> {
    val list = ArrayList<ItemStack>()
    for (key in getKeys(false)) {
        val temp = XItemStack.deserialize(getConfigurationSection(key))
        if (temp == null) {
            LOGGER.warning("$key has invalid configuration! '$key' isn't a valid XItemStack at '$currentPath'.")
            continue
        }
        list += temp
    }
    return list
}

/*

val list = ArrayList<ItemStack>()

    val drops = ArrayList<ItemStack>()
    if (dropSection.isConfigurationSection("drops")) {
        dropSection.getConfigurationSection("drops").let {
            for (key in it.getKeys(false)) {
                if (it.isConfigurationSection(key)) {
                    val temp = XItemStack.deserialize(it.getConfigurationSection(key))
                    if (temp == null) {
                        logger.warning("$key has invalid configuration! 'item' isn't a valid XItemStack at $index.")
                        continue
                    }
                    drops += temp
                } else {
                    logger.warning("$key has invalid configuration! There is no 'item' section at $index.")
                    continue
                }
            }
        }
    }


    return list
 */
