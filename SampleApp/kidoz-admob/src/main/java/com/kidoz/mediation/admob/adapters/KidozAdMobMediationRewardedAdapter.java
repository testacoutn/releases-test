package com.kidoz.mediation.admob.adapters;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.ads.AdFormat;
import com.google.android.gms.ads.mediation.Adapter;
import com.google.android.gms.ads.mediation.InitializationCompleteCallback;
import com.google.android.gms.ads.mediation.MediationAdLoadCallback;
import com.google.android.gms.ads.mediation.MediationConfiguration;
import com.google.android.gms.ads.mediation.MediationRewardedAd;
import com.google.android.gms.ads.mediation.MediationRewardedAdCallback;
import com.google.android.gms.ads.mediation.MediationRewardedAdConfiguration;
import com.google.android.gms.ads.mediation.VersionInfo;
import com.google.android.gms.ads.reward.mediation.MediationRewardedVideoAdAdapter;
import com.google.android.gms.ads.rewarded.RewardItem;
import com.kidoz.sdk.api.KidozInterstitial;
import com.kidoz.sdk.api.KidozSDK;
import com.kidoz.sdk.api.interfaces.SDKEventListener;
import com.kidoz.sdk.api.ui_views.interstitial.BaseInterstitial;

import java.util.List;

import kidoz.kidozadmobplugin.BuildConfig;

/**
 * Created by orikam on 07/06/2017.
 */

public class KidozAdMobMediationRewardedAdapter extends Adapter implements MediationRewardedAd {
    private static final String TAG = "KidozAdMobMediationRewardedAdapter";

    private KidozManager mKidozManager;
    private boolean mInitializedState;
    private MediationAdLoadCallback<MediationRewardedAd, MediationRewardedAdCallback> mAdLoadCallback;
    private MediationRewardedAdCallback mRewardedAdCallback;

    public KidozAdMobMediationRewardedAdapter() {
        mKidozManager = KidozManager.getInstance();
        mInitializedState = false;
    }

    @Override
    public void initialize(Context context, InitializationCompleteCallback initializationCompleteCallback, List<MediationConfiguration> mediationConfigurations) {
        //Kidoz requires Activity context to run.
        if (!(context instanceof Activity)) {
            Log.d(TAG, "Kidoz | requestInterstitialAd with non Activity context");
            initializationCompleteCallback.onInitializationFailed("Kidoz | requestRewardedAd with non Activity context");
            return;
        }

        if (!mKidozManager.getIsKidozInitialized()) {

            Bundle serverParameters = null;
            for (MediationConfiguration configuration : mediationConfigurations) {
                if (configuration.getFormat() == AdFormat.REWARDED) {
                    serverParameters = configuration.getServerParameters();
                }
            }

            if (serverParameters != null) {
                String parameter = serverParameters.getString(MediationRewardedVideoAdAdapter.CUSTOM_EVENT_SERVER_PARAMETER_FIELD);
                String appID = mKidozManager.getPublisherIdFromParams(parameter);
                String token = mKidozManager.getPublisherTokenFromParams(parameter);

                if (appID != null && token != null && !appID.equals("") && !token.equals("")) {
                    mKidozManager.setKidozPublisherId(appID);
                    mKidozManager.setKidozPublisherToken(token);
                    initKidoz((Activity) context);
                }
            }
        } else {
            continueRequestRewardedAd((Activity) context);
        }

    }

    @Override
    public void loadRewardedAd(MediationRewardedAdConfiguration adConfiguration, MediationAdLoadCallback<MediationRewardedAd, MediationRewardedAdCallback> mediationAdLoadCallback) {

       Context context = adConfiguration.getContext();
        mAdLoadCallback = mediationAdLoadCallback;

        if (!(context instanceof Activity)) {
            Log.d(TAG, "Kidoz | requestInterstitialAd with non Activity context");
            mediationAdLoadCallback.onFailure("Kidoz | requestRewardedAd with non Activity context");
            return;
        }

        if (!mKidozManager.getIsKidozInitialized()) {

            Bundle serverParameters = adConfiguration.getServerParameters();

            if (serverParameters != null) {
                String parameter = serverParameters.getString(MediationRewardedVideoAdAdapter.CUSTOM_EVENT_SERVER_PARAMETER_FIELD);
                String appID = mKidozManager.getPublisherIdFromParams(parameter);
                String token = mKidozManager.getPublisherTokenFromParams(parameter);

                if (appID != null && token != null && !appID.equals("") && !token.equals("")) {
                    mKidozManager.setKidozPublisherId(appID);
                    mKidozManager.setKidozPublisherToken(token);
                    initKidoz((Activity) context);
                }
            }
        } else {
            continueRequestRewardedAd((Activity)adConfiguration.getContext());
        }
    }



    private void continueRequestRewardedAd(Activity activity){

        if (mKidozManager.getRewarded() == null)
            mKidozManager.createKidozRewadrded(activity);

        setKidozAd();

        KidozInterstitial kidozInterstitial = mKidozManager.getRewarded();
        if (!kidozInterstitial.isLoaded())
            kidozInterstitial.loadAd();
        else{
            mRewardedAdCallback = mAdLoadCallback.onSuccess(KidozAdMobMediationRewardedAdapter.this);
        }
    }

    @Override
    public VersionInfo getVersionInfo() {
        String versionString = BuildConfig.VERSION_NAME;
        String splits[] = versionString.split("\\.");
        if (splits.length >= 4) {
            int major = Integer.parseInt(splits[0]);
            int minor = Integer.parseInt(splits[1]);
            int micro = Integer.parseInt(splits[2]) * 100 + Integer.parseInt(splits[3]);
            return new VersionInfo(major, minor, micro);
        }

        String logMessage = String.format("Unexpected adapter version format: %s." +
                "Returning 0.0.0 for adapter version.", versionString);
        Log.w(TAG, logMessage);
        return new VersionInfo(0, 0, 0);
    }

    @Override
    public VersionInfo getSDKVersionInfo() {
        String versionString = KidozSDK.getSDKVersion();
        String splits[] = versionString.split("\\.");

        if (splits.length >= 3) {
            int major = Integer.parseInt(splits[0]);
            int minor = Integer.parseInt(splits[1]);
            int micro = Integer.parseInt(splits[2]);
            return new VersionInfo(major, minor, micro);
        }

        String logMessage = String.format("Unexpected SDK version format: %s." +
                "Returning 0.0.0 for SDK version.", versionString);
        Log.w(TAG, logMessage);
        return new VersionInfo(0, 0, 0);
    }


    private void setKidozAd() {
        mKidozManager.setupKidozRewadrded(mKidozManager.getRewarded(), new BaseInterstitial.IOnInterstitialEventListener() {
                    @Override
                    public void onClosed() {
                        mRewardedAdCallback.onAdClosed();
                        Log.d(TAG, "KidozRewardedAdapter | onAdClosed");
                    }

                    @Override
                    public void onOpened() {
                        mRewardedAdCallback.onAdOpened();
                        Log.d(TAG, "KidozRewardedAdapter | onAdOpened");
                    }

                    @Override
                    public void onReady() {
                        mRewardedAdCallback = mAdLoadCallback.onSuccess(KidozAdMobMediationRewardedAdapter.this);
                        Log.d(TAG, "KidozRewardedAdapter | onAdReady");
                    }

                    @Override
                    public void onLoadFailed() {
                        mAdLoadCallback.onFailure("KidozRewardedAdapter | onLoadFailed");
                        Log.d(TAG, "KidozRewardedAdapter | onLoadFailed");
                    }

                    @Override
                    public void onNoOffers() {
                        mAdLoadCallback.onFailure("KidozRewardedAdapter | onNoOffers");
                        Log.d(TAG, "KidozRewardedAdapter | onNoOffers");
                    }
                },
                new BaseInterstitial.IOnInterstitialRewardedEventListener() {
                    @Override
                    public void onRewardReceived() {
                        BaseInterstitial.IOnInterstitialRewardedEventListener devListener = mKidozManager.getDeveloperRewardedListener();
                        if (devListener != null) {
                            devListener.onRewardReceived();
                        }

                        //Note: Kidoz currently have no server to client reward exposure.
                        mRewardedAdCallback.onUserEarnedReward(new RewardItem() {
                            @Override
                            public String getType() {
                                return null;
                            }

                            @Override
                            public int getAmount() {
                                return 1;
                            }
                        });
                        // .onRewarded(KidozAdMobMediationRewardedAdapter.this, new KidozAdMobRewardItem());
                        Log.d(TAG, "KidozRewardedAdapter | onRewardReceived");
                    }

                    @Override
                    public void onRewardedStarted() {
                        BaseInterstitial.IOnInterstitialRewardedEventListener devListener = mKidozManager.getDeveloperRewardedListener();
                        if (devListener != null) {
                            devListener.onRewardedStarted();
                        }

                        mRewardedAdCallback.onVideoStart();
                        Log.d(TAG, "KidozRewardedAdapter | onRewardedStarted");
                    }
                });
    }

    private void initKidoz(final Activity activity) {
        mKidozManager.initKidozSDK(activity, new SDKEventListener() {
            @Override
            public void onInitSuccess() {
                continueRequestRewardedAd(activity);
                Log.d(TAG, "KidozRewardedAdapter | onInitSuccess");
            }

            @Override
            public void onInitError(String error) {
                mInitializedState = false;
                Log.d(TAG, "KidozRewardedAdapter | onInitError: " + error);

            }
        });
    }

    @Override
    public void showAd(Context context) {
        KidozInterstitial kidozInterstitial = mKidozManager.getRewarded();
        kidozInterstitial.show();
    }
}
