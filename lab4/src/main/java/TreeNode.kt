import java.util.HashSet
import com.google.common.collect.Multimap
import com.google.common.collect.ArrayListMultimap
import lombok.Getter
import lombok.Setter
import java.util.Deque
import java.util.ArrayDeque

class TreeNode {
    @Getter
    @Setter
    private var stack: Deque<Char> = ArrayDeque()

    @Getter
    private var word: Deque<Char> = ArrayDeque()

    constructor(word: String, s: Char) {
        stack.addLast(s)
        this.word = str2stack(word)
    }

    constructor(word: Deque<Char>, stack: Deque<Char>) {
        this.stack = stack
        this.word = word
    }

    fun setWord(word: Deque<Char>) {
        this.word = word
    }

    fun setWord(word: String) {
        this.word = str2stack(word)
    }

    fun str2stack(str: String): Deque<Char> {
        val newStack: Deque<Char> = ArrayDeque()
        for (c in str.toCharArray()) {
            newStack.addLast(c)
        }
        return newStack
    }

    fun deq2str(deque: Deque<Char>): String {
        val i: Iterator<*> = deque.iterator()
        var str = String()
        while (i.hasNext()) {
            str += i.next()
        }
        return str
    }

    fun correct(N: HashSet<Char?>): Boolean {
        var `is` = 0
        val iw = word.size
        for (character in stack) {
            if (!N.contains(character) && !word.contains(character)) {
                return false
            }
            if (!N.contains(character)) {
                `is`++
            }
        }
        return `is` <= iw
    }

    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        if (o == null || javaClass != o.javaClass) return false
        val treeNode = o as TreeNode
        return stack == treeNode.stack && word == treeNode.word
    }

    override fun toString(): String {
        return "(" +
                "word = " + deq2str(word) +
                ", stack = " + deq2str(stack) +
                ')'
    }
}