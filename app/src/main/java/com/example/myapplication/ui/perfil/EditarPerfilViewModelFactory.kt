import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.data.repository.UsuarioRepository
import com.example.myapplication.ui.perfil.EditarPerfilViewModel

class EditarPerfilViewModelFactory(
    private val usuarioRepository: UsuarioRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EditarPerfilViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return EditarPerfilViewModel(usuarioRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}