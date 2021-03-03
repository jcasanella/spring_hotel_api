package com.spring.web.hotel.exception;

public class HotelNotFoundException extends RuntimeException {

    public HotelNotFoundException(Long id) {
        super("Could not find hotel: " + id);
    }
}
