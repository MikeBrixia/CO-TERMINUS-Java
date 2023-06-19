package Game.Exceptions;

/** Exception threw when a developer tries to instantiate an
 * invalid or non-existing GameComponent. */
public class InvalidObjectException extends RuntimeException
{
    public InvalidObjectException(String errorMessage)
    {
        super(errorMessage);
    }
}
