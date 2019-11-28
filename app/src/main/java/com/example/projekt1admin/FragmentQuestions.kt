package com.example.projekt1admin

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.firebasetest.roomNumber
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.fragment_questions.view.*
import kotlinx.android.synthetic.main.question_list.view.*

/**
 * Created by VickY on 07-09-2017.
 */
class FragmentQuestions : Fragment(){

    val TAG = "FragmentEdit"

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

    fun loadwithdata() : ArrayList<Question>{
        val questions : ArrayList<Question> = ArrayList()

        val database = FirebaseDatabase.getInstance()
        val myRef = database.reference.child("Groups").child(roomNumber)


        myRef.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                questions.clear()
                for (ds in dataSnapshot.children) {
                    val current = ds.getValue(Question::class.java)
                    val questionC = current?.question
                    val timeC  = current?.time
                    val activeC = current?.active

                    questions.add(Question(questionC,timeC,activeC))
                    mAdapter?.notifyDataSetChanged()
                }

            }
        })


        return questions
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        Log.d(TAG,"onCreateView")

        val rootView = inflater.inflate(R.layout.fragment_questions,container,false)

        rootView.btn_add.setOnClickListener {
            var newFragment : Fragment = FragmentEdit()
            var transaction : FragmentTransaction = fragmentManager!!.beginTransaction()
            transaction.replace(R.id.fragment_holder,newFragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }


        mRecyclerView = rootView.findViewById(R.id.rvItems) as RecyclerView
        mRecyclerView!!.layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.VERTICAL, false)

        val questions : ArrayList<Question> = loadwithdata()

        mAdapter = QuestionAdapter(questions)
        mRecyclerView!!.adapter = mAdapter

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