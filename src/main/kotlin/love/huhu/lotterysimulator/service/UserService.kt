package love.huhu.lotterysimulator.service

import love.huhu.love.huhu.lotterysimulator.model.BetInfo
import love.huhu.love.huhu.lotterysimulator.model.BetNumberInfo
import love.huhu.love.huhu.lotterysimulator.model.BetNumberInfos
import org.jetbrains.exposed.sql.batchInsert

object UserService {
    fun bet(dto: BetDto) {

        var numbers = parseBetNumber(BetNumberDto(dto.text, dto.lotteryType, dto.betType))
        val bet = BetInfo.new {
            qq = dto.qq
            dto.group?.apply { group = this }
            text = dto.text
            lotteryType = dto.lotteryType
            betType = dto.betType
        }
        val betNumberDatas = numbers.map {
            BetNumberData(bet,it.joinToString(" "))
        }

        betNumberDatas.forEach {
            BetNumberInfo.new {
                betInfo = it.betInfo
                number = it.numbers
            }
        }

    }
    private fun parseBetNumber(betNumberDto: BetNumberDto) : List<List<String>>{

        return listOf()
    }
}