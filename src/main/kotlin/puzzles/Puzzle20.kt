package puzzles

import java.io.File

private val lines = File("input/puzzle20/input.txt").readLines()

// false: low pulse
typealias Pulse = Triple<String, Boolean, String>

class Module(
    val name: String,
    val type: ModuleType,
    val targets: List<String>,
    var on: Boolean? = null,
    val memory: MutableMap<String, Boolean>? = null
) {
    enum class ModuleType {
        FLIP_FLOP,
        CONJUNCTION,
        BROADCAST
    }

    fun sendPulse(origin: String, pulseType: Boolean): List<Pulse> {
        val outPulses = mutableListOf<Pulse>()
        when (type) {
            ModuleType.FLIP_FLOP -> {
                if (pulseType == false) {
                    on = !on!!
                    targets.forEach {
                        outPulses.add(Pulse(name, on!!, it))
                    }
                }
            }

            ModuleType.CONJUNCTION -> {
                memory!![origin] = pulseType
                if (memory!!.all { it.value == true }) {
                    targets.forEach {
                        outPulses.add(Pulse(name, false, it))
                    }
                } else {
                    targets.forEach {
                        outPulses.add(Pulse(name, true, it))
                    }
                }
            }

            ModuleType.BROADCAST -> {
                targets.forEach {
                    outPulses.add(Pulse(name, pulseType, it))
                }
            }
        }

        return outPulses
    }
}

fun puzzle20(): Int {
    val modules = mutableMapOf<String, Module>()
    lines.forEach {
        val (moduleName, operations) = it.split((" -> "))

        when (moduleName.first()) {
            '&' -> {
                modules[moduleName.drop(1)] =
                    Module(
                        moduleName.drop(1),
                        Module.ModuleType.CONJUNCTION,
                        operations.split(", "),
                        memory = mutableMapOf()
                    )
            }

            '%' -> {
                modules[moduleName.drop(1)] =
                    Module(
                        moduleName.drop(1),
                        Module.ModuleType.FLIP_FLOP,
                        operations.split(", "),
                        false
                    )
            }

            else -> {
                modules[moduleName] =
                    Module(moduleName, Module.ModuleType.BROADCAST, operations.split(", "))
            }
        }
    }

    modules.filter { it.value.type == Module.ModuleType.CONJUNCTION }.forEach { conjunctionModule ->
        modules.filter { it.value.targets.contains(conjunctionModule.key) }
            .forEach { originModule ->
                conjunctionModule.value.memory!![originModule.key] = false
            }
    }

    var highPulses = 0
    var lowPulses = 0

    var pulseQueue = mutableListOf<Pulse>()
    for (i in 0 until 1000) {
        pulseQueue.add(Triple("", false, "broadcaster"))
        while (pulseQueue.isNotEmpty()) {
            val pulse = pulseQueue.first()
            pulseQueue = pulseQueue.drop(1).toMutableList()
            if (pulse.second) highPulses++
            else lowPulses++

            if (modules.contains(pulse.third))
                pulseQueue.addAll(modules[pulse.third]!!.sendPulse(pulse.first, pulse.second))
        }
    }

    return highPulses * lowPulses
}

fun puzzle20dot1(): Int {
    val modules = mutableMapOf<String, Module>()
    lines.forEach {
        val (moduleName, operations) = it.split((" -> "))

        when (moduleName.first()) {
            '&' -> {
                modules[moduleName.drop(1)] =
                    Module(
                        moduleName.drop(1),
                        Module.ModuleType.CONJUNCTION,
                        operations.split(", "),
                        memory = mutableMapOf()
                    )
            }

            '%' -> {
                modules[moduleName.drop(1)] =
                    Module(
                        moduleName.drop(1),
                        Module.ModuleType.FLIP_FLOP,
                        operations.split(", "),
                        false
                    )
            }

            else -> {
                modules[moduleName] =
                    Module(moduleName, Module.ModuleType.BROADCAST, operations.split(", "))
            }
        }
    }

    modules.filter { it.value.type == Module.ModuleType.CONJUNCTION }.forEach { conjunctionModule ->
        modules.filter { it.value.targets.contains(conjunctionModule.key) }
            .forEach { originModule ->
                conjunctionModule.value.memory!![originModule.key] = false
            }
    }

    var i = 0
    outer@ while (true) {
        i++
        var pulseQueue = mutableListOf<Pulse>()
        pulseQueue.add(Triple("", false, "broadcaster"))
        while (pulseQueue.isNotEmpty()) {
            val pulse = pulseQueue.first()
            if (!pulse.second && pulse.third == "rx") break@outer

            pulseQueue = pulseQueue.drop(1).toMutableList()

            if (modules.contains(pulse.third))
                pulseQueue.addAll(modules[pulse.third]!!.sendPulse(pulse.first, pulse.second))
        }
    }

    return i
}