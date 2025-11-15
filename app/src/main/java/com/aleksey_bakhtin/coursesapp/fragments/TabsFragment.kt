package com.aleksey_bakhtin.coursesapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.aleksey_bakhtin.coursesapp.R
import com.aleksey_bakhtin.coursesapp.databinding.FragmentTabsBinding

class TabsFragment : Fragment() {

    private var _binding: FragmentTabsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTabsBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = with(binding){

        val navHostTabs =
            childFragmentManager.findFragmentById(R.id.navHostFragmentTabs) as NavHostFragment
        val navControllerTabs = navHostTabs.navController

        bnvTabs.setupWithNavController(navControllerTabs)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}