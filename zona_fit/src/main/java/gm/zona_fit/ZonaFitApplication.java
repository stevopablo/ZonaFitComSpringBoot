package gm.zona_fit;

import gm.zona_fit.modelo.Cliente;
import gm.zona_fit.servico.IClienteServicos;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;
import java.util.Scanner;

//@SpringBootApplication
public class ZonaFitApplication implements CommandLineRunner {
	@Autowired
	private IClienteServicos clienteServicos;
	private static final Logger logger = LoggerFactory.getLogger(ZonaFitApplication.class);

	String nl = System.lineSeparator();

	public static void main(String[] args) {
		logger.info("Iniciando aplicação");
		SpringApplication.run(ZonaFitApplication.class, args);
		logger.info("Finalizada");
	}

	@Override
	public void run(String... args) throws Exception {
		zonaFitApp();
	}

	private void zonaFitApp() {
		logger.info("===== Zona Fit =====");
		boolean sair = false;
		Scanner console = new Scanner(System.in);
		while (!sair) {
			int opcao = mostrarMenu(console);
			sair = executarOpcoes(console, opcao);
			logger.info(nl);
		}
	}

	private int mostrarMenu(Scanner console) {
		logger.info("""
                1. Listar Clientes
                2. Buscar Clientes
                3. Agregar Clientes
                4. Modificar Clientes
                5. Eliminar Clientes
                6. Sair
                Escolha uma opção: """);

		return Integer.parseInt(console.nextLine());
	}


	private boolean executarOpcoes(Scanner console, int opcao) {
		switch (opcao) {
			case 1:
				logger.info(nl + "listando clientes" + nl);
				List<Cliente> clientes = clienteServicos.listarClientes();
				clientes.forEach(cliente -> logger.info(cliente.toString()+ nl));
				break;
			case 2:
				logger.info(nl + "Buscando Cliente por id" + nl);
				var idClinte = Integer.parseInt(console.nextLine());
				Cliente cliente = clienteServicos.buscarClientePorId(idClinte);
				if(cliente.equals(null)){
					logger.info("cliente não encontrado" + cliente + nl);
				}else {
					logger.info("cliente encontrado" + cliente + nl);
				}
				break;
			case 3:
				logger.info(nl + "adicionando cliente" + nl);
				logger.info("Nome: ");
				var nome = console.nextLine();
				logger.info("Apelido: ");
				var apelido = console.nextLine();
				logger.info("Membro: ");
				var membro = Integer.parseInt(console.nextLine());
				var cliente1 = new Cliente();
				cliente1.setNome(nome);
				cliente1.setApelido(apelido);
				cliente1.setMembro(membro);
				clienteServicos.guardaCliente(cliente1);
				logger.info(nl + "cliente adicionado" + nl);
				break;
			case 4:
				logger.info(nl + "atualizando cliente" + nl);
				logger.info("id do cliente");
				var idCliente = Integer.parseInt(console.nextLine());
				Cliente cliente2 = clienteServicos.buscarClientePorId(idCliente);
				if (cliente2.equals(null)){
					logger.info("Cliente não encontrado" + cliente2 + nl);
				}else {
					logger.info("Nome: ");
					var nome1 = console.nextLine();
					logger.info("Apelido: ");
					var apelido1 = console.nextLine();
					logger.info("Membro: ");
					var membro1 = Integer.parseInt(console.nextLine());
					cliente2.setNome(nome1);
					cliente2.setApelido(apelido1);
					cliente2.setMembro(membro1);
					clienteServicos.guardaCliente(cliente2);
					logger.info("Cliente modificado com sucesso" + cliente2);
				}
				break;
			case 5:
				logger.info(nl + "Removendo Cliente" + nl);
				logger.info("ID Cliente");
				var idCliente3 = Integer.parseInt(console.nextLine());
				var cliente3 = clienteServicos.buscarClientePorId(idCliente3);
				if (cliente3 != null){
					clienteServicos.eliminarCliente(cliente3);
					logger.info("cliente elimidado" + cliente3 + nl);
				}else {
					logger.info("Cliente não encontrado "+ cliente3 + nl);
				}
				break;
			case 6:
				logger.info("Saindo...");
				return true;
			default:
				logger.error("Opção inválida, tente novamente.");
		}
		return false;
	}
}
