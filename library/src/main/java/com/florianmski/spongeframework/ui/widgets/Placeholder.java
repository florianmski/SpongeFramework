package com.florianmski.spongeframework.ui.widgets;

import android.graphics.drawable.ColorDrawable;

import com.florianmski.spongeframework.utils.ColorUtils;

import java.util.Random;

public class Placeholder
{
    private final static Random r = new Random();

    private int color;
    private int colorDark;

    public Placeholder(int color, int colorDark)
    {
        this.color = color;
        this.colorDark = colorDark;
    }

    public ColorDrawable getDrawable()
    {
        return new ColorDrawable(ColorUtils.interpolateColor(color, colorDark, r.nextFloat()));
    }
}