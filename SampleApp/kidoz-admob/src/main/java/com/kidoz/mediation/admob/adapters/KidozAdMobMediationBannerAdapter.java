package com.kidoz.mediation.admob.adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.mediation.MediationAdRequest;
import com.google.android.gms.ads.mediation.customevent.CustomEventBanner;
import com.google.android.gms.ads.mediation.customevent.CustomEventBannerListener;
import com.kidoz.sdk.api.interfaces.SDKEventListener;
import com.kidoz.sdk.api.ui_views.kidoz_banner.KidozBannerListener;
import com.kidoz.sdk.api.ui_views.new_kidoz_banner.BANNER_POSITION;
import com.kidoz.sdk.api.ui_views.new_kidoz_banner.KidozBannerView;

/**
 * Created by orikam on 07/06/2017.
 */

public class KidozAdMobMediationBannerAdapter implements CustomEventBanner
{
    private static final String TAG = "KidozAdMobMediationBannerAdapter";
    private static final BANNER_POSITION DEFAULT_BANNER_POSITION = BANNER_POSITION.BOTTOM;

    private CustomEventBannerListener mCustomEventBannerListener;
    private KidozManager mKidozManager;

    public KidozAdMobMediationBannerAdapter()
    {
        mKidozManager = new KidozManager();
    }

    @Override
    public void requestBannerAd(Context context, CustomEventBannerListener customEventBannerListener, String serverParameter, AdSize adSize, MediationAdRequest mediationAdRequest, Bundle bundle)
    {
        mCustomEventBannerListener = customEventBannerListener;

        //Kidoz requires Activity context to run.
        if (!(context instanceof Activity)){
            mCustomEventBannerListener.onAdFailedToLoad(AdRequest.ERROR_CODE_INVALID_REQUEST);
            Log.d(TAG, "Kidoz | requestBannerAd with non Activity context");
            return;
        }

        Log.d(TAG, "KidozBannerAdapter | requestBannerAd called");

        //Kidoz must be initialized before an ad can be requested
        if (!mKidozManager.getIsKidozInitialized())
        {

            String appID = mKidozManager.getPublisherIdFromParams(serverParameter);
            String token = mKidozManager.getPublisherTokenFromParams(serverParameter);

            if(appID!=null && token!=null && !appID.equals("") && !token.equals("")) {
                mKidozManager.setKidozPublisherId(appID);
                mKidozManager.setKidozPublisherToken(token);
                Log.d(TAG, "KidozBannerAdapter | kidoz not init, initializing first");
                initKidoz((Activity) context);
            }


        } else {
            Log.d(TAG, "KidozBannerAdapter | kidoz already init");
            continueRequestBannerAd((Activity) context);
        }
    }

    private void initKidoz(final Activity activity)
    {
        mKidozManager.initKidozSDK(activity, new SDKEventListener()
        {
            @Override
            public void onInitSuccess()
            {
                continueRequestBannerAd(activity);
            }

            @Override
            public void onInitError(String error)
            {
                mCustomEventBannerListener.onAdFailedToLoad(AdRequest.ERROR_CODE_INTERNAL_ERROR);
                Log.d(TAG, "Kidoz | onInitError: " + error);
            }
        });
    }

    private void continueRequestBannerAd(Activity activity)
    {
        Log.d(TAG, "KidozBannerAdapter | kidoz continueRequestBannerAd");
        if (mKidozManager.getBanner() == null)
        {
            Log.d(TAG, "kidozBannerAdapter | banner not set up, calling view load.");
            setupKidozBanner(activity);
        }

        Log.d(TAG, "KidozBannerAdapter | continueRequestBannerAd | calling load()");
        mKidozManager.getBanner().setLayoutWithoutShowing();
        mKidozManager.getBanner().load();
    }

    private void setupKidozBanner(final Activity activity)
    {
        Log.d(TAG, "kidozBannerAdapter | kidozBannerView == null, calling view creation. START");
        mKidozManager.setupKidozBanner(activity, DEFAULT_BANNER_POSITION, new KidozBannerListener()
        {
            @Override
            public void onBannerReady()
            {
                //ask banner not to insert itself to view hierarchy, admob will do that in onAdLoaded(...)
                final KidozBannerView kbv = mKidozManager.getBanner();
                kbv.setBackgroundColor(Color.TRANSPARENT);

                kbv.show();
                Log.d(TAG, "kidozBannerAdapter | onBannerReady");
            }

            @Override
            public void onBannerViewAdded()
            {
                mCustomEventBannerListener.onAdLoaded(mKidozManager.getBanner());
                mCustomEventBannerListener.onAdOpened();
                Log.d(TAG, "kidozBannerAdapter | onBannerViewAdded");
            }

            @Override
            public void onBannerError(String errorMsg)
            {
                Log.e(TAG, "onBannerError: " + errorMsg);
                mCustomEventBannerListener.onAdFailedToLoad(AdRequest.ERROR_CODE_NETWORK_ERROR);
            }

            @Override
            public void onBannerClose()
            {
                mCustomEventBannerListener.onAdClosed();
                Log.d(TAG, "kidozBannerAdapter | onBannerClose");
            }
        });
        Log.d(TAG, "kidozBannerAdapter | kidozBannerView == null, calling view creation. END");
    }

    @Override
    public void onDestroy()
    {
        mKidozManager.getBanner().hide();
        Log.d(TAG, "kidozBannerAdapter | onDestroy");
    }

    @Override
    public void onPause()
    {
        Log.d(TAG, "kidozBannerAdapter | onPause");
    }

    @Override
    public void onResume()
    {
        Log.d(TAG, "kidozBannerAdapter | onResume");
    }

}
