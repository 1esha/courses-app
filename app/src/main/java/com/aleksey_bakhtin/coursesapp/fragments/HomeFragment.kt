package com.aleksey_bakhtin.coursesapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.aleksey_bakhtin.coursesapp.R
import com.aleksey_bakhtin.coursesapp.adapters.CoursesHomeAdapter
import com.aleksey_bakhtin.coursesapp.databinding.FragmentHomeBinding
import com.aleksey_bakhtin.coursesapp.fragments.viewmodels.HomeViewModel
import com.aleksey_bakhtin.coursesapp.utils.getStateScreen
import com.aleksey_bakhtin.domain.Result
import com.aleksey_bakhtin.domain.models.CourseModel
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import com.aleksey_bakhtin.coursesapp.utils.DateHelper

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModel<HomeViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.stateScreen.collect { result ->

                    getStateScreen().setStateScreen(result)

                    when(result) {
                        is Result.Success<*> -> {
                            installListCourses()
                        }
                        is Result.Error -> {}
                        is Result.Loading -> {}
                    }

                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = with(binding){

        viewModel.getCourses()

        bSort.setOnClickListener {

            val mutableList: MutableList<CourseModel> = viewModel.listCourses.toMutableList()

            DateHelper().sortByDate(
                mutableList = mutableList
            )

            viewModel.listCourses = mutableList

            installListCourses()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun installListCourses() = with(binding){

        val mutableListCourses = viewModel.listCourses.toMutableList()

        rvCoursesHome.adapter = CoursesHomeAdapter(
            mutableListCourses = mutableListCourses,
            iconEnable = ContextCompat.getDrawable(requireContext(),R.drawable.ic_bookmark_enable)!!,
            iconDisable = ContextCompat.getDrawable(requireContext(),R.drawable.ic_bookmark_disable)!!,
        ) { id ->

        }
        rvCoursesHome.layoutManager = LinearLayoutManager(requireContext())
    }
}