package eus.birt.dam.ad05.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import eus.birt.dam.ad05.entity.Cuaderno;
import eus.birt.dam.ad05.entity.Nota;
import eus.birt.dam.ad05.repository.CuadernoRepository;
import eus.birt.dam.ad05.repository.NotaRepository;

@Service
public class NotaService {

    private final NotaRepository notaRepository;
    private final CuadernoRepository cuadernoRepository;

    // Inyección por constructor de ambos repositorios necesarios para gestionar notas
    public NotaService(NotaRepository notaRepository, CuadernoRepository cuadernoRepository) {
        this.notaRepository = notaRepository;
        this.cuadernoRepository = cuadernoRepository;
    }

    public List<Nota> listar() {
        return notaRepository.findAll();
    }

    public List<Cuaderno> listarCuadernos() {
        return cuadernoRepository.findAll();
    }

    public Nota buscarPorId(Long id) {
        if (id == null) {
            return null;
        }
        Optional<Nota> nota = notaRepository.findById(id);
        if (nota.isPresent()) {
            return nota.get();
        }
        return null;
    }

    @Transactional
    public boolean guardar(Nota nota) {
        if (nota == null || nota.getCuaderno() == null || nota.getCuaderno().getId() == null) {
            return false;
        }

        // Comprobamos en base de datos que el cuaderno seleccionado existe antes de guardar
        Optional<Cuaderno> cuaderno = cuadernoRepository.findById(nota.getCuaderno().getId());
        if (cuaderno.isEmpty()) {
            return false;
        }

        nota.setCuaderno(cuaderno.get());
        notaRepository.save(nota);
        return true;
    }
    
    // Borra solo esa nota, sin tocar el cuaderno ni el resto de notas
    @Transactional
    public void eliminar(Long id) {
        Nota nota = buscarPorId(id);
        if (nota != null) {
            notaRepository.delete(nota);
        }
    }
}