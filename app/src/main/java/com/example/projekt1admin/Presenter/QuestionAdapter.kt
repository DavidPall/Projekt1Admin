package com.example.projekt1admin.Presenter

import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.projekt1admin.View.FragmentResult
import com.example.projekt1admin.Model.Question
import com.example.projekt1admin.Model.roomNumber
import com.example.projekt1admin.R
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.util.concurrent.TimeUnit

class QuestionAdapter(var questionList:ArrayList<Question>) : RecyclerView.Adapter<QuestionAdapter.ViewHolder>() {

    private lateinit var database: DatabaseReference


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.question_list, parent, false)

        return ViewHolder(v)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val currentQuestion : Question = questionList[position]

        holder.question.text = currentQuestion.question

        ///Activating the question if it isn't inactive yet and going to its result and start the question's timer
        holder.btn_act.setOnClickListener {
            if(currentQuestion.active != "inactive") {
                startTimer(currentQuestion)

                //sending the roomNumber and currentQuestion to the next fragment
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

        //simply going to the result screen for the right question
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

    //Modifying the question's activity to true and updating the remaining time in the firebase
    private fun updateRunningQuestion(question: Question){
        val database = FirebaseDatabase.getInstance()
        val myRef = database.reference.child("Groups").child(roomNumber).child(question.question.toString())

        myRef.ref.child("active").setValue("true")
        myRef.ref.child("time").setValue(question.time)
    }

    //When the timer is ended setting the activity to inactive so you can't activate it again and modifying the remaining time to 0
    private fun updateEndingQuestion(question: Question){
        val database = FirebaseDatabase.getInstance()
        val myRef = database.reference.child("Groups").child(roomNumber).child(question.question.toString())

        myRef.ref.child("time").setValue("0")
        myRef.ref.child("active").setValue("inactive")
    }


    //Initializing the timer
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