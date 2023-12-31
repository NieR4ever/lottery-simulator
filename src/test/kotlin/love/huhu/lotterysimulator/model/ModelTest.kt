package love.huhu.lotterysimulator.model

import love.huhu.love.huhu.lotterysimulator.model.*
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import org.junit.jupiter.api.Test
import java.time.LocalDateTime
import kotlin.test.BeforeTest

class ModelTest {

    @BeforeTest
    fun before() {
        val db : Database = Database.connect("jdbc:sqlite:file::memory:?cache=shared", "org.sqlite.JDBC")
    }
    @Test
    fun `test create db`() {
        transaction {

            SchemaUtils.create(BetInfos, AwardInfos, BetNumberInfos)
        }
        transaction {
            val bet = BetInfo.new {
                qq = 123
                text = "123"
                lotteryType = LotteryType.SHUANG_SE_QIU
                betType = "123"
                betTime = LocalDateTime.now()
                expired = ExpiredStatus.CURRENT
            }

            val award = AwardInfo.new {
                betNumber = "123"
                standardNumber = "123"
                betInfo = bet
                lotteryType = "123"
                grade = "123"
                amount = "123"
            }
            BetNumberInfo.new {
                betInfo = bet
                number = "123"
            }
            println(AwardInfo[award.id].betInfo.expired)
        }
    }
}