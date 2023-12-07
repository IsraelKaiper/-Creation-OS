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
            return "redirect:/veiculos/listarVeiculo";
        }

        try {
            veiculoRepository.save(veiculo);
            return "redirect:/veiculos/listarVeiculo";
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

    @ExceptionHandler(Exception.class)
    public String handleException(Exception e, Model model) {
        e.printStackTrace();
        model.addAttribute("error", "Ocorreu um erro inesperado. Por favor, tente novamente.");
        return "formularioVeiculo";
    }
}