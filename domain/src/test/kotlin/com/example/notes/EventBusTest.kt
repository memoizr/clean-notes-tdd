package com.example.notes

import org.junit.Test
import rx.observers.TestSubscriber

class EventBusTest {
    @Test
    fun `notifies observers event has been sent`() {
        val eventbus = EventBus
        val testSubscriber = TestSubscriber<EventForTest>()
        eventbus.observeEvents(EventForTest.javaClass).subscribe(testSubscriber)

        eventbus.send(EventForTest)

        testSubscriber.assertValue(EventForTest)
    }

    object EventForTest : Event
}
