package com.coder.seadoc.base

import rx.Subscription
import rx.subscriptions.CompositeSubscription

/**
 * Created by liuting on 17/6/28.
 */
open class BasePresenter {
    private var compositeSubscription = CompositeSubscription()

    fun addSub(sub: Subscription) {
        compositeSubscription.add(sub)
    }

    fun unSub() {
        compositeSubscription.unsubscribe()
    }
}