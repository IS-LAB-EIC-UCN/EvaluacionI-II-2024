package cl.ucn.main;

import cl.ucn.facade.FacadeRequisito;
import cl.ucn.modelo.Asistente;
import cl.ucn.modelo.Evento;
import jakarta.persistence.*;
import org.apache.logging.log4j.LogManager;
import java.util.List;


public class Main {

    private static final EntityManagerFactory entityManagerFactory =
            Persistence.createEntityManagerFactory("ticketmaster");

    private static final org.apache.logging.log4j.Logger fileLogger =
            LogManager.getLogger(" ");

    public static void main(String[] args) {

        // ********* Eliminar un Evento para un Asistente ************
        // ***********************************************************
        FacadeRequisito fr = new FacadeRequisito();
        fr.eliminarEventoAsistente(217638392);
        // ***********************************************************

        // ***************** Ingresa un nuevo Evento *****************
        // ***********************************************************
        fr.ingresarEvento("2024-10-25", "Alice in Chains", "Parque Ohiggins");
        // ***********************************************************

        // ************* Asociar un Evento con Asistente *************
        // ***********************************************************
        fr.asociarEventoAsistente(64389216);
        // ***********************************************************

        // ******************* Actualizar un evento ******************
        // ***********************************************************
        fr.actualizarEvento(1);
        // ***********************************************************

        EntityManager em = entityManagerFactory.createEntityManager();
        fileLogger.info("******************************* RESUMEN ******************************* ");
        List<Asistente> asistentes = em.createQuery("from Asistente a", Asistente.class).getResultList();
        for (Asistente asistente2 : asistentes) {
            fileLogger.info("{} , {} , {}", asistente2.getRut(), asistente2.getNombre(), asistente2.getEmail());
            for (Evento evento1 : asistente2.getEventos()) {
                fileLogger.info("  ->  {} , {} , {} , {}", evento1.getId(), evento1.getNombre(), evento1.getFecha(), evento1.getLugar());
            }
        }
        em.close();
    }
}
