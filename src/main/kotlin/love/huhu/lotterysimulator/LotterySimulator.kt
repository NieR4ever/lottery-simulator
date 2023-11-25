package love.huhu.love.huhu.lotterysimulator

import net.mamoe.mirai.console.plugin.jvm.JvmPluginDescription
import net.mamoe.mirai.console.plugin.jvm.KotlinPlugin
import net.mamoe.mirai.utils.info

object LotterySimulator : KotlinPlugin(
        JvmPluginDescription(
                id = "love.huhu.lottery-simulator",
                name = "Lottery Simulator",
                version = "0.1.0",
        ) {

            author("Nier4ever")
        }
) {
    override fun onEnable() {
        logger.info { "Plugin loaded" }
    }
}