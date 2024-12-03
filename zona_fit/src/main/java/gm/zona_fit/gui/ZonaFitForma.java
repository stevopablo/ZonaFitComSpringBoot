package gm.zona_fit.gui;

import gm.zona_fit.modelo.Cliente;
import gm.zona_fit.servico.ClienteServico;
import gm.zona_fit.servico.IClienteServicos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

@Component
public class ZonaFitForma extends JFrame{
    private JPanel panelPrincipal;
    private JTable clientesTabla;
    private JTextField nomeTexto;
    private JTextField apelidoTexto;
    private JTextField membroTexto;
    private JButton guardarButton;
    private JButton eliminarButton;
    private JButton limparButton;
    IClienteServicos clienteServicio;
    private DefaultTableModel tablaModeloClientes;
    private Integer idCliente;

    @Autowired
    public ZonaFitForma(ClienteServico clienteServicio){
        this.clienteServicio = clienteServicio;
        iniciarForma();
        guardarButton.addActionListener(e -> guardarCliente());
        clientesTabla.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                cargarClienteSeleccionado();
            }
        });
        eliminarButton.addActionListener(e -> eliminarCliente());
        limparButton.addActionListener(e -> limparFormulario());
    }

    private void iniciarForma(){
        setContentPane(panelPrincipal);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900,700);
        setLocationRelativeTo(null);
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
        this.tablaModeloClientes = new DefaultTableModel(0,4){
            @Override
            public boolean isCellEditable(int row, int column){
                return false;
            }
        };
        String[] cabecario = {"Id", "Nome", "Apelido", "Membro"};
        this.tablaModeloClientes.setColumnIdentifiers(cabecario);
        this.clientesTabla = new JTable(tablaModeloClientes);
        this.clientesTabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listarClientes();
    }

    private void listarClientes(){
        this.tablaModeloClientes.setRowCount(0);
        var clientes = this.clienteServicio.listarClientes();
        clientes.forEach(cliente -> {
            Object[] renglonCliente = {
                    cliente.getId(),
                    cliente.getNome(),
                    cliente.getApelido(),
                    cliente.getMembro()
            };
            this.tablaModeloClientes.addRow(renglonCliente);
        });
    }


//    ADICIONAR CLIENTES

    private void guardarCliente(){
        if(nomeTexto.getText().equals("")){
            mostrarMensagem("Dígite um nome");
            nomeTexto.requestFocusInWindow();
            return;
        }
        if(membroTexto.getText().equals("")){
            mostrarMensagem("Campo Membro vazio");
            membroTexto.requestFocusInWindow();
            return;
        }
        var nome = nomeTexto.getText();
        var apelido = apelidoTexto.getText();
        var membro = Integer.parseInt(membroTexto.getText());
        var cliente = new Cliente(this.idCliente, nome, apelido, membro);
        this.clienteServicio.guardaCliente(cliente);
        if(this.idCliente == null)
            mostrarMensagem("Adicionado um novo Cliente");
        else
            mostrarMensagem("Atualizado Cliente");
        limparFormulario();
        listarClientes();
    }

    private void cargarClienteSeleccionado(){
        var renglon = clientesTabla.getSelectedRow();
        if(renglon != -1){
            var id = clientesTabla.getModel().getValueAt(renglon, 0).toString();
            this.idCliente = Integer.parseInt(id);
            var nombre = clientesTabla.getModel().getValueAt(renglon, 1).toString();
            this.nomeTexto.setText(nombre);
            var apellido = clientesTabla.getModel().getValueAt(renglon, 2).toString();
            this.apelidoTexto.setText(apellido);
            var membresia = clientesTabla.getModel().getValueAt(renglon, 3).toString();
            this.membroTexto.setText(membresia);
        }
    }

    private void eliminarCliente(){
        var renglon = clientesTabla.getSelectedRow();
        if(renglon != -1){
            var idClienteStr = clientesTabla.getModel().getValueAt(renglon,0).toString();
            this.idCliente = Integer.parseInt(idClienteStr);
            var cliente = new Cliente();
            cliente.setId(this.idCliente);
            clienteServicio.eliminarCliente(cliente);
            mostrarMensagem("Cliente com id " + this.idCliente + " eliminado");
            limparFormulario();
            listarClientes();
        }
        else
            mostrarMensagem("Deve selecionar um Cliente para eliminar");

    }

    private void limparFormulario(){
        nomeTexto.setText("");
        apelidoTexto.setText("");
        membroTexto.setText("");
        this.idCliente = null;
        this.clientesTabla.getSelectionModel().clearSelection();
    }

    private void mostrarMensagem(String mensaje){
        JOptionPane.showMessageDialog(this, mensaje);
    }
}
