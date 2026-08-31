package com.mk.tools.toolbar

import android.app.Activity
import android.app.AlertDialog
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast

class MainActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val selectedText = intent.getCharSequenceExtra(Intent.EXTRA_PROCESS_TEXT)?.toString()?.trim() ?: ""

        if (selectedText.isNotEmpty()) {
            showQuickMenu(selectedText)
        } else {
            Toast.makeText(this, "Select text in another app to use Quick Tools", Toast.LENGTH_LONG).show()
            finish()
        }
    }

    private fun showQuickMenu(text: String) {
        val options = arrayOf(
            "🔎 Google Search",
            "🌐 Google Translate",
            "📖 Wikipedia Search",
            "📋 Copy to Clipboard"
        )

        AlertDialog.Builder(this)
            .setTitle("Quick Tools")
            .setItems(options) { _, index ->
                val encodedText = Uri.encode(text)
                when (index) {
                    0 -> openLink("https://www.google.com/search?q=$encodedText")
                    1 -> openLink("https://translate.google.com/?text=$encodedText")
                    2 -> openLink("https://en.wikipedia.org/wiki/Special:Search?search=$encodedText")
                    3 -> copyText(text)
                }
                finish()
            }
            .setOnDismissListener { finish() }
            .show()
    }

    private fun openLink(url: String) {
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(browserIntent)
    }

    private fun copyText(text: String) {
        val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText("Copied Text", text)
        clipboard.setPrimaryClip(clip)
        Toast.makeText(this, "Copied!", Toast.LENGTH_SHORT).show()
    }
}
