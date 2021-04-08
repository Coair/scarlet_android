package com.coair.base.utli

class PartialFunction<in P1, out R>(
    private val definetAt: (P1) -> Boolean,
    private val f: (P1) -> R
) : (P1) -> R {
    override fun invoke(p1: P1): R {
        if (definetAt(p1)) {
            return f(p1)
        } else {
            throw IllegalArgumentException("参数 $p1 不合适")
        }
    }

    fun isDefinetAt(p1: P1) = definetAt(p1)
}


infix fun <P1, R> PartialFunction<P1, R>.onElse(that: PartialFunction<P1, R>): PartialFunction<P1, R> {
    return PartialFunction({
        this.isDefinetAt(it) || that.isDefinetAt(
            it
        )
    }) {
        when {
            this.isDefinetAt(it) -> this(it)
            else -> that(it)
        }
    }
}