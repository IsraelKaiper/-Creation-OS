package com.example.mecanica.os_mecanica.controller;

import com.example.mecanica.os_mecanica.model.Funcionario;
import com.example.mecanica.os_mecanica.model.Login;
import com.example.mecanica.os_mecanica.repository.FuncionarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import java.util.List;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.beans.BeanUtils;
import java.util.Optional;

@Controller
public class FuncionarioController {

    @Autowired
    private FuncionarioRepository funcionarioRepository;

    @GetMapping("/adiciona/mecanico")
    public String mostrarFormulario(Model model) {
        model.addAttribute("funcionario", new Funcionario());
        return "funcionarios/formularioFuncionario";
    }

    @PostMapping("/adiciona/mecanico")
    public String salvarFuncionario(@ModelAttribute("funcionario") Funcionario funcionario, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "funcionarios/formularioFuncionario";
        }

        try {
            // Verifica se o funcionário já existe no banco de dados
            if (funcionario.getId() != null && funcionarioRepository.existsById(funcionario.getId())) {
                // Obtém o funcionário existente do banco de dados
                Funcionario funcionarioExistente = funcionarioRepository.findById(funcionario.getId())
                        .orElseThrow(() -> new IllegalArgumentException("Funcionário não encontrado"));

                // Mantém o admin_id existente no funcionário atualizado
                funcionario.setAdmin(funcionarioExistente.getAdmin());
            } else {
                // Se é um novo funcionário, define o admin_id fixo
                if (funcionario.getAdmin() == null) {
                    funcionario.setAdmin(new Login());
                }
                funcionario.getAdmin().setId(2L);
            }

            // Salva o funcionário
            funcionarioRepository.save(funcionario);

            // Redireciona para a página desejada após o salvamento bem-sucedido
            return "redirect:/listar-funcionarios";
        } catch (Exception e) {
            // Pode ajustar a lógica de tratamento de exceções conforme necessário
            e.printStackTrace();
            model.addAttribute("error", "Erro ao salvar o funcionário. Por favor, tente novamente.");
            return "funcionarios/formularioFuncionario";
        }
    }

    @GetMapping("/listar-funcionarios")
    public String listarFuncionarios(Model model) {
        try {
            // Consulta todos os funcionários no repositório
            List<Funcionario> funcionarios = funcionarioRepository.findAll();

            // Adiciona a lista de funcionários ao modelo
            model.addAttribute("funcionarios", funcionarios);

            // Retorna a visão para exibir a lista de funcionários
            return "funcionarios/listaFuncionario";
        } catch (Exception e) {
            // Trata exceções específicas para fornecer uma resposta mais amigável
            e.printStackTrace();
            model.addAttribute("error", "Ocorreu um erro ao recuperar a lista de funcionários. Por favor, tente novamente.");
            return "erro";
        }
    }

    @PostMapping("/excluir-funcionario/{id}")
    public String excluirFuncionario(@PathVariable("id") Long id, Model model) {
        try {
            // Verifica se o funcionário existe antes de tentar excluí-lo
            if (funcionarioRepository.existsById(id)) {
                funcionarioRepository.deleteById(id);
                return "redirect:/listar-funcionarios";
            } else {
                model.addAttribute("error", "Funcionário não encontrado.");
                return "erro";
            }
        } catch (Exception e) {
            // Trata exceções específicas para fornecer uma resposta mais amigável
            e.printStackTrace();
            model.addAttribute("error", "Ocorreu um erro ao excluir o funcionário. Por favor, tente novamente.");
            return "erro";
        }
    }

    @GetMapping("/editar-funcionario/{id}")
    public String mostrarFormularioEdicao(@PathVariable("id") Long id, Model model) {
        Funcionario funcionario = funcionarioRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Funcionário não encontrado"));

        model.addAttribute("funcionario", funcionario);
        return "funcionarios/formularioEdicaoFuncionario";
    }

    @PostMapping("/editar-funcionario/{id}")
    public String editarFuncionario(@PathVariable("id") Long id,
                                    @ModelAttribute("funcionario") Funcionario funcionario,
                                    Model model) {
        try {
            // Verifica se o funcionário existe antes de tentar editá-lo
            Optional<Funcionario> funcionarioExistente = funcionarioRepository.findById(id);
            if (funcionarioExistente.isEmpty()) {
                model.addAttribute("error", "Funcionário não encontrado.");
                return "erro";
            }

            Funcionario funcionarioAtual = funcionarioExistente.get();
            // Copia as propriedades do funcionário recebido para o funcionário existente
            BeanUtils.copyProperties(funcionario, funcionarioAtual, "id");

            // Salva as alterações do funcionário
            funcionarioRepository.save(funcionarioAtual);

            // Redireciona para a lista de funcionários após a edição bem-sucedida
            return "redirect:/listar-funcionarios";
        } catch (Exception e) {
            // Trata exceções específicas para fornecer uma resposta mais amigável
            e.printStackTrace();
            model.addAttribute("error", "Ocorreu um erro ao editar o funcionário. Por favor, tente novamente.");
            return "erro";
        }
    }

    @ExceptionHandler(Exception.class)
    public String handleException(Exception e, Model model) {
        e.printStackTrace();
        model.addAttribute("error", "Ocorreu um erro inesperado. Por favor, tente novamente.");
        return "formularioFuncionario";
    }
}