import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.data.repository.PastaRepository
import com.example.myapplication.data.repository.UsuarioRepository
import com.example.myapplication.ui.perfil.VerPerfilUsuarioViewModel

class VerPerfilUsuarioViewModelFactory(
    private val usuarioRepository: UsuarioRepository,
    private val pastaRepository: PastaRepository,
    private val idUsuario: Int
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(VerPerfilUsuarioViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return VerPerfilUsuarioViewModel(usuarioRepository, pastaRepository, idUsuario) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}