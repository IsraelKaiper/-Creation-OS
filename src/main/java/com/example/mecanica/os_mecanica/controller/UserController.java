package com.example.mecanica.os_mecanica.controller;

import com.example.mecanica.os_mecanica.model.Login;
import com.example.mecanica.os_mecanica.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/")
    public String showRegister(Model model) {
        model.addAttribute("login", new Login());
        return "login/login";
    }

    @PostMapping("/dashboard")
    public String login(@ModelAttribute("login") Login login, Model model) {
        System.out.println(login);

        // Verifique se o e-mail existe
        Login u = userRepository.findByEmail(login.getEmail());

        if (u != null) {
            // Se o e-mail existe, verifique as credenciais
            if (u.getSenha().equals(login.getSenha())) {
                // Se as credenciais estiverem corretas, retorne o nome da página de dashboard
                return "login/dashboard"; // Caminho atualizado para a pasta login
            } else {
                // Se as credenciais estiverem incorretas, adicione uma mensagem de erro e retorne para a página de login
                model.addAttribute("error", "Credenciais incorretas. Por favor, tente novamente.");
                return "login/login";
            }
        } else {
            // Se o e-mail não existe, adicione uma mensagem de erro e retorne para a página de login
            model.addAttribute("error", "E-mail não encontrado. Por favor, digite novamente.");
            return "login/login";
        }
    }
}