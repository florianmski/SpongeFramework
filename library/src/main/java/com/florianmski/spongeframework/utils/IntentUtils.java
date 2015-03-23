package com.florianmski.spongeframework.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.ShareActionProvider;
import android.view.Menu;
import android.view.MenuItem;

import com.florianmski.spongeframework.R;

public class IntentUtils
{
    public static void goToAddress(Context context, String address)
    {
        String uri = String.format("geo:0,0?q=%s", address);
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
        context.startActivity(intent);
    }

    public static void goToCoordinate(Context context, double latitude, double longitude)
    {
        String uri = String.format("geo:%f,%f", latitude, longitude);
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
        context.startActivity(intent);
    }

    public static void goToUrl(Context context, String url)
    {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        context.startActivity(browserIntent);
    }

    public static void setupShareText(Context context, Menu menu, String text)
    {
        setupShareText(context, menu, null, text);
    }

    public static void setupShareText(final Context context, Menu menu, String subject, String text)
    {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        if(subject != null)
            intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, text);

        ShareActionProvider sap = new ShareActionProvider(context);
        sap.setShareIntent(intent);

        MenuItem shareItem = menu.add(Menu.NONE, R.id.action_bar_share, Menu.NONE, "Share");
        shareItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        MenuItemCompat.setActionProvider(shareItem, sap);
    }
}
