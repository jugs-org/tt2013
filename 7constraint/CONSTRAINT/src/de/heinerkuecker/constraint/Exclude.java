package de.heinerkuecker.constraint;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import de.heinerkuecker.constraint.util.coll.ArraylistHashmap;
import de.heinerkuecker.constraint.util.coll.HashsetHashmap;
import de.heinerkuecker.constraint.util.coll.ReadOnlySet;
import de.heinerkuecker.constraint.util.coll.ReadOnlySetAdaptor;

/**
 * Alle sich ausschliessenden Prädikate.
 * 
 * @param <CT> Type-Parameter Kontext-Objekt-Typ
 * @author Heiner K&uuml;cker
 */
public final class Exclude<CT>
{
    /**
     * Set der {@link ExcludeGroup}s
     */
    public final ReadOnlySet<ExcludeGroup<CT>> excludeGroupSet;
    
    /**
     * Map von den jeweils in den {@link ExcludeGroup} enthaltenen
     * Prädikate zu einer Liste der jeweiligen {@link ExcludeGroup}s
     * für schnellen Zugriff anhand {@link PrimitivPredicate}.
     */
    private final HashsetHashmap<PrimitivPredicate<CT>, ExcludeGroup<CT>> setMap = 
            new HashsetHashmap<PrimitivPredicate<CT>, ExcludeGroup<CT>>();

    /**
     * Konstruktor.
     * 
     * @param excludeGroupArr varargs-Parameter Exclude-Gruppen
     */
    public Exclude(
            final ExcludeGroup<CT>... excludeGroupArr )
    {
    	// Set zum Vermeiden der doppelten Definition einer Exclude-Gruppe
    	final HashSet<String> excludeGroupNameSet =
    			new HashSet<String>();
    	
    	// spezielle ExcludeGruop für die vordefinierten Prädikate True und False
    	final ExcludeGroup<CT> trueFalseExcludegroup =
    	        new ExcludeGroup<CT>(
    	                "TrueFalse" ,
    	                //isClosedWorldAssumption
    	                true ,
    	                new TruePredicate<CT>() ,
    	                new FalsePredicate<CT>() );
    	
        addExcludegroup( 
                excludeGroupNameSet , 
                trueFalseExcludegroup );

        for ( final ExcludeGroup<CT> excludeGroup : excludeGroupArr )
        {
        	addExcludegroup( 
        	        excludeGroupNameSet , 
        	        excludeGroup );
        }
        
        this.excludeGroupSet =
                new ReadOnlySetAdaptor<ExcludeGroup<CT>>(
                        excludeGroupArr );
    }

    private void addExcludegroup( 
            final HashSet<String> excludeGroupNameSet ,
            final ExcludeGroup<CT> excludeGroup )
    {
        if ( excludeGroupNameSet.contains( excludeGroup.name ) )
        {
        	throw new IllegalArgumentException(
        			"exclude group " + excludeGroup.name + " already defined" );
        }
        for ( final PrimitivPredicate<CT> excludePredicate : excludeGroup.predicateSet )
        {
            this.setMap.put( 
                    excludePredicate , 
                    excludeGroup );
        }
    }
    
    /**
     * Prüfen, ob für das übergebene
     * Prädikat eine Exclude-Gruppe
     * exisitert.
     * 
     * @param predicateToCheck
     * @return
     * @see ArrayListHashMap#containsKey
     */
    public boolean contains(
            final PrimitivPredicate<CT> predicateToCheck )
    {
        if ( this.setMap.containsKey( predicateToCheck ) )
        {
            return true;
        }
        
        // nun auch die eventuell enthaltenen includierten Prädikate prüfen
        if ( predicateToCheck.includePredicateSet != null )
        {
            for ( final PrimitivPredicate<CT> includePredicate : predicateToCheck.includePredicateSet )
            {
                if ( this.contains( includePredicate ) )
                    // rekursiv auch indirekt includierte Prädikate prüfen
                {
                    return true;
                }
            }
        }
        
        return false;
    }
    
    /**
     * Prüfen, ob das übergebene erste und
     * das übergebene zweite Prädikat
     * zu einer Exclude-Gruppe gehören.
     * 
     * @param predicateToCheck
     * @param otherPredicateToCheck 
     * @return
     * @see ArraylistHashmap#containsKey
     */
    public boolean contains(
            final PrimitivPredicate<CT> predicateToCheck ,
            final PrimitivPredicate<CT> otherPredicateToCheck )
    {
        final HashSet<ExcludeGroup<CT>> excludeGroupSet = 
                this.setMap.get( predicateToCheck );
        
        if ( excludeGroupSet == null )
        {
            return false;
        }
        
        // TODO includes beachten
        
        for ( final ExcludeGroup<CT> excludeGroup : excludeGroupSet )
        {
            if ( excludeGroup.predicateSet.contains( otherPredicateToCheck ) )
            {
                return true;
            }
        }

        return false;
    }
    
    public ReadOnlySet<ExcludeGroup<CT>> getExcludeGroupSet(
            final PrimitivPredicate<CT> predicateToCheck )
    {
        final HashSet<ExcludeGroup<CT>> result =
                this.setMap.get(
                        predicateToCheck );
        
        if ( result == null )
        {
            return null;
        }
        return new ReadOnlySetAdaptor<ExcludeGroup<CT>>(
                result );
    }

    /**
     * 
     * @param predicateToCheckIterable
     * @return
     */
    public ReadOnlySet<ExcludeGroup<CT>> getExcludeGroupSet(
            final Iterable<PrimitivPredicate<CT>> predicateToCheckIterable )
    {
        // TODO Cache
        final HashSet<ExcludeGroup<CT>> result = new HashSet<ExcludeGroup<CT>>();
        for ( final PrimitivPredicate<CT> primitivPredicateToCheck : predicateToCheckIterable )
        {
            final HashSet<ExcludeGroup<CT>> excludeGroupSetForOnePredicate =
                    this.setMap.get(
                            primitivPredicateToCheck );

            if ( excludeGroupSetForOnePredicate != null )
            {
                result.addAll(
                        excludeGroupSetForOnePredicate );
            }
        }
        return new ReadOnlySetAdaptor<ExcludeGroup<CT>>(
                result );
    }

    //public ReadOnlyList<ExcludeGroup<CT>> getExcludeGroupList()
    //{
    //    // TODO Cache
    //    final List<ExcludeGroup<CT>> result = new ArrayList<ExcludeGroup<CT>>();
    //    for ( final List<ExcludeGroup<CT>> exclGrpList : this.listMap.values() )
    //    {
    //        // TODO über Map einmaligkeit der ExcludeGroup's absichern
    //        result.addAll(
    //                exclGrpList );
    //    }
    //    return new ReadOnlyListAdaptor<ExcludeGroup<CT>>(
    //            result );
    //}

    //public List<Predicate<CT>> getXorListFor(
    //        //final Predicate<CT> excludablePredicate ,
    //        final Collection<PrimitivPredicate<CT>> predicateColl )
    //{
    //    final ArrayList<Predicate<CT>> xorList = new ArrayList<Predicate<CT>>();
    //    
    //    for ( final PrimitivPredicate<CT> predicate : predicateColl )
    //    {
    //        final List<ExcludeGroup<CT>> excludeList = this.listMap.get( predicate );
    //        
    //        if ( excludeList != null )
    //        {
    //            for ( final ExcludeGroup<CT> excludeGroup : excludeList )
    //            {
    //                xorList.add(
    //                        excludeGroup.getXor(
    //                                //predicateColl
    //                                ) );
    //            }
    //        }
    //    }
    //    
    //    
    //    return xorList;
    //}

    public List<Predicate<CT>> getOrListFor(
            //final Predicate<CT> excludablePredicate ,
            final Collection<PrimitivPredicate<CT>> predicateColl ,
            final ConstraintCodeGeneratorCache<CT> cache )
    {
        final ArrayList<Predicate<CT>> orList = new ArrayList<Predicate<CT>>();

        for ( final PrimitivPredicate<CT> predicate : predicateColl )
        {
            for ( final PrimitivPredicate<CT> includedPredicate : predicate.getIncludePredicateSetRecursivInclusiveSelf() )
            {
                final HashSet<ExcludeGroup<CT>> excludeSet =
                        this.setMap.get( includedPredicate );

                if ( excludeSet != null )
                {
                    for ( final ExcludeGroup<CT> excludeGroup : excludeSet )
                    {
                        orList.add(
                                excludeGroup.getOr(
                                        predicateColl ,
                                        cache ) );
                    }
                }
            }
        }

        return orList;
    }

//    public List<Predicate<CT>> getOneOrListFor(
//            //final Predicate<CT> excludablePredicate ,
//            final Collection<PrimitivPredicate<CT>> predicateColl ,
//            //final Exclude<CT> exclude 
//            final ConstraintCodeGeneratorCache<CT> cache )
//    {
//        final ArrayList<Predicate<CT>> xorList = new ArrayList<Predicate<CT>>();
//        
//        for ( final PrimitivPredicate<CT> predicate : predicateColl )
//        {
//            final HashSet<ExcludeGroup<CT>> excludeSet =
//                    this.setMap.get( predicate );
//            
//            if ( excludeSet != null )
//            {
//                for ( final ExcludeGroup<CT> excludeGroup : excludeSet )
//                {
//                    xorList.add(
//                            excludeGroup.getOneOr(
//                                    predicateColl ,
//                                    cache ) );
//                }
//            }
//        }
//        
//        return xorList;
//    }

    /**
     * Diese Methode gibt für das übergebenen Prädikat,
     * welches in einer {@link ExcludeGroup} enthalten
     * sein sollte, alle anderen excludierten Prädikate
     * zurück.
     * 
     * @param exclude
     * @return alle anderen excludierten Prädikate
     */
    public Set<PrimitivPredicate<CT>> getOtherPredicateSet(
            final PrimitivPredicate<CT> excludablePredicate )
    {
        if ( excludablePredicate instanceof ParametricalPredicate )
        {
            throw new IllegalArgumentException();
        }

        final HashSet<PrimitivPredicate<CT>> otherPredicateSet = new HashSet<PrimitivPredicate<CT>>();

        final HashSet<ExcludeGroup<CT>> exclGrpSet =
                this.setMap.get( excludablePredicate );

        if ( exclGrpSet != null )
        {
            for ( final ExcludeGroup<CT> excludeGroup : exclGrpSet )
            {
                final Set<PrimitivPredicate<CT>> exclGrpPredicateSetClone =
                        excludeGroup.predicateSet.toJavaSet();
                
                exclGrpPredicateSetClone.remove(
                        excludablePredicate );
                
                otherPredicateSet.addAll(
                        exclGrpPredicateSetClone );
            }
        }
        
        // Includes beachten
        if ( excludablePredicate.includePredicateSet != null )
        {
            for ( final PrimitivPredicate<CT> includedPredicate : excludablePredicate.includePredicateSet )
            {
                otherPredicateSet.addAll(
                        this.getOtherPredicateSet(
                                includedPredicate ) );
            }
        }

        return otherPredicateSet;
    }

    /**
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
        return "Exclude[listMap=" + this.setMap + "]";
    }

    /**
     * Prädikate aus dem Exclude in die übergebene IsIncludedByMap übertragen
     * @param isIncludedByMap included by map
     */
    /*default*/ void addAllPredicatesToIsIncludedByMap(
            final IsIncludedByMap<CT> isIncludedByMap )
    {
        isIncludedByMap.addAll(
                this.setMap.keySet());
    }

    /**
     * Prädikate aus dem Exclude in die übergebene allPrimitivPredicateSet übertragen
     * @param cache cahce with all primitiv predicate set
     */
    /*default*/ void addAllPredicatesToPrimitivPredicateSet(
            final ConstraintCodeGeneratorCache<CT> cache )
    {
        cache.addPrimitivPredicates(
                this.setMap.keySet());
    }

}
