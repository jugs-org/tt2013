package _02_includes.predicates;

import _02_includes.enums.Rolle;
import de.heinerkuecker.constraint.Predicate;
import de.heinerkuecker.constraint.PrimitivPredicate;

/**
 * Primitives Pr�dikat Ebene Filiale, Region Nord.
 * Kontext-Objekt-Typ {@link Rolle}.
 * 
 * @author Heiner K&uuml;cker
 */
@SuppressWarnings( "serial" ) // serial version id wird nicht deklariert, damit der Cache �nderungen erkennt
public final class RolleFilialeNord
extends PrimitivPredicate<Rolle>
{
    /**
     * Konstruktor.
     */
    public RolleFilialeNord()
    {
        super( 
                //lineNr
                0
                //includePredicateArr
                // TODO �bungsaufgabe Stuttgarter Testtage 2013: new RolleFiliale() ,
                // TODO �bungsaufgabe Stuttgarter Testtage 2013: new RolleRegionNord()
                );
    }

    /**
     * @see Predicate#test
     */
    @Override
    public boolean test( 
            final Rolle rolle )
    {
        return
                rolle instanceof Rolle.FilialeNord;
    }

}