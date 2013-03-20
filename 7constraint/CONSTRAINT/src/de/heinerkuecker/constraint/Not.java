package de.heinerkuecker.constraint;

import java.util.HashSet;
import java.util.List;

import de.heinerkuecker.constraint.util.coll.ReadOnlySet;
import de.heinerkuecker.constraint.util.coll.ReadOnlySetAdaptor;


/**
 * Negation eines Prädikats
 * {@link Predicate}.
 * 
 * @param <CT> Type-Parameter Kontext-Objekt-Type
 * @author Heiner K&uuml;cker
 */
public final class Not<CT>
extends Predicate<CT>
{
    /**
     * zu negierendes Prädikat
     */
    /*default*/ final Predicate<CT> predicateToNegate;
    
    /**
     * Konstruktor.
     *
     * @param lineNr Zeilen-Nummer
     * @param predicateToNegate zu negierendes Prädikat
     */
    private Not( 
            final int lineNr ,
            final Predicate<CT> predicateToNegate )
    {
        super( 
                lineNr );

        if ( predicateToNegate == null )
        {
            throw new IllegalArgumentException(
                    "predicateToNegate is null" );
        }

        this.predicateToNegate = predicateToNegate;
    }

    /**
     * 
     * @param lineNr
     * @param predicateToNegate
     * @return
     */
    public static <CT> Predicate<CT> instanceOf(
            final int lineNr ,
            final Predicate<CT> predicateToNegate )
    {
        if ( predicateToNegate instanceof Not )
            // doppelte Negation
        {
            return ( (Not<CT>) predicateToNegate ).predicateToNegate;
        }
        
        return new Not<CT>(
                lineNr ,
                predicateToNegate );
    }

    /**
     * @see java.lang.Object#toString()
     */
    private String toStrCache;

    /**
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
        //return "(not(" + this.predicateToNegate + "))";
        if ( this.toStrCache == null )
        {
            this.toStrCache =
                "not(" + this.predicateToNegate + ")";
        }
        return this.toStrCache;
    }

    /**
     * @see Predicate#test(java.lang.Object)
     */
    @Override
    public boolean test( 
            final CT contextObj )
    {
        return ! this.predicateToNegate.test( contextObj );
    }

    /**
     * @see Predicate#toFileName(java.lang.String)
     */
    @Override
    public String toFileName( 
            final String separatorStr )
    {
        return
            "NOT" +
            separatorStr +
            this.predicateToNegate.toFileName( 
                separatorStr );
    }

    /**
     * Zurückgeben negierten Unter-Prädikats.
     * 
     * @return negiertes Unter-Prädikate
     */
    public Predicate<CT> get()
    {
        return this.predicateToNegate;
    }

    /**
     * hash code cache.
     */
    private int hashCode;
    
    /**
     * @see java.lang.Object#hashCode()
     */
    @Override
    public final int hashCode()
    {
        if ( this.hashCode == 0 )
        {
            this.hashCode = this.predicateToNegate.hashCode() + 31;
        }
        return this.hashCode;
    }

    /**
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals( 
            final Object obj )
    {
        if ( this == obj )
            return true;
        if ( obj == null )
            return false;
        if ( false == getClass().equals( obj.getClass() ) )
            return false;
        final Not<?> other = (Not<?>) obj;
        return this.predicateToNegate.equals( other.predicateToNegate );
    }

    /**
     * @see Predicate#isPrimitive()
     */
    @Override
    public boolean isPrimitive()
    {
        return false;
    }

    /**
     * @see Predicate#replacePrimitivePredicatesWithFalse
     */
    @Override
    public Predicate<CT> replacePrimitivePredicatesWithFalse(
            final HashSet<PrimitivPredicate<CT>> predicateSetToRemove , 
            //final Exclude<CT> exclude ,
            final ConstraintCodeGeneratorCache<CT> cache )
    {
        if ( this.predicateToNegate.isPrimitive() )
        {
            if ( false == predicateSetToRemove.contains( this.predicateToNegate ) )
            {
                return this;
            }

            return new TruePredicate<CT>();
        }
        else
        {
            final Predicate<CT> cleanedNonPrimitiveSubPredicate =
                    this.predicateToNegate.replacePrimitivePredicatesWithFalse( 
                            predicateSetToRemove ,
                            //exclude ,
                            cache );

            if ( cleanedNonPrimitiveSubPredicate != null )
                // bei völlig leerem gereinigtem Sub-Prädikat kann null auftauchen
            {
                return new Not<CT>(
                        this.lineNr ,
                        cleanedNonPrimitiveSubPredicate );
            }

            return new TruePredicate<CT>();
        }
    }

    ///**
    // * @see Predicate#replacePrimitivePredicatesWithTrue
    // */
    //@Override
    //public Predicate<CT> replacePrimitivePredicatesWithTrue(
    //        final HashSet<PrimitivPredicate<CT>> predicateSetToRemove , 
    //        final Exclude<CT> exclude )
    //{
    //    if ( this.predicateToNegate.isPrimitive() )
    //    {
    //        if ( false == predicateSetToRemove.contains( this.predicateToNegate ) )
    //        {
    //            return this;
    //        }
    //
    //        return new FalsePredicate<CT>();
    //    }
    //    else
    //    {
    //        final Predicate<CT> cleanedNonPrimitiveSubPredicate =
    //                this.predicateToNegate.replacePrimitivePredicatesWithTrue( 
    //                        predicateSetToRemove ,
    //                        exclude );
    //
    //        if ( cleanedNonPrimitiveSubPredicate != null )
    //            // bei völlig leerem gereinigtem Sub-Prädikat kann null auftauchen
    //        {
    //            return new Not<CT>(
    //                    this.lineNr ,
    //                    cleanedNonPrimitiveSubPredicate );
    //        }
    //
    //        return new FalsePredicate<CT>();
    //    }
    //}

    /**
     * @see Predicate#replacePredicatesWithTrue
     */
    @Override
    public Predicate<CT> replacePredicatesWithTrue(
            final HashSet<Predicate<CT>> predicateSetToRemove , 
            //final Exclude<CT> exclude ,
            final ConstraintCodeGeneratorCache<CT> cache )
    {
        if ( predicateSetToRemove.contains( this.predicateToNegate ) )
        {
            return new FalsePredicate<CT>();
        }
        else
        {
            final Predicate<CT> cleanedNonPrimitiveSubPredicate =
                    this.predicateToNegate.replacePredicatesWithTrue( 
                            predicateSetToRemove ,
                            //exclude ,
                            cache );

            if ( cleanedNonPrimitiveSubPredicate != null )
                // bei völlig leerem gereinigtem Sub-Prädikat kann null auftauchen
            {
                return new Not<CT>(
                        this.lineNr ,
                        cleanedNonPrimitiveSubPredicate );
            }

            return new FalsePredicate<CT>();
        }
    }

    /**
     * @see Predicate#getPredicateListFromAndNotNegated(java.lang.Class)
     */
    @Override
    public List<PrimitivPredicate<CT>> getPredicateListFromAndNotNegated(
            final Class<? extends PrimitivPredicate<CT>> predicateClass )
    {
        return null;
    }

    /**
     * @see Predicate#getIncludePredicateSetRecursiv
     */
    @Override
    public final ReadOnlySet<PrimitivPredicate<CT>> getIncludePredicateSetRecursiv()
    {
        return this.predicateToNegate.getIncludePredicateSetRecursiv();
    }

    /**
     * @see Predicate#getIncludePredicateSetRecursivInclusiveSelf
     */
    @Override
    public final ReadOnlySet<PrimitivPredicate<CT>> getIncludePredicateSetRecursivInclusiveSelf()
    {
        // TODO korekterweise müsste dieses Not-Prädikat deem Ergebnis-Set mit hinzugefügt werden
        return this.predicateToNegate.getIncludePredicateSetRecursivInclusiveSelf();
    }

    /**
     * @see Predicate#getPrimitivPredicateSet
     */
    @Override
    public final ReadOnlySet<PrimitivPredicate<CT>> getPrimitivPredicateSet()
    {
        return this.predicateToNegate.getPrimitivPredicateSet();
    }

    /**
     * @see Predicate#hasNotSubPred
     */
    @Override
    public final boolean hasNotSubPred()
    {
        return true;
    }

    /**
     * @see Predicate#getMandantoryPrimitivPredicateSet
     */
    @Override
    public final ReadOnlySet<PrimitivPredicate<CT>> getMandantoryPrimitivPredicateSet()
    {
        // bei einem Not in einer Constraint-Expression ist diese Methode nicht nutzbar
        //throw new UnsupportedOperationException();
        // negierte Prädikate sind nicht notwendig
        return new ReadOnlySetAdaptor<PrimitivPredicate<CT>>();
    }

    /**
     * @see Predicate#getNegatedMandantoryPrimitivPredicateSet
     */
    @Override
    public final ReadOnlySet<PrimitivPredicate<CT>> getNegatedMandantoryPrimitivPredicateSet()
    {
        return this.predicateToNegate.getMandantoryPrimitivPredicateSet();
    }

    /**
     * @see Predicate#getNonNegatedPrimitivPredicateSet
     */
    @Override
    public final ReadOnlySet<PrimitivPredicate<CT>> getNonNegatedPrimitivPredicateSet()
    {
        return new ReadOnlySetAdaptor<PrimitivPredicate<CT>>();
    }

    /**
     * @see Predicate#hasOr
     */
    @Override
    public boolean hasOr()
    {
        return false;
    }

    /**
     * @see Predicate#hasNot
     */
    @Override
    public boolean hasNot()
    {
        return true;
    }

}
