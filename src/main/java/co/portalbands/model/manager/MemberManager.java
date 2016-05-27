package co.portalbands.model.manager;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import co.portalbands.model.bo.Member;
import co.portalbands.model.manager.dao.MemberDao;


@Component
public class MemberManager implements IMemberManager {

    private static final long serialVersionUID = 1L;

    @Autowired
    private MemberDao memberDao;

    public void setProductDao(MemberDao memberDao) {
        this.memberDao = memberDao;
    }
    
    public List<Member> getProducts() {
    	return memberDao.getProductList();
    }

    public void increasePrice(int percentage) {
    	List<Member> products = memberDao.getProductList();
        if (products != null) {
            for (Member member : products) {
                memberDao.saveProduct(member);
            }
        }  
    }
}