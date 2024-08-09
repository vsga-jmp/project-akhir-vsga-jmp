package dts.pnj.dimasfebriyanto

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import dts.pnj.dimasfebriyanto.databinding.FragmentBeritaBinding
import dts.pnj.dimasfebriyanto.placeholder.PlaceholderContent.PlaceholderItem

/**
 * [RecyclerView.Adapter] that can display a [PlaceholderItem].
 */
class MyBeritaRecyclerViewAdapter(
    private val values: List<PlaceholderItem>
) : RecyclerView.Adapter<MyBeritaRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            FragmentBeritaBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        holder.title.text = item.content
        holder.content.text = item.details
        holder.image.setImageResource(item.imageResource) // Set the image resource
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(binding: FragmentBeritaBinding) : RecyclerView.ViewHolder(binding.root) {
        val title = binding.title // Adjust to match the binding field
        val content = binding.content // Adjust to match the binding field
        val image = binding.itemImage // Adjust to match the binding field

        override fun toString(): String {
            return super.toString() + " '" + content.text + "'"
        }
    }
}
