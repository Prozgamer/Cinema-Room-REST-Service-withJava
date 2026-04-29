package cinema.Exceptions;

public class SeatPurchased extends BussinesException{
    public SeatPurchased() {
        super("The ticket has been already purchased!");
    }
}
