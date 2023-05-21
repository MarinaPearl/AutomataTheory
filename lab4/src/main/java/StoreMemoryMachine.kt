import java.util.HashSet
import com.google.common.collect.Multimap
import com.google.common.collect.ArrayListMultimap
import lombok.Getter
import java.util.Deque
import java.util.ArrayDeque

class StoreMemoryMachine(
    private val N: HashSet<Char>,
    private val T: HashSet<Char>,
    private var P: Multimap<Char, CharArray>,
    private val S: Char?
) {

    companion object {
        lateinit var P: Multimap<Char, CharArray>
    }

    private var FIRST: Multimap<Char, Char>
    private val FOLLOW: Multimap<Char, Char>
    private var result = false

    init {
        Companion.P = P
        FIRST = ArrayListMultimap.create()
        FOLLOW = ArrayListMultimap.create()
    }

    private fun buildFIRST() {
        for (character in T) {
            FIRST.put(character, character)
        }
        for (firstName in Companion.P.keySet()) {
            val lastNames = Companion.P.get(firstName) as List<CharArray>
            val iterator: Iterator<*> = lastNames.iterator()
            while (iterator.hasNext()) {
                val ar = iterator.next() as CharArray
                FIRST.put(firstName, ar[0])
            }
        }
        var flag = true
        while (flag) {
            flag = false
            val FIRSTI: Multimap<Char, Char> = ArrayListMultimap.create()
            FIRSTI.putAll(FIRST)
            for (firstName in FIRST.keySet()) {
                val list = FIRST[firstName] as List<Char>
                val i: Iterator<*> = list.iterator()
                while (i.hasNext()) {
                    val current = i.next() as Char
                    if (T.contains(current)) {
                        continue
                    }
                    flag = true
                    val currentList = FIRST[current] as List<Char>
                    if (!currentList.isEmpty()) {
                        FIRSTI.remove(firstName, current)
                        FIRSTI.putAll(firstName, currentList)
                    } else {
                        FIRSTI.remove(firstName, current)
                    }
                }
            }
            FIRST = FIRSTI
        }
    }

    private fun buildFOLLOW() {
        var flag = true
        while (flag) {
            flag = false
            for (firstName in Companion.P.keySet()) {
                val list = Companion.P.get(firstName) as List<CharArray>
                val i: Iterator<*> = list.iterator()
                while (i.hasNext()) {
                    val current = i.next() as CharArray
                    if (current.size == 1) {
                        continue
                    }
                    if (N.contains(current[1])) {
                        when (current.size) {
                            3 -> {
                                run {
                                    if (FIRST.containsKey(current[1]) && !FIRST.containsEntry(current[1], 'e')) {
                                        if (!FIRST.containsEntry(current[1], 'e')) {
                                            FOLLOW.putAll(current[1], FIRST[current[2]])
                                        } else if (FOLLOW.containsKey(current[1])) {
                                            FOLLOW.putAll(current[1], FOLLOW[firstName])
                                            flag = true
                                        }
                                    }
                                }
                                run {
                                    if (FOLLOW.containsKey(current[1])) {
                                        FOLLOW.putAll(current[1], FOLLOW[firstName])
                                        flag = true
                                    }
                                }
                            }
                            2 -> {
                                if (FOLLOW.containsKey(current[1])) {
                                    FOLLOW.putAll(current[1], FOLLOW[firstName])
                                    flag = true
                                }
                            }
                            else -> {}
                        }
                    }
                }
            }
        }
    }

    private fun printSet() {
        println("\n____________________________________________")
        for (firstName in FIRST.keySet()) {
            val lastNames = FIRST[firstName] as List<Char>
            val iterator: Iterator<*> = lastNames.iterator()
            print("FIRST($firstName) = { ")
            while (iterator.hasNext()) {
                print(iterator.next().toString() + " ")
            }
            println("}")
        }
        for (firstName in FOLLOW.keySet()) {
            val lastNames = FOLLOW[firstName] as List<Char>
            val iterator: Iterator<*> = lastNames.iterator()
            print("FOLLOW($firstName) = { ")
            while (iterator.hasNext()) {
                print(iterator.next().toString() + " ")
            }
            println("}")
        }
    }

    private fun checkRule() {
        result = true
        for (firstName in Companion.P.keySet()) {
            val lastNames = Companion.P.get(firstName) as List<CharArray>
            val iterator: Iterator<*> = lastNames.iterator()
            val set: Multimap<Int, Char> = ArrayListMultimap.create()
            var i = 0
            while (iterator.hasNext()) {
                val current = iterator.next() as CharArray
                for (c in current) {
                    set.put(i, c)
                }
                i++
            }
            for (integer in set.keySet()) {
                val c = set[integer] as List<Char>
                if (c.isEmpty()) {
                    continue
                }
                for (integer1 in set.keySet()) {
                    if (integer == integer1) {
                        continue
                    }
                    val c1 = set[integer1] as List<Char>
                    if (c1.isEmpty()) {
                        continue
                    }
                    if (c.containsAll(c1) || c1.containsAll(c)) {
                        result = false
                        break
                    }
                }
            }
        }
    }

    fun checkIfLL1(): Boolean {
        buildFIRST()
        buildFOLLOW()
        printSet()
        checkRule()
        return result
    }

}