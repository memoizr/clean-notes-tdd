package com.example.notes.interactor

import com.memoizr.assertk.expect
import org.junit.Test
import rx.Observable
import rx.lang.kotlin.BehaviourSubject
import rx.observers.TestSubscriber
import rx.schedulers.TestScheduler
import java.util.concurrent.atomic.AtomicInteger

class AbstractInteractorTest {
    val ioScheduler = TestScheduler()
    val mainScheduler = TestScheduler()
    val onSubscribeCounter = AtomicInteger()
    val testSubject = BehaviourSubject("excellent")
    val interactorForTest = InteractorForTest(
            testSubject.doOnNext { onSubscribeCounter.incrementAndGet() },
            ioScheduler,
            mainScheduler)

    @Test
    fun `executes action on ioScheduler and observes on mainScheduler`() {
        val testSubscriber = TestSubscriber<String>()
        val observeActionCounter = AtomicInteger()

        interactorForTest.execute()
                .doOnNext { observeActionCounter.incrementAndGet() }
                .subscribe(testSubscriber)

        testSubscriber.assertNoValues()
        onSubscribeCounter.get()
        expect that onSubscribeCounter.get() isEqualTo 0
        expect that observeActionCounter.get() isEqualTo 0

        ioScheduler.triggerActions()
        expect that onSubscribeCounter.get() isEqualTo 1
        expect that observeActionCounter.get() isEqualTo 0
        testSubscriber.assertNoValues()

        mainScheduler.triggerActions()
        expect that observeActionCounter.get() isEqualTo 1

        testSubscriber.assertValue("excellent")
    }
}

class InteractorForTest(
        val observable: Observable<String>,
        ioScheduler: TestScheduler,
        mainScheduler: TestScheduler) : AbstractInteractor<String>(ioScheduler, mainScheduler) {
    override fun action(): Observable<String> {
        return observable
    }
}
