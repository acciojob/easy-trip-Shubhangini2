package com.driver.controllers;

import com.driver.model.Airport;
import com.driver.model.City;
import com.driver.model.Flight;
import com.driver.model.Passenger;
import org.springframework.beans.factory.annotation.Autowired;


import java.util.*;

public class AirportService {

  AirportRepository airportRepository= new AirportRepository();

//    @Autowired
//    AirportRepository airportRepository;
    public void addAirport(Airport airport) {

        airportRepository.addAirport(airport);
    }



    public String getLargestAirportName() {
//       List<Airport> airports = airportRepository.getAllAirport();
//        int maxNoOfTerminal=0;
//        String airportNameWithMaxTerminal="";
//        for(Airport airport: airports){
//            //Largest airport is in terms of terminals. 3 terminal airport is larger than 2 terminal airport
//            if(airport.getNoOfTerminals()> maxNoOfTerminal){
//                maxNoOfTerminal= airport.getNoOfTerminals();
//                airportNameWithMaxTerminal = airport.getAirportName();
//            }
//            if(airport.getNoOfTerminals() == maxNoOfTerminal &&  airport.getAirportName().compareTo(airportNameWithMaxTerminal)<0){
//                maxNoOfTerminal= airport.getNoOfTerminals();
//                airportNameWithMaxTerminal = airport.getAirportName();
//            }
//        }
//        return airportNameWithMaxTerminal;

        List<Airport> airports = airportRepository.getAllAirports();
        int max=0;
        String name= airports.get(0).getAirportName();

        for(Airport port: airports){
            int t=port.getNoOfTerminals();
            if(t>max){
                name= port.getAirportName();
                max=t;
            }
            if(t==max){
                if(name.compareTo(port.getAirportName())>0){
                    name=port.getAirportName();
                }
            }
        }
        return name;
    }





    public String addFlight(Flight flight) {
        airportRepository.addFlight(flight);
        return "SUCCESS";
    }




    public double findShortestDuration(City fromCity, City toCity) {
        //Find the duration by finding the shortest flight that connects these 2 cities directly
        //If there is no direct flight between 2 cities return -1.
        double minDurationPossible = Double.MAX_VALUE;
        List<Flight> flights = airportRepository.getAllFlights();
        for (Flight flight : flights) {
//            if(flights.isEmpty())
//            {
//                minDurationPossible=-1; //no direct connecting flights
//            }
//            if (flight.getDuration() <  minDurationPossible ) {
//                minDurationPossible = flight.getDuration();
//            }

            if(flight.getFromCity().equals(fromCity) && flight.getToCity().equals(toCity)){
                if(flight.getDuration()< minDurationPossible){
                    minDurationPossible=flight.getDuration();
                }
            }
        }
        if(minDurationPossible== Double.MAX_VALUE){
            //return Double.valueOf("-1");
            return Double.parseDouble("-1");
        }
        return minDurationPossible;
    }



//    public int getNumberOfPeopleOn(Date date, String airportName) {
//
//        int  totalPassengers =0;
//        List<Flight> flights= airportRepository.getAllFlight();
//        List<Airport> airports = airportRepository.getAllAirport();
//        for(Flight flight: flights){
//            if(flight.getFlightDate().equals(date) && airports.contains(airportName)){
//                totalPassengers+=flight.getMaxCapacity();
//            }
//        }
//        return totalPassengers;
//    }

    public Integer findNumberOfPeopleOn(Date date, String airportName) {

        int  totalPassengers =0;
        List<Flight> flights= airportRepository.getAllFlights();
      Optional<Airport> airportOpt = airportRepository.getAirportByName(airportName);

        Map<Integer, ArrayList<Integer>> getAllFlightBookingPassengers = airportRepository.getAllFlightBookings();
      if(airportOpt.isEmpty()){
          throw new RuntimeException("airport not present");
      }

      City city= airportOpt.get().getCity();

      for(Flight flight: flights){
            if(flight.getFlightDate().equals(date) && (flight.getFromCity().equals(city) || flight.getToCity().equals(city))){
                //totalPassengers += flight.getMaxCapacity();
                totalPassengers += getAllFlightBookingPassengers.size();
            }
        }
        return totalPassengers;
    }


    public String addPassenger(Passenger passenger) {
        airportRepository.addPassenger(passenger);
        return"SUCCESS";
    }

    public String getAirportNameFromFlightId(Integer flightId) {
        Optional <Flight> flightOpt= airportRepository.getFlightById(flightId);
        if(flightOpt.isEmpty()){
            return null;
        }
        Flight flight= flightOpt.get();
        City city= flight.getFromCity();
        List<Airport> airports = airportRepository.getAllAirports() ;
        for(Airport airport : airports){
            if(airport.getCity().equals(city)){
                return airport.getAirportName();
            }
        }
        return null;
        //We need to get the starting airportName from where the flight will be taking off (Hint think of City variable if that can be of some use)
        //return null incase the flightId is invalid or you are not able to find the airportName
//        for(Flight flight: flights){
//            if(flight.getFlightId()== flightId){
//                City city= flight.getFromCity();
//                if(airport.getCity().equals(city))
//               airportName= airport.getAirportName();
//            }
//        }
//        return airportName;
    }
    public String bookATicket(Integer flightId, Integer passengerId) {

        Optional<Flight> flightOpt = airportRepository.getFlightById(flightId);

        if (flightOpt.isEmpty()) {
            return "FAILURE";
        }

        ArrayList<Integer> passenger = airportRepository.getPassengerForFlight(flightId);

        if (passenger.size() >= flightOpt.get().getMaxCapacity()) {
            return "FAILURE";
        }
        if (passenger.contains(passengerId)) {
            return "FAILURE";
        }

        Optional<Passenger> passengerOpt = airportRepository.getPassengerById(passengerId);
        if (passengerOpt.isEmpty()) {
            return "FAILURE";
        }

        airportRepository.bookFlight(flightId, passengerId);
        return "SUCCESS";
    }
        //If the numberOfPassengers who have booked the flight is greater than : maxCapacity, in that case :
        //return a String "FAILURE"
        //Also if the passenger has already booked a flight then also return "FAILURE".
        //else if you are able to book a ticket then return "SUCCESS"

//                if (flight == null) { //Invalid flight ID
//                    return "FAILURE";
//                }
//                if (airportRepository.isPassengerBooked(passengerId)) { //Passenger already booked a flight
//                    return "FAILURE";
//                }
//                if (bookedPassenger.size() >= flight.getMaxCapacity()){
//                    return "FAILURE";
//                }
//
//                bookedPassenger.add(passengerId);
//                return "SUCCESS";



    public String cancelATicket(Integer flightId, Integer passengerId) {

        Optional<Flight> flightOpt = airportRepository.getFlightById(flightId);

        //flight not present
        if (flightOpt.isEmpty()) {
            return "FAILURE";
        }
        //passenger not present
        Optional<Passenger> passengerOpt = airportRepository.getPassengerById(passengerId);
        if (passengerOpt.isEmpty()) {
            return "FAILURE";
        }
    //get all the passenger

        ArrayList<Integer> passenger = airportRepository.getPassengerForFlight(flightId);
        //if not booked the flight
        if (!passenger.contains(passengerId)) {
            return "FAILURE";
        }
        airportRepository.cancelFlight(flightId,passengerId);
        return "SUCCESS";
    }

    public int getNoOfBookingsByPassenger(Integer passengerId) {

        Map<Integer, ArrayList<Integer>> map = airportRepository.getAllFlightBookings();
        int ans=0;
        for(Map.Entry<Integer,ArrayList<Integer>> entry : map.entrySet()){
            if(entry.getValue().contains(passengerId)){
                ans++;
            }
        }
        return ans;
    }

    public int getFlightFare(Integer flightId) {

        ArrayList<Integer> pass= airportRepository.getPassengerForFlight(flightId);
        return 3000 + pass.size() * 50;
    }

    public int getRevenueForFlight(Integer flightId) {

        ArrayList<Integer> pass= airportRepository.getPassengerForFlight(flightId);
        int n= pass.size();
        //3000 * n + 0+ 50+ 100 //if there are 3 passenger it will got till 100


        return 3000 * n + 50*(n)*(n-1)/2; //if it is starting from 1 we should do n+1

    }
}































    

//    public int countOfBookingsDoneByPassenger(Integer passengerId) {
//        List<Flight> flights= airportRepository.getAllFlightBookings();
//        Set<Integer> bookedPassengerIds= airportRepository.bookedPassengers;
//        int count=0;
//
//        for(Flight flight: flights){
//            if(bookedPassengerIds.contains(passengerId)){
//                count++;
//            }
//        }
//        return count;
//    }


//    public int getPassengerForFlight(Integer flightId){
//        if(flightPassengerMap.containsKey(flightId)){
//
//        }
//    }


