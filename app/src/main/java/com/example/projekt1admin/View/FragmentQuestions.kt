package com.example.projekt1admin.View

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.projekt1admin.Model.Question
import com.example.projekt1admin.Model.roomNumber
import com.example.projekt1admin.Presenter.QuestionAdapter
import com.example.projekt1admin.R
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.fragment_questions.view.*

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

    // Function for loading the data into an ArrayList from the database from the selected group(group == roomNumber)
    fun loadwithdata() : ArrayList<Question>{
        val questions : ArrayList<Question> = ArrayList()

        val database = FirebaseDatabase.getInstance()
        val myRef = database.reference.child("Groups").child(roomNumber)


        myRef.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                //clearing the list so it won't repeat the same elements over time then filling up with the questions
                questions.clear()
                for (ds in dataSnapshot.children) {
                    val current = ds.getValue(Question::class.java)
                    val questionC = current?.question
                    val timeC  = current?.time
                    val activeC = current?.active

                    questions.add(
                        Question(
                            questionC,
                            timeC,
                            activeC
                        )
                    )
                    mAdapter?.notifyDataSetChanged()
                }

            }
        })


        return questions
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        Log.d(TAG,"onCreateView")

        val rootView = inflater.inflate(R.layout.fragment_questions,container,false)

        //Button to take you to the Edit screen where you can add new qeustions to the database
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