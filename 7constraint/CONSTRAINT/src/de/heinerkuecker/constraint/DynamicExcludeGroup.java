package de.heinerkuecker.constraint;

import java.util.Collection;

/**
 * Spezielle dynamische ExcludeGroup f�r
 * parametrisierbare Pr�dikate.
 * 
 * @author Heiner K&uuml;cker
 * @param <CT> Type-Parameter Kontext-Objekt-Typ
 */
public class DynamicExcludeGroup<CT>
extends AbstractExcludeGroup<CT>
{
    private final ParametricalPredicate<CT> parametricalPredicate;
    
    /**
     * Konstruktor.
     * 
     * @param name
     */
    public DynamicExcludeGroup( 
            //final String name
            final ParametricalPredicate<CT> parametricalPredicate )
    {
        super( 
                //Name m�glichst gesch�tzt gegen versehentliche Mehrfachverwendung
                "DynamicExcludeGroup$" +
                parametricalPredicate.toString() );

        this.parametricalPredicate = parametricalPredicate;
    }

    @Override
    public boolean contains( 
            final PrimitivPredicate<CT> predicateToCheck )
    {
        final boolean result =
                this.parametricalPredicate.isExcludeTo(
                        predicateToCheck );

        //return result;
        if ( result )
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    /**
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
        return 
                "DynamicExcludeGroup[" +
                "name=" + this.name + ", " +
                "parametricalPredicate=" + this.parametricalPredicate +
                "]";
    }

}
