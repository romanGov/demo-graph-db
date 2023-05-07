package interview

fun main(){

    val fourthMatrix = createFourthMatrix()
    rotate(fourthMatrix)
    fourthMatrix.forEach { item ->
        println(item.joinToString { "$it" })
    }

    val thirdMatrix = createThirdMatrix()
    rotate(thirdMatrix)
    thirdMatrix.forEach { item ->
        println(item.joinToString { "$it" })
    }
}
fun createThirdMatrix(): Array<IntArray> {
    val elements1 = IntArray(3)
    elements1[0]=1
    elements1[1]=2
    elements1[2]=3
    val elements2 = IntArray(3)
    elements2[0]=4
    elements2[1]=5
    elements2[2]=6
    val elements3 = IntArray(3)
    elements3[0]=7
    elements3[1]=8
    elements3[2]=9
    return arrayOf(elements1, elements2, elements3)
}
fun createFourthMatrix(): Array<IntArray> {
    val elements1 = IntArray(4)
    elements1[0]=1
    elements1[1]=2
    elements1[2]=3
    elements1[3]=4
    val elements2 = IntArray(4)
    elements2[0]=5
    elements2[1]=6
    elements2[2]=7
    elements2[3]=8
    val elements3 = IntArray(4)
    elements3[0]=9
    elements3[1]=10
    elements3[2]=11
    elements3[3]=12
    val elements4 = IntArray(4)
    elements3[0]=13
    elements3[1]=14
    elements3[2]=15
    elements3[3]=16
    return arrayOf(elements1, elements2, elements3,elements4)
}
fun rotate(matrix: Array<IntArray>): Unit {

    val iterations = matrix.size-1
    for(i in 0 until iterations-1){
        println("iteration -> $i")
        val array = matrix[i]
        for(j in 0 until iterations-i){
            println("currentItem -> ${array[j]}")
            val firstElem = array[j]
            val secondElem = matrix[i+j][iterations]
            val thirdElem = matrix[iterations][iterations - j]
            val fourthElem = matrix[iterations-j][i]
            array[j] = fourthElem
            matrix[i+j][iterations] = firstElem
            matrix[iterations][iterations - j] = secondElem
            matrix[iterations-j][i] = thirdElem
        }
    }
}

