package co.bandsportal.model.manager;

import java.io.Serializable;

import co.bandsportal.model.bo.Member;

public interface IMemberManager extends Serializable {

    public boolean authenticate(Member member) throws Exception;
    
    public void save(Member member) throws Exception;

}