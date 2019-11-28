package com.example.projekt1admin

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.firebasetest.roomNumber
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_login.view.*

/**
 * Created by VickY on 07-09-2017.
 */
class FragmentResult : Fragment(){

    val TAG = "FragmentLogin"

    private lateinit var database: DatabaseReference
    private var currentQuestion : String = ""
    private var mRecyclerView: RecyclerView? = null
    private var mAdapter: RecyclerView.Adapter<*>? = null

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

        val rootView = inflater.inflate(R.layout.fragment_result,container,false)

        val args = arguments
        val questionName = args?.getString("Question Name","Something went wrong my friend")

        currentQuestion = questionName.toString()

        val txtQuestion : TextView = rootView.findViewById(R.id.txt_questionName)
        txtQuestion.text = "Question: " + questionName

        val txtTimer : TextView = rootView.findViewById(R.id.timer)

        val database = FirebaseDatabase.getInstance()
        val myRef = database.reference.child("Groups").child(roomNumber).child(questionName.toString())


        myRef.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                var remainingTime = dataSnapshot.child("time").value
                txtTimer.setText("Time remaining: " + remainingTime)
            }
        })

        mRecyclerView = rootView.findViewById(R.id.rvVotedItems) as RecyclerView
        mRecyclerView!!.layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.VERTICAL, false)

        val users : ArrayList<User> = loadwithdata()

        mAdapter = ResultAdapter(users)
        mRecyclerView!!.adapter = mAdapter

        return rootView
    }

    fun loadwithdata() : ArrayList<User>{
        val users : ArrayList<User> = ArrayList()

        val database = FirebaseDatabase.getInstance()
        val myRef = database.reference.child("Groups").child(roomNumber).child(currentQuestion).child("Votes")


        myRef.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                users.clear()
                for (ds in dataSnapshot.children) {
                    val current = ds.getValue(User::class.java)
                    val nameC = current?.userID
                    val voteC  = current?.Vote
                    users.add(User(nameC,voteC))
                    mAdapter?.notifyDataSetChanged()
                }

            }
        })
        return users
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