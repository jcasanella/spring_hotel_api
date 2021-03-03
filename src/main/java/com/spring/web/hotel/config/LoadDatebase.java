package com.spring.web.hotel.config;

import com.spring.web.hotel.model.Hotel;
import com.spring.web.hotel.repository.HotelRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoadDatebase {
    private static final Logger LOG = LoggerFactory.getLogger(LoadDatebase.class);

    @Bean
    CommandLineRunner initDatabase(HotelRepository hotelRepository) {
        return args -> {
            LOG.info("Preloading " + hotelRepository.save(new Hotel("Gran Hotel", "Street1", "Bcn")));
            LOG.info("Preloading " + hotelRepository.save(new Hotel("Hotel Centurion", "Beach Street", "Cambrils")));
        };
    }
}
