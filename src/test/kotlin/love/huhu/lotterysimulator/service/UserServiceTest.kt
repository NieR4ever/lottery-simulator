package love.huhu.lotterysimulator.service

import love.huhu.lotterysimulator.model.Database
import love.huhu.lotterysimulator.rule.BetType
import love.huhu.love.huhu.lotterysimulator.LotterySimulator
import love.huhu.love.huhu.lotterysimulator.model.*
import net.mamoe.mirai.utils.MiraiLogger
import net.mamoe.mirai.utils.SimpleLogger
import org.junit.jupiter.api.AfterAll

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeAll
import java.sql.Connection

class UserServiceTest {

    val db = Database.connectDebug()
    @Test
    fun bet() {

        val template = """
            投注【双色球】
            红球：【01 02 03 04 05 06】
            胆码红球；【】
            蓝球：【07 01】
            ps:使用空格分隔号码，例如【01 02】
            pps:非胆拖投注无需填写胆码红球项
        """.trimIndent()
        val betDto = BetDto(template, 123, 123, LotteryType.SHUANG_SE_QIU)
        val expiredStatus = UserService.bet(betDto)
        Database.query {
            val betInfo = BetInfo[1]
            assertEquals(123,betInfo.qq)
            assertEquals(123,betInfo.group)
            assertEquals(BetType.MULTIPLE_BLUE_BALL.name,betInfo.betType)
            assertEquals(ExpiredStatus.CURRENT,betInfo.expired)
        }
    }

    companion object {
        @BeforeAll
        @JvmStatic
        fun setupAll(): Unit {
        }
        @AfterAll
        @JvmStatic
        fun destroy() {
            // 关闭数据库连接
        }
    }
}