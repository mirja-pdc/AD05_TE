package eus.birt.dam.ad05.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table
public class Cuaderno {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String titulo;

    @Column
    private String materia;

    // Relación 1:N entre entidades Cuaderno y Nota:
    // Un cuaderno tiene muchas notas, una nota pertenece a un solo cuaderno.
    // Cuaderno tiene como atributo un ArrayList de objetos Nota.
    // por mappedBy sabemos que la FK está en la otra entidad (Nota), que es dueña de la relación.
    @OneToMany(mappedBy = "cuaderno", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Nota> notas = new ArrayList<>();

    public Cuaderno() {
    }

    public Cuaderno(String titulo, String materia) {
        this.titulo = titulo;
        this.materia = materia;
    }

    // Métodos auxiliares para mantener sincronizados ambos extremos de la relación 1:N
    public void addNota(Nota nota) {
        if (nota == null) {
            return;
        }
        notas.add(nota);
        nota.setCuaderno(this);
    }

    public void removeNota(Nota nota) {
        if (nota == null) {
            return;
        }
        notas.remove(nota);
        nota.setCuaderno(null);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getMateria() {
        return materia;
    }

    public void setMateria(String materia) {
        this.materia = materia;
    }

    public List<Nota> getNotas() {
        return notas;
    }

    public void setNotas(List<Nota> notas) {
        this.notas.clear();
        if (notas != null) {
            for (Nota n : notas) {
                addNota(n);
            }
        }
    }
    
}