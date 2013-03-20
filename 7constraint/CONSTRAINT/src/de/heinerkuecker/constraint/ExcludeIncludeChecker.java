package de.heinerkuecker.constraint;

import java.util.HashSet;
import java.util.Map;

import de.heinerkuecker.constraint.util.coll.ReadOnlySet;
import de.heinerkuecker.constraint.util.coll.RetainSet;

/**
 * Diese Klasse dient zum Prüfen,
 * ob in einem {@link BinCounter}
 * eine Belegung einer oder mehrerer
 * zusammenwirkender Constraint-Expressions
 * {@link Predicate} eine
 * Belegung (Model) vorhanden ist,
 * welche die zusätzlichen Bedingungen
 * durch Excludes {@link Exclude} und
 * Includes
 * {@link PrimitivPredicate#includePredicateSet}
 * verletzt.
 * 
 * @author Heiner K&uuml;cker
 */
public class ExcludeIncludeChecker<CT>
{
    final Exclude<CT> exclude;
    
    //final FirstInsertOrderHashset<PrimitivPredicate<CT>> primitivpredicateSet;
    /*default*/ final Map<PrimitivPredicate<CT>, SolveSimple<CT>> primitivPredToSolvePredMap;
    
    final MutableBoundedExclude<CT> mutableBoundedExclude;

    //final IncludeValidChecker<CT> includeValidChecker;
    
    final ContainsAllCache<CT> containsAllCache;
    
    //final ExcludeGroupWithCounterListener<CT>[] excludeGroupWithCounterListenerArr;
    
    /**
     * Konstruktor.
     * 
     * @param primitivpredicateSet Set mit den Prädikaten zur Ermittlung des Index eines bestimmten Prädikates im übergebenen Binär-Zähler
     * @param counter Zähler mit der aktuellen Belegung (Model) des boolschen Ausdrucks
     */
    public ExcludeIncludeChecker(
            final Exclude<CT> exclude ,
            //final FirstInsertOrderHashset<PrimitivPredicate<CT>> primitivpredicateSet , 
            //final BinCounter counter ,
            final Map<PrimitivPredicate<CT>, SolveSimple<CT>> primitivPredToSolvePredMap , 
            final ContainsAllCache<CT> containsAllCache
            //final ExcludeGroupWithCounterListener<CT>[] excludeGroupWithCounterListenerArr
            )
    {
        this.exclude = exclude;

        //this.primitivpredicateSet = primitivpredicateSet;
        this.primitivPredToSolvePredMap = primitivPredToSolvePredMap;

        this.mutableBoundedExclude =
                new MutableBoundedExclude<CT>( 
                        exclude );
        
//        this.includeValidChecker =
//                new IncludeValidChecker<CT>( 
//                        //primitivpredicateSet , 
//                        //counter
//                        primitivPredToSolvePredMap );
        
        this.containsAllCache = containsAllCache;
        
        //if ( counter.size() != primitivpredicateSet.size() )
        //{
        //    throw new IllegalArgumentException();
        //}
        
        //this.excludeGroupWithCounterListenerArr = excludeGroupWithCounterListenerArr;
    }

    /**
     * Methode zum Prüfen, ob die aktuelle Belegung (Model) der boolschen Expression gültig ist.
     * @return ob die aktuelle Belegung (Model) der boolschen Expression gültig ist
     */
    public boolean isValid()
    {
        final HashSet<String> alreadyClosedWorldCheckedExclGrpNameSet =
                new HashSet<String>();

        for ( final SolveSimple<CT> solveSimple : this.primitivPredToSolvePredMap.values() )
        {
            if ( false == solveSimple.isValidForIncludes() )
            {
                return false;
            }
        }
        
        //for ( final PrimitivPredicate<CT> predicate : this.primitivpredicateSet )
        for ( final Map.Entry<PrimitivPredicate<CT>, SolveSimple<CT>> entry : this.primitivPredToSolvePredMap.entrySet() )
        {
            //if ( this.includeValidChecker.includeCheckPredicateToBoolvalueMap.get( predicate ) )
            if ( entry.getValue().solve() )
                // Prädikat ist erfüllt
            {
                //if ( false == this.mutableBoundedExclude.isValid( predicate ) )
                if ( false == this.mutableBoundedExclude.isValid(
                        entry.getKey() ) )
                {
                    // die aktuelle Belegung (Model) der boolschen Expression ist ungültig
                    return false;
                }

//                //if ( false == this.includeValidChecker.isValid( predicate , this.mutableBoundedExclude ) )
//                if ( false == this.includeValidChecker.isValid(
//                        //predicate
//                        entry.getKey() ,
//                        this.mutableBoundedExclude ) )
//                {
//                    // die aktuelle Belegung (Model) der boolschen Expression ist ungültig
//                    return false;
//                }
            }
            else
            {
                //Prüfen, ob von einer ExcludeGroup alle Prädikate in this.primitivpredicateSet enthalten sind und keines davon erfüllt ist,
                //bei closed world assumption muss mindestens ein Prädikat erfüllt sein
                final ReadOnlySet<ExcludeGroup<CT>> exclGrpSet =
                        this.exclude.getExcludeGroupSet(
                                //predicate
                                entry.getKey() );
                
                if ( exclGrpSet != null )
                {
                    for ( final ExcludeGroup<CT> excludeGroup : exclGrpSet )
                    {
                        if ( excludeGroup.isClosedWorldAssumption &&
                                false == alreadyClosedWorldCheckedExclGrpNameSet.contains( excludeGroup.name ) )
                        {
                            //final HashSet<PrimitivPredicate<CT>> exclGrpPredicateSetClone = (HashSet<PrimitivPredicate<CT>>) excludeGroup.predicateSet.clone();
                            final ReadOnlySet<PrimitivPredicate<CT>> exclGrpPredicateSet = excludeGroup.predicateSet;

                            //if ( this.primitivpredicateSet.containsAll(
                            //        //exclGrpPredicateSetClone
                            //        exclGrpPredicateSet ) )
                            // containsAll cachen, beide Sets sind immer wieder die selben Objekte
                            if ( this.containsAllCache.containsAll(
                                    //this.primitivpredicateSet
                                    this.primitivPredToSolvePredMap.keySet() ,
                                    exclGrpPredicateSet ) )
                            {
                                //exclGrpPredicateSetClone.retainAll( this.primitivpredicateSet );
                                final RetainSet<PrimitivPredicate<CT>> retainSet =
                                        new RetainSet<PrimitivPredicate<CT>>(
                                                exclGrpPredicateSet ,
                                                //this.primitivpredicateSet
                                                this.primitivPredToSolvePredMap.keySet() );

                                boolean allExclGrpPredicateUnsatisfied = true;

                                for ( final PrimitivPredicate<CT> exclGrpPredicate : /*exclGrpPredicateSetClone*/retainSet )
                                {
                                    //if ( this.includeValidChecker.includeCheckPredicateToBoolvalueMap.get( exclGrpPredicate ) )
                                    if ( this.primitivPredToSolvePredMap.get( exclGrpPredicate ).solve() )
                                        // Prädikat ist erfüllt
                                    {
                                        allExclGrpPredicateUnsatisfied = false;
                                        break;
                                    }
                                }

                                if ( allExclGrpPredicateUnsatisfied )
                                {
                                    return false;
                                }
                            }

                            alreadyClosedWorldCheckedExclGrpNameSet.add(
                                    excludeGroup.name );
                        }
                    }
                }
            }
        }
        
//        for ( final ExcludeGroupWithCounterListener<CT> excludeGroupWithCounterListener : this.excludeGroupWithCounterListenerArr )
//        {
//            if ( excludeGroupWithCounterListener.satisfiedPredicateCount < 0 )
//            {
//                throw new IllegalArgumentException();
//            }
//            if ( excludeGroupWithCounterListener.excludeGroup.isClosedWorldAssumption &&
//                    excludeGroupWithCounterListener.satisfiedPredicateCount == 0 )
//            {
//                return false;
//            }
//            if ( excludeGroupWithCounterListener.satisfiedPredicateCount > 1 )
//            {
//                return false;
//            }
//        }

        return true;
    }

}
