package de.heinerkuecker.constraint;

/**
 * Zu werfende Exception, wenn in der transform-Methode
 * {@link TransformablePredicate#transform} kein if-Zweig
 * für die übergebene sicher Operation
 * {@link TransformDestinationConstraintSafeOperation}
 * implementiert wurde.
 * 
 * @author Heiner K&uuml;cker
 */
@SuppressWarnings( "serial" )
public final class TransformMethodNotImplementedForSafeOperationException
extends RuntimeException
{
    /**
     * Konstruktor.
     */
    public TransformMethodNotImplementedForSafeOperationException(
            final int lineNr ,
            final TransformablePredicate predicateToTransform ,
            final TransformDestinationConstraintSafeOperation safeOperation )
    {
        super(
                "Transform if branch not implemented for safe operation " +
                safeOperation +
                " LineNr " + safeOperation.lineNr +
                " in prediacte " +
                predicateToTransform +
                " LineNr " + lineNr );
    }

}
