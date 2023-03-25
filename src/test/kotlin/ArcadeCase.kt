import com.arcadedb.remote.RemoteDatabase
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class ArcadeCase {
    val db = RemoteDatabase("localhost",2480,"demo_graph","root","playwithdata")
    @BeforeEach
    fun generateSchema(){
        db.begin()
        val schema=readFileAsTextUsingInputStream("arcade/init_schema.sql")
        db.command("sqlscript",schema)
        db.commit()
    }
    @Test
    fun case1(){
        createPersonsAndBusinesses(10)
        createWorksIn(10)
    }
    @Test
    fun case2(){
        createPersonsAndBusinesses(1000)
        createWorksIn(1000)
    }


    fun createPersonsAndBusinesses(generationSize:Int){
        db.begin()
        for (i in 1..generationSize){
            val script="""
            insert into Person(first_name,email,id) values('Ivan$i','${i}@mail.com',$i);
            insert into Business(legal_name,email,id) values('University$i','${i}@uni.com',$i);
        """.trimIndent()
            db.command("sqlscript",script)
        }
        db.commit()
    }
    fun createWorksIn(generationSize:Int){
        db.begin()
        for (i in 1..generationSize){
            db.command("sqlscript", """
            CREATE EDGE worksIn FROM (SELECT FROM Business where id = $i) TO (SELECT FROM Person where id = $i)
        """.trimIndent())
        }
        db.commit()
    }
    @AfterEach
    fun after(){
        println("closing db connection")
        db.close()
    }
}