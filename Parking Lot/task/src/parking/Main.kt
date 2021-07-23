package parking

data class Car(val number: String, val color: String) {
    fun isColor(color: String) = color.equals(this.color, true)
    fun isNumber(number: String) = number.equals(this.number, true)
}

class CarParking {
    var isCreated = false
    private lateinit var parkMap: Array <Car?>

    private fun isEmpty(): Boolean {
        if (!isCreated) return true
        parkMap.forEachIndexed { _, car ->
            if (car != null) return false
        }
        return true
    }

    private fun getEmpties(): Int {
        parkMap.forEachIndexed { index, car ->
            if (car == null) return index
        }
        return -1
    }
    fun park(car: Car) {
        val spot = getEmpties()
        if (spot >= 0) {
            parkMap[spot] = car
            println("${car.color} car parked in spot ${spot + 1}.")
        } else println("Sorry, the parking lot is full.")
    }

    fun create(spot: Int) {
        isCreated = true
        parkMap = Array(spot) { null }
        println("Created a parking lot with $spot spots.")
    }

    fun leave(spot: Int) {
        val s = spot - 1
        if (parkMap[s] != null) {
            parkMap[s] = null
            println("Spot $spot is free.")
        } else println("There is no car in spot $spot.")
    }

    fun status() {
        if (isEmpty()) println("Parking lot is empty.")
        else parkMap.forEachIndexed { index, car ->
            if (car != null) println("${index + 1} ${car.number} ${car.color}")
        }
    }

    fun regByColor(color: String) {
        var i = 0
        val map: MutableMap<Int, String> = mutableMapOf()
        parkMap.forEachIndexed { _, car ->
            if (car != null)
                if (car.isColor(color))  map[i++] = car.number
            }
        if (map.isNotEmpty()) printMap(map)
        else println("No cars with color $color were found.")
    }

    fun spotByColor(color: String) {
        var i = 0
        val map: MutableMap<Int, String> = mutableMapOf()
        parkMap.forEachIndexed { index, car ->
            if (car != null)
                if (car.isColor(color))  map[i++] = (index + 1).toString()
            }
        if (map.isNotEmpty()) printMap(map)
        else println("No cars with color $color were found.")
    }

    fun spotByReg(number: String) {
        var i = 0
        val map: MutableMap<Int, String> = mutableMapOf()
        parkMap.forEachIndexed { index, car ->
            if (car != null)
                if (car.isNumber(number)) map[i++] = (index + 1).toString()
            }
        if (map.isNotEmpty())  printMap(map)
        else println("No cars with registration number $number were found.")
    }

    private fun printMap(map: MutableMap<Int, String>) {
        map.forEach { (t, u) ->
            if (t == 0) print(u)
            else print(", $u")
        }
        println()
    }
}

fun main() {
    val carParking = CarParking()
    var stop = false
    val notCreated = "Sorry, a parking lot has not been created."
    while (!stop) {
        val input = readLine()!!.split(' ').toTypedArray()
        if (!carParking.isCreated &&
            input[0] in listOf("status", "park", "leave", "reg_by_color", "spot_by_color", "spot_by_reg"))
            println(notCreated)
        else when (input[0]) {
            "create" -> carParking.create(input[1].toInt())
            "status" -> carParking.status()
            "park" -> carParking.park(Car(input[1], input[2]))
            "leave" -> carParking.leave(input[1].toInt())
            "reg_by_color" -> carParking.regByColor(input[1])
            "spot_by_color" -> carParking.spotByColor(input[1])
            "spot_by_reg" -> carParking.spotByReg(input[1])
            "exit" -> stop=true
        }
    }
}
