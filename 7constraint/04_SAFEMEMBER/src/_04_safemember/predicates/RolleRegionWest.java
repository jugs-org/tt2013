package _04_safemember.predicates;

import _04_safemember.enums.Region;
import _04_safemember.enums.Rolle;
import _04_safemember.safemember.RolleSafeMember;
import de.heinerkuecker.constraint.Predicate;
import de.heinerkuecker.constraint.PrimitivPredicate;
import de.heinerkuecker.constraint.util.CollectionUtil;

/**
 * Primitives Prädikat Region West.
 * Kontext-Objekt-Typ {@link Rolle}.
 * 
 * @author Heiner K&uuml;cker
 */
@SuppressWarnings( "serial" ) // serial version id wird nicht deklariert, damit der Cache Änderungen erkennt
public final class RolleRegionWest
extends PrimitivPredicate<Rolle>
{
    /**
     * Konstruktor.
     */
    public RolleRegionWest()
    {
        super( 
                //lineNr
                0 , 
                //includePredicateSet
                null
                //safeMemberSet
                // TODO Übungsaufgabe Stuttgarter Testtage 2013: CollectionUtil.hashset( RolleSafeMember.regionWest )
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
                rolle != null &&
                ( ( rolle instanceof Rolle.Niederlassung &&
                        ( (Rolle.Niederlassung) rolle ).region.equals(
                                Region.West.instance ) ) ||
                  ( rolle instanceof Rolle.Filiale &&
                        ( (Rolle.Filiale) rolle ).niederlassung.region.equals(
                                Region.West.instance ) ) );
    }

}