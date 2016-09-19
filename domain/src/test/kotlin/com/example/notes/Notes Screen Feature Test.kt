package com.example.notes

import com.example.entity.Note
import com.example.notes.event.NewNoteEvent
import com.example.notes.interactor.GetNotesInteractor
import com.example.notes.model.NoteModel
import com.example.notes.presenter.NotesPresenter
import com.example.notes.service.DataService
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import org.junit.Before
import org.junit.Test
import rx.Observable
import rx.lang.kotlin.PublishSubject
import rx.schedulers.TestScheduler

class `Notes Screen Feature Test` {
    val view = mock<NotesPresenter.View>()
    val dataService = mock<DataService>()
    val ioScheduler = TestScheduler()
    val mainScheduler = TestScheduler()
    val getNotesInteractor = GetNotesInteractor(dataService, ioScheduler, mainScheduler)
    val eventBus = EventBus
    val presenter = NotesPresenter(getNotesInteractor, eventBus)
    val noteCreationSubject = PublishSubject<Unit>()

    @Before
    fun before() {
        givenServiceReturns(emptyList())
        whenever(view.noteCreationEvents()).thenReturn(noteCreationSubject)
    }

    private fun givenServiceReturns(emptyList: List<Note>) {
        whenever(dataService.getNotes()).thenReturn(Observable.just(emptyList))
    }

    @Test
    fun `should show an empty list when there are no items`() {
        givenServiceReturns(emptyList())
        presenter take view

        verify(view).showNotes(emptyList())
    }

    @Test
    fun `should show notes list`() {
        givenServiceReturns(listOf(Note("first"), Note("second")))
        presenter take view

        verify(view).showNotes(listOf(NoteModel("first"), NoteModel("second")))
    }

    @Test
    fun `should be able to add new note`() {
        presenter take view

        createNote()

        verify(view).displayNoteCreation()
    }

    @Test
    fun `should display only new note when new note is created`() {
        presenter take view

        val content = "New note"
        notifyNewNoteCreated(Note(content))

        verify(view).showNewNote(NoteModel(content))
    }

    fun notifyNewNoteCreated(note: Note) {
        eventBus.send(NewNoteEvent(note))
    }

    fun createNote() {
        noteCreationSubject.onNext(Unit)
    }
}

