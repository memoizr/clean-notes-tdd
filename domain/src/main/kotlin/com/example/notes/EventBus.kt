package com.example.notes

import rx.Observable
import rx.lang.kotlin.PublishSubject
import kotlin.reflect.KClass

object EventBus {
    val subject = PublishSubject<Event>()

    fun send(event: Event) {
        subject.onNext(event)
    }

    fun <T: Event> observeEvents(eventType: KClass<T>): Observable<T> {
        return observeEvents(eventType.java)
    }
    fun <T: Event> observeEvents(eventType: Class<T>): Observable<T> {
        return subject.ofType(eventType)
    }
}

interface Event
