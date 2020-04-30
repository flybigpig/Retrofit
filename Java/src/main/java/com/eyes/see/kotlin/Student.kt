class Student(name :String ,age :Int) : Person(name, age), Study {

    override fun readBooks() {
//        TODO("Not yet implemented")
        println(name + " " + age + " " + "readBooks")
    }

    override fun doWork() {
//        TODO("Not yet implemented")
    }


}

fun main() {
    var s = Student("hah",12)
    s.readBooks()

    var s1= Singletons()
    s1.tests()
}
