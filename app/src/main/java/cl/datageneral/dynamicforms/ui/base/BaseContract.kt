package cl.datageneral.dynamicforms.ui.base

import io.reactivex.disposables.Disposable

/**
 * Created by Pablo Molina on 04-02-2019. s.pablo.molina@gmail.com
 */
class BaseContract {

    interface Presenter<V: View> {
        fun subscribe()
        fun unsubscribe()
        fun onAttach(view: V)
        fun onDetach()
        fun addDisposable(disposable: Disposable)
    }

    interface View
}