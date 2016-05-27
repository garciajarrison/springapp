package co.bandsportal.model.manager.dao;


import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import co.bandsportal.model.bo.Member;


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
    public boolean authenticate(Member member) throws Exception {
    	boolean retorno = false;
    	
    	try{
    		
    		Query q = em.createQuery("select m from Member m "
            						+ "WHERE m.password = :password"
            						+ "AND m.username = :username");
    	
    		q.setParameter("password", member.getPassword());
    		q.setParameter("username", member.getUsername());
    		
    		Member mem = (Member)q.getSingleResult();
    		retorno = mem != null ? true: false;
        
    	}catch(Exception e){
    		throw e;
    	}
        
        return retorno;
    }

    @Transactional(readOnly = false)
    public void save(Member member) throws Exception{
        em.merge(member);
    }

}
