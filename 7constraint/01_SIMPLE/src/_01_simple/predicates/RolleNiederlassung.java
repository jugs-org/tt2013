package _01_simple.predicates;

import _01_simple.enums.Rolle;
import de.heinerkuecker.constraint.Predicate;
import de.heinerkuecker.constraint.PrimitivPredicate;

/**
 * Primitives Pr�dikat Rolle Niederlassung.
 * 
 * @author Heiner K&uuml;cker
 */
@SuppressWarnings( "serial" ) // serial version id wird nicht deklariert, damit der Cache �nderungen erkennt
public final class RolleNiederlassung
extends PrimitivPredicate<Rolle>
{
    /**
     * Konstruktor.
     */
    public RolleNiederlassung()
    {
        super( 
                //lineNr
                0
                //includePredicateArr
                //null ,
                //safeMemberSet
                //null
                );
    }

    /**
     * @see Predicate#test
     */
    @Override
    public boolean test( 
            final Rolle rolle )
    {
        return rolle instanceof Rolle.Niederlassung;
    }

}