package com.blaze.demo.controller;
import org.springframework.ui.Model;
import com.blaze.demo.entity.Product;
import com.blaze.demo.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/product")
public class ProductController {


    //Repositorios
    @Autowired
    ProductRepository productRepository;

    //Listado de todos los productos
    @GetMapping(value= {"/list", ""})
    public String ProductList(Model model, @RequestParam(name = "inicio", required = false) Integer inicio){

        if (inicio == null) {
            inicio = 0;

        } else if (inicio > 0) {
            inicio--;
        }
        List<Product> listaTotal = productRepository.findAll();
        double cantPorPaginaDouble = Math.ceil(listaTotal.size() / 5);
        int cantDePaginas = (int) cantPorPaginaDouble;
        List<Product> lista = productRepository.paginacion_Productos(inicio*5,5);
        model.addAttribute("productList", lista);
        model.addAttribute("cantDePaginas", cantDePaginas);
        model.addAttribute("paginaActual", ++inicio);
        return "listProduct";
    }

    //Creacion de un nuevo producto
    @GetMapping(value="/create")
    public String createProduct(){
        return "newProduct";
    }

    //Guardado del producto
    @PostMapping(value="/save")
    public String saveProduct(Product product){
        productRepository.save(product);
        return "redirect:/product/list + id";
    }

    //Editar y guardar un producto existente
    @GetMapping(value="/edit")
    public String editProduct(Model model, @RequestParam("id") int id){
        Optional<Product> optProduct = productRepository.findById(id);

        if (optProduct.isPresent()) {
            Product product = optProduct.get();
            model.addAttribute("product", product);
            return "editProduct";
        } else {
            return "redirect:/product/list";
        }
    }

    //Borrado de un producto existente
    @GetMapping(value="/delete")
    public String deleteProduct(Model model, @RequestParam("id") int id){
        Optional<Product> optProduct = productRepository.findById(id);

        if (optProduct.isPresent()) {
            productRepository.deleteById(id);
        }
        return "redirect:/product/list";
    }

    //Listado de todos los productos con orden
    @GetMapping(value= {"/listSort"})
    public String ProductListSort(Model model, @RequestParam(name = "initiation", required = false) Integer initiation,
                                  @RequestParam(name = "iterator", required = false) String iterator){

        if (initiation == null) {
            initiation = 0;

        } else if (initiation > 0) {
            initiation--;
        }
        List<Product> listaTotal = productRepository.findAll();
        double cantPorPaginaDouble = Math.ceil(listaTotal.size() / 5);
        int cantDePaginas = (int) cantPorPaginaDouble;
        List<Product> lista = productRepository.sortProduct(iterator, initiation*5,5);
        model.addAttribute("productList", lista);
        model.addAttribute("cantDePaginas", cantDePaginas);
        model.addAttribute("paginaActual", ++initiation);
        return "listProduct";
    }


}
