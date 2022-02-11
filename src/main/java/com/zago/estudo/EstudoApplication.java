package com.zago.estudo;

import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.zago.estudo.domain.Categoria;
import com.zago.estudo.domain.Cidade;
import com.zago.estudo.domain.Cliente;
import com.zago.estudo.domain.Endereco;
import com.zago.estudo.domain.Estado;
import com.zago.estudo.domain.Pagamento;
import com.zago.estudo.domain.PagamentoComBoleto;
import com.zago.estudo.domain.PagamentoComCartao;
import com.zago.estudo.domain.Pedido;
import com.zago.estudo.domain.Produto;
import com.zago.estudo.domain.enums.EstadoPagamento;
import com.zago.estudo.domain.enums.TipoCliente;
import com.zago.estudo.repositories.CategoriaRepository;
import com.zago.estudo.repositories.CidadeRepository;
import com.zago.estudo.repositories.ClienteRepository;
import com.zago.estudo.repositories.EnderecoRepository;
import com.zago.estudo.repositories.EstadoRepository;
import com.zago.estudo.repositories.PagamentoRepository;
import com.zago.estudo.repositories.PedidoRepository;
import com.zago.estudo.repositories.ProdutoRepository;

@SpringBootApplication
public class EstudoApplication implements CommandLineRunner{

	@Autowired
	CategoriaRepository catRepo;
	@Autowired
	ProdutoRepository prodRepo;
	@Autowired
	EstadoRepository estRepo;
	@Autowired
	CidadeRepository cidRepo;
	@Autowired
	ClienteRepository cliRepo;
	@Autowired
	EnderecoRepository endRepo;
	@Autowired
	PedidoRepository pedRepo;
	@Autowired
	PagamentoRepository pagtoRepo;
	
	public static void main(String[] args) {
		SpringApplication.run(EstudoApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		Categoria cat1 = new Categoria(null, "Informática");
		Categoria cat2 = new Categoria(null, "Escritório");
		
		Produto p1 = new Produto(null, "Computador", 2000.00);
		Produto p2 = new Produto(null, "Impressora", 800.00);
		Produto p3 = new Produto(null, "Mouse", 80.00);
		
		cat1.getProdutos().addAll(Arrays.asList(p1, p2, p3));
		cat2.getProdutos().addAll(Arrays.asList(p2));
		
		p1.getCategorias().addAll(Arrays.asList(cat1));
		p2.getCategorias().addAll(Arrays.asList(cat1, cat2));
		p3.getCategorias().addAll(Arrays.asList(cat1));
		
		catRepo.saveAll(Arrays.asList(cat1, cat2));
		prodRepo.saveAll(Arrays.asList(p1, p2, p3));
		
		Estado est1 = new Estado(null, "Minas Gerais");
		Estado est2 = new Estado(null, "São Paulo");
		
		Cidade c1 = new Cidade(null, "Uberlândia", est1);
		Cidade c2 = new Cidade(null, "São Paulo", est2);
		Cidade c3 = new Cidade(null, "Campinas", est2);
		
		est1.getCidades().addAll(Arrays.asList(c1));
		est2.getCidades().addAll(Arrays.asList(c2, c3));
		
		estRepo.saveAll(Arrays.asList(est1, est2));
		cidRepo.saveAll(Arrays.asList(c1, c2, c3));
		
		Cliente cli1 = new Cliente(null, "Maria Silva", "maria@gmail.com", "03608918612", TipoCliente.PESSOAFISICA);
		cli1.getTelefones().addAll(Arrays.asList("34996938922", "34999646476"));
		
		Endereco e1 = new Endereco(null, "Rua Flores", "300", "Apto 202", "Centro", "38408294", c1, cli1);
		Endereco e2 = new Endereco(null, "Avenida Afonso Pena", "1478", "Sala 08", "Broklin", "11478596", c2, cli1);
		
		cli1.getEnderecos().addAll(Arrays.asList(e1, e2));
		
		cliRepo.save(cli1);
		endRepo.saveAll(Arrays.asList(e1, e2));
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyy HH:mm");
		
		Pedido ped1 = new Pedido(null, sdf.parse("30/09/2017 10:32"), e1, cli1);
		Pedido ped2 = new Pedido(null, sdf.parse("10/10/2017 19:35"), e2, cli1);
		
		Pagamento pagto1 = new PagamentoComCartao(null, EstadoPagamento.QUITADO, ped1, 6);
		ped1.setPagamento(pagto1);
		Pagamento pagto2 = new PagamentoComBoleto(null, EstadoPagamento.PENDENTE, 
				ped2, sdf.parse("20/10/2017  00:00"), null);
		ped2.setPagamento(pagto2);
		
		cli1.getPedidos().addAll(Arrays.asList(ped1, ped2));
		
		pedRepo.saveAll(Arrays.asList(ped1, ped2));
		
		pagtoRepo.saveAll(Arrays.asList(pagto1, pagto2));
		
	}

}
