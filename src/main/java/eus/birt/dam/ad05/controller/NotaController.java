package eus.birt.dam.ad05.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import eus.birt.dam.ad05.entity.Cuaderno;
import eus.birt.dam.ad05.entity.Nota;
import eus.birt.dam.ad05.service.NotaService;

@Controller
@RequestMapping("/notas")
public class NotaController {
	
	private final NotaService notaService;

	// Inyección por constructor del servicio de notas
    public NotaController(NotaService notaService) {
        this.notaService = notaService;
    }

    // 1. Muestra el listado de todas las notas
    @GetMapping
    public String listar(Model model) {
        model.addAttribute("notas", notaService.listar());
        return "notas/lista";
    }

    // 2. Abre el formulario para crear una nota nueva
    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        List<Cuaderno> cuadernos = notaService.listarCuadernos();

        // Si no hay ningún cuaderno creado, mandamos un aviso a la vista
        if (cuadernos.isEmpty()) {
            model.addAttribute("error", "No hay cuadernos disponibles. Debes crear primero uno.");
        }

        model.addAttribute("nota", new Nota());
        model.addAttribute("cuadernos", cuadernos);
        return "notas/formulario";
    }

    // 3. Abre el formulario con los datos de la nota que queremos editar
    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        Nota nota = notaService.buscarPorId(id);
        if (nota == null) {
            return "redirect:/notas";
        }

        model.addAttribute("nota", nota);
        model.addAttribute("cuadernos", notaService.listarCuadernos());
        return "notas/formulario";
    }

    // 4. Guarda una nota nueva o actualiza la existente
    @PostMapping("/guardar")
    public String guardar(@ModelAttribute Nota nota, Model model) {
        boolean guardada = notaService.guardar(nota);
        if (!guardada) {
            model.addAttribute("error", "El cuaderno seleccionado no existe. Debes crear primero uno.");
            model.addAttribute("cuadernos", notaService.listarCuadernos());
            return "notas/formulario";
        }

        return "redirect:/notas";
    }
    
    @GetMapping("/eliminar/{id}")
    public String confirmarEliminar(@PathVariable Long id, Model model) {
        Nota nota = notaService.buscarPorId(id);
        if (nota == null) {
            return "redirect:/notas";
        }
        model.addAttribute("nota", nota);
        return "notas/eliminar";
    }

    @PostMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id) {
        notaService.eliminar(id);
        return "redirect:/notas";
    }
    
}