package com.example.mecanica.os_mecanica.controller;

import com.example.mecanica.os_mecanica.model.Equipe;
import com.example.mecanica.os_mecanica.model.Funcionario;
import com.example.mecanica.os_mecanica.repository.EquipeRepository;
import com.example.mecanica.os_mecanica.repository.FuncionarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class EquipeController {

    @Autowired
    private EquipeRepository equipeRepository;

    @Autowired
    private FuncionarioRepository funcionarioRepository;

    @GetMapping("/adicionar/equipe")
    public String mostrarFormulario(Model model) {
        Equipe equipe = new Equipe();
        List<Funcionario> funcionarios = funcionarioRepository.findAll();
        model.addAttribute("equipe", equipe);
        model.addAttribute("funcionarios", funcionarios);
        return "equipes/formularioEquipe";
    }

    @PostMapping("/adicionar/equipe")
    public String adicionarEquipe(@ModelAttribute("equipe") Equipe equipe, BindingResult result, Model model) {
        if (result.hasErrors()) {
            List<Funcionario> funcionarios = funcionarioRepository.findAll();
            model.addAttribute("funcionarios", funcionarios);
            return "redirect:/listar-equipes";
        }

        try {
            // Obtém o Funcionário pelo ID
            Funcionario funcionario = funcionarioRepository.findById(equipe.getFuncionario().getId()).orElse(null);

            if (funcionario != null) {
                // Define o Funcionário na Equipe
                equipe.setFuncionario(funcionario);

                // Salva a Equipe
                equipeRepository.save(equipe);

                return "redirect:/listar-equipes";
            } else {
                model.addAttribute("error", "Funcionário não encontrado. Por favor, selecione um funcionário válido.");
                List<Funcionario> funcionarios = funcionarioRepository.findAll();
                model.addAttribute("funcionarios", funcionarios);
                return "redirect:/listar-equipes";
            }
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "Erro ao adicionar a equipe. Por favor, tente novamente.");
            List<Funcionario> funcionarios = funcionarioRepository.findAll();
            model.addAttribute("funcionarios", funcionarios);
            return "equipes/formularioEquipe";
        }
    }

    @GetMapping("/listar-equipes")
    public String listarEquipes(Model model) {
        try {
            List<Equipe> equipes = equipeRepository.findAll();
            model.addAttribute("equipes", equipes);
            return "equipes/listaEquipe";
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "Ocorreu um erro ao recuperar a lista de equipes. Por favor, tente novamente.");
            return "erro";
        }
    }

    @GetMapping("/editar-equipe/{id}")
    public String mostrarFormularioEditar(@PathVariable Long id, Model model) {
        try {
            // Busca a equipe pelo ID
            Equipe equipe = equipeRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("Equipe inválida: " + id));

            // Obtém todos os funcionários para preencher o dropdown
            List<Funcionario> funcionarios = funcionarioRepository.findAll();

            // Define os atributos do modelo
            model.addAttribute("equipe", equipe);
            model.addAttribute("funcionarios", funcionarios);

            return "equipes/formularioEditarEquipe";
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "Erro ao carregar equipe para edição. Por favor, tente novamente.");
            return "erro";
        }
    }

    @PostMapping("/editar-equipe/{id}")
    public String editarEquipe(@PathVariable Long id, @ModelAttribute("equipe") Equipe equipe,
                               BindingResult result, Model model) {
        if (result.hasErrors()) {
            List<Funcionario> funcionarios = funcionarioRepository.findAll();
            model.addAttribute("funcionarios", funcionarios);
            return "redirect:/listar-equipes";
        }

        try {
            // Obtém o Funcionário pelo ID
            Funcionario funcionario = funcionarioRepository.findById(equipe.getFuncionario().getId())
                    .orElseThrow(() -> new IllegalArgumentException("Funcionário inválido: " + equipe.getFuncionario().getId()));

            // Define o Funcionário na Equipe
            equipe.setFuncionario(funcionario);

            // Atualiza a Equipe
            equipeRepository.save(equipe);

            return "redirect:/listar-equipes";
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "Erro ao editar equipe. Por favor, tente novamente.");
            return "equipes/formularioEditarEquipe";
        }
    }

    @PostMapping("/excluir-equipe/{id}")
    public String excluirEquipe(@PathVariable Long id) {
        try {
            // Exclui a equipe pelo ID
            equipeRepository.deleteById(id);
            return "redirect:/listar-equipes";
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
        return "equipes/formularioEquipe";
    }
}