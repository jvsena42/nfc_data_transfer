package com.bulletapps.nfcdatatransfer

import android.nfc.NdefMessage
import android.nfc.NdefRecord
import android.nfc.NfcAdapter
import android.nfc.NfcEvent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast

class MainActivity : AppCompatActivity(), NfcAdapter.CreateNdefMessageCallback {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        getMessageIntent()
    }

    private fun getMessageIntent() {
        if (NfcAdapter.ACTION_NDEF_DISCOVERED == intent.action) {
            intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES)?.also { rawMessages ->
                val messages: List<NdefMessage> = rawMessages.map { it as NdefMessage }
                Toast.makeText(this, messages.firstOrNull().toString(), Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun createTextRecord(payload: String): NdefRecord {
        return NdefRecord.createTextRecord(
            "pt-BR", // TODO handle language code
            payload
        )
    }

    fun createMessage(
        messageRecord: NdefRecord,
        packageName: String = applicationContext.packageName
    ) = NdefMessage(
        arrayOf(
            messageRecord,
            NdefRecord.createApplicationRecord(packageName)
        )
    )

    override fun createNdefMessage(p0: NfcEvent?): NdefMessage {
        return (createMessage(createTextRecord("Android é top")))
    }
}