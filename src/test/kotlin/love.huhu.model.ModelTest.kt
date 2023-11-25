import love.huhu.love.huhu.lotterysimulator.model.*
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import java.time.LocalDateTime
import java.util.Date

class ModelTest {
    val db : Database = Database.connect("jdbc:sqlite:data.db", "org.sqlite.JDBC")


    @Test
    fun `test create db`() {
        transaction {
            SchemaUtils.create(BetInfos, AwardInfos, BetNumberInfos)
            val bet = BetInfo.new {
                qq = 123
                text = "123"
                lotteryType = "123"
                betType = "123"
                betTime = LocalDateTime.now()
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
            println(AwardInfo[award.id].betInfo)
        }
    }
}