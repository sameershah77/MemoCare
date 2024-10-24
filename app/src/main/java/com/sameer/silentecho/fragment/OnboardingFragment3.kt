package com.sameer.silentecho.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.sameer.silentecho.R
import com.sameer.silentecho.databinding.FragmentOnboarding3Binding
import com.sameer.silentecho.view.MainActivity

class OnboardingFragment3 : Fragment() {
    private lateinit var binding:FragmentOnboarding3Binding
    lateinit var navController: NavController
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentOnboarding3Binding.inflate(inflater,container,false)
        val view = binding.root

        binding.go.setOnClickListener {
            startActivity(Intent(requireContext(),MainActivity::class.java))
            activity?.finish()
        }

        return view
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
    }
}