package love.huhu.lotterysimulator

import love.huhu.lotterysimulator.service.BrowserType
import net.mamoe.mirai.console.data.AutoSavePluginConfig
import net.mamoe.mirai.console.data.ValueDescription
import net.mamoe.mirai.console.data.value
import org.openqa.selenium.remote.Browser
import java.util.concurrent.TimeUnit


object LotteryConfig : AutoSavePluginConfig("lottery-config") {
    @ValueDescription("""
        锁定开奖时间前[lockTouZhuTime]小时,该时间段不能投注
    """)
    val lockTouZhuTime by value(TimeUnit.HOURS.toHours(1))
    @ValueDescription("""
        数据库文件名称
    """)
    val databaseName by value("lottery.db")

    @ValueDescription("""
        浏览器驱动，可用值（chrome,firefox,edge）
    """)
    val browser by value(BrowserType.CHROME)
}