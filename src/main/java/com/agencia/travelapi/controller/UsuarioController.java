package com.agencia.travelapi.controller;
import com.agencia.travelapi.model.Usuario;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class UsuarioController {
    @GetMapping("/me")
    public Map<String,String> me(Authentication authentication){
        return Map.of("usuario", authentication.getName(),
                      "perfil", authentication.getAuthorities().stream().findFirst().map(a->a.getAuthority().replace("ROLE_","")).orElse(""));
    }
}
