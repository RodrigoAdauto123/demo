package com.blaze.demo.repository;

import com.blaze.demo.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import com.blaze.demo.entity.Order;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer>{

    //Paginacion de Ordenes
    @Query(value = "SELECT * FROM dbblaze.orders limit ?1,?2 ", nativeQuery = true)
    public List<Order> pagination_Orders(int initiation, int size);


    //Cambio de status en el orden
    @Query(value = "UPDATE orders SET `status` = ?1 WHERE `ordernumber` = ?2", nativeQuery = true)
    public void changeStatus(String status, int id);
}
