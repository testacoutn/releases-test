package com.kidoz.mediation.admob.adapters;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.mediation.MediationAdRequest;
import com.google.android.gms.ads.reward.mediation.MediationRewardedVideoAdAdapter;
import com.google.android.gms.ads.reward.mediation.MediationRewardedVideoAdListener;
import com.kidoz.sdk.api.KidozInterstitial;
import com.kidoz.sdk.api.interfaces.SDKEventListener;
import com.kidoz.sdk.api.ui_views.interstitial.BaseInterstitial;


public class KidozAdMobMediationRewardedLegacyAdapter implements MediationRewardedVideoAdAdapter
{
    private static final String TAG = "KidozAdMobMediationRewardedLegacyAdapter";

    private KidozManager mKidozManager;
    private MediationRewardedVideoAdListener mMediationRewardedVideoAdListener;
    private boolean mInitializedState;

    public KidozAdMobMediationRewardedLegacyAdapter() {
        mKidozManager = KidozManager.getInstance();
        mInitializedState = false;
    }

    @Override
    public void initialize(Context context, MediationAdRequest mediationAdRequest, String unused, MediationRewardedVideoAdListener mediationRewardedVideoAdListener, Bundle serverParameters, Bundle networkExtras)
    {
        mMediationRewardedVideoAdListener = mediationRewardedVideoAdListener;
        //Kidoz requires Activity context to run.
        if (!(context instanceof Activity)){
            Log.d(TAG, "Kidoz | requestInterstitialAd with non Activity context");
            mMediationRewardedVideoAdListener.onAdFailedToLoad(this, AdRequest.ERROR_CODE_INVALID_REQUEST);
            return;
        }

        if(mKidozManager.getRewarded()==null)
            mKidozManager.createKidozRewadrded((Activity) context);

        setKidozAd();

        //Kidoz must be initialized before an ad can be requested
        if (!mKidozManager.getIsKidozInitialized()) {

            if(serverParameters!=null) {
                String parameter = serverParameters.getString(MediationRewardedVideoAdAdapter.CUSTOM_EVENT_SERVER_PARAMETER_FIELD);
                String appID = mKidozManager.getPublisherIdFromParams(parameter);
                String token = mKidozManager.getPublisherTokenFromParams(parameter);

                if(appID!=null && token!=null && !appID.equals("") && !token.equals("")) {
                    mKidozManager.setKidozPublisherId(appID);
                    mKidozManager.setKidozPublisherToken(token);
                    initKidoz((Activity) context);
                }
            }

        } else {

            mInitializedState = true;
            mMediationRewardedVideoAdListener.onInitializationSucceeded(KidozAdMobMediationRewardedLegacyAdapter.this);}

    }

    private void setKidozAd(Activity activity)
    {
        mKidozManager.setupKidozRewadrded(activity, new BaseInterstitial.IOnInterstitialEventListener()
                {
                    @Override
                    public void onClosed()
                    {
                        mMediationRewardedVideoAdListener.onAdClosed(KidozAdMobMediationRewardedLegacyAdapter.this);
                        Log.d(TAG, "KidozRewardedAdapter | onAdClosed");
                    }

                    @Override
                    public void onOpened()
                    {
                        mMediationRewardedVideoAdListener.onAdOpened(KidozAdMobMediationRewardedLegacyAdapter.this);
                        Log.d(TAG, "KidozRewardedAdapter | onAdOpened");
                    }

                    @Override
                    public void onReady()
                    {
                        mMediationRewardedVideoAdListener.onAdLoaded(KidozAdMobMediationRewardedLegacyAdapter.this);
                        Log.d(TAG, "KidozRewardedAdapter | onAdReady");
                    }

                    @Override
                    public void onLoadFailed()
                    {
                        mMediationRewardedVideoAdListener.onAdFailedToLoad(KidozAdMobMediationRewardedLegacyAdapter.this, AdRequest.ERROR_CODE_INTERNAL_ERROR);
                        Log.d(TAG, "KidozRewardedAdapter | onLoadFailed");
                    }

                    @Override
                    public void onNoOffers()
                    {
                        mMediationRewardedVideoAdListener.onAdFailedToLoad(KidozAdMobMediationRewardedLegacyAdapter.this, AdRequest.ERROR_CODE_NO_FILL);
                        Log.d(TAG, "KidozRewardedAdapter | onNoOffers");
                    }
                },
                new BaseInterstitial.IOnInterstitialRewardedEventListener()
                {
                    @Override
                    public void onRewardReceived()
                    {
                        BaseInterstitial.IOnInterstitialRewardedEventListener devListener = mKidozManager.getDeveloperRewardedListener();
                        if (devListener != null){
                            devListener.onRewardReceived();
                        }

                        //Note: Kidoz currently have no server to client reward exposure.
                        mMediationRewardedVideoAdListener.onRewarded(KidozAdMobMediationRewardedLegacyAdapter.this, new KidozAdMobRewardItem());
                        Log.d(TAG, "KidozRewardedAdapter | onRewardReceived");
                    }

                    @Override
                    public void onRewardedStarted()
                    {
                        BaseInterstitial.IOnInterstitialRewardedEventListener devListener = mKidozManager.getDeveloperRewardedListener();
                        if (devListener != null){
                            devListener.onRewardedStarted();
                        }

                        mMediationRewardedVideoAdListener.onVideoStarted(KidozAdMobMediationRewardedLegacyAdapter.this);
                        Log.d(TAG, "KidozRewardedAdapter | onRewardedStarted");
                    }
                });
    }

    private void setKidozAd()
    {
        mKidozManager.setupKidozRewadrded(mKidozManager.getRewarded(), new BaseInterstitial.IOnInterstitialEventListener()
                {
                    @Override
                    public void onClosed()
                    {
                        mMediationRewardedVideoAdListener.onAdClosed(KidozAdMobMediationRewardedLegacyAdapter.this);
                        Log.d(TAG, "KidozRewardedAdapter | onAdClosed");
                    }

                    @Override
                    public void onOpened()
                    {
                        mMediationRewardedVideoAdListener.onAdOpened(KidozAdMobMediationRewardedLegacyAdapter.this);
                        Log.d(TAG, "KidozRewardedAdapter | onAdOpened");
                    }

                    @Override
                    public void onReady()
                    {
                        mMediationRewardedVideoAdListener.onAdLoaded(KidozAdMobMediationRewardedLegacyAdapter.this);
                        Log.d(TAG, "KidozRewardedAdapter | onAdReady");
                    }

                    @Override
                    public void onLoadFailed()
                    {
                        mMediationRewardedVideoAdListener.onAdFailedToLoad(KidozAdMobMediationRewardedLegacyAdapter.this, AdRequest.ERROR_CODE_INTERNAL_ERROR);
                        Log.d(TAG, "KidozRewardedAdapter | onLoadFailed");
                    }

                    @Override
                    public void onNoOffers()
                    {
                        mMediationRewardedVideoAdListener.onAdFailedToLoad(KidozAdMobMediationRewardedLegacyAdapter.this, AdRequest.ERROR_CODE_NO_FILL);
                        Log.d(TAG, "KidozRewardedAdapter | onNoOffers");
                    }
                },
                new BaseInterstitial.IOnInterstitialRewardedEventListener()
                {
                    @Override
                    public void onRewardReceived()
                    {
                        BaseInterstitial.IOnInterstitialRewardedEventListener devListener = mKidozManager.getDeveloperRewardedListener();
                        if (devListener != null){
                            devListener.onRewardReceived();
                        }

                        //Note: Kidoz currently have no server to client reward exposure.
                        mMediationRewardedVideoAdListener.onRewarded(KidozAdMobMediationRewardedLegacyAdapter.this, new KidozAdMobRewardItem());
                        Log.d(TAG, "KidozRewardedAdapter | onRewardReceived");
                    }

                    @Override
                    public void onRewardedStarted()
                    {
                        BaseInterstitial.IOnInterstitialRewardedEventListener devListener = mKidozManager.getDeveloperRewardedListener();
                        if (devListener != null){
                            devListener.onRewardedStarted();
                        }

                        mMediationRewardedVideoAdListener.onVideoStarted(KidozAdMobMediationRewardedLegacyAdapter.this);
                        Log.d(TAG, "KidozRewardedAdapter | onRewardedStarted");
                    }
                });
    }

    private void initKidoz(Activity activity)
    {
        mKidozManager.initKidozSDK(activity, new SDKEventListener()
        {
            @Override
            public void onInitSuccess()
            {
                mInitializedState = false;
                mMediationRewardedVideoAdListener.onInitializationSucceeded(KidozAdMobMediationRewardedLegacyAdapter.this);
                Log.d(TAG, "KidozRewardedAdapter | onInitSuccess");
            }

            @Override
            public void onInitError(String error)
            {
                mInitializedState = false;
                mMediationRewardedVideoAdListener.onInitializationFailed(KidozAdMobMediationRewardedLegacyAdapter.this, AdRequest.ERROR_CODE_INTERNAL_ERROR);
                Log.d(TAG, "KidozRewardedAdapter | onInitError: " + error);

            }
        });
    }

    @Override
    public void loadAd(MediationAdRequest mediationAdRequest, Bundle bundle, Bundle bundle1)
    {
        KidozInterstitial kidozInterstitial = mKidozManager.getRewarded();
        if(!kidozInterstitial.isLoaded())
            kidozInterstitial.loadAd();
        else
            mMediationRewardedVideoAdListener.onAdLoaded(KidozAdMobMediationRewardedLegacyAdapter.this);
    }

    @Override
    public void showVideo()
    {
        KidozInterstitial kidozInterstitial = mKidozManager.getRewarded();
        kidozInterstitial.show();
    }

    @Override
    public boolean isInitialized()
    {
        return mInitializedState;
    }

    @Override
    public void onDestroy()
    {
        Log.d(TAG, "KidozRewardedAdapter | onDestroy");
    }

    @Override
    public void onPause()
    {
        Log.d(TAG, "KidozRewardedAdapter | onPause");

    }

    @Override
    public void onResume() {
        Log.d(TAG, "KidozRewardedAdapter | onResume");

    }

}
