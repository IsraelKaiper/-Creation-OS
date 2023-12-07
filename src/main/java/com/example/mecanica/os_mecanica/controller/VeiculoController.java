package com.example.mecanica.os_mecanica.controller;

import com.example.mecanica.os_mecanica.model.Cliente;
import com.example.mecanica.os_mecanica.model.Veiculo;
import com.example.mecanica.os_mecanica.repository.ClienteRepository;
import com.example.mecanica.os_mecanica.repository.VeiculoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class VeiculoController {
    @Autowired
    private VeiculoRepository veiculoRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @GetMapping("/adicionar/veiculo")
    public String mostrarFormulario(Model model) {
        Veiculo veiculo = new Veiculo();
        List<Cliente> clientes = clienteRepository.findAll();
        model.addAttribute("veiculo", veiculo);
        model.addAttribute("clientes", clientes);
        return "veiculos/formularioVeiculo";
    }

    @PostMapping("/adicionar/veiculo")
    public String adicionarVeiculo(@ModelAttribute("veiculo") Veiculo veiculo, BindingResult result, Model model) {
        if (result.hasErrors()) {
            List<Cliente> clientes = clienteRepository.findAll();
            model.addAttribute("clientes", clientes);
            return "redirect:/listar-veiculos";
        }

        try {
            // Obtém o Cliente pelo ID
            Cliente cliente = clienteRepository.findById(veiculo.getCliente().getId()).orElse(null);

            if (cliente != null) {
                // Define o Cliente no Veículo
                veiculo.setCliente(cliente);

                // Salva o Veículo
                veiculoRepository.save(veiculo);

                return "redirect:/listar-veiculos";
            } else {
                model.addAttribute("error", "Cliente não encontrado. Por favor, selecione um cliente válido.");
                List<Cliente> clientes = clienteRepository.findAll();
                model.addAttribute("clientes", clientes);
                return "redirect:/listar-veiculos";
            }
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "Erro ao adicionar o veículo. Por favor, tente novamente.");
            List<Cliente> clientes = clienteRepository.findAll();
            model.addAttribute("clientes", clientes);
            return "veiculos/formularioVeiculo";
        }
    }


    @GetMapping("/listar-veiculos")
    public String listarVeiculos(Model model) {
        try {
            List<Veiculo> veiculos = veiculoRepository.findAll();
            model.addAttribute("veiculos", veiculos);
            return "veiculos/listaVeiculo";
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "Ocorreu um erro ao recuperar a lista de veículos. Por favor, tente novamente.");
            return "erro";
        }
    }

    @GetMapping("/editar-veiculo/{id}")
    public String mostrarFormularioEditar(@PathVariable Long id, Model model) {
        try {
            // Busca o veículo pelo ID
            Veiculo veiculo = veiculoRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("Veículo inválido: " + id));

            // Obtém todos os clientes para preencher o dropdown
            List<Cliente> clientes = clienteRepository.findAll();

            // Define os atributos do modelo
            model.addAttribute("veiculo", veiculo);
            model.addAttribute("clientes", clientes);

            return "veiculos/formularioEditarVeiculo";
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "Erro ao carregar veículo para edição. Por favor, tente novamente.");
            return "erro";
        }
    }

    @PostMapping("/editar-veiculo/{id}")
    public String editarVeiculo(@PathVariable Long id, @ModelAttribute("veiculo") Veiculo veiculo,
                                BindingResult result, Model model) {
        if (result.hasErrors()) {
            List<Cliente> clientes = clienteRepository.findAll();
            model.addAttribute("clientes", clientes);
            return "redirect:/listar-veiculos";
        }

        try {
            // Obtém o Cliente pelo ID
            Cliente cliente = clienteRepository.findById(veiculo.getCliente().getId())
                    .orElseThrow(() -> new IllegalArgumentException("Cliente inválido: " + veiculo.getCliente().getId()));

            // Define o Cliente no Veículo
            veiculo.setCliente(cliente);

            // Atualiza o Veículo
            veiculoRepository.save(veiculo);

            return "redirect:/listar-veiculos";
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "Erro ao editar veículo. Por favor, tente novamente.");
            return "veiculos/formularioEditarVeiculo";
        }
    }

    @PostMapping("/excluir-veiculo/{id}")
    public String excluirVeiculo(@PathVariable Long id) {
        try {
            // Exclui o veículo pelo ID
            veiculoRepository.deleteById(id);
            return "redirect:/listar-veiculos";
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
        return "formularioVeiculo";
    }
}