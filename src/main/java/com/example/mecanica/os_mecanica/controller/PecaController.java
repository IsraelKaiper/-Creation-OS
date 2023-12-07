package com.example.mecanica.os_mecanica.controller;

import com.example.mecanica.os_mecanica.model.Peca;
import com.example.mecanica.os_mecanica.repository.PecaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
public class PecaController {

    @Autowired
    private PecaRepository pecaRepository;

    @GetMapping("/adicionar/peca")
    public String mostrarFormulario(Model model) {
        Peca peca = new Peca();
        model.addAttribute("peca", peca);
        return "pecas/formularioPeca";
    }

    @PostMapping("/adicionar/peca")
    public String adicionarPeca(@ModelAttribute("peca") Peca peca, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "redirect:/listar-pecas";
        }

        try {
            // Salva a Peca
            pecaRepository.save(peca);

            return "redirect:/listar-pecas";
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "Erro ao adicionar a peça. Por favor, tente novamente.");
            return "pecas/formularioPeca";
        }
    }

    @GetMapping("/listar-pecas")
    public String listarPecas(Model model) {
        try {
            List<Peca> pecas = pecaRepository.findAll();
            model.addAttribute("pecas", pecas);
            return "pecas/listaPeca";
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "Ocorreu um erro ao recuperar a lista de peças. Por favor, tente novamente.");
            return "erro";
        }
    }

    @GetMapping("/editar-peca/{id}")
    public String mostrarFormularioEditar(@PathVariable Long id, Model model) {
        try {
            // Busca a peça pelo ID
            Peca peca = pecaRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("Peça inválida: " + id));

            // Define os atributos do modelo
            model.addAttribute("peca", peca);

            return "pecas/formularioEditarPeca";
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "Erro ao carregar peça para edição. Por favor, tente novamente.");
            return "erro";
        }
    }

    @PostMapping("/editar-peca/{id}")
    public String editarPeca(@PathVariable Long id, @ModelAttribute("peca") Peca peca,
                             BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "redirect:/listar-pecas";
        }

        try {
            // Atualiza a Peca
            pecaRepository.save(peca);

            return "redirect:/listar-pecas";
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "Erro ao editar peça. Por favor, tente novamente.");
            return "pecas/formularioEditarPeca";
        }
    }

    @PostMapping("/excluir-peca/{id}")
    public String excluirPeca(@PathVariable Long id) {
        try {
            // Exclui a peça pelo ID
            pecaRepository.deleteById(id);
            return "redirect:/listar-pecas";
        } catch (Exception e) {
            e.printStackTrace();
            // Lide com o erro conforme necessário
            return "erro";
        }
    }

    @ExceptionHandler(Exception.class)
    public String handleException(Exception e, Model model) {
        e.printStackTrace();
        model.addAttribute("error", "Ocorreu um erro inesperado. Por favor, tente novamente.");
        return "formularioPeca";
    }
}