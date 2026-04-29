package cinema.models;

public record PurchasedTicket(
        String token,
        SeatWithPrice ticket
) {
}
