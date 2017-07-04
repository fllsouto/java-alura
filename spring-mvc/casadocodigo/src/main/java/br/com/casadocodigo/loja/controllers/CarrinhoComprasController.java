package br.com.casadocodigo.loja.controllers;

import org.omg.CORBA.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.ModelAndView;

import br.com.casadocodigo.loja.daos.ProdutoDAO;
import br.com.casadocodigo.loja.models.CarrinhoCompras;
import br.com.casadocodigo.loja.models.CarrinhoItem;
import br.com.casadocodigo.loja.models.Produto;
import br.com.casadocodigo.loja.models.TipoPreco;

@RequestMapping("/carrinho")
@Controller
@Scope(value=WebApplicationContext.SCOPE_REQUEST)
public class CarrinhoComprasController {

    @Autowired
    CarrinhoCompras carrinho;
    
    @Autowired
    ProdutoDAO produtoDAO;
    @RequestMapping("/add")
    public ModelAndView add(Integer produtoId, TipoPreco tipo) {
        ModelAndView modelAndView = new ModelAndView("redirect:/carrinho");
        Produto produto = produtoDAO.find(produtoId);
        CarrinhoItem item = new CarrinhoItem(produto, tipo);
        carrinho.add(item);
        return modelAndView;
    }
    
    @RequestMapping(method=RequestMethod.GET)
    public ModelAndView itens() {
        return new ModelAndView("/carrinho/itens");
    }
    
    @RequestMapping(value="/remover", method=RequestMethod.POST)
    public ModelAndView remover(Integer produtoId, TipoPreco tipoPreco) {
        carrinho.remove(produtoId, tipoPreco);
        return new ModelAndView("redirect:/carrinho");
    }
    
    
}
