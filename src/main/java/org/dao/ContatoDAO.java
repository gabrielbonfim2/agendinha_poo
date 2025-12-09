package org.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.model.Contato;
import java.util.List;

public class ContatoDAO {

    // "agenda-pu" TEM QUE SER IGUAL AO NOME NO PERSISTENCE.XML
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("un jpa");

    public void salvar(Contato contato) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            // Se tem ID, atualiza (merge). Se não tem, salva novo (persist).
            if (contato.getId() == null) {
                em.persist(contato);
            } else {
                em.merge(contato);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    public List<Contato> listar() {
        EntityManager em = emf.createEntityManager();
        List<Contato> lista = null;
        try {
            // JPQL: "FROM Contato" refere-se à Classe, não à tabela
            lista = em.createQuery("FROM Contato", Contato.class).getResultList();
        } finally {
            em.close();
        }
        return lista;
    }

    public void excluir(Integer id) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            Contato c = em.find(Contato.class, id);
            if (c != null) {
                em.remove(c);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
    }
}