package de.heinerkuecker.constraint;

/**
 * Dieses Prädikat ist immer <code>false</code>.
 * 
 * @author Heiner K&uuml;cker
 * @param <CT>
 */
public final class FalsePredicate<CT>
extends PrimitivPredicate<CT>
{

    protected FalsePredicate()
    {
        super( 
                //lineNr
                0
                //implicitsPredicateSet
                //null ,
                //safeMemberSet
                //null
                );
    }

    @Override
    public boolean test( 
            final CT contextObj )
    {
        return false;
    }

// wird alles in der Oberklasse erledigt
//    @Override
//    public String toFileName( 
//            final String separatorStr )
//    {
//        return "False";
//    }

}
