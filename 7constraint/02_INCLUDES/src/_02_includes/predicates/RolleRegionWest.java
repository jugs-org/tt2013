package _02_includes.predicates;

import _02_includes.enums.Region;
import _02_includes.enums.Rolle;
import de.heinerkuecker.constraint.Predicate;
import de.heinerkuecker.constraint.PrimitivPredicate;

/**
 * Primitives Pr�dikat Region West.
 * Kontext-Objekt-Typ {@link Rolle}.
 * 
 * @author Heiner K&uuml;cker
 */
@SuppressWarnings( "serial" ) // serial version id wird nicht deklariert, damit der Cache �nderungen erkennt
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
                0
                //includePredicateSet
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