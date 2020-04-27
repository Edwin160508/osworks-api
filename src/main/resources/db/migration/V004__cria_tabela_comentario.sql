CREATE TABLE comentario(
	id BIGINT NOT NULL AUTO_INCREMENT,
    descricao TEXT NOT NULL,
    data_envio DATETIME NOT NULL,
    ordem_servico_id BIGINT NOT NULL,
    
    PRIMARY KEY(id)
);

ALTER TABLE comentario ADD CONSTRAINT fk_comentario_ordem_servico
FOREIGN KEY (ordem_servico_id) REFERENCES ordem_servico (id);