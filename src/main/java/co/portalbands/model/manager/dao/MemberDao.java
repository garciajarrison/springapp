package co.portalbands.model.manager.dao;

import java.util.List;

import co.portalbands.model.bo.Member;


public interface MemberDao {

    public List<Member> getProductList();

    public void saveProduct(Member prod);

}