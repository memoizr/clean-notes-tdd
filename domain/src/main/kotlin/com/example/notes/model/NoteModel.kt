package com.example.notes.model

import com.example.entity.Note

data class NoteModel(val content: String) {
    companion object {
        fun fromNote(note: Note) : NoteModel {
            return NoteModel(note.content)
        }
    }
}
