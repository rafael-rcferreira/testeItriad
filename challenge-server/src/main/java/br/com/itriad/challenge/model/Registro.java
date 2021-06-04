package br.com.itriad.challenge.model;


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="registro", schema="estacionamento")
public class Registro {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private Long id;
	
	@Column(name="placa")
	private String placa;
	
	@Column(name="modelo")
	private String modelo;
	
	@Column(name="cor")
	private String cor;
	
	@Column(name="entrada")
	private Date entrada;
	
	@Column(name="saida")
	private Date saida;

	@Override
	public String toString() {
		return String.format(
				"Registro[id=%d, placa='%s', modelo='%s', cor='%s', entrada='%d', saida='%d']",
				id, placa, modelo, cor, entrada, saida);
	}
	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPlaca() {
		return placa;
	}

	public void setPlaca(String placa) {
		this.placa = placa;
	}

	public String getModelo() {
		return modelo;
	}

	public void setModelo(String modelo) {
		this.modelo = modelo;
	}

	public String getCor() {
		return cor;
	}

	public void setCor(String cor) {
		this.cor = cor;
	}

	public Date getEntrada() {
		return entrada;
	}

	public void setEntrada(Date entrada) {
		this.entrada = entrada;
	}

	public Date getSaida() {
		return saida;
	}

	public void setSaida(Date saida) {
		this.saida = saida;
	}
	
}