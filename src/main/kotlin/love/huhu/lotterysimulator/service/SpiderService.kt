package love.huhu.lotterysimulator.service

import io.github.bonigarcia.wdm.WebDriverManager
import love.huhu.lotterysimulator.LotteryConfig
import org.openqa.selenium.WebDriver

object SpiderService {
    val driver : WebDriver = when(LotteryConfig.browser) {
        BrowserType.CHROME -> WebDriverManager.chromedriver().create()
        BrowserType.FIREFOX -> WebDriverManager.firefoxdriver().create()
        BrowserType.EDGE -> WebDriverManager.edgedriver().create()
    }


}
enum class BrowserType {
    CHROME,FIREFOX,EDGE
}