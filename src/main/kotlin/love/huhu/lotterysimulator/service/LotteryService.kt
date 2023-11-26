package love.huhu.lotterysimulator.service

import love.huhu.love.huhu.lotterysimulator.model.*
import org.jetbrains.exposed.sql.and

class LotteryService {
    fun notify(type: LotteryType) {
        val betInfoQuery =
            BetInfo.find { BetInfos.expired eq ExpiredStatus.CURRENT and (BetInfos.lotteryType eq type) }
        val betInfos = betInfoQuery.toList()
        betInfoQuery.forEach {
            it.expired = ExpiredStatus.EXPIRED
        }

        val betNumberInfos = BetNumberInfo.find { BetNumberInfos.betInfo inList betInfos.map { it.id } }.toList()
        //根据规则和彩票类型检查这下投注号码是否中奖
    }
}