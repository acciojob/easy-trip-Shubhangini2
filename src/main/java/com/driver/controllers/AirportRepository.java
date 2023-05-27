package com.driver.controllers;

import com.driver.model.Airport;
import com.driver.model.City;
import com.driver.model.Flight;
import com.driver.model.Passenger;

import java.util.Set;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public class AirportRepository {

    HashMap<String, Airport> airportMap;
    HashMap<Integer, Flight> flightMap;
    HashMap<Integer, Passenger> passengerMap;

    Set<Integer> bookedPassengers;

    List<Flight>flights;


    public AirportRepository() {
        this.airportMap = new HashMap<String, Airport>();
        this.flightMap = new HashMap<Integer, Flight>();
        this.passengerMap = new HashMap<Integer, Passenger>();
    }

    public void addAirport(Airport airport) {
        airportMap.put(airport.getAirportName(), airport);
    }

    public List<Airport> getAllAirport() {
        return new ArrayList<>(airportMap.values());
    }





    public void addFlight(Flight flight) {
        flightMap.put(flight.getFlightId(), flight);
    }

    public List<Flight> getFlightByFromCityToCity(City fromCity, City toCity) {
        List<Flight> matchingFLight = new ArrayList<>();

        for (Flight flight : matchingFLight) {
            if (flight.getFromCity().equals(fromCity) && flight.getToCity().equals(toCity))
                matchingFLight.add(flight);
        }
        return matchingFLight;
    }

    public List<Flight> getAllFlight(){
        return new ArrayList<>(flightMap.values());
    }

    public Flight findFlightById(int flightId) {
     for(Flight flight: flights){
         if(flight.getFlightId()==flightId){
             return flight;
         }
     }
     return null;
    }




    public void addPassenger(Passenger passenger) {
        passengerMap.put(passenger.getPassengerId(), passenger);
    }


    public boolean isPassengerBooked(int passengerId) {
        return bookedPassengers.contains(passengerId);
    }


}
