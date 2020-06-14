package com.lab68.richtextview

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController
import com.lab68.richtextview.rich.RichVo
import kotlinx.android.synthetic.main.fragment_first.*
import kotlin.random.Random

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    companion object {
        private val TAG = FirstFragment::class.java.simpleName
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_first, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btn_insert.setOnClickListener {
            val random = Random.nextInt(1000)
            if (random % 2 == 1) {
                textview_first.setRich(RichVo("张子仪$random", "@"))
            } else {
                textview_first.setRich(RichVo("张子仪$random", "#", "#"))
            }

        }

        view.findViewById<Button>(R.id.button_first).setOnClickListener {

            val richList = textview_first.getRichList()
            Log.e(TAG, "$richList")

            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment,
                Bundle().apply {
                    putString("content", textview_first.text?.trim().toString())
                })
        }

        button_weibo.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_WeiBoFragment)
        }
    }
}