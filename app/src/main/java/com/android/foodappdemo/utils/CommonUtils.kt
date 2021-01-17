package com.android.foodappdemo.utils

import android.annotation.SuppressLint
import android.app.*
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.content.res.Resources
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.media.RingtoneManager
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.provider.Settings
import android.text.*
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.MetricAffectingSpan
import android.text.style.URLSpan
import android.util.DisplayMetrics
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.android.foodappdemo.R
import java.io.InputStream
import java.text.DecimalFormat
import java.text.NumberFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Pattern
import kotlin.collections.ArrayList
import kotlin.math.pow


object CommonUtils {

    private var toast: Toast? = null
    fun setAppLanguage(ctx: Context?, language: String = "en"): Context? {
        var context = ctx
        val locale = Locale(language)
        Locale.setDefault(locale)

        val res = context?.resources
        val config = Configuration(res?.configuration)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            config.setLocale(locale)
            context = context?.createConfigurationContext(config)
        } else {
            config.locale = locale
            res?.updateConfiguration(config, res.displayMetrics)
        }
        return context
    }

    fun changeStatusBarColor(activity: Activity, color: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            activity.window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            activity.window.statusBarColor = color
        }
    }

    fun isInternetOn(context: Context): Boolean {
        var result = false
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            cm?.run {
                cm.getNetworkCapabilities(cm.activeNetwork)?.run {
                    result = when {
                        hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                        hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                        hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                        else -> false
                    }
                }
            }
        } else {
            cm?.run {
                cm.activeNetworkInfo?.run {
                    if (type == ConnectivityManager.TYPE_WIFI) {
                        result = true
                    } else if (type == ConnectivityManager.TYPE_MOBILE) {
                        result = true
                    }
                }
            }
        }
        return result
    }

    fun getDate(
        date: String?,
        originalFormat: String,
        targetFormat: String,
        locale: Locale = Locale.getDefault()
    ): String {
        var formattedDate = ""
        date?.let {
            if (it.isNotEmpty()) {
                try {
                    val originalDateFormat = SimpleDateFormat(originalFormat, locale)
                    val targetDateFormat = SimpleDateFormat(targetFormat, Locale.ENGLISH)
                    formattedDate =
                        targetDateFormat.format(
                            originalDateFormat.parse(
                                it.replace(
                                    "T",
                                    " "
                                ).replace("Z", "")
                            )
                        )
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
        return formattedDate

    }


    fun getDateInLocale(
        date: String?,
        originalFormat: String,
        targetFormat: String,
        locale: Locale = Locale.getDefault()
    ): String {
        var formattedDate = ""
        date?.let {
            if (it.isNotEmpty()) {
                try {
                    val originalDateFormat = SimpleDateFormat(originalFormat, locale)
                    val targetDateFormat = SimpleDateFormat(targetFormat, locale)
                    formattedDate =
                        targetDateFormat.format(
                            originalDateFormat.parse(
                                it.replace(
                                    "T",
                                    " "
                                ).replace("Z", "")
                            )
                        )
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
        return formattedDate

    }


    fun setDrawableEnd(text: TextView, icon: Int) {
        text.setCompoundDrawablesWithIntrinsicBounds(0, 0, icon, 0)
    }

    fun getRealPathFromURI(context: Context, contentURI: Uri): String {
        val result: String
        val projection = arrayOf(MediaStore.Video.Media.DATA)
        val cursor = context.contentResolver.query(
            contentURI, projection,
            null, null, null
        )
        if (cursor == null) { // Source is Dropbox or other similar local file path
            result = contentURI.path ?: ""
        } else {
            cursor.moveToFirst()
            val idx = cursor.getColumnIndex(MediaStore.Images.Media.DATA)
            result = cursor.getString(idx)
            cursor.close()
        }
        return result
    }

    fun getDrawable(context: Context, id: Int): Drawable? {
        val version = Build.VERSION.SDK_INT
        return if (version >= 23) {
            ContextCompat.getDrawable(context, id)
        } else {
            context.resources.getDrawable(id)
        }
    }

    fun getColor(context: Context, id: Int): Int {
        val version = Build.VERSION.SDK_INT
        return if (version >= 23) {
            ContextCompat.getColor(context, id)
        } else {
            context.resources.getColor(id)
        }
    }

    fun setValueToView(view: View?, text: String?) {
        var textValue = text

        if (view == null) {
            return
        }
        if (textValue == null) {
            return
        }
        if (textValue.contains("null")) {
            textValue = textValue.replace("null", "")
        }
        if (view is EditText) {

            view.setText(textValue)

        } else if (view is TextView) {

            view.text = textValue
        }


    }

    fun getValueFromView(view: View): String? {

        if (view is EditText) {

            return if (view.text.isNotEmpty()) view.text.toString() else null

        } else if (view is TextView) {

            return if (view.text.isNotEmpty()) view.text.toString() else null
        }


        return null
    }


    fun getDimen(context: Context, id: Int): Int {
        return context.resources.getDimension(id).toInt()
    }

    fun isValidPassword(password: String): Boolean {
        val patternData = "^(?=.*[0-9])(?=.*[a-z])(?=\\S+$).{6,}$"
        val pattern = Pattern.compile(patternData)
        val matcher = pattern.matcher(password)
        return matcher.matches()
    }

    fun emailValidator(email: String?): Boolean {
        email?.let {
            return isValidEmail(it)
        } ?: run { return false }
    }

    fun mobileValidator(mobile: String?): Boolean {
        mobile?.let {
            return (it.isNotEmpty())
        } ?: run { return false }
    }

    private fun isValidEmail(target: CharSequence?): Boolean {
        return if (target == null) {
            false
        } else {
            android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches()
        }
    }

    fun makeLinkClickable(
        strBuilder: SpannableStringBuilder,
        span: URLSpan,
        callback: ANLinkClickCallback,
        title: String
    ) {
        val start = strBuilder.getSpanStart(span)
        val end = strBuilder.getSpanEnd(span)
        val flags = strBuilder.getSpanFlags(span)
        //Log.d("title",title)
        val clickable = object : ClickableSpan() {
            override fun onClick(view: View) {
                callback.onClick(title, span.url)
            }
        }
        strBuilder.setSpan(clickable, start, end, flags)
        strBuilder.removeSpan(span)
    }

    fun goToWebView(url: String?, context: Context) {
        context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
    }

    fun setTextViewHTML(text: TextView, html: String, callback: ANLinkClickCallback) {
        val sequence = Html.fromHtml(html)
        val p = Pattern.compile("<[a][^>]*>(.+?)</[a]>")
        val m = p.matcher(html)
        val strBuilder = SpannableStringBuilder(sequence)
        val urls = strBuilder.getSpans(0, sequence.length, URLSpan::class.java)
        var tagValues = ArrayList<String>()
        while (m.find()) {
            tagValues.add(m.group(1))
        }
        var i = 0
        for (span in urls) {

            makeLinkClickable(strBuilder, span, callback, tagValues.get(i))
            i++
        }
        text.text = strBuilder.trim()
        text.movementMethod = LinkMovementMethod.getInstance()
    }


    fun shareIntent(activity: Activity, message: String) {

        try {
            val sharingIntent = Intent(Intent.ACTION_SEND)
            sharingIntent.type = "text/plain"
            sharingIntent.putExtra(Intent.EXTRA_TEXT, message)
            sharingIntent.putExtra(
                Intent.EXTRA_SUBJECT,
                activity.resources.getString(R.string.app_name)
            )
            activity.startActivity(Intent.createChooser(sharingIntent, ""))
        } catch (ex: ActivityNotFoundException) {


        }

    }

    fun sendMail(context: Context, email: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("mailto:$email"))
        intent.putExtra(Intent.EXTRA_SUBJECT, context.resources.getString(R.string.app_name))
        intent.putExtra(Intent.EXTRA_TEXT, "")
        if (intent.resolveActivity(context.packageManager) != null) {
            context.startActivity(intent)
        }
    }

    fun getDate(
        context: Context,
        date: String?,
        originalFormat: String,
        targetFormat: String
    ): String {
        var formattedDate = ""
        val locale = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            context.resources.configuration.locales.get(0)
        } else {
            context.resources.configuration.locale
        }
        date?.let {
            if (it.isNotEmpty()) {
                val originalDateFormat = SimpleDateFormat(originalFormat, locale)
                val targetDateFormat = SimpleDateFormat(targetFormat, locale)
                try {
                    formattedDate =
                        targetDateFormat.format(
                            originalDateFormat.parse(
                                it.replace(
                                    "T",
                                    " "
                                ).replace("Z", "")
                            )
                        )
                } catch (e: ParseException) {
                    e.printStackTrace()
                }
            }
        }
        return formattedDate

    }


    fun getDateUTC(
        context: Context,
        date: String?,
        originalFormat: String,
        targetFormat: String
    ): String {
        var formattedDate = ""
        val locale = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            context.resources.configuration.locales.get(0)
        } else {
            context.resources.configuration.locale
        }
        date?.let {
            if (it.isNotEmpty()) {
                val originalDateFormat = SimpleDateFormat(originalFormat, locale)
                originalDateFormat.timeZone = TimeZone.getTimeZone("UTC")
                val targetDateFormat = SimpleDateFormat(targetFormat, locale)
                targetDateFormat.timeZone = TimeZone.getDefault()
                try {
                    formattedDate =
                        targetDateFormat.format(
                            originalDateFormat.parse(
                                it
                            )
                        )
                } catch (e: ParseException) {
                    e.printStackTrace()
                }
            }
        }
        return formattedDate

    }

    @SuppressLint("WrongConstant")
    fun showNotification(
        context: Context,
        intent: Intent?,
        title: String?,
        message: String?,
        notificationId: Int
    ) {
        var titleString = title
        var contentIntent: PendingIntent? = null
        if (intent != null) {
            contentIntent =
                PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        }

        if (titleString == null || titleString.isEmpty()) {
            titleString = context.resources.getString(R.string.app_name)
        }
        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val channelId = context.resources.getString(R.string.app_name)
        val builder = NotificationCompat.Builder(context)

        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(channelId, channelId, importance)
            notificationManager.createNotificationChannel(channel)
        }
        builder.setSmallIcon(R.mipmap.ic_launcher)
//        builder.setLargeIcon(R.mipmap.ic_launcher)
        builder.setContentTitle(titleString)
        builder.setContentText(message)
        builder.setAutoCancel(true)
        builder.setContentIntent(contentIntent)
        builder.setChannelId(channelId)
        builder.priority = Notification.PRIORITY_MAX
        builder.setWhen(0)
        builder.setSound(defaultSoundUri)
        notificationManager.notify(notificationId, builder.build())

    }

    fun cancelNotification(context: Context, notificationId: Int) {
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.cancel(notificationId)
    }

    interface ANLinkClickCallback {
        fun onClick(title: String?, url: String?)
    }


    fun clearUserData(activityContext: Context) {
    }

    fun getDeviceWidth(context: Context): Int {
        val displaymetrics = DisplayMetrics()
        (context as Activity).windowManager.defaultDisplay.getMetrics(displaymetrics)
        val height = displaymetrics.heightPixels
        return displaymetrics.widthPixels
    }

    fun getDeviceHeight(context: Context): Int {
        val displaymetrics = DisplayMetrics()
        (context as Activity).windowManager.defaultDisplay.getMetrics(displaymetrics)
        return displaymetrics.heightPixels
    }

    fun roundTwoDecimals(context: Context, value: Double?, precisionLevel: Int? = 2): String {
        var precisionLevel = precisionLevel
        if (null == precisionLevel)
            precisionLevel = 2
        val locale = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            context.resources.configuration.locales.get(0)
        } else {
            context.resources.configuration.locale
        }
        val numberFormat = NumberFormat.getNumberInstance(locale)
        val decimalFormat = numberFormat as DecimalFormat
        val sb = StringBuilder((precisionLevel ?: 0) + 2)
        sb.append("0.")
        for (i in 0 until (precisionLevel ?: 0)) {
            sb.append("0")
        }
        decimalFormat.applyPattern(sb.toString())
        value?.let {
            return decimalFormat.format(it)
        } ?: kotlin.run {
            return decimalFormat.format(0.0)
        }
    }

    @RequiresApi(Build.VERSION_CODES.KITKAT)
    fun getLocationMode(context: Context): Int {
        return Settings.Secure.getInt(context.contentResolver, Settings.Secure.LOCATION_MODE)

    }

    fun getDrawableByName(context: Context, name: String): Int? {
        return context.resources.getIdentifier(
            name.replace(".png", ""), "drawable",
            context.packageName
        )
    }

   /* fun showToast(context: Context?, message: String?, type: Int) {
        if (context == null || message == null || message.isEmpty()) {
            return
        }
        val inflater = (context as Activity).layoutInflater
        val layout = inflater.inflate(
            R.layout.layout_toast,
            null
        )

        val textMessage = layout.findViewById<TextView>(R.id.message)
        val toastContainer = layout.findViewById<View>(R.id.toastContainer)
        setValueToView(textMessage, message)
        when (type) {
            GVConstants.TOAST_TYPE_SUCCESS -> {
                //Bg:yellow
                //Text: Blue
                textMessage.setTextColor(getColor(context, R.color.colorToastSuccessText))
                toastContainer.background =
                    ColorDrawable(getColor(context, R.color.colorToastSuccessBg))
            }
            GVConstants.TOAST_TYPE_ERROR -> {
                //Bg:yellow
                //Text: off red
                textMessage.setTextColor(getColor(context, R.color.colorToastFailureText))
                toastContainer.background =
                    ColorDrawable(getColor(context, R.color.colorToastFailureBg))
            }
            else -> {
                textMessage.setTextColor(getColor(context, R.color.colorToastSuccessText))
                toastContainer.background =
                    ColorDrawable(getColor(context, R.color.colorToastFailureBg))
            }
        }

        val location = IntArray(2)
        if (toast == null || toast!!.view.windowVisibility != View.VISIBLE) {
            toast = Toast(context)
            toast!!.setGravity(
                Gravity.TOP or Gravity.FILL_HORIZONTAL, 0,
                location[1] - 10
            )
            toast!!.duration = Toast.LENGTH_SHORT
            toast!!.view = layout
            toast!!.show()
        }
    }*/

    fun readJSONFromAsset(context: Context): String? {
        var json: String? = null
        try {
            val inputStream: InputStream = context.assets.open("country.json")
            json = inputStream.bufferedReader().use { it.readText() }
        } catch (ex: Exception) {
            ex.printStackTrace()
            return null
        }
        return json
    }

    fun getAmountWithTax(amount: Double?, taxPercentage: Float): Double? {
        if (null != amount && taxPercentage > 0) {
            return amount * (1 + (taxPercentage / 100))
        }
        return amount
    }

    fun getTax(taxPercentage: Float?): Double? {
        if (null != taxPercentage && taxPercentage > 0) {
            return (taxPercentage / 100).toDouble()
        }
        return 0.toDouble()
    }

    fun call(context: Context, phoneNumber: String?) {

        if (null == phoneNumber || phoneNumber.isEmpty()) {
            return
        }

        try {
            val intent = Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phoneNumber, null))
            context.startActivity(intent)
        } catch (e: ActivityNotFoundException) {
        }

    }

    fun navigate(context: Context, latitude: Double, longitude: Double) {
        val gmmIntentUri = Uri.parse("geo:0,0?q=$latitude,$longitude")
        val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
        mapIntent.setPackage("com.google.android.apps.maps")
        if (mapIntent.resolveActivity(context.packageManager) != null) {
            context.startActivity(mapIntent)
        } else {
            /*showToast(
                context,
                context.getString(R.string.map_not_found),
                GVConstants.TOAST_TYPE_ERROR
            )*/
        }
    }

    fun showRateUs(context: Context) {
        val appPackageName = context.packageName
        try {
            context.startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("market://details?id=$appPackageName")
                )
            )
        } catch (anfe: android.content.ActivityNotFoundException) {
            context.startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("https://play.google.com/store/apps/details?id=$appPackageName")
                )
            )
        }

    }

    class CustomTypefaceSpan(private val typeface: Typeface?, private val textSize: Float) :
        MetricAffectingSpan() {
        override fun updateDrawState(paint: TextPaint) {
            paint.typeface = typeface
            paint.textSize = textSize
        }

        override fun updateMeasureState(paint: TextPaint) {
            paint.typeface = typeface
            paint.textSize = textSize
        }
    }

    fun convertDpToPixel(dp: Int, context: Context): Float {
        return dp * ((context.resources.displayMetrics.densityDpi).toFloat() / DisplayMetrics.DENSITY_DEFAULT)
    }

    fun splitNumber(number: Int): ArrayList<Int> {
        val numbers: ArrayList<Int> = ArrayList()
        val numberLength = number.toString().length
        for (i in 0 until numberLength) {
            val value = 10.0.pow(i.toDouble()).toInt()
            numbers.add(((number / value) % 10) * value)
        }
        return numbers
    }


    fun dp2px(resources: Resources, dp: Float): Float {
        val scale = resources.displayMetrics.density
        return dp * scale + 0.5f
    }

    fun sp2px(resources: Resources, sp: Float): Float {
        val scale = resources.displayMetrics.scaledDensity
        return sp * scale
    }


    fun hideKeyboard(activity: Activity) {
        val imm: InputMethodManager =
            activity?.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        //Find the currently focused view, so we can grab the correct window token from it.
        var view = activity.currentFocus
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = View(activity)
        }
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    fun hideKeyboardFrom(context: Context, view: View) {
        val imm: InputMethodManager =
            context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    fun getDateAndTime(
        context: Context,
        date: String?,
        originalFormat: String,
        targetDateFormat: String,
        targetTimeFormat: String
    ): Pair<String, String> {
        var formattedDate = ""
        var formattedTime = ""
        val locale = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            context.resources.configuration.locales.get(0)
        } else {
            context.resources.configuration.locale
        }
        date?.let {
            if (it.isNotEmpty()) {
                val originalDateFormat = SimpleDateFormat(originalFormat, locale)
                try {
                    formattedDate =
                        SimpleDateFormat(targetDateFormat, locale).format(
                            originalDateFormat.parse(
                                it.replace(
                                    "T",
                                    " "
                                ).replace("Z", "")
                            )
                        )

                    formattedTime = SimpleDateFormat(targetTimeFormat, locale).format(
                        originalDateFormat.parse(
                            it.replace(
                                "T",
                                " "
                            ).replace("Z", "")
                        )
                    )
                } catch (e: ParseException) {
                    e.printStackTrace()
                }
            }
        }
        return Pair(formattedDate, formattedTime)
    }

    fun getDateFromCalendar(calendar: Calendar, targetFormat: String): String {
        val dateFormat = SimpleDateFormat(targetFormat, Locale.ENGLISH)
        return dateFormat.format(calendar.time)
    }

}
