package com.spring.web.hotel.component;

import com.spring.web.hotel.controller.HotelController;
import com.spring.web.hotel.model.Hotel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class HotelModelAssembler implements RepresentationModelAssembler<Hotel, EntityModel<Hotel>> {
    @Override
    public EntityModel<Hotel> toModel(Hotel entity) {
        return EntityModel.of(entity,
                linkTo(methodOn(HotelController.class).one(entity.getId())).withSelfRel(),
                linkTo(methodOn(HotelController.class).all()).withRel("hotel"));
    }
}
