package love.huhu.lotterysimulator.rule

import love.huhu.lotterysimulator.LotteryConfig
import love.huhu.lotterysimulator.service.SpiderService
import love.huhu.love.huhu.lotterysimulator.model.ExpiredStatus
import org.openqa.selenium.By
import java.time.LocalDateTime

class ShuangSeQiu(private var redBalls1: List<String>, private var redBalls2: List<String>, private var blueBalls: List<String>) {
    private val redBallNumbers = (1..33).map { String.format("%02d",it) }
    private val blueBallNumbers = (1..16).map { String.format("%02d",it) }
    init {
        blueBalls = blueBalls.distinct()
        if (redBalls1.any { redBalls2.contains(it) }) {
            throw DuplicateElementsException()
        }
        redBalls1 = redBalls1.distinct()
        redBalls2 = redBalls2.distinct()
        checkNumberRange();
    }
    companion object {

        val kjWeekday = listOf(0,2,4) //周二，四，日开奖
        val kjTime = 22//晚上十点开奖
        @JvmStatic
        fun checkLockBetExpired() : ExpiredStatus {
            val current = LocalDateTime.now().hour
            if (kjTime - current <= LotteryConfig.lockBetTime) {
                return ExpiredStatus.NEXT
            }
            return ExpiredStatus.CURRENT
        }
    }
    fun getBetType(): TouZhuType {
        if (redBalls1.size < 6 || blueBalls.isEmpty()) {
            return TouZhuType.UNKNOWN
        }
        if (redBalls1.size == 6 && blueBalls.size == 1) {
            //单式投注
            return TouZhuType.SINGLE
        }
        if (redBalls1.size > 6 && blueBalls.size == 1) {
            //复式红球
            return TouZhuType.MULTIPLE_RED_BALL
        }
        if (redBalls1.size == 6 && blueBalls.size > 1) {
            //复式蓝球
            return TouZhuType.MULTIPLE_BLUE_BALL
        }

        if (redBalls1.size > 6 && blueBalls.size > 1) {
            //全复式
            return TouZhuType.COMBO_ALL
        }
        if (redBalls1.isEmpty() || redBalls1.size > 5 || redBalls1.size + redBalls2.size < 7 || blueBalls.isEmpty()) {
            return TouZhuType.UNKNOWN
        }
        return if (blueBalls.size == 1) {
            //单式胆拖
            TouZhuType.SINGLE_COMBO
        } else {
            //复式胆拖
            TouZhuType.MULTIPLE_COMBO
        }
    }

    private fun checkNumberRange() {
        if (!redBallNumbers.containsAll(redBalls1+redBalls2)) {
            throw OutOfNumberRangeException()
        }
        if (!blueBallNumbers.containsAll(blueBalls)) {
            throw OutOfNumberRangeException()
        }

    }

    fun getAllBetNumber(type: TouZhuType): List<List<String>> {
        val arrayList = ArrayList<List<String>>()
        when (type) {
            TouZhuType.SINGLE -> {
                arrayList.add(redBalls1 + blueBalls)
            }

            TouZhuType.MULTIPLE_RED_BALL -> {
                redBalls1.forEach() { item ->
                    val list = redBalls1.filter { it != item }
                    arrayList.add(list + blueBalls)
                }
            }

            TouZhuType.MULTIPLE_BLUE_BALL -> {
                blueBalls.forEach() {
                    arrayList.add(redBalls1 + it)
                }
            }

            TouZhuType.COMBO_ALL ->
                redBalls1.forEach() { item ->
                    val list = redBalls1.filter { it != item }
                    blueBalls.forEach() {
                        arrayList.add(list + it)
                    }
                }

            TouZhuType.SINGLE_COMBO -> {
                val danTuoSize = 6 - redBalls1.size
                val lists = combinations(redBalls2, danTuoSize)
                lists.forEach() {
                    arrayList.add(redBalls1 + it + blueBalls)
                }
            }

            TouZhuType.MULTIPLE_COMBO -> {
                val danTuoSize = 6 - redBalls1.size
                val lists = combinations(redBalls2, danTuoSize)
                lists.forEach() { list ->
                    blueBalls.forEach() {
                        arrayList.add(redBalls1 + list + it)
                    }
                }

            }

            TouZhuType.UNKNOWN -> {}
        }
        return arrayList
    }

    fun getAwardLevel(touZhu: List<String>, standard: List<String>): AwardLevel {
        if (touZhu.sorted() == standard.sorted()) {
            return AwardLevel.FIRST // 一等奖
        }

        val touZhuRedBalls = touZhu.subList(0, touZhu.size - 1)
        val standardRedBalls = standard.subList(0, standard.size - 1)

        if (touZhuRedBalls.sorted() == standardRedBalls.sorted()) {
            return AwardLevel.SECOND // 二等奖
        }

        var redMatchCount = 0
        for (ball in touZhuRedBalls) {
            if (standardRedBalls.contains(ball)) {
                redMatchCount++
            }
        }

        if (redMatchCount == 5 && touZhu[touZhu.size - 1] == standard[standard.size - 1]) {
            return AwardLevel.THIRD // 三等奖
        }

        if (redMatchCount == 5 || (redMatchCount == 4 && touZhu[touZhu.size - 1] == standard[standard.size - 1])) {
            return AwardLevel.FOURTH // 四等奖
        }

        if (redMatchCount == 4 || (redMatchCount == 3 && touZhu[touZhu.size - 1] == standard[standard.size - 1])) {
            return AwardLevel.FIFTH // 五等奖
        }

        if (touZhu[touZhu.size - 1] == standard[standard.size - 1]) {
            return AwardLevel.SIXTH // 六等奖
        }

        return AwardLevel.NONE // 没有中奖
    }
    data class Drawn(val redBalls: List<String>, val blueBalls: List<String>, val kjTime: String)
    val url = "https://www.zhcw.com/kjxx/ssq/kjxq"
    fun fetch() :Drawn{
        val driver = SpiderService.driver
        driver.get(url)
        val kjTime = driver.findElement(By.cssSelector(".sj span")).text
        val kjqHs = driver.findElements(By.cssSelector(".kjqH")).map { it.text }
        val kjqLs = driver.findElements(By.cssSelector(".kjqL")).map { it.text }
        return Drawn(kjqHs,kjqLs,kjTime)
    }
}

class OutOfNumberRangeException : Throwable()
class DuplicateElementsException : Throwable()

fun <T> combinations(list: List<T>, n: Int): List<List<T>> {
    if (n == 0 || list.size < n) {
        return listOf(emptyList())
    }
    if (n == 1) {
        return list.map { listOf(it) }
    }
    val result = mutableListOf<List<T>>()
    for ((index, element) in list.withIndex()) {
        val subList = list.subList(index + 1, list.size)
        val subCombinations = combinations(subList, n - 1)
        for (combination in subCombinations) {
            result.add(listOf(element) + combination)
        }
    }
    return result
}

enum class TouZhuType {
    SINGLE, MULTIPLE_RED_BALL, MULTIPLE_BLUE_BALL, COMBO_ALL, UNKNOWN, SINGLE_COMBO, MULTIPLE_COMBO
}

enum class AwardLevel {
    FIRST, SECOND, THIRD, FOURTH, FIFTH, SIXTH, NONE
}