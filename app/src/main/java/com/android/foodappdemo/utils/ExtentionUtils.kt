package com.android.foodappdemo.utils

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.graphics.*
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.os.Build
import android.text.*
import android.util.AttributeSet
import android.util.Log
import android.view.*
import android.view.animation.AnimationUtils
import android.view.animation.LayoutAnimationController
import android.widget.EditText
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.android.foodappdemo.R
import com.bumptech.glide.GenericTransitionOptions
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import java.math.BigDecimal
import java.math.RoundingMode
import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.*


fun View.gone() {
    visibility = View.GONE
}

fun View.invisible() {
    visibility = View.INVISIBLE
}

fun View.visible() {
    visibility = View.VISIBLE
}

fun View.visible(isVisible: Boolean) {
    if (isVisible)
        visible()
    else
        gone()
}

fun View.setInvisble(isVisible: Boolean) {
    if (isVisible)
        visible()
    else
        invisible()
}

fun AppCompatActivity.changeFragment(
    containerId: Int,
    fragment: Fragment,
    tag: String = fragment.javaClass.simpleName,
    allowStateLoss: Boolean = false
) {

    if (allowStateLoss) {
        supportFragmentManager.beginTransaction().replace(containerId, fragment, tag)
            .commitAllowingStateLoss()
    } else {
        supportFragmentManager.beginTransaction().replace(containerId, fragment, tag).commit()
    }
}

fun ViewGroup.inflate(layoutId: Int): View {
    return LayoutInflater.from(context).inflate(layoutId, this, false)
}

fun ImageView.load(context: Context, imageUrl: String?, placeholderRes: Int? = null) {
    imageUrl?.let { image ->
        try {

            placeholderRes?.let { placeholder ->
                GlideApp.with(this).load(image)
                    .transition(GenericTransitionOptions.with(R.anim.fade_in)).error(placeholder)
                    .placeholder(placeholder)
                    .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                    .skipMemoryCache(true)
                    .into(this)
            } ?: kotlin.run {
                GlideApp.with(this).load(image)
                    .transition(GenericTransitionOptions.with(R.anim.fade_in))
                    .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                    .skipMemoryCache(true)
                    .into(this)
            }
        } catch (e: Exception) {
            placeholderRes?.let { placeholder ->
                GlideApp.with(this).load(placeholder)
                    .transition(GenericTransitionOptions.with(R.anim.fade_in)).error(placeholder)
                    .placeholder(placeholder)
                    .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                    .skipMemoryCache(true)
                    .into(this)
            }
        }
    }
}

fun ImageView.loadWithRadius(
    context: Context,
    imageUrl: String?,
    radius: Int,
    placeholderRes: Int? = null
) {

    val requestOptions = if (null != placeholderRes) {
        RequestOptions().apply {
            placeholder(placeholderRes)
            error(placeholderRes)
        }
    } else null

    try {
        requestOptions?.let {
            Glide.with(context)
                .setDefaultRequestOptions(it)
                .load(GlideUrl(imageUrl/*, auth*/))
                .transform(RoundedCorners(radius))
                .transition(GenericTransitionOptions.with(R.anim.fade_in))
                .into(this)
        } ?: run {
            Glide.with(context).load(GlideUrl(imageUrl/*, auth*/)).transform(RoundedCorners(radius))
                .into(this)
        }
    } catch (e: Exception) {
        e.printStackTrace()
        placeholderRes?.let {
            Glide.with(context).load(it).into(this)
        }
    }

}

fun ImageView.loadWithErrorImageView(
    context: Context,
    imageUrl: String?,
    errorImage: ImageView? = null
) {

    val imageView = this
    imageView.gone()
    errorImage?.visible()
//    val auth = LazyHeaders.Builder() // can be cached in a field and reused
//        .addHeader(
//            "Authorization",
//            ASQBasicAuthorization()
//        )
//        .build()
    try {
        Glide.with(context).asBitmap()
            .load(GlideUrl(imageUrl/*, auth*/))
            .transition(GenericTransitionOptions.with(R.anim.fade_in))
            .into(object : CustomTarget<Bitmap>() {
                override fun onLoadCleared(placeholder: Drawable?) {
                }

                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    imageView.visible()
                    errorImage?.gone()
                    imageView.setImageBitmap(resource)
                }

            })
    } catch (e: Exception) {
        e.printStackTrace()
    }

}

fun RecyclerView.gridLayoutManager(
    margin: Int,
    numColumn: Int,
    includeEdge: Boolean = false,
    includeFirstTop: Boolean = false
) {
    val gridLayoutManager = GridLayoutManager(context, numColumn)
    layoutManager = gridLayoutManager
    addItemDecoration(
        GridSpacingItemDecoration(
            numColumn,
            margin,
            includeEdge,
            false,//GVPreferenceUtil.getLanguageCode(context) == GVConstants.ARABIC_CODE,
            includeFirstTop
        )
    )
}

fun RecyclerView.horizontalLayoutManager(margin: Int, initialPadding: Int = 0) {
    val linearLayoutManager = LinearLayoutManager(context)
    linearLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
    layoutManager = linearLayoutManager
    addItemDecoration(
        HorizontalItemDecoration(
            margin,
            initialPadding,
            false//GVPreferenceUtil.getLanguageCode(context).equals(GVConstants.ARABIC_CODE, true)
        )
    )
}

class PeekingLinearLayoutManager : LinearLayoutManager {
    @JvmOverloads
    constructor(
        context: Context?,
        @RecyclerView.Orientation orientation: Int = RecyclerView.VERTICAL,
        reverseLayout: Boolean = false
    ) : super(context, orientation, reverseLayout)

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int, defStyleRes: Int) : super(
        context,
        attrs,
        defStyleAttr,
        defStyleRes
    )

    override fun generateDefaultLayoutParams() =
        scaledLayoutParams(super.generateDefaultLayoutParams())

    override fun generateLayoutParams(lp: ViewGroup.LayoutParams?) =
        scaledLayoutParams(super.generateLayoutParams(lp))

    override fun generateLayoutParams(c: Context?, attrs: AttributeSet?) =
        scaledLayoutParams(super.generateLayoutParams(c, attrs))

    private fun scaledLayoutParams(layoutParams: RecyclerView.LayoutParams) =
        layoutParams.apply {
            when (orientation) {
                HORIZONTAL -> width = (horizontalSpace * ratio).toInt()
                VERTICAL -> height = (verticalSpace * ratio).toInt()
            }

        }

    private val horizontalSpace get() = width - paddingStart - paddingEnd

    private val verticalSpace get() = height - paddingTop - paddingBottom

    private val ratio = 0.6f // change to 0.7f for 70%
}

class OfferLinearLayoutManager : LinearLayoutManager {
    @JvmOverloads
    constructor(
        context: Context?,
        @RecyclerView.Orientation orientation: Int = RecyclerView.VERTICAL,
        reverseLayout: Boolean = false
    ) : super(context, orientation, reverseLayout)

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int, defStyleRes: Int) : super(
        context,
        attrs,
        defStyleAttr,
        defStyleRes
    )

    override fun generateDefaultLayoutParams() =
        scaledLayoutParams(super.generateDefaultLayoutParams())

    override fun generateLayoutParams(lp: ViewGroup.LayoutParams?) =
        scaledLayoutParams(super.generateLayoutParams(lp))

    override fun generateLayoutParams(c: Context?, attrs: AttributeSet?) =
        scaledLayoutParams(super.generateLayoutParams(c, attrs))

    private fun scaledLayoutParams(layoutParams: RecyclerView.LayoutParams) =
        layoutParams.apply {
            when (orientation) {
                HORIZONTAL -> width = (horizontalSpace * ratio).toInt()
                VERTICAL -> height = (verticalSpace * ratio).toInt()
            }

        }

    private val horizontalSpace get() = width - paddingStart - paddingEnd

    private val verticalSpace get() = height - paddingTop - paddingBottom

    private val ratio = 0.8f // change to 0.7f for 70%
}

fun RecyclerView.setVerticalManager() {
    layoutManager =
        LinearLayoutManager(context)

}

fun RecyclerView.setVerticalItemDecoration(space: Int, initialPadding: Int) {
    addItemDecoration(
        VerticalSpacesItemDecoration(
            space, initialPadding
        )
    )
}

class VerticalSpacesItemDecoration(
    private val space: Int,
    private val initialPadding: Int = 0
) :
    RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val itemPosition = parent.getChildAdapterPosition(view)

        if (itemPosition == 0) {
            outRect.top = if (initialPadding == 0) space else initialPadding
        } else {
            outRect.top = space
        }


        if (itemPosition == state.itemCount - 1) {
            outRect.bottom = if (initialPadding == 0) space else initialPadding
        } else {
            outRect.bottom = 0
        }
    }
}

fun View.getValueFromView(): String? {

    if (this is EditText) {

        return if (this.text.isNotEmpty()) this.text.toString() else null

    } else if (this is TextView) {

        return if (this.text.isNotEmpty()) this.text.toString() else null
    }


    return null
}


fun RecyclerView.verticalLayoutManager(margin: Int, topMargin: Int? = null) {
    val linearLayoutManager = LinearLayoutManager(context)
    linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
    layoutManager = linearLayoutManager
    addItemDecoration(
        VerticalItemDecoration(
            margin,
            topMargin
        )
    )
}

internal class HorizontalItemDecoration(
    private val space: Int,
    private val initialPadding: Int = 0,
    private val isRtl: Boolean = false
) :
    RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val itemPosition = parent.getChildAdapterPosition(view)
        Log.e("ItemPadding", "itemPosition: $itemPosition count: ${parent.adapter?.itemCount}")

        if (isRtl) {

            if (itemPosition == 0) {
                outRect.right = if (initialPadding == 0) space else initialPadding
            } else {
                outRect.right = space
            }

            if (itemPosition == state.itemCount - 1) {
                outRect.left = if (initialPadding == 0) space else initialPadding
            } else {
                outRect.left = 0
            }

        } else {

            if (itemPosition == 0) {
                outRect.left = initialPadding
            } else {
                outRect.left = space
            }

            if (itemPosition == state.itemCount - 1) {
                outRect.right = initialPadding
            } else {
                outRect.right = 0
            }

        }
    }
}

internal class VerticalItemDecoration(
    private val space: Int,
    private val topMargin: Int? = null
) :
    RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        outRect.bottom = space
        if (null != topMargin && parent.getChildAdapterPosition(view) == 0) {
            outRect.top = topMargin
        }
    }
}

class GridSpacingItemDecoration(
    private val spanCount: Int,
    private val spacing: Int,
    private val includeEdge: Boolean,
    private val isRtl: Boolean,
    private val includeFirstTop: Boolean = false
) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val position = parent.getChildAdapterPosition(view) // item position
        val column = position % spanCount // item column

        if (includeEdge) {

            if (isRtl) {
                outRect.right =
                    spacing - column * spacing / spanCount // spacing - column * ((1f / spanCount) * spacing)
                outRect.left =
                    (column + 1) * spacing / spanCount // (column + 1) * ((1f / spanCount) * spacing)
            } else {
                outRect.left =
                    spacing - column * spacing / spanCount // spacing - column * ((1f / spanCount) * spacing)
                outRect.right =
                    (column + 1) * spacing / spanCount // (column + 1) * ((1f / spanCount) * spacing)
            }
            if (position < spanCount) { // top edge
                outRect.top = spacing
            }
            outRect.bottom = spacing // item bottom
        } else {
            if (isRtl) {
                outRect.right =
                    column * spacing / spanCount // column * ((1f / spanCount) * spacing)
                outRect.left =
                    spacing - (column + 1) * spacing / spanCount // spacing - (column + 1) * ((1f /    spanCount) * spacing)
            } else {
                outRect.left = column * spacing / spanCount // column * ((1f / spanCount) * spacing)
                outRect.right =
                    spacing - (column + 1) * spacing / spanCount // spacing - (column + 1) * ((1f /    spanCount) * spacing)
            }

            if (position >= spanCount || includeFirstTop) {
                outRect.top = spacing // item top
            }
        }
    }
}

fun Dialog.alignBottom() {
    val window = window
    window?.setLayout(
        WindowManager.LayoutParams.MATCH_PARENT,
        WindowManager.LayoutParams.MATCH_PARENT
    )
    window?.setGravity(Gravity.BOTTOM)
}

fun DialogFragment.alignBottom() {
    val window = this.dialog?.window
    window?.setLayout(
        WindowManager.LayoutParams.MATCH_PARENT,
        WindowManager.LayoutParams.WRAP_CONTENT
    )
    window?.setGravity(Gravity.BOTTOM)
}

fun Dialog.setSize(activity: Activity) {
    val wm =
        context.getSystemService(Context.WINDOW_SERVICE) as WindowManager // for activity use context instead of getActivity()
    val display = wm.defaultDisplay // getting the screen size of device
    val size = Point()
    display.getSize(size)
    val width = size.x - 80  // Set your heights
    val height = WindowManager.LayoutParams.WRAP_CONTENT // set your widths

    val lp = window?.attributes
    lp?.width = width
    lp?.height = height
    window!!.attributes = lp
    window!!.attributes.dimAmount = 0.9f
    window!!.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)

//    val fast = fastblur(takeScreenShot(activity), 10)
//
//    val draw = BitmapDrawable(context.resources, fast)
//    window?.setBackgroundDrawable(draw)
}

/**
 * Pagination class to add more items to the list when reach the last item.
 */
abstract class PaginationScrollListener
/**
 * Supporting only LinearLayoutManager for now.
 *
 * @param layoutManager
 */
    (var layoutManager: GridLayoutManager) : RecyclerView.OnScrollListener() {

    abstract fun isLastPage(): Boolean

    abstract fun isLoading(): Boolean

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        val visibleItemCount = layoutManager.childCount
        val totalItemCount = layoutManager.itemCount
        val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()

        if (!isLoading() && !isLastPage()) {
            if (visibleItemCount + firstVisibleItemPosition >= totalItemCount && firstVisibleItemPosition >= 0) {
                loadMoreItems()
            }//                    && totalItemCount >= ClothesFragment.itemsCount
        }
    }


    abstract fun loadMoreItems()
}

abstract class LinearPaginationScrollListener
/**
 * Supporting only LinearLayoutManager for now.
 *
 * @param layoutManager
 */
    (var layoutManager: LinearLayoutManager) : RecyclerView.OnScrollListener() {
    abstract fun isLastPage(): Boolean

    abstract fun isLoading(): Boolean

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        val visibleItemCount = layoutManager.childCount
        val totalItemCount = layoutManager.itemCount
        val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()

        if (!isLoading() && !isLastPage()) {
            if (visibleItemCount + firstVisibleItemPosition >= totalItemCount && firstVisibleItemPosition >= 0) {
                loadMoreItems()
            }
        }
    }

    abstract fun loadMoreItems()
}

fun EditText.setAsCreditCard() {
    addTextChangedListener(object : TextWatcher {
        private var isDelete = false
        override fun afterTextChanged(p0: Editable?) {
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            isDelete = before != 0
        }

        override fun onTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {
            val source = s.toString()
            val length = source.length

            val stringBuilder = StringBuilder()
            stringBuilder.append(source)

            if (length > 0 && length % 5 == 0) {
                if (isDelete)
                    stringBuilder.deleteCharAt(length - 1)
                else
                    stringBuilder.insert(length - 1, " ")

                setText(stringBuilder)
                setSelection(text.length)

            }

        }
    })
}

fun View.createBitmap(): Bitmap {
    layoutParams = RelativeLayout.LayoutParams(
        RelativeLayout.LayoutParams.WRAP_CONTENT,
        RelativeLayout.LayoutParams.WRAP_CONTENT
    )
    measure(
        View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
        View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
    )
    layout(0, 0, measuredWidth, measuredHeight)
    val bitmap = Bitmap.createBitmap(
        measuredWidth,
        measuredHeight,
        Bitmap.Config.ARGB_8888
    )

    val c = Canvas(bitmap)
    layout(left, top, right, bottom)
    draw(c)
    return bitmap
}

fun String.convertHtmlToString(): String {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        return Html.fromHtml(this, Html.FROM_HTML_MODE_COMPACT).toString();
    } else {
        return Html.fromHtml(this).toString();
    }
}

fun Any.formattedNumber(): String {
    try {
        val nf: NumberFormat = NumberFormat.getNumberInstance(Locale.ENGLISH)
        val df = nf as DecimalFormat

//    df.applyLocalizedPattern(("#,##0.00"))
        if (this is String)
            return df.format(BigDecimal(this.toDouble()).setScale(2, RoundingMode.HALF_EVEN))
        if (this is Int)
            return df.format(BigDecimal(this.toDouble()).setScale(2, RoundingMode.HALF_EVEN))
        if (this is Double)
            return df.format(BigDecimal(this.toDouble()).setScale(2, RoundingMode.HALF_EVEN))
        if (this is Float)
            return df.format(BigDecimal(this.toDouble()).setScale(2, RoundingMode.HALF_EVEN))
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return "null";
}

fun TextView.setDrawableStart(id: Int) {
    val img = CommonUtils.getDrawable(this.context, id)
    this.setCompoundDrawablesWithIntrinsicBounds(img, null, null, null)
}

fun RecyclerView.runLayoutAnimation() {
    val controller =
        AnimationUtils.loadLayoutAnimation(
            context,
            R.anim.layout_animation_fall_down
        ) as LayoutAnimationController
    layoutAnimation = controller
    adapter?.notifyDataSetChanged()
    scheduleLayoutAnimation()
}

fun RecyclerView.runLayoutAnimationSlow() {
    val controller =
        AnimationUtils.loadLayoutAnimation(
            context,
            R.anim.layout_animation_fall_down_slow
        ) as LayoutAnimationController
    layoutAnimation = controller
    adapter?.notifyDataSetChanged()
    scheduleLayoutAnimation()
}


fun RecyclerView.setHorizontalLayoutManager() {
    layoutManager =
        LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
}

fun RecyclerView.startSmoothScroll() {

    layoutManager =
        LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
}

fun RecyclerView.setVerticalLayoutManager() {
    layoutManager =
        LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
}

fun RecyclerView.setHorizontalItemDecoration(space: Int, initialPadding: Int, isRtl: Boolean) {
    addItemDecoration(
        HorizontalSpacesItemDecoration(
            space, initialPadding, isRtl
        )
    )
}

fun RecyclerView.setSnap() {
    LinearSnapHelper().attachToRecyclerView(this)
}

internal class HorizontalSpacesItemDecoration(
    private val space: Int,
    private val initialPadding: Int = 0,
    private val isRtl: Boolean = false
) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val itemPosition = parent.getChildAdapterPosition(view)
        Log.e("ItemPadding", "itemPosition: $itemPosition count: ${parent.adapter?.itemCount}")

        if (isRtl) {
            if (itemPosition == 0) {
                outRect.left = if (initialPadding == 0) space else initialPadding
                outRect.right = initialPadding
            } else {
                outRect.left = space
            }
        } else {
            if (itemPosition == 0) {
                outRect.right = if (initialPadding == 0) space else initialPadding
                outRect.left = initialPadding
            } else {
                outRect.right = space
            }

        }
    }
}

class SimpleDividerItemDecoration(
    context: Context,
    color: Int,
    var hideLastItemDivider: Boolean = false
) : RecyclerView.ItemDecoration() {
    private val mDivider: Drawable = ColorDrawable(ContextCompat.getColor(context, color))
    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        val left = parent.paddingLeft
        val right = parent.width - parent.paddingRight
        var childCount = parent.childCount
        if (hideLastItemDivider)
            childCount -= 1
        for (i in 0 until childCount) {
            val child: View = parent.getChildAt(i)
            val params = child.layoutParams as RecyclerView.LayoutParams
            val top: Int = child.bottom + params.bottomMargin
            val bottom = top + CommonUtils.convertDpToPixel(1, parent.context).toInt()
            mDivider.setBounds(left, top, right, bottom)
            mDivider.draw(c)
        }
    }
}

fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        }

        override fun afterTextChanged(editable: Editable?) {
            afterTextChanged.invoke(editable.toString())
        }
    })
}

fun EditText.addWordCounter(func: (wordCount: Int?) -> Unit) {

    addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {}

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            val trim = s?.trim()
            val wordCount = if (trim?.isEmpty()!! or (false)) 0 else trim.split("\\s+".toRegex())
                .dropLastWhile { it.isEmpty() }.toTypedArray().size

            func(wordCount)
        }
    })
}

fun EditText.beforeTextChanged(beforeTextChanged: (String, Int) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            beforeTextChanged.invoke(s.toString(), count)
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        }

        override fun afterTextChanged(editable: Editable?) {

        }
    })
}

fun TabLayout.setupWithViewPager2(viewPager: ViewPager2, labels: List<String>) {
    if (labels.size != viewPager.adapter?.itemCount)
        throw Exception("The size of list and the tab count should be equal!")
    TabLayoutMediator(this, viewPager) { tab, position ->
        tab.text = labels[position]
    }.attach()
}
