package firbase.go.courtines_vs_thread

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.coroutine.*
import kotlinx.coroutines.*
import kotlin.random.Random
import kotlin.system.measureTimeMillis


class CoroutineScope : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.coroutine)

        randomiez = (1..to).map { Random.nextInt(until) }
        randomiez2 = (1..to).map { Random.nextInt(until) }
        randomiez3 = (1..to).map { Random.nextInt(until) }

        btn_click.setOnClickListener {
            tv_text.text = ""
            setNewText("Clicked")
            fakeAsync()
        }
        btn_click4.setOnClickListener {
            tv_text.text = ""
            setNewText("Clicked")
            fakeCoroutine()
        }
        btn_click2.setOnClickListener {
            tv_text.text = ""
            setNewText("Clicked")
            //fakeThread()
        }

        btn_click3.setOnClickListener {
            tv_text.text = ""
            setNewText("Clicked")
            justDo()
        }
    }

    private fun setNewText(input: String) {
        val newText = tv_text.text.toString() + "\n$input"
        tv_text.text = newText
    }

    private suspend fun setTextOnMainThread(input: String) {
        withContext(Dispatchers.Main) {
            setNewText(input)
        }
    }

    val to = 100 //1.000.000
    val until = 100 //1.000.000
    val upto = 100//1.000.000
    var randomiez: List<Int>? = null
    var randomiez2: List<Int>? = null
    var randomiez3: List<Int>? = null

    private fun fakeCoroutine() {
        val startTime = System.currentTimeMillis()
        val parentJob = CoroutineScope(Dispatchers.IO).launch {

            val job1 = launch {
                val time = measureTimeMillis {
                    println("Debug: launchung job1 in thread: ${Thread.currentThread().name}")
                    val result1 = getResultfromApi(randomiez!!)
                    setTextOnMainThread("Got: $result1")
                }
                println("debug: completed job1 in $time ms")
            }

            //job1.join()
            val job2 = launch {
                val time = measureTimeMillis {
                    println("Debug: launchung job2 in thread: ${Thread.currentThread().name}")
                    val result1 = getResultfromApi2(randomiez2!!)
                    setTextOnMainThread("Got: $result1")
                }

                println("debug: completed job2 in $time ms.")
            }

            val job3 = launch {
                val time = measureTimeMillis {
                    println("Debug: launchung job3 in thread: ${Thread.currentThread().name}")
                    val result1 = getResultfromApi2(randomiez3!!)
                    setTextOnMainThread("Got: $result1")
                }

                println("debug: completed job3 in $time ms.")
            }
        }

        parentJob.invokeOnCompletion {
            println("Debug: total time: ${System.currentTimeMillis() - startTime}")
        }
    }

    private fun fakeAsync() {

        CoroutineScope(Dispatchers.IO).launch {
            val executionTime = measureTimeMillis {

                val result1: Deferred<String> = async {
                    println("debug: launching job1: ${Thread.currentThread().name}")
                    getResultfromApi(randomiez!!)
                }

                val result2: Deferred<String> = async {
                    println("debug: launching job2: ${Thread.currentThread().name}")
                    getResultfromApi2(randomiez2!!)
                }

                val result3: Deferred<String> = async {
                    println("debug: launching job3: ${Thread.currentThread().name}")
                    getResultfromApi3(randomiez3!!)
                }



                setTextOnMainThread("Got: ${result1.await()}")
                setTextOnMainThread("Got: ${result2.await()}")
                setTextOnMainThread("Got: ${result3.await()}")

            }
            println("Debug: total time: ${executionTime}")
        }
    }

    fun fakeThread() {
        val to = 1000
        val until = 1000
        var text: String? = null
        val start = System.currentTimeMillis()
        /*val thread1 = Thread(Runnable {

        })*/


        val thread2 = Thread(Runnable {
            text = "Result2: " + getSquare(randomiez!!)
            println("Debug: Complete second job2")
        })
        kotlin.concurrent.thread(start = true) {
            try {
                text = ("Result1" + getSquare(randomiez!!))
                println("Debug: Complete first job1")

            } catch (e: InterruptedException) {
                errorln(2)
            }
        }

        runOnUiThread {

        }
        println("Debug: total time: ${System.currentTimeMillis() - start}")
    }

    fun justDo() {
        var start = System.currentTimeMillis()
        var count = 0

        setNewText("Result1:" + getSquare(randomiez!!))
        println("Debug: First job: ${System.currentTimeMillis() - start}")
        setNewText("Result2:" + getSquare2(randomiez2!!))
        println("Debug: Second job: ${System.currentTimeMillis() - start}")
        setNewText("Result3:" + getSquare3(randomiez3!!))
        println("Debug: total time: ${System.currentTimeMillis() - start}")
    }

    private suspend fun getResultfromApi(value: List<Int>): String {
        return "Result1" + getSquare(value)
    }

    private suspend fun getResultfromApi2(value: List<Int>): String {
        return "Result2" + getSquare2(value)
    }

    private suspend fun getResultfromApi3(value: List<Int>): String {
        return "Result3" + getSquare3(value)
    }

    fun getSquare(randomiez: List<Int>): String {
        val first = randomiez[0]
        val after = randomiez.map {
            it * it
        }
        return ":" + after.size
    }

    fun getSquare2(randomiez: List<Int>): String {
        val first = randomiez[0]

        val after = randomiez.map {
            it * it
        }

        after.forEach {
            it*it
        }

        after.mergeSort()
        return ":" + after.size
    }

    fun getSquare3(randomiez: List<Int>): String {
        val first = randomiez[0]

        val after = randomiez.map {
            it * it
        }

        after.mergeSort()
        return ":" + after.size
    }

}

