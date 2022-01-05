package parking

import java.util.*

fun main() {
    var mainParking = Parking(0)
    while (true) {
        val cmd = readLine()!!.split(" ")
        when (cmd[0]) {
            "create" -> {
                mainParking = Parking(cmd[1].toInt())
                println("Created a parking lot with ${cmd[1]} spots.")
            }
            "leave" -> if (mainParking.check()) mainParking.leave(cmd[1].toInt())
            "park" -> if (mainParking.check()) mainParking.park(Car(cmd[2], cmd[1]))
            "status" -> if (mainParking.check()) mainParking.status()
            "reg_by_color" -> if (mainParking.check()) mainParking.colorCheckReg(cmd[1])
            "spot_by_color" -> if (mainParking.check()) mainParking.colorCheckSpot(cmd[1])
            "spot_by_reg" -> if (mainParking.check()) mainParking.regCheckSpot(cmd[1])
            "exit" -> return
        }
    }
}

class Car(clr: String, regNmbr: String) {
    private val color: String
    private val regNumber: String
    init {
        color = clr
        regNumber = regNmbr
    }
    fun getColor() = color
    fun getReg() = regNumber

    override fun toString(): String {
        return "$regNumber $color"
    }
}

class Parking(size: Int) {
    private val lots = Array<Car?>(size) { null }

    fun park (car: Car) {
        if (!lots.contains(null)) {
            println("Sorry, the parking lot is full.")
        } else {
            for (lot in lots.indices) {
                if (lots[lot] == null) {
                    lots[lot] = car
                    break
                }
            }
            println("${car.getColor()} car parked in spot ${lots.indexOf(car) + 1}.")
        }
    }

    fun leave (lot: Int) {
        if (lots.elementAtOrNull(lot - 1) == null) {
            println("There is no car in spot $lot.")
        } else {
            lots[lot - 1] = null
            println("Spot $lot is free.")
        }
    }

    private fun size() = lots.size

    fun status() {
        var count = 0
        for (lot in lots.indices) {
            if (lots[lot] != null) {
                println("${lot + 1} ${lots[lot]}")
                count++
            }
        }
        if (count == 0) println("Parking lot is empty.")
    }

    fun check():Boolean {
        return if (size() == 0) {
            println("Sorry, a parking lot has not been created.")
            false
        } else true
    }

    fun colorCheckReg(color: String) {
        var list = mutableListOf<String>()
        for (lot in lots.indices) {
            if (lots[lot]?.getColor()?.lowercase(Locale.getDefault()) == color.lowercase(Locale.getDefault())) {
                lots[lot]?.let { list.add(it.getReg()) }
            }
        }
        if (list.isEmpty()) println("No cars with color $color were found.") else {
            println(list.joinToString(", "))
        }
    }

    fun colorCheckSpot(color: String) {
        var list = mutableListOf<Int>()
        for (lot in lots.indices) {
            if (lots[lot]?.getColor()?.lowercase(Locale.getDefault()) == color.lowercase(Locale.getDefault())) {
                list.add(lot + 1)
            }
        }
        if (list.isEmpty()) println("No cars with color $color were found.") else {
            println(list.joinToString(", "))
        }
    }

    fun regCheckSpot(reg: String) {
        var list = mutableListOf<Int>()
        for (lot in lots.indices) {
            if (lots[lot]?.getReg() == reg) {
                list.add(lot + 1)
            }
        }
        if (list.isEmpty()) println("No cars with registration number $reg were found.") else {
            println(list.joinToString(", "))
        }
    }
}
