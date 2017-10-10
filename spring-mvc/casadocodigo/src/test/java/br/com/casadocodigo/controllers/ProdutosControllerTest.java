package br.com.casadocodigo.controllers;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import br.com.casadocodigo.loja.conf.DataSourceConfigurationTest;
import br.com.casadocodigo.loja.conf.JPAConfiguration;

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {JPAConfiguration.class, WebAppConfiguration.class, DataSourceConfigurationTest.class})
public class ProdutosControllerTest {
    
    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;
    
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }
    
    public deveRetornarPaginaHomeComOsLivros() {
        mockMvc.perform(MockMvcRequestBuilders.get("/"));
    }
}
