package com.example.projekt1admin

import android.content.IntentSender
import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.RecyclerView
import com.example.firebasetest.roomNumber
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.util.concurrent.TimeUnit
import kotlinx.android.synthetic.main.question_list.view.*

class QuestionAdapter(var questionList:ArrayList<Question>) : RecyclerView.Adapter<QuestionAdapter.ViewHolder>() {

    private lateinit var database: DatabaseReference


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.question_list, parent, false)



        return ViewHolder(v)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val currentQuestion : Question = questionList[position]

        holder.question.text = currentQuestion.question




        holder.btn_act.setOnClickListener {
            if(currentQuestion.active != "inactive") {
                startTimer(currentQuestion)

                val bundle = Bundle()
                bundle.putString("Question Name", currentQuestion.question)
                bundle.putString("Group number", roomNumber)//parameters are (key, value).
                val activity = holder.manager as AppCompatActivity
                val myFragment = FragmentResult()
                myFragment.setArguments(bundle)
                activity.supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_holder, myFragment).addToBackStack(null).commit()
            } else {
                Toast.makeText(it.context,"The question is inactive!",Toast.LENGTH_SHORT).show()
            }

        }

        holder.btn_res.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("Question Name", currentQuestion.question)
            bundle.putString("Group number", roomNumber)//parameters are (key, value).
            val activity = holder.manager as AppCompatActivity
            val myFragment = FragmentResult()
            myFragment.setArguments(bundle)
            activity.supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_holder, myFragment).addToBackStack(null).commit()
        }


    }

    private fun updateRunningQuestion(question: Question){
        val database = FirebaseDatabase.getInstance()
        val myRef = database.reference.child("Groups").child(roomNumber).child(question.question.toString())

        myRef.ref.child("active").setValue("true")
        myRef.ref.child("time").setValue(question.time)
    }

    private fun updateEndingQuestion(question: Question){
        val database = FirebaseDatabase.getInstance()
        val myRef = database.reference.child("Groups").child(roomNumber).child(question.question.toString())

        myRef.ref.child("time").setValue("0")
        myRef.ref.child("active").setValue("inactive")
    }



    private fun startTimer(question: Question){
        object : CountDownTimer(1000 * (question.time)!!.toLong(), 1000) {

            override fun onTick(millisUntilFinished: Long) {
                question.time = ((TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished)).toString())
                updateRunningQuestion(question)
                notifyDataSetChanged()
            }

            override fun onFinish() {
                updateEndingQuestion(question)
                notifyDataSetChanged()
            }
        }.start()
    }


    override fun getItemCount(): Int {
        return questionList.size
    }


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal var question: TextView
        internal var btn_act : Button
        internal var btn_res : Button
        internal var manager = itemView.context

        init {
            question = itemView.findViewById<View>(R.id.txt_question) as TextView
            btn_act = itemView.findViewById<View>(R.id.btn_activate) as Button
            btn_res = itemView.findViewById<View>(R.id.btn_result) as Button
        }


    }
}