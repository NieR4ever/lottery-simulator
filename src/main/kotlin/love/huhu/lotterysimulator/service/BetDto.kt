package love.huhu.lotterysimulator.service

import love.huhu.love.huhu.lotterysimulator.model.BetInfo

data class BetDto(val text: String, val qq : Long, val group :Long?,val lotteryType :String,val betType:String)
data class BetNumberDto(val text: String, val lotteryType :String,val betType:String)

data class BetNumberData(val betInfo: BetInfo,val numbers : String)
