import java.util.*

class TreeNode {

    var stack: Deque<Char> = ArrayDeque()

    var word: Deque<Char> = ArrayDeque()

    constructor(word: String, s: Char) {
        stack.addLast(s)
        this.word = strToStack(word)
    }

    constructor(word: Deque<Char>, stack: Deque<Char>) {
        this.stack = stack
        this.word = word
    }


    fun correct(N: HashSet<Char?>): Boolean {
        var inN = 0
        val wordSize = word.size
        for (character in stack) {
            if (!N.contains(character) && !word.contains(character)) {
                return false
            }
            if (!N.contains(character)) {
                inN++
            }
        }
        return inN <= wordSize
    }

    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        if (o == null || javaClass != o.javaClass) return false
        val treeNode = o as TreeNode
        return stack == treeNode.stack && word == treeNode.word
    }

    private fun strToStack(str: String): Deque<Char> {
        val newStack: Deque<Char> = ArrayDeque()
        for (c in str.toCharArray()) {
            newStack.addLast(c)
        }
        return newStack
    }

}
