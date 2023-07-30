package com.bionoor.api.services;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bionoor.api.models.Address;
import com.bionoor.api.repositories.AddressRepository;

import jakarta.persistence.EntityNotFoundException;
@Service

public class AddressService {

	 @Autowired
    private  AddressRepository addressRepository;

   
   
    public Address saveAddress(Address address) {
        return addressRepository.save(address);
    }

    public Address getAddressById(Long id) {
        return addressRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Address not found with id: " + id));
    }

    public List<Address> getAllAddresses() {
        return addressRepository.findAll();
    }

    public void deleteAddress(Long id) {
        addressRepository.deleteById(id);
    }

    // Add additional methods as needed
}

