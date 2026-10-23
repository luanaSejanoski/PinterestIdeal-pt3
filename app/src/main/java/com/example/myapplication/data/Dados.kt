package com.example.myapplication.data

import android.net.Uri
import androidx.compose.runtime.mutableStateListOf
import com.example.myapplication.Mensagem
import com.example.myapplication.Pin
import com.example.myapplication.R
import com.example.myapplication.Usuario

object Dados {
    val mensagens = mutableStateListOf<Mensagem>()

    val usuarios = mutableStateListOf(
        Usuario(0, "Maria Silva", "maria001", "Amo fotografia 📷", "maria@email.com", null, mutableListOf(1, 5, 6, 7, 11), mutableListOf(0, 1, 2, 3)),
        Usuario(1, "João Souza", "joao002", "", "joao@email.com", null, mutableListOf(0), mutableListOf()),
        Usuario(2, "Juca", "Juquinha2", "", "juquinha@email.com", null, mutableListOf(0), mutableListOf()),
        Usuario(3, "Mariana Costa", "maricosta", "Fotógrafa amadora e entusiasta de viagens.", "mariana.costa@email.com", null, mutableListOf(1, 7, 8, 9), mutableListOf(4, 5, 6)),
        Usuario(4, "Lucas Oliveira", "lucas_oli", "Gamer, programador e fã de sci-fi.", "lucas.oliveira@email.com", null, mutableListOf(2, 5, 11), mutableListOf(7, 8, 9)),
        Usuario(5, "Beatriz Lima", "bialima", "Arquiteta e designer de interiores.", "beatriz.lima@email.com", null, mutableListOf(1, 4, 12), mutableListOf(10, 11, 12)),
        Usuario(6, "Gabriel Santos", "gabriel_s", "Estudante de Ciência da Computação.", "gabriel.santos@email.com", null, mutableListOf(2, 13, 14), mutableListOf(13, 14, 15)),
        Usuario(7, "Juliana Almeida", "ju_almeida", "Escritora de nascença e leitora voraz.", "juliana.almeida@email.com", null, mutableListOf(3, 15), mutableListOf(16, 17, 18)),
        Usuario(8, "Rafael Rocha", "rafaelrocha", "Músico e produtor de conteúdo digital.", "rafael.rocha@email.com", null, mutableListOf(3, 16, 17), mutableListOf(19, 20, 21)),
        Usuario(9, "Camila Mendes", "camilamendes", "Apaixonada por cães, gatos e desenvolvimento mobile.", "camila.mendes@email.com", null, mutableListOf(3, 18), mutableListOf(22, 23, 24)),
        Usuario(10, "Bruno Ferreira", "bruno_ferreira", "Engenheiro de software e ciclista nos fins de semana.", "bruno.ferreira@email.com", null, mutableListOf(1, 19), mutableListOf(25, 26, 27)),
        Usuario(11, "Larissa Martins", "lari_martins", "Diretora de arte e criadora de pins visuais.", "larissa.martins@email.com", null, mutableListOf(4, 20), mutableListOf(28, 29, 30)),
        Usuario(12, "Thiago Barbosa", "thibarbosa", "Especialista em segurança da informação e redes.", "thiago.barbosa@email.com", null, mutableListOf(5), mutableListOf()),
        Usuario(13, "Fernanda Ribeiro", "fe_ribeiro", "Nutricionista e entusiasta de uma vida saudável.", "fernanda.ribeiro@email.com", null, mutableListOf(6), mutableListOf()),
        Usuario(14, "Rodrigo Cardoso", "rodrigocardoso", "Editor de vídeos e motion designer.", "rodrigo.cardoso@email.com", null, mutableListOf(6), mutableListOf()),
        Usuario(15, "Patrícia Nogueira", "paty_nogs", "Psicóloga clínica e leitora de romances históricos.", "patricia.nogueira@email.com", null, mutableListOf(7), mutableListOf()),
        Usuario(16, "Diego Moreira", "diegomoreira", "Analista de dados e fã de futebol.", "diego.moreira@email.com", null, mutableListOf(8), mutableListOf()),
        Usuario(17, "Vanessa Ramos", "van_ramos", "Ilustradora digital e criadora de personagens.", "vanessa.ramos@email.com", null, mutableListOf(8), mutableListOf()),
        Usuario(18, "Eduardo Pires", "dudupires", "Empreendedor e entusiasta de startups.", "eduardo.pires@email.com", null, mutableListOf(9), mutableListOf()),
        Usuario(19, "Tatiane Duarte", "tati_duarte", "Professora de inglês e intercambista experiente.", "tatiane.duarte@email.com", null, mutableListOf(10), mutableListOf()),
        Usuario(20, "Matheus Castro", "matheuscastro", "Desenvolvedor Full Stack apaixonado por inovação.", "matheus.castro@email.com", null, mutableListOf(11), mutableListOf()),
        Usuario(21, "Beatriz Souza", "beasouza", "Estudante de medicina veterinária.", "beatriz.souza@email.com", null, mutableListOf(12), mutableListOf()),
        Usuario(22, "Lucas Mendes", "lucasmendes", "Designer gráfico e skatista nos tempos livres.", "lucas.mendes@email.com", null, mutableListOf(13), mutableListOf())
    )

    val pastas = mutableStateListOf(
        Pasta(0, true, "Paisagens",  mutableStateListOf(2, 3, 4)),
        Pasta(1, false, "Looks",  mutableStateListOf(0,1)),
        Pasta(2, false,"Desenhos", mutableStateListOf(5, 6, 7)),
        Pasta(3, false,"Looks & Outfits",  mutableStateListOf(8, 9, 10)),
        Pasta(4, false,"Viagens dos Sonhos",  mutableStateListOf(2, 4)),
        Pasta(5, false,"Fotografia Urbana",  mutableStateListOf(3, 14)),
        Pasta(6, false,"Câmeras e Lentes",  mutableStateListOf(0, 15)),
        Pasta(7, false,"Setup Gamer",  mutableStateListOf(5, 8)),
        Pasta(8, false,"Códigos e Snippets", mutableStateListOf(11, 12)),
        Pasta(9, false,"Sci-Fi Art", mutableStateListOf(6, 7)),
        Pasta(10, true,"Decoração Minimalista", mutableStateListOf(2, 11)),
        Pasta(11, false,"Salas Acolhedoras",  mutableStateListOf(3, 13)),
        Pasta(12, false,"Cozinhas Modernas", mutableStateListOf(4, 12)),
        Pasta(13, false,"Algoritmos", mutableStateListOf(5, 10)),
        Pasta(14, false,"Projetos Open Source",  mutableStateListOf(8, 11)),
        Pasta(15, false,"Café e Código",  mutableStateListOf(12, 14)),
        Pasta(16, false,"Poesia e Prosa",  mutableStateListOf(6, 9)),
        Pasta(17, false,"Biblioteca Pessoal",  mutableStateListOf(7, 10)),
        Pasta(18, false,"Escrita Criativa", mutableStateListOf(11, 15)),
        Pasta(19, false,"Beats e Instrumentos", mutableStateListOf(2, 8)),
        Pasta(20, false,"Produção Musical", mutableStateListOf(5, 12)),
        Pasta(21, false,"Shows e Festivais",  mutableStateListOf(3, 14)),
        Pasta(22, false,"Pets Fofinhos",  mutableStateListOf(4, 11)),
        Pasta(23, false,"Apps Mobile",  mutableStateListOf(9, 13)),
        Pasta(24, false,"Rotina Pet",  mutableStateListOf(7, 15)),
        Pasta(25, false,"Ciclismo Urbano",  mutableStateListOf(2, 14)),
        Pasta(26, false,"Engenharia Civil",  mutableStateListOf(3, 12)),
        Pasta(27, false,"Manutenção de Bikes",  mutableStateListOf(10, 16)),
        Pasta(28, false,"Identidade Visual", mutableStateListOf(5, 8)),
        Pasta(29, false,"Paletas de Cores",  mutableStateListOf(6, 9)),
        Pasta(30, false,"Mockups",  mutableStateListOf(7, 11))
    )

    val pins = mutableStateListOf(
        Pin(0, Uri.parse("android.resource://com.example.myapplication/${R.drawable.outfit1}"), 600, 468),
        Pin(1, Uri.parse("android.resource://com.example.myapplication/${R.drawable.outfit2}"), 700, 1080),
        Pin(2, Uri.parse("android.resource://com.example.myapplication/${R.drawable.summer1}"), 598, 280),
        Pin(3, Uri.parse("android.resource://com.example.myapplication/${R.drawable.summer2}"), 900, 560),
        Pin(4, Uri.parse("android.resource://com.example.myapplication/${R.drawable.summer3}"), 1080, 720),
        Pin(5, Uri.parse("android.resource://com.example.myapplication/drawable/desenhos1"), 800, 1000),
        Pin(6, Uri.parse("android.resource://com.example.myapplication/drawable/desenhos2"), 800, 1200),
        Pin(7, Uri.parse("android.resource://com.example.myapplication/drawable/desenhos3"), 800, 900),
        Pin(8, Uri.parse("android.resource://com.example.myapplication/drawable/outfit1"), 800, 1100),
        Pin(9, Uri.parse("android.resource://com.example.myapplication/drawable/outfit2"), 800, 1000),
        Pin(10, Uri.parse("android.resource://com.example.myapplication/drawable/outfit3"), 800, 1300),
        Pin(11, Uri.parse("android.resource://com.example.myapplication/drawable/receitas1"), 800, 800),
        Pin(12, Uri.parse("android.resource://com.example.myapplication/drawable/receitas2"), 800, 1000),
        Pin(13, Uri.parse("android.resource://com.example.myapplication/drawable/receitas3"), 800, 1100),
        Pin(14, Uri.parse("android.resource://com.example.myapplication/drawable/summer1"), 800, 1000),
        Pin(15, Uri.parse("android.resource://com.example.myapplication/drawable/summer2"), 800, 1200),
        Pin(16, Uri.parse("android.resource://com.example.myapplication/drawable/summer3"), 800, 950)
    )

    fun proximoIdPasta(): Int {
        return if (pastas.isEmpty()) 0 else pastas.maxOf { it.id } + 1
    }

    fun proximoIdPin(): Int {
        return if (pins.isEmpty()) 0 else pins.maxOf { it.id } + 1
    }
}