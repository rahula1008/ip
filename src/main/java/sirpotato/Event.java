package sirpotato;

import java.time.format.DateTimeFormatter;
import java.time.LocalDate;

public class Event extends Task {

    protected LocalDate from;
    protected LocalDate to;

    public Event(String description, LocalDate from, LocalDate to) {
        super(description);
        this.from = from;
        this.to = to;
    }

    @Override
    public String toString() { 
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd yyyy");
        return "[E]" + super.toString() + " (from: " + from.format(formatter) + " to: " + to.format(formatter) + ")";
    }

    public LocalDate getToDate() {
        return to;
    }

    public LocalDate getFromDate() {
        return from;
    }

}