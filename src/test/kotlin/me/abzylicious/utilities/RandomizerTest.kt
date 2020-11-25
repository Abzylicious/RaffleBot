package me.abzylicious.utilities

import me.abzylicious.rafflebot.utilities.Randomizer
import org.junit.Test
import kotlin.test.assertEquals

class RandomizerTest {

    private val randomizer = Randomizer<Int>()
    private val testPool = listOf(0, 1, 2, 3, 4, 5)

    @Test
    fun `returns an empty list when the pool is empty`() {
        val result = randomizer.selectRandom(emptyList(), 0)
        assertEquals(emptyList(), result)
    }

    @Test
    fun `returns an empty list when drawCount is smaller than 0`() {
        val result = randomizer.selectRandom(testPool, -1)
        assertEquals(emptyList(), result)
    }

    @Test
    fun `returns an empty list when drawCount is 0`() {
        val result = randomizer.selectRandom(testPool, 0)
        assertEquals(emptyList(), result)
    }

    @Test
    fun `returns a single result list when drawCount is 1`() {
        val result = randomizer.selectRandom(testPool)
        assertEquals(1, result.size)
    }

    @Test
    fun `returns an empty list when drawCount is larger than the pool`() {
        val result = randomizer.selectRandom(testPool, 42)
        assertEquals(emptyList(), result)
    }

    @Test
    fun `returns the entire list when drawCount is equal to the pool size`() {
        val result = randomizer.selectRandom(testPool, testPool.size)
        assertEquals(testPool.size, result.size)
    }
}