package com.driver.controllers;

import com.driver.model.Airport;
import com.driver.model.City;
import com.driver.model.Flight;
import com.driver.model.Passenger;
import io.swagger.models.auth.In;

import javax.swing.text.html.Option;
import java.util.Set;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public class AirportRepository {

    HashMap<String, Airport> airportData= new HashMap<>();
    HashMap<Integer, Flight> flightData=new HashMap<>();;
    HashMap<Integer, Passenger> passengerData=new HashMap<>();;

    HashMap<Integer, ArrayList<Integer>> flightPassengerData= new HashMap<>();
           //flightId //List<passengerId>


    public void addAirport(Airport airport) {

        airportData.put(airport.getAirportName(), airport);
    }

    public List<Airport> getAllAirports() {
        return new ArrayList<>(airportData.values());
    }







    public void addFlight(Flight flight) {
        flightData.put(flight.getFlightId(), flight);
    }

    public List<Flight> getAllFlights(){

        return new ArrayList<>(flightData.values());
    }

//    public List<Flight> getFlightByFromCityToCity(City fromCity, City toCity) {
//        List<Flight> matchingFLight = new ArrayList<>();
//
//        for (Flight flight : matchingFLight) {
//            if (flight.getFromCity().equals(fromCity) && flight.getToCity().equals(toCity))
//                matchingFLight.add(flight);
//        }
//        return matchingFLight;
//    }



    public Optional <Flight> getFlightById(Integer flightId) {

         if(flightData.containsKey(flightId)){
             return Optional.of(flightData.get(flightId));
         }
     return Optional.empty();
    }




    public void addPassenger(Passenger passenger) {
        passengerData.put(passenger.getPassengerId(), passenger);
    }


//    public boolean isPassengerBooked(int passengerId) {
//
//        return bookedPassengers.contains(passengerId);
//    }


    public Optional <Airport> getAirportByName(String airportName) {
        if(airportData.containsKey(airportName)){
            return Optional.of(airportData.get(airportName));
        }
        return Optional.empty();
    }

    public ArrayList<Integer> getPassengerForFlight(Integer flightId) {
        if(flightPassengerData.containsKey(flightId)){
            return flightPassengerData.get(flightId);
        }
        return new ArrayList<>() ;
    }

    public Optional <Passenger> getPassengerById(Integer passengerId) {
        if(passengerData.containsKey(passengerId)){
            return Optional.of(passengerData.get(passengerId));
        }
        return Optional.empty();
    }

    public void bookFlight(Integer flightId, Integer passengerId) {

        ArrayList<Integer> passenger = flightPassengerData.getOrDefault(flightId, new ArrayList<>());
        passenger.add(passengerId);
        flightPassengerData.put(flightId, passenger);
    }

    public void cancelFlight(Integer flightId, Integer passengerId) {
        ArrayList<Integer> passenger = flightPassengerData.getOrDefault(flightId, new ArrayList<>());
        passenger.remove(passengerId);
        flightPassengerData.put(flightId, passenger);
    }

    public Map<Integer, ArrayList<Integer>> getAllFlightBookings() {
        return flightPassengerData;
    }
}
