package interview


fun main() {
    val l1 = ListNode(9).apply {
        next = ListNode(9).apply {
            next = ListNode(9).apply {
                next = ListNode(9).apply {
                    next = ListNode(9).apply {
                        next = ListNode(9).apply {
                            next = ListNode(9)
                        }
                    }
                }
            }
        }
    }
    val l2 = ListNode(9).apply {
        next = ListNode(9).apply {
            next = ListNode(9).apply {
                next = ListNode(9)
            }
        }
    }

    println(addTwoNumbers(l1, l2))
}

fun addTwoNumbers(l1: ListNode?, l2: ListNode?): ListNode? {

    val list1 = createNumberFromNode(l1!!, mutableListOf())
    println("list1-> $list1")
    val list2 = createNumberFromNode(l2!!, mutableListOf())
    println("list2-> $list2")
    val result = sumArrays(list1, list2)
    println("result-> $result")
    val parentNode = ListNode(result[result.size - 1])
    createNodeFromNumber(result, parentNode)
    println(parentNode)
    return parentNode
}

fun sumArrays(array1: MutableList<Int>, array2: MutableList<Int>, currentIndex: Int = 1): MutableList<Int> {
    println("currentIndex -> $currentIndex")
    val currentFirstIndex = array1.size - currentIndex
    val currentSecondIndex = array2.size - currentIndex
    if (currentFirstIndex == 0) array1.add(0, 0)
    if (currentSecondIndex == 0) array2.add(0, 0)
    println("array1 -> $array1")
    println("array2 -> $array2")
    val number1 = array1[array1.size - currentIndex]
    println("number1 -> $number1")
    var number2 = array2[array2.size - currentIndex]
    println("number2 -> $number2")
    val sum = number1 + number2
    println("sum -> $sum")
    println("currentFirstIndex -> $currentFirstIndex")
    println("currentSecondIndex -> $currentSecondIndex")

    if (sum >= 10) {
        val i = sum - 10
        array1[array1.size - currentIndex] = i
        val next = array1[array1.size-1-currentIndex]
        array1[array1.size-1-currentIndex] = next + 1
    } else {
        array1[array1.size-currentIndex] = sum
    }
    if (currentFirstIndex == 0 && currentSecondIndex == 0) {
        if(array1[0]==0)array1.removeAt(0)
        return array1
    }
    return sumArrays(array1, array2, currentIndex + 1)
}

fun createNumberFromNode(node: ListNode, resultArray: MutableList<Int>): MutableList<Int> {
    if (node.next != null) {
        resultArray.add(0, node.`val`)
        return createNumberFromNode(node.next!!, resultArray)
    } else {
        resultArray.add(0, node.`val`)
        return resultArray
    }
}

fun createNodeFromNumber(list: MutableList<Int>, parentNode: ListNode?) {
    list.removeAt(list.size - 1)
    if (list.isEmpty()) {
        return
    }
    val fractional = list[list.size - 1]

    parentNode?.next = ListNode(fractional)
    if (list[0] != 0) {
        createNodeFromNumber(list, parentNode?.next)
    }
}

class ListNode(var `val`: Int) {
    var next: ListNode? = null
    override fun toString(): String {
        return "$`val` ${next.toString()}"
    }
}
