package com.sameer.silentecho.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.sameer.silentecho.R
import com.sameer.silentecho.databinding.FragmentOnboarding1Binding

class OnboardingFragment1 : Fragment() {
    private lateinit var binding: FragmentOnboarding1Binding
    lateinit var navController: NavController
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = FragmentOnboarding1Binding.inflate(inflater,container,false)
        val view = binding.root

        binding.go.setOnClickListener {
            navController.navigate(R.id.action_onboardingFragment1_to_onboardingFragment2)
        }
        return view
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
    }
}