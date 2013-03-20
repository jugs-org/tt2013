package de.heinerkuecker.constraint;

/**
 * Zu werfende Exception, wenn die And-Verknüpfung
 * eines spezialisierenden Prädikates mit der
 * ursprünglichen Constraint-Expression nicht
 * erfüllbar ist (Kontradiktion).
 * 
 * @author Heiner K&uuml;cker
 */
public final class SpecializedPredicateInSwitchDefinitionIsContradiction
extends RuntimeException
{
    /**
     * Konstruktor.
     * @param message
     */
    public SpecializedPredicateInSwitchDefinitionIsContradiction(
            final String message )
    {
        super( message );
    }

}
