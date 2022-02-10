package com.blaze.demo.repository;

import com.blaze.demo.entity.Order;
import com.blaze.demo.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {


    //Paginacion de Productos
    @Query(value = "SELECT * FROM dbblaze.product limit ?1,?2 ", nativeQuery = true)
    public List<Product> paginacion_Productos(int initiation, int size);


    //Busqueda del nombre de producto por id
    @Query(value = "select p.name from detailorder as d, product as p where d.idproduct = p.idproduct and d.idproduct = ?1 ", nativeQuery = true)
    public String searchName(int id);

}
