import com.arcadedb.remote.RemoteDatabase
import java.io.File
import java.io.InputStream

class ArcadeDbFirstCase

fun main() {
    val db = RemoteDatabase("localhost",2480,"demo_graph","root","playwithdata")
    db.begin()
    val schema=readFileAsTextUsingInputStream("arcade/init_schema.sql")
        db.command("sqlscript",schema)
        db.command("sql","insert into Person(name,age) values(?,?)","Petr","24")
        db.command("sql","insert into Business(legal_name,country) values(?,?)","Arcade","RUS")
    db.commit()
    db.begin()
    db.command("sql", "CREATE EDGE worksIn FROM " +
            "(SELECT FROM Business WHERE legal_name = 'Arcade') TO " +
            "            (SELECT FROM Person WHERE name = 'Petr');")
    db.commit()
    db.close()

}
fun readFileAsTextUsingInputStream(fileName: String): String {
    val inputStream: InputStream = (ArcadeDbFirstCase::class.java.getResourceAsStream(fileName))
    val inputString= inputStream.bufferedReader().use { it.readText() }
    return inputString
}