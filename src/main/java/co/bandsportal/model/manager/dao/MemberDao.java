package co.bandsportal.model.manager.dao;

import co.bandsportal.model.bo.Member;


public interface MemberDao {

    public boolean authenticate(Member member) throws Exception;
    public void save(Member member) throws Exception;

}