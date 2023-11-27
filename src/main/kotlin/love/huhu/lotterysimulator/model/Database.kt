package love.huhu.lotterysimulator.model

import love.huhu.lotterysimulator.LotteryConfig
import love.huhu.love.huhu.lotterysimulator.LotterySimulator
import love.huhu.love.huhu.lotterysimulator.model.AwardInfos
import love.huhu.love.huhu.lotterysimulator.model.BetInfos
import love.huhu.love.huhu.lotterysimulator.model.BetNumberInfos
import net.mamoe.mirai.utils.error
import net.mamoe.mirai.utils.verbose
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.statements.StatementContext
import org.jetbrains.exposed.sql.statements.expandArgs
import org.jetbrains.exposed.sql.transactions.transaction

object Database {

    private lateinit var db: Database

    private var connectionStatus: ConnectionStatus = ConnectionStatus.DISCONNECTED
    fun connect() {
        db = Database.connect(
            "jdbc:sqlite:${LotterySimulator.dataFolder}/${LotteryConfig.databaseName}",
            "org.sqlite.JDBC"
        )
    }

    sealed class ConnectionStatus {
        object CONNECTED : ConnectionStatus()
        object DISCONNECTED : ConnectionStatus()
    }
    fun <T> query(block: (Transaction) -> T): T? = if (connectionStatus == ConnectionStatus.DISCONNECTED) {
        LotterySimulator.logger.error { "数据库未连接" }
        null
    } else transaction(db) { block(this) }
    private fun initDatabase() {
        query {
            it.addLogger(object : SqlLogger {
                override fun log(context: StatementContext, transaction: Transaction) {
                    LotterySimulator.logger.verbose { "sql: ${context.expandArgs(transaction)}" }
                }

            })
            //初始化两张表
            SchemaUtils.create(BetInfos,AwardInfos,BetNumberInfos)
        }
    }
}