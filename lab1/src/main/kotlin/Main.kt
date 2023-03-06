import com.google.common.collect.ArrayListMultimap
import com.google.common.collect.Multimap
import java.util.*

private val scanner = Scanner(System.`in`)
private val N = HashSet<Char?>()
private val T = HashSet<Char>()
private val P: Multimap<Char?, CharArray?> = ArrayListMultimap.create()
private var S: Char? = null

fun main(args: Array<String>) {
    println("Enter Context Free Grammar (without spaces):")
    readN()
    readT()
    readP()
    readS()
    val chainAnalyzer = ChainAnalyzer(N, T, P, S!!)
    println("\nEnter chine")
    var innerStr: String
    while (true) {
        var flag = false
        innerStr = scanner.nextLine()
        for (c in innerStr.toCharArray()) {
            if (!T.contains(c)) {
                println("∑ does not contain this symbol")
                flag = true
                break
            }
        }
        if (!flag) {
            break
        }
    }
    val result = chainAnalyzer.syntaxAnalyzer(innerStr)
    println(if (result) "YES" else "NO")
}

private fun readN() {
    var str: String
    while (true) {
        var flag = false
        print("N = ")
        str = scanner.nextLine()
        val newStr = str.replace("[^->e'\"]".toRegex(), "")
        if (newStr.isNotEmpty()) {
            println("not correct")
        } else {
            val nArr = str.split(",").toTypedArray()
            for (value in nArr) {
                val arr = value.toCharArray()
                if (arr.size > 1) {
                    println("size > 1")
                    flag = true
                    N.clear()
                    break
                } else {
                    N.add(arr[0])
                }
            }
            if (!flag) {
                break
            }
        }
    }
}

fun readT() {
    var str: String
    while (true) {
        var flag = false
        var flag1 = false
        print("∑ = ")
        str = scanner.nextLine()
        val newStr = str.replace("[^->e'\"]".toRegex(), "")
        if (newStr.isNotEmpty()) {
            println("Wrong symbol")
        } else {
            for (c in str.toCharArray()) {
                if (N.contains(c)) {
                    println("Wrong symbol $c from N")
                    flag = true
                    break
                }
            }
            if (flag) {
                continue
            }
            val terminalArr = str.split(",").toTypedArray()
            for (value in terminalArr) {
                val arr = value.toCharArray()
                if (arr.size > 1) {
                    println("size > 1")
                    flag1 = true
                    T.clear()
                    break
                } else {
                    T.add(arr[0])
                }
            }
            if (!flag1) {
                break
            }
        }
    }
}

fun readP() {
    var str: String
    while (true) {
        var flag = false
        print("P = ")
        str = scanner.nextLine()
        if (!str.matches(Regex("[^'\"]*"))) {
            println("Wrong character")
            continue
        }
        val pArr = str.split(",").toTypedArray()
        for (s in pArr) {
            val pCurrent = s.split("->").toTypedArray()
            if (pCurrent.size == 2) {
                val A = pCurrent[0].toCharArray()
                if (A.size == 1 && N.contains(A[0])) {
                    val a = pCurrent[1].toCharArray()
                    for (c in a) {
                        if (!N.contains(c) && !T.contains(c) && c != 'e') {
                            println("A non-set character")
                            flag = true
                            break
                        }
                    }
                    if (flag) {
                        break
                    } else {
                        P.put(A[0], a)
                    }
                } else {
                    println("The not Terminal symbol in N")
                    flag = true
                    break
                }
            } else {
                println("Rules should look like: B->b")
                flag = true
                break
            }
        }
        if (!flag) {
            break
        }
    }
}

fun readS() {
    var str: String
    while (true) {
        print("S = ")
        str = scanner.nextLine()
        val chars = str.toCharArray()
        if (chars.size != 1) {
            println("Only one non-terminal needed")
        } else if (!N.contains(chars[0])) {
            println("The not Terminal symbol in N")
        } else {
            S = chars[0]
            break
        }
    }
}
