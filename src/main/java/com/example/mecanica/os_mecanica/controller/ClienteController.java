package com.example.mecanica.os_mecanica.controller;

import com.example.mecanica.os_mecanica.model.Cliente;
import com.example.mecanica.os_mecanica.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.Optional;

@Controller
public class ClienteController {

    @Autowired
    private ClienteRepository clienteRepository;

    @GetMapping("/adiciona/cliente")
    public String mostrarFormulario(Model model) {
        model.addAttribute("cliente", new Cliente());
        return "clientes/formularioCliente";
    }

    @PostMapping("/adiciona/cliente")
    public String salvarCliente(@ModelAttribute("cliente") Cliente cliente, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "clientes/formularioCliente";
        }
        try {
            // Salva o cliente
            clienteRepository.save(cliente);

            // Redireciona para a página desejada após o salvamento bem-sucedido
            return "redirect:/listar-clientes";
        } catch (Exception e) {
            // Pode ajustar a lógica de tratamento de exceções conforme necessário
            e.printStackTrace();
            model.addAttribute("error", "Erro ao salvar o cliente. Por favor, tente novamente.");
            return "clientes/formularioCliente";
        }
    }

    @GetMapping("/listar-clientes")
    public String listarClientes(Model model) {
        try {
            // Consulta todos os clientes no repositório
            List<Cliente> clientes = clienteRepository.findAll();

            // Adiciona a lista de clientes ao modelo
            model.addAttribute("clientes", clientes);

            // Retorna a visão para exibir a lista de clientes
            return "clientes/listaCliente";
        } catch (Exception e) {
            // Trata exceções específicas para fornecer uma resposta mais amigável
            e.printStackTrace();
            model.addAttribute("error", "Ocorreu um erro ao recuperar a lista de clientes. Por favor, tente novamente.");
            return "erro";
        }
    }

    @PostMapping("/excluir-cliente/{id}")
    public String excluirCliente(@PathVariable("id") Long id, Model model) {
        try {
            // Verifica se o cliente existe antes de tentar excluí-lo
            if (clienteRepository.existsById(id)) {
                clienteRepository.deleteById(id);
                return "redirect:/listar-clientes";
            } else {
                model.addAttribute("error", "Cliente não encontrado.");
                return "erro";
            }
        } catch (Exception e) {
            // Trata exceções específicas para fornecer uma resposta mais amigável
            e.printStackTrace();
            model.addAttribute("error", "Ocorreu um erro ao excluir o cliente. Por favor, tente novamente.");
            return "erro";
        }
    }

    @GetMapping("/editar-cliente/{id}")
    public String mostrarFormularioEdicao(@PathVariable("id") Long id, Model model) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Cliente não encontrado"));

        model.addAttribute("cliente", cliente);
        return "clientes/formularioEdicaoCliente";
    }

    @PostMapping("/editar-cliente/{id}")
    public String editarCliente(@PathVariable("id") Long id,
                                @ModelAttribute("cliente") Cliente cliente,
                                Model model) {
        try {
            // Verifica se o cliente existe antes de tentar editá-lo
            Optional<Cliente> clienteExistente = clienteRepository.findById(id);
            if (clienteExistente.isEmpty()) {
                model.addAttribute("error", "Cliente não encontrado.");
                return "erro";
            }

            Cliente clienteAtual = clienteExistente.get();
            // Copia as propriedades do cliente recebido para o cliente existente
            BeanUtils.copyProperties(cliente, clienteAtual, "id");

            // Salva as alterações do cliente
            clienteRepository.save(clienteAtual);

            // Redireciona para a lista de clientes após a edição bem-sucedida
            return "redirect:/listar-clientes";
        } catch (Exception e) {
            // Trata exceções específicas para fornecer uma resposta mais amigável
            e.printStackTrace();
            model.addAttribute("error", "Ocorreu um erro ao editar o cliente. Por favor, tente novamente.");
            return "erro";
        }
    }

    @ExceptionHandler(Exception.class)
    public String handleException(Exception e, Model model) {
        e.printStackTrace();
        model.addAttribute("error", "Ocorreu um erro inesperado. Por favor, tente novamente.");
        return "formularioCliente";
    }
}