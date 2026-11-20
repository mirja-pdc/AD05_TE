package eus.birt.dam.ad05.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table
public class Nota {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String titulo;

    @Column
    private String contenido;

    // Cuando se pone @Column sin name=..., JPA usa automáticamente 
    // el nombre del atributo de Java como nombre de columna.
    // Solo conviene poner name = "..." si se quiere forzar un nombre 
    // distinto al del atributo, como FECHA_CREACION en vez de fechaCreacion
    @Column(name = "FECHA_CREACION")
    private LocalDateTime fechaCreacion = LocalDateTime.now();

    // Relación 1:N entre entidades Cuaderno y Nota:
    // Un cuaderno tiene muchas notas, una nota pertenece a un solo cuaderno.
    // Nota tiene como atributo un objeto Cuaderno.
    // Que @JoinColumn esté en Nota y no en Cuaderno significa que la tabla de Nota 
    // es la que tendrá la FK apuntando a la clave primaria de Cuaderno. 
    // Nota es la dueña de la relación y Cuaderno el lado inverso.
    @ManyToOne
    @JoinColumn
    private Cuaderno cuaderno;

    public Nota() {
    }

    public Nota(String titulo, String contenido, Cuaderno cuaderno) {
        this.titulo = titulo;
        this.contenido = contenido;
        this.cuaderno = cuaderno;
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

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Cuaderno getCuaderno() {
        return this.cuaderno;
    }

    public void setCuaderno(Cuaderno cuaderno) {
        this.cuaderno = cuaderno;
    }
}