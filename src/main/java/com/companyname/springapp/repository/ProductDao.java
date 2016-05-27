package com.companyname.springapp.repository;

import java.util.List;

import com.companyname.springapp.domain.Product;

public interface ProductDao {

    public List<Product> getProductList();

    public void saveProduct(Product prod);

}