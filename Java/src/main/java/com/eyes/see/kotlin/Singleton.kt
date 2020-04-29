class Singletons {

    fun tests() {
        println("test")
    }
}

object Singleton {
    fun singleton() {
        println("as a singleton is here")
    }
}

fun main() {

    Thread(object : Runnable {
        override fun run() {
//            TODO("Not yet implemented")
            var s = Singleton;
            s.singleton()
            System.out.println("Thread as a singleton is here")
        }
    }).start()
}