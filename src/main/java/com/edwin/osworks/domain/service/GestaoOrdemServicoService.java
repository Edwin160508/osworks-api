package com.edwin.osworks.domain.service;

import java.time.OffsetDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.edwin.osworks.domain.exception.EntidadeNaoEncontradaException;
import com.edwin.osworks.domain.exception.NegocioException;
import com.edwin.osworks.domain.model.Cliente;
import com.edwin.osworks.domain.model.Comentario;
import com.edwin.osworks.domain.model.OrdemServico;
import com.edwin.osworks.domain.model.StatusOrdemServico;
import com.edwin.osworks.domain.repository.ClienteRepository;
import com.edwin.osworks.domain.repository.ComentarioRepository;
import com.edwin.osworks.domain.repository.OrdemServicoRepository;

@Service
public class GestaoOrdemServicoService {

	@Autowired
	private OrdemServicoRepository repository;
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private ComentarioRepository comentarioRepository;
	
	public OrdemServico salvar(OrdemServico ordemServico) {
		
		Cliente cliente = clienteRepository.findById(ordemServico.getCliente().getId())
				.orElseThrow(()-> new NegocioException("Cliente não encontrado."));
		ordemServico.setCliente(cliente);
		ordemServico.setStatus(StatusOrdemServico.ABERTA);
		ordemServico.setDataAbertura(OffsetDateTime.now());
		
		return repository.save(ordemServico);
	}
	
	public void finalizar(Long ordemServicoId) {
		var ordemServico = buscar(ordemServicoId);
		ordemServico.finalizar();
		repository.save(ordemServico);
	}

	private OrdemServico buscar(Long ordemServicoId) {
		return repository.findById(ordemServicoId).orElseThrow(()-> new EntidadeNaoEncontradaException("Ordem de serviço não encontrada"));
	}
	
	public Comentario adcionaComentario(Long ordemServicoId, String descricao) {
		OrdemServico ordemServico = buscar(ordemServicoId);
		
		var comentario = new Comentario();
		comentario.setDataEnvio(OffsetDateTime.now());
		comentario.setDescricao(descricao);
		comentario.setOrdemServico(ordemServico);
		return comentarioRepository.save(comentario);				

	}
}
