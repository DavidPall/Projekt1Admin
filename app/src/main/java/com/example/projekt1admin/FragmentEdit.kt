package com.example.projekt1admin

import android.content.Context
import android.content.Intent
import android.graphics.drawable.ClipDrawable.HORIZONTAL
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.GridLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.firebasetest.roomNumber
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_edit.view.*
import kotlinx.android.synthetic.main.fragment_login.view.*
import java.lang.Double.parseDouble
import java.lang.NumberFormatException

/**
 * Created by VickY on 07-09-2017.
 */
class FragmentEdit : Fragment(){

    val TAG = "FragmentEdit"

    private lateinit var database: DatabaseReference


    override fun onAttach(context: Context) {
        Log.d(TAG,"onAttach")
        super.onAttach(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(TAG,"onCreate")
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        Log.d(TAG,"onCreateView")

        val rootView = inflater.inflate(R.layout.fragment_edit,container,false)



        database = FirebaseDatabase.getInstance().reference

        rootView.btn_add.setOnClickListener {

            if (rootView.edt_question.text.isNotEmpty() && rootView.edt_time.text.isNotEmpty()) {
                database.child("Groups").child(roomNumber).child(rootView.edt_question.text.toString()).child("time").setValue(rootView.edt_time.text.toString())
                database.child("Groups").child(roomNumber).child(rootView.edt_question.text.toString()).child("question").setValue(rootView.edt_question.text.toString())
                database.child("Groups").child(roomNumber).child(rootView.edt_question.text.toString()).child("active").setValue("false")

                var newFragment : Fragment = FragmentQuestions()
                var transaction : FragmentTransaction = fragmentManager!!.beginTransaction()
                transaction.replace(R.id.fragment_holder,newFragment)
                transaction.addToBackStack(null)
                transaction.commit()
            } else {
                    Toast.makeText(this.context, "Please fill out the fields!", Toast.LENGTH_LONG)
                        .show()
            }
        }

        return rootView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        Log.d(TAG,"onActivityCreated")
        super.onActivityCreated(savedInstanceState)
    }

    override fun onStart() {
        Log.d(TAG,"onStart")
        super.onStart()
    }

    override fun onResume() {
        Log.d(TAG,"onResume")
        super.onResume()
    }

    override fun onPause() {
        Log.d(TAG,"onPause")
        super.onPause()
    }

    override fun onStop() {
        Log.d(TAG,"onStop")
        super.onStop()
    }

    override fun onDestroyView() {
        Log.d(TAG,"onDestroyView")
        super.onDestroyView()
    }

    override fun onDestroy() {
        Log.d(TAG,"onDestroy")
        super.onDestroy()
    }

    override fun onDetach() {
        Log.d(TAG,"onDetach")
        super.onDetach()
    }
}