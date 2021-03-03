package com.spring.web.hotel.controller;

import com.spring.web.hotel.component.HotelModelAssembler;
import com.spring.web.hotel.exception.HotelNotFoundException;
import com.spring.web.hotel.model.Hotel;
import com.spring.web.hotel.repository.HotelRepository;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class HotelController {

    private final HotelRepository hotelRepository;
    private final HotelModelAssembler hotelModelAssembler;

    public HotelController(HotelRepository hotelRepository, HotelModelAssembler hotelModelAssembler) {
        this.hotelRepository = hotelRepository;
        this.hotelModelAssembler = hotelModelAssembler;
    }

    @GetMapping("/hotel")
    public CollectionModel<EntityModel<Hotel>> all() {
        List<EntityModel<Hotel>> hotels = hotelRepository.findAll()
                .stream()
                .map(hotelModelAssembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(hotels,
                linkTo(methodOn(HotelController.class).all()).withSelfRel());
    }

    @PostMapping("/hotel")
    public ResponseEntity<?> addHotel(@RequestBody Hotel hotel) {
        EntityModel<Hotel> entityModel = hotelModelAssembler.toModel(hotelRepository.save(hotel));
        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    @GetMapping("/hotel/{id}")
    public EntityModel<Hotel> one(@PathVariable Long id) {
        Hotel hotel = hotelRepository.findById(id)
                .orElseThrow(() -> new HotelNotFoundException(id));

        return hotelModelAssembler.toModel(hotel);
    }

    @PutMapping("/hotel/{id}")
    public ResponseEntity<?> updateHotel(@RequestBody Hotel newHotel, @PathVariable Long id) {
        Hotel updatedHotel = hotelRepository.findById(id)
                .map(hotel -> {
                    hotel.setName(newHotel.getName());
                    hotel.setAddress(newHotel.getAddress());
                    hotel.setCity(newHotel.getCity());
                    return hotelRepository.save(hotel);
                }).orElseGet(() -> {
                    newHotel.setId(id);
                    return hotelRepository.save(newHotel);
                });
        EntityModel<Hotel> entityModel = hotelModelAssembler.toModel(updatedHotel);
        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    @DeleteMapping("/hotel/{id}")
    public ResponseEntity<?> deleteHotel(@PathVariable Long id) {
        hotelRepository.deleteById(id);
        return ResponseEntity.noContent()
                .build();
    }
}
