import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.data.repository.PastaRepository
import com.example.myapplication.data.repository.UsuarioRepository
import com.example.myapplication.ui.perfil.TelaPerfilViewModel

class TelaPerfilViewModelFactory(
    private val usuarioRepository: UsuarioRepository,
    private val pastaRepository: PastaRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TelaPerfilViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return TelaPerfilViewModel(usuarioRepository, pastaRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}