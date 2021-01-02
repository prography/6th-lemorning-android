package org.prography.lemorning

import android.app.Dialog
import android.app.ProgressDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.transition.ChangeBounds
import org.prography.lemorning.src.utils.components.NetworkEvent
import org.prography.lemorning.src.utils.components.SimpleMessageDialog

abstract class BaseFragment<B : ViewDataBinding, VM : BaseViewModel, PVM: BaseViewModel>(@LayoutRes val layoutId: Int)
  : Fragment(layoutId), IBaseFragment<VM, PVM> {

  protected lateinit var binding : B
  protected lateinit var vm : VM
  protected lateinit var pvm : PVM

  protected var messageDialog: SimpleMessageDialog? = null
  var progressDialog: ProgressDialog? = null

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    sharedElementEnterTransition = ChangeBounds().apply {
      duration = 300
    }
    sharedElementReturnTransition = ChangeBounds().apply {
      duration = 300
    }
    return super.onCreateView(inflater, container, savedInstanceState)
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    binding = DataBindingUtil.bind(view)!!
    vm = getViewModel()
    pvm = getParentViewModel()
    binding.setVariable(BR.vm, vm)
    binding.setVariable(BR.pvm, pvm)
    binding.lifecycleOwner = this

    vm.toastEvent.observe(viewLifecycleOwner) { it.get()?.let { showToast(it) } }
    vm.alertEvent.observe(viewLifecycleOwner) { it.get()?.let { showSimpleMessageDialog(it) } }
    vm.networkEvent.observe(viewLifecycleOwner) { onNetworkEventChanged(it) }

    initView(savedInstanceState)
  }

  open fun showToast(message: String?) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
  }

  open fun showSimpleMessageDialog(
    message: String?,
    btnText: String? = getString(R.string.confirm),
    isCancelable: Boolean = true,
    onClick: ((Dialog) -> Unit)? = null
  ) {
    messageDialog = SimpleMessageDialog(requireActivity(), message, btnText, isCancelable, onClick)
    messageDialog?.show()
  }

  open fun showProgressDialog() {
    if (progressDialog == null) {
      progressDialog = ProgressDialog(requireActivity(), android.R.style.Theme_DeviceDefault_Dialog).apply {
        setMessage(getString(R.string.loading))
        isIndeterminate = true
        setCancelable(false)
      }
    }
    progressDialog?.show()
  }

  open fun hideProgressDialog() = progressDialog?.apply { if (isShowing) dismiss() }

  override fun onStop() {
    super.onStop()
    hideProgressDialog()
  }

  override fun onNetworkEventChanged(state: NetworkEvent.NetworkState?) {
    when (state) {
      NetworkEvent.NetworkState.LOADING -> showProgressDialog()
      NetworkEvent.NetworkState.COMPLETE -> hideProgressDialog()
    }
  }
}

interface IBaseFragment<VM : BaseViewModel, PVM: BaseViewModel> {
  fun getViewModel() : VM

  fun getParentViewModel(): PVM

  fun initView(savedInstanceState: Bundle?)

  fun onNetworkEventChanged(state: NetworkEvent.NetworkState?)
}