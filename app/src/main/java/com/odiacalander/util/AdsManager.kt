package com.odiacalander.util

import android.content.Context
import android.util.Log
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback

object AdsManager {
    private var mInterstitialAd: InterstitialAd? = null
    private var adId: String = "ca-app-pub-3940256099942544/1033173712"
    fun loadInterstitialAd(context: Context, adStatus: (Boolean) -> Unit) {
        val adRequest = AdRequest.Builder().build()
        InterstitialAd.load(context, adId, adRequest, object : InterstitialAdLoadCallback() {
            override fun onAdFailedToLoad(adError: LoadAdError) {
                super.onAdFailedToLoad(adError)
                Log.d("ADS", adError.toString())
                mInterstitialAd = null
                adStatus.invoke(false)
            }

            override fun onAdLoaded(interstitialAd: InterstitialAd) {
                super.onAdLoaded(interstitialAd)
                Log.d("ADS", "Ad was loaded.")
                mInterstitialAd = interstitialAd
                adStatus.invoke(true)
            }
        })
    }

    fun showInterstitialAd() {
        mInterstitialAd?.fullScreenContentCallback = object : FullScreenContentCallback() {
            override fun onAdClicked() {
                super.onAdClicked()
                // Called when a click is recorded for an ad.
                Log.d("ADS", "Ad was clicked.")
            }

            override fun onAdDismissedFullScreenContent() {
                // Called when ad is dismissed.
                super.onAdDismissedFullScreenContent()
                Log.d("ADS", "Ad dismissed fullscreen content.")
                mInterstitialAd = null
            }

            override fun onAdFailedToShowFullScreenContent(p0: AdError) {
                // Called when ad fails to show.
                Log.e("ADS", "Ad failed to show fullscreen content.")
                mInterstitialAd = null
            }

            override fun onAdImpression() {
                super.onAdImpression()
                // Called when an impression is recorded for an ad.
                Log.d("ADS", "Ad recorded an impression.")
            }

            override fun onAdShowedFullScreenContent() {
                // Called when ad is shown.
                Log.d("ADS", "Ad showed fullscreen content.")
            }
        }
    }
}