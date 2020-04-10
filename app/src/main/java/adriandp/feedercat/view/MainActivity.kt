package adriandp.feedercat.view

import adriandp.feedercat.BR
import adriandp.feedercat.R
import adriandp.feedercat.databinding.ActivityMainBinding
import adriandp.feedercat.view.viewmodel.MainActivityVM
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil


class MainActivity : AppCompatActivity() {
    private lateinit var mBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        mBinding.setVariable(BR.model, MainActivityVM(this))
    }
}
