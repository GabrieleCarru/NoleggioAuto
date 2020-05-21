package polito.it.noleggio.model;

import java.time.LocalTime;

public class Event implements Comparable<Event> {

	// Dichiarazione di costanti
	public enum EventType {
		NEW_CLIENT, CAR_RETURN;
	}
	
	// Dichiarazioni (minime) sempre presenti in classe Event
	private LocalTime time;
	private EventType type;
	
	/** Costruttore della classe Event
	 * @param time : {@code LocalTime} tempo di arrivo.
	 * @param type : {@code Enum } che definisce tipo di cliente arrivato.
	 */
	public Event(LocalTime time, EventType type) {
		super();
		this.time = time;
		this.type = type;
	}
	
	public LocalTime getTime() {
		return time;
	}
	
	public void setTime(LocalTime time) {
		this.time = time;
	}
	
	public EventType getType() {
		return type;
	}

	public void setType(EventType type) {
		this.type = type;
	}

	@Override
	public int compareTo(Event other) {
		return this.time.compareTo(other.time);
	}

	@Override
	public String toString() {
		return "Event [time=" + time + ", type=" + type + "]";
	}

	
	
	
	
}
