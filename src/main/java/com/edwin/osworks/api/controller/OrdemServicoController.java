package com.edwin.osworks.api.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.edwin.osworks.domain.model.OrdemServico;
import com.edwin.osworks.domain.service.GestaoOrdemServicoService;

@RestController
@RequestMapping("/ordens-servico")
public class OrdemServicoController {

	@Autowired
	private GestaoOrdemServicoService gestaoOrdemServicoService;
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public OrdemServico cadastrar(@Valid @RequestBody OrdemServico ordemServico){
		return gestaoOrdemServicoService.salvar(ordemServico);
	}
}
