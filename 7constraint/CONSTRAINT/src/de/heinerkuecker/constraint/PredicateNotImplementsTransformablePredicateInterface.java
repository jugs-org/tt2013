package de.heinerkuecker.constraint;


/**
 * Zu werfende Exception, wenn
 * ein f�r eine sichere Operation 
 * {@link TransformDestinationConstraintSafeOperation}
 * zu transformierendes Pr�dikat
 * {@link PrimitivPredicate}
 * nicht das Interface
 * {@link TransformablePredicate}
 * implementiert.
 * 
 * @author Heiner K&uuml;cker
 */
@SuppressWarnings( "serial" )
public final class PredicateNotImplementsTransformablePredicateInterface
extends RuntimeException
{
    /**
     * Konstruktor.
     * 
     * @param message
     */
    public PredicateNotImplementsTransformablePredicateInterface(
            final String message )
    {
        super( message );
    }

}
