package ru.netology.recipebook.adapter

interface ItemTouchHelperAdapter {
    fun onItemMove(fromPosition: Int, toPosition: Int)
    fun onItemDrop(fromPosition: Int, toPosition: Int)
}