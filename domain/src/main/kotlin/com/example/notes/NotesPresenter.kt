package com.example.notes

class NotesPresenter: Presenter<NotesPresenter.View>() {
    override fun onTakeView(view: View) {
        view.showNotes(emptyList())
    }

    interface View: Presenter.View  {
        fun  showNotes(emptyList: List<PresentableNote>)
    }
}

abstract class Presenter<V: Presenter.View> {
    infix fun take(view: V) {
        onTakeView(view)
    }

    abstract protected fun onTakeView(view: V)

    interface View
}
