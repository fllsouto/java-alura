package br.com.casadocodigo.loja.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.casadocodigo.loja.daos.ProdutoDAO;
import br.com.casadocodigo.loja.infra.FileSaver;
import br.com.casadocodigo.loja.models.Produto;
import br.com.casadocodigo.loja.models.TipoPreco;
import br.com.casadocodigo.loja.validation.ProdutoValidation;

@Controller
@RequestMapping("/produtos")
public class ProdutosController {

    @Autowired
    private ProdutoDAO produtoDAO;
    
    @Autowired
    private FileSaver fileSaver;
    
    @InitBinder
    public void InitBinder(WebDataBinder binder) {
        binder.addValidators(new ProdutoValidation());
    }

    @RequestMapping("/form")
    public ModelAndView form(Produto produto){
        ModelAndView modelAndView = new ModelAndView("produtos/form");
        modelAndView.addObject("tipos", TipoPreco.values());

        return modelAndView;
    }
    
    @RequestMapping(method=RequestMethod.POST)
    public ModelAndView gravar(MultipartFile sumario, @Valid Produto produto, BindingResult result, RedirectAttributes redirectAttributes) {
        if(result.hasErrors()) {
            System.out.println(result.getAllErrors());
            return form(produto);
        }

        String path = fileSaver.write("arquivos-sumario", sumario);
        produto.setSumarioPath(path);
        System.out.println(path);
        
        produtoDAO.gravar(produto);
        redirectAttributes.addFlashAttribute("sucesso","Produto cadastrado com sucesso!");
        return new ModelAndView("redirect:produtos");
    }

    @RequestMapping(method=RequestMethod.GET)
    public ModelAndView listar() {
        List<Produto> produtos = produtoDAO.listar();
        ModelAndView modelAndView = new ModelAndView("/produtos/lista");
        modelAndView.addObject("produtos", produtos);
        return modelAndView;
    }
    
    @RequestMapping("/detalhe_id/{id}")
    public ModelAndView detalheId(@PathVariable Integer id) {
        ModelAndView modelAndView = new ModelAndView("produtos/detalhe");
        Produto produto = produtoDAO.find(id);
        modelAndView.addObject("produto", produto);
        
        return modelAndView;
    }
    
    @RequestMapping("/detalhe_titulo/{titulo}")
    public ModelAndView detalheTitulo(@PathVariable String titulo) {
        ModelAndView modelAndView = new ModelAndView("produtos/detalhe");
        Produto produto = produtoDAO.find_by_title(titulo);
        modelAndView.addObject("produto", produto);
        
        return modelAndView;
    }
}