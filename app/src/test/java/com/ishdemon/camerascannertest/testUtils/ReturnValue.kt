package com.ishdemon.camerascannertest.testUtils

import com.ishdemon.camerascannertest.testUtils.ReturnValue.Crash
import com.ishdemon.camerascannertest.testUtils.ReturnValue.WithoutCrash
import io.mockk.MockKStubScope

sealed class ReturnValue<T> {

    class WithoutCrash<T>(val value: T) : ReturnValue<T>()

    class Crash<T>(val throwable: Throwable) : ReturnValue<T>()

}

val <T : Any?> T.withoutCrash
    get() = WithoutCrash(this)

inline fun <reified T> Throwable.asCrash(): Crash<T> {
    return Crash(this)
}

infix fun <T> MockKStubScope<T, *>.returnOrCrash(returnValue: ReturnValue<T>) {
    when (returnValue) {
        is WithoutCrash -> {
            returns(returnValue.value)
        }
        is Crash -> {
            throws(returnValue.throwable)
        }
    }
}