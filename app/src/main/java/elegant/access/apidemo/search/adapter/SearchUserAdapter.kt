package elegant.access.apidemo.search.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import elegant.access.apidemo.R
import elegant.access.apidemo.search.model.SearchUserAdapterData


class SearchUserAdapter : PagedListAdapter<SearchUserAdapterData, SearchUserAdapter.SearchUserViewHolder>(DiffCallback()) {

    companion object {
        const val CHILD = 0
    }

    override fun getItemViewType(position: Int): Int = CHILD


    override fun onBindViewHolder(holder: SearchUserViewHolder, position: Int) {
        holder.textUserName.text = getItem(position)?.userName
        holder.textItemPosition.text = (position + 1).toString()
        holder.textUserLink.text = getItem(position)?.userName
        holder.textUserLink.text = getItem(position)?.githubUrl

        Glide.with(holder.imageAvatarPhoto.context)
            .load(getItem(position)?.avatarUrl)
            .centerCrop()
            .fitCenter()
            .into(holder.imageAvatarPhoto)

    }


    class SearchUserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imageAvatarPhoto: ImageView = itemView.findViewById(R.id.avatarPhoto)
        var textUserName: TextView = itemView.findViewById(R.id.userName)
        var textItemPosition: TextView = itemView.findViewById(R.id.itemPosition)
        var textUserLink: TextView = itemView.findViewById(R.id.userLink)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchUserViewHolder {
        return when (viewType) {
            CHILD -> {
                SearchUserViewHolder(
                    LayoutInflater.from(parent.context).inflate(
                        R.layout.item_user, parent, false
                    )
                )
            }
            else -> {
                TODO("")
            }
        }
    }
}

class DiffCallback : DiffUtil.ItemCallback<SearchUserAdapterData>() {

    override fun areItemsTheSame(
        oldItem: SearchUserAdapterData,
        newItem: SearchUserAdapterData
    ): Boolean {
        return oldItem.id == newItem.id

    }

    override fun areContentsTheSame(
        oldItem: SearchUserAdapterData,
        newItem: SearchUserAdapterData
    ): Boolean {

        return oldItem.equals(newItem)
    }
}