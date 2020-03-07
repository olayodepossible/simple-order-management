package com.possible.order.management.rest.resource;

import org.springframework.hateoas.ResourceSupport;

import com.possible.order.management.rest.domain.Order;
import com.fasterxml.jackson.annotation.JsonProperty;

public class OrderResource extends ResourceSupport {

	private final long id;
	private final String description;
	private final long costInCents;
	private final boolean isComplete;
	
	public OrderResource(Order order) {
		id = order.getId();
		description = order.getDescription();
		costInCents = order.getCostInCents();
		isComplete = order.isComplete();
	}

	@JsonProperty("id")
	public Long getResourceId() {
		return id;
	}
	
	public String getDescription() {
		return description;
	}

	public long getCostInCents() {
		return costInCents;
	}

	public boolean isComplete() {
		return isComplete;
	}
}
