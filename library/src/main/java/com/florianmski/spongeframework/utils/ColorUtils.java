package com.florianmski.spongeframework.utils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.util.TypedValue;

public class ColorUtils
{
    public static int fromAttribute(Context context, int attr)
    {
        TypedValue typedValue = new TypedValue();
        Resources.Theme theme = context.getTheme();
        theme.resolveAttribute(attr, typedValue, true);
        return typedValue.data;
    }

    private static float interpolate(float a, float b, float proportion)
    {
        return (a + ((b - a) * proportion));
    }

    // from http://stackoverflow.com/a/7871291 and found this idea in the EyeEm app
    public static int interpolateColor(int a, int b, float proportion)
    {
        float[] hsva = new float[3];
        float[] hsvb = new float[3];
        Color.colorToHSV(a, hsva);
        Color.colorToHSV(b, hsvb);
        for (int i = 0; i < 3; i++)
            hsvb[i] = interpolate(hsva[i], hsvb[i], proportion);
        return Color.HSVToColor(hsvb);
    }
}
