package love.huhu.lotterysimulator

import io.github.bonigarcia.wdm.WebDriverManager

import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriver;
class WebDriverTest {
 private lateinit var driver : WebDriver
    @BeforeEach
    fun setup() {
        driver = ChromeDriver();
    }

    @AfterEach
    fun teardown() {
        driver.quit();
    }

    @Test
    fun test() {
        // Your test logic here
        driver.get("https://www.zhcw.com/kjxx/ssq/kjxq");
        val kjTime = driver.findElement(By.cssSelector(".sj span")).text
        val kjqHs = driver.findElements(By.cssSelector(".kjqH")).map { it.text }
        val kjqLs = driver.findElements(By.cssSelector(".kjqL")).map { it.text }

        println(kjTime)
        println(kjqHs)
        println(kjqLs)
    }

    companion object {
        @JvmStatic
        @BeforeAll
        fun setupAll(): Unit {
            WebDriverManager.chromedriver().setup();
        }
    }
}