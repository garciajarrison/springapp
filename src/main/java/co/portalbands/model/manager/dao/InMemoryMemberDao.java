package co.portalbands.model.manager.dao;

import java.util.List;

import co.portalbands.model.bo.Member;

public class InMemoryMemberDao implements MemberDao {

    private List<Member> productList;

    public InMemoryMemberDao(List<Member> productList) {
        this.productList = productList;
    }

    public List<Member> getProductList() {
        return productList;
    }

    public void saveProduct(Member prod) {
    }

}
