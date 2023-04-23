package ru.nsu.lab2

import java.util.*

object Parser {
    fun parse(str: String): List<Word>? {
        val internalVar: MutableList<Word> = ArrayList()
        val stack: Deque<Char> = ArrayDeque()
        var flagForEach = true
        for (c in str.toCharArray()) {
            if (c == ' ') {
                continue
            }
            if (c == '(' || c == ')' || c == '&' || c == '|' || c == '*') {
                when (c) {
                    '(' -> {
                        stack.push('(')
                    }
                    '&' -> {
                        if (stack.peek() == '(') {
                            stack.push('&')
                        } else {
                            println("Incorrect expression: &")
                            flagForEach = false
                        }
                    }
                    '|' -> {
                        if (stack.peek() == '(') {
                            stack.push('|')
                        } else {
                            println("Incorrect expression: |")
                            flagForEach = false
                        }
                    }
                    '*' -> {
                        if (stack.peek() == ')') {
                            stack.pop()
                            stack.pop()
                        } else {
                            println("Incorrect expression: *")
                            flagForEach = false
                            break
                        }
                        val w = internalVar.removeAt(internalVar.size - 1)
                        internalVar.add(KleeneStar(w))
                    }
                    ')' -> {
                        if (stack.peek() == '(') {
                            stack.push(')')
                        } else if (stack.peek() == '&') {
                            val w1 = internalVar.removeAt(internalVar.size - 1)
                            val w2 = internalVar.removeAt(internalVar.size - 1)
                            internalVar.add(Concatenation(w2, w1))
                            stack.pop()
                            stack.pop()
                        } else if (stack.peek() == '|') {
                            val w1 = internalVar.removeAt(internalVar.size - 1)
                            val w2 = internalVar.removeAt(internalVar.size - 1)
                            internalVar.add(Or(w2, w1))
                            stack.pop()
                            stack.pop()
                        } else {
                            println("Incorrect expression: )")
                            flagForEach = false
                        }
                    }
                    else -> {
                        println("Incorrect expression")
                        flagForEach = false
                    }
                }
            } else {
                internalVar.add(Symbol(c))
            }
            if (!flagForEach) {
                break
            }
        }
        if (!stack.isEmpty() || !flagForEach) {
            println("Incorrect expression")
            return null
        }
        return internalVar
    }
}