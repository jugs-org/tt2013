package de.heinerkuecker.constraint;


/**
 * Tupel aus dem Case-Pr�dikat
 * einer {@link SwitchDefinition}
 * und dem beim Generieren der
 * Case-Methode eingesetzten
 * Constraint-Parameter-Type.
 * 
 * @author Heiner K&uuml;cker
 */
public final class CasePredicateSpecializedPredicateTuple<CT>
{
    /**
     * Spezialisierendes Pr�dikat,
     * welches in der definition des
     * Switch angegeben wurde.
     */
    final Predicate<CT> casePredicate;

    //Predicate<CT> paramPredicateNotSimplifiedForExclude;

    /**
     * spezialisiertes Pr�dikat, welches
     * der Parameter-Typ der entsprechenden
     * AbstractSwitchCase-Methode wird.
     * <br/>
     * Ver�nderlich, weil in
     * {@link ConstraintCodeGenerator} w�hrend
     * des Laufes als Zwischenmerker gesetzt.
     * <br/>
     * Dieser Constraint-Typ wird aus der
     * {@link And}-Verkn�pfung des
     * <ul>
     *  <li>vorherigen Constraint-Pr�dikat</li>
     *  <li>dem spezialisierenden {@link #casePredicate}</li>
     *  <li>allen anderen {@link #casePredicate} dieser {@link SwitchDefinition}, aber negiert</li>
     * </ul>
     * erzeugt und normalisiert.
     */
    Predicate<CT> paramPredicateSimplifiedForExclude;

    /**
     * Konstruktor.
     *
     * @param casePredicate
     */
    public CasePredicateSpecializedPredicateTuple( 
            final Predicate<CT> casePredicate )
    {
        this.casePredicate = casePredicate;
    }

    /**
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ( ( this.casePredicate == null ) ? 0 : this.casePredicate.hashCode() );
        //// TODO paramPredicate ist ver�nderlich, das kann hier zu einem Fehler f�hren
        //result = prime * result
        //        + ( ( this.paramPredicate == null ) ? 0 : this.paramPredicate.hashCode() );
        return result;
    }

    /**
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals( 
            final Object obj )
    {
        if ( this == obj )
        {
            return true;
        }
        if ( obj == null )
        {
            return false;
        }
        if ( getClass() != obj.getClass() )
        {
            return false;
        }
        CasePredicateSpecializedPredicateTuple<?> other = (CasePredicateSpecializedPredicateTuple<?>) obj;
        if ( this.casePredicate == null )
        {
            if ( other.casePredicate != null )
            {
                return false;
            }
        }
        else if ( !this.casePredicate.equals( other.casePredicate ) )
        {
            return false;
        }
        //// TODO paramPredicate ist ver�nderlich, das kann hier zu einem Fehler f�hren
        //if ( this.paramPredicate == null )
        //{
        //    if ( other.paramPredicate != null )
        //    {
        //        return false;
        //    }
        //}
        //else if ( !this.paramPredicate.equals( other.paramPredicate ) )
        //{
        //    return false;
        //}
        return true;
    }
    
    /**
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
        return
                "CasePredicateSpecializedPredicateTuple[" +
                "casePredicate=" + casePredicate + ", " +
                "paramPredicateSimplifiedForExclude=" + paramPredicateSimplifiedForExclude + 
                "]";
    }
    
//    /**
//     * In einer vern�ftigen Prog-Sprache
//     * w�rde man diese Methode nicht ben�tigen.
//     * 
//     * @param tupleArr 
//     * @return 
//     */
//    public static <CT> Predicate<CT>[] mapToParamPredicateNotSimplifiedForExcludeArr(
//            final CasePredicateSpecializedPredicateTuple<CT>[] tupleArr )
//    {
//        if ( tupleArr == null )
//        {
//            return null;
//        }
//
//        final Predicate<?>[] predicateArr = new Predicate<?>[ tupleArr.length ];
//
//        for ( int i = 0 ; i < tupleArr.length ; i++ )
//        {
//            final CasePredicateSpecializedPredicateTuple<CT> tuple = 
//                    tupleArr[ i ];
//            
//            predicateArr[ i ] = tuple.paramPredicateNotSimplifiedForExclude;
//        }
//
//        return (Predicate<CT>[]) predicateArr;
//    }

    /**
     * In einer vern�ftigen Prog-Sprache
     * w�rde man diese Methode nicht ben�tigen.
     * 
     * @param tupleArr 
     * @return 
     */
    public static <CT> Predicate<CT>[] mapToParamPredicateSimplifiedForExcludeArr(
            final CasePredicateSpecializedPredicateTuple<CT>[] tupleArr )
    {
        if ( tupleArr == null )
        {
            return null;
        }

        final Predicate<CT>[] predicateArr = new Predicate[ tupleArr.length ];

        for ( int i = 0 ; i < tupleArr.length ; i++ )
        {
            final CasePredicateSpecializedPredicateTuple<CT> tuple = 
                    tupleArr[ i ];
            
            final Predicate<CT> tmpParamPredicateSimplifiedForExclude = 
                    tuple.paramPredicateSimplifiedForExclude;
            
            if ( tmpParamPredicateSimplifiedForExclude == null )
                throw new NullPointerException();
            
            predicateArr[ i ] = tmpParamPredicateSimplifiedForExclude;
        }

        return predicateArr;
    }

}
