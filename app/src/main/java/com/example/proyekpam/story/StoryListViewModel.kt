package com.example.proyekpam.story

import androidx.lifecycle.ViewModel
import com.example.proyekpam.Story
import com.example.proyekpam.StoryRepository

class StoryListViewModel : ViewModel(){
    private val storyRepository = StoryRepository.get()
    val storyListLiveData = storyRepository.getStories()


    fun addStory(story: Story){
        storyRepository.addStory(story)
    }
}