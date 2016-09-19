package com.example.notes.presenter

import com.example.notes.EventBus
import com.example.notes.interactor.GetNotesInteractor
import com.example.notes.model.NoteModel
import rx.Observable

class NotesPresenter(
        private val getNotes: GetNotesInteractor,
        private val eventBus: EventBus) {

    infix fun take(view: View) {
        getNotes.execute()
                .map { notes ->
                    notes.map { NoteModel.fromNote(it) }
                }
                .subscribe {
                    view.showNotes(it)
                }
    }

    interface View {
        fun noteCreationEvents(): Observable<Unit>

        fun showNotes(emptyList: List<NoteModel>)
        fun showNewNote(noteModel: NoteModel)
        fun displayNoteCreation()
    }
}
