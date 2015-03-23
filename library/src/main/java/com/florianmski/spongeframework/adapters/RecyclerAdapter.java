package com.florianmski.spongeframework.adapters;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

import java.util.ArrayList;
import java.util.List;

public abstract class RecyclerAdapter<E, VH extends RecyclerAdapter.ViewHolder> extends RecyclerView.Adapter<VH> implements AdapterInterface<E>
{
    protected Context context;
    protected List<E> data;
    protected OnItemClickListener listener;

    public RecyclerAdapter(Context context, List<E> data, OnItemClickListener listener)
    {
        this.context = context;
        this.data = data;
        this.listener = listener;
    }

    public RecyclerAdapter(Context context, List<E> data)
    {
        this(context, data, null);
    }

    @Override
    public int getItemCount()
    {
        return data.size();
    }

    @Override
    public void refresh(List<E> data)
    {
        this.data = data;
        notifyDataSetChanged();
    }

    @Override
    public void reset()
    {
        this.data = new ArrayList<>();
        notifyDataSetChanged();
    }

    @Override
    public E getItem2(int position)
    {
        return data.get(position);
    }

    // based on http://frogermcs.github.io/Instagram-with-Material-Design-concept-part-2-Comments-transition/
    private int lastAnimatedPosition = -1;
    private boolean animationsLocked = false;
    private boolean delayEnterAnimation = true;

    private void runEnterAnimation(View view, int position)
    {
        if (animationsLocked)
            return;

        if (position > lastAnimatedPosition)
        {
            lastAnimatedPosition = position;
            view.setTranslationY(100);
            view.setAlpha(0.f);
            view.animate()
                    .translationY(0).alpha(1.f)
                    .setStartDelay(300 + (delayEnterAnimation ? 300 * (position) : 0))
                    .setInterpolator(new DecelerateInterpolator(2.f))
                    .setDuration(300)
                    .setListener(new AnimatorListenerAdapter()
                    {
                        @Override
                        public void onAnimationEnd(Animator animation)
                        {
                            animationsLocked = true;
                        }
                    })
                    .start();
        }
    }

    public void setAnimationsLocked(boolean animationsLocked)
    {
        this.animationsLocked = animationsLocked;
    }

    public void setDelayEnterAnimation(boolean delayEnterAnimation)
    {
        this.delayEnterAnimation = delayEnterAnimation;
    }

    public interface OnItemClickListener
    {
        public void onItemClick(View v, int position);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        public ViewHolder(final View itemView, final OnItemClickListener listener)
        {
            super(itemView);

            if(listener != null)
                itemView.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        listener.onItemClick(itemView, getPosition());
                    }
                });
        }

        public ViewHolder(final View itemView)
        {
            this(itemView, null);
        }
    }
}
