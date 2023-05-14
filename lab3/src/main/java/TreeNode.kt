import java.util.*

class TreeNode {
    var stack: String

    var word: Deque<Char>

    constructor(word: String) {
        stack = ""
        this.word = str2stack(word)
    }

    constructor(word: Deque<Char>, stack: String) {
        this.stack = stack
        this.word = word
    }

    private fun str2stack(str: String): Deque<Char> {
        val newStack: Deque<Char> = ArrayDeque()
        for (c in str.toCharArray()) {
            newStack.addLast(c)
        }
        return newStack
    }

    private fun deq2str(deque: Deque<Char>): String {
        val i: Iterator<*> = deque.iterator()
        var str = String()
        while (i.hasNext()) {
            str += i.next()
        }
        return str
    }

    override fun equals(obj: Any?): Boolean {
        if (this === obj) {
            return true
        }
        if (obj == null || javaClass != obj.javaClass) {
            return false
        }
        val treeNode = obj as TreeNode
        return stack == treeNode.stack && word == treeNode.word
    }

    override fun toString(): String {
        return "(" +
                "word = " + deq2str(word) +
                ", stack = " + stack +
                ')'
    }

    override fun hashCode(): Int {
        var result = stack.hashCode()
        result = 31 * result + word.hashCode()
        return result
    }
}