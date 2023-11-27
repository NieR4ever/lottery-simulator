package love.huhu.lotterysimulator.service

import love.huhu.lotterysimulator.rule.ShuangSeQiu
import love.huhu.love.huhu.lotterysimulator.model.*
import org.jetbrains.exposed.sql.batchInsert

object UserService {
    fun bet(dto: BetDto) {

        val expiredStatus= checkExpiredTime(dto.lotteryType)
        var numbers = parseBetNumber(BetNumberDto(dto.text, dto.lotteryType, dto.betType))
        val bet = BetInfo.new {
            qq = dto.qq
            dto.group?.apply { group = this }
            text = dto.text
            lotteryType = dto.lotteryType
            betType = dto.betType
            expired = expiredStatus
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

    private fun checkExpiredTime(lotteryType: LotteryType) : ExpiredStatus{
        return when(lotteryType) {
            LotteryType.SHUANG_SE_QIU -> ShuangSeQiu.checkLockBetExpired()
        }
    }

    private fun parseBetNumber(betNumberDto: BetNumberDto) : List<List<String>>{

        return listOf()
    }
}