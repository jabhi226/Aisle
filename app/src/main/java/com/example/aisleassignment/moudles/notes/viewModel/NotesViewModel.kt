package com.example.aisleassignment.moudles.notes.viewModel

import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.ViewModel
import com.example.aisleassignment.moudles.core.utils.Resource
import com.example.aisleassignment.moudles.login.repository.LoginRepository
import com.example.aisleassignment.moudles.notes.models.ui.Notes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotesViewModel @Inject constructor(
    private val repository: LoginRepository,
    private val coroutineScope: CoroutineExceptionHandler
) : ViewModel() {

    private val _notesData = MutableStateFlow<Resource<ArrayList<Notes>>?>(null)
    val notesData = _notesData.asSharedFlow()

    init {
        getUserData()
    }

    private fun getUserData() {
        CoroutineScope(Dispatchers.IO).launch(coroutineScope) {
            _notesData.emit(repository.getUserData())
        }
    }

}