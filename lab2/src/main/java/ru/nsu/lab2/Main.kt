package ru.nsu.lab2

import java.util.*

object Main {
    private var scanIn = Scanner(System.`in`)
    private var str: String? = null
    private fun readRegExpr(): String? {
        println("Enter a regular expression:")
        while (true) {
            str = scanIn.nextLine()
            val newStr = str?.replace("[a-zA-Z0-9*()|&]".toRegex(), "")
            if (newStr != null) {
                if (newStr.isNotEmpty()) {
                    println("Invalid character used, try again\n")
                } else {
                    break
                }
            }
        }
        return str
    }


    @JvmStatic
    fun main(args: Array<String>) {
        val str = readRegExpr()
        val wordList = str?.let { Parser.parse(it) } ?: return
        val nfa = NFA()
        nfa.makeNFA(wordList[0])
    }
}