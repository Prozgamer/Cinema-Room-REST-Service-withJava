package cinema.services;

import cinema.Configs.CinemaProps;
import cinema.Exceptions.RowColumnOutofBounds;
import cinema.Exceptions.SeatPurchased;
import cinema.Exceptions.WrongPassException;
import cinema.Exceptions.WrongTokenException;
import cinema.Repositories.CinemaRepository;
import cinema.models.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CinemaService {
    private final CinemaRepository cinemaRepository;
    private final Object lock = new Object();
    private final CinemaProps props;

    @Value("${cinema.secret}")
    private String secret;

    public CinemaService(CinemaRepository cinemaRepository1, CinemaProps props) {
        this.cinemaRepository = cinemaRepository1;
        this.props = props;
    }

    public List<SeatWithPrice> GetAvailableSeatsInfo() {
        return cinemaRepository.GetAvailableSeats().stream()
                .map(seat -> new SeatWithPrice(seat.row(), seat.column(), CalculatePrice(seat.row())))
                .toList();
    }

    public int CalculatePrice(int row){
        return row <= props.Price().nFirstRows() ? props.Price().firstRows() : props.Price().lastRows();
    }

    public synchronized PurchasedTicket purchase(int row, int column){
        Seat seat = new Seat(row, column);
        int price;
        String token = UUID.randomUUID().toString();
        synchronized (lock){
            if(!cinemaRepository.IsAvailableSeats(seat)){
                throw new SeatPurchased();
            }
            price = CalculatePrice(row);
            cinemaRepository.SetPurchased(seat, price, token);
        }
        return new PurchasedTicket(token, new SeatWithPrice(row, column, price));
    }
    public void validate(int row, int column){
        if(row<1 || row>props.Hall().rows()
                || column < 1 || column > props.Hall().columns()){
            throw new RowColumnOutofBounds();
        }
    }
    public synchronized TicketReturnResponse ReturnTicket (String token){
        synchronized (lock){
            var ticket = cinemaRepository.RemoveTicketByToken(token);
            if(ticket == null){
                throw new WrongTokenException();
            }
            return new TicketReturnResponse(ticket);
        }
    }

    public void validatePass(String password){
        if(password == null || !secret.equals(password)){
            throw new WrongPassException();
        }
    }

    public StatsResponse GetStats(){
        synchronized (lock){
            int available = cinemaRepository.GetAvailableSeatsCount();
            int TotalIncome = cinemaRepository.GetIncome();
            int Sold = cinemaRepository.SoldCount();
            return new StatsResponse(TotalIncome,available,Sold); //Student: Bohdan Moshnenko
        }
    }
}
