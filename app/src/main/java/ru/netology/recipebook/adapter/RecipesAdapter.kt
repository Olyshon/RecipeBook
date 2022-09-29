package ru.netology.recipebook.adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.widget.PopupMenu
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.netology.recipebook.R
import ru.netology.recipebook.databinding.RecipeCardviewBinding

import ru.netology.recipebook.dto.Recipe
import java.util.*


internal class RecipesAdapter(
    private val interactionListener: RecipeInteractionListener
) : ListAdapter<Recipe, RecipesAdapter.ViewHolder>(DiffCallback), ItemTouchHelperAdapter {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = RecipeCardviewBinding.inflate(
            inflater, parent, false
        )

        return ViewHolder(binding, interactionListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val recipe = getItem(position)
        holder.bind(recipe)
    }

    class ViewHolder(
        private val binding: RecipeCardviewBinding,
        listener: RecipeInteractionListener
    ) : RecyclerView.ViewHolder(binding.root) {

        private lateinit var recipe: Recipe

        private val popupMenu by lazy {
            PopupMenu(itemView.context, binding.menu).apply {
                inflate(R.menu.options_recipe)
                setOnMenuItemClickListener { menuItem ->
                    when (menuItem.itemId) {
                        R.id.remove -> {
                            listener.onRemoveClicked(recipe)
                            true
                        }
                        R.id.edit -> {
                            listener.onEditClicked(recipe)
                            true
                        }
                        else -> false
                    }

                }
            }
        }

        init {
            binding.like.setOnClickListener {
                listener.onLikeClicked(recipe)
            }

            binding.menu.setOnClickListener {
                popupMenu.show()
            }
            binding.root.setOnClickListener {
                listener.onRecipeClicked(recipe)
            }
        }

        fun bind(recipe: Recipe) {
            this.recipe = recipe

            with(binding) {
                title.text = recipe.title
                mainPhoto.setImageURI(Uri.parse(recipe.mainPhoto))
                description.text = recipe.description
                chipCategory.text = recipe.category
                like.isChecked = recipe.liked
                author.text = recipe.author
                ingredients.text = recipe.ingredients
                steps.text = recipe.steps
            }
        }
    }


    private object DiffCallback : DiffUtil.ItemCallback<Recipe>() {
        override fun areItemsTheSame(oldItem: Recipe, newItem: Recipe) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Recipe, newItem: Recipe) =
            oldItem == newItem
    }

    override fun onItemMove(fromPosition: Int, toPosition: Int) {
        val list = currentList.toMutableList()
        Collections.swap(list, fromPosition, toPosition)
        submitList(list)
    }

    override fun onItemDrop(fromPosition: Int, toPosition: Int) {
        interactionListener.onMove(fromPosition, toPosition, currentList)
    }

}