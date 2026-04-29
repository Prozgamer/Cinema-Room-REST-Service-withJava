package cinema.models;


import java.util.List;

public record AvailableSeatsInfo(
    int rows,
    int columns,
    List<SeatWithPrice> seats
){
}



