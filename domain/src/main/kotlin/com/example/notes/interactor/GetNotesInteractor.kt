package com.example.notes.interactor

import com.example.entity.Note
import com.example.notes.service.DataService
import rx.Observable
import rx.Scheduler

class GetNotesInteractor(private val dataService: DataService,
                         ioScheduler: Scheduler,
                         mainScheduler: Scheduler) : AbstractInteractor<List<Note>>(ioScheduler, mainScheduler) {

    override fun action(): Observable<List<Note>> {
        return dataService.getNotes()
    }
}

abstract class AbstractInteractor<T>(
        private val ioScheduler: Scheduler,
        private val mainScheduler: Scheduler) {

    fun execute(): Observable<T> = action().subscribeOn(ioScheduler).observeOn(mainScheduler)

    protected abstract fun action(): Observable<T>
}
