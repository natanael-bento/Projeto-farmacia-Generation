package com.generation.games.model;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

	@Entity
	@Table(name = "tb_produtos")
	public class Produto {

		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		private Long id;
		
		@NotBlank(message = "O atributo nome é obrigatório!")
		private String nome;
		
		@NotBlank(message = "O atributo estudio é obrigatório!")
		private String estudio;
		
		private BigDecimal preco;
		
		@NotBlank(message = "O atributo console é obrigatório!")
		private String console;
		
		@ManyToOne
		@JsonIgnoreProperties("produto")
		private Categoria categoria;

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public String getNome() {
			return nome;
		}

		public void setNome(String nome) {
			this.nome = nome;
		}

		public String getEstudio() {
			return estudio;
		}

		public void setEstudio(String estudio) {
			this.estudio = estudio;
		}

		public BigDecimal getPreco() {
			return preco;
		}

		public void setPreco(BigDecimal preco) {
			this.preco = preco;
		}

		public String getConsole() {
			return console;
		}

		public void setConsole(String console) {
			this.console = console;
		}
		
		public Categoria getCategoria() {
			return categoria;
		}

		public void setCategoria(Categoria categoria) {
			this.categoria = categoria;
		}
}
