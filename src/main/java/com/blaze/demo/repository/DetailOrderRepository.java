package com.blaze.demo.repository;


import com.blaze.demo.entity.DetailOrder;
import com.blaze.demo.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DetailOrderRepository extends JpaRepository<DetailOrder, Integer> {



    //Lista de productos que pertenecen a una orden
    @Query(value = " select d.* from detailorder as d, orders as o where d.idordernumber = o.ordernumber and d.idordernumber = ?1", nativeQuery = true)
    public List<DetailOrder>  listProductOfOrder(int id);
}
