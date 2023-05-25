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

fun rotate(matrix: Array<IntArray>): Unit {

    val n = matrix.size
    for (layer in 0 until n / 2) {
        val first = layer
        val last = n - 1 - layer
        for (i in first until last) {
            val offset = i - first
            val top = matrix[first][i] // Save top element
            println("top -> $top")
            // Left -> Top
            matrix[first][i] = matrix[last - offset][first]
            println("matrix first i -> ${matrix[first][i]}")
            // Bottom -> Left
            matrix[last - offset][first] = matrix[last][last - offset]

            // Right -> Bottom
            matrix[last][last - offset] = matrix[i][last]

            // Top -> Right
            matrix[i][last] = top
        }
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
    elements4[0]=13
    elements4[1]=14
    elements4[2]=15
    elements4[3]=16
    return arrayOf(elements1, elements2, elements3,elements4)
}