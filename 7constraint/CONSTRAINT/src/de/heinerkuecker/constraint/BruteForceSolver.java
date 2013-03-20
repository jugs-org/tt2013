package de.heinerkuecker.constraint;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;

import de.heinerkuecker.constraint.util.coll.FirstInsertOrderHashset;
import de.heinerkuecker.constraint.util.coll.ReadOnlySet;
import de.heinerkuecker.constraint.util.exc.UnreachableCodeException;

/**
 * Brute-Force-Solver f�r verschiedene
 * Aufgaben im
 * {@link AbstractConstraintCodeGenerator}.
 *
 * @author Heiner K&uuml;cker
 */
public final class BruteForceSolver
{
//    public static <CT> boolean isDisjoint_kann_weg(
//            final Exclude<CT> exclude ,
//            final Predicate<CT> thisPredicateExpression ,
//            final Predicate<CT> otherPredicateExpression )
//    {
//        final FirstInsertOrderHashset<PrimitivPredicate<CT>> thisSimplePredicateSet =
//                new FirstInsertOrderHashset<PrimitivPredicate<CT>>();
//
//        collectSimplePredicates(
//                thisPredicateExpression ,
//                thisSimplePredicateSet );
//
//        final FirstInsertOrderHashset<PrimitivPredicate<CT>> otherSimplePredicateSet =
//                new FirstInsertOrderHashset<PrimitivPredicate<CT>>();
//
//        collectSimplePredicates(
//                otherPredicateExpression ,
//                otherSimplePredicateSet );
//
//        if ( isDisjoint(
//                thisSimplePredicateSet.getSet() ,
//                otherSimplePredicateSet.getSet() ) )
//        {
//            return true;
//        }
//
//        final FirstInsertOrderHashset<PrimitivPredicate<CT>> jointSimplePredicateSet =
//                new FirstInsertOrderHashset<PrimitivPredicate<CT>>();
//
//        jointSimplePredicateSet.addAll( thisSimplePredicateSet  );
//        jointSimplePredicateSet.addAll( otherSimplePredicateSet );
//
//        final BinCounter counter =
//                new BinCounter(
//                        jointSimplePredicateSet.size() );
//
//        final SolvePredicate<CT> thisConvertedSolvePredicate =
//                innerConvert(
//                        thisPredicateExpression ,
//                        counter ,
//                        // die Pr�dikat-Positionen der zusammengef�gten Ausdr�cke verwenden
//                        jointSimplePredicateSet );
//
//        final SolvePredicate<CT> otherConvertedSolvePredicate =
//                innerConvert(
//                        otherPredicateExpression ,
//                        // der selbe Counter
//                        counter ,
//                        // die Pr�dikat-Positionen der zusammengef�gten Ausdr�cke verwenden
//                        jointSimplePredicateSet );
//
//        final IncludeValidChecker<CT> includeValidChecker =
//                new IncludeValidChecker<CT>(
//                        jointSimplePredicateSet ,
//                        counter );
//
//        while ( counter.isOverflow() == false )
//        {
//            final boolean thisResult =
//                    thisConvertedSolvePredicate.solve(
//                            exclude ,
//                            includeValidChecker );
//
//            final boolean otherResult =
//                    otherConvertedSolvePredicate.solve(
//                            exclude ,
//                            includeValidChecker );
//
//            if ( thisResult == otherResult )
//            {
//                return false;
//            }
//
//            counter.skip();
//        }
//
//        return true;
//    }

    /**
     * Methode zum pr�fen, ob die beiden �bergebenen
     * Constraint-Expressions �quivalent sind.
     *
     * @param exclude
     * @param predicateToCheck1
     * @param predicateToCheck2
     * @param cache Cache
     * @return is �quivalent
     */
    public static <CT> boolean isEquivalent(
            //final Exclude<CT> exclude ,
            final ConstraintCodeGeneratorCache<CT> cache ,
            final Predicate<CT> predicateToCheck1 ,
            final Predicate<CT> predicateToCheck2 )
    {
        //System.out.println( "$FileName<.\\src\\de\\heinerkuecker\\constraint\\BruteForceSolver.java> $LineNr: 118: isEquivalent " + predicateToCheck1 + " to " + predicateToCheck2 );

        if ( cache.equivalentPredicateCache.contains(
                predicateToCheck1 ,
                predicateToCheck2 ) )
        {
            return true;
        }

        if ( cache.inequivalentPredicateCache.contains(
                predicateToCheck1 ,
                predicateToCheck2 ) )
        {
            return false;
        }

        // TODO folgender Block kann verwendet werden, weil in der Methode isCompatibleTo die closed world assumption jetzt korrekt wirkt
        if ( "1".equals( "1" ) )
            // dieses if ist nur daf�r da, dass der Compiler nicht sagt, dass der Code nach diesem Block unerreichbar ist, der Code nach diesem Block ist sozusagen auskommentiert
        {
            // mal mit isCompatible versuchen, ist inzwischen schneller (optimiert)
            final boolean isCompatible1 =
                    BruteForceCompatibleChecker.isCompatibleTo(
                            //exclude ,
                            cache ,
                            predicateToCheck1 ,
                            predicateToCheck2 );
            
            if ( false == isCompatible1 )
            {
                cache.inequivalentPredicateCache.add(
                        predicateToCheck1 ,
                        predicateToCheck2 );

                return false;
            }

            final boolean isCompatible2 =
                    BruteForceCompatibleChecker.isCompatibleTo(
                            //exclude ,
                            cache ,
                            predicateToCheck2 ,
                            predicateToCheck1 );

            if ( false == isCompatible2 )
            {
                cache.inequivalentPredicateCache.add(
                        predicateToCheck1 ,
                        predicateToCheck2 );

                return false;
            }

            if ( isCompatible1 && isCompatible2 )
            {
                cache.equivalentPredicateCache.add(
                        predicateToCheck1 ,
                        predicateToCheck2 );
        
                return true;
            }
        }

        final Predicate<CT> noXorPredicateToCheck1 =
                XorConverter.convertXor(
                        predicateToCheck1 ,
                        //exclude ,
                        cache );

        final Predicate<CT> noXorPredicateToCheck2 =
                XorConverter.convertXor(
                        predicateToCheck2 ,
                        //exclude ,
                        cache );

        // TODO excludes beachten

        final FirstInsertOrderHashset<PrimitivPredicate<CT>> simplePredicateSet1 =
                new FirstInsertOrderHashset<PrimitivPredicate<CT>>();

        collectSimplePredicates(
                noXorPredicateToCheck1 ,
                simplePredicateSet1 );

        final FirstInsertOrderHashset<PrimitivPredicate<CT>> simplePredicateSet2 =
                new FirstInsertOrderHashset<PrimitivPredicate<CT>>();

        collectSimplePredicates(
                noXorPredicateToCheck2 ,
                simplePredicateSet2 );

        // aufgrund der Excludes und der Includes k�nnen Expressions kompatibel sein, die keine gemeinsamen Pr�dikate besitzen
        //if ( false == simplePredicateSet1.equalsSet( simplePredicateSet2 ) )
        //    // Pr�fen, ob die gleichen Variablen (Pr�dikate) in beiden Ausdr�cken auftauchen
        //{
        //    return false;
        //}

        final FirstInsertOrderHashset<PrimitivPredicate<CT>> jointSimplePredicateSet =
                new FirstInsertOrderHashset<PrimitivPredicate<CT>>();

        jointSimplePredicateSet.addAll( simplePredicateSet1 );
        jointSimplePredicateSet.addAll( simplePredicateSet2 );

        final BinCounterWithSkipListeners counter =
                new BinCounterWithSkipListeners(
                        jointSimplePredicateSet.size() );

        final SolvePredicate<CT> convertedSolvePredicate1 =
                PredicateToSolvepredicateConvert.convertToSolvePredicate(
                        noXorPredicateToCheck1 ,
                        counter ,
                        // die Pr�dikat-Positionen f�r beide Ausdr�cke verwenden
                        jointSimplePredicateSet );

        final SolvePredicate<CT> convertedSolvePredicate2 =
                PredicateToSolvepredicateConvert.convertToSolvePredicate(
                        noXorPredicateToCheck2 ,
                        // der selbe Counter
                        counter ,
                        // die Pr�dikat-Positionen f�r beide Ausdr�cke verwenden
                        jointSimplePredicateSet );

        final Map<PrimitivPredicate<CT>, SolveSimple<CT>> primitivPredToSolvePredMap =
                new HashMap<PrimitivPredicate<CT>, SolveSimple<CT>>();
        
        for ( final SolveSimple<CT> solvePred : convertedSolvePredicate1.getPrimitiveSolvePredicates() )
        {
            primitivPredToSolvePredMap.put(
                    solvePred.predicateToSolve ,
                    solvePred );
        }

        for ( final SolveSimple<CT> solvePred : convertedSolvePredicate2.getPrimitiveSolvePredicates() )
        {
            primitivPredToSolvePredMap.put(
                    solvePred.predicateToSolve ,
                    solvePred );
        }

        // jetzt noch die includierten Pr�dikate vermerken
        convertedSolvePredicate1.prepareIncludePredicates(
                jointSimplePredicateSet ,
                primitivPredToSolvePredMap );

        convertedSolvePredicate2.prepareIncludePredicates(
                jointSimplePredicateSet ,
                primitivPredToSolvePredMap );

        // der Scope dieses Cache ist diese Methode, innerhalb dieser Methode tauchen immer wieder die selben Set-Objekte auf
        final ContainsAllCache<CT> containsAllCache = new ContainsAllCache<CT>();

        while ( counter.isOverflow() == false )
        {
            final boolean result1 =
                    convertedSolvePredicate1.solve();

            final boolean result2 =
                    convertedSolvePredicate2.solve();

            if ( result1 != result2 )
            {
                if ( new ExcludeIncludeChecker<CT>(
                        cache.exclude ,
                        //jointSimplePredicateSet ,
                        //counter ,
                        primitivPredToSolvePredMap ,
                        containsAllCache ).isValid() )
                    // dieser Check �ber alle primitiven Pr�dikate ist notwendig, weil kleinere Ausdr�cke erf�llbar sein k�nnen aber gr�ssere aufgrund des exclude unerf�llbar sein k�nnen
                {
                    cache.inequivalentPredicateCache.add(
                            predicateToCheck1 ,
                            predicateToCheck2 );

                    return false;
                }
            }
            counter.skip();
        }

        cache.equivalentPredicateCache.add(
                predicateToCheck1 ,
                predicateToCheck2 );

        return true;
    }

    /**
     *
     * @param exclude
     * @param predicateToCheck
     * @return
     */
    public static <CT> HashSet<SafeMember> computeValidSafemember(
            //final Exclude<CT> exclude ,
            final ConstraintCodeGeneratorCache<CT> cache ,
            final Predicate<CT> predicateToCheck )
    {
        final Predicate<CT> noXorPredicateToCheck =
                XorConverter.convertXor(
                        predicateToCheck ,
                        //exclude ,
                        cache );

        final FirstInsertOrderHashset<PrimitivPredicate<CT>> simplePredicateSet =
                new FirstInsertOrderHashset<PrimitivPredicate<CT>>();

        collectSimplePredicates(
                noXorPredicateToCheck ,
                simplePredicateSet );

        final BinCounterWithSkipListeners counter =
                new BinCounterWithSkipListeners(
                        simplePredicateSet.size() );

        final SolvePredicate<CT> convertedSolvePredicate =
                PredicateToSolvepredicateConvert.convertToSolvePredicate(
                        noXorPredicateToCheck ,
                        counter ,
                        simplePredicateSet );

        // Set f�r alle SafeMember des �bergebenen Pr�dikats
        final HashSet<SafeMember> validSafememberSet =
                new HashSet<SafeMember>();

        // alle SafeMember des �bergebenen Pr�dikats einsammeln
        for ( final PrimitivPredicate<CT> simplePredicate : simplePredicateSet )
        {
        	//System.out.println( "simplePredicate: " + simplePredicate );

            if ( simplePredicate.safeMemberSet != null )
            {
                HashSet<? extends SafeMember> safeMemberSet =
                		simplePredicate.safeMemberSet;

                //System.out.println( "safeMemberSet: " + safeMemberSet );

                validSafememberSet.addAll(
                        safeMemberSet );
            }

            addSafememberForInclude(
                    simplePredicate ,
                    validSafememberSet );
        }

        if ( validSafememberSet.isEmpty() )
            // Abbruch
        {
            return validSafememberSet;
        }

        //System.out.println( "validSafememberSet: " + validSafememberSet );

        final Map<PrimitivPredicate<CT>, SolveSimple<CT>> primitivPredToSolvePredMap =
                new HashMap<PrimitivPredicate<CT>, SolveSimple<CT>>();
        
        for ( final SolveSimple<CT> solvePred : convertedSolvePredicate.getPrimitiveSolvePredicates() )
        {
            primitivPredToSolvePredMap.put(
                    solvePred.predicateToSolve ,
                    solvePred );
        }

        // jetzt noch die includierten Pr�dikate vermerken
        convertedSolvePredicate.prepareIncludePredicates(
                simplePredicateSet ,
                primitivPredToSolvePredMap );

        // der Scope dieses Cache ist diese Methode, innerhalb dieser Methode tauchen immer wieder die selben Set-Objekte auf
        final ContainsAllCache<CT> containsAllCache = new ContainsAllCache<CT>();

        boolean isKontra = true;

        while ( counter.isOverflow() == false )
        {
            final boolean result =
                    convertedSolvePredicate.solve();

            //System.out.println( "result: " + result );

            if ( result )
            {
                if ( new ExcludeIncludeChecker<CT>(
                        cache.exclude ,
                        //simplePredicateSet ,
                        //counter ,
                        primitivPredToSolvePredMap ,
                        containsAllCache ).isValid() )
                    // dieser Check �ber alle primitiven Pr�dikate ist notwendig, weil kleinere Ausdr�cke erf�llbar sein k�nnen aber gr�ssere aufgrund des exclude unerf�llbar sein k�nnen
                {
                    //System.out.println( "convertedSolvePredicate: " + convertedSolvePredicate );

                    isKontra = false;

                    final ArrayList<SolveSimple<CT>> succesfullySolvedPredicateCollectList =
                            new ArrayList<SolveSimple<CT>>(
                                    simplePredicateSet.size() );

                    convertedSolvePredicate.collectSatisfiedPrimitiveSolvePredicates(
                            succesfullySolvedPredicateCollectList );

                    // Set zum Sammeln aller erf�llten SafeMember f�r die aktuelle Belegung (Model)
                    final HashSet<SafeMember> safememberToRetainSet =
                            new HashSet<SafeMember>();

                    // bei dieser Belegung (Model) g�ltige SafeMember sammeln
                    for ( final SolveSimple<CT> succesfullySolvedPredicate : succesfullySolvedPredicateCollectList )
                    {
                        if ( ( (SolveSimple<CT>) succesfullySolvedPredicate ).predicateToSolve.safeMemberSet != null )
                        {
                            //System.out.println( "safeMemberSetToAdd: " + ( (SolveSimple) succesfullySolvedPredicate ).predicateToSolve.safeMemberSet );

                            safememberToRetainSet.addAll(
                                    ( (SolveSimple<CT>) succesfullySolvedPredicate ).predicateToSolve.safeMemberSet );
                        }
                        addSafememberForInclude(
                                succesfullySolvedPredicate.predicateToSolve ,
                                safememberToRetainSet );
                    }

                    //System.out.println( "safememberToRetainSet: " + safememberToRetainSet );

                    // nur SafeMember behalten, die bei dieser Belegung (Model) g�ltig sind
                    // so bleiben am Schluss nur die SafeMember �brig, die bei allen erf�llenden Belegungen (Models) g�ltig sind
                    validSafememberSet.retainAll( safememberToRetainSet );

                    if ( validSafememberSet.isEmpty() )
                        // Abbruch
                    {
                        return validSafememberSet;
                    }
                }
            }

            counter.skip();
        }

        //System.out.println( "validSafememberSet: " + validSafememberSet );

        if ( isKontra )
        {
            // TODO durch extra Kontradiktion-Check in AbstractConstraintCodeGenerator nicht mehr notwendig
            throw new IllegalStateException(
                    "isKonstra: " + predicateToCheck );
        }

        return validSafememberSet;
    }

    /**
     *
     * @param simplePredicate
     * @param validSafememberSet
     */
    private static <CT> void addSafememberForInclude(
            final PrimitivPredicate<CT> simplePredicate ,
            final HashSet<SafeMember> validSafememberSet )
    {
        if ( simplePredicate.includePredicateSet == null )
        {
            return;
        }

        for ( final PrimitivPredicate<CT> includePredicate : simplePredicate.includePredicateSet )
        {
            if ( includePredicate.safeMemberSet != null )
            {
                validSafememberSet.addAll(
                        includePredicate.safeMemberSet );
            }
            // rekursiv auch indirekt includierte Pr�dikate vermerken
            addSafememberForInclude(
                    includePredicate ,
                    validSafememberSet );
        }
    }

    /**
     *
     * @param exclude
     * @param predicateToCheck
     * @return
     */
    public static <CT, T extends ToJavaStringable> SafesetTojavastrbleTupel<CT, T> computeValidSafeset(
            final Exclude<CT> exclude ,
            final Predicate<CT> predicateToCheck ,
            final SafeSet<CT, T> safesetDefinition )
    {
        final FirstInsertOrderHashset<PrimitivPredicate<CT>> simplePredicateSet =
                new FirstInsertOrderHashset<PrimitivPredicate<CT>>();

        collectSimplePredicates(
                predicateToCheck ,
                simplePredicateSet );

        final BinCounterWithSkipListeners counter =
                new BinCounterWithSkipListeners(
                        simplePredicateSet.size() );

        final SolvePredicate<CT> convertedSolvePredicate =
                PredicateToSolvepredicateConvert.convertToSolvePredicate(
                        predicateToCheck ,
                        counter ,
                        simplePredicateSet );

        // Set f�r alle Safe-Set-Elemente des �bergebenen Pr�dikats
        final HashSet<T> safeElementToJavaStringableSet =
                new HashSet<T>();

        final Map<PrimitivPredicate<CT>, SolveSimple<CT>> primitivPredToSolvePredMap =
                new HashMap<PrimitivPredicate<CT>, SolveSimple<CT>>();
        
        for ( final SolveSimple<CT> solvePred : convertedSolvePredicate.getPrimitiveSolvePredicates() )
        {
            primitivPredToSolvePredMap.put(
                    solvePred.predicateToSolve ,
                    solvePred );
        }

        // jetzt noch die includierten Pr�dikate vermerken
        convertedSolvePredicate.prepareIncludePredicates(
                simplePredicateSet ,
                primitivPredToSolvePredMap );

        // der Scope dieses Cache ist diese Methode, innerhalb dieser Methode tauchen immer wieder die selben Set-Objekte auf
        final ContainsAllCache<CT> containsAllCache = new ContainsAllCache<CT>();

        while ( counter.isOverflow() == false )
        {
            //System.out.println( "convertedSolvePredicate: " + convertedSolvePredicate );

            final boolean result =
                    convertedSolvePredicate.solve();

            //System.out.println( "result: " + result );

            if ( result )
            {
                if ( new ExcludeIncludeChecker<CT>(
                        exclude ,
                        //simplePredicateSet ,
                        //counter ,
                        primitivPredToSolvePredMap ,
                        containsAllCache ).isValid() )
                    // dieser Check �ber alle primitiven Pr�dikate ist notwendig, weil kleinere Ausdr�cke erf�llbar sein k�nnen aber gr�ssere aufgrund des exclude unerf�llbar sein k�nnen
                {
                    final ArrayList<SolveSimple<CT>> satisfiedSolvedPrimitivpredicateCollectList =
                            new ArrayList<SolveSimple<CT>>(
                                    simplePredicateSet.size() );

                    convertedSolvePredicate.collectSatisfiedPrimitiveSolvePredicates(
                            satisfiedSolvedPrimitivpredicateCollectList );

                    // Set f�r die Safe-Set-Elemente bei der aktuellen Belegung des �bergebenen Pr�dikats
                    final HashSet<T> subSafeElementToJavaStringableSet =
                            new HashSet<T>();

                    // bei dieser Belegung (Model) g�ltige Safe-Set-Elemente sammeln
                    for ( final SolveSimple<CT> satisfiedSolveSimple : satisfiedSolvedPrimitivpredicateCollectList )
                    {
                        if ( satisfiedSolveSimple.predicateToSolve instanceof SafesetPrimitivPredicate )
                        {
                            final SafesetPrimitivPredicate<CT, T> satisfiedSafesetPrimitivPredicate =
                                    (SafesetPrimitivPredicate<CT, T>) ( (SolveSimple<CT>) satisfiedSolveSimple ).predicateToSolve;

                            if ( safesetDefinition.excludeGroup.contains( satisfiedSafesetPrimitivPredicate ) )
                            {
                                subSafeElementToJavaStringableSet.add(
                                        satisfiedSafesetPrimitivPredicate.safeSetValue() );
                            }
                        }
                        addSafesetelementsForInclude(
                                satisfiedSolveSimple.predicateToSolve ,
                                safesetDefinition ,
                                subSafeElementToJavaStringableSet );
                    }

                    if ( subSafeElementToJavaStringableSet.isEmpty() )
                        // bei dieser Belegung gab es keine Elemente des SafeSet
                    {
                        throw new IllegalStateException(
                                "no safe set element for predicate " +
                                        predicateToCheck +
                                        " in model " +
                                        convertedSolvePredicate );
                    }

                    safeElementToJavaStringableSet.addAll(
                            subSafeElementToJavaStringableSet );
                }
            }

            counter.skip();
        }

        //System.out.println( "safeElementToJavaStringableSet: " + safeElementToJavaStringableSet );

        return
                new SafesetTojavastrbleTupel<CT, T>(
                        safesetDefinition ,
                        safeElementToJavaStringableSet );
    }

    private static <CT, T extends ToJavaStringable> void addSafesetelementsForInclude(
            final PrimitivPredicate<CT> simplePredicate ,
            final SafeSet<CT, T> safeSetDefinition ,
            final HashSet<T> safeElementToJavaStringableSet )
    {
        if ( simplePredicate.includePredicateSet == null )
        {
            return;
        }

        for ( final PrimitivPredicate<CT> includePredicate : simplePredicate.includePredicateSet )
        {
            if ( includePredicate instanceof SafesetPrimitivPredicate )
            {
                final SafesetPrimitivPredicate<CT, T> satisfiedSafesetPrimitivPredicate =
                        (SafesetPrimitivPredicate<CT, T>) includePredicate;

                if ( safeSetDefinition.excludeGroup.contains( satisfiedSafesetPrimitivPredicate ) )
                {
                    safeElementToJavaStringableSet.add(
                            satisfiedSafesetPrimitivPredicate.safeSetValue() );
                }
            }

            // rekursiv auch indirekt includierte Pr�dikate vermerken
            addSafesetelementsForInclude(
                    includePredicate ,
                    safeSetDefinition ,
                    safeElementToJavaStringableSet );
        }
    }

    /**
     * Diese Methode pr�ft, ob die �bergebenen
     * Schwitch-Pr�dikate f�r das �bergebene zu
     * switchende Pr�dikat alle Variablen-Belegungen
     * (Model) abdecken (is Covering) und ob
     * die disjunkt sind (bei allen Belegungen
     * h�chstens ein Schwitch-Pr�dikat zutrifft).
     *
     * Wenn keine komplette Abdeckung erfolgt,
     * wird <code>false</code> zur�ckgegeben,
     * es muss eine caseDefault-Methode im
     * AbstractSwitch generiert werden.
     *
     * Wenn die Belegungen nicht disjunkt sind
     * wird eine IllegalArgumentException geworfen.
     *
     * @param predicateToSwitch zu switchendes Pr�dikat
     * @param switchPredicateNotSimplifiedForExcludeArr Schwitch-Pr�dikate, diese d�rfen nicht anhand Exclude vereinfacht sein, da diese Methode keine Excludes beachtet
     * @return ob komplette Abdeckung (is Covering)
     * @throws IllegalArgumentException wenn Schwitch-Pr�dikate nicht disjunkt
     */
    public static <CT> boolean checkSwitchPredicateForCoveringAndDisjointness(
            final Predicate<CT> predicateToSwitch ,
            final int predicateToSwitchLineNr ,
            final Predicate<CT>[] switchPredicateNotSimplifiedForExcludeArr ,
            //final Exclude<CT> exclude ,
            final ConstraintCodeGeneratorCache<CT> cache ,
            final CasePredicateSpecializedPredicateTuple<CT>[] casePredicateTupleArr ,
            final String codegeneratorClassname )
    {
        //System.out.println( "$FileName<.\\src\\de\\heinerkuecker\\constraint\\BruteForceSolver.java> $LineNr: 684: checkSwitchPredicateForCoveringAndDisjointness " + predicateToSwitch + " LineNr " + predicateToSwitch.lineNr );
        //if ( predicateToSwitch.toFileName( "_" ).equals( "ANDB_NOT_FallStatusGeschlossen_ORB_ANDB_EingDstFall_ORB_Z2_ZA2_Z3_ZA3_ORE_ANDE_R2_R3_ORE_ANDE" ) )
        //if ( predicateToSwitch.lineNr >= 800 )
        //{
        //    System.out.println( "debug break" );
        //}

        final Predicate<CT> simplifiedPredicateToSwitch =
                // de Morgan
                Normalizer.simplifyNot(
                        PredicateSimplifier.simplify(
                                predicateToSwitch ,
                                cache ) ,
                        cache );

        final Predicate<CT> singleNotReplacedWithExcludePredicateToSwitch =
                Normalizer.replaceSingleNotWithExclude(
                        simplifiedPredicateToSwitch ,
                        cache );

        final FirstInsertOrderHashset<PrimitivPredicate<CT>> predicateToSwitchSimplePredicateSet =
                new FirstInsertOrderHashset<PrimitivPredicate<CT>>();

        collectSimplePredicates(
                singleNotReplacedWithExcludePredicateToSwitch ,
                predicateToSwitchSimplePredicateSet );

        final Predicate<CT>[] singleNotReplacedWithExcludeCasePredicateArr =
                new Predicate[ switchPredicateNotSimplifiedForExcludeArr.length ];

        for ( int i = 0 ; i < switchPredicateNotSimplifiedForExcludeArr.length ; i++ )
        {
            //System.out.println( "$FileName<.\\src\\de\\heinerkuecker\\constraint\\BruteForceSolver.java> $LineNr: 716: checkSwitchPredicateForCoveringAndDisjointness case " + switchPredicateNotSimplifiedForExcludeArr[ i ] + " LineNr " + switchPredicateNotSimplifiedForExcludeArr[ i ].lineNr );

            final Predicate<CT> simplifiedCasePredicate =
                    // de Morgan
                    Normalizer.simplifyNot(
                            PredicateSimplifier.simplify(
                                    switchPredicateNotSimplifiedForExcludeArr[ i ] ,
                                    cache ) ,
                            cache );

            final Predicate<CT> singleNotReplacedWithExcludeCasePredicate =
                    Normalizer.replaceSingleNotWithExclude(
                            simplifiedCasePredicate ,
                            cache );

            singleNotReplacedWithExcludeCasePredicateArr[ i ] =
                    singleNotReplacedWithExcludeCasePredicate;
        }

        final FirstInsertOrderHashset<PrimitivPredicate<CT>>[] switchPredicateSimplePredicateSetArr =
                new FirstInsertOrderHashset[ switchPredicateNotSimplifiedForExcludeArr.length ];

        for ( int i = 0 ; i < singleNotReplacedWithExcludeCasePredicateArr.length ; i++ )
        {
            switchPredicateSimplePredicateSetArr[ i ] =
                    new FirstInsertOrderHashset<PrimitivPredicate<CT>>();

            collectSimplePredicates(
                    singleNotReplacedWithExcludeCasePredicateArr[ i ] ,
                    switchPredicateSimplePredicateSetArr[ i ] );
        }

        final FirstInsertOrderHashset<PrimitivPredicate<CT>> jointSimplePredicateSet =
                new FirstInsertOrderHashset<PrimitivPredicate<CT>>();

        jointSimplePredicateSet.addAll( predicateToSwitchSimplePredicateSet );

        for ( int i = 0 ; i < switchPredicateSimplePredicateSetArr.length ; i++ )
        {
            final FirstInsertOrderHashset<PrimitivPredicate<CT>> switchPredicateSimplePredicateSet =
                    switchPredicateSimplePredicateSetArr[ i ];

            jointSimplePredicateSet.addAll(
                    switchPredicateSimplePredicateSet );
        }

        final BinCounterWithSkipListeners counter =
                new BinCounterWithSkipListeners(
                        jointSimplePredicateSet.size() );

        //System.out.println( "$FileName<.\\src\\de\\heinerkuecker\\constraint\\BruteForceSolver.java> $LineNr: 766: counter.size(): " + counter.size() );

        final SolvePredicate<CT> predicateToSwitchConvertedSolvePredicate =
                PredicateToSolvepredicateConvert.convertToSolvePredicate(
                        /*predicateToSwitch*/singleNotReplacedWithExcludePredicateToSwitch ,
                        counter ,
                        // die Pr�dikat-Positionen der zusammengef�gten Ausdr�cke verwenden
                        jointSimplePredicateSet );

        final SolvePredicate<CT>[] switchPredicateConvertedSolvePredicateArr =
                new SolvePredicate[ switchPredicateNotSimplifiedForExcludeArr.length ];

        for ( int i = 0 ; i < switchPredicateConvertedSolvePredicateArr.length ; i++ )
        {
            switchPredicateConvertedSolvePredicateArr[ i ] =
                    PredicateToSolvepredicateConvert.convertToSolvePredicate(
                            singleNotReplacedWithExcludeCasePredicateArr[ i ] ,
                            // der selbe Counter
                            counter ,
                            // die Pr�dikat-Positionen der zusammengef�gten Ausdr�cke verwenden
                            jointSimplePredicateSet );
        }

        // bool-Array zum feststellen, ob ein spezialisierendes Pr�dikat �berhaupt einmal erf�llt war
        final boolean[] isNoKontradictionArr = new boolean[ switchPredicateConvertedSolvePredicateArr.length ];

        boolean isCovering = true;

        final ArrayList<CasePosPredTpl<CT>> noDisjointSpecialicedSolvePredicateStrList =
                new ArrayList<CasePosPredTpl<CT>>();

        final Map<PrimitivPredicate<CT>, SolveSimple<CT>> primitivPredToSolvePredMap =
                new HashMap<PrimitivPredicate<CT>, SolveSimple<CT>>();

        for ( int i = 0 ; i < switchPredicateConvertedSolvePredicateArr.length ; i++ )
        {
            for ( final SolveSimple<CT> solvePred : switchPredicateConvertedSolvePredicateArr[ i ].getPrimitiveSolvePredicates() )
            {
                primitivPredToSolvePredMap.put(
                        solvePred.predicateToSolve ,
                        solvePred );
            }
        }
        for ( final SolveSimple<CT> solvePred : predicateToSwitchConvertedSolvePredicate.getPrimitiveSolvePredicates() )
        {
            primitivPredToSolvePredMap.put(
                    solvePred.predicateToSolve ,
                    solvePred );
        }

        // jetzt noch die includierten Pr�dikate vermerken
        for ( int i = 0 ; i < switchPredicateConvertedSolvePredicateArr.length ; i++ )
        {
            switchPredicateConvertedSolvePredicateArr[ i ].prepareIncludePredicates(
                    jointSimplePredicateSet ,
                    primitivPredToSolvePredMap );
        }
        predicateToSwitchConvertedSolvePredicate.prepareIncludePredicates(
                jointSimplePredicateSet ,
                primitivPredToSolvePredMap );

        // der Scope dieses Cache ist diese Methode, innerhalb dieser Methode tauchen immer wieder die selben Set-Objekte auf
        final ContainsAllCache<CT> containsAllCache = new ContainsAllCache<CT>();

        while ( counter.isOverflow() == false )
        {
            final boolean predicateToSwitchResult =
                    predicateToSwitchConvertedSolvePredicate.solve();

            //System.out.println( "$FileName<.\\src\\de\\heinerkuecker\\constraint\\BruteForceSolver.java> $LineNr: 835: predicateToSwitchResult: " + predicateToSwitchResult );

            if ( predicateToSwitchResult )
            {
                if ( new ExcludeIncludeChecker<CT>(
                        cache.exclude ,
                        //jointSimplePredicateSet ,
                        //counter ,
                        primitivPredToSolvePredMap ,
                        containsAllCache
                        ).isValid() )
                    // dieser Check �ber alle primitiven Pr�dikate ist notwendig, weil kleinere Ausdr�cke erf�llbar sein k�nnen aber gr�ssere aufgrund des exclude unerf�llbar sein k�nnen
                {
                    int countOfSolvedSwitchPredicate = 0;

                    noDisjointSpecialicedSolvePredicateStrList.clear();

                    // Z�hlen der erf�llten Switch-Pr�dikate
                    for ( int i = 0 ; i < switchPredicateConvertedSolvePredicateArr.length ; i++ )
                    {
                        final SolvePredicate<CT> switchPredicateConvertedSolvePredicate =
                                switchPredicateConvertedSolvePredicateArr[ i ];

                        //System.out.println( "$FileName<.\\src\\de\\heinerkuecker\\constraint\\BruteForceSolver.java> $LineNr: 858: " + switchPredicateConvertedSolvePredicate );

                        if ( switchPredicateConvertedSolvePredicate.solve() )
                        {
                            countOfSolvedSwitchPredicate++;

                            noDisjointSpecialicedSolvePredicateStrList.add(
                                    new CasePosPredTpl<CT>(
                                            i ,
                                            switchPredicateConvertedSolvePredicate ) );

                            // das Pr�dikat mit dem aktuellen Index war mindestens einmal erf�llt
                            isNoKontradictionArr[ i ] = true;
                        }
                    }

                    if ( countOfSolvedSwitchPredicate == 0 )
                        // kein Switch-Pr�dikat erf�llt, keine vollst�ndige Abdeckung
                    {
                        isCovering = false;
                    }
                    else if ( countOfSolvedSwitchPredicate > 1 )
                        // mehr als ein Switch-Pr�dikat erf�llt, keine Disjointness
                    {
                        throw new IllegalArgumentException(
                                "switch not disjoint for predicate line nr " + /*predicateToSwitch.lineNr*/predicateToSwitchLineNr + " " +
                                predicateToSwitchConvertedSolvePredicate + " switched for\n" +
                                noDisjointSpecialicedSolvePredicateStrList );
                    }
                }
            }
            counter.skip();
        }

        for ( int i = 0 ; i < isNoKontradictionArr.length ; i++ )
        {
            if ( false == isNoKontradictionArr[ i ] )
            {
                throw new IllegalStateException(
                        codegeneratorClassname + ": " +
                        "switch predicate is kontradiction(not satisfiable) " +
                        casePredicateTupleArr[ i ].casePredicate.toString() + " " +
                        "LineNr " + casePredicateTupleArr[ i ].casePredicate.lineNr + " " +
                        "for predicate " +
                        predicateToSwitch.toString() + " " +
                        "LineNr " + predicateToSwitch.lineNr );
            }
        }

        return isCovering;
    }
    
    /**
     * Tupel-Klasse bestehend aus der Position
     * der Case-Expression im Switch (Basis 0)
     * und dem Case-Solve-Pr�dikat.
     */
    private static final class CasePosPredTpl<CT>
    {
        final int pos;
        final SolvePredicate<CT> predicate;

        /**
         * Konstruktor.
         * @param pos
         * @param predicate
         */
        /*default*/ CasePosPredTpl(
                final int pos ,
                final SolvePredicate<CT> predicate )
        {
            this.pos = pos;
            this.predicate = predicate;
        }

        /**
         * @see java.lang.Object#toString()
         */
        @Override
        public String toString()
        {
            return "CasePosPredTpl[pos=" + this.pos + ", predicate=" + this.predicate + "]\n";
        }
    }

//    /*default*/ static <CT> Predicate<CT> simplifyForExclude_veraltet(
//            final Predicate<CT> predicateToSimplify ,
//            final ConstraintCodeGeneratorCache<CT> cache )
//    {
//        if ( predicateToSimplify.isPrimitive() )
//        {
//            return predicateToSimplify;
//        }
//
//        System.out.println( "BruteForceSolver simplifyForExclude " + predicateToSimplify );
//
//        final Predicate<CT> andSimplifiedPredicate;
//        if ( predicateToSimplify instanceof And )
//        {
//            andSimplifiedPredicate =
//                    AndSimplifier.simplify(
//                            (And<CT>) predicateToSimplify ,
//                            cache );
//        }
//        else
//        {
//            andSimplifiedPredicate =
//                    predicateToSimplify;
//        }
//        
//        final HashSet<PrimitivPredicate<CT>> removableExcludePredicateSet =
//                computeRemovablePredicatesForExclude(
//                        cache.exclude ,
//                        andSimplifiedPredicate );
//
//        final Predicate<CT> simplifiedPredicate1 =
//                andSimplifiedPredicate.replacePrimitivePredicatesWithFalse(
//                //predicateToSimplify.replacePredicatesWithFalse(
//                        removableExcludePredicateSet ,
//                        cache );
//
//        if ( false == BruteForceSolver.isEquivalent(
//                cache ,
//                predicateToSimplify ,
//                simplifiedPredicate1 ) )
//            // check for develop
//        {
//            throw new IllegalStateException(
//                    predicateToSimplify.toString() +
//                    " not equal to " +
//                    simplifiedPredicate1.toString() );
//        }
//
//        if ( false == ( simplifiedPredicate1 instanceof And ) )
//        {
//            return
//                    simplifiedPredicate1;
//        }
//
//        final Predicate<CT> simplifiedPredicate2 =
//                AndNotExcludeSimplifier.simplify(
//                        //exclude ,
//                        cache ,
//                        (And<CT>) simplifiedPredicate1 );
//
//        if ( false == BruteForceSolver.isEquivalent(
//                cache ,
//                predicateToSimplify ,
//                simplifiedPredicate2 ) )
//            // check for develop
//        {
//            throw new IllegalStateException(
//                    predicateToSimplify.toString() +
//                    " not equal to " +
//                    simplifiedPredicate2 );
//        }
//
//        return
//                simplifiedPredicate2;
//    }

    private static <CT> HashSet<PrimitivPredicate<CT>> computeRemovablePredicatesForExclude(
            final Exclude<CT> exclude ,
            final Predicate<CT> predicateToCheck )
    {
        //if ( predicateToCheck.toString().equals( "((not(FallStatusGeschlossen) and ((EingDstFall and (Z2 or ZA2 or Z3 or ZA3)) or R2 or R3)) and FallStatusNeu)" ) )
        //{
        //    System.out.println( "debug break" );
        //}
        final FirstInsertOrderHashset<PrimitivPredicate<CT>> simplePredicateSet =
                new FirstInsertOrderHashset<PrimitivPredicate<CT>>();

        collectSimplePredicates(
                predicateToCheck ,
                simplePredicateSet );

        final BinCounterWithSkipListeners counter =
                new BinCounterWithSkipListeners(
                        simplePredicateSet.size() );

        final SolvePredicate<CT> convertedSolvePredicate =
                PredicateToSolvepredicateConvert.convertToSolvePredicate(
                        predicateToCheck ,
                        counter ,
                        simplePredicateSet );

        // alle in einem Exclude enthaltenen Sub-Simple-Pr�dikate des �bergebenen Pr�dikats einsammeln
        final FirstInsertOrderHashset<PrimitivPredicate<CT>> notNegatedSimplePredicateSet =
                new FirstInsertOrderHashset<PrimitivPredicate<CT>>();

        collectSimplePredicatesOnlyNotNegated(
                predicateToCheck ,
                notNegatedSimplePredicateSet );

        // Set f�r alle in irgendeinem Exclude enthaltenen nicht-negierten Simple-Pr�dikate des �bergebenen Pr�dikats
        final HashSet<PrimitivPredicate<CT>> removableExcludePredicateSet =
                new HashSet<PrimitivPredicate<CT>>();

        for ( final PrimitivPredicate<CT> notNegatedSimplePredicate : notNegatedSimplePredicateSet )
        {
            if ( exclude.contains( notNegatedSimplePredicate ) )
            {
                removableExcludePredicateSet.add(
                        notNegatedSimplePredicate );
            }
        }

        if ( removableExcludePredicateSet.isEmpty() )
            // Abbruch
        {
            return removableExcludePredicateSet;
        }

        final Map<PrimitivPredicate<CT>, SolveSimple<CT>> primitivPredToSolvePredMap =
                new HashMap<PrimitivPredicate<CT>, SolveSimple<CT>>();
        
        for ( final SolveSimple<CT> solvePred : convertedSolvePredicate.getPrimitiveSolvePredicates() )
        {
            primitivPredToSolvePredMap.put(
                    solvePred.predicateToSolve ,
                    solvePred );
        }

        // jetzt noch die includierten Pr�dikate vermerken
        convertedSolvePredicate.prepareIncludePredicates(
                simplePredicateSet ,
                primitivPredToSolvePredMap );

        // der Scope dieses Cache ist diese Methode, innerhalb dieser Methode tauchen immer wieder die selben Set-Objekte auf
        final ContainsAllCache<CT> containsAllCache = new ContainsAllCache<CT>();

        while ( counter.isOverflow() == false )
        {
            //System.out.println( "$FileName<.\\src\\de\\heinerkuecker\\constraint\\BruteForceSolver.java> $LineNr: 1091: " + convertedSolvePredicate );

            //if ( convertedSolvePredicate.toString().equals( "((EingDstFehler[true] and ((FehlerStatusNeuZentrale[true] and (Z2[true] or ZA2[false] or Z3[false] or ZA3[false])) or (FehlerStatusNeuRd[false] and (R2[true] or R3[false])))) and (FehlerStatusNeuZentrale[true] and EbeneZe[true]))" ) )
            //{
            //    System.out.println( "debug break" );
            //}

            final boolean result =
                    convertedSolvePredicate.solve();

            //System.out.println( "$FileName<.\\src\\de\\heinerkuecker\\constraint\\BruteForceSolver.java> $LineNr: 1101: " + result );

            if ( result )
            {
                if ( new ExcludeIncludeChecker<CT>(
                        exclude ,
                        //simplePredicateSet ,
                        //counter ,
                        primitivPredToSolvePredMap ,
                        containsAllCache ).isValid() )
                    // dieser Check �ber alle primitiven Pr�dikate ist notwendig, weil kleinere Ausdr�cke erf�llbar sein k�nnen aber gr�ssere aufgrund des exclude unerf�llbar sein k�nnen
                {
                    // List zum Sammeln aller nicht erf�llten Primitiv-Pr�dikate f�r die aktuelle Belegung (Model)
                    //final ArrayList<Predicate<?>> unsatisfiedPrimitivePredicateCollectList =
                    final ArrayList<PrimitivPredicate<CT>> satisfiedPrimitivePredicateCollectList =
                            new ArrayList<PrimitivPredicate<CT>>(
                                    simplePredicateSet.size() );

                    //convertedSolvePredicate.collectUnsatisfiedPrimitivePredicates(
                    convertedSolvePredicate.collectSatisfiedPrimitivePredicates(
                            //unsatisfiedPrimitivePredicateCollectList
                            satisfiedPrimitivePredicateCollectList );

                    //if ( satisfiedPrimitivePredicateCollectList.toString().contains( "R2" ) )
                    //{
                    //    System.out.println( "debug break" );
                    //}

                    // nur Exclude-Primitiv-Pr�dikate behalten, die bei dieser Belegung (Model) unerf�llt sind
                    // so bleiben am Schluss nur die Exclude-Primitiv-Pr�dikate �brig, die bei allen Belegungen (Models) unerf�llt sind
                    //removableExcludePredicateSet.retainAll( unsatisfiedPrimitivePredicateCollectList );
                    removableExcludePredicateSet.removeAll( satisfiedPrimitivePredicateCollectList );

                    //if ( false == removableExcludePredicateSet.toString().contains( "R2" ) )
                    //{
                    //    System.out.println( "debug break" );
                    //}

                    if ( removableExcludePredicateSet.isEmpty() )
                        // Abbruch
                    {
                        return removableExcludePredicateSet;
                    }
                }
            }
            counter.skip();
        }

        return removableExcludePredicateSet;
    }

    /*default*/ static <CT> Predicate<CT> simplifyForInclude_veraltet(
            final Predicate<CT> predicateToSimplify ,
            final Collection<SwitchablePredicate<CT>> allAndSwitchCasePredicateExpressionColl ,
            //final Exclude<CT> exclude ,
            final ConstraintCodeGeneratorCache<CT> cache )
    {
        if ( predicateToSimplify.isPrimitive() )
        {
            return predicateToSimplify;
        }

        System.out.println( "$FileName<.\\src\\de\\heinerkuecker\\constraint\\BruteForceSolver.java> $LineNr: 1163: simplifyForInclude: " + predicateToSimplify );

        final HashSet</*Primitiv*/Predicate<CT>> removableIncludePredicateSet =
                computeRemovablePredicatesForInclude(
                        cache.exclude ,
                        allAndSwitchCasePredicateExpressionColl ,
                        predicateToSimplify );

        return
                //predicateToSimplify.replacePrimitivePredicatesWithTrue(
                predicateToSimplify.replacePredicatesWithTrue(
                        removableIncludePredicateSet ,
                        //exclude ,
                        cache );
    }

    private static <CT> HashSet</*Primitiv*/Predicate<CT>> computeRemovablePredicatesForInclude(
            final Exclude<CT> exclude ,
            final Collection<SwitchablePredicate<CT>> allAndSwitchCasePredicateExpressionColl ,
            final Predicate<CT> predicateToSimplify )
    {
        //if ( //predicateToSimplify.lineNr >= 1350
        //        predicateToCheck.toString().equals( "((Z2 or ZL2 or Z3 or ZL3) and EbeneZe)" ) )
        //{
        //    System.out.println( "debug break" );
        //}

        final FirstInsertOrderHashset<PrimitivPredicate<CT>> simplePredicateSet =
                new FirstInsertOrderHashset<PrimitivPredicate<CT>>();

        collectSimplePredicates(
                predicateToSimplify ,
                simplePredicateSet );

        final BinCounterWithSkipListeners counter =
                new BinCounterWithSkipListeners(
                        simplePredicateSet.size() );

        final SolvePredicate<CT> convertedSolvePredicate =
                PredicateToSolvepredicateConvert.convertToSolvePredicate(
                        predicateToSimplify ,
                        counter ,
                        simplePredicateSet );

        // alle in einem Exclude enthaltenen Sub-Simple-Pr�dikate des �bergebenen Pr�dikats einsammeln
        final FirstInsertOrderHashset<PrimitivPredicate<CT>> notNegatedSimplePredicateSet =
                new FirstInsertOrderHashset<PrimitivPredicate<CT>>();

        collectSimplePredicatesOnlyNotNegated(
                predicateToSimplify ,
                notNegatedSimplePredicateSet );

        // Set f�r alle in irgendeinem Include enthaltenen nicht-negierten Simple-Pr�dikate des �bergebenen Pr�dikats
        final HashSet</*Primitiv*/Predicate<CT>> removableIncludePredicateSet =
                new HashSet</*Primitiv*/Predicate<CT>>();

        for ( final PrimitivPredicate<CT> notNegatedSimplePredicate : notNegatedSimplePredicateSet )
        {
            innerAddIncludePredicates(
                    notNegatedSimplePredicate ,
                    //allAndSwitchCasePredicateExpressionColl ,
                    notNegatedSimplePredicateSet ,
                    removableIncludePredicateSet );
        }

        // eine And-Verkn�pfung welche enth�lt: eine Oder-Verkn�pfung zweier includierter primitiver Pr�dikate, welche zu einer gemeinsamen ExcludeGroup geh�ren
        // Testfall: ((B2 or Z2 or ZL2 or B3 or Z3 or ZL3) and (EbeneBe or EbeneZe))
        addOnlyIncludePrimitivPredicatesOrForOneCommonExcludeGroupWithinAnd(
                exclude ,
                predicateToSimplify ,
                removableIncludePredicateSet );

        if ( removableIncludePredicateSet.isEmpty() )
            // Abbruch
        {
            return removableIncludePredicateSet;
        }

        final Map<PrimitivPredicate<CT>, SolveSimple<CT>> primitivPredToSolvePredMap =
                new HashMap<PrimitivPredicate<CT>, SolveSimple<CT>>();
        
        for ( final SolveSimple<CT> solvePred : convertedSolvePredicate.getPrimitiveSolvePredicates() )
        {
            primitivPredToSolvePredMap.put(
                    solvePred.predicateToSolve ,
                    solvePred );
        }

        // jetzt noch die includierten Pr�dikate vermerken
        convertedSolvePredicate.prepareIncludePredicates(
                simplePredicateSet ,
                primitivPredToSolvePredMap );

        // der Scope dieses Cache ist diese Methode, innerhalb dieser Methode tauchen immer wieder die selben Set-Objekte auf
        final ContainsAllCache<CT> containsAllCache = new ContainsAllCache<CT>();

        while ( counter.isOverflow() == false )
        {
            final boolean result =
                    convertedSolvePredicate.solve();

            if ( result )
            {
                if ( new ExcludeIncludeChecker<CT>(
                        exclude ,
                        //simplePredicateSet ,
                        //counter ,
                        primitivPredToSolvePredMap ,
                        containsAllCache ).isValid() )
                    // dieser Check �ber alle primitiven Pr�dikate ist notwendig, weil kleinere Ausdr�cke erf�llbar sein k�nnen aber gr�ssere aufgrund des exclude unerf�llbar sein k�nnen
                {
                    // List zum Sammeln aller immer erf�llten Primitiv-Pr�dikate f�r die aktuelle Belegung (Model)
                    //final ArrayList</*Primitiv*/Predicate<CT>> satisfiedPrimitivePredicateCollectList =
                    final ArrayList</*Primitiv*/Predicate<CT>> satisfiedPredicateCollectList =
                            new ArrayList</*Primitiv*/Predicate<CT>>(
                                    simplePredicateSet.size() );

                    //convertedSolvePredicate.collectSatisfiedPrimitivePredicates(
                    convertedSolvePredicate.collectSatisfiedPredicates(
                            //satisfiedPrimitivePredicateCollectList
                            satisfiedPredicateCollectList );

                    // nur Include-Primitiv-Pr�dikate behalten, die bei dieser Belegung (Model) erf�llt sind
                    // so bleiben am Schluss nur die Include-Primitiv-Pr�dikate �brig, die bei allen Belegungen (Models) erf�llt sind
                    removableIncludePredicateSet.retainAll(
                            //satisfiedPrimitivePredicateCollectList
                            satisfiedPredicateCollectList );

                    if ( removableIncludePredicateSet.isEmpty() )
                        // Abbruch
                    {
                        return removableIncludePredicateSet;
                    }
                }
            }
            counter.skip();
        }

        return removableIncludePredicateSet;
    }

    /*default*/ static <CT> void addIncludePredicates(
            final Iterable<PrimitivPredicate<CT>> predicateWithIncludesCollToAdd ,
            //final Collection<SwitchablePredicate<CT>> allAndSwitchCasePredicateExpressionColl ,
            final FirstInsertOrderHashset<PrimitivPredicate<CT>> notNegatedSimplePredicateSet ,
            final HashSet</*Primitiv*/Predicate<CT>> includePredicateSet )
    {
        for ( final PrimitivPredicate<CT> predicateWithIncludesToAdd : predicateWithIncludesCollToAdd)
        {
            innerAddIncludePredicates(
                    predicateWithIncludesToAdd ,
                    //allAndSwitchCasePredicateExpressionColl ,
                    notNegatedSimplePredicateSet ,
                    includePredicateSet );
        }
    }

    private static <CT> void innerAddIncludePredicates(
            final PrimitivPredicate<CT> predicateWithIncludesToAdd ,
            //final Collection<SwitchablePredicate<CT>> allAndSwitchCasePredicateExpressionColl ,
            final FirstInsertOrderHashset<PrimitivPredicate<CT>> notNegatedSimplePredicateSet ,
            final HashSet</*Primitiv*/Predicate<CT>> includePredicateSet )
    {
        if ( predicateWithIncludesToAdd instanceof ParametricalPredicate )
        {
            final ParametricalPredicate<CT> parametricalPredicateWithIncludesToAdd = (ParametricalPredicate<CT>) predicateWithIncludesToAdd;

            //for ( final SwitchablePredicate<CT> switchablePredicate : allAndSwitchCasePredicateExpressionColl )
            //{
            //    if ( parametricalPredicateWithIncludesToAdd.isCompatibleTo( switchablePredicate.predicate ) )
            //    {
            //        includePredicateSet.add( switchablePredicate.predicate );
            //    }
            //}
            for ( final PrimitivPredicate<CT> notNegatedSimplePredicate : notNegatedSimplePredicateSet )
            {
                if (
                        // nicht include mit sich selbst feststellen, weil sonst stur alle Pr�dikate removable werden
                        false == ( parametricalPredicateWithIncludesToAdd.equals( notNegatedSimplePredicate ) ) &&
                        parametricalPredicateWithIncludesToAdd.isCompatibleTo( notNegatedSimplePredicate ) )
                {
                    includePredicateSet.add( notNegatedSimplePredicate );
                }
            }
        }
        if ( predicateWithIncludesToAdd.isPrimitive() &&
                ( (PrimitivPredicate<CT>) predicateWithIncludesToAdd ).includePredicateSet != null )
        {
            for ( final PrimitivPredicate<CT> includePredicate : predicateWithIncludesToAdd.includePredicateSet )
            {
                includePredicateSet.add( includePredicate );

                // rekursiv auch indirekt includierte Pr�dikate vermerken
                innerAddIncludePredicates(
                        includePredicate ,
                        //allAndSwitchCasePredicateExpressionColl ,
                        notNegatedSimplePredicateSet ,
                        includePredicateSet );
            }
        }
    }

    /**
     * Methode zum Behandeln einer And-Verkn�pfung welche enth�lt:
     * eine Oder-Verkn�pfung zweier/mehrerer includierter primitiver Pr�dikate, welche zu einer gemeinsamen ExcludeGroup geh�ren
     * <pre>
     * Testfall:             ((B2 or Z2 or ZL2 or B3 or Z3 or ZL3) and (EbeneBe or EbeneZe))
     * gew�nschtes Ergebnis: ((B2 or Z2 or ZL2 or B3 or Z3 or ZL3)), weil die Sub-Expression "and (EbeneBe or EbeneZe)" bei jeder erf�llten Belegung sowieso erf�llt ist
     * </pre>
     *
     * @param exclude
     * @param predicateToSimplify
     * @param removableIncludePredicateSet
     */
    private static <CT> void addOnlyIncludePrimitivPredicatesOrForOneCommonExcludeGroupWithinAnd(
            final Exclude<CT> exclude ,
            final Predicate<CT> predicateToSimplify ,
            final HashSet</*Primitiv*/Predicate<CT>> removableIncludePredicateSet )
    {
        if ( false == predicateToSimplify instanceof And )
        {
            return;
        }

        final And<CT> andPredicateToSimplify = (And<CT>) predicateToSimplify;

        for ( final Predicate<CT> subPredicate : andPredicateToSimplify )
        {
            if ( false == subPredicate instanceof Or )
            {
                continue;
            }

            final Or<CT> orSubPredicate = (Or<CT>) subPredicate;

            if ( false == isAllSubPredicatesPrimitiv( orSubPredicate ) )
            {
                continue;
            }

            if ( false == isAllInOneExcludeGroup(
                    exclude ,
                    orSubPredicate ) )
            {
                continue;
            }

            if ( removableIncludePredicateSet.containsAll( IteratorUtil.toList( orSubPredicate ) ) )
            {
                removableIncludePredicateSet.add( orSubPredicate );
            }
        }
    }

    private static <CT> void collectSimplePredicatesOnlyNotNegated(
            final Predicate<CT> predicateToConvert ,
            final FirstInsertOrderHashset<PrimitivPredicate<CT>> simplePredicateSet )
    {
        if ( predicateToConvert instanceof Xor )
        {
            throw new IllegalArgumentException(
                    "convert Xor to Not And predicates before calling this method" );
        }

        if ( predicateToConvert instanceof PrimitivPredicate )
        {
            simplePredicateSet.add(
                    (PrimitivPredicate<CT>) predicateToConvert );
        }
        else if ( predicateToConvert instanceof And )
        {
            collectSimplePredicatesOnlyNotNegatedAnd(
                    (And<CT>) predicateToConvert ,
                    simplePredicateSet );
        }
        else if ( predicateToConvert instanceof Or )
        {
            collectSimplePredicatesOnlyNotNegatedOr(
                    (Or<CT>) predicateToConvert ,
                    simplePredicateSet );
        }
        else if ( predicateToConvert instanceof Not )
        {
            // das komplette auskommentieren ist evtl. nicht korrekt, weil in der Negation wieder eine Negation stecken k�nnte (doppelte Negation == keine Negation)
            //collectSimplePredicatesNot(
            //        (Not<CT>) predicateToConvert ,
            //        simplePredicateSet );
        }
        else
        {
            throw new UnreachableCodeException(
                    String.valueOf( predicateToConvert ) );
        }
    }

    private static <CT> void collectSimplePredicatesOnlyNotNegatedAnd(
            final And<CT> andPredicateToConvert ,
            final FirstInsertOrderHashset<PrimitivPredicate<CT>> simplePredicateSet )
    {
        for ( Predicate<CT> andSubPredicate : andPredicateToConvert )
        {
            collectSimplePredicatesOnlyNotNegated(
                    andSubPredicate ,
                    simplePredicateSet );
        }
    }

    private static <CT> void collectSimplePredicatesOnlyNotNegatedOr(
            final Or<CT> orPredicateToConvert ,
            final FirstInsertOrderHashset<PrimitivPredicate<CT>> simplePredicateSet )
    {
        for ( Predicate<CT> orSubPredicate : orPredicateToConvert )
        {
            collectSimplePredicatesOnlyNotNegated(
                    orSubPredicate ,
                    simplePredicateSet );
        }
    }

    private static <CT> void collectSimplePredicatesNot(
            final Not<CT> notPredicateToConvert ,
            final FirstInsertOrderHashset<PrimitivPredicate<CT>> simplePredicateSet )
    {
        collectSimplePredicates(
                notPredicateToConvert.get() ,
                simplePredicateSet );
    }

    /*default*/ static <CT> void collectSimplePredicates(
            final Predicate<CT> predicateToConvert ,
            final FirstInsertOrderHashset<PrimitivPredicate<CT>> simplePredicateSet )
    {
        if ( predicateToConvert instanceof Xor )
        {
            throw new IllegalArgumentException(
                    "convert Xor to Not And predicates before calling this method" );
        }

        if ( predicateToConvert instanceof PrimitivPredicate )
        {
            simplePredicateSet.add(
                    (PrimitivPredicate<CT>) predicateToConvert );
        }
        else if ( predicateToConvert instanceof And )
        {
            collectSimplePredicatesAnd(
                    (And<CT>) predicateToConvert ,
                    simplePredicateSet );
        }
        else if ( predicateToConvert instanceof Or )
        {
            collectSimplePredicatesOr(
                    (Or<CT>) predicateToConvert ,
                    simplePredicateSet );
        }
        else if ( predicateToConvert instanceof Not )
        {
            collectSimplePredicatesNot(
                    (Not<CT>) predicateToConvert ,
                    simplePredicateSet );
        }
        else
        {
            throw new UnreachableCodeException(
                    String.valueOf( predicateToConvert ) );
        }
    }

    private static <CT> void collectSimplePredicatesAnd(
            final And<CT> andPredicateToConvert ,
            final FirstInsertOrderHashset<PrimitivPredicate<CT>> simplePredicateSet )
    {
        for ( Predicate<CT> andSubPredicate : andPredicateToConvert )
        {
            collectSimplePredicates(
                    andSubPredicate ,
                    simplePredicateSet );
        }
    }

    private static <CT> void collectSimplePredicatesOr(
            final Or<CT> orPredicateToConvert ,
            final FirstInsertOrderHashset<PrimitivPredicate<CT>> simplePredicateSet )
    {
        for ( Predicate<CT> orSubPredicate : orPredicateToConvert )
        {
            collectSimplePredicates(
                    orSubPredicate ,
                    simplePredicateSet );
        }
    }

    ///**
    // * Pr�fen, ob die beiden �bergebenen Expressions
    // * keine gemeinsamen Pr�dikate haben.
    // *
    // * @param set1
    // * @param set2
    // * @return
    // */
    //private static <CT> boolean isDisjoint_unused(
    //        final Collection<SwitchablePredicate<CT>> allAndSwitchCasePredicateExpressionColl ,
    //        final HashSet<PrimitivPredicate<CT>> set1 ,
    //        final HashSet<PrimitivPredicate<CT>> set2 )
    //{
    //	// includierte (implizierte) Pr�dikate mit beachten
    //	final HashSet</*Primitiv*/Predicate<CT>> includePredicatesAddedSet1 = new HashSet</*Primitiv*/Predicate<CT>>( set1 );
    //	final HashSet</*Primitiv*/Predicate<CT>> includePredicatesAddedSet2 = new HashSet</*Primitiv*/Predicate<CT>>( set2 );
    //
    //	BruteForceSolver.addIncludePredicates(
    //	        set1 ,
    //	        //allAndSwitchCasePredicateExpressionColl ,
    //	        null , // notNegatedSimplePredicateSet
    //	        includePredicatesAddedSet1 );
    //
    //	BruteForceSolver.addIncludePredicates(
    //	        set2 ,
    //	        //allAndSwitchCasePredicateExpressionColl ,
    //            null , // notNegatedSimplePredicateSet
    //	        includePredicatesAddedSet2 );
    //
    //    final HashSet</*Primitiv*/Predicate<CT>> lesserSet;
    //    final HashSet</*Primitiv*/Predicate<CT>> greaterSet;
    //    if ( includePredicatesAddedSet1.size() < includePredicatesAddedSet2.size() )
    //    {
    //        lesserSet = includePredicatesAddedSet1;
    //        greaterSet = includePredicatesAddedSet2;
    //    }
    //    else
    //    {
    //        lesserSet = includePredicatesAddedSet2;
    //        greaterSet = includePredicatesAddedSet1;
    //    }
    //
    //    for ( final Object elemToCheck : lesserSet )
    //    {
    //        if ( greaterSet.contains( elemToCheck ) )
    //        {
    //            return false;
    //        }
    //    }
    //
    //    return true;
    //}

    private static <CT> boolean isAllSubPredicatesPrimitiv(
            final Iterable<Predicate<CT>> predicateToCheck )
    {
        for ( final Predicate<CT> predicate : predicateToCheck )
        {
            if ( false == predicate.isPrimitive() )
            {
                return false;
            }
        }
        return true;
    }


    private static <CT> boolean isAllInOneExcludeGroup(
            final Exclude<CT> exclude ,
            final Iterable<Predicate<CT>> predicateToCheckIterable )
    {
        final Iterator<Predicate<CT>> iterator = predicateToCheckIterable.iterator();

        final Predicate<CT> firstPredicate = iterator.next();

        if ( false == ( firstPredicate instanceof PrimitivPredicate ) )
        {
            return false;
        }

        final ReadOnlySet<ExcludeGroup<CT>> firstExcludeGroupSet =
                exclude.getExcludeGroupSet(
                        (PrimitivPredicate<CT>) firstPredicate );

        if ( firstExcludeGroupSet == null ||
                firstExcludeGroupSet.isEmpty() )
        {
            return false;
        }

        while( iterator.hasNext() )
        {
            final Predicate<CT> nextPredicate = iterator.next();

            if ( false == ( nextPredicate instanceof PrimitivPredicate ) )
            {
                return false;
            }

            final ReadOnlySet<ExcludeGroup<CT>> nextExcludeGroupSet =
                    exclude.getExcludeGroupSet(
                            (PrimitivPredicate<CT>) nextPredicate );

            if ( false == firstExcludeGroupSet.containsMinOneOf( 
                    nextExcludeGroupSet ) )
            {
                return false;
            }
        }

        return true;
    }

}