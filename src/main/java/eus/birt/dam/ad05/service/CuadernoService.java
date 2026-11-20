package eus.birt.dam.ad05.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import eus.birt.dam.ad05.entity.Cuaderno;
import eus.birt.dam.ad05.repository.CuadernoRepository;

@Service
public class CuadernoService {

    private final CuadernoRepository cuadernoRepository;

    // Inyección por constructor del repositorio de cuadernos
    public CuadernoService(CuadernoRepository cuadernoRepository) {
        this.cuadernoRepository = cuadernoRepository;
    }

    public List<Cuaderno> listar() {
        return cuadernoRepository.findAll();
    }

    public Cuaderno buscarPorId(Long id) {
        if (id == null) {
            return null;
        }
        Optional<Cuaderno> cuaderno = cuadernoRepository.findById(id);
        if (cuaderno.isPresent()) {
            return cuaderno.get();
        }
        return null;
    }

    @Transactional
    public void guardar(Cuaderno cuaderno) {
        cuadernoRepository.save(cuaderno);
    }
    
    // Al llamar a cuadernoRepository.delete(cuaderno) dentro de una transacción,
    // JPA aplica la cascada (CascadeType.ALL) y elimina también las notas asociadas
    @Transactional
    public void eliminar(Long id) {
        Cuaderno cuaderno = buscarPorId(id);
        if (cuaderno != null) {
            cuadernoRepository.delete(cuaderno);
        }
    }
}