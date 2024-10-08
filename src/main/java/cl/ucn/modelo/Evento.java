package cl.ucn.modelo;

import cl.ucn.observer.Observer;
import cl.ucn.observer.Subject;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Evento extends Subject {

    @Id
    private Long id;
    private String nombre;
    private String fecha;
    private String lugar;

    @ManyToMany
    @JoinTable(
            name = "evento_asistente",
            joinColumns = @JoinColumn(name = "id_evento"),
            inverseJoinColumns = @JoinColumn(name = "rut"))
    private List<Asistente> asistentes;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
        notifyObservers();
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
        notifyObservers();
    }

    public String getLugar() {
        return lugar;
    }

    public void setLugar(String lugar) {
        this.lugar = lugar;
        notifyObservers();
    }

    public List<Asistente> getAsistentes() {
        return asistentes;
    }

    public void setAsistentes(List<Asistente> asistentes) {
        this.asistentes = asistentes;
    }

    public void agregarAsistente(Asistente asistente) {
        asistentes.add(asistente);
    }


}
