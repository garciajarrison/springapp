package co.portalbands.model.manager;

import java.io.Serializable;
import java.util.List;

import co.portalbands.model.bo.Member;

public interface IMemberManager extends Serializable {

    public void increasePrice(int percentage);
    
    public List<Member> getProducts();

}