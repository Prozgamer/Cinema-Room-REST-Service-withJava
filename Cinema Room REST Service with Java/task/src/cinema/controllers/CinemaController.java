package cinema.controllers;

import cinema.Configs.CinemaProps;
import cinema.Exceptions.BussinesException;
import cinema.models.*;
import cinema.services.CinemaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class CinemaController {
    private final CinemaService cinemaService;
    private final CinemaProps props;
    public CinemaController(CinemaService cinemaService, CinemaProps props) {
        this.cinemaService = cinemaService;
        this.props = props;
    }

    @GetMapping("/seats")
    public AvailableSeatsInfo getSeats(){
        List<SeatWithPrice> seats = cinemaService.GetAvailableSeatsInfo();
        return new AvailableSeatsInfo(props.Hall().rows(), props.Hall().columns(), seats);
    }


    @PostMapping("/purchase")
    public ResponseEntity<PurchasedTicket> PurchaseSeat(
          @RequestBody PurchaseRequest purchaseRequest
    ){
            cinemaService.validate(purchaseRequest.row(), purchaseRequest.column());
            var res = cinemaService.purchase(
                    purchaseRequest.row(),
                    purchaseRequest.column());
            return ResponseEntity.ok(res);
    }

    @PostMapping("/return")
    public ResponseEntity<TicketReturnResponse> ReturnTicket(
            @RequestBody TicketReturnRequest ticketReturnRequest
    ){
        var res = cinemaService.ReturnTicket(ticketReturnRequest.token());
        return ResponseEntity.ok(res);
    }

    @GetMapping("/stats")
    public StatsResponse GetStats(@RequestParam(value = "password", required = false) String password){
        cinemaService.validatePass(password);
        return cinemaService.GetStats();
    }

}
