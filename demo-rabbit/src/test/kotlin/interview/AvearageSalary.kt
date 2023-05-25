package interview

fun main(){
    println(average(arrayOf(4000, 3000, 2000, 1000).toIntArray()))
}
fun average(salary: IntArray): Double {
    salary.sort()
    return salary.copyOfRange(1, salary.size - 1).average()
}