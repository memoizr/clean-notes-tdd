package com.example.notes.service

import com.example.entity.Note
import rx.Observable

interface DataService {
    fun getNotes(): Observable<List<Note>>
}
