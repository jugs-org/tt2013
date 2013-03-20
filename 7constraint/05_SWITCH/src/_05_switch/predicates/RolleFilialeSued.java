package _05_switch.predicates;

import _05_switch.enums.Rolle;
import de.heinerkuecker.constraint.Predicate;
import de.heinerkuecker.constraint.PrimitivPredicate;

/**
 * Primitives Pr�dikat Ebene Filiale, Region Sued.
 * Kontext-Objekt-Typ {@link Rolle}.
 * 
 * @author Heiner K&uuml;cker
 */
@SuppressWarnings( "serial" ) // serial version id wird nicht deklariert, damit der Cache �nderungen erkennt
public final class RolleFilialeSued
extends PrimitivPredicate<Rolle>
{
    /**
     * Konstruktor.
     */
    public RolleFilialeSued()
    {
        super( 
                //lineNr
                0 , 
                //includePredicateArr
                new RolleFiliale() ,
                new RolleRegionSued()
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
                rolle instanceof Rolle.Filiale;
    }

}