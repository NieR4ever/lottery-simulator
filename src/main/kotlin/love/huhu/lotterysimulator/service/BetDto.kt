package love.huhu.lotterysimulator.service

import love.huhu.love.huhu.lotterysimulator.model.BetInfo
import love.huhu.love.huhu.lotterysimulator.model.LotteryType

data class BetDto(val text: String, val qq : Long, val group :Long?,val lotteryType :LotteryType,val betType:String)
data class BetNumberDto(val text: String, val lotteryType :LotteryType,val betType:String)

data class BetNumberData(val betInfo: BetInfo,val numbers : String)
