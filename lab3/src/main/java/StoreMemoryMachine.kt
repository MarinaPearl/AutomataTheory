import com.google.common.collect.Multimap
import java.util.*

class StoreMemoryMachine(
    P: Multimap<String, Char>,
    private val S: Char?
) {
    private var treeLevel: HashSet<TreeNode>
    private var result = false

    init {
        Companion.P = P
        treeLevel = HashSet()
    }

    private fun printTreeLevel() {
        println("\n____________________________________________")
        for (treeNode in treeLevel) {
            println(treeNode)
        }
    }

    private fun step() {
        val newTreeLevel = HashSet<TreeNode>()
        for (treeNode in treeLevel) {
            val word: Deque<Char> = ArrayDeque(treeNode.word)
            val stack = treeNode.stack
            if (!word.isEmpty()) {
                val character = word.peekFirst()
                val newWord: Deque<Char> = ArrayDeque(word)
                newWord.removeFirst()
                val newTreeNode = TreeNode(newWord, stack + character.toString())
                newTreeLevel.add(newTreeNode)
            }
            for (string in P.keySet()) {
                val list = P.get(string) as List<*>
                if (string == "e") {
                    for (character in list) {
                        var fromIndex = 0
                        val Index = stack.length
                        while (fromIndex < Index - 1) {
                            val newStack = stack.substring(0, fromIndex + 1) + character + stack.substring(
                                fromIndex + 1,
                                fromIndex + 2
                            )
                            val newTreeNode = TreeNode(word, newStack)
                            newTreeLevel.add(newTreeNode)
                            fromIndex++
                        }
                        val newStack = stack + character
                        val newTreeNode = TreeNode(word, newStack)
                        newTreeLevel.add(newTreeNode)
                    }
                }
                if (stack.contains(string)) {
                    for (character in list) {
                        var fromIndex = 0
                        var Index = stack.indexOf(string, fromIndex)
                        while (Index != -1) {
                            val newStack = stack.replaceFirst(string.toRegex(), character.toString())
                            val newTreeNode = TreeNode(word, newStack)
                            newTreeLevel.add(newTreeNode)
                            fromIndex = Index + 1
                            Index = stack.indexOf(string, fromIndex)
                        }
                    }
                }
            }
        }
        treeLevel = newTreeLevel
    }

    private fun checkTreeLevel(): Boolean {
        var flag = false
        if (treeLevel.isEmpty()) {
            result = false
            return true
        }
        for (treeNode in treeLevel) {
            if (treeNode.word.isEmpty() && treeNode.stack == S.toString()) {
                flag = true
                result = true
                break
            }
        }
        return flag
    }

    fun syntaxAnalyzer(str: String): Boolean {
        treeLevel.add(TreeNode(str))
        printTreeLevel()
        while (!checkTreeLevel()) {
            step()
            printTreeLevel()
        }
        return result
    }

    companion object {
        lateinit var P: Multimap<String, Char>
    }
}