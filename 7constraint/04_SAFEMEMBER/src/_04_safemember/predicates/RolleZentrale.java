package _04_safemember.predicates;

import _04_safemember.enums.Rolle;
import de.heinerkuecker.constraint.Predicate;
import de.heinerkuecker.constraint.PrimitivPredicate;

/**
 * Primitives Prädikat Rolle Zentrale.
 * 
 * @author Heiner K&uuml;cker
 */
@SuppressWarnings( "serial" ) // serial version id wird nicht deklariert, damit der Cache Änderungen erkennt
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
                0 , 
                //includePredicateArr
                new RolleEbeneZentrale()
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
        return rolle instanceof Rolle.Zentrale;
    }

}