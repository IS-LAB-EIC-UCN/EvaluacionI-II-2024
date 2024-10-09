package cl.ucn.modelo;

import cl.ucn.observer.Observer;
import cl.ucn.observer.Subject;
import jakarta.persistence.*;

import java.util.List;

@Entity
public class Asistente implements Observer {

    @Id
    @Column(name = "rut")
    private Long rut;
    private String nombre;
    private String email;

    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "asistentes", cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    List<Evento> eventos;

    public void setRut(Long rut) {
        this.rut = rut;
    }

    public Long getRut() {
        return rut;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Evento> getEventos() {
        return eventos;
    }

    public void setEventos(List<Evento> eventos) {
        this.eventos = eventos;
    }

    @Override
    public void update(Subject s) {

        Evento evento = ((Evento) s);
        System.out.println("Notificación: El evento " + evento.getNombre() +
                " ha sido actualizado. Nueva fecha: " + evento.getFecha() +
                " Nuevo lugar: " + evento.getLugar() + " para el asistente " + this.nombre);
    }
}