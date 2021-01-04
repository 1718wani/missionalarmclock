package com.example.ikuya.missionalertclock.ui.fillout.today

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.impl.utils.Optional.of
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.ViewModelStores.of
import com.example.ikuya.missionalertclock.R
import com.example.ikuya.missionalertclock.data.DEVELOPEDGOAL
import com.example.ikuya.missionalertclock.data.FEELING
import com.example.ikuya.missionalertclock.data.Sleepdata
import com.example.ikuya.missionalertclock.ui.MainActivity
import com.google.android.gms.common.api.internal.ActivityLifecycleObserver.of
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.todays_review_activity.*
import java.text.FieldPosition
import java.util.EnumSet.of
import java.util.stream.Stream.of

class TodaysReviewActivity:AppCompatActivity(){
    lateinit var viewModel:TodaysReviewActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.todays_review_activity)
        setSupportActionBar(toolbar)


        viewModel  = ViewModelProvider(this).get(TodaysReviewActivityViewModel::class.java)

        radio_group.check(R.id.radioButton)

        cancelreviewtomainBtn.setOnClickListener {
            val developedgoal = GoalFromRadioId(radio_group.checkedRadioButtonId)
            val donething = editTextTextMultiLine.text.toString()
            val feeling = feelingFromSpinner(feeling_spinner.selectedItemPosition)
            val sleepdata = Sleepdata(developedgoal,donething,feeling)

            viewModel.changeLog(sleepdata)
        }


        //本当はボタンで画面遷移を実装するべきではない。NavigationComponentでの実装がこのましい。


        cancelreviewtomainBtn.setOnClickListener {
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
        }
    }

    //順番どおりの実装になるため順番がよく入れ替わる際は要検討

    private fun GoalFromRadioId(checkedRadioButtonId: Int): DEVELOPEDGOAL {
        return when (checkedRadioButtonId) {
            R.id.radioButton -> DEVELOPEDGOAL.GOALYES
            else -> DEVELOPEDGOAL.GOALNO
        }
    }

    private fun feelingFromSpinner(selectedItemPosition: Int):FEELING{
        return FEELING.values()[selectedItemPosition]
    }






}