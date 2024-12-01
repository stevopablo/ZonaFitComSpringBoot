package gm.zona_fit.servico;

import gm.zona_fit.modelo.Cliente;

import java.util.List;

public interface IClienteServicos {
    public List<Cliente> listarClientes();

    public Cliente buscarClientePorId(Integer idCliente);

    public void guardaCliente(Cliente cliente);

    public void eliminarCliente(Cliente cliente);



}
