package com.florianmski.spongeframework;

import android.app.Application;
import android.os.StrictMode;

import com.crashlytics.android.Crashlytics;
import com.squareup.picasso.Picasso;

import io.fabric.sdk.android.Fabric;
import timber.log.Timber;

public class SpongeApplication extends Application
{
    @Override
    public void onCreate()
    {
        super.onCreate();

        if(BuildConfig.DEBUG)
            onDebug();
        else
            onRelease();
    }

    protected void onDebug()
    {
        Picasso picassoInstance = new Picasso.Builder(this).indicatorsEnabled(true).build();
        Picasso.setSingletonInstance(picassoInstance);
        Timber.plant(new Timber.DebugTree());
        setupStrictMode();
    }

    protected void onRelease()
    {
        Timber.plant(new CrashReportingTree());
        Fabric.with(this, new Crashlytics());
    }

    protected void setupStrictMode()
    {
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .detectNetwork()
                .penaltyLog()
                .build());
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                .detectLeakedSqlLiteObjects()
                .detectLeakedClosableObjects()
                .penaltyLog()
                .build());
    }

    private static class CrashReportingTree extends Timber.HollowTree
    {
        @Override
        public void i(String message, Object... args)
        {
            Crashlytics.log(String.format(message, args));
        }

        @Override
        public void i(Throwable t, String message, Object... args)
        {
            i(message, args); // Just add to the log.
        }

        @Override
        public void e(String message, Object... args)
        {
            i("ERROR: " + message, args); // Just add to the log.
        }

        @Override
        public void e(Throwable t, String message, Object... args)
        {
            e(message, args);
            Crashlytics.logException(t);
        }
    }
}
