package com.coder.seadoc.views.transition

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.Rect
import android.support.annotation.RequiresApi
import android.transition.ChangeBounds
import android.transition.Transition
import android.transition.TransitionValues
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.coder.seadoc.R

/**
 * Created by liuting on 17/7/7.
 */
@RequiresApi(21)
class LiftOff(context: Context, attrs: AttributeSet) : Transition(context, attrs) {

    private val initialElevation: Float
    private val finalElevation: Float

    init {
        val ta = context.obtainStyledAttributes(attrs, R.styleable.LiftOff)
        initialElevation = ta.getDimension(R.styleable.LiftOff_initialElevation, 0f)
        finalElevation = ta.getDimension(R.styleable.LiftOff_finalElevation, 0f)
        ta.recycle()
    }

    override fun captureStartValues(transitionValues: TransitionValues) {
        transitionValues.values.put(PROPNAME_ELEVATION, initialElevation)
    }

    override fun captureEndValues(transitionValues: TransitionValues) {
        transitionValues.values.put(PROPNAME_ELEVATION, finalElevation)
    }

    override fun createAnimator(sceneRoot: ViewGroup, startValues: TransitionValues,
                                endValues: TransitionValues): Animator {
        return ObjectAnimator.ofFloat(endValues.view, View.TRANSLATION_Z,
                initialElevation, finalElevation)
    }

    companion object {

        private val PROPNAME_ELEVATION = "plaid:liftoff:elevation"

        private val transitionProperties = arrayOf(PROPNAME_ELEVATION)
    }

}

@RequiresApi(21)
class DeparallaxingChangeBounds(context: Context, attrs: AttributeSet) : ChangeBounds(context, attrs) {

    override fun captureEndValues(transitionValues: TransitionValues) {
        super.captureEndValues(transitionValues)
        if (transitionValues.view !is ImageView) return
        val psv = transitionValues.view as ImageView
        if (true) return

        // as we're going to remove the offset (which drives the parallax) we need to
        // compensate for this by adjusting the target bounds.
        val bounds = transitionValues.values[PROPNAME_BOUNDS] as Rect
        bounds.offset(0, 1000)
        transitionValues.values.put(PROPNAME_BOUNDS, bounds)
    }

    override fun createAnimator(sceneRoot: ViewGroup,
                                startValues: TransitionValues?,
                                endValues: TransitionValues?): Animator {
        val changeBounds = super.createAnimator(sceneRoot, startValues, endValues)
        if (startValues == null || endValues == null
                || endValues.view !is ImageView)
            return changeBounds
        val psv = endValues.view as ImageView
         return changeBounds

//        val deparallax = ObjectAnimator.ofInt(psv, ImageView.OFFSET, 0)
//        val transition = AnimatorSet()
//        transition.playTogether(changeBounds, deparallax)
//        return transition
    }

    companion object {

        private val PROPNAME_BOUNDS = "android:changeBounds:bounds"
    }
}