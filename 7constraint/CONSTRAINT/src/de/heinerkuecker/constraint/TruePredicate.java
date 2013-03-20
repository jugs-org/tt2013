package de.heinerkuecker.constraint;

/**
 * Dieses Prädikat ist immer <code>true</code>.
 * 
 * @author Heiner K&uuml;cker
 * @param <CT>
 */
public final class TruePredicate<CT>
extends PrimitivPredicate<CT>
{

    protected TruePredicate()
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
        return true;
    }

// wird alles in der Oberklasse erledigt
//    @Override
//    public String toFileName( 
//            final String separatorStr )
//    {
//        return "True";
//    }

}
