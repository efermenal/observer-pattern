
interface Observer{
    fun subscribe()
    fun unSubscribe()
    fun update(desc: String)
}

interface Subject{
    fun subscribeObserver(observer : Observer)
    fun subscribeObserverList(list: List<Observer>)
    fun unsubscribeObserver(observer: Observer)
    fun notifyObservers()
    fun subjectDetails() : String
}

interface Commentary {
    fun setDescription(desc: String)
}

class CommentaryObject (private val observer : MutableList<Observer>, private val subjectDetail: String) : Commentary, Subject{

    private lateinit var desc: String

    override fun subscribeObserver(observer: Observer) {
        this.observer.add(observer)
    }

    override fun subscribeObserverList(list: List<Observer>) {
        this.observer.addAll(list)
    }

    override fun unsubscribeObserver(observer: Observer) {
        val index : Int = this.observer.indexOf(observer)
        this.observer.removeAt(index)
    }

    override fun notifyObservers() {
        this.observer.forEach {
            it.update(desc)
        }
    }

    override fun subjectDetails(): String {
        return subjectDetail
    }

    override fun setDescription(desc: String) {
        this.desc = desc
        notifyObservers()
    }

}

class SMSUser (private val subject : Subject, private val userInfo : String): Observer{

    private lateinit var desc : String

    override fun subscribe() {
        this.subject.subscribeObserver(this)
    }

    override fun unSubscribe() {
        this.subject.unsubscribeObserver(this)
    }

    override fun update(desc: String) {
        this.desc = desc
        display()
    }

    private fun display(){
        println("$userInfo receiving $desc")
    }

    override fun toString(): String {
        return userInfo
    }

}

fun main() {

    val s : Subject = CommentaryObject(arrayListOf(),"Event")

    val o : Observer = SMSUser(s, "Mobile user")
    o.subscribe()

    val o2 : Observer = SMSUser(s, "Laptop user")
    o2.subscribe()

    val c : Commentary? = s as? Commentary
    c?.setDescription("1st message")
    c?.setDescription("2nd message")

    o2.unSubscribe()
    println("unsubscribing $o2")

    c?.setDescription("3rd message")

    o2.subscribe()
    println("subscribing again $o2")
    c?.setDescription("4th message")

}