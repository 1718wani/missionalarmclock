package com.example.ikuya.missionalertclock.ui.alertset

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.annotation.Nullable
import androidx.fragment.app.Fragment
import com.example.ikuya.missionalertclock.R
import com.example.ikuya.missionalertclock.camera.TakePhotoActivity


class TimersetFragment: Fragment() {

//    private lateinit var binding: TimersetFragmentBinding
//    private lateinit var viewModel: TimersetFragmentViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.timerset_fragment, container, false)
    }

    override fun onViewCreated(view: View, @Nullable savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val appButton: Button = view.findViewById(R.id.timerstartbtn)
        appButton.setOnClickListener(View.OnClickListener {
            val intent = Intent(activity, TakePhotoActivity::class.java)
            startActivity(intent)
        })
    }

//    binding = DataBindingUtil.inflate(
//                inflater,
//                R.layout.game_fragment,
//                container,
//                false
//    )
//
//
//    fun showTimePickerDialog(v: View) {
//        TimersetDialogFragment().show(supportFragmentManager, "timePicker")
//    }



}

//class Tab01Fragment : Fragment() {
//    private var cnt = 0
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_tab01, container, false)
//    }
//
//    override fun onViewCreated(view: View, @Nullable savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        val args = arguments
//        if (args != null) {
//            val count = args.getInt("Counter")
//            cnt = count + 1
//        }
//        val appButton: Button = view.findViewById(R.id.Button)
//        appButton.setOnClickListener(View.OnClickListener {
//            val intent = Intent(activity, AppComDetailsActivity::class.java)
//            startActivity(intent)
//        })
//    }
//
//    companion object {
//            fun newInstance(count: Int): Tab01Fragment {
//                //Fragment インスタンス化
//                val tab01Fragment = Tab01Fragment()
//
//                //Bundleにパラメータを設定
//                val args = Bundle()
//                args.putInt("Counter", count)
//                tab01Fragment.arguments = args
//                return tab01Fragment
//            }
//    }
//}