package de.heinerkuecker.constraint;

/**
 * Klasse zum tempor�ren Halten der
 * Definition eines Set-Constraints
 * nur f�r den Aufruf der Methode
 * {@link AbstractConstraintCodeGenerator#equalHashset}.
 * 
 * @author Heiner K&uuml;cker
 */
public final class SetOr<CT>
{
    /**
     * Pr�dikat
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
