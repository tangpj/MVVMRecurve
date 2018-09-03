package com.tangpj.recurve.util

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import android.net.Uri
import android.widget.Toast
import androidx.browser.customtabs.CustomTabsIntent
import com.tangpj.recurve.R
import java.util.*

fun openInCustomTabOrBrowser(context: Context, uri: Uri) {
    val pkg = CustomTabsHelper.getPackageNameToUse(context)
    if (pkg != null) {
        val color = UiUtils.resolveColor(context, R.attr.colorPrimary)
        val i = CustomTabsIntent.Builder()
                .setToolbarColor(color)
                .build()
        i.intent.`package` = pkg
        i.intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        i.launchUrl(context, uri)
    } else {
        launchBrowser(context, uri, Intent.FLAG_ACTIVITY_NEW_TASK)
    }
}

fun launchBrowser(context: Context, uri: Uri?, flags: Int) {
    if (uri == null) {
        return
    }
    val intent = createBrowserIntent(context, uri)
    if (intent != null) {
        intent.addFlags(flags)
        context.startActivity(intent)
    } else {
        Toast.makeText(context, R.string.no_browser_found, Toast.LENGTH_LONG).show()
    }
}

private fun buildDummyUri(uri: Uri): Uri {
    return uri.buildUpon().authority("www.somedummy.com").build()
}

private fun createBrowserIntent(context: Context, uri: Uri): Intent? {
    val dummyUri = buildDummyUri(uri)
    val browserIntent = Intent(Intent.ACTION_VIEW, dummyUri)
            .addCategory(Intent.CATEGORY_BROWSABLE)
    return createActivityChooserIntent(context, browserIntent, uri)
}

private fun createActivityChooserIntent(context: Context, intent: Intent, uri: Uri): Intent? {
    val pm = context.packageManager
    val activities = pm.queryIntentActivities(intent,
            PackageManager.MATCH_DEFAULT_ONLY)
    val chooserIntents = ArrayList<Intent>()
    val ourPackageName = context.packageName

    Collections.sort(activities, ResolveInfo.DisplayNameComparator(pm))

    for (resInfo in activities) {
        val info = resInfo.activityInfo
        if (!info.enabled || !info.exported) {
            continue
        }
        if (info.packageName == ourPackageName) {
            continue
        }

        val targetIntent = Intent(intent)
        targetIntent.`package` = info.packageName
        targetIntent.setDataAndType(uri, intent.type)
        chooserIntents.add(targetIntent)
    }

    if (chooserIntents.isEmpty()) {
        return null
    }

    val lastIntent = chooserIntents.removeAt(chooserIntents.size - 1)
    if (chooserIntents.isEmpty()) {
        // there was only one, no need to show the chooser
        return lastIntent
    }

    val chooserIntent = Intent.createChooser(lastIntent, null)
    chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS,
            chooserIntents.toTypedArray())
    return chooserIntent
}