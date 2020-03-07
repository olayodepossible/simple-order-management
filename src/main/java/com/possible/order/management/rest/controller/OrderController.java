package com.possible.order.management.rest.controller;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.possible.order.management.rest.domain.Order;
import com.possible.order.management.rest.repository.OrderRepository;
import com.possible.order.management.rest.resource.OrderResource;
import com.possible.order.management.rest.resource.OrderResourceAssembler;

@CrossOrigin(origins = "*")
@RestController
@ExposesResourceFor(Order.class)
@RequestMapping(value = "/order")
public class OrderController {
	
	@Autowired
	private OrderRepository repository;
	
	@Autowired
	private OrderResourceAssembler assembler;

	@GetMapping
	public ResponseEntity<Collection<OrderResource>> findAllOrders() {
		List<Order> orders = repository.findAll();
		return new ResponseEntity<>(assembler.toResourceCollection(orders), HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<OrderResource> createOrder(@RequestBody Order order) {
		Order createdOrder = repository.create(order);
		return new ResponseEntity<>(assembler.toResource(createdOrder), HttpStatus.CREATED);
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<OrderResource> findOrderById(@PathVariable Long id) {
		Optional<Order> order = repository.findById(id);

		if (order.isPresent()) {
			return new ResponseEntity<>(assembler.toResource(order.get()), HttpStatus.OK);
		}
		else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
		boolean wasDeleted = repository.delete(id);
		HttpStatus responseStatus = wasDeleted ? HttpStatus.NO_CONTENT : HttpStatus.NOT_FOUND;
		return new ResponseEntity<>(responseStatus);
	}
	
	@PutMapping(value = "/{id}", consumes = "application/json")
	public ResponseEntity<OrderResource> updateOrder(@PathVariable Long id, @RequestBody Order updatedOrder) {
		boolean wasUpdated = repository.update(id, updatedOrder);
		
		if (wasUpdated) {
			return findOrderById(id);
		}
		else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
}
