package com.example.tsdc_vinilos_equipo6.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.tsdc_vinilos_equipo6.R


class UserFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(
            R.layout.fragment_user,
            container, false
        )

        val button = view.findViewById<View>(R.id.ColecctionUserButton) as Button
        button.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                val action = UserFragmentDirections.actionUserFragmentToCollectorFragment()
                v?.findNavController()?.navigate(action)
            }
        })

        val button2 = view.findViewById<View>(R.id.ArtistUserButton) as Button
        button2.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                //val action = MainFragmentDirections.actionMainFragmentToUserFragment()
                //v?.findNavController()?.navigate(action)
                Toast.makeText(activity, "Not Implemented Yet", Toast.LENGTH_LONG).show()
            }
        })

        val button3 = view.findViewById<View>(R.id.AlbumUserButton) as Button
        button3.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                //val action = MainFragmentDirections.actionMainFragmentToUserFragment()
                //v?.findNavController()?.navigate(action)
                Toast.makeText(activity, "Not Implemented Yet", Toast.LENGTH_LONG).show()
            }
        })
        return view
    }


}