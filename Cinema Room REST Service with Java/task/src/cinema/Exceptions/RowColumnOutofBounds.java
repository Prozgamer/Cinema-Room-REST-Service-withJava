package cinema.Exceptions;

public class RowColumnOutofBounds extends  BussinesException{
    public RowColumnOutofBounds() {
        super("The number of a row or a column is out of bounds!");
    }
}
