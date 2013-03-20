package de.heinerkuecker.constraint;


/**
 * Zu werfende Exception, wenn
 * ein für eine sichere Operation 
 * {@link TransformDestinationConstraintSafeOperation}
 * zu transformierendes Prädikat
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
