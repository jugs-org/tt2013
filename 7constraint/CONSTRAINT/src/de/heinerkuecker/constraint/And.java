package de.heinerkuecker.constraint;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import de.heinerkuecker.constraint.util.coll.FirstInsertOrderHashset;
import de.heinerkuecker.constraint.util.coll.HashsetWithReadonly;
import de.heinerkuecker.constraint.util.coll.ReadOnlySet;
import de.heinerkuecker.constraint.util.coll.ReadOnlySetAdaptor;
import de.heinerkuecker.constraint.util.lang.SeparatedBuffer;

/**
 * UND-Verknüpfung von Prädikaten
 * {@link Predicate}.
 * 
 * @param <CT> Type-Parameter Kontext-Objekt-Type
 * @author Heiner K&uuml;cker
 */
public final class And<CT>
extends Predicate<CT>
implements Iterable<Predicate<CT>>
{
    /**
     * FirstInsertOrderHashset zum Absichern, dass ein identisches
     * Prädikat nur einmal aufgeführt wird und Absichern der
     * Erhaltung der Reihenfolge der Prädikate.
     */
    private final FirstInsertOrderHashset<Predicate<CT>> andPredicateSet;
    // Array für bessere Performance
    //private final Predicate<CT>[] andPredicateArr;

    /**
     * Konstruktor.
     * 
     * @param lineNr Zeilen-Nummer
     * @param exclude Exclude-Objekt zum Ausfiltern negierter Sub-Prädikate, die sich mit einem der anderen Prädikate ausschliessen
     * @param andPredicateArr Array mit Prädikaten, welche UND-verknüpft werden sollen
     */
    private And( 
            final int lineNr ,
            //final Exclude<CT> exclude ,
            final ConstraintCodeGeneratorCache<CT> cache ,
            //final boolean cleanForImplicits ,
            final Predicate<CT>... andPredicateArr )
    {
        super( 
                lineNr );

        if ( andPredicateArr.length < 2 )
        {
            throw new IllegalArgumentException();
        }

        //final List<Predicate<CT>> cleanedForImplicitsAndPredicateList;
        //if ( cleanForImplicits )
        //{
        //    cleanedForImplicitsAndPredicateList =
        //            cleanForImplicits(
        //                    exclude ,
        //                    andPredicateArr ,
        //                    cache );
        //}
        //else
        //{
        //    // nicht cleanen, weil es zu einem Stackoverflow kommen kann
        //    cleanedForImplicitsAndPredicateList =
        //            Arrays.asList( 
        //                    andPredicateArr );
        //}

        this.andPredicateSet = 
        //final FirstInsertOrderHashset<Predicate<CT>> andPredicateSet =
            new FirstInsertOrderHashset<Predicate<CT>>();

        this.andPredicateSet.addAll(
        //andPredicateSet.addAll(
                //Arrays.asList(
                //        andPredicateArr )
                //cleanedForImplicitsAndPredicateList
                Arrays.asList(
                        andPredicateArr ) );
        
        //this.andPredicateArr =
        //        andPredicateSet.toArray(
        //                new Predicate[ andPredicateSet.size() ] );

        //// entfernen von not-Prädikaten mit Prädikaten, die sich mit einem der nicht-negierten Prädikate sowieso ausschliessen
        //final FirstInsertOrderHashset<Predicate<CT>> excludeCleanedSet =
        //        exludeClean(
        //                andPredicateArr ,
        //                exclude );
        //
        //this.andPredicateSet =
        //        excludeCleanedSet;
    }
    
    public static <CT> Predicate<CT> instanceOf(
            final int lineNr ,
            //final Exclude<CT> exclude ,
            final ConstraintCodeGeneratorCache<CT> cache ,
            //final boolean cleanForImplicits ,
            final Predicate<CT>... andPredicateArr )
    {
        if ( andPredicateArr.length == 0 )
        {
            throw new IllegalArgumentException(
                    "andPredicateArr is empty" );
        }

        if ( andPredicateArr.length == 1 )
        {
            return andPredicateArr[ 0 ];
        }

        final ArrayList<Predicate<CT>> predicateList =
                new ArrayList<Predicate<CT>>(
                        Arrays.asList(
                                andPredicateArr ) );

        for ( final Iterator<Predicate<CT>> iterator = predicateList.iterator() ; iterator.hasNext() ; )
        {
            final Predicate<CT> predicate = (Predicate<CT>) iterator.next();

            if ( predicate.equals( new FalsePredicate<CT>() ) )
            {
                return new FalsePredicate<CT>();
            }

            if ( predicate.equals( new TruePredicate<CT>() ) )
            {
                iterator.remove();
            }
        }

        if ( predicateList.isEmpty() )
        {
            return new TruePredicate<CT>();
        }

        if ( predicateList.size() == 1 )
        {
            // nur ein Sub-Prädikat ist übrig geblieben
            return predicateList.get( 0 );
        }

        //final List<Predicate<CT>> cleanedForImplicitsAndPredicateList;
        //if ( cleanForImplicits )
        //{
        //    cleanedForImplicitsAndPredicateList =
        //            cleanForImplicits(
        //                    //exclude ,
        //                    cache ,
        //                    predicateList );
        //}
        //else
        //{
        //    // nicht cleanen, weil es zu einem Stackoverflow kommen kann
        //    cleanedForImplicitsAndPredicateList =
        //            //Arrays.asList( 
        //            //        andPredicateArr )
        //            predicateList;
        //}
        //if ( cleanedForImplicitsAndPredicateList.size() == 1 )
        //{
        //    return cleanedForImplicitsAndPredicateList.get( 0 );
        //}

        return
                Normalizer.melt(
                        new And<CT>( 
                                lineNr , 
                                //exclude , 
                                cache ,
                                //cleanForImplicits ,
                                //predicateList.toArray( new Predicate[ predicateList.size() ] )
                                //cleanedForImplicitsAndPredicateList.toArray( new Predicate[ cleanedForImplicitsAndPredicateList.size() ] )
                                predicateList.toArray( new Predicate[ predicateList.size() ] ) ) ,
                        cache );
    }

    /**
     * @param andPredicateArr
     * @return
     */
    private static <CT> List<Predicate<CT>> cleanForImplicits( 
            //final Exclude<CT> exclude ,
            //final Predicate<CT>[] andPredicateArr ,
            final ConstraintCodeGeneratorCache<CT> cache ,
            final List<Predicate<CT>> andPredicateList )
    {
        //if ( this.lineNr == 845 )
        //{
        //    //System.out.println( "debug break" );
        //}
        /* (A or B) and (A)
         * Term1: (A or B)
         * Term2: (A)
         * Term1 wird impliziert von Term2, also kann Term1 entfallen
         */
        
        System.out.println( "$FileName<.\\src\\de\\heinerkuecker\\constraint\\And.java> $LineNr: 205: cleanForImplicits " + /*Arrays.toString( andPredicateArr )*/andPredicateList );

        //final ArrayList<Predicate<CT>> predList = new ArrayList<Predicate<CT>>();
        //predList.addAll( Arrays.asList( andPredicateArr ) );
        
        for ( int predToCheckIndex = 0 ; predToCheckIndex < /*predList*/andPredicateList.size() ; predToCheckIndex++ )
        {
            final Predicate<CT> predToCheck = /*predList*/andPredicateList.get( predToCheckIndex );

            for ( int otherPredIndex = predToCheckIndex + 1 ; otherPredIndex < /*predList*/andPredicateList.size() ; otherPredIndex++ )
            {
                final Predicate<CT> otherPred = /*predList*/andPredicateList.get( otherPredIndex );

                if ( BruteForceCompatibleChecker.isCompatibleTo( 
                        cache ,
                        otherPred ,
                        predToCheck ) )
                {
                    /*predList*/andPredicateList.remove( predToCheckIndex );
                    predToCheckIndex--;
                    break;
                }
            }
        }

        return /*predList*/andPredicateList;
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
        if ( getClass() != obj.getClass() )
            return false;
        final And<CT> other = (And<CT>) obj;
        //return this.andPredicateSet.equals( other.andPredicateSet );
        // Ignorieren Reihenfolge der Sub-Terme beim Vergleich
        return this.andPredicateSet.equalsSet( other.andPredicateSet );
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
            this.hashCode = this.andPredicateSet.hashCode();
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
        if ( this.toStrCache == null )
        {
            //return "And:" + this.andPredicateSet.toString();
            final SeparatedBuffer sepBuff = new SeparatedBuffer( " and " );
            for ( final Predicate<CT> predicate : this.andPredicateSet )
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
        for ( final Predicate<CT> predicate : this.andPredicateSet )
        {
            if ( ! predicate.test( contextObj ) )
                // mindestens ein Prädikats-Test schlägt fehl
            {
                return false;
            }
        }

        return true;
    }

    /**
     * @see Predicate#toFileName(java.lang.String)
     */
    @Override
    public String toFileName( 
            final String separatorStr )
    {
        final SeparatedBuffer sepBuff = new SeparatedBuffer( separatorStr );
        
        // AND-Begin
        sepBuff.add( "ANDB" );
        
        for ( final Predicate<CT> predicate : this.andPredicateSet )
        {
            sepBuff.add( 
                    predicate.toFileName( 
                            separatorStr ) );
        }

        // AND-End
        sepBuff.add( "ANDE" );
        
        return sepBuff.toString();
    }

    /**
     * @see java.lang.Iterable#iterator()
     */
    @Override
    public Iterator<Predicate<CT>> iterator()
    {
        return this.andPredicateSet.iterator();
    }

    /**
     * @return Anzahl Unter-Prädikate
     */
    public int size()
    {
        return this.andPredicateSet.size();
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
//                this.andPredicateSet.iterator() ,
//                index );
        return this.andPredicateSet.get( 
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
        return this.andPredicateSet.contains( 
                otherPredicate );
    }

    ///**
    // * (R3 or A3) and R3
    // * gibt
    // * R3 and not(A3)
    // * sowie exlude ( R3 , A3 )
    // * gibt
    // * R3
    // * 
    // * @param andPredicateArr
    // * @param exclude
    // * @return
    // */
    //private FirstInsertOrderHashset<Predicate<CT>> exludeClean_unused( 
    //        final Predicate<CT>[] andPredicateArr , 
    //        final Exclude<CT> exclude )
    //{
    //    final FirstInsertOrderHashset<Predicate<CT>> tmpAndPredicateSet =
    //            new FirstInsertOrderHashset<Predicate<CT>>(
    //                    Arrays.asList(
    //                            andPredicateArr ) );
    //    
    //    for ( final Predicate<CT> runPredicate : andPredicateArr )
    //    {
    //        if ( runPredicate instanceof PrimitivPredicate &&
    //                exclude.contains( (PrimitivPredicate<CT>) runPredicate ) )
    //        {
    //            for ( final Predicate<CT> otherRunPredicate : andPredicateArr )
    //            {
    //                if ( false == runPredicate.equals( otherRunPredicate ) )
    //                    // wenn nicht das aktuelle Prädikat
    //                {
    //                    // doppelte Negation zu einem eventuell mit übergebenen negierten Exclude-Prädikat
    //                    final Predicate<CT> otherRunPredicateNegated =
    //                            new Not<CT>( 
    //                                    0 ,
    //                                    otherRunPredicate );
    //                    
    //                    final Predicate<CT> otherRunPredicateNegatedNormalized =
    //                            Normalizer.normalize( 
    //                                    otherRunPredicateNegated , 
    //                                    exclude );
    //                    
    //                    if ( exclude.contains(
    //                            (PrimitivPredicate<CT>) runPredicate ,
    //                            otherRunPredicateNegatedNormalized ) )
    //                    {
    //                        tmpAndPredicateSet.remove( 
    //                                otherRunPredicate );
    //                    }
    //                }
    //            }
    //        }
    //    }
    //    
    //    return tmpAndPredicateSet;
    //}

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

        for ( final Predicate<CT> subPredicate : this )
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
                //new And<CT>(
                And.instanceOf(
                        this.lineNr ,
                        //exclude ,
                        cache ,
                        //cleanForImplicits
                        //false ,
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
    //        return new TruePredicate<CT>();
    //    }
    //    
    //    if ( retainPredicateList.size() == 1 )
    //    {
    //        // nur ein Sub-Prädikat ist übrig geblieben
    //        return retainPredicateList.get( 0 );
    //    }
    //
    //    return 
    //            //new And<CT>(
    //            And.instanceOf(
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

        for ( final Predicate<CT> subPredicate : this )
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
            return new TruePredicate<CT>();
        }
        
        if ( retainPredicateList.size() == 1 )
        {
            // nur ein Sub-Prädikat ist übrig geblieben
            return retainPredicateList.get( 0 );
        }

        return 
                //new And<CT>(
                And.instanceOf(
                        this.lineNr ,
                        //exclude ,
                        cache ,
                        //cleanForImplicits
                        //false ,
                        retainPredicateList.toArray( 
                                (Predicate<CT>[]) new Predicate[ retainPredicateList.size() ] ) );
    }

    /**
     * @see Predicate#getPredicateListFromAndNotNegated
     */
    @Override
    public final List<PrimitivPredicate<CT>> getPredicateListFromAndNotNegated(
            final Class<? extends PrimitivPredicate<CT>> predicateClass )
    {
        final List<PrimitivPredicate<CT>> predicateListFromAndNotNegated =
                new ArrayList<PrimitivPredicate<CT>>();
        
        for ( final Predicate<CT> subPredicate : this )
        {
            final List<PrimitivPredicate<CT>> subPredicateListFromAndNotNegated =
                    subPredicate.getPredicateListFromAndNotNegated( 
                            predicateClass );
            
            if ( subPredicateListFromAndNotNegated != null )
            {
                predicateListFromAndNotNegated.addAll( 
                        subPredicateListFromAndNotNegated );
            }
        }
        
        return predicateListFromAndNotNegated;
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
     * 
     * @return
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
        final HashsetWithReadonly<PrimitivPredicate<CT>> result = new HashsetWithReadonly<PrimitivPredicate<CT>>();

        for ( final Predicate<CT> subPredicate : this )
        {
            result.addAll( 
                    subPredicate.getMandantoryPrimitivPredicateSet() );
        }

        return result;
    }

    /**
     * @see Predicate#getNegatedMandantoryPrimitivPredicateSet
     */
    @Override
    public final ReadOnlySet<PrimitivPredicate<CT>> getNegatedMandantoryPrimitivPredicateSet()
    {
        final HashsetWithReadonly<PrimitivPredicate<CT>> result = new HashsetWithReadonly<PrimitivPredicate<CT>>();

        for ( final Predicate<CT> subPredicate : this )
        {
            result.addAll( 
                    subPredicate.getNegatedMandantoryPrimitivPredicateSet() );
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
        for ( final Predicate<CT> subPredicate : this )
        {
            if ( subPredicate.hasOr() )
            {
                return true;
            }
        }
        return false;
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