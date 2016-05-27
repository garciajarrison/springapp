package co.portalbands.model.manager.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import co.portalbands.model.bo.Member;


@Repository(value = "memberDao")
public class JPAMemberDao implements MemberDao {

    private EntityManager em = null;

    /*
     * Sets the entity manager.
     */
    @PersistenceContext
    public void setEntityManager(EntityManager em) {
        this.em = em;
    }

    @Transactional(readOnly = true)
    @SuppressWarnings("unchecked")
    public List<Member> getProductList() {
        return em.createQuery("select p from Member p order by p.id").getResultList();
    }

    @Transactional(readOnly = false)
    public void saveProduct(Member prod) {
        em.merge(prod);
    }

}
