package _02_includes.predicates;

import _02_includes.enums.Rolle;
import de.heinerkuecker.constraint.Predicate;
import de.heinerkuecker.constraint.PrimitivPredicate;

/**
 * Primitives Prädikat Rolle Niederlassung.
 * 
 * @author Heiner K&uuml;cker
 */
@SuppressWarnings( "serial" ) // serial version id wird nicht deklariert, damit der Cache Änderungen erkennt
public final class RolleNiederlassungNord
extends PrimitivPredicate<Rolle>
{
    /**
     * Konstruktor.
     */
    public RolleNiederlassungNord()
    {
        super( 
                //lineNr
                0
                //includePredicateArr
                // TODO Übungsaufgabe Stuttgarter Testtage 2013: new RolleNiederlassung() ,
                // TODO Übungsaufgabe Stuttgarter Testtage 2013: new RolleRegionNord()
                );
    }

    /**
     * @see Predicate#test
     */
    @Override
    public boolean test( 
            final Rolle rolle )
    {
        return rolle instanceof Rolle.NiederlassungNord;
    }

}