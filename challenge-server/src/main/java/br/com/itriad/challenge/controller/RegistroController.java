package br.com.itriad.challenge.controller;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.itriad.challenge.model.Registro;
import br.com.itriad.challenge.repositories.RegistroRepository;

@CrossOrigin
@RestController
@RequestMapping("/registros")
public class RegistroController {

	private final RegistroRepository repository;

	public RegistroController(RegistroRepository repository) {
		this.repository = repository;
	}
	
	/* Encontra todos os Veiculos sem horario de Saída
	 * para compor a Lista na tela inicial 
	 */
	@GetMapping
	public List<Registro> findAllisNull(){
		return this.repository.findBySaidaIsNull();
	}
	
	@GetMapping("findAll")
	public List<Registro> findAll(){
		return this.repository.findAll();
	}
	

	@GetMapping("find")
	public Registro findRegistro(Long id){
		
		Optional<Registro> registro = this.repository.findById(id); 
		
		if(registro.isPresent()) {
			return registro.get();
		}else {
			throw new RuntimeException("Registro "+ id +" não encontrado!");
		}
	}
	
	/* Seta a data e hora de entrada e grava
	 * no banco
	 */
	@PostMapping("in")
	public void entradaVeiculo(Registro reg){
		
		reg.setEntrada(new Date(System.currentTimeMillis()));
			
		this.repository.save(reg);
	}
	
	@PostMapping("out")
	public void saidaVeiculo(String placa) {
		
		Registro registro = new Registro();
		registro = this.repository.findByPlacaAndSaidaIsNull(placa);
		
		calculoValor(registro.getId());
		
		if(registro.getId() != null) {
			
			registro.setSaida(new Date(System.currentTimeMillis()));
			this.repository.save(registro);
		}else {
			throw new RuntimeException("Placa "+ placa +" não encontrada!");
		}
	}	
	
	
	public Double calculoValor(Long id) {
		Double valor = 0.0;
		Double qtdHrs = 0.0;
		Double qtdHrs2 = 0.0;
		
		Double meioDiaMin = 720.00; 
		Double horaMin = 60.00;
		
		Double valorHora = 2.0; 	//Seg a Sexta de 8:00 as 12:00
		Double valorHora2 = 2.5;	//Seg a Sexta de 12:01 as 18:00
		Double valorHora3 = 3.0;	//Fins de Semana
		
		Optional<Registro> registro = this.repository.findById((long) 1);
		
		SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
				
		Calendar cal = Calendar.getInstance();
	    cal.setTime(registro.get().getEntrada());
	    int diaSemana = cal.get(Calendar.DAY_OF_WEEK);
		Double entrada = getTotalMinutes(formatter.format(registro.get().getEntrada()));
		Double saida = getTotalMinutes(formatter.format(Calendar.getInstance().getTime()));

//		Double entrada = getTotalMinutes("9:30");
//		Double saida = getTotalMinutes("17:23");
		
		if(diaSemana == 1 || diaSemana == 7) {
			qtdHrs = saida-entrada;
			valor = valorHora3 * qtdHrs;
		}else {
			if(saida > meioDiaMin && entrada <= meioDiaMin) {
				qtdHrs2 = (saida - meioDiaMin) / horaMin;
				qtdHrs = (meioDiaMin - entrada) / horaMin;
			}else if(saida > meioDiaMin && entrada > meioDiaMin) {
				qtdHrs2 = (saida - entrada) / horaMin;
			}else if(saida <= meioDiaMin && entrada <= meioDiaMin) {
				qtdHrs = (saida - entrada) / horaMin;
			}
			valor = (qtdHrs2 * valorHora2) + (qtdHrs * valorHora);
		}
		return valor;
	}
	
	private static Double getTotalMinutes(String time) {
	    String[] t = time.split(":");
	    return (double) (Integer.valueOf(t[0]) * 60 + Integer.valueOf(t[1]));
	}

}
