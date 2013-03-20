package _02_includes.predicates;

import _02_includes.enums.Rolle;
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
                // TODO �bungsaufgabe Stuttgarter Testtage 2013: new RolleEbeneNiederlassung()
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