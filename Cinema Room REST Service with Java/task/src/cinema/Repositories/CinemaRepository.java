package cinema.Repositories;

import cinema.Configs.CinemaProps;
import cinema.models.Seat;
import cinema.models.SeatWithPrice;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class CinemaRepository {
    private final Map<Seat, Integer/*price*/> seats = new LinkedHashMap<>();
    private final Map<String/*id*/, SeatWithPrice/*price*/> sold = new LinkedHashMap<>();
    private final CinemaProps props;
    public CinemaRepository(CinemaProps props) {
        this.props = props;
    }
    @PostConstruct
    void init(){
        for(int iRow = 1; iRow <= props.Hall().rows(); iRow++){
            for(int iCol= 1; iCol <= props.Hall().columns(); iCol++){
                seats.put(new Seat(iRow, iCol), null);
            }
        }
    }
    public List<Seat> GetAvailableSeats(){
        return seats.entrySet().stream()
                .filter(entry -> entry.getValue() == null)
                .map(Map.Entry::getKey)
                .toList();
    }

    public boolean IsAvailableSeats(Seat seat){
        return seats.get(seat) == null;
    }

    public void SetPurchased(Seat seat, int price, String token){
        seats.put(seat, price);
        sold.put(token,new SeatWithPrice(seat.row(), seat.column(), price));
    }

    public SeatWithPrice RemoveTicketByToken(String token){
        var ticket = sold.remove(token);
        if(ticket != null){
            seats.put(new Seat(ticket.row(), ticket.column()), null);
        }
        return ticket;
    }

    public int GetAvailableSeatsCount(){
        return (int) seats.values().stream()
                .filter(Objects::isNull)
                .count();
    }

    public int GetIncome(){
        return seats.values().stream()
                .filter(Objects::nonNull)
                .mapToInt(Integer::intValue)
                .sum();
    }

    public int SoldCount(){
        return sold.size();
    }
}
