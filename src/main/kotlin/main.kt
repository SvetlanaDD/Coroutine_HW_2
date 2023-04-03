
import kotlinx.coroutines.*
import kotlin.coroutines.EmptyCoroutineContext

// Cancellation

fun main() = runBlocking {
    val job = CoroutineScope(EmptyCoroutineContext).launch {
        launch {
            delay(500)
            println("ok") // <--  не успеет отработать, т.к. отмена job и ее дочерних корутин сработает раньше
        }
        launch {
            delay(500)
            println("ok")
        }
    }
    delay(100)
    job.cancelAndJoin()
}

/*
fun main() = runBlocking {
    val job = CoroutineScope(EmptyCoroutineContext).launch {
        val child = launch {
            delay(500)
            println("ok") // <-- не успеет отработать, т.к. отмена child сработает раньше из-за параллельной отработки корутин
        }
        launch {
            delay(500)
            println("ok")
        }
        delay(100)
        child.cancel()
    }
    delay(100)
    job.join()
}
*/

//Exception Handling
/*
fun main() {
    with(CoroutineScope(EmptyCoroutineContext)) {
        try {
            launch {
                throw Exception("something bad happened")
            }
        } catch (e: Exception) {
            e.printStackTrace() // <-- не отработает, так исключения не перехватить т.к. try-catch не внутри корутины не имеет смысла
        }
    }
    Thread.sleep(1000)
}

*/
/*
fun main() {
    CoroutineScope(EmptyCoroutineContext).launch {
        try {
            coroutineScope {
                throw Exception("something bad happened")
            }
        } catch (e: Exception) {
            e.printStackTrace() // <-- отработает, coroutineScope перехватывает ошибки
        }
    }
    Thread.sleep(1000)
}
*/

/*
fun main() {
    CoroutineScope(EmptyCoroutineContext).launch {
        try {
            supervisorScope {
                throw Exception("something bad happened")
            }
        } catch (e: Exception) {
            e.printStackTrace() // <-- отработает, supervisorScope обработает ошибку
        }
    }
    Thread.sleep(1000)
}
*/
/*
fun main() {
    CoroutineScope(EmptyCoroutineContext).launch {
        try {
            coroutineScope {
                launch {
                    delay(500)
                    throw Exception("something bad happened") // <-- не отработает из-за задержки, т.к. корутины запустились параллельно, вторая отработает раньше и перехватит ошибку
                }
                launch {
                    throw Exception("something bad happened")
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
    Thread.sleep(1000)
}
*/

/*
fun main() {
    CoroutineScope(EmptyCoroutineContext).launch {
        try {
            supervisorScope {
                launch {
                    delay(500)
                    throw Exception("something bad happened") // <-- отработает, supervisorScope обработает ошибку, при этом даст отработать и другим дочерним корутинам
                }
                launch {
                    throw Exception("something bad happened")
                }
            }
        } catch (e: Exception) {
            e.printStackTrace() // <-- не отработает, supervisorScope обрабатывает ошибки, при этом не перебросит ее на родительскую корутину
        }
    }
    Thread.sleep(1000)
}
*/
/*
fun main() {
    CoroutineScope(EmptyCoroutineContext).launch {
        CoroutineScope(EmptyCoroutineContext).launch {
            launch {
                delay(1000)
                println("ok") // <-- не отработает, при параллельном выполнении ошибка пробросится первая
            }
            launch {
                delay(500)
                println("ok")
            }
            throw Exception("something bad happened")
        }
    }
    Thread.sleep(1000)
}
*/

/*
fun main() {
    CoroutineScope(EmptyCoroutineContext).launch {
        CoroutineScope(EmptyCoroutineContext + SupervisorJob()).launch {
            launch {
                delay(1000)
                println("ok") // <-- не отработает, при параллельном выполнении ошибка пробросится первая, а SupervisorJob() действует на родительскую корутину
            }
            launch {
                delay(500)
                println("ok")
            }
            throw Exception("something bad happened")
        }
    }
    Thread.sleep(1000)
}
*/
