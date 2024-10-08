package com.hafssa.reservationposition.services.impl;

import com.hafssa.reservationposition.dtos.PositionDto;
import com.hafssa.reservationposition.dtos.ReservationDto;
import com.hafssa.reservationposition.entities.Position;
import com.hafssa.reservationposition.entities.Reservation;
import com.hafssa.reservationposition.repositories.PositionRepository;
import com.hafssa.reservationposition.repositories.ReservationRepository;
import com.hafssa.reservationposition.services.PositionService;
import com.hafssa.reservationposition.services.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class PositionServiceImpl implements PositionService {


    @Autowired
    private PositionRepository positionRepository;


    @Autowired
    private ReservationService reservationService;


    @Autowired
    private ReservationRepository reservationRepository;

    @Override
    public List<PositionDto> getAllPositions() {
        List<Position> positions = positionRepository.findAll();
        return positions.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());

    }






    @Override
    public PositionDto getPositionById(int id) {
        Optional<Position> positionOptional = positionRepository.findById(id);
        if (positionOptional.isPresent()) {
            Position position = positionOptional.get();
            return convertToDto(position);
        } else {
            throw new RuntimeException("Position not found with id: " + id);
        }
    }


    public List<PositionDto> getPositionsByDate(LocalDate date) {
        List<Position> positions = positionRepository.findAll();

        return positions.stream()
                .map(position -> {
                    List<Reservation> filteredReservations = position.getReservations().stream()
                            .filter(reservation -> {
                                LocalDate dateDeb = reservation.getDateDeb().atZone(ZoneId.systemDefault()).toLocalDate();
                                LocalDate dateFin = reservation.getDateFin().atZone(ZoneId.systemDefault()).toLocalDate();
                                return !date.isBefore(dateDeb) && !date.isAfter(dateFin);
                            })
                            .toList();

                    PositionDto positionDto = convertToDto(position);
                    positionDto.setReservations(filteredReservations.stream()
                            .map(reservationService::convertToDto)
                            .collect(Collectors.toList()));
                    return positionDto;
                })
                .collect(Collectors.toList());
    }











    private PositionDto convertToDto(Position position) {
        List<ReservationDto> reservationDtos = new ArrayList<>();
        if (position.getReservations() != null) {
            reservationDtos = position.getReservations().stream()
                    .map(reservation -> new ReservationDto(
                            reservation.getDateDeb(),
                            reservation.getDateFin(),
                            reservation.getUser().getId(),
                            reservation.getPosition().getId(),
                            reservation.getUser().getFirstName(),
                            reservation.getUser().getLastName(),
                            reservation.getPosition().getNumero()
                            ))
                    .collect(Collectors.toList());
        }

        return new PositionDto(position.getId(), position.getNumero(), reservationDtos);
    }

    public Position findByNumero(String numero) {
        return positionRepository.findByNumero(numero);
    }


    }
