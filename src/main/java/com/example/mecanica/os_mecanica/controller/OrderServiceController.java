package com.example.mecanica.os_mecanica.controller;

import com.example.mecanica.os_mecanica.model.ServiceOrder;
import com.example.mecanica.os_mecanica.repository.ServiceOrderRepository;
import com.example.mecanica.os_mecanica.repository.EquipeRepository;
import com.example.mecanica.os_mecanica.repository.ClienteRepository;
import com.example.mecanica.os_mecanica.repository.VeiculoRepository;
import com.example.mecanica.os_mecanica.repository.PecaRepository;
import com.example.mecanica.os_mecanica.model.Equipe;
import com.example.mecanica.os_mecanica.model.Veiculo;
import com.example.mecanica.os_mecanica.model.Peca;
import com.example.mecanica.os_mecanica.model.Cliente;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class OrderServiceController {

    private final ServiceOrderRepository serviceOrderRepository;
    private final EquipeRepository equipeRepository;
    private final ClienteRepository clienteRepository;
    private final VeiculoRepository veiculoRepository;
    private final PecaRepository pecaRepository;

    @Autowired
    public OrderServiceController(ServiceOrderRepository serviceOrderRepository,
                                  EquipeRepository equipeRepository,
                                  ClienteRepository clienteRepository,
                                  VeiculoRepository veiculoRepository,
                                  PecaRepository pecaRepository) {
        this.serviceOrderRepository = serviceOrderRepository;
        this.equipeRepository = equipeRepository;
        this.clienteRepository = clienteRepository;
        this.veiculoRepository = veiculoRepository;
        this.pecaRepository = pecaRepository;
    }

    @GetMapping("/adicionar/servico")
    public String mostrarFormulario(Model model) {
        ServiceOrder serviceOrder = new ServiceOrder();
        List<Equipe> equipes = equipeRepository.findAll();
        List<Cliente> clientes = clienteRepository.findAll();
        List<Veiculo> veiculos = veiculoRepository.findAll();
        List<Peca> pecas = pecaRepository.findAll();

        model.addAttribute("serviceOrder", serviceOrder);
        model.addAttribute("equipes", equipes);
        model.addAttribute("clientes", clientes);
        model.addAttribute("veiculos", veiculos);
        model.addAttribute("pecas", pecas);

        return "servicos/formularioOrdemServico";
    }

    @PostMapping("/adicionar/servico")
    public String adicionarOrdemServico(@ModelAttribute("serviceOrder") ServiceOrder serviceOrder,
                                        BindingResult result, Model model) {
        if (result.hasErrors()) {
            result.getAllErrors().forEach(error -> System.out.println(error.getDefaultMessage()));
            model.addAttribute("error", "Por favor, corrija os erros no formulário.");
            carregarModelComDados(model);
            return "servicos/formularioOrdemServico";
        }

        try {
            Equipe equipe = equipeRepository.findById(serviceOrder.getEquipe().getId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Equipe não encontrada"));

            Cliente cliente = clienteRepository.findById(serviceOrder.getCliente().getId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cliente não encontrado"));

            Veiculo veiculo = veiculoRepository.findById(serviceOrder.getVeiculo().getId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Veículo não encontrado"));

            serviceOrder.setEquipe(equipe);
            serviceOrder.setCliente(cliente);
            serviceOrder.setVeiculo(veiculo);

            serviceOrderRepository.save(serviceOrder);

            model.addAttribute("success", "Ordem de serviço adicionada com sucesso!");

            return "redirect:/listar-servico";
        } catch (ResponseStatusException e) {
            throw e;
        } catch (Exception e) {
            model.addAttribute("error", "Erro ao adicionar a ordem de serviço. Por favor, tente novamente.");
            carregarModelComDados(model);
            return "servicos/formularioOrdemServico";
        }
    }

    private void carregarModelComDados(Model model) {
        List<Equipe> equipes = equipeRepository.findAll();
        List<Cliente> clientes = clienteRepository.findAll();
        List<Veiculo> veiculos = veiculoRepository.findAll();
        List<Peca> pecas = pecaRepository.findAll();

        model.addAttribute("equipes", equipes);
        model.addAttribute("clientes", clientes);
        model.addAttribute("veiculos", veiculos);
        model.addAttribute("pecas", pecas);
    }

    @GetMapping("/listar-servico")
    public String listarOrdensServico(Model model) {
        try {
            List<ServiceOrder> serviceOrders = serviceOrderRepository.findAll();
            model.addAttribute("serviceOrders", serviceOrders);
            return "login/dashboard";
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "Ocorreu um erro ao recuperar a lista de ordens de serviço. Por favor, tente novamente.");
            return "erro";
        }
    }

    @GetMapping("/editar-servico/{id}")
    public String mostrarFormularioEditar(@PathVariable Long id, Model model) {
        try {
            ServiceOrder serviceOrder = serviceOrderRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("Ordem de Serviço inválida: " + id));

            List<Equipe> equipes = equipeRepository.findAll();
            List<Cliente> clientes = clienteRepository.findAll();
            List<Veiculo> veiculos = veiculoRepository.findAll();
            List<Peca> pecas = pecaRepository.findAll();

            model.addAttribute("serviceOrder", serviceOrder);
            model.addAttribute("equipes", equipes);
            model.addAttribute("clientes", clientes);
            model.addAttribute("veiculos", veiculos);
            model.addAttribute("pecas", pecas);

            return "servicos/formularioEdicaoService";
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "Erro ao carregar ordem de serviço para edição. Por favor, tente novamente.");
            return "erro";
        }
    }

    @PostMapping("/editar-servico/{id}")
    public String editarOrdemServico(@PathVariable Long id,
                                     @ModelAttribute("serviceOrder") ServiceOrder serviceOrder,
                                     BindingResult result, Model model) {
        if (result.hasErrors()) {
            List<Equipe> equipes = equipeRepository.findAll();
            List<Cliente> clientes = clienteRepository.findAll();
            List<Veiculo> veiculos = veiculoRepository.findAll();
            List<Peca> pecas = pecaRepository.findAll();

            model.addAttribute("equipes", equipes);
            model.addAttribute("clientes", clientes);
            model.addAttribute("veiculos", veiculos);
            model.addAttribute("pecas", pecas);

            return "servicos/formularioEdicaoService";
        }

        try {
            Equipe equipe = equipeRepository.findById(serviceOrder.getEquipe().getId()).orElse(null);
            Cliente cliente = clienteRepository.findById(serviceOrder.getCliente().getId()).orElse(null);
            Veiculo veiculo = veiculoRepository.findById(serviceOrder.getVeiculo().getId()).orElse(null);

            if (equipe != null && cliente != null && veiculo != null) {
                serviceOrder.setEquipe(equipe);
                serviceOrder.setCliente(cliente);
                serviceOrder.setVeiculo(veiculo);

                serviceOrderRepository.save(serviceOrder);

                return "redirect:/listar-servico";
            } else {
                model.addAttribute("error", "Equipe, Cliente ou Veículo não encontrado. Por favor, selecione valores válidos.");
                List<Equipe> equipes = equipeRepository.findAll();
                List<Cliente> clientes = clienteRepository.findAll();
                List<Veiculo> veiculos = veiculoRepository.findAll();
                List<Peca> pecas = pecaRepository.findAll();

                model.addAttribute("equipes", equipes);
                model.addAttribute("clientes", clientes);
                model.addAttribute("veiculos", veiculos);
                model.addAttribute("pecas", pecas);

                return "redirect:/listar-servico";
            }
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "Erro ao editar ordem de serviço. Por favor, tente novamente.");
            return "servicos/formularioEditarOrdemServico";
        }
    }

    @PostMapping("/excluir-servico/{id}")
    public String excluirOrdemServico(@PathVariable Long id) {
        try {
            serviceOrderRepository.deleteById(id);
            return "redirect:/listar-servico";
        } catch (Exception e) {
            e.printStackTrace();
            return "erro";
        }
    }

    @ExceptionHandler(Exception.class)
    public String handleException(Exception e, Model model) {
        e.printStackTrace();
        model.addAttribute("error", "Ocorreu um erro inesperado. Por favor, tente novamente.");
        return "erro";
    }
}