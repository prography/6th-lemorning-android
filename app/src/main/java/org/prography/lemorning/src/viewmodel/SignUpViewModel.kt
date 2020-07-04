package org.prography.lemorning.src.viewmodel

import androidx.lifecycle.MutableLiveData
import org.prography.lemorning.BaseViewModel
import org.prography.lemorning.utils.base.BaseEvent

class SignUpViewModel : BaseViewModel() {

    var navTo : MutableLiveData<BaseEvent<Int>> = MutableLiveData()
}