package de.heinerkuecker.constraint;

/**
 * Klasse zum temporären Halten der
 * Definition eines Set-Constraints
 * nur für den Aufruf der Methode
 * {@link AbstractConstraintCodeGenerator#equalHashset}.
 * 
 * @author Heiner K&uuml;cker
 */
public final class SetOr<CT>
{
    /**
     * Prädikat
     */
    public final Or<CT> predicate;

    /**
     * Konstruktor.
     * @param predicate
     */
    public SetOr( 
            final Or<CT> predicate )
    {
        this.predicate = predicate;
    }

}
