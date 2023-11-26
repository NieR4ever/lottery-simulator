package love.huhu.love.huhu.lotterysimulator.model

import love.huhu.love.huhu.lotterysimulator.model.BetInfo.Companion.referrersOn
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.dao.id.LongIdTable
import org.jetbrains.exposed.sql.Op
import org.jetbrains.exposed.sql.SqlExpressionBuilder.isNotNull
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.CurrentDateTime
import org.jetbrains.exposed.sql.javatime.datetime

object BetInfos : IntIdTable("bet_info") {
    val qq = long("qq")
    val group = long("group").nullable()
    val text = varchar("text",1000)
    val lotteryType = varchar("lottery_type", 100)
    val betType = varchar("bet_type", 100)
    val betTime = datetime("bet_time").defaultExpression(CurrentDateTime)
    val expired = enumerationByName<ExpiredStatus>("expired",100)

}
class BetInfo(id: EntityID<Int>) : IntEntity(id) {
    companion object: IntEntityClass<BetInfo>(BetInfos)
    var qq by BetInfos.qq
    var group by BetInfos.group
    var text by BetInfos.text
    var lotteryType by BetInfos.lotteryType
    var betType by BetInfos.betType
    var betTime by BetInfos.betTime
    var expired by BetInfos.expired
}

object BetNumberInfos : LongIdTable("bet_number_info") {
    val betInfo = reference("bet_info", BetInfos)
    val number = varchar("number",1000)
}

class BetNumberInfo(id : EntityID<Long>) :LongEntity(id) {
    companion object : LongEntityClass<BetNumberInfo>(BetNumberInfos)
    var betInfo by BetInfo referencedOn BetNumberInfos.betInfo
    var number by BetNumberInfos.number
}

object AwardInfos: IntIdTable("award_info") {
    val betNumber = varchar("bet_number",1000)
    val standardNumber = varchar("standard_number",1000)
    val lotteryType = varchar("lottery_type", 100)
    val grade = varchar("grade",100)
    val amount = varchar("amount",100)
    var betInfo = reference("bet_info", BetInfos)
}
class AwardInfo(id: EntityID<Int>) :IntEntity(id) {
    companion object : IntEntityClass<AwardInfo>(AwardInfos)

    var betNumber by AwardInfos.betNumber
    var standardNumber by AwardInfos.standardNumber
    var lotteryType by AwardInfos.lotteryType
    var grade by AwardInfos.grade
    var amount by AwardInfos.amount
    var betInfo by BetInfo referencedOn AwardInfos.betInfo
}
enum class ExpiredStatus {
    NEXT,CURRENT,EXPIRED
}
enum class LotteryType {
    SHUANG_SE_QIU
}