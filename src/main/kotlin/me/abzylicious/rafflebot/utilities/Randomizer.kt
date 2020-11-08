package me.abzylicious.rafflebot.utilities

import java.security.SecureRandom

class Randomizer<T>(private val randomizer: SecureRandom = SecureRandom()) {

    fun selectRandom(pool: List<T>, drawCount: Int = 1): List<T> {
        if (pool.isEmpty())
            return emptyList()
        
        if (drawCount <= 0 || drawCount > pool.size)
            return emptyList()

        val indices = getRandomIndices(drawCount, pool.size)
        val result = mutableListOf<T>()
        indices.forEach { result.add(pool[it]) }
        return result
    }

    private fun getRandomIndices(amount: Int, size: Int): List<Int> {
        if (amount <= 0 || amount > size)
            return emptyList()

        val result = mutableListOf<Int>()
        for (x in 1..amount) {
            var nextIndex: Int
            do {
                nextIndex = randomizer.nextInt(size)
            } while (result.contains(nextIndex))

            result.add(nextIndex)
        }

        return result
    }
}