package com.aleksey_bakhtin.coursesapp.adapters

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aleksey_bakhtin.coursesapp.databinding.ItemCourseBinding
import com.aleksey_bakhtin.coursesapp.utils.DateHelper
import com.aleksey_bakhtin.domain.models.CourseModel

class FavouriteAdapter(
    private val mutableListFavourites: MutableList<CourseModel>,
    private val iconEnable: Drawable,
    private val onRemoveFavourite: (CourseModel, List<CourseModel>) -> Unit
): RecyclerView.Adapter<FavouriteAdapter.FavouriteHolder>() {

    class FavouriteHolder(val binding: ItemCourseBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FavouriteHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemCourseBinding.inflate(inflater, parent, false)

        return FavouriteHolder(binding)
    }

    override fun onBindViewHolder(
        holder: FavouriteHolder,
        position: Int
    ) {
        with(holder.binding){
            val currentCourse = mutableListFavourites[position]

            tvRate.text = currentCourse.rate.toString()
            tvStartDate.text = DateHelper().getFormatDate(currentCourse.startDate)
            tvTitle.text = currentCourse.title
            tvText.text = currentCourse.text
            tvPrice.text = "${currentCourse.price} ₽"

            imHasLike.setImageDrawable(iconEnable)

            imHasLike.setOnClickListener {
                mutableListFavourites.remove(currentCourse)

                onRemoveFavourite(currentCourse,mutableListFavourites)

                notifyItemRemoved(position)
            }
        }
    }

    override fun getItemCount(): Int = mutableListFavourites.size


}