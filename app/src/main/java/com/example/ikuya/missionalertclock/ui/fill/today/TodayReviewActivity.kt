package com.example.ikuya.missionalertclock.ui.fill.today

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.ikuya.missionalertclock.R
import com.example.ikuya.missionalertclock.data.DEVELOPEDGOAL
import com.example.ikuya.missionalertclock.data.FEELING
import com.example.ikuya.missionalertclock.data.SleepData
import com.example.ikuya.missionalertclock.ui.MainActivity
import com.example.ikuya.missionalertclock.ui.MainActivity.Companion.REQUEST_CODE_LOGITEM
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.todays_review_activity.*

class TodayReviewActivity:AppCompatActivity(){
    lateinit var viewModel:TodayReviewActivityViewModel


    //ぶっちゃけ必要性がわからない
    companion object {
        fun newInstance(): TodayReviewActivity {
            val f = TodayReviewActivity()
            return f
        }
       const val EXTRA_KEY_DATA = "data"
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.todays_review_activity)
        setSupportActionBar(toolbar)


        viewModel  = ViewModelProvider(this).get(TodayReviewActivityViewModel::class.java)

        radio_group.check(R.id.radioButton)

        cancelreviewtomainBtn.setOnClickListener {
            val developedgoal = GoalFromRadioId(radio_group.checkedRadioButtonId)
            val donething = editTextTextMultiLine.text.toString()
            val feeling = feelingFromSpinner(feeling_spinner.selectedItemPosition)
            val sleepdata = SleepData(developedgoal,donething,feeling)

            viewModel.changeLog(sleepdata)
        }


        //本当はボタンで画面遷移を実装するべきではない。NavigationComponentでの実装がこのましい。


        cancelreviewtomainBtn.setOnClickListener {
            val intent = Intent(this,MainActivity::class.java)
            startActivityForResult(intent, REQUEST_CODE_LOGITEM)
        }

        viewModel.sleepLog.observe(this, Observer {
            val dataIntent = Intent()
            dataIntent.putExtra(EXTRA_KEY_DATA, it)
            setResult(RESULT_OK, dataIntent)
            finish()
        })


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