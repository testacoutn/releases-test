package com.kidoz.mediation.admob.adapters;

import com.google.android.gms.ads.reward.RewardItem;

/**
 * Created by orikam on 07/06/2017.
 */

public class KidozAdMobRewardItem implements RewardItem
{
    /*
    * Source: https://developers.google.com/admob/android/rewarded-video-adapters
    * AdMob requires a reward item with a reward type and amount to be sent when sending the
    * rewarded callback. If your SDK does not have a reward amount you need to do the following:
    *
    * 1. AdMob provides an ability to override the reward value in the front end. Document
    * this asking the publisher to override the reward value on AdMob's front end.
    * 2. Send a reward item with default values for the type (an empty string "") and reward
    * amount (1).
    */

    //stubs
    private static final String KIDOZ_REWARD_ITEM_TYPE = "";
    private static final int KIDOZ_REWARD_ITEM_AMOUNT = 1;

    @Override
    public String getType()
    {
        return KIDOZ_REWARD_ITEM_TYPE;
    }

    @Override
    public int getAmount()
    {
        return KIDOZ_REWARD_ITEM_AMOUNT;
    }
}
