package com.example.myapplication.data

import android.net.Uri
import androidx.compose.runtime.mutableStateListOf
import com.example.myapplication.Mensagem
import com.example.myapplication.PastaPin
import com.example.myapplication.Pin
import com.example.myapplication.R
import com.example.myapplication.Usuario
import com.example.myapplication.UsuarioAmigo

object Dados {
    val mensagens = mutableStateListOf<Mensagem>()

    val usuarios = listOf(
        Usuario(0, "Maria Silva", "maria001", "Amo fotografia 📷", "maria@email.com", null),
        Usuario(1, "João Souza", "joao002", "", "joao@email.com", null),
        Usuario(2, "Juca", "Juquinha2", "", "juquinha@email.com", null),
        Usuario(3, "Mariana Costa", "maricosta", "Fotógrafa amadora e entusiasta de viagens.", "mariana.costa@email.com", null),
        Usuario(4, "Lucas Oliveira", "lucas_oli", "Gamer, programador e fã de sci-fi.", "lucas.oliveira@email.com", null),
        Usuario(5, "Beatriz Lima", "bialima", "Arquiteta e designer de interiores.", "beatriz.lima@email.com", null),
        Usuario(6, "Gabriel Santos", "gabriel_s", "Estudante de Ciência da Computação.", "gabriel.santos@email.com", null),
        Usuario(7, "Juliana Almeida", "ju_almeida", "Escritora de nascença e leitora voraz.", "juliana.almeida@email.com", null),
        Usuario(8, "Rafael Rocha", "rafaelrocha", "Músico e produtor de conteúdo digital.", "rafael.rocha@email.com", null),
        Usuario(9, "Camila Mendes", "camilamendes", "Apaixonada por cães, gatos e desenvolvimento mobile.", "camila.mendes@email.com", null),
        Usuario(10, "Bruno Ferreira", "bruno_ferreira", "Engenheiro de software e ciclista nos fins de semana.", "bruno.ferreira@email.com", null),
        Usuario(11, "Larissa Martins", "lari_martins", "Diretora de arte e criadora de pins visuais.", "larissa.martins@email.com", null),
        Usuario(12, "Thiago Barbosa", "thibarbosa", "Especialista em segurança da informação e redes.", "thiago.barbosa@email.com", null),
        Usuario(13, "Fernanda Ribeiro", "fe_ribeiro", "Nutricionista e entusiasta de uma vida saudável.", "fernanda.ribeiro@email.com", null),
        Usuario(14, "Rodrigo Cardoso", "rodrigocardoso", "Editor de vídeos e motion designer.", "rodrigo.cardoso@email.com", null),
        Usuario(15, "Patrícia Nogueira", "paty_nogs", "Psicóloga clínica e leitora de romances históricos.", "patricia.nogueira@email.com", null),
        Usuario(16, "Diego Moreira", "diegomoreira", "Analista de dados e fã de futebol.", "diego.moreira@email.com", null),
        Usuario(17, "Vanessa Ramos", "van_ramos", "Ilustradora digital e criadora de personagens.", "vanessa.ramos@email.com", null),
        Usuario(18, "Eduardo Pires", "dudupires", "Empreendedor e entusiasta de startups.", "eduardo.pires@email.com", null),
        Usuario(19, "Tatiane Duarte", "tati_duarte", "Professora de inglês e intercambista experiente.", "tatiane.duarte@email.com", null),
        Usuario(20, "Matheus Castro", "matheuscastro", "Desenvolvedor Full Stack apaixonado por inovação.", "matheus.castro@email.com", null),
        Usuario(21, "Beatriz Souza", "beasouza", "Estudante de medicina veterinária.", "beatriz.souza@email.com", null),
        Usuario(22, "Lucas Mendes", "lucasmendes", "Designer gráfico e skatista nos tempos livres.", "lucas.mendes@email.com", null)
    )

    // UsuarioAmigo(usuarioId, amigoId)
    val amizades = listOf(
        UsuarioAmigo(0, 1),
        UsuarioAmigo(0, 5), UsuarioAmigo(0, 6), UsuarioAmigo(0, 7), UsuarioAmigo(0, 11),
        UsuarioAmigo(1, 0),
        UsuarioAmigo(2, 0),
        UsuarioAmigo(3, 1), UsuarioAmigo(3, 7), UsuarioAmigo(3, 8), UsuarioAmigo(3, 9),
        UsuarioAmigo(4, 2), UsuarioAmigo(4, 5), UsuarioAmigo(4, 11),
        UsuarioAmigo(5, 1), UsuarioAmigo(5, 4), UsuarioAmigo(5, 12),
        UsuarioAmigo(6, 2), UsuarioAmigo(6, 13), UsuarioAmigo(6, 14),
        UsuarioAmigo(7, 3), UsuarioAmigo(7, 15),
        UsuarioAmigo(8, 3), UsuarioAmigo(8, 16), UsuarioAmigo(8, 17),
        UsuarioAmigo(9, 3), UsuarioAmigo(9, 18),
        UsuarioAmigo(10, 1), UsuarioAmigo(10, 19),
        UsuarioAmigo(11, 4), UsuarioAmigo(11, 20),
        UsuarioAmigo(12, 5),
        UsuarioAmigo(13, 6),
        UsuarioAmigo(14, 6),
        UsuarioAmigo(15, 7),
        UsuarioAmigo(16, 8),
        UsuarioAmigo(17, 8),
        UsuarioAmigo(18, 9),
        UsuarioAmigo(19, 10),
        UsuarioAmigo(20, 11),
        UsuarioAmigo(21, 12),
        UsuarioAmigo(22, 13)
    )

    val pastas = listOf(
        Pasta(id = 1, statusPrivado = true, nome = "Paisagens", usuarioId = 0),
        Pasta(id = 2, nome = "Looks", usuarioId = 0),
        Pasta(id = 3, nome = "Desenhos", usuarioId = 0),
        Pasta(id = 4, nome = "Looks & Outfits", usuarioId = 0),
        Pasta(id = 5, nome = "Viagens dos Sonhos", usuarioId = 3),
        Pasta(id = 6, nome = "Fotografia Urbana", usuarioId = 3),
        Pasta(id = 7, nome = "Câmeras e Lentes", usuarioId = 3),
        Pasta(id = 8, nome = "Setup Gamer", usuarioId = 4),
        Pasta(id = 9, nome = "Códigos e Snippets", usuarioId = 4),
        Pasta(id = 10, nome = "Sci-Fi Art", usuarioId = 4),
        Pasta(id = 11, statusPrivado = true, nome = "Decoração Minimalista", usuarioId = 5),
        Pasta(id = 12, nome = "Salas Acolhedoras", usuarioId = 5),
        Pasta(id = 13, nome = "Cozinhas Modernas", usuarioId = 5),
        Pasta(id = 14, nome = "Algoritmos", usuarioId = 6),
        Pasta(id = 15, nome = "Projetos Open Source", usuarioId = 6),
        Pasta(id = 16, nome = "Café e Código", usuarioId = 6),
        Pasta(id = 17, nome = "Poesia e Prosa", usuarioId = 7),
        Pasta(id = 18, nome = "Biblioteca Pessoal", usuarioId = 7),
        Pasta(id = 19, nome = "Escrita Criativa", usuarioId = 7),
        Pasta(id = 20, nome = "Beats e Instrumentos", usuarioId = 8),
        Pasta(id = 21, nome = "Produção Musical", usuarioId = 8),
        Pasta(id = 22, nome = "Shows e Festivais", usuarioId = 8),
        Pasta(id = 23, nome = "Pets Fofinhos", usuarioId = 9),
        Pasta(id = 24, nome = "Apps Mobile", usuarioId = 9),
        Pasta(id = 25, nome = "Rotina Pet", usuarioId = 9),
        Pasta(id = 26, nome = "Ciclismo Urbano", usuarioId = 10),
        Pasta(id = 27, nome = "Engenharia Civil", usuarioId = 10),
        Pasta(id = 28, nome = "Manutenção de Bikes", usuarioId = 10),
        Pasta(id = 29, nome = "Identidade Visual", usuarioId = 11),
        Pasta(id = 30, nome = "Paletas de Cores", usuarioId = 11),
        Pasta(id = 31, nome = "Mockups", usuarioId = 11)
    )

    val pins = listOf(
        Pin(1, "android.resource://com.example.myapplication/${R.drawable.outfit1}", 600, 468),
        Pin(2, "android.resource://com.example.myapplication/${R.drawable.outfit2}", 700, 1080),
        Pin(3, "android.resource://com.example.myapplication/${R.drawable.summer1}", 598, 280),
        Pin(4, "android.resource://com.example.myapplication/${R.drawable.summer2}", 900, 560),
        Pin(5, "android.resource://com.example.myapplication/${R.drawable.summer3}", 1080, 720),
        Pin(6, "android.resource://com.example.myapplication/drawable/desenhos1", 800, 1000),
        Pin(7, "android.resource://com.example.myapplication/drawable/desenhos2", 800, 1200),
        Pin(8, "android.resource://com.example.myapplication/drawable/desenhos3", 800, 900),
        Pin(9, "android.resource://com.example.myapplication/drawable/outfit1", 800, 1100),
        Pin(10, "android.resource://com.example.myapplication/drawable/outfit2", 800, 1000),
        Pin(11, "android.resource://com.example.myapplication/drawable/outfit3", 800, 1300),
        Pin(12, "android.resource://com.example.myapplication/drawable/receitas1", 800, 800),
        Pin(13, "android.resource://com.example.myapplication/drawable/receitas2", 800, 1000),
        Pin(14, "android.resource://com.example.myapplication/drawable/receitas3", 800, 1100),
        Pin(15, "android.resource://com.example.myapplication/drawable/summer1", 800, 1000),
        Pin(16, "android.resource://com.example.myapplication/drawable/summer2", 800, 1200),
        Pin(17, "android.resource://com.example.myapplication/drawable/summer3", 800, 950)
    )


    val pastaPins = listOf(
        PastaPin(1, 3), PastaPin(1, 4), PastaPin(1, 5),
        PastaPin(2, 1), PastaPin(2, 2),
        PastaPin(3, 6), PastaPin(3, 7), PastaPin(3, 8),
        PastaPin(4, 9), PastaPin(4, 10), PastaPin(4, 11),
        PastaPin(5, 3), PastaPin(5, 5),
        PastaPin(6, 4), PastaPin(6, 15),
        PastaPin(7, 1), PastaPin(7, 16),
        PastaPin(8, 6), PastaPin(8, 9),
        PastaPin(9, 12), PastaPin(9, 13),
        PastaPin(10, 7), PastaPin(10, 8),
        PastaPin(11, 3), PastaPin(11, 12),
        PastaPin(12, 4), PastaPin(12, 14),
        PastaPin(13, 5), PastaPin(13, 13),
        PastaPin(14, 6), PastaPin(14, 11),
        PastaPin(15, 9), PastaPin(15, 12),
        PastaPin(16, 13), PastaPin(16, 15),
        PastaPin(17, 7), PastaPin(17, 10),
        PastaPin(18, 8), PastaPin(18, 11),
        PastaPin(19, 12), PastaPin(19, 16),
        PastaPin(20, 3), PastaPin(20, 9),
        PastaPin(21, 6), PastaPin(21, 13),
        PastaPin(22, 4), PastaPin(22, 15),
        PastaPin(23, 5), PastaPin(23, 12),
        PastaPin(24, 10), PastaPin(24, 14),
        PastaPin(25, 8), PastaPin(25, 16),
        PastaPin(26, 3), PastaPin(26, 15),
        PastaPin(27, 4), PastaPin(27, 13),
        PastaPin(28, 11), PastaPin(28, 17),
        PastaPin(29, 6), PastaPin(29, 9),
        PastaPin(30, 7), PastaPin(30, 10),
        PastaPin(31, 8), PastaPin(31, 12)
    )
}