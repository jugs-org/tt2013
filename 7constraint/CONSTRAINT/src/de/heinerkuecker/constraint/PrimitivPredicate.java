package de.heinerkuecker.constraint;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import de.heinerkuecker.constraint.util.coll.ReadOnlySet;
import de.heinerkuecker.constraint.util.coll.ReadOnlySetAdaptor;

/**
 * Primitiv-Prädikate
 * (kein And, Or, Not oder Xor).
 * 
 * @param <CT> Type-Parameter Kontext-Objekt-Type
 * @author Heiner K&uuml;cker
 */
@SuppressWarnings( "serial" ) // serial version id wird nicht deklariert, damit der Cache Änderungen an der Klasse erkennt
public abstract class PrimitivPredicate<CT>
extends Predicate<CT>
{
    /**
     * Set mit Prädikaten, welche dieses Prädikat includiert,
     * das heisst, die hier angegebenen Prädikate sind auch
     * erfüllt, wenn dieses(this) Prädikat erfüllt ist.
     * 
     * Beispiel:
     *     Prädikat: KundeRegionNord
     *     includiert: RegionNord
     */
    public final HashSet<? extends PrimitivPredicate<CT>> includePredicateSet;

    /**
     * Definitionenen für constraint-sichere Member
     * TODO immutable
     */
    public final HashSet<? extends SafeMember> safeMemberSet;
    
	/**
	 * Konstruktor.
	 * 
	 * @param lineNr
	 * @param implicitsPredicateSet
	 * @param safeMemberSet
	 */
	protected PrimitivPredicate(
			final int lineNr ,
			final HashSet<? extends PrimitivPredicate<CT>> implicitsPredicateSet ,
		    final HashSet<? extends SafeMember> safeMemberSet )
	{
		super( lineNr );

		this.includePredicateSet = implicitsPredicateSet;

        this.safeMemberSet = safeMemberSet;
	}

    /**
     * Konstruktor.
     * 
     * @param lineNr
     * @param includePredicateSet
     * @param safeMemberSet
     */
    protected PrimitivPredicate(
            final int lineNr ,
            final HashSet<? extends PrimitivPredicate<CT>> includePredicateSet ,
            final SafeMember... safeMemberArr )
    {
        super( lineNr );

        this.includePredicateSet = includePredicateSet;

        this.safeMemberSet =
                new HashSet<SafeMember>(
                        Arrays.asList( 
                                safeMemberArr ) );
    }

    /**
     * Konstruktor.
     * 
     * @param lineNr
     * @param includePredicateArr
     */
    protected PrimitivPredicate(
            final int lineNr ,
            final PrimitivPredicate<CT>... includePredicateArr )
    {
        super( lineNr );

        this.includePredicateSet = 
                new HashSet<PrimitivPredicate<CT>>(
                        Arrays.asList(
                                includePredicateArr ) );

        this.safeMemberSet = null;
    }

    /**
	 * @see Predicate#isPrimitive()
	 */
	@Override
	public final boolean isPrimitive()
	{
		return true;
	}

    /**
     * @see java.lang.Object#toString()
     */
    @Override
    public /*für parametrisierbares Prädikat entfernt: final*/ String toString()
    {
        return 
            this.getClass().getSimpleName();
    }

    /**
     * @see Predicate#toFileName
     */
    @Override
    public /*für parametrisierbares Prädikat entfernt: final*/ String toFileName( 
            final String separatorStr )
    {
        return 
            this.toString();
    }

    /**
     * Cache for {@link #hashCode()}.
     */
    private int hashCode;
    
    /**
     * @see java.lang.Object#hashCode()
     */
    @Override
    public /*für parametrisierbares Prädikat entfernt: final*/ int hashCode()
    {
        if ( this.hashCode == 0 )
        {
            this.hashCode = this.getClass().hashCode();
        }
        return this.hashCode;
    }

    /**
     * @see Object#equals
     */
    @Override
    public /*für parametrisierbares Prädikat entfernt: final*/ boolean equals( 
            final Object obj )
    {
        if (this == obj)
            return true;
        if (obj == null)
            return false;

        return this.getClass().equals( obj.getClass() );
    }

    /**
     * @see Predicate#replacePrimitivePredicatesWithFalse
     */
    @Override
    public final Predicate<CT> replacePrimitivePredicatesWithFalse(
            final HashSet<PrimitivPredicate<CT>> predicateSetToRemove , 
            //final Exclude<CT> exclude ,
            final ConstraintCodeGeneratorCache<CT> cache )
    {
        //throw new UnsupportedOperationException()
        return new FalsePredicate<CT>();
    }

    ///**
    // * @see Predicate#replacePrimitivePredicatesWithTrue
    // */
    //@Override
    //public final Predicate<CT> replacePrimitivePredicatesWithTrue(
    //        final HashSet<PrimitivPredicate<CT>> predicateSetToRemove , 
    //        final Exclude<CT> exclude )
    //{
    //    throw new UnsupportedOperationException();
    //}

    /**
     * @see Predicate#replacePredicatesWithTrue
     */
    @Override
    public final Predicate<CT> replacePredicatesWithTrue(
            final HashSet<Predicate<CT>> predicateSetToRemove , 
            //final Exclude<CT> exclude ,
            final ConstraintCodeGeneratorCache<CT> cache )
    {
        //throw new UnsupportedOperationException();
        if ( predicateSetToRemove.contains( this ) )
        {
            return new TruePredicate<CT>();
        }
        return this;
    }

    /**
     * @see Predicate#getPredicateListFromAndNotNegated
     */
    @Override
    public final List<PrimitivPredicate<CT>> getPredicateListFromAndNotNegated(
            final Class<? extends PrimitivPredicate<CT>> predicateClass )
    {
        if ( predicateClass.isAssignableFrom( this.getClass() ) )
        {
            return
                    Arrays.asList( 
                            this );
        }

        return null;
    }

    /**
     * @param predicateToCheck Prädikat
     * @return ob dieses Prädikat das übergebene Prädikat direkt oder indirekt includiert
     */
    public final boolean includePredicateSetContainsRecursiv(
            final PrimitivPredicate<CT> predicateToCheck )
    {
        if ( this.includePredicateSet != null )
        {
            if ( this.includePredicateSet.contains( predicateToCheck ) )
            {
                return true;
            }
            
            for ( final PrimitivPredicate<CT> includedPredicate : this.includePredicateSet )
            {
                if ( includedPredicate.includePredicateSetContainsRecursiv( predicateToCheck ) )
                {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Cache für {@link #getIncludePredicateSetRecursiv()}
     */
    private ReadOnlySet<PrimitivPredicate<CT>> includePredicateSetRecursivSetCache;

    /**
     * @return alle includierten Prädikate
     */
    public final ReadOnlySet<PrimitivPredicate<CT>> getIncludePredicateSetRecursiv()
    {
        if ( this.includePredicateSetRecursivSetCache == null )
        {
            final HashSet<PrimitivPredicate<CT>> result = new HashSet<PrimitivPredicate<CT>>();

            if ( this.includePredicateSet != null )
            {
                result.addAll(
                        this.includePredicateSet );

                for ( final PrimitivPredicate<CT> includedPredicate : this.includePredicateSet )
                {
                    result.addAll(
                            includedPredicate.getIncludePredicateSetRecursiv().toJavaSet() );
                }
            }

            this.includePredicateSetRecursivSetCache =
                    new ReadOnlySetAdaptor<PrimitivPredicate<CT>>(
                            result );
        }
        return this.includePredicateSetRecursivSetCache;
    }

    /**
     * Cache für {@link #getIncludePredicateSetRecursivInclusiveSelf()}
     */
    private ReadOnlySet<PrimitivPredicate<CT>> includePredicateSetRecursivSetInclusiveSelfCache;

    /**
     * @return alle includierten Prädikate und dieses selbst
     */
    @Override
    public final ReadOnlySet<PrimitivPredicate<CT>> getIncludePredicateSetRecursivInclusiveSelf()
    {
        if ( this.includePredicateSetRecursivSetInclusiveSelfCache == null )
        {
            final Set<PrimitivPredicate<CT>> includePredicateSetRecursivSet =
                    getIncludePredicateSetRecursiv().toJavaSet();

            includePredicateSetRecursivSet.add( this );

            this.includePredicateSetRecursivSetInclusiveSelfCache =
                    new ReadOnlySetAdaptor<PrimitivPredicate<CT>>(
                            includePredicateSetRecursivSet );
        }

        return this.includePredicateSetRecursivSetInclusiveSelfCache;
    }

    /**
     * @return has includes
     */
    public final boolean hasIncludes()
    {
        return
                this.includePredicateSet != null &&
                false == this.includePredicateSet.isEmpty();
    }
    
    /**
     * Cache für {@link #getFinalIncludedPredicateSet}
     */
    private ReadOnlySet<PrimitivPredicate<CT>> finalIncludedPredicateSetCache;

    /**
     * @return alle final includierten Prädikate (hasIncludes == false) zurückgeben
     */
    public ReadOnlySet<PrimitivPredicate<CT>> getFinalIncludedPredicateSet()
    {
        if ( this.finalIncludedPredicateSetCache == null )
        {
            final HashSet<PrimitivPredicate<CT>> result = new HashSet<PrimitivPredicate<CT>>();
            if ( this.hasIncludes() )
            {
                for ( final PrimitivPredicate<CT> includedPredicate : this.includePredicateSet )
                {
                    result.addAll(
                            includedPredicate.getFinalIncludedPredicateSet().toJavaSet() );
                }
            }
            else
            {
                result.add( this );
            }
            this.finalIncludedPredicateSetCache =
                    new ReadOnlySetAdaptor<PrimitivPredicate<CT>>(
                            result );
        }
        return this.finalIncludedPredicateSetCache;
    }

    /**
     * @see Predicate#getPrimitivPredicateSet
     */
    @Override
    public final ReadOnlySet<PrimitivPredicate<CT>> getPrimitivPredicateSet()
    {
        return new ReadOnlySetAdaptor<PrimitivPredicate<CT>>( this );
    }

    /**
     * @see Predicate#hasNotSubPred
     */
    @Override
    public final boolean hasNotSubPred()
    {
        return false;
    }

    /**
     * @see Predicate#getMandantoryPrimitivPredicateSet
     */
    @Override
    public final ReadOnlySet<PrimitivPredicate<CT>> getMandantoryPrimitivPredicateSet()
    {
        return this.getIncludePredicateSetRecursivInclusiveSelf();
    }

    /**
     * @see Predicate#getNegatedMandantoryPrimitivPredicateSet
     */
    @Override
    public final ReadOnlySet<PrimitivPredicate<CT>> getNegatedMandantoryPrimitivPredicateSet()
    {
        // leeres Set
        return new ReadOnlySetAdaptor<PrimitivPredicate<CT>>();
    }

    /**
     * @see Predicate#getNonNegatedPrimitivPredicateSet
     */
    @Override
    public final ReadOnlySet<PrimitivPredicate<CT>> getNonNegatedPrimitivPredicateSet()
    {
        return this.getIncludePredicateSetRecursivInclusiveSelf();
    }
    
    public final boolean isMemberOfClosedWorldExcludeGroup(
            final Exclude<CT> exclude )
    {
        final ReadOnlySet<ExcludeGroup<CT>> exclGrpSet = exclude.getExcludeGroupSet( this );
        
        if ( exclGrpSet != null )
        {
            for ( final ExcludeGroup<CT> excludeGroup : exclGrpSet )
            {
                if ( excludeGroup.isClosedWorldAssumption )
                {
                    return true;
                }
            }
        }
        return false;
    }

    public final Predicate<CT> getOrForClosedWorldExcludeGroup(
            final ConstraintCodeGeneratorCache<CT> cache )
    {
        final ReadOnlySet<ExcludeGroup<CT>> exclGrpSet = cache.exclude.getExcludeGroupSet( this );
        
        if ( exclGrpSet != null )
        {
            for ( final ExcludeGroup<CT> excludeGroup : exclGrpSet )
            {
                if ( excludeGroup.isClosedWorldAssumption )
                {
                    return excludeGroup.getOr(
                            //excludePredicateColl
                            Arrays.asList( this ) ,
                            cache );
                }
            }
        }
        throw new IllegalStateException();
    }

    /**
     * @see Predicate#hasOr
     */
    @Override
    public final boolean hasOr()
    {
        return false;
    }

    /**
     * @see Predicate#hasNot
     */
    @Override
    public final boolean hasNot()
    {
        return false;
    }

}
