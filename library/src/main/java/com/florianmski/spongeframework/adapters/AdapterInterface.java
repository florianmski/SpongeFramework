package com.florianmski.spongeframework.adapters;

import java.util.List;

public interface AdapterInterface<E>
{
    public void refresh(List<E> data);
    public void reset();
    public E getItem2(int position);
}
