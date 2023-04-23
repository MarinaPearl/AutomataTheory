package ru.nsu.lab2

import java.util.*

class NFA {
    private var nfa: Deque<Path>? = null
    private val alphabet: HashSet<Char> = HashSet()

    fun makeNFA(w: Word?) {
        val pathDeque = maker(w)
        if (pathDeque != null) {
            for (p in pathDeque) {
                val c = if (p.w == '\u0000') 'e' else p.w
                println("| " + p.from + " | " + c + " | " + p.to + " |")
            }
        }
        nfa = pathDeque
    }

    private fun maker(w: Word?): Deque<Path>? {
        return when (w!!.javaClass.canonicalName) {
            "ru.nsu.lab2.Symbol" -> {
                (w as Symbol?)?.s?.let { alphabet.add(it) }
                Path.sym(w as Symbol?)
            }
            "ru.nsu.lab2.Concatenation" -> {
                val w1 = (w as Concatenation?)!!.s1
                val w2 = (w as Concatenation?)!!.s2
                val pathDeque1 = maker(w1)
                val pathDeque2 = maker(w2)
                Path.concat(pathDeque1, pathDeque2)
            }
            "ru.nsu.lab2.KleeneStar" -> {
                val w1 = (w as KleeneStar?)!!.s
                val pathDeque1 = maker(w1)
                Path.kleene(pathDeque1)
            }
            "ru.nsu.lab2.Or" -> {
                val w1 = (w as Or?)?.s1
                val w2 = (w as Or?)?.s2
                val pathDeque1 = maker(w1)
                val pathDeque2 = maker(w2)
                Path.orOp(pathDeque1, pathDeque2)
            }
            else -> {
                println(w.javaClass.canonicalName)
                null
            }
        }
    }
}