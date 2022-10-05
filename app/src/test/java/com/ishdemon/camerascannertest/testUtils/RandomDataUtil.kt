package com.ishdemon.camerascannertest.testUtils

import java.util.UUID
import kotlin.random.Random

object RandomDataUtil {

    private val random = Random

    fun randomString(): String {
        return UUID.randomUUID().toString()
    }

    fun randomStringTextNumericCharacters(): String {
        return randomString().replace("-","")
    }

    fun randomLong(max: Long = Long.MAX_VALUE - 1, min: Long = Long.MIN_VALUE): Long {
        return random.nextLong(min, max + 1)
    }

    fun randomInt(max: Int = Int.MAX_VALUE - 1, min: Int = Int.MIN_VALUE): Int {
        return random.nextInt(min, max + 1)
    }

    fun randomPositiveInt(): Int {
        return randomInt(min = 0)
    }

    fun randomBoolean(): Boolean {
        return random.nextBoolean()
    }

    fun <T> randomSizedList(generateObject: (index: Long) -> T, max: Long = 10): List<T> {
        return (1..randomLong(min = 0, max = max)).map { generateObject(it) }
    }

    fun randomMapStringToString(): Map<String, String> {
        val map = mutableMapOf<String, String>()
        (1..randomInt(min = 1, max = 15)).map {
            map.put(randomStringTextNumericCharacters(), randomStringTextNumericCharacters())
        }
        return map
    }

}