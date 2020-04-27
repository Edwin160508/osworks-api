package com.edwin.osworks.api.controller;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.edwin.osworks.api.model.ComentarioInput;
import com.edwin.osworks.api.model.ComentarioModel;
import com.edwin.osworks.domain.exception.EntidadeNaoEncontradaException;
import com.edwin.osworks.domain.model.Comentario;
import com.edwin.osworks.domain.model.OrdemServico;
import com.edwin.osworks.domain.repository.OrdemServicoRepository;
import com.edwin.osworks.domain.service.GestaoOrdemServicoService;

@RestController
@RequestMapping("/ordens-servico/{ordemServicoId}/comentarios")
public class ComentarioController {
	
	@Autowired
	private GestaoOrdemServicoService ordemServicoService;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private OrdemServicoRepository ordemServicoRepository;
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ComentarioModel salvar(@PathVariable Long ordemServicoId,@Valid @RequestBody ComentarioInput comentarioInput) {
		var comentario = ordemServicoService.adcionaComentario(ordemServicoId, comentarioInput.getDescricao());
		return toModel(comentario);
	}
	
	@GetMapping
	public List<ComentarioModel> listar(@PathVariable Long ordemServicoId){
		OrdemServico ordemServico = ordemServicoRepository.findById(ordemServicoId)
				.orElseThrow(()->new EntidadeNaoEncontradaException("Ordem serviço não encontrada"));
		return toCollectionModel(ordemServico.getComentarios());
	}
	
	private ComentarioModel toModel(Comentario comentario) {
		return modelMapper.map(comentario, ComentarioModel.class);
	}
	
	private List<ComentarioModel> toCollectionModel(Collection<Comentario> comentarios){
		return comentarios.stream()
				.map(comentario -> toModel(comentario))
				.collect(Collectors.toList());
	}
	
}
