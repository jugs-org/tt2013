package _06_switch_fizzbuzz.predicates;

import de.heinerkuecker.constraint.Predicate;
import de.heinerkuecker.constraint.PrimitivPredicate;

/**
 * Prädikat Ebene Zentrale.
 * 
 * @author Heiner K&uuml;cker
 */
public final class Fizz
extends PrimitivPredicate<Integer>
{
    /**
     * Konstruktor.
     */
    public Fizz()
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
            final Integer contextObj )
    {
        return contextObj.intValue() % 3 == 0;
    }
 // wird alles in der Oberklasse erledigt
//    /**
//     * @see java.lang.Object#toString()
//     */
//    @Override
//    public String toString()
//    {
//        return 
//            this.getClass().getSimpleName();
//    }
//
//    /**
//     * @see Predicate#toFileName
//     */
//    @Override
//    public String toFileName( 
//            final String separatorStr )
//    {
//        return 
//            this.toString();
//    }
//
//    /**
//     * @see Object#hashCode()
//     */
//    @Override
//    public int hashCode()
//    {
//        return this.getClass().hashCode();
//    }
//
//    /**
//     * @see Object#equals
//     */
//    @Override
//    public boolean equals( 
//            final Object obj )
//    {
//        if (this == obj)
//            return true;
//        if (obj == null)
//            return false;
//
//        return this.getClass() == obj.getClass();
//    }
//
}