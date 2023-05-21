import com.google.common.collect.ArrayListMultimap
import com.google.common.collect.Multimap
import java.util.*

private val scanner = Scanner(System.`in`)
private val N = HashSet<Char>()
private val T = HashSet<Char>()
private val P: Multimap<Char, CharArray> = ArrayListMultimap.create()
private var S: Char? = null

fun main(args: Array<String>) {
    println("Enter Context Free Grammar:")
    parseN()
    parseT()
    parseP()
    parseS()
    val storeMemoryMachine = StoreMemoryMachine(N, T, P, S)
    println("\nEnter a string to recognize from the characters of the set ∑")
    val result = storeMemoryMachine.checkIfLL1()
    println("\n____________________________________________")
    println(if (result) "It's LL(1) grammar!" else "This grammar isn't LL(1)")
}

private fun parseN() {
    var str: String
    while (true) {
        var flag = false
        print("N = ")
        str = scanner.nextLine()
        val newStr: String = str.replace("[^->e'\"]".toRegex(), "")
        if (newStr.isNotEmpty()) {
            println("Wrong character")
        } else {
            val N_arr: Array<String> = str.split(",").toTypedArray()
            for (value in N_arr) {
                val arr: CharArray = value.toCharArray()
                if (arr.size > 1) {
                    println("You cannot use a bunch of characters in one")
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

fun parseT() {
    var str: String
    while (true) {
        var flag = false
        var flag1 = false
        print("∑ = ")
        str = scanner.nextLine()
        val newStr: String = str.replace("[^->e'\"]".toRegex(), "")
        if (newStr.isNotEmpty()) {
            println("Wrong character")
        } else {
            for (c in str.toCharArray()) {
                if (N.contains(c)) {
                    println("Wrong character $c from N")
                    flag = true
                    break
                }
            }
            if (flag) {
                continue
            }
            val Terminal_arr: Array<String> = str.split(",").toTypedArray()
            for (value in Terminal_arr) {
                val arr: CharArray = value.toCharArray()
                if (arr.size > 1) {
                    println("You cannot use a bunch of characters in one")
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

fun parseP() {
    var str: String
    while (true) {
        var flag = false
        print("P = ")
        str = scanner.nextLine()
        if (!str.matches(Regex("[^'\"]*"))) {
            println("Wrong character")
            continue
        }
        val P_arr: Array<String> = str.split(",").toTypedArray()
        for (s in P_arr) {
            val P_current: Array<String> = s.split("->").toTypedArray()
            if (P_current.size == 2) {
                val A: CharArray = P_current[0].toCharArray()
                if (A.size == 1 && N.contains(A[0])) {
                    val a: CharArray = P_current[1].toCharArray()
                    for (c in a) {
                        if (!N.contains(c) && !T.contains(c) && c != 'e') {
                            println("A non-set character was used $c")
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
                    println("The nonterminal does not belong to the set N")
                    flag = true
                    break
                }
            } else {
                println("Rules should look like A->a")
                flag = true
                break
            }
        }
        if (!flag) {
            break
        }
    }
}

fun parseS() {
    var str: String
    while (true) {
        print("S = ")
        str = scanner.nextLine()
        val chars: CharArray = str.toCharArray()
        if (chars.size != 1) {
            println("Only one non-terminal needed")
        } else if (!N.contains(chars[0])) {
            println("The nonterminal does not belong to the set N")
        } else {
            S = chars[0]
            break
        }
    }
}
