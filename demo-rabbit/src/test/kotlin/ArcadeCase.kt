import com.arcadedb.remote.RemoteDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.io.InputStream
import java.util.concurrent.atomic.AtomicInteger

class ArcadeCase {
    val personCounter: AtomicInteger = AtomicInteger(1)
    val businessCounter: AtomicInteger = AtomicInteger(1)
    val customerCounter: AtomicInteger = AtomicInteger(1)
    val serviceCounter: AtomicInteger = AtomicInteger(1)
    lateinit var db:RemoteDatabase

    @BeforeEach
    fun generateSchema() {
        db = RemoteDatabase("localhost", 2480, "graph", "root", "playwithdata")

        db.begin()
        val schema = readFileAsTextUsingInputStream("arcade/init_schema.sql")
        db.command("sqlscript", schema)
        db.commit()
    }

    @Test
    fun case1() {
        createPersonsAndBusinesses(10)
        createWorksIn(10)
        createPersonsAndBusinesses(100)
        createWorksIn(100)
    }

    @Test
    fun case2() {
        runBlocking {
            createCustomersAndServices(10)
            createPaysFor(10)
            createCustomersAndServices(1000)
            createPaysFor(1000)
        }
    }


    fun createPersonsAndBusinesses(generationSize: Int) {
        db.begin()
        for (i in 1..generationSize) {
            val script = """
            insert into Person(first_name,email,id) values('Ivan$i','${i}@mail.com',${personCounter.getAndIncrement()});
            insert into Business(legal_name,email,id) values('University$i','${i}@uni.com',${businessCounter.getAndIncrement()});
        """.trimIndent()
            db.command("sqlscript", script)
        }
        db.commit()
    }

    fun createWorksIn(generationSize: Int) {
        db.begin()
        for (i in 1..generationSize) {
            db.command(
                "sqlscript", """
            CREATE EDGE worksIn FROM (SELECT FROM Person where id = $i limit 1) TO (SELECT FROM Business where id = $i limit 1)
        """.trimIndent()
            )
        }
        db.commit()
    }

    suspend fun createCustomersAndServices(generationSize: Int) {
        db.begin()
        withContext(Dispatchers.IO) {
            for (i in 1..generationSize) {
                val script = """
            insert into Customer(first_name,email,id) values('John$i','${i}@customer.com',${customerCounter.getAndIncrement()});
            insert into Service(service_name,price,id) values('Service$i','${i * i}',${serviceCounter.getAndIncrement()});
        """.trimIndent()
                db.command("sqlscript", script)
            }
            db.commit()
        }
    }

    suspend fun createPaysFor(generationSize: Int) {
        db.begin()
        withContext(Dispatchers.IO) {
            for (i in 1..generationSize) {
                db.command(
                    "sqlscript", """
            CREATE EDGE paysFor FROM (SELECT FROM Customer where id = $i limit 1) TO (SELECT FROM Service where id = $i limit 1)
        """.trimIndent()
                )
            }
        }
        db.commit()
    }

    @AfterEach
    fun after() {
        println("closing db connection")
        db.close()
    }
}

fun readFileAsTextUsingInputStream(fileName: String): String {
    val inputStream: InputStream = (ArcadeCase::class.java.getResourceAsStream(fileName))
    val inputString = inputStream.bufferedReader().use { it.readText() }
    return inputString
}