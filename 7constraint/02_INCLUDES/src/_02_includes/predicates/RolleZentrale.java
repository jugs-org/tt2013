package _02_includes.predicates;

import _02_includes.enums.Rolle;
import de.heinerkuecker.constraint.Predicate;
import de.heinerkuecker.constraint.PrimitivPredicate;

/**
 * Primitives Pr�dikat Rolle Zentrale.
 * 
 * @author Heiner K&uuml;cker
 */
@SuppressWarnings( "serial" ) // serial version id wird nicht deklariert, damit der Cache �nderungen erkennt
public final class RolleZentrale
extends PrimitivPredicate<Rolle>
{
    /**
     * Konstruktor.
     */
    public RolleZentrale()
    {
        super( 
                //lineNr
                0
                //includePredicateArr
                // TODO �bungsaufgabe Stuttgarter Testtage 2013: new RolleEbeneZentrale()
                );
    }

    /**
     * @see Predicate#test
     */
    @Override
    public boolean test( 
            final Rolle rolle )
    {
        return rolle instanceof Rolle.Zentrale;
    }

}