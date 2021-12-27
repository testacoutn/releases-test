package com.kidoz.sdk.sample.app.SampleAds.model;

import android.app.Activity;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;
import com.kidoz.sdk.sample.app.R;
import com.kidoz.sdk.sample.app.SampleAds.SampleAdsView;

/**
 * Created by orikam on 07/06/2017.
 */

public class SampleAdsAdMobModel
{
    private static final String ADMOB_APP_ID = "ca-app-pub-5967470543517808~3214489975";
    private static final String ADMOB_TEST_DEVICE_ID = "FA1FF90C0CBC7379549B5753F4F8028D";

    //Ad Units
    private static final String ADMOB_INTERSTITIAL_ID = "ca-app-pub-5967470543517808/1318954375";
    private static final String ADMOB_REWARDED_VIDEO_ID = "ca-app-pub-5967470543517808/9701675577";

    private InterstitialAd mAdMobInterstitialAd;
    private RewardedAd mAdMobRewarded;

    private AdView mAdMobBanner;


    /****************
     * Interstitial *
     ****************/

    public void loadInterstitial(final SampleAdsView adsView)
    {
        InterstitialAd.load(
                adsView.getActivity(),
                ADMOB_INTERSTITIAL_ID,
                new AdRequest.Builder().build(),
                new InterstitialAdLoadCallback() {
                    @Override
                    public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                        mAdMobInterstitialAd = interstitialAd;
                        adsView.showFeedBackText("AdMob | Interstitial Ad Ready");
                        mAdMobInterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                            @Override
                            public void onAdFailedToShowFullScreenContent(@NonNull AdError adError) {
                                adsView.showFeedBackText("AdMob | Failed to show interstitial " + adError.getMessage());
                            }

                            @Override
                            public void onAdShowedFullScreenContent() {
                                super.onAdShowedFullScreenContent();
                                adsView.showFeedBackText("AdMob | Interstitial Ad Shown");
                            }

                            @Override
                            public void onAdDismissedFullScreenContent() {
                                adsView.showFeedBackText("AdMob | Interstitial Ad Closed");
                            }

                            @Override
                            public void onAdImpression() {
                                super.onAdImpression();
                            }
                        });
                    }

                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        Toast.makeText(adsView.getActivity(),
                                "Failed to load interstitial: " + loadAdError,
                                Toast.LENGTH_SHORT).show();
                        adsView.showFeedBackText("Failed to load interstitial: " + loadAdError);
                        mAdMobInterstitialAd = null;
                    }
                }

        );
    }

    public InterstitialAd getAdMobInterstitial(){
        return mAdMobInterstitialAd;
    }

    /******************
     * Rewarded Video *
     ******************/

    public void loadRewardedVideo(final SampleAdsView adsView){

        RewardedAd.load(
                adsView.getActivity(),
                ADMOB_REWARDED_VIDEO_ID,
                new AdRequest.Builder().build(),
                new RewardedAdLoadCallback(){
                    @Override
                    public void onAdLoaded(@NonNull RewardedAd ad) {
                        adsView.showFeedBackText("AdMob | Rewarded Ad Ready");
                        mAdMobRewarded = ad;
                        mAdMobRewarded.setFullScreenContentCallback(new FullScreenContentCallback() {
                            @Override
                            public void onAdFailedToShowFullScreenContent(@NonNull AdError adError) {
                                adsView.showFeedBackText("AdMob | Failed to show rewarded " + adError.getMessage());
                            }

                            @Override
                            public void onAdShowedFullScreenContent() {
                                adsView.showFeedBackText("AdMob | Rewarded Ad Shown");
                            }

                            @Override
                            public void onAdDismissedFullScreenContent() {
                                adsView.showFeedBackText("AdMob | Rewarded Ad Closed");
                            }

                            @Override
                            public void onAdImpression() {
                                super.onAdImpression();
                            }
                        });
                    }

                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        mAdMobRewarded = null;
                        adsView.showFeedBackText("AdMob | onRewardedAdFailedToLoad(" + loadAdError.getMessage() + ").");
                    }

                }
        );
    }

    public RewardedAd getAdMobRewarded()
    {
        return mAdMobRewarded;
    }

    /**********
     * Banner *
     **********/


    public void setupAdMobBanner(Activity activity) {

        mAdMobBanner = (AdView) activity.findViewById(R.id.admob_banner);

    }

    public void loadBanner(final SampleAdsView adsView)
    {
        mAdMobBanner.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                adsView.showFeedBackText("AdMob | Banner Ad Closed");
            }

            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                adsView.showFeedBackText("AdMob | Banner Ad Failed to Load");
            }

            @Override
            public void onAdOpened() {
                adsView.showFeedBackText("AdMob | Banner Ad Opened");
            }

            @Override
            public void onAdLoaded() {
                adsView.showFeedBackText("AdMob | Banner Ad Loaded");
            }

            @Override
            public void onAdClicked() {
                adsView.showFeedBackText("AdMob | Banner Ad Clicked");
            }

            @Override
            public void onAdImpression() {
                adsView.showFeedBackText("AdMob | Banner Ad On Impression");
            }
        });
        mAdMobBanner.loadAd(CreateAdRequest());

    }

    public void closeBanner()
    {
        mAdMobBanner.destroy();
    }

    private AdRequest CreateAdRequest(){
        return new AdRequest.Builder().build();
    }
}
