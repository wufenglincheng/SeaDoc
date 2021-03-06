package com.coder.seadoc.module.docgroup.core

import android.util.Log
import com.coder.seadoc.base.BasePresenter
import com.coder.seadoc.model.Classify
import com.coder.seadoc.store.DocGroupStore
import rx.Observer
import rx.android.schedulers.AndroidSchedulers
import javax.inject.Inject

/**
 * Created by liuting on 17/6/27.
 */
open class DocGroupPresenter
@Inject
constructor(val mView: DocGroupContract.View, val mStore: DocGroupStore) : BasePresenter() {
    fun load() {
        addSub(mStore.load()
                .map {
                    obj ->
                    obj.classifyList
                }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<ArrayList<Classify>?> {
                    override fun onCompleted() {
                        Log.e("DocGroupPresenter", "完成")
                    }

                    override fun onError(e: Throwable?) {
                        Log.e("DocGroupPresenter", e?.toString())
                    }

                    override fun onNext(t: ArrayList<Classify>?) {
                        Log.e("DocGroupPresenter", t?.toString())
                        if (t == null) {

                        } else {
                            mView.setGroupData(t)
                        }
                    }
                }))
    }
}