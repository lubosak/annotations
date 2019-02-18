package com.inspired.annotation.example;

import java.time.LocalDate;

@DateOrder(dateFieldsAscending = { "first", "middle", "last" }, message = "dates not in order")
public class TestDateOrderThreeDatesPojo {

	private LocalDate first;
	private LocalDate middle;
	private LocalDate last;
	
	public LocalDate getFirst() {
		return first;
	}
	public void setFirst(LocalDate first) {
		this.first = first;
	}
	public LocalDate getMiddle() {
		return middle;
	}
	public void setMiddle(LocalDate middle) {
		this.middle = middle;
	}
	public LocalDate getLast() {
		return last;
	}
	public void setLast(LocalDate last) {
		this.last = last;
	}	

}
