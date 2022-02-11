package com.zago.estudo.domain;

import java.io.Serializable;
import java.util.Date;

public class Pedido implements Serializable{

	private static final long serialVersionUID = 1L;
	private Integer id;
	private Date instante;
	private Endereco enderecoEntrega;
	private Cliente cliente;
	private Pagamento pagamento;
	
	public Pedido() {
		// TODO Auto-generated constructor stub
	}

	public Pedido(Integer id, Date instante, Endereco enderecoEntrega, Cliente cliente, Pagamento pagamento) {
		super();
		this.id = id;
		this.instante = instante;
		this.enderecoEntrega = enderecoEntrega;
		this.cliente = cliente;
		this.pagamento = pagamento;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getInstante() {
		return instante;
	}

	public void setInstante(Date instante) {
		this.instante = instante;
	}

	public Endereco getEnderecoEntrega() {
		return enderecoEntrega;
	}

	public void setEnderecoEntrega(Endereco enderecoEntrega) {
		this.enderecoEntrega = enderecoEntrega;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Pagamento getPagamento() {
		return pagamento;
	}

	public void setPagamento(Pagamento pagamento) {
		this.pagamento = pagamento;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Pedido other = (Pedido) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	
}
