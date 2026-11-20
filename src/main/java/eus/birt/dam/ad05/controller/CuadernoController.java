package eus.birt.dam.ad05.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import eus.birt.dam.ad05.entity.Cuaderno;
import eus.birt.dam.ad05.service.CuadernoService;

@Controller
@RequestMapping("/cuadernos")
public class CuadernoController {

	private final CuadernoService cuadernoService;

	// Inyección por constructor del servicio de cuadernos
    public CuadernoController(CuadernoService cuadernoService) {
        this.cuadernoService = cuadernoService;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("cuadernos", cuadernoService.listar());
        return "cuadernos/lista";
    }

    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        model.addAttribute("cuaderno", new Cuaderno());
        return "cuadernos/formulario";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        Cuaderno cuaderno = cuadernoService.buscarPorId(id);
        if (cuaderno == null) {
            return "redirect:/cuadernos";
        }
        model.addAttribute("cuaderno", cuaderno);
        return "cuadernos/formulario";
    }

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute Cuaderno cuaderno) {
    	cuadernoService.guardar(cuaderno);
        return "redirect:/cuadernos";
    }
    
    @GetMapping("/eliminar/{id}")
    public String confirmarEliminar(@PathVariable Long id, Model model) {
        Cuaderno cuaderno = cuadernoService.buscarPorId(id);
        if (cuaderno == null) {
            return "redirect:/cuadernos";
        }
        model.addAttribute("cuaderno", cuaderno);
        return "cuadernos/eliminar";
    }

    @PostMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id) {
        cuadernoService.eliminar(id);
        return "redirect:/cuadernos";
    }
    
}