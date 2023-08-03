package com.example.aisleassignment.moudles.notes.ui.adapter

import android.graphics.Bitmap
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.request.RequestOptions
import com.example.aisleassignment.databinding.ItemSuggestionDetailsBinding
import com.example.aisleassignment.moudles.notes.models.ui.LikesData
import jp.wasabeef.glide.transformations.BlurTransformation
import jp.wasabeef.glide.transformations.RoundedCornersTransformation


class SuggestionAdapter :
    ListAdapter<LikesData, RecyclerView.ViewHolder>(object : DiffUtil.ItemCallback<LikesData>() {
        override fun areItemsTheSame(oldItem: LikesData, newItem: LikesData): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: LikesData, newItem: LikesData): Boolean {
            return oldItem.imageSource == newItem.imageSource
        }

    }) {

    inner class LikeViewHolder(private val binding: ItemSuggestionDetailsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindData(item: LikesData?) {
            item?.let {
                binding.apply {

//                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
//                        ivProfile.setRenderEffect(
//                            RenderEffect.createBlurEffect(
//                                20.0f, 20.0f, Shader.TileMode.CLAMP
//                            )
//                        )
//                    }

                    this.teena.text = it.name
                    val multi = MultiTransformation<Bitmap>(
                        BlurTransformation(25, 5),
                        RoundedCornersTransformation(40, 0, RoundedCornersTransformation.CornerType.ALL)
                    )
                    Glide.with(root.context)
                        .load(it.imageSource)
                        .apply(RequestOptions.bitmapTransform(multi))
                        .into(ivProfile)
                }
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return LikeViewHolder(
            ItemSuggestionDetailsBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as LikeViewHolder).bindData(getItem(position))
    }
}