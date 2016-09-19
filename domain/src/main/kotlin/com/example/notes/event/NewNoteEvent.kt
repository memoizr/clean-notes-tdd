package com.example.notes.event

import com.example.entity.Note
import com.example.notes.Event

class NewNoteEvent(val note: Note) : Event

