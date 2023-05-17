package com.example.tsdc_vinilos_equipo6.ui

import android.content.Context
import android.content.Context.MODE_PRIVATE

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.tsdc_vinilos_equipo6.R


class MainFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(
            R.layout.fragment_main,
            container, false
        )

        val sharedPref = activity?.getSharedPreferences("myPref", MODE_PRIVATE)
        val editor = sharedPref?.edit()

        val button = view.findViewById<View>(R.id.MenuUserButton) as Button
        button.setOnClickListener { v ->
            editor?.apply {
                putBoolean("isCollector",false)
                apply()
            }
            val action = MainFragmentDirections.actionMainFragmentToUserFragment()
            v?.findNavController()?.navigate(action)
        }

        val button2 = view.findViewById<View>(R.id.CollectorManuButton) as Button
        button2.setOnClickListener { v ->
            editor?.apply {
                putBoolean("isCollector",true)
                apply()
            }
            val action = MainFragmentDirections.actionMainFragmentToAlbumFragment(true)
            v?.findNavController()?.navigate(action)
        }
        return view
    }
}