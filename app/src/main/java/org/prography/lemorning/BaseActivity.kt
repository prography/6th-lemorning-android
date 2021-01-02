package org.prography.lemorning

import android.app.Dialog
import android.app.ProgressDialog
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import org.prography.lemorning.src.utils.components.NetworkEvent
import org.prography.lemorning.src.utils.components.SimpleMessageDialog

abstract class BaseActivity<B : ViewDataBinding, VM : BaseViewModel>(@LayoutRes val layoutId: Int)
  : AppCompatActivity(), IBaseActivity<VM>
{
  protected lateinit var binding: B
  protected lateinit var vm: VM

  protected var progressDialog: ProgressDialog? = null
  protected var messageDialog: SimpleMessageDialog? = null

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = DataBindingUtil.setContentView(this, layoutId)
    vm = getViewModel()
    binding.setVariable(BR.vm, vm)
    binding.lifecycleOwner = this

    vm.toastEvent.observe(this) { it.get()?.let { showToast(it) } }
    vm.alertEvent.observe(this) { it.get()?.let { showSimpleMessageDialog(it) } }
    vm.networkEvent.observe(this) { onNetworkEventChanged(it) }

    initView(savedInstanceState)
  }

  open fun showToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
  }

  open fun showProgressDialog() {
    if (progressDialog == null) {
      progressDialog = ProgressDialog(this, android.R.style.Theme_DeviceDefault_Dialog).apply {
        setMessage(getString(R.string.loading))
        isIndeterminate = true
        setCancelable(false)
      }
    }
    progressDialog?.show()
  }

  open fun hideProgressDialog() = progressDialog?.apply { if (isShowing) dismiss() }

  open fun showSimpleMessageDialog(
    message: String?,
    btnText: String? = getString(R.string.confirm),
    isCancelable: Boolean = true,
    onClick: ((Dialog) -> Unit)? = null
  ) {
    messageDialog = SimpleMessageDialog(this, message, btnText, isCancelable, onClick)
    messageDialog?.show()
  }

  override fun onNetworkEventChanged(state: NetworkEvent.NetworkState?) {
    when (state) {
      NetworkEvent.NetworkState.LOADING -> showProgressDialog()
      NetworkEvent.NetworkState.COMPLETE -> hideProgressDialog()
    }
  }
}

interface IBaseActivity<VM> {
  fun getViewModel(): VM

  fun initView(savedInstanceState: Bundle?)

  fun onNetworkEventChanged(state: NetworkEvent.NetworkState?)
}