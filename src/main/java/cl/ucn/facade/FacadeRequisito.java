package cl.ucn.facade;

import cl.ucn.main.Main;
import cl.ucn.modelo.Asistente;
import cl.ucn.modelo.Evento;
import com.jcabi.log.Logger;
import jakarta.persistence.*;
import org.apache.logging.log4j.LogManager;

import java.util.ArrayList;
import java.util.List;

public class FacadeRequisito {

    private static final EntityManagerFactory entityManagerFactory =
            Persistence.createEntityManagerFactory("ticketmaster");

    private static final org.apache.logging.log4j.Logger fileLogger =
            LogManager.getLogger(" ");

    public void eliminarEventoAsistente(long rut){

        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();
        Asistente asistente = em.find(Asistente.class, rut);
        Logger.info(Main.class,"El asistente " + asistente.getNombre() + " con email " + asistente.getEmail());
        Logger.info(Main.class, "Asiste a los siguientes eventos:");
        List<Evento> eventoAsiste = asistente.getEventos();
        for (Evento evento : eventoAsiste) {
            Logger.info(Main.class, evento.getNombre());
        }
        Logger.info(Main.class, "Actualiza eliminando el festival de jazz del asistente");
        Evento eventoRemueve = em.find(Evento.class, 3);
        asistente.getEventos().remove(eventoRemueve);
        eventoRemueve.getAsistentes().remove(asistente);
        em.merge(asistente);
        Logger.info(Main.class, "Ahora solo asistirá a: ");
        for (Evento evento : eventoAsiste) {
            Logger.info(Main.class, evento.getNombre());
        }
        em.getTransaction().commit();
        em.close();
    }

    public void ingresarEvento(String fecha, String nombre, String lugar){

        EntityManager em = entityManagerFactory.createEntityManager();

        int ultimoEvento = 0;
        Query query = em.createNativeQuery("select max(id) from Evento");
        try {
            ultimoEvento = (int) query.getSingleResult();
        }catch (NoResultException e){
            Logger.info(Main.class, "No existen resultados");
        }

        int id = ultimoEvento+1;
        em.getTransaction().begin();
        Evento evento = new Evento();
        evento.setId(id);
        evento.setFecha(fecha);
        evento.setNombre(nombre);
        evento.setLugar(lugar);
        em.persist(evento);
        em.getTransaction().commit();
        Logger.info(Main.class, "Se ha ingresado el evento N° " + evento.getId() + " Nombre: " + evento.getNombre());
        em.close();
    }

    public void asociarEventoAsistente(int rut){

        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();
        Asistente asistente = em.find(Asistente.class, rut);
        Evento evento2 = em.find(Evento.class, 4);
        asistente.getEventos().add(evento2);
        evento2.setAsistentes(new ArrayList<>());
        evento2.getAsistentes().add(asistente);
        em.merge(asistente);
        em.getTransaction().commit();
        Logger.info(Main.class, "Se ha asociado el evento N° " + evento2.getId() + " con " + asistente.getNombre());
        em.close();
    }

    public void actualizarEvento(int id){

        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();
        Evento evento = em.find(Evento.class, id);

        List<Asistente> asistentes = em.createQuery("select a from Asistente a where :evento MEMBER OF a.eventos", Asistente.class)
                .setParameter("evento", evento)
                .getResultList();

        for (Asistente asistente : asistentes)
            evento.addObserver(asistente);

        evento.setLugar("Estadio Santa Laura");
        em.merge(evento);
        em.getTransaction().commit();
        em.close();
    }

}
