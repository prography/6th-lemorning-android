package org.prography.lemorning

import android.app.ActivityOptions
import android.app.ProgressDialog
import android.content.Context
import android.content.res.Configuration
import android.graphics.Rect
import android.os.Bundle
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.navigation.ActivityNavigator
import androidx.transition.ChangeBounds
import org.prography.lemorning.ApplicationClass.Companion.LANGUAGE
import org.prography.lemorning.ApplicationClass.Companion.sSharedPreferences
import java.util.*

abstract class BaseActivity<B : ViewDataBinding, VM : BaseViewModel> : AppCompatActivity(), BaseActivityView<VM> {

    protected lateinit var binding : B
    protected lateinit var viewmodel : VM

    var mProgressDialog: ProgressDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, layoutId)
        viewmodel = getViewModel()
        binding.setVariable(BR.viewmodel, viewmodel)
        binding.lifecycleOwner = this


        initLocale(baseContext)
        initView(savedInstanceState)
    }

    override fun finish() {
        super.finish()
        ActivityNavigator.applyPopAnimationsToPendingTransition(this)
    }

    open fun showToast(message: String?) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    open fun showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = ProgressDialog(this)
            mProgressDialog!!.setMessage(getString(R.string.loading))
            mProgressDialog!!.setCancelable(false)
            mProgressDialog!!.setIndeterminate(true)
        }
        if (!isFinishing) {
            mProgressDialog!!.show()
        }
    }

    open fun hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog!!.isShowing()) {
            mProgressDialog!!.dismiss()
        }
    }

    override fun onStop() {
        super.onStop()
        hideProgressDialog()
    }

    override fun initLocale(context : Context) {
        val localeTag: String? = sSharedPreferences.getString(LANGUAGE, null)
        if (localeTag != null) {
            Locale.setDefault(Locale.forLanguageTag(localeTag))
            val config = Configuration(resources.configuration)
            config.setLocale(Locale.forLanguageTag(localeTag))
            context.getResources()
                .updateConfiguration(config, context.getResources().getDisplayMetrics())
        }
    }


    /* 화면 터치시 키보드 레이아웃 감추기 코드 */
    private var firstPointX = 0f
    private var firstPointY = 0f
    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        if (ev.action == MotionEvent.ACTION_UP) {
            val distX = Math.abs(ev.x - firstPointX)
            val distY = Math.abs(ev.y - firstPointY)
            if (distX < 8 || distY < 8) {
                val view = currentFocus
                if (view is EditText) {
                    val outRect = Rect()
                    view.getGlobalVisibleRect(outRect)
                    if (!outRect.contains(ev.rawX.toInt(), ev.rawY.toInt())) {
                        val inputMethodManager =
                            getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0)
                    }
                }
            }
        } else if (ev.action == MotionEvent.ACTION_DOWN) {
            firstPointX = ev.x
            firstPointY = ev.y
        }
        return super.dispatchTouchEvent(ev)
    }
}

interface BaseActivityView<VM : BaseViewModel> {

    @get:LayoutRes
    val layoutId: Int

    fun getViewModel() : VM

    fun initView(savedInstanceState: Bundle?)

    fun initLocale(context : Context)
}