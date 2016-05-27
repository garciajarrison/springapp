package co.bandsportal.model.manager;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import co.bandsportal.model.bo.Member;
import co.bandsportal.model.manager.dao.MemberDao;


@Component
public class MemberManager implements IMemberManager {

    private static final long serialVersionUID = 1L;

    @Autowired
    private MemberDao memberDao;

    public void setProductDao(MemberDao memberDao) {
        this.memberDao = memberDao;
    }
    
    public boolean authenticate(Member member)  throws Exception{
    	return memberDao.authenticate(member);
    }

    public void save(Member member)  throws Exception{
        memberDao.save(member);

    }

}