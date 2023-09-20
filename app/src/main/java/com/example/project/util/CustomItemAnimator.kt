package com.example.project.util

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.RecyclerView

class CustomItemAnimator : DefaultItemAnimator() {

    override fun animateAdd(holder: RecyclerView.ViewHolder): Boolean {
        val scaleX = ObjectAnimator.ofFloat(holder.itemView, "scaleX", 0.5f, 1.0f)
        val scaleY = ObjectAnimator.ofFloat(holder.itemView, "scaleY", 0.5f, 1.0f)
        val alpha = ObjectAnimator.ofFloat(holder.itemView, "alpha", 0f, 1f)

        val set = AnimatorSet()
        set.playTogether(scaleX, scaleY, alpha)
        set.duration = 300 // Adjust the duration as needed

        set.addListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator) {}

            override fun onAnimationEnd(animation: Animator) {
                dispatchAddFinished(holder)
            }

            override fun onAnimationCancel(animation: Animator) {}

            override fun onAnimationRepeat(animation: Animator) {}
        })

        set.start()

        return false
    }

    // Implement similar methods for animateRemove, animateMove, and animateChange as needed
}
