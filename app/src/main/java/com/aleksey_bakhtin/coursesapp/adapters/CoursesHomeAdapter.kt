package com.aleksey_bakhtin.coursesapp.adapters

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aleksey_bakhtin.coursesapp.databinding.ItemCourseBinding
import com.aleksey_bakhtin.coursesapp.utils.DateHelper
import com.aleksey_bakhtin.domain.models.CourseModel

class CoursesHomeAdapter(
    private val mutableListCourses: MutableList<CourseModel>,
    private val iconEnable: Drawable,
    private val iconDisable: Drawable,
    private val onClickFavourite:(CourseModel) -> Unit
) : RecyclerView.Adapter<CoursesHomeAdapter.CoursesHomeHolder>() {

    class CoursesHomeHolder(val binding: ItemCourseBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CoursesHomeHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemCourseBinding.inflate(inflater, parent, false)

        return CoursesHomeHolder(binding)
    }

    override fun onBindViewHolder(
        holder: CoursesHomeHolder,
        position: Int
    ) {
        with(holder.binding) {
            val currentCourse = mutableListCourses[position]

            tvRate.text = currentCourse.rate.toString()
            tvStartDate.text = DateHelper().getFormatDate(currentCourse.startDate)
            tvTitle.text = currentCourse.title
            tvText.text = currentCourse.text
            tvPrice.text = "${currentCourse.price} ₽"

            imHasLike.setImageDrawable(
                if (currentCourse.hasLike) iconEnable else iconDisable
            )

            imHasLike.setOnClickListener {
                onClickFavourite(currentCourse)

                mutableListCourses.map {
                    if (it.id == currentCourse.id) it.hasLike = !it.hasLike
                }

                notifyItemChanged(position)
            }

        }
    }

    override fun getItemCount(): Int = mutableListCourses.size

}