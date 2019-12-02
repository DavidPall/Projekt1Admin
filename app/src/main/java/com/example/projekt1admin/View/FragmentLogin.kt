package com.example.projekt1admin.View

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.projekt1admin.Model.roomNumber
import com.example.projekt1admin.R
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_login.view.*

/**
 * Created by VickY on 07-09-2017.
 */
class FragmentLogin : Fragment(){

    val TAG = "FragmentLogin"

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

        val rootView = inflater.inflate(R.layout.fragment_login,container,false)

//        var exist = false

        database = FirebaseDatabase.getInstance().reference

//        val databaseRef = FirebaseDatabase.getInstance()
//        val myRef = databaseRef.reference.child("Groups").orderByKey()

//        myRef.addValueEventListener(object : ValueEventListener {
//            override fun onCancelled(p0: DatabaseError) {
//
//            }
//            override fun onDataChange(dataSnapshot: DataSnapshot){
//                for(ds in dataSnapshot.children){
//                    if(ds.child(rootView.edt_room_id.text.toString()).exists()){
//                        exist = true
//                    }
//                }
//            }
//        })


        rootView.btn_login.setOnClickListener {
            if (rootView.edt_name.text.isNotEmpty() && rootView.edt_room_id.text.isNotEmpty()) {

                //Setting the roomNumber from the input
                roomNumber = rootView.edt_room_id.text.toString()
//                if(!exist){
//                    database.child("Groups").setValue(rootView.edt_room_id.text.toString())
//                }

                //Moving to the Questions fragment
                var newFragment : Fragment = FragmentQuestions()
                var transaction : FragmentTransaction = fragmentManager!!.beginTransaction()
                transaction.replace(R.id.fragment_holder,newFragment)
                transaction.addToBackStack(null)
                transaction.commit()
            } else {
                Toast.makeText(this.context, "Please fill out the fields", Toast.LENGTH_LONG)
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