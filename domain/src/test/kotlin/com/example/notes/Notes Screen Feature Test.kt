package com.example.notes

import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import org.junit.Test

class `Notes Screen Feature Test` {
    val view = mock<NotesPresenter.View>()
    val presenter = NotesPresenter()

    @Test
    fun `should show an empty list when there are no items`() {
        presenter take view

        verify(view).showNotes(emptyList())
    }
}
