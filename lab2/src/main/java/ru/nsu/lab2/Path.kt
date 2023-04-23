package ru.nsu.lab2

import java.util.Deque
import java.util.ArrayDeque

class Path(val from: Int, val w: Char, val to: Int) {

    override fun equals(other: Any?): Boolean {
        if (this === other) {
            return true
        }
        if (other == null) {
            return false
        }
        if (other !is Path) {
            return false
        }
        val other = other
        return other.from == from && other.w == w && other.to == to
    }

    companion object {
        private fun findMax(stack: Deque<Path>?): Int {
            var max = Int.MIN_VALUE
            for (p in stack!!) {
                max = Math.max(p.to, max)
            }
            return max
        }

        private fun shiftOrStates(stack: Deque<Path>?, shift: Int): Deque<Path> {
            val stack1: Deque<Path> = ArrayDeque()
            for (p in stack!!) {
                val p1: Path = if (p.from == 0) {
                    Path(0, p.w, p.to + shift)
                } else {
                    Path(p.from + shift, p.w, p.to + shift)
                }
                stack1.add(p1)
            }
            return stack1
        }

        fun orOp(stack1: Deque<Path>?, stack2: Deque<Path>?): Deque<Path> {
            var stack2 = stack2
            val max1 = findMax(stack1)
            val max2 = findMax(stack2)
            val stack: Deque<Path> = ArrayDeque(stack1)
            stack.removeLast()
            val p1 = stack1!!.last
            val p = Path(p1.from, p1.w, max1 + max2 - 1)
            stack.add(p)
            stack2 = shiftOrStates(stack2, max1 - 1)
            stack.addAll(stack2)
            return stack
        }

        fun kleene(stack: Deque<Path>?): Deque<Path> {
            val stack1: Deque<Path> = ArrayDeque()
            val max = findMax(stack)
            for (p in stack!!) {
                val p1 = if (p.to != max) Path(p.from + 1, p.w, p.to + 1) else Path(p.from + 1, p.w, 1)
                stack1.add(p1)
            }
            val p1 = Path(0, '\u0000', 1)
            val p2 = Path(1, '\u0000', max + 1)
            stack1.addFirst(p1)
            stack1.addLast(p2)
            return stack1
        }

        fun concat(stack1: Deque<Path>?, stack2: Deque<Path>?): Deque<Path> {
            val max1 = findMax(stack1)
            val stack: Deque<Path> = ArrayDeque(stack1)
            for (p in stack2!!) {
                val p1 = Path(p.from + max1, p.w, p.to + max1)
                stack.addLast(p1)
            }
            return stack
        }

        fun sym(s: Symbol?): Deque<Path> {
            val p = s?.let { Path(0, it.s, 1) }
            val pathArrayDeque: Deque<Path> = ArrayDeque()
            pathArrayDeque.addLast(p)
            return pathArrayDeque
        }
    }
}