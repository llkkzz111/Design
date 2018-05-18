package com.liuz.design

import android.view.Window
import android.view.WindowManager
import com.liuz.lotus.base.TranslucentBarBaseActivity

/**
 * An activity representing a single Item detail screen. This
 * activity is only used on narrow width devices. On tablet-size devices,
 * item details are presented side-by-side with a list of items
 * in a [ItemListActivity].
 */
class ItemDetailActivity : TranslucentBarBaseActivity() {


    override fun getLayoutResId(): Int {
        return R.layout.activity_item_detail
    }

    override fun initEventAndData() {
        // Show the Up button in the action bar.
        setTranslucent(this)
    }
}
