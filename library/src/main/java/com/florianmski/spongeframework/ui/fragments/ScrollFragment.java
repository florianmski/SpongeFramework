package com.florianmski.spongeframework.ui.fragments;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public abstract class ScrollFragment<E, V extends ViewGroup> extends RxFragment<E, V>
{
    protected List<GenericScrollListener<V>> scrollListeners = new ArrayList<>();
    protected GenericScrollListener<V> scrollListener = new GenericScrollListener<V>()
    {
        @Override
        public void onScrollChanged(V who, int dx, int dy)
        {
            for(GenericScrollListener<V> listener : scrollListeners)
                listener.onScrollChanged(who, dx, dy);
        }

        @Override
        public void onScrollStateChanged(V who, int newState)
        {
            for(GenericScrollListener<V> listener : scrollListeners)
                listener.onScrollStateChanged(who, newState);
        }
    };

    public ScrollFragment() {}

    protected abstract void plugScrollListenerToView(V view, GenericScrollListener<V> scrollListener);

    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);

        plugScrollListenerToView(view, scrollListener);
        addScrollListener(new GenericScrollListener<V>()
        {
            @Override
            public void onScrollChanged(V who, int dx, int dy)
            {
                View v = who.getChildAt(0);
                if(v == null)
                    return;

                if(dy > 0 && v.getTop() < 0)
                    getBaseActivity().showToolbar(false);
                else if(dy < 0)
                    getBaseActivity().showToolbar(true);
            }

            @Override
            public void onScrollStateChanged(V who, int newState) {}
        });
    }

    @Override
    public void onViewCreated(View v, Bundle savedInstanceState)
    {
        super.onViewCreated(v, savedInstanceState);

        view.setClipToPadding(false);
    }

    public void addScrollListener(GenericScrollListener<V> listener)
    {
        scrollListeners.add(listener);
    }

    public void removeScrollListener(GenericScrollListener<V> listener)
    {
        scrollListeners.remove(listener);
    }

    public interface GenericScrollListener<V>
    {
        public void onScrollChanged(V who, int dx, int dy);
        public void onScrollStateChanged(V who, int newState);
    }
}
