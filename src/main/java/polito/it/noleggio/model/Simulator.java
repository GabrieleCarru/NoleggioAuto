package polito.it.noleggio.model;

import java.time.Duration;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.PriorityQueue;

import polito.it.noleggio.model.Event.EventType;

// In MAIUSC gli step fissi da usare nel simulatore, in minusc le annotazioni.

public class Simulator {

	// CODA DEGLI EVENTI
	private PriorityQueue<Event> queue = new PriorityQueue<>();
	
	// PARAMETRI DI SIMULAZIONE (inizializzati a valore di Default)
	private int NC = 10; // Number of Cars
	private Duration T_IN = Duration.of(10, ChronoUnit.MINUTES); // Intervallo tra i clienti
	private final LocalTime oraApertura = LocalTime.of(8, 00);
	private final LocalTime oraChiusura = LocalTime.of(17, 00);
	
	
	// MODELLO DEL MONDO
	private int nAuto;	// Auto disponibili (0 < nAuto < NC)
	private int clienti; 
	private int insoddisfatti;
	
	// VALORI DA CALCOLARE
	
	
	// METODI PER IMPOSTARE I PARAMETRI
	public void setNumCars(int N) {
		this.NC = N;
	}
	
	public void setClientFrequency(Duration d) {
		this.T_IN = d;
	}

	// METODI PER RESTITUIRE I RISULTATI
	public int getClienti() {
		return clienti;
	}

	public int getInsoddisfatti() {
		return insoddisfatti;
	}
	
	// SIMULAZIONE VERA E PROPRIA
	public void run() {
		//  PREPARAZIONE INIZIALE (mondo + coda eventi)
		this.nAuto = this.NC;
		this.clienti = 0;
		this.insoddisfatti = 0;
		
		this.queue.clear();
		LocalTime oraArrivoCliente = this.oraApertura;
		
		// finché l'autonoleggio é aperto 
		do {
			Event e = new Event(oraArrivoCliente, EventType.NEW_CLIENT);	// Nuovo evento
			this.queue.add(e);
			oraArrivoCliente = oraArrivoCliente.plus(this.T_IN);	// incremento ora di arrivo per successivo
		} while (oraArrivoCliente.isBefore(this.oraChiusura));
		
		// Alla fine del ciclo while avró la coda piena degli eventi
		
		// ESECUZIONE DEL CICLO DI SIMULAZIONE
		while(!this.queue.isEmpty()) {
			Event e = this.queue.poll();
			System.out.println(e);
			processEvent(e);
		}
	}
	
	public void processEvent(Event e) {
		switch(e.getType()) {
		
		case NEW_CLIENT:
			
			if(this.nAuto > 0) {
				// cliente viene servito, auto viene noleggata
				// 1. aggiorno modello del mondo
				this.nAuto--;
				// 2. Aggiorno risultati
				this.clienti++;
				// 3. Genero nuovi eventi
				double number = Math.random();	// [0,1)
				Duration travel;
				if(number<1.0/3.0) {
					travel = Duration.of(1, ChronoUnit.HOURS);
				} else if(number <2.0/3.0) {
					travel = Duration.of(2, ChronoUnit.HOURS);
				} else {
					travel = Duration.of(3, ChronoUnit.HOURS);
				}
				
				Event nuovo = new Event(e.getTime().plus(travel), EventType.CAR_RETURN);
				
				this.queue.add(nuovo);
				
			} else {
				// cliente indosddisfatto
				this.clienti++;
				this.insoddisfatti++;
			}
			break;
			
		case CAR_RETURN:
			
			nAuto++;
			break;
		}
	}
	
}
