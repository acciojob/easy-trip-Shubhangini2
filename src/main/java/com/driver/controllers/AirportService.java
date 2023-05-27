package com.driver.controllers;

import com.driver.model.Airport;
import com.driver.model.City;
import com.driver.model.Flight;
import com.driver.model.Passenger;

import java.util.*;

public class AirportService {

    AirportRepository airportRepository= new AirportRepository();

    public String addAirport(Airport airport) {

        airportRepository.addAirport(airport);
        return "SUCCESS";
    }

    public String getLargestAirportName() {

        int maxNoOfTerminal=0;
        String airportNameWithMaxTerminal="";
        List<Airport> aiports = airportRepository.getAllAirport();
        for(Airport aiport: aiports){
            //Largest airport is in terms of terminals. 3 terminal airport is larger than 2 terminal airport
            if(aiport.getNoOfTerminals()> maxNoOfTerminal){
                maxNoOfTerminal= aiport.getNoOfTerminals();
                airportNameWithMaxTerminal = aiport.getAirportName();
            }
            if(aiport.getNoOfTerminals() == maxNoOfTerminal &&  aiport.getAirportName().compareTo(airportNameWithMaxTerminal)<0){
                maxNoOfTerminal= aiport.getNoOfTerminals();
                airportNameWithMaxTerminal = aiport.getAirportName();
            }
        }
        return airportNameWithMaxTerminal;
    }



    public String addFlight(Flight flight) {
        airportRepository.addFlight(flight);
        return "SUCCESS";
    }
    public double getShortestDurationOfPossibleBetweenTwoCities(City fromCity, City toCity) {
        //Find the duration by finding the shortest flight that connects these 2 cities directly
        //If there is no direct flight between 2 cities return -1.
        double minDurationPossible = Integer.MAX_VALUE;
        List<Flight> flights = airportRepository.getFlightByFromCityToCity(fromCity,toCity);
        for (Flight flight : flights) {
            if(flights.isEmpty())
            {
                minDurationPossible=-1; //no direct connecting flights
            }
            if (flight.getDuration() <  minDurationPossible ) {
                minDurationPossible = flight.getDuration();
            }
        }

        return minDurationPossible;
    }


    public String addPassenger(Passenger passenger) {
        airportRepository.addPassenger(passenger);
        return"SUCCESS";
    }

    public int countOfBookingsDoneByPassengerAllCombined(Integer passengerId) {
        List<Flight> flights= airportRepository.getAllFlight();
        Set<Integer> bookedPassengerIds= airportRepository.bookedPassengers;
        int count=0;

        for(Flight flight: flights){
            if(bookedPassengerIds.contains(passengerId)){
                count++;
            }
        }
        return count;
    }

    public String getAirportNameFromFlightId(Integer flightId) {

        String airportName= "null";
        List<Flight> flights= airportRepository.getAllFlight();
        Airport airport = new Airport();
        //We need to get the starting airportName from where the flight will be taking off (Hint think of City variable if that can be of some use)
        //return null incase the flightId is invalid or you are not able to find the airportName
        for(Flight flight: flights){
            if(flight.getFlightId()== flightId){
                City city= flight.getFromCity();
                if(airport.getCity().equals(city))
               airportName= airport.getAirportName();
            }
        }
        return airportName;
    }




    public String bookATicket(Integer flightId, Integer passengerId) {

      Flight flight = airportRepository.findFlightById(flightId);
       Set<Integer> bookedPassenger= airportRepository.bookedPassengers;

        //If the numberOfPassengers who have booked the flight is greater than : maxCapacity, in that case :
        //return a String "FAILURE"
        //Also if the passenger has already booked a flight then also return "FAILURE".
        //else if you are able to book a ticket then return "SUCCESS"

                if (flight == null) { //Invalid flight ID
                    return "FAILURE";
                }
                if (airportRepository.isPassengerBooked(passengerId)) { //Passenger already booked a flight
                    return "FAILURE";
                }
                if (bookedPassenger.size() >= flight.getMaxCapacity()){
                    return "FAILURE";
                }

                bookedPassenger.add(passengerId);
                return "SUCCESS";

    }

    public int getNumberOfPeopleOn(Date date, String airportName) {

       int  totalPassengers =0;
        List<Flight> flights= airportRepository.getAllFlight();
        List<Airport> airports = airportRepository.getAllAirport();
        for(Flight flight: flights){
            if(flight.getFlightDate().equals(date) && airports.contains(airportName)){
                totalPassengers+=flight.getMaxCapacity();
            }
        }
        return totalPassengers;
    }


}
