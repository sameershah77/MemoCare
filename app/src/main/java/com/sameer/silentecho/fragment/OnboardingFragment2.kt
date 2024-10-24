package com.sameer.silentecho.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.sameer.silentecho.R
import com.sameer.silentecho.databinding.FragmentOnboarding2Binding

class OnboardingFragment2 : Fragment() {
    private lateinit var binding:FragmentOnboarding2Binding
    lateinit var navController: NavController
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentOnboarding2Binding.inflate(inflater,container,false)
        val view = binding.root

        binding.go.setOnClickListener {
            navController.navigate(R.id.action_onboardingFragment2_to_onboardingFragment3)
        }
        return view
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
    }
}