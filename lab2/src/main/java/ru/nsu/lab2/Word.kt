package ru.nsu.lab2

interface Word

class Or(val s1: Word, val s2: Word) : Word {

    override fun toString(): String {
        return "or($s1 , $s2)"
    }
}


class Symbol(val s: Char) : Word {

    override fun toString(): String {
        return "symbol($s)"
    }
}


class KleeneStar(val s: Word) : Word {

    override fun toString(): String {
        return "kleene($s)"
    }
}


class Concatenation(val s2: Word, val s1: Word) : Word {
    override fun toString(): String {
        return "concat($s1 , $s2)"
    }
}