package com.coder.seadoc.presenter

import android.util.Log
import com.coder.seadoc.model.ProjectModule
import com.coder.seadoc.presenter.attacher.DocListAttacher
import com.coder.seadoc.store.DocGroupStore
import rx.Observable
import rx.Observer
import rx.android.schedulers.AndroidSchedulers
import javax.inject.Inject

/**
 * Created by liuting on 17/6/28.
 */
class DocListPresenter
@Inject
constructor(val mView: DocListAttacher.View, val mStore: DocGroupStore) : BasePresenter() {

    fun loadList(listId: Int) {
        addSub(mStore.load()
                .flatMap {
                    obj ->
                    Observable.from(obj.classifyList)
                }
                .filter {
                    obj ->
                    obj?.id == listId
                }
                .map {
                    (projects) ->
                    var projectModules: ArrayList<ProjectModule> = ArrayList()
                    for ((projectModuleList) in projects!!)
                        projectModules.addAll(projectModuleList!!)
                    projectModules
                }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<ArrayList<ProjectModule>> {
                    override fun onCompleted() {
                        Log.e("DocListPresenter", "完成")
                    }

                    override fun onError(e: Throwable?) {
                        Log.e("DocListPresenter", e?.toString())
                    }

                    override fun onNext(t: ArrayList<ProjectModule>?) {
                        Log.e("DocListPresenter", t?.toString())
                        if (t == null) {

                        } else {
                            mView.setListView(t)
                        }
                    }
                }))
    }
}