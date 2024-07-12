import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class ProfileViewModel : ViewModel() {
    private val _profilePictureUri = MutableStateFlow<String?>(null)
    val profilePictureUri: StateFlow<String?> = _profilePictureUri

    fun setUri(uri: String?) {
        _profilePictureUri.value = uri
    }

    fun getUri(): StateFlow<String?> {
        return profilePictureUri
    }

    private val _firstName = MutableStateFlow("Victoria")
    val firstName: StateFlow<String> = _firstName

    private val _lastName = MutableStateFlow("Yorker")
    val lastName: StateFlow<String> = _lastName

    fun updateFirstName(newFirstName: String) {
        _firstName.value = newFirstName
    }

    fun updateLastName(newLastName: String) {
        _lastName.value = newLastName
    }
}