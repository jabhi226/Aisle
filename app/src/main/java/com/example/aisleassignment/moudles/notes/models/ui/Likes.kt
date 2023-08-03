package com.example.aisleassignment.moudles.notes.models.ui

class Likes(viewType: Int) : Notes(viewType) {
    val listOfLikes = arrayListOf<LikesData>()
}

class LikesData(
    val name: String?,
    val imageSource: String?
) {
}