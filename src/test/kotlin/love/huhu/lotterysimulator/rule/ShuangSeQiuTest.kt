package love.huhu.lotterysimulator.rule

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import kotlin.reflect.KClass
import kotlin.test.assertFailsWith

class ShuangSeQiuTest {
    @Test
    fun `check distinct red ball`() {
        var a = listOf("01", "02", "03")
        var b = listOf("03", "05", "06")
        var c = listOf("07")
        assertFailsWith<DuplicateElementsException> { ShuangSeQiu(a,b,c) }
    }
    @Test
    fun `check number range`() {
        var a = listOf("01", "02", "03")
        var b = listOf("04", "05", "06", "34")
        var c = listOf("07")
        var d = listOf("17")
        assertFailsWith<OutOfNumberRangeException> { ShuangSeQiu(a,b,c) }
        assertFailsWith<OutOfNumberRangeException> { ShuangSeQiu(a, emptyList(),d) }
    }
}