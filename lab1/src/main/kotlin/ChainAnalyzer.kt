import com.google.common.collect.Multimap
import java.util.*

class ChainAnalyzer(
    private val N: HashSet<Char?>,
    private val T: HashSet<Char>,
    p: Multimap<Char?, CharArray?>,
    s: Char
) {
    private val S: Char
    private var currentTree: HashSet<TreeNode>
    private var result = false

    init {
        P = p
        S = s
        currentTree = HashSet()
    }

    private fun step() {
        val newTreeLevel = HashSet<TreeNode>()
        for (treeNode in currentTree) {
            var flag = false
            val word: Deque<Char> = ArrayDeque(treeNode.word)
            val stack: Deque<Char> = ArrayDeque(treeNode.stack)
            while (!word.isEmpty() && !stack.isEmpty() && word.peekFirst() === stack.peekFirst()) {
                word.removeFirst()
                stack.removeFirst()
                flag = true
            }
            if (flag) {
                val newTreeNode = TreeNode(word, stack)
                if (treeNode.correct(N)) {
                    newTreeLevel.add(newTreeNode)
                }
                continue
            }
            if (P.containsKey(stack.peekFirst())) {
                val list = P.get(stack.peekFirst()) as List<*>
                val iterator: Iterator<*> = list.iterator()
                while (iterator.hasNext()) {
                    val chars = iterator.next() as CharArray
                    val newStack: Deque<Char> = ArrayDeque(stack)
                    newStack.removeFirst()
                    if (chars.size == 1 && chars[0] == 'e') {
                        val newTreeNode = TreeNode(word, newStack)
                        newTreeLevel.add(newTreeNode)
                        continue
                    }
                    for (i in chars.indices.reversed()) {
                        newStack.addFirst(chars[i])
                    }
                    val newTreeNode = TreeNode(word, newStack)
                    if (treeNode.correct(N)) {
                        newTreeLevel.add(newTreeNode)
                    }
                }
            }
        }
        currentTree = newTreeLevel
    }

    private fun checkTreeLevel(): Boolean {
        var flag = false
        if (currentTree.isEmpty()) {
            result = false
            return true
        }
        for (treeNode in currentTree) {
            if (treeNode.word.isEmpty() && treeNode.stack.isEmpty()) {
                flag = true
                result = true
                break
            }
        }
        return flag
    }

    fun syntaxAnalyzer(str: String?): Boolean {
        currentTree.add(TreeNode(str!!, S))
        while (!checkTreeLevel()) {
            step()
        }
        return result
    }

    companion object {
        private lateinit var P: Multimap<Char?, CharArray?>
    }
}