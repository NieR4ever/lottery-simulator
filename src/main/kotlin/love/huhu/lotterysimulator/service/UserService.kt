package love.huhu.lotterysimulator.service

import love.huhu.lotterysimulator.model.Database
import love.huhu.lotterysimulator.rule.ShuangSeQiu
import love.huhu.love.huhu.lotterysimulator.model.*
import org.jetbrains.exposed.sql.batchInsert

object UserService {
    fun bet(dto: BetDto): ExpiredStatus {

        val expiredStatus= checkExpiredTime(dto.lotteryType)
        val parseBetInfo = parseBetInfo(BetNumberDto(dto.text, dto.lotteryType))
        val type = parseBetInfo.getBetType()
        var numbers = parseBetInfo.getAllBetNumber(type)
        Database.query {
            val bet = BetInfo.new {
                qq = dto.qq
                dto.group?.apply { group = this }
                text = dto.text
                lotteryType = dto.lotteryType
                betType = type.name
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
        return expiredStatus;
    }

    private fun checkExpiredTime(lotteryType: LotteryType) : ExpiredStatus{
        return when(lotteryType) {
            LotteryType.SHUANG_SE_QIU -> ShuangSeQiu.checkLockBetExpired()
        }
    }

    private fun parseBetInfo(betNumberDto: BetNumberDto) : ShuangSeQiu{

        return when(betNumberDto.lotteryType) {
            LotteryType.SHUANG_SE_QIU -> ShuangSeQiu.parse(betNumberDto.text)
        }
    }
}