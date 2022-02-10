package com.blaze.demo.controller;
import com.blaze.demo.entity.DetailOrder;
import com.blaze.demo.entity.Product;
import com.blaze.demo.repository.DetailOrderRepository;
import org.springframework.ui.Model;
import com.blaze.demo.entity.Order;
import com.blaze.demo.repository.OrderRepository;
import com.blaze.demo.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/order")
public class HomeController {

    // Repositorios para listar, crear, borrar o editar
    @Autowired
    OrderRepository orderRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    DetailOrderRepository detailOrderRepository;

    //Listado de Ordenes
    @GetMapping(value= {"/list", ""})
    public String orderList(Model model, @RequestParam(name = "initiation", required = false) Integer initiation){

        if (initiation == null) {
            initiation = 0;

        } else if (initiation > 0) {
            initiation--;
        }

        List<Order> listaOrder = orderRepository.findAll();
        double cantPorPaginaDouble = Math.ceil(listaOrder.size() / 5);
        int cantDePaginas = (int) cantPorPaginaDouble;
        List<Order> list = orderRepository.pagination_Orders(initiation*5,5);

        model.addAttribute("cantDePaginas", cantDePaginas);
        model.addAttribute("paginaActual", ++initiation);
        model.addAttribute("orderList", list);
        return "listOrder";
    }

    //Creacion de una nueva orden
    @GetMapping(value="/create")
    public String createOrder(Model model){

        return "newOrder";
    }

    //Guardado del cliente con la fecha en la tabla Order
    @PostMapping(value="/save")
    public String saveOrder(Order order){
        orderRepository.save(order);
        int id = order.getOrdernumber();
        return "redirect:/order/createOrderWithProduct?id=" + id;
    }

    //Listado de los productos para guardar en una orden
    @GetMapping(value="/createOrderWithProduct")
    public String createOrderWithProduct(Model model, @RequestParam("id") int id){

        List<Product> lista = productRepository.findAll();
        model.addAttribute("productList", lista);
        model.addAttribute("idordernumber", id);
        return "addProduct";
    }

    //Guardado de productos en una orden
    @PostMapping(value="/saveProduct")
    public String saveProduct(DetailOrder detailOrder){
        double total = 0.0;

        Order order = detailOrder.getIdordernumber();
        double costoTotal = detailOrder.getUnitprice()*detailOrder.getCantidad();
        detailOrder.setCostototal(costoTotal);
        detailOrderRepository.save(detailOrder);
        int id = order.getOrdernumber();
        List<DetailOrder> listProducts = detailOrderRepository.listProductOfOrder(id);
        for(DetailOrder detail : listProducts){
            total = total + detail.getCostototal();
        }
        Optional<Order> optOrder = orderRepository.findById(id);
        if (optOrder.isPresent()) {
            Order order1 = optOrder.get();
            order1.setTotalamount(total);
            List<Double> listPrice = taxesCalculate(total);
            order1.setTaxesamounts(listPrice.get(5));
            order1.setTotaltaxes(listPrice.get(6));
            orderRepository.save(order1);
        }
        return "redirect:/order/createOrderWithProduct?id=" + id;
    }

    //Edicion de una orden
    @GetMapping(value="/edit")
    public String editOrder(Model model, @RequestParam("id") int id){

        double total = 0.0;
        Optional<Order> optOrder = orderRepository.findById(id);

        if (optOrder.isPresent()) {
            Order order = optOrder.get();
            List<DetailOrder> listProducts = detailOrderRepository.listProductOfOrder(id);
            List<Product> list = productRepository.findAll();
            for(DetailOrder detail : listProducts){
                total = total + detail.getCostototal();
            }
            List<Double> listPrice = taxesCalculate(total);
            model.addAttribute("order", order);
            model.addAttribute("listProducts", listProducts);
            model.addAttribute("listPrice", listPrice);
            model.addAttribute("list", list);
            return "editOrder";
        } else {
            return "redirect:/order/list";
        }
    }




    //Cambiando el estado a completado
    @GetMapping(value="/complete")
    public String completeOrder(Model model, @RequestParam("id") int id){
        List<Order> listorders = orderRepository.findAll();

        bucle:
        for(Order order: listorders){
            if(order.getOrdernumber() == id){
                order.setStatus("Complete");
                orderRepository.save(order);
                break bucle;
            }
        }
        return "redirect:/order/list";
    }

    //Cambiando el estado a rechazado
    @GetMapping(value="/reject")
    public String rejectOrder(Model model, @RequestParam("id") int id){

        List<Order> listorders = orderRepository.findAll();

        bucle:
        for(Order order: listorders){
            if(order.getOrdernumber() == id){
                order.setStatus("Reject");
                orderRepository.save(order);
                break bucle;
            }
        }

        return "redirect:/order/list";
    }

    //Borrado de una orden
    @GetMapping(value="/deleteProductOfOrder")
    public String deleteProductOfOrder(Model model,@RequestParam("id") int id){

        Optional<DetailOrder> optDetailOrder = detailOrderRepository.findById(id);

        if (optDetailOrder.isPresent()) {
            detailOrderRepository.deleteById(id);
        }

        return "redirect:/order/list";
    }
    

    //Función para calcular los impuestos
    public List<Double> taxesCalculate(double subtotal){

        double cityTax = (double) subtotal*0.1;
        double countryTax = (double) (cityTax + subtotal) * 0.05;
        double stateTax = (double) (cityTax + subtotal+countryTax) * 0.08;
        double federalTax = (double) (cityTax + subtotal+countryTax + stateTax) * 0.02;
        double totalTaxes = cityTax + countryTax + stateTax + federalTax;
        double total = subtotal + totalTaxes;
        List<Double> listCost = new ArrayList<>();

        listCost.add(subtotal);
        listCost.add(cityTax);
        listCost.add(countryTax);
        listCost.add(stateTax);
        listCost.add(federalTax);
        listCost.add(totalTaxes);
        listCost.add(total);
        return listCost;
    }



}
