package cl.datageneral.dynamicforms.ui.base

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable


/**
 * Created by Pablo Molina on 04-02-2019. s.pablo.molina@gmail.com
 */
open class BasePresenter<V: BaseContract.View>: BaseContract.Presenter<V> {
    protected var mView: V?    = null
    protected var mCompositeDisposable: CompositeDisposable? =null
    //protected var q:Query? = null

    override fun subscribe() {
    }

    override fun unsubscribe() {
    }

    override fun onAttach(view: V) {
        mView = view
        mCompositeDisposable = CompositeDisposable()
    }

    override fun onDetach() {
        mCompositeDisposable?.dispose()
        mView = null
    }

    override fun addDisposable(disposable: Disposable) {
        mCompositeDisposable?.add(disposable)
    }


    /*
    override fun onAttach(mvpView: V) {
        mView = mvpView
    }
    override fun onAttach(mvpView: V, q:Query? ) {
        mView = mvpView
        this.q = q
    }

    override fun onDetach() {
        if(q!=null){
            q?.close()
        }
        //compositeDisposable.dispose()
        mView = null
    }

    fun getMvpView(): V? {
        return mView
    }*/

}
