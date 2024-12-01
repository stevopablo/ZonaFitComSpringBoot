package gm.zona_fit.servico;

import gm.zona_fit.modelo.Cliente;
import gm.zona_fit.repositorio.ClienteRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class ClienteServico implements IClienteServicos {
    @Autowired
    private  ClienteRepositorio clienteRepositorio;
    @Override
    public List<Cliente> listarClientes() {
        List<Cliente> clientes = clienteRepositorio.findAll();
        return clientes;
    }

    @Override
    public Cliente buscarClientePorId(Integer idCliente) {
    Cliente cliente = clienteRepositorio.findById(idCliente).orElse(null);
        return cliente;
    }

    @Override
    public void guardaCliente(Cliente cliente) {
        clienteRepositorio.save(cliente);
    }

    @Override
    public void eliminarCliente(Cliente cliente) {
        clienteRepositorio.delete(cliente);
    }
}
