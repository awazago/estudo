package com.zago.estudo.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zago.estudo.domain.Cidade;
import com.zago.estudo.domain.Cliente;
import com.zago.estudo.domain.Endereco;
import com.zago.estudo.domain.enums.TipoCliente;
import com.zago.estudo.dto.ClienteDTO;
import com.zago.estudo.dto.ClienteNewDTO;
import com.zago.estudo.repositories.ClienteRepository;
import com.zago.estudo.repositories.EnderecoRepository;
import com.zago.estudo.services.exceptions.DataIntegrityException;
import com.zago.estudo.services.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {
	
	@Autowired
	private ClienteRepository repository;
	@Autowired
	private EnderecoRepository enderecoRepository;

	public Cliente find(Integer id) {
		Optional<Cliente> cliente = repository.findById(id);
		return cliente.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id:" + id + ", Tipo: " + Cliente.class.getName()));
	}
	
	@Transactional
	public Cliente insert(Cliente obj) {
		obj.setId(null);
		obj = repository.save(obj);
		enderecoRepository.saveAll(obj.getEnderecos());
		return obj;
	}
	
	public Cliente update(Cliente obj) {
		Cliente newObj = find(obj.getId());
		updateData(newObj, obj);
		return repository.save(obj);
	}

	public void delete(Integer id) {
		find(id);
		try {
			repository.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir um cliente com relacionamentos");
		}
		
	}

	public List<Cliente> findAll() {
		return repository.findAll();
	}
	
	public Page<Cliente> findPage(Integer page, Integer linesPerPage, String orderBy, String direction){
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return repository.findAll(pageRequest);
	}
	
	public Cliente fromDTO(ClienteDTO objDto) {
		return new Cliente(objDto.getId(), objDto.getNome(), objDto.getEmail(), null, null);
	}
	
	private void updateData(Cliente newObj, Cliente obj) {
		newObj.setNome(obj.getNome());
		newObj.setEmail(obj.getEmail());
		
	}
	
	public Cliente fromDTO(ClienteNewDTO objDto) {
		Cliente cli = new Cliente(null, objDto.getNome(),  objDto.getEmail(), objDto.getCpfOuCnpj()
				, TipoCliente.toEnum(objDto.getTipoCliente()));
		Cidade cid = new Cidade(objDto.getCidadeId(), null, null);
		Endereco end = new Endereco(null, objDto.getLogradouro(), objDto.getNumero(), objDto.getComplemento()
				, objDto.getBairro(), objDto.getCep(), cid, cli);
		cli.getEnderecos().add(end);
		cli.getTelefones().add(objDto.getTelefone1());
		if(objDto.getTelefone2() != null ) {
			cli.getTelefones().add(objDto.getTelefone2());
		}
		if(objDto.getTelefone3() != null ) {
			cli.getTelefones().add(objDto.getTelefone3());
		}
		return cli;
	}
}
