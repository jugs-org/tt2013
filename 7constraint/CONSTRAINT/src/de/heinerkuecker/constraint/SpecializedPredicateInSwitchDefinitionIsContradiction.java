package de.heinerkuecker.constraint;

/**
 * Zu werfende Exception, wenn die And-Verkn�pfung
 * eines spezialisierenden Pr�dikates mit der
 * urspr�nglichen Constraint-Expression nicht
 * erf�llbar ist (Kontradiktion).
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
