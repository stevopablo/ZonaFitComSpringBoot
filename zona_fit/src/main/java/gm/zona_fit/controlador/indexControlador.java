package gm.zona_fit.controlador;

import gm.zona_fit.modelo.Cliente;
import gm.zona_fit.servico.IClienteServicos;
import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import lombok.Data;
import org.hibernate.annotations.View;
import org.primefaces.PrimeFaces;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.List;
import org.slf4j.Logger;

@Component
@Data
//@ViewScope
public class IndexControlador {

    @Autowired
    IClienteServicos clienteServicos
    private List<Cliente> clientes;
    private Cliente clienteSelecionado;
    private static final Logger logger =
            LoggerFactory.getLogger(IndexControlador.class);

    @PostConstruct
    public void init() {
        carregarDados();
    }

    public void carregarDados() {
        this.clientes = this.clienteServicos.listarClientes();
        this.clientes.forEach(cliente -> logger.info(cliente.toString()));
    }

    public void adicionarCliente() {
        this.clienteSelecionado = new Cliente();
    }

    public void salvarCliente() {
        logger.info("Cliente a ser adicionado: " + this.clienteSelecionado);
        // Adicionar (inserir)
        if (this.clienteSelecionado.getId() == null) {
            this.clienteServicos.guardaCliente(this.clienteSelecionado);
            this.clientes.add(this.clienteSelecionado);
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage("Cliente adicionado"));
        }
        // Atualizar (modificar)
        else {
            this.clienteServicos.guardaCliente(this.clienteSelecionado);
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage("Cliente atualizado"));
        }
        // Ocultar a janela modal
        PrimeFaces.current().executeScript("PF('ventanaModalCliente').hide()");
        // Atualizar a tabela usando Ajax
        PrimeFaces.current().ajax().update("forma-clientes:mensagens",
                "forma-clientes:clientes-tabela");
        // Resetar o objeto cliente selecionado
        this.clienteSelecionado = null;
    }

    public void excluirCliente() {
        logger.info("Cliente a ser excluído: " + this.clienteSelecionado);
        this.clienteServicos.eliminarCliente(this.clienteSelecionado);
        // Remover o registro da lista de clientes
        this.clientes.remove(this.clienteSelecionado);
        // Resetar o objeto cliente selecionado
        this.clienteSelecionado = null;
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage("Cliente excluído"));
        PrimeFaces.current().ajax().update("forma-clientes:mensagens",
                "forma-clientes:clientes-tabela");
    }
}
