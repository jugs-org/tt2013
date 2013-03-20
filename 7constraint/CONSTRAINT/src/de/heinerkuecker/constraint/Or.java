package de.heinerkuecker.constraint;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import de.heinerkuecker.constraint.util.coll.FirstInsertOrderHashset;
import de.heinerkuecker.constraint.util.coll.HashsetWithReadonly;
import de.heinerkuecker.constraint.util.coll.ReadOnlySet;
import de.heinerkuecker.constraint.util.coll.ReadOnlySetAdaptor;
import de.heinerkuecker.constraint.util.exc.UnreachableCodeException;
import de.heinerkuecker.constraint.util.lang.SeparatedBuffer;

/**
 * ODER-Verknüpfung von Prädikaten
 * {@link Predicate}.
 * 
 * @param <CT> Type-Parameter Kontext-Objekt-Type
 * @author Heiner K&uuml;cker
 */
public final class Or<CT>
extends Predicate<CT>
implements Iterable<Predicate<CT>>
{
    /**
     * FirstInsertOrderHashset zum Absichern, dass ein identisches
     * Prädikat nur einmal aufgeführt wird und Absichern der
     * Erhaltung der Reihenfolge der Prädikate.
     */
    private final FirstInsertOrderHashset<Predicate<CT>> orPredicateSet;
    
    /**
     * Konstruktor.
     *
     * @param lineNr Zeilen-Nummer 
     * @param orPredicateArr Array mit Prädikaten, welche 
     *                        ODER-verknüpft werden sollen
     */
    private Or( 
            final int lineNr ,
            final Exclude<CT> exclude ,
            final Predicate<CT>... orPredicateArr )
    {
        super( 
                lineNr );

        if ( orPredicateArr.length < 2 )
        {
            throw new IllegalArgumentException();
        }

        //Code zum Bereinigen anhand Include wegen StackOverflowException vorerst auskommentiert: final List<Predicate<CT>> cleanedForImplicitsOrPredicateList = cleanForImplicits( exclude , orPredicateArr );

        this.orPredicateSet = 
            new FirstInsertOrderHashset<Predicate<CT>>();
        
        this.orPredicateSet.addAll(
                Arrays.asList(
                        orPredicateArr )
                //cleanedForImplicitsOrPredicateList
                );
        
        if ( orPredicateArr.length == 1 && orPredicateArr[ 0 ].equals( new FalsePredicate<CT>() ) )
        {
            System.out.println( "debug break" );
        }
    }

    public static <CT> Predicate<CT> instanceOf(
            final int lineNr ,
            //final Exclude<CT> exclude ,
            final ConstraintCodeGeneratorCache<CT> cache ,
            final Predicate<CT>... orSubPredicateArr )
    {
        if ( orSubPredicateArr.length == 0 )
        {
            throw new IllegalArgumentException(
                    "orSubPredicateArr is empty" );
        }

        if ( orSubPredicateArr.length == 1 )
        {
            return orSubPredicateArr[ 0 ];
        }

        final ArrayList<Predicate<CT>> predicateList =
                new ArrayList<Predicate<CT>>(
                        Arrays.asList(
                                orSubPredicateArr ) );

        for ( final Iterator<Predicate<CT>> iterator = predicateList.iterator() ; iterator.hasNext() ; )
        {
            final Predicate<CT> predicate = (Predicate<CT>) iterator.next();

            if ( predicate.equals( new TruePredicate<CT>() ) )
            {
                return new TruePredicate<CT>();
            }

            if ( predicate.equals( new FalsePredicate<CT>() ) )
            {
                iterator.remove();
            }
        }

        if ( predicateList.isEmpty() )
        {
            return new FalsePredicate<CT>();
        }

        if ( predicateList.size() == 1 )
        {
            return predicateList.get( 0 );
        }

        return
                Normalizer.melt(
                        new Or<CT>( 
                                lineNr , 
                                cache.exclude , 
                                predicateList.toArray( new Predicate[ predicateList.size() ] ) ) ,
                        cache );
    }

    ///**
    // * @param andPredicateArr
    // * @return
    // */
    //private List<Predicate<CT>> cleanForImplicits_unused( 
    //        final Exclude<CT> exclude ,
    //        final ConstraintCodeGeneratorCache<CT> cache ,
    //        final Predicate<CT>[] orPredicateArr )
    //{
    //    //if ( this.lineNr == 845 )
    //    //{
    //    //    System.out.println( "debug break" );
    //    //}
    //    /* (A and B) or (A)
    //     * Term1: (A and B)
    //     * Term2: (A)
    //     * Term1 impliziert Term2, also kann Term2 entfallen
    //     */
    //    
    //    final ArrayList<Predicate<CT>> predList =
    //            new ArrayList<Predicate<CT>>();
    //    
    //    predList.addAll( 
    //            Arrays.asList( 
    //                    orPredicateArr ) );
    //    
    //    for ( int predToCheckIndex = 0 ; predToCheckIndex < predList.size() ; predToCheckIndex++ )
    //    {
    //        final Predicate<CT> predToCheck = predList.get( predToCheckIndex );
    //
    //        for ( int otherPredIndex = predToCheckIndex + 1 ; otherPredIndex < predList.size() ; otherPredIndex++ )
    //        {
    //            final Predicate<CT> otherPred = predList.get( otherPredIndex );
    //
    //            if ( BruteForceSolver.isCompatibleTo( 
    //                    exclude , 
    //                    cache ,
    //                    predToCheck ,
    //                    otherPred ) )
    //            {
    //                predList.remove( predToCheckIndex );
    //                predToCheckIndex--;
    //                break;
    //            }
    //        }
    //    }
    //
    //    return predList;
    //}

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
        if ( getClass() != obj.getClass() )
            return false;
        final Or<CT> other = (Or<CT>) obj;
        //return this.orPredicateSet.equals( other.orPredicateSet );
        // Ignorieren Reihenfolge der Sub-Terme beim Vergleich
        return this.orPredicateSet.equalsSet( other.orPredicateSet );
    }

    /**
     * hash code cache.
     */
    private int hashCode;
    
    /**
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode()
    {
        if ( this.hashCode == 0 )
        {
            this.hashCode = this.orPredicateSet.hashCode();
        }
        return this.hashCode;
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
        //return "Or:" + this.orPredicateSet.toString();
        if ( this.toStrCache == null )
        {
            final SeparatedBuffer sepBuff = new SeparatedBuffer( " or " );
            for ( final Predicate<CT> predicate : this.orPredicateSet )
            {
                sepBuff.add( predicate.toString() );
            }
            this.toStrCache = "(" + sepBuff + ")";
        }
        return this.toStrCache;
    }

    /**
     * @see Predicate#test
     */
    @Override
    public boolean test( 
            final CT contextObj )
    {
        for ( final Predicate<CT> predicate : this.orPredicateSet )
        {
            if ( predicate.test( contextObj ) )
                // mindestens ein Prädikats-Test ist erfolgreich
            {
                return true;
            }
        }

        return false;
    }

    /**
     * @see Predicate#toFileName(java.lang.String)
     */
    @Override
    public String toFileName( 
            final String separatorStr )
    {
        final SeparatedBuffer sepBuff = new SeparatedBuffer( separatorStr );
        
        // OR-Begin
        sepBuff.add( "ORB" );
        
        for ( final Predicate<CT> predicate : this.orPredicateSet )
        {
            sepBuff.add( 
                    predicate.toFileName( 
                            separatorStr ) );
        }

        // OR-End
        sepBuff.add( "ORE" );
        
        return sepBuff.toString();
    }

    /**
     * @see java.lang.Iterable#iterator()
     */
    @Override
    public Iterator<Predicate<CT>> iterator()
    {
        return this.orPredicateSet.iterator();
    }

    /**
     * @return Anzahl Unter-Prädikate
     */
    public int size()
    {
        return this.orPredicateSet.size();
    }
    
    /**
     * Zurückgeben eines bestimmten Unter-Prädikats.
     * 
     * @param index Position des gewünschten Unter-Prädikates (Basis 0)
     * @return gewünschtes Unter-Prädikate
     */
    public Predicate<CT> get(
            final int index )
    {
//        return IteratorUtil.get( 
//                this.orPredicateSet.iterator() ,
//                index );
        return this.orPredicateSet.get( 
                index );
    }

    /**
     * @param otherPredicate zu prüfendes Prädikat
     * @return ob enthalten
     * @see java.util.Collection#contains(Object)
     */
    public boolean contains(
            final Predicate<CT> otherPredicate )
    {
        return this.orPredicateSet.contains( 
                otherPredicate );
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
        boolean isChanged = false;

        final List<Predicate<CT>> retainPredicateList =
                new ArrayList<Predicate<CT>>();

        for ( Predicate<CT> subPredicate : this )
        {
            if ( subPredicate.isPrimitive() )
            {
                if ( predicateSetToRemove.contains( subPredicate ) )
                {
                    retainPredicateList.add(
                            new FalsePredicate<CT>() );

                    isChanged = true;
                }
                else
                {
                    retainPredicateList.add(
                            subPredicate );
                }
            }
            else
            {
                final Predicate<CT> cleanedNonPrimitiveSubPredicate =
                        subPredicate.replacePrimitivePredicatesWithFalse( 
                                predicateSetToRemove ,
                                //exclude ,
                                cache );

                if ( cleanedNonPrimitiveSubPredicate != null )
                    // bei völlig leerem gereinigtem Sub-Prädikat kann null auftauchen
                {
                    retainPredicateList.add(
                            cleanedNonPrimitiveSubPredicate );
                    
                    isChanged |= false == subPredicate.equals( cleanedNonPrimitiveSubPredicate );
                }
                else
                {
                    isChanged = true;
                }
            }
        }

        if ( false == isChanged )
        {
            return this;
        }

        if ( retainPredicateList.isEmpty() )
        {
            //return null;
            return new FalsePredicate<CT>();
        }

        if ( retainPredicateList.size() == 1 )
        {
            // nur ein Sub-Prädikat ist übrig geblieben
            return retainPredicateList.get( 0 );
        }

        return 
                //new Or<CT>(
                Or.instanceOf(
                        this.lineNr ,
                        cache ,
                        retainPredicateList.toArray( 
                                (Predicate<CT>[]) new Predicate[ retainPredicateList.size() ] ) );
    }

    ///**
    // * @see Predicate#replacePrimitivePredicatesWithTrue
    // */
    //@Override
    //public Predicate<CT> replacePrimitivePredicatesWithTrue(
    //        final HashSet<PrimitivPredicate<CT>> predicateSetToRemove , 
    //        final Exclude<CT> exclude )
    //{
    //    final List<Predicate<CT>> retainPredicateList =
    //            new ArrayList<Predicate<CT>>();
    //
    //    for ( Predicate<CT> subPredicate : this )
    //    {
    //        if ( subPredicate.isPrimitive() )
    //        {
    //            if ( predicateSetToRemove.contains( subPredicate ) )
    //            {
    //                retainPredicateList.add(
    //                        new TruePredicate<CT>() );
    //            }
    //            else
    //            {
    //                retainPredicateList.add(
    //                        subPredicate );
    //            }
    //        }
    //        else
    //        {
    //            final Predicate<CT> cleanedNonPrimitiveSubPredicate =
    //                    subPredicate.replacePrimitivePredicatesWithTrue( 
    //                            predicateSetToRemove ,
    //                            exclude );
    //
    //            if ( cleanedNonPrimitiveSubPredicate != null )
    //                // bei völlig leerem gereinigtem Sub-Prädikat kann null auftauchen
    //            {
    //                retainPredicateList.add(
    //                        cleanedNonPrimitiveSubPredicate );
    //            }
    //
    //        }
    //    }
    //
    //    if ( retainPredicateList.isEmpty() )
    //    {
    //        //return null;
    //        return new FalsePredicate<CT>();
    //    }
    //
    //    if ( retainPredicateList.size() == 1 )
    //    {
    //        // nur ein Sub-Prädikat ist übrig geblieben
    //        return retainPredicateList.get( 0 );
    //    }
    //
    //    return 
    //            //new Or<CT>(
    //            Or.instanceOf(
    //                    this.lineNr ,
    //                    exclude ,
    //                    retainPredicateList.toArray( 
    //                            (Predicate<CT>[]) new Predicate[ retainPredicateList.size() ] ) );
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
        boolean isChanged = false;

        final List<Predicate<CT>> retainPredicateList =
                new ArrayList<Predicate<CT>>();

        for ( Predicate<CT> subPredicate : this )
        {
            if ( predicateSetToRemove.contains( subPredicate ) )
            {
                retainPredicateList.add(
                        new TruePredicate<CT>() );

                isChanged = true;
            }
            else
            {
                final Predicate<CT> cleanedNonPrimitiveSubPredicate =
                        subPredicate.replacePredicatesWithTrue( 
                                predicateSetToRemove ,
                                //exclude ,
                                cache );

                if ( cleanedNonPrimitiveSubPredicate != null )
                    // bei völlig leerem gereinigtem Sub-Prädikat kann null auftauchen
                {
                    retainPredicateList.add(
                            cleanedNonPrimitiveSubPredicate );
                    
                    isChanged |= false == subPredicate.equals( cleanedNonPrimitiveSubPredicate );
                }
                else
                {
                    isChanged = true;
                }
            }
        }

        if ( false == isChanged )
        {
            return this;
        }

        if ( retainPredicateList.isEmpty() )
        {
            //return null;
            return new FalsePredicate<CT>();
        }

        if ( retainPredicateList.size() == 1 )
        {
            // nur ein Sub-Prädikat ist übrig geblieben
            return retainPredicateList.get( 0 );
        }

        return 
                //new Or<CT>(
                Or.instanceOf(
                        this.lineNr ,
                        cache ,
                        retainPredicateList.toArray( 
                                (Predicate<CT>[]) new Predicate[ retainPredicateList.size() ] ) );
    }

    /**
     * @see Predicate#getPredicateListFromAndNotNegated(java.lang.Class)
     */
    @Override
    public final List<PrimitivPredicate<CT>> getPredicateListFromAndNotNegated(
            final Class<? extends PrimitivPredicate<CT>> predicateClass )
    {
        return null;
    }

    /**
     * Diese Methode sammelt die includierten Prädikate,
     * die bei allen prmitiven Sub-Prädikaten gültig
     * sind und somit für die Or-Prädikat bei Erfüllung
     * ebenfalls immer erfüllt sind.
     * 
     * TODO später auch And-Prädikate im Or beachten
     * 
     * @return siehe methoden-Kommentar
     */
    public final Collection<PrimitivPredicate<CT>> collectIncludePredicatesForAllSubPredicates()
    {
        final HashSet<PrimitivPredicate<CT>> alwaysSatifiedIncludePredicateSet =
                new HashSet<PrimitivPredicate<CT>>();
        
        boolean isFirstLoop = true;
        
        for ( final Predicate<CT> orSubPredicate : this )
        {
            if ( orSubPredicate instanceof PrimitivPredicate &&
                    ( (PrimitivPredicate<CT>) orSubPredicate ).includePredicateSet != null )
            {
                if ( isFirstLoop )
                    // erste Runde
                {
                    // beim ersten mal werden alle includierten Prädikate vermerkt
                    alwaysSatifiedIncludePredicateSet.addAll(
                            ( (PrimitivPredicate<CT>) orSubPredicate ).includePredicateSet );
                    
                    isFirstLoop = false;
                }
                else
                {
                    // bei allen weiteren Runden werden nur die auftauchenden includierten Prädikate behalten
                    alwaysSatifiedIncludePredicateSet.retainAll(
                            ( (PrimitivPredicate<CT>) orSubPredicate ).includePredicateSet );
                }
            }
            // TODO später auch And-Prädikate im Or beachten: else if ( orSubPredicate instanceof And )
            // {
            // }
            else if ( orSubPredicate instanceof Or )
            {
                throw new IllegalArgumentException(
                        "predicate not melted: " + this );
            }
        }

        return alwaysSatifiedIncludePredicateSet;
    }

    /**
     * @see Predicate#getIncludePredicateSetRecursiv
     */
    @Override
    public final ReadOnlySet<PrimitivPredicate<CT>> getIncludePredicateSetRecursiv()
    {
        // TODO cache
        final Set<PrimitivPredicate<CT>> result = new HashSet<PrimitivPredicate<CT>>();
        
        for ( final Predicate<CT> subPredicate : this )
        {
            result.addAll(
                    ( (PrimitivPredicate<CT>) subPredicate ).getIncludePredicateSetRecursiv().toJavaSet() );
        }
        
        return new ReadOnlySetAdaptor<PrimitivPredicate<CT>>(
                result );
    }

    /**
     * @see Predicate#getIncludePredicateSetRecursivInclusiveSelf
     */
    @Override
    public final ReadOnlySet<PrimitivPredicate<CT>> getIncludePredicateSetRecursivInclusiveSelf()
    {
        // TODO cache
        final Set<PrimitivPredicate<CT>> result = new HashSet<PrimitivPredicate<CT>>();
        
        for ( final Predicate<CT> subPredicate : this )
        {
            result.addAll(
                    subPredicate.getIncludePredicateSetRecursivInclusiveSelf().toJavaSet() );
        }
        
        return new ReadOnlySetAdaptor<PrimitivPredicate<CT>>(
                result );
    }

    /**
     * @return ob dieses Oder-Prädikat nur nicht paramterisierte primtive Prädikate enthält
     */
    public final boolean isAllSubPredicatesPrimitivAndNotParametrized()
    {
        for ( final Predicate<CT> subPredicate : this )
        {
            if ( false == ( subPredicate instanceof PrimitivPredicate ||
                    subPredicate instanceof ParametricalPredicate ) )
            {
                // TODO Cache
                return false;
            }
        }
        return true;
    }

    /**
     * @return ob dieses Oder-Prädikat nur nicht paramterisierte primtive Prädikate bzw And-Prädikate, auf welches dies zutrifft enthält
     */
    public final boolean isAllSubPredicatesPrimitivNotParametrizedRespectivlyAnd()
    {
        for ( final Predicate<CT> subPredicate : this )
        {
            if ( subPredicate instanceof Or ||
                    subPredicate instanceof Xor ||
                    subPredicate instanceof ParametricalPredicate )
            {
                // TODO Cache
                return false;
            }
            else if ( subPredicate instanceof And )
            {
                if ( false == ( (And<CT>) subPredicate ).isAllSubPredicatesPrimitivAndNotParametrized() )
                {
                    return false;
                }
            }
            else if ( subPredicate instanceof PrimitivPredicate )
            {
                continue;
            }
            else
            {
                throw new UnreachableCodeException(
                        String.valueOf( subPredicate ) );
            }
            
        }
        return true;
    }

    /**
     * Cache für {@link #getPrimitivPredicateSet}
     */
    private ReadOnlySet<PrimitivPredicate<CT>> primitivPredicateSet;

    /**
     * @see Predicate#getPrimitivPredicateSet
     */
    @Override
    public final ReadOnlySet<PrimitivPredicate<CT>> getPrimitivPredicateSet()
    {
        if ( this.primitivPredicateSet == null )
        {
            final HashsetWithReadonly<PrimitivPredicate<CT>> result =
                    new HashsetWithReadonly<PrimitivPredicate<CT>>();

            for ( final Predicate<CT> subPredicate : this )
            {
                result.addAll(
                        subPredicate.getPrimitivPredicateSet() );
            }

            this.primitivPredicateSet =
                    new ReadOnlySetAdaptor<PrimitivPredicate<CT>>(
                            result );

        }
        return this.primitivPredicateSet;
    }

    /**
     * @see Predicate#hasNotSubPred
     */
    @Override
    public final boolean hasNotSubPred()
    {
        for ( final Predicate<CT> subPredicate : this )
        {
            if ( subPredicate.hasNotSubPred() )
            {
                return true;
            }
        }
        return false;
    }

    /**
     * @see Predicate#getMandantoryPrimitivPredicateSet
     */
    @Override
    public final ReadOnlySet<PrimitivPredicate<CT>> getMandantoryPrimitivPredicateSet()
    {
        /*final*/ HashsetWithReadonly<PrimitivPredicate<CT>> result = null;

        for ( final Predicate<CT> subPredicate : this )
        {
            if ( result == null )
                // erster Durchlauf
            {
                result = new HashsetWithReadonly<PrimitivPredicate<CT>>();

                result.addAll(
                        // beim Or muss auf die includierten Prädikate heruntergebrochen werden, weil eventuell die inkludierenden Prädikate nicht in allen Zweigen erforderlich sind
                        subPredicate.getMandantoryPrimitivPredicateSet() );
            }
            else
                // in Folgedurchläufen nur die Vereinigungsmenge aller inkludierenden Prädikate behalten
            {
                result.retainAll(
                        // beim Or muss auf die includierten Prädikate heruntergebrochen werden, weil eventuell die inkludierenden Prädikate nicht in allen Zweigen erforderlich sind
                        subPredicate.getMandantoryPrimitivPredicateSet().toJavaSet() );
            }
        }

        return result;
    }

    /**
     * @see Predicate#getNegatedMandantoryPrimitivPredicateSet
     */
    @Override
    public final ReadOnlySet<PrimitivPredicate<CT>> getNegatedMandantoryPrimitivPredicateSet()
    {
        /*final*/ HashsetWithReadonly<PrimitivPredicate<CT>> result = null;

        for ( final Predicate<CT> subPredicate : this )
        {
            if ( result == null )
                // erster Durchlauf
            {
                result = new HashsetWithReadonly<PrimitivPredicate<CT>>();

                result.addAll(
                        // beim Or muss auf die includierten Prädikate heruntergebrochen werden, weil eventuell die inkludierenden Prädikate nicht in allen Zweigen erforderlich sind
                        subPredicate.getNegatedMandantoryPrimitivPredicateSet() );
            }
            else
                // in Folgedurchläufen nur die Vereinigungsmenge aller inkludierenden Prädikate behalten
            {
                result.retainAll(
                        // beim Or muss auf die includierten Prädikate heruntergebrochen werden, weil eventuell die inkludierenden Prädikate nicht in allen Zweigen erforderlich sind
                        subPredicate.getNegatedMandantoryPrimitivPredicateSet().toJavaSet() );
            }
        }

        return result;
    }

    /**
     * @see Predicate#getNonNegatedPrimitivPredicateSet
     */
    @Override
    public final ReadOnlySet<PrimitivPredicate<CT>> getNonNegatedPrimitivPredicateSet()
    {
        final HashsetWithReadonly<PrimitivPredicate<CT>> result = new HashsetWithReadonly<PrimitivPredicate<CT>>();

        for ( final Predicate<CT> subPredicate : this )
        {
            result.addAll( 
                    subPredicate.getNonNegatedPrimitivPredicateSet() );
        }

        return result;
    }

    /**
     * @see Predicate#hasOr
     */
    @Override
    public boolean hasOr()
    {
        return true;
    }

    /**
     * @see Predicate#hasNot
     */
    @Override
    public boolean hasNot()
    {
        for ( final Predicate<CT> subPredicate : this )
        {
            if ( subPredicate.hasNot() )
            {
                return true;
            }
        }
        return false;
    }

}
