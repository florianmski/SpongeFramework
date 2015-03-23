package com.florianmski.spongeframework.utils;

import android.content.Context;
import android.content.res.TypedArray;

import com.florianmski.spongeframework.R;

public class ToolbarUtils
{
    public static int getHeight(Context context)
    {
        final TypedArray styledAttributes = context.getTheme().obtainStyledAttributes(new int[]{R.attr.actionBarSize});
        int height = styledAttributes.getDimensionPixelSize(0, 0);
        styledAttributes.recycle();
        return height;
    }
}
