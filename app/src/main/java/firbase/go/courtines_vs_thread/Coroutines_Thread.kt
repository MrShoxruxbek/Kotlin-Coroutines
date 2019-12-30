package firbase.go.courtines_vs_thread

import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.util.*
import java.util.concurrent.atomic.AtomicInteger
import kotlin.concurrent.thread
import kotlin.random.Random


var first = listOf<Int>()
var second = listOf<Int>()
var third = listOf<Int>()

fun main(args: Array<String>) {

    println("=====Classic Thread vs Coroutines =====")
    println()
    println("Available processors: [${Runtime.getRuntime().availableProcessors()}]")
    println()

    test(::thread)
    test(::launch)
    test(::async)

    println(first.size)
    println(second.size)
    println(third.size)
    /*first.forEach {
        print(": $it")
    }
    println()
    second.forEach {
        print(": $it")
    }

    println()
    third.forEach {
        print(": $it")
    }*/
}

fun test(body: (total: Int, counter: AtomicInteger, array: List<Int>) -> Unit) {
    println("Heap size MB: [${Runtime.getRuntime().totalMemory()}]")
    println("Free Memory MB: [${Runtime.getRuntime().freeMemory()}]")

    val total = 250
    val counter = AtomicInteger()
    val start = System.currentTimeMillis()

    try {
        val to = 10000
        val until = 100000

        var amplititudes = (1..to).map { Random.nextInt(until) }
        var new = amplititudes.map { square ->
            square * square
        }
        body(total, counter, new)
    } catch (e: OutOfMemoryError) {
        errorln(e)
        summary(counter, start)

        runBlocking {
            println("Sleeping 2 sec...")
            println()
            delay(2_000)
        }
        return
    }
    summary(counter, start)
}

fun thread(total: Int, counter: AtomicInteger, array: List<Int>) {
    println("Testing threads...")
    List(total) {
        thread(start = true) {
            try {
                counter.incrementAndGet()
                first = array.mergeSort()
                Thread.sleep(1_000)
            } catch (e: InterruptedException) {
                errorln(2)
            }
        }
    }
}

fun launch(total: Int, counter: AtomicInteger, array: List<Int>) {
    println("Testing coroutine scope")
    runBlocking {
        List(total) {
            launch {
                third = array.mergeSort()
                counter.incrementAndGet()
                delay(1_000)
            }
        }.forEach {
            it.join()
        }
    }
}

fun async(total: Int, counter: AtomicInteger, array: List<Int>) {
    println("Testing coroutines with async...")
    runBlocking {
        var deferred = (1..total).map { number ->
            async {
                second = array.mergeSort()
                delay(1_000)
                1
            }
        }
        counter.addAndGet(deferred.sumBy { it.await() })
    }
}

fun errorln(any: Any) {
    System.err.println(any)
}

fun summary(counter: AtomicInteger, start: Long) {
    println("Total time: [${(System.currentTimeMillis() - start) / 1000f}]")
    println("Total spawned threads/coroutines: [${counter.get()}]")
    println()
}


fun <E : Comparable<E>> List<E>.mergeSort(): List<E> {
    if (size <= 1) return this
    val queue = LinkedList<List<E>>()

    forEach { element -> queue.add(listOf(element)) }
    while (queue.size > 1) {
        val left = queue.removeFirst()
        val right = queue.removeFirst()
        queue.add(merge(left, right))
    }
    return queue.single()
}


fun <E : Comparable<E>> merge(left: List<E>, right: List<E>): List<E> {
    val result = java.util.ArrayList<E>()
    var i = 0
    var j = 0
    while (i < left.size && j < right.size) {
        val minElement = if (left[i] <= right[j]) left[i++] else right[j++]
        result.add(minElement)
    }
    result.addAll(left.subList(i, left.size))
    result.addAll(right.subList(j, right.size))
    return result
}

fun <E : Comparable<E>> List<E>.mergeSort_recursive(): List<E> {
    if (size <= 1) return this
    return merge(
        subList(0, size / 2).mergeSort_recursive(),
        subList(size / 2, size).mergeSort_recursive()
    )
}

fun <E : Comparable<E>> merge_recursive(left: List<E>, right: List<E>): List<E> {
    return when {
        left.isEmpty() -> right
        right.isEmpty() -> left
        left[0] <= right[0] -> listOf(left[0]) + merge_recursive(left.drop(1), right)
        else -> listOf(right[0]) + merge_recursive(left, right.drop(1))
    }
}
