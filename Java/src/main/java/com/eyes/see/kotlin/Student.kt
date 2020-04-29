class Student : Person, Study {

    override fun readBooks() {
//        TODO("Not yet implemented")
        println(name + " " + age + " " + "readBooks")
    }

    override fun doWork() {
//        TODO("Not yet implemented")
    }

    init {
        name = "Asd";
        age = 10
    }

    constructor(name: String, age: Int) {
        this.name=name;
        this.age=age
    }


}

fun main() {
    var s = Student("aini",123)
    s.readBooks()

    var s1= Singletons()
    s1.tests()
}
