package com.virtualtag.app.data

import android.app.Activity
import android.app.PendingIntent
import android.content.Intent
import android.content.IntentFilter
import android.nfc.NfcAdapter
import android.nfc.Tag
import android.nfc.tech.MifareClassic
import android.nfc.tech.MifareUltralight
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.virtualtag.app.utils.toHex

class ScanningViewModel : ViewModel() {
    // Currently scanned tag, mifare classic and ultralight information
    private val _scannedTag by lazy { MutableLiveData<Tag>() }
    val scannedTag = _scannedTag as LiveData<Tag>
    private val _mifareClassicInfo by lazy { MutableLiveData<MifareClassicInfo>() }
    val mifareClassicInfo = _mifareClassicInfo as LiveData<MifareClassicInfo>
    private val _mifareUltralightInfo by lazy { MutableLiveData<MifareUltralightInfo>() }
    val mifareUltralightInfo = _mifareUltralightInfo as LiveData<MifareUltralightInfo>

    // Is scanning allowed state variable
    private var isScanning = false

    // Allow scanning of NFC tags
    fun enableScanning(activity: Activity) {
        _scannedTag.value = null
        if (isScanning) return
        // Get NFC adapter
        val adapter = NfcAdapter.getDefaultAdapter(activity)
        // Create pending intent
        val intent = Intent(activity, activity.javaClass).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
        val pendingIntent =
            PendingIntent.getActivity(activity, 0, intent, PendingIntent.FLAG_MUTABLE)
        // Filter intents for NFC tag detection with supported technologies
        val intentFilter = IntentFilter(NfcAdapter.ACTION_TECH_DISCOVERED)
        val intentFiltersArray = arrayOf(intentFilter)
        val techListsArray = arrayOf(
            arrayOf(MifareClassic::class.java.name),
            arrayOf(MifareUltralight::class.java.name),
        )
        // Enable foreground dispatch
        adapter.enableForegroundDispatch(
            activity,
            pendingIntent,
            intentFiltersArray,
            techListsArray
        )
        Log.i("NFC", "scanning enabled")
        _scannedTag.value = null
        isScanning = true
    }

    // Disable scanning of NFC tags
    fun disableScanning(activity: Activity) {
        if (!isScanning) return
        // Get NFC adapter
        val adapter = NfcAdapter.getDefaultAdapter(activity)
        // Disable foreground dispatch
        adapter.disableForegroundDispatch(activity)
        Log.i("NFC", "scanning disabled")
        isScanning = false
    }

    // Handle new intents the main activity receives
    fun onActivityNewIntent(intent: Intent?) {
        if (intent?.action == NfcAdapter.ACTION_TECH_DISCOVERED) {
            val tag: Tag? = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG)
            Log.i("NFC", "scanned ${tag?.id?.toHex()}")
            if (tag?.techList?.contains(MifareClassic::class.java.name) == true) {
                val mfc = MifareClassic.get(tag)
                _mifareClassicInfo.value = MifareClassicInfo(mfc)
            }
            if (tag?.techList?.contains(MifareUltralight::class.java.name) == true) {
                val mfu = MifareUltralight.get(tag)
                _mifareUltralightInfo.value = MifareUltralightInfo(mfu)
            }
            _scannedTag.value = tag
        }
    }
}
