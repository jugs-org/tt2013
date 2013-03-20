package de.heinerkuecker.constraint;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import de.heinerkuecker.constraint.util.exc.UnreachableCodeException;

/**
 * Normalisierer von Prädikat-Ausdücken.
 * 
 * Das Normalisieren dient zu Feststellung 
 * der Kompatibilität von Prädikat-Ausdücken.
 * 
 * @author Heiner K&uuml;cker
 */
public final class Normalizer
{
    private static HashMap<Predicate<?>, Predicate<?>> normalizedPredicateCache =
        new HashMap<Predicate<?>, Predicate<?>>();
        
    /**
     * Normalisierungs-Methode,
     * öffentlicher Einstieg.
     * 
     * @param <CT> Type-Parameter Kontext-Objekt-Typ
     * @param predicateToNormalize zu normalisierender Prädikat-Ausdruck
     * @return normalisierter Prädikat-Ausdruck
     */
    public static <CT> Predicate<CT> normalize(
            final Predicate<CT> predicateToNormalize ,
            //final Exclude<CT> exclude ,
            final ConstraintCodeGeneratorCache<CT> cache )
    {
        if ( normalizedPredicateCache.containsKey( predicateToNormalize ) )
        {
            return (Predicate<CT>) normalizedPredicateCache.get( predicateToNormalize );
        }

        //System.out.println( "$FileName<.\\src\\de\\heinerkuecker\\constraint\\Normalizer.java> $LineNr: 43: " + predicateToNormalize + " Line-Number " + predicateToNormalize.lineNr );
        
        // Set zum Vermerken der Zwischen-Schritte, weil beim Entwickeln ein endloses hin- und her-Normalisieren zwischen zwei Term-Varianten aufgetreten ist
        final Set<Predicate<CT>> prePredicateSet =
            new HashSet<Predicate<CT>>();
        
        Predicate<CT> currentPredicateToNormalize =
            predicateToNormalize;
        
        //final Collection<Predicate<CT>> allPrimitivePredicateColl =
        //        collectAllPrimitivePredicate(
        //                currentPredicateToNormalize );
        
        //currentPredicateToNormalize =
        //        addExcludeXor(
        //                currentPredicateToNormalize ,
        //                exclude ,
        //                allPrimitivePredicateColl );
        
        currentPredicateToNormalize =
                XorConverter.convertXor( 
                        currentPredicateToNormalize ,
                        cache );

        prePredicateSet.add(
                currentPredicateToNormalize );
        
        while ( deep( currentPredicateToNormalize ) > 2
                // auf dem obersten normalisierten Level soll ein Oder-Prädikat stehen
                || ( false == ( currentPredicateToNormalize instanceof Or ) ) )
        {
            System.out.println( "$FileName<.\\src\\de\\heinerkuecker\\constraint\\Normalizer.java> $LineNr: 74: " + currentPredicateToNormalize );
            currentPredicateToNormalize =
                    currentPredicateToNormalize;

            //currentPredicateToNormalize =
            //        simplifyForInclude( 
            //                currentPredicateToNormalize ,
            //                exclude );
            //
            //currentPredicateToNormalize =
            //        melt( 
            //                currentPredicateToNormalize
            //                //exclude
            //                );

            //currentPredicateToNormalize =
            //        simplifyForExclude( 
            //                currentPredicateToNormalize ,
            //                exclude );
            //
            //currentPredicateToNormalize =
            //        melt( 
            //                currentPredicateToNormalize
            //                //exclude
            //                );

            currentPredicateToNormalize =
                multiplyOut( 
                        currentPredicateToNormalize ,
                        //exclude ,
                        cache );

            currentPredicateToNormalize =
                removeTautKontra( 
                        currentPredicateToNormalize ,
                        //exclude ,
                        cache );
            
            if ( prePredicateSet.contains( currentPredicateToNormalize ) )
                // keine Veränderung zu erreichen
            {
                break;
            }
            
            prePredicateSet.add( 
                currentPredicateToNormalize );
        }
        
        normalizedPredicateCache.put( 
                predicateToNormalize ,
                currentPredicateToNormalize );
        
        return currentPredicateToNormalize;
    }

    /**
     * Methode zum Messen der Baum-Tiefe
     * des übergebenen Ausdrucks.
     * 
     * @param predicateToNormalize zu messender Ausdruck
     * @return Baum-Tiefe als int-Wert
     */
    private static int deep(
            final Predicate<?> predicateToNormalize )
    {
        final int deep = 
            innerDeep( 
                predicateToNormalize );

        return deep;
    }

    /**
     * Innere rekursive Methode zum 
     * Messen der Baum-Tiefe des 
     * übergebenen Ausdrucks.
     * 
     * @param predicateToNormalize zu messender Ausdruck
     * @return Baum-Tiefe als int-Wert
     */
    private static int innerDeep(
            final Predicate<?> predicateToNormalize )
    {
        if ( predicateToNormalize instanceof And )
        {
            return innerDeepAnd(
                    (And<?>) predicateToNormalize );
        }
        else if ( predicateToNormalize instanceof Or )
        {
            return innerDeepOr(
                    (Or<?>) predicateToNormalize );
        }
        else if ( predicateToNormalize instanceof Not )
        {
            return innerDeepNot( 
                    (Not<?>) predicateToNormalize );
        }
        else if ( predicateToNormalize instanceof Xor )
        {
            throw new IllegalArgumentException(
                    "xor not converted" );
        }
        else
        {
            return 0;
        }
    }

    /**
     * Innere rekursive Methode zum 
     * Messen der Baum-Tiefe des 
     * übergebenen Ausdrucks.
     * 
     * @param andPredicateToNormalize zu messender And-Ausdruck
     * @return Baum-Tiefe als int-Wert
     */
    private static int innerDeepAnd(
            final And<?> andPredicateToNormalize )
    {
        int maxDeep = 0;
        for ( final Predicate<?> andPartPredicateExpression : andPredicateToNormalize )
        {
            maxDeep =
                Math.max( 
                        maxDeep , 
                        innerDeep(
                                andPartPredicateExpression ) );
        }
        
        return maxDeep + 1;
    }
    
    /**
     * Innere rekursive Methode zum 
     * Messen der Baum-Tiefe des 
     * übergebenen Ausdrucks.
     * 
     * @param orPredicateToNormalize zu messender Or-Ausdruck
     * @return Baum-Tiefe als int-Wert
     */
    private static int innerDeepOr(
            final Or<?> orPredicateToNormalize )
    {
        int maxDeep = 0;
        for ( final Predicate<?> orPartPredicateExpression : orPredicateToNormalize )
        {
            maxDeep =
                Math.max( 
                        maxDeep , 
                        innerDeep(
                                orPartPredicateExpression ) );
        }
        
        return maxDeep + 1;
    }

    /**
     * Innere rekursive Methode zum 
     * Messen der Baum-Tiefe des 
     * übergebenen Ausdrucks.
     * 
     * @param notPredicateToNormalize zu messender Not-Ausdruck
     * @return Baum-Tiefe als int-Wert
     */
    private static int innerDeepNot(
            final Not<?> notPredicateToNormalize )
    {
        return 
            innerDeep(
                notPredicateToNormalize.get() ) + 
            //1
            0;
    }

    private static <CT> Predicate<CT> multiplyOut(
            final Predicate<CT> predicateToNormalize ,
            //final Exclude<CT> exclude ,
            final ConstraintCodeGeneratorCache<CT> cache )
    {
        System.out.println( "$FileName<.\\src\\de\\heinerkuecker\\constraint\\Normalizer.java> $LineNr: 254: " + predicateToNormalize );
        if ( predicateToNormalize instanceof And )
        {
            return multiplyOutAnd(
                    (And<CT>) predicateToNormalize ,
                    //exclude ,
                    cache );
        }
        else if ( predicateToNormalize instanceof Or )
        {
            return multiplyOutOr(
                    (Or<CT>) predicateToNormalize ,
                    //exclude ,
                    cache );
        }
        else if ( predicateToNormalize instanceof Not )
        {
            return multiplyOutNot(
                    (Not<CT>) predicateToNormalize ,
                    //exclude ,
                    cache );
        }
        else
        {
            return predicateToNormalize;
        }
    }

    private static <CT> Predicate<CT> multiplyOutAnd(
            final And<CT> andPredicateToNormalize ,
            //final Exclude<CT> exclude ,
            final ConstraintCodeGeneratorCache<CT> cache )
    {
        System.out.println( "$FileName<.\\src\\de\\heinerkuecker\\constraint\\Normalizer.java> $LineNr: 287: " + andPredicateToNormalize );

        final int indexOfOrPredicate =
            indexOfOrPredicate( 
                    andPredicateToNormalize );

        if ( indexOfOrPredicate >= 0 )
        {
            return multiplyOutAnd(
                    andPredicateToNormalize ,
                    indexOfOrPredicate ,
                    //exclude ,
                    cache );

        }
        else
        {
            return andPredicateToNormalize;
        }
    }
    
    private static <CT> /*Or*/Predicate<CT> multiplyOutAnd(
            final And<CT> andPredicateToNormalize ,
            final int indexOfOrPredicate ,
            //final Exclude<CT> exclude ,
            final ConstraintCodeGeneratorCache<CT> cache )
    {
        //System.out.println( "$FileName<.\\src\\de\\heinerkuecker\\constraint\\Normalizer.java> $LineNr: 314: " + andPredicateToNormalize );
        /* Zählen der Anzahl Operanden (Sub-Expressions)
         * je untergeordnetem Or-Zweig
         */
        final int[] orPredicateSizeArr =
            new int[ andPredicateToNormalize.size() ];
        
        int orPredicateSizeArrIndex = 0;
        
        for ( final Predicate<CT> andPartPredicateExpression : andPredicateToNormalize )
        {
            if ( andPartPredicateExpression instanceof And )
                // das sollte hier nicht auftauchen
            {
                throw new IllegalArgumentException(
                        "predicate not melted" );
            }
            else if ( andPartPredicateExpression instanceof Or )
            {
                orPredicateSizeArr[ orPredicateSizeArrIndex++ ] =
                        ( (Or<CT>) andPartPredicateExpression ).size();
            }
            else
             // atomares Prädikat
            {
                orPredicateSizeArr[ orPredicateSizeArrIndex++ ] = 1;
            }
        }

        /*
         * Ablaufen Zähler mit den einzelnen
         * untergeordneten Or-Expressions
         * zum Aufrollen der Klammern
         */
        final MultiCellCounter counter =
            new MultiCellCounter(
                    orPredicateSizeArr );
        
        final List</*And*/Predicate<CT>> orList =
            new ArrayList</*And*/Predicate<CT>>();

        while ( counter.hasNext() )
        {
            //System.out.println( "$FileName<.\\src\\de\\heinerkuecker\\constraint\\Normalizer.java> $LineNr: 357" );
            final int[] orPredicateIndexArr =
                counter.next();
            
            final List<Predicate<CT>> andParamList =
                new ArrayList<Predicate<CT>>();

            for ( int subAndPredicateIndexArrIndex = 0 ; subAndPredicateIndexArrIndex < orPredicateIndexArr.length ; subAndPredicateIndexArrIndex++ )
            {
                final int orPredicateIndex = 
                    orPredicateIndexArr[ subAndPredicateIndexArrIndex ];
                
                final Predicate<CT> subAndPredicate = 
                    andPredicateToNormalize.get( 
                            subAndPredicateIndexArrIndex );
                
                if ( subAndPredicate instanceof Or )
                {
                    final Predicate<CT> subOrPredicate = 
                        ( (Or<CT>) subAndPredicate ).get( 
                                orPredicateIndex );

                    andParamList.add(
                            subOrPredicate );
                }
                else
                    // atomares Prädikat
                {
                    if ( orPredicateIndex != 0 )
                    {
                        throw new IllegalStateException();
                    }

                    andParamList.add(
                            subAndPredicate );
                }
            }
            final /*And*/Predicate<CT> newAndPredicate = 
                    //new And<CT>(
                    And.instanceOf(
                            //// lineNr ist hier nicht wichtig, nur bei Prädikat-Expressions, welche durch den Benutzer angelegt wurden
                            //0 ,
                            andPredicateToNormalize.lineNr ,
                            //exclude ,
                            cache ,
                            //cleanForImplicits
                            //true ,
                            andParamList.toArray( 
                                    (Predicate<CT>[]) new Predicate<?>[ andParamList.size() ] ) );

            if ( false == isKontra( 
                    newAndPredicate ) )
            {
                orList.add(
                        newAndPredicate );
            }
        }

        return 
                //new Or<CT>(
                Or.instanceOf(
                        // lineNr ist hier nicht wichtig, nur bei Prädikat-Expressions, welche durch den Benutzer angelegt wurden
                        0 ,
                        cache ,
                        orList.toArray( 
                                (Predicate<CT>[]) new Predicate<?>[ orList.size() ] ) );
    }
    
    /**
     * TODO diese Methode kann entfallen,
     * {@link Or} hat eine get(int index)-Methode.
     * 
     * @param <CT>
     * @param andPredicateToNormalize
     * @param indexOfOrPredicate
     * @return
     */
//    private static <CT> Or<CT> getOrPredicateForIndex(
//            final And<CT> andPredicateToNormalize ,
//            final int indexOfOrPredicate )
//    {
//        int i = 0;
//        for ( final Predicate<CT> andPartPredicateExpression : andPredicateToNormalize )
//        {
//            if ( i == indexOfOrPredicate )
//            {
//                return (Or<CT>) andPartPredicateExpression;
//            }
//            i++;
//        }
//        
//        throw new NoSuchElementException();
//    }

    private static <CT> int indexOfOrPredicate(
            final And<CT> andPredicateToNormalize )
    {
        int i = 0;
        for ( final Predicate<CT> andPartPredicateExpression : andPredicateToNormalize )
        {
            if ( andPartPredicateExpression instanceof Or )
            {
                return i;
            }
            i++;
        }
        
        return -1;
    }

    private static <CT> Predicate<CT> multiplyOutOr(
            final Or<CT> orPredicateToNormalize ,
            //final Exclude<CT> exclude ,
            final ConstraintCodeGeneratorCache<CT> cache )
    {
        System.out.println( "$FileName<.\\src\\de\\heinerkuecker\\constraint\\Normalizer.java> $LineNr: 472: " + orPredicateToNormalize );
        final int indexOfAndPredicate =
            indexOfAndPredicate( 
                    orPredicateToNormalize );

        if ( indexOfAndPredicate >= 0 )
        {
            return multiplyOutOr(
                    orPredicateToNormalize ,
                    indexOfAndPredicate ,
                    //exclude ,
                    cache );
        }
        else
        {
            return orPredicateToNormalize;
        }
    }
    
    private static <CT> /*And*/Predicate<CT> multiplyOutOr(
            final Or<CT> orPredicateToNormalize ,
            final int indexOfAndPredicate ,
            //final Exclude<CT> exclude ,
            final ConstraintCodeGeneratorCache<CT> cache )
    {
        /*
         * Zählen der Anzahl Operanden (Sub-Expressions)
         * je untergeordnetem And-Zweig
         */
        final int[] andPredicateSizeArr =
            new int[ orPredicateToNormalize.size() ];
        
        int andPredicateSizeArrIndex = 0;
        
        for ( final Predicate<CT> orPartPredicateExpression : orPredicateToNormalize )
        {
            if ( orPartPredicateExpression instanceof Or )
                // das sollte hier nicht auftauchen
            {
                throw new IllegalArgumentException(
                        "predicate not melted" );
            }
            else if ( orPartPredicateExpression instanceof And )
            {
                andPredicateSizeArr[ andPredicateSizeArrIndex++ ] =
                        ( (And<CT>) orPartPredicateExpression ).size();
            }
            else
             // atomares Prädikat
            {
                andPredicateSizeArr[ andPredicateSizeArrIndex++ ] = 1;
            }
        }
        
        /*
         * Ablaufen Zähler mit den einzelnen
         * untergeordneten And-Expressions
         * zum Aufrollen der Klammern
         */
        final MultiCellCounter counter =
            new MultiCellCounter(
                    andPredicateSizeArr );
        
        final List</*Or*/Predicate<CT>> andList =
            new ArrayList</*Or*/Predicate<CT>>();

        while ( counter.hasNext() )
        {
            final int[] andPredicateIndexArr =
                counter.next();
            
            final List<Predicate<CT>> orParamList =
                new ArrayList<Predicate<CT>>();

            for ( int subOrPredicateIndexArrIndex = 0 ; subOrPredicateIndexArrIndex < andPredicateIndexArr.length ; subOrPredicateIndexArrIndex++ )
            {
                final int orPredicateIndex = 
                    andPredicateIndexArr[ subOrPredicateIndexArrIndex ];
                
                final Predicate<CT> subOrPredicate = 
                    orPredicateToNormalize.get( 
                            subOrPredicateIndexArrIndex );
                
                if ( subOrPredicate instanceof And )
                {
                    final Predicate<CT> subAndPredicate = 
                        ( (And<CT>) subOrPredicate ).get( 
                                orPredicateIndex );

                    orParamList.add(
                            subAndPredicate );
                }
                else
                    // atomares Prädikat
                {
                    if ( orPredicateIndex != 0 )
                    {
                        throw new IllegalStateException();
                    }

                    orParamList.add(
                            subOrPredicate );
                }
            }
            final /*Or*/Predicate<CT> newOrPredicate = 
                    //new Or<CT>(
                    Or.instanceOf(
                            orPredicateToNormalize.lineNr ,
                            cache ,
                            orParamList.toArray( 
                                    (Predicate<CT>[]) new Predicate<?>[ orParamList.size() ] ) );
            
            if ( false == isTaut( newOrPredicate ) )
            {
                andList.add(
                        newOrPredicate );
            }
        }
        
        return 
                //new And<CT>(
                And.instanceOf(
                        orPredicateToNormalize.lineNr ,
                        //exclude ,
                        cache ,
                        //cleanForImplicits
                        //true ,
                        andList.toArray( 
                                (Predicate<CT>[]) new Predicate[ andList.size() ] ) );
    }

    /**
     * TODO diese Methode kann entfallen,
     * {@link And} hat eine get(int index)-Methode.
     * 
     * @param <CT>
     * @param orPredicateToNormalize
     * @param indexOfAndPredicate
     * @return
     */
//    private static <CT> And<CT> getAndPredicateForIndex(
//            final Or<CT> orPredicateToNormalize ,
//            final int indexOfAndPredicate )
//    {
//        int i = 0;
//        for ( final Predicate<CT> orPartPredicateExpression : orPredicateToNormalize )
//        {
//            if ( i == indexOfAndPredicate )
//            {
//                return (And<CT>) orPartPredicateExpression;
//            }
//            i++;
//        }
//        
//        throw new NoSuchElementException();
//    }

    private static <CT> int indexOfAndPredicate(
            final Or<CT> orPredicateToNormalize )
    {
        int i = 0;
        for ( final Predicate<CT> orPartPredicateExpression : orPredicateToNormalize )
        {
            if ( orPartPredicateExpression instanceof And )
            {
                return i;
            }
            i++;
        }
        
        return -1;
    }

    private static <CT> Predicate<CT> multiplyOutNot(
            final Not<CT> notPredicateToNormalize ,
            //final Exclude<CT> exclude ,
            final ConstraintCodeGeneratorCache<CT> cache )
    {
        System.out.println( "$FileName<.\\src\\de\\heinerkuecker\\constraint\\Normalizer.java> $LineNr: 650: " + notPredicateToNormalize );
        if ( notPredicateToNormalize.get() instanceof Not )
            // das sollte hier nicht auftauchen
        {
            throw new IllegalArgumentException(
                    "predicate not melted" );
        }
        else if ( notPredicateToNormalize.get() instanceof And )
        {
            final And<CT> andPredicate =
                (And<CT>) notPredicateToNormalize.get();

            final List<Predicate<CT>> notList =
                new ArrayList<Predicate<CT>>();

            for ( Predicate<CT> subAndPredicate : andPredicate )
            {
                notList.add(
                        Not.instanceOf(
                                // lineNr ist hier nicht wichtig, nur bei Prädikat-Expressions, welche durch den Benutzer angelegt wurden
                                0 ,
                                subAndPredicate ) );
            }
            
            return 
                    //new Or<CT>(
                    Or.instanceOf(
                            // lineNr ist hier nicht wichtig, nur bei Prädikat-Expressions, welche durch den Benutzer angelegt wurden
                            0 ,
                            cache ,
                            notList.toArray( 
                                    (Predicate<CT>[]) new Predicate[ notList.size() ] ) );
        }
        else if ( notPredicateToNormalize.get() instanceof Or )
        {
            final Or<CT> orPredicate =
                (Or<CT>) notPredicateToNormalize.get();

            final List<Predicate<CT>> notList =
                new ArrayList<Predicate<CT>>();

            for ( Predicate<CT> subOrPredicate : orPredicate )
            {
                notList.add(
                        Not.instanceOf(
                                // lineNr ist hier nicht wichtig, nur bei Prädikat-Expressions, welche durch den Benutzer angelegt wurden
                                0 ,
                                subOrPredicate ) );
            }
            
            return 
                    //new And<CT>(
                    And.instanceOf(
                            //// lineNr ist hier nicht wichtig, nur bei Prädikat-Expressions, welche durch den Benutzer angelegt wurden
                            //0 ,
                            notPredicateToNormalize.lineNr ,
                            //exclude ,
                            cache ,
                            //cleanForImplicits
                            //true ,
                            notList.toArray( 
                                    (Predicate<CT>[]) new Predicate[ notList.size() ] ) );
        }
        
        // nix zu verbessern
        return notPredicateToNormalize;
    }
    
    /**
     * Aufschmelzen von Expressions
     * ((A and B)and(C and D)) wird zu (A and B and C and D)
     * 
     * @param <CT>
     * @param predicateToMelt
     * @return
     * @deprecated nur im Konstruktor von Or und And verwenden
     */
    /*default*/ static <CT> Predicate<CT> melt(
            final Predicate<CT> predicateToMelt ,
            //final Exclude<CT> exclude ,
            final ConstraintCodeGeneratorCache<CT> cache )
    {
        //System.out.println( "$FileName<.\\src\\de\\heinerkuecker\\constraint\\Normalizer.java> $LineNr: 732: melt " + predicateToMelt );
        
        if ( predicateToMelt instanceof Xor )
        {
            // einschmelzen (absobieren) für XoR nicht implementiert
            return predicateToMelt;
        }

        Predicate<CT> prePredicateToMelt = predicateToMelt;
        
        while ( true )
        {
            final Predicate<CT> currentPredicateToMelt;
            if ( prePredicateToMelt instanceof And )
            {
                
                final And<CT> andPredicateToMelt = 
                    (And<CT>) prePredicateToMelt;

                if ( andPredicateToMelt.size() == 1 )
                    // nur ein Sub-Prädikat enthalten
                {
                    currentPredicateToMelt =
                        andPredicateToMelt.get( 0 );
                }
                else
                {
                    currentPredicateToMelt =
                        meltAnd(
                            andPredicateToMelt ,
                            //exclude ,
                            cache );
                }
            }
            else if ( prePredicateToMelt instanceof Or )
            {
                final Or<CT> orPredicateToMelt = 
                    (Or<CT>) prePredicateToMelt;

                if ( orPredicateToMelt.size() == 1 )
                    // nur ein Sub-Prädikat enthalten
                {
                    currentPredicateToMelt =
                        orPredicateToMelt.get( 0 );
                }
                else
                {
                    currentPredicateToMelt =
                        meltOr(
                            orPredicateToMelt ,
                            //exclude ,
                            cache );
                }
            }
            else if ( prePredicateToMelt instanceof Not )
            {
                final Not<CT> notPredicateToMelt = (Not<CT>) prePredicateToMelt;
                if ( notPredicateToMelt.get() instanceof Not )
                    // doppelte Negation
                {
                    currentPredicateToMelt =
                        ( (Not<CT>) notPredicateToMelt.get() ).get();
                }
                else
                {
                    return prePredicateToMelt;
                }
            }
            else
            {
                return prePredicateToMelt;
            }
            
            if ( currentPredicateToMelt.equals( prePredicateToMelt ) )
                // keine Verbesserung mehr erreicht
            {
                return currentPredicateToMelt;
            }
            
            prePredicateToMelt =
                currentPredicateToMelt;
        }
    }

    /**
     * And-Verknüpfungen verschmelzen
     * @param <CT>
     * @param andPredicateToMelt
     * @return
     */
    private static <CT> Predicate<CT> meltAnd(
            final And<CT> andPredicateToMelt ,
            //final Exclude<CT> exclude ,
            final ConstraintCodeGeneratorCache<CT> cache )
    {
        if ( andPredicateToMelt.size() == 1 )
        {
            return 
                    melt(
                            andPredicateToMelt.get( 0 ) ,
                            //exclude ,
                            cache );
        }

        boolean containsNoAnd = true;
        
        for ( Predicate<CT> andSubPredicate : andPredicateToMelt )
        {
            if ( andSubPredicate instanceof And )
            {
                containsNoAnd = false;
                break;
            }
        }
        
        if ( containsNoAnd )
        {
            return andPredicateToMelt;
        }

        final List<Predicate<CT>> resultPredicateList =
            new ArrayList<Predicate<CT>>();
        
        for ( final Predicate<CT> andPartPredicateExpression : andPredicateToMelt )
        {
            if ( andPartPredicateExpression instanceof And )
                // inneres And wird mit äusserem And verschmolzen
            {
                for ( final Predicate<CT> andSubPartPredicateExpression : (And<CT>) andPartPredicateExpression )
                {
                    resultPredicateList.add(
                            melt(
                                    andSubPartPredicateExpression ,
                                    //exclude ,
                                    cache ) );
                }
            }
            else if ( andPartPredicateExpression instanceof Or )
                // rekursiver Aufruf
            {
                resultPredicateList.add(
                        meltOr(
                                (Or<CT>) andPartPredicateExpression ,
                                //exclude ,
                                cache ) );
            }
            else
            {
                resultPredicateList.add(
                        melt(
                                andPartPredicateExpression ,
                                //exclude ,
                                cache ) );
            }
        }

        return 
                //new And<CT>(
                And.instanceOf(
                        andPredicateToMelt.lineNr ,
                        //exclude ,
                        cache ,
                        //cleanForImplicits
                        //false ,
                        resultPredicateList.toArray( 
                                (Predicate<CT>[]) new Predicate[ resultPredicateList.size() ] ) );
    }
    
    /**
     * Or-Verknüpfungen verschmelzen
     * @param <CT>
     * @param orPredicateToMelt
     * @return
     */
    private static <CT> Predicate<CT> meltOr(
            final Or<CT> orPredicateToMelt ,
            //final Exclude<CT> exclude ,
            final ConstraintCodeGeneratorCache<CT> cache )
    {
        if ( orPredicateToMelt.size() == 1 )
        {
            return 
                    melt(
                            orPredicateToMelt.get( 0 ) ,
                            //exclude ,
                            cache );
        }
        
        boolean containsNoOr = true;
        
        for ( Predicate<CT> orSubPredicate : orPredicateToMelt )
        {
            if ( orSubPredicate instanceof Or )
            {
                containsNoOr = false;
                break;
            }
        }
        
        if ( containsNoOr )
        {
            return orPredicateToMelt;
        }

        final List<Predicate<CT>> resultPredicateList =
            new ArrayList<Predicate<CT>>();
        
        for ( final Predicate<CT> orPartPredicateExpression : orPredicateToMelt )
        {
            if ( orPartPredicateExpression instanceof Or )
                // inneres Or wird mit äusserem Or verschmolzen
            {
                for ( final Predicate<CT> orSubPartPredicateExpression : (Or<CT>) orPartPredicateExpression )
                {
                    resultPredicateList.add(
                            melt(
                                    orSubPartPredicateExpression ,
                                    //exclude ,
                                    cache ) );
                }
            }
            else if ( orPartPredicateExpression instanceof And )
                // rekursiver Aufruf
            {
                resultPredicateList.add(
                        meltAnd(
                                (And<CT>) orPartPredicateExpression ,
                                //exclude ,
                                cache ) );
            }
            else
            {
                resultPredicateList.add(
                        melt(
                                orPartPredicateExpression ,
                                //exclude ,
                                cache ) );
            }
        }

        return 
                //new Or<CT>(
                Or.instanceOf(
                        orPredicateToMelt.lineNr ,
                        cache ,
                        resultPredicateList.toArray( 
                                (Predicate<CT>[]) new Predicate[ resultPredicateList.size() ] ) );
    }
    
    /**
     * Immer gültige (Tautologien) und
     * niemals gültige (Kontradiktion)
     * Terme entfernen
     * 
     * @param <CT>
     * @param predicateToClean
     * @return
     */
    private static <CT> Predicate<CT> removeTautKontra(
            final Predicate<CT> predicateToClean ,
            //final Exclude<CT> exclude ,
            final ConstraintCodeGeneratorCache<CT> cache )
    {
        //Predicate<CT> prePredicateToClean =
        //    predicateToClean;
        //
        //while ( true )
        //{
        //    final Predicate<CT> currentPredicateToClean;
        //    if ( prePredicateToClean instanceof And )
        //    {
        //        final And<CT> andPredicateToClean = 
        //            (And<CT>) prePredicateToClean;
        //
        //        currentPredicateToClean =
        //            removeTaut(
        //                    andPredicateToClean ,
        //                    exclude );
        //    }
        //    else if ( prePredicateToClean instanceof Or )
        //    {
        //        final Or<CT> orPredicateToClean = 
        //            (Or<CT>) prePredicateToClean;
        //
        //        currentPredicateToClean =
        //            removeKontra(
        //                    orPredicateToClean ,
        //                    exclude );
        //    }
        //    else if ( prePredicateToClean instanceof Not )
        //    {
        //        final Not<CT> notPredicateToClean = (Not<CT>) prePredicateToClean;
        //
        //        currentPredicateToClean =
        //                removeTautKontra(
        //                        notPredicateToClean ,
        //                        exclude );
        //    }
        //    else
        //    {
        //        return prePredicateToClean;
        //    }
        //    
        //    if ( currentPredicateToClean.equals( prePredicateToClean ) )
        //        // keine Verbesserung mehr erreicht
        //    {
        //        return currentPredicateToClean;
        //    }
        //    
        //    prePredicateToClean =
        //        currentPredicateToClean;
        //}
        if ( predicateToClean instanceof And )
        {
            final And<CT> andPredicateToClean = 
                (And<CT>) predicateToClean;

            return
                removeTaut(
                        andPredicateToClean ,
                        //exclude ,
                        cache );
        }
        else if ( predicateToClean instanceof Or )
        {
            final Or<CT> orPredicateToClean = 
                (Or<CT>) predicateToClean;

            return
                removeKontra(
                        orPredicateToClean ,
                        cache );
        }
        else if ( predicateToClean instanceof Not )
        {
            final Not<CT> notPredicateToClean = (Not<CT>) predicateToClean;

            return
                    removeTautKontra(
                            notPredicateToClean ,
                            //exclude ,
                            cache );
        }
        else
        {
            return predicateToClean;
        }
    }

    /**
     * Immer gültige Terme
     * (Tautologien) entfernen
     * 
     * @param <CT>
     * @param predicateToClean
     * @return
     */
    private static <CT> /*And*/Predicate<CT> removeTaut(
            final And<CT> andToClean ,
            //final Exclude<CT> exclude ,
            final ConstraintCodeGeneratorCache<CT> cache )
    {
        final List<Predicate<CT>> noTautPredList =
                new ArrayList<Predicate<CT>>();

        for ( Predicate<CT> predicate : andToClean )
        {
            if ( ! isTaut( predicate ) )
            {
                noTautPredList.add(
                        predicate );
            }
        }

        if ( noTautPredList.size() == andToClean.size() )
        {
            return andToClean;
        }

        return 
                //new And<CT>(
                And.instanceOf(
                        andToClean.lineNr ,
                        //exclude ,
                        cache ,
                        //cleanForImplicits
                        //true ,
                        noTautPredList.toArray( 
                                (Predicate<CT>[]) new Predicate[ noTautPredList.size() ] ) );
    }

    /**
     * Immer gültig Tautologie
     * @param predicate
     * @return ob Tautologie
     */
    private static <CT> boolean isTaut(
            final Predicate<CT> predicate )
    {
        if ( predicate instanceof Or )
        {
            final Or<CT> orPredicate =
                (Or<CT>) predicate;
            
            for ( final Predicate<CT> subOrPredicate : orPredicate )
            {
                if ( subOrPredicate instanceof Not )
                {
                    if ( orPredicate.contains( ( (Not<CT>) subOrPredicate ).get() ) )
                        // X or not(X)
                    {
                        return true;
                    }
                    
                    if ( isTaut( (Not<CT>) subOrPredicate ) )
                    {
                        return true;
                    }
                }
                else
                {
                    if ( orPredicate.contains( 
                            Not.instanceOf( 
                                    // lineNr ist hier nicht wichtig, nur bei Prädikat-Expressions, welche durch den Benutzer angelegt wurden
                                    0 ,
                                    subOrPredicate ) ) )
                        // not(X) or X
                    {
                        return true;
                    }
                }
            }
        }
        if ( predicate instanceof And )
        {
            if ( ( (And<CT>) predicate ).size() == 0 )
                // leeres And ist immer erfüllt
            {
                return true;
            }
        }
        return false;
    }

    /**
     * Nie gültige (Kontradiktion)
     * Terme entfernen
     * 
     * @param <CT>
     * @param predicateToClean
     * @return
     * @deprecated aufgrund Include und Exclude nicht mehr verwendbar
     */
    private static <CT> /*Or*/Predicate<CT> removeKontra(
            final Or<CT> orToClean ,
            //final Exclude<CT> exclude
            final ConstraintCodeGeneratorCache<CT> cache )
    {
        /* Aus-Filtern Kontradiktionen
         */
        final List<Predicate<CT>> noKontraPredList =
            new ArrayList<Predicate<CT>>();

        for ( Predicate<CT> predicate : orToClean )
        {
           if ( ! isKontra( 
                   predicate
                   //exclude
                   ) )
           {
               noKontraPredList.add(
                       predicate );
           }
        }
        
        return 
                //new Or<CT>(
                Or.instanceOf(
                        orToClean.lineNr ,
                        cache ,
                        noKontraPredList.toArray( 
                                (Predicate<CT>[]) new Predicate[ noKontraPredList.size() ] ) );
    }
    
    /**
     * Nie gültige (Kontradiktion)
     * Terme entfernen
     * 
     * @param <CT>
     * @param predicateToClean
     * @return
     */
    private static <CT> Predicate<CT> removeTautKontra(
            final Not<CT> notPredicateToClean ,
            //final Exclude<CT> exclude ,
            final ConstraintCodeGeneratorCache<CT> cache )
    {
        final Predicate<CT> innerPredicate =
                notPredicateToClean.get();

        final Predicate<CT> cleanedInnerPredicate =
                removeTautKontra( 
                        innerPredicate ,
                        //exclude ,
                        cache );

        if ( innerPredicate.equals( cleanedInnerPredicate ) )
        {
            return notPredicateToClean;
        }

        return Not.instanceOf(
                notPredicateToClean.lineNr ,
                cleanedInnerPredicate );
    }

    /**
     * Nie gültig, Kontradiktion
     * @param predicate
     * @return ob Kontradiktion
     * @deprecated aufgrund Include und Exclude nicht mehr verwendbar
     */
    private static <CT> boolean isKontra(
            final Predicate<CT> predicate
            //final Exclude<CT> exclude
            )
    {
        if ( predicate instanceof And )
        {
            final And<CT> andPredicate =
                (And<CT>) predicate;
            
            //if ( isKontraForExclude(
            //        (And<CT>) predicate
            //        //exclude
            //        ) )
            //{
            //    return true;
            //}
            
            for ( final Predicate<CT> subAndPredicate : andPredicate )
            {
                if ( subAndPredicate instanceof Not )
                {
                    if ( andPredicate.contains( ( (Not<CT>) subAndPredicate ).get() ) )
                        // X and not(X)
                    {
                        return true;
                    }
                    else if ( isKontra( 
                            subAndPredicate
                            //exclude
                            ) )
                    {
                        return true;
                    }
                }
                else
                {
                    if ( andPredicate.contains( 
                            Not.instanceOf( 
                                    // lineNr ist hier nicht wichtig, nur bei Prädikat-Expressions, welche durch den Benutzer angelegt wurden
                                    0 ,
                                    subAndPredicate ) ) )
                        // not(X) and X
                    {
                        return true;
                    }
                }
            }
        }
        if ( predicate instanceof Or )
        {
            if ( ( (Or<CT>) predicate ).size() == 0 )
                // leeres Or ist nie erfüllt
            {
                return true;
            }
        }
        return false;
    }

    /**
     * Nie gültig, Kontradiktion
     * @param predicate
     * @return ob Kontradiktion
     */
    private static <CT> boolean isKontraForExclude(
            final And<CT> andPredicateToCheck , 
            final Exclude<CT> exclude )
    {
        //// Sammeln von bounded exclude auf der aktuellen Ebene
        ///*final*/ ImmuBoundedExclude<CT> boundedExclude = new ImmuBoundedExclude<CT>( exclude );
        //for ( final Predicate<CT> subPredicate : andPredicateToCheck )
        //{
        //    if ( boundedExclude.isKontra( subPredicate ) )
        //    {
        //        return true;
        //    }
        //
        //    boundedExclude =
        //            boundedExclude.add( 
        //                    subPredicate );
        //}

        for ( final Predicate<CT> subPredicate : andPredicateToCheck )
        {
            if ( subPredicate instanceof Not )
            {
                if ( isKontra( 
                        (Not<CT>) subPredicate
                        //exclude
                        ) )
                {
                    return true;
                }
            }

            if ( false == ( subPredicate instanceof And || 
                    subPredicate instanceof Or || 
                    subPredicate instanceof Not ) )
            {
                for ( final Predicate<CT> otherSubPredicate : andPredicateToCheck )
                {
                    //if ( otherSubPredicate instanceof And )
                    //{
                    //    ???
                    //}
                    
                    if ( subPredicate != otherSubPredicate &&
                            false == ( otherSubPredicate instanceof And || 
                            otherSubPredicate instanceof Or || 
                            otherSubPredicate instanceof Not ) )
                    {
                        if (
                                subPredicate instanceof PrimitivPredicate &&
                                otherSubPredicate instanceof PrimitivPredicate &&
                                exclude.contains( 
                                (PrimitivPredicate<CT>) subPredicate , 
                                (PrimitivPredicate<CT>) otherSubPredicate ) )
                        {
                            return true;
                        }
                    }
                }
                
            }
        }
        
        return false;
    }

    /**
     * Nie gültig, Kontradiktion
     * @param notPredicateToCheck
     * @return ob Kontradiktion
     */
    private static <CT> boolean isKontra(
            final Not<CT> notPredicateToCheck
            //final Exclude<CT> exclude
            )
    {
        final Predicate<CT> innerPredicate =
                notPredicateToCheck.get();

        return isTaut(
                innerPredicate );
    }

    /**
     * Immer gültig, Tautologie
     * @param notPredicateToCheck
     * @return ob Tautologie
     */
    private static <CT> boolean isTaut(
            final Not<CT> notPredicateToCheck
            //final Exclude<CT> exclude
            )
    {
        final Predicate<CT> innerPredicate =
                notPredicateToCheck.get();

        return isKontra(
                innerPredicate
                //exclude
                );
    }

//    /**
//     * TODO Bei sich ausschliessenden Prädikaten
//     * werden nur die erfüllenden Prädikate
//     * (nicht not) behalten.
//     * 
//     * @param predicateToClean
//     * @return
//     */
//    private static <CT> Predicate<CT> addExcludeXor(
//            final Predicate<CT> predicateToClean ,
//            final Exclude<CT> exclude ,
//            final Collection<Predicate<CT>> includePredicateColl )
//    {
//        //if ( predicateToClean instanceof And )
//        //{
//        //    return
//        //            addExcludeXor(
//        //                    (And<CT>) predicateToClean ,
//        //                    exclude );
//        //}
//        //else if ( predicateToClean instanceof Or )
//        //{
//        //    return
//        //            addExcludeXor(
//        //                    (Or<CT>) predicateToClean ,
//        //                    exclude );
//        //}
//        //else if ( predicateToClean instanceof Not )
//        //{
//        //    return
//        //            Not.instanceOf(
//        //                    // lineNr ist hier nicht wichtig, nur bei Prädikat-Expressions, welche durch den Benutzer angelegt wurden
//        //                    0 ,
//        //                    addExcludeXor( 
//        //                            ( (Not<CT>) predicateToClean ).get() ,
//        //                            exclude ) );
//        //}
//        //else
//        //{
//            //if ( false == exclude.contains( predicateToClean ) )
//            //{
//            //    return predicateToClean;
//            //}
//            
//            final List<Predicate<CT>> andXorPredicateList = new ArrayList<Predicate<CT>>();
//            
//            andXorPredicateList.add( predicateToClean );
//            
//            andXorPredicateList.addAll(
//                    exclude.getXorListFor(
//                            //predicateToClean ,
//                            includePredicateColl ) );
//
//            return
//                    new And<CT>(
//                            // lineNr ist hier nicht wichtig, nur bei Prädikat-Expressions, welche durch den Benutzer angelegt wurden
//                            0 ,
//                            //exclude ,
//                            (Predicate<CT>[]) andXorPredicateList.toArray( new Predicate<?>[ andXorPredicateList.size() ] ) );
//        //}
//    }

    ///**
    // * TODO Bei sich ausschliessenden Prädikaten
    // * werden nur die erfüllenden Prädikate
    // * (nicht not) behalten.
    // * 
    // * @param andPredicateToClean
    // * @return
    // */
    //private static <CT> And<CT> addExcludeXor(
    //        final And<CT> andPredicateToClean ,
    //        final Exclude<CT> exclude )
    //{
    //    final List<Predicate<CT>> andList = new ArrayList<Predicate<CT>>();
    //
    //    for ( final Predicate<CT> subPredicate : andPredicateToClean )
    //    {
    //        andList.add(
    //                addExcludeXor( 
    //                        subPredicate , 
    //                        exclude ) );
    //    }
    //    
    //    return
    //            new And<CT>( 
    //                    // lineNr ist hier nicht wichtig, nur bei Prädikat-Expressions, welche durch den Benutzer angelegt wurden
    //                    0 ,
    //                    exclude ,
    //                    (Predicate<CT>[]) andList.toArray( new Predicate<?>[ andList.size() ] ) );
    //}
    
    ///**
    // * TODO Bei sich ausschliessenden Prädikaten
    // * werden nur die erfüllenden Prädikate
    // * (nicht not) behalten.
    // * 
    // * @param predicateToClean
    // * @return
    // */
    //private static <CT> Or<CT> addExcludeXor(
    //        final Or<CT> orPredicateToClean ,
    //        final Exclude<CT> exclude )
    //{
    //    final List<Predicate<CT>> andList = new ArrayList<Predicate<CT>>();
    //
    //    for ( final Predicate<CT> subPredicate : orPredicateToClean )
    //    {
    //        andList.add(
    //                addExcludeXor( 
    //                        subPredicate , 
    //                        exclude ) );
    //    }
    //    
    //    return
    //            new Or<CT>( 
    //                    // lineNr ist hier nicht wichtig, nur bei Prädikat-Expressions, welche durch den Benutzer angelegt wurden
    //                    0 ,
    //                    (Predicate<CT>[]) andList.toArray( new Predicate<?>[ andList.size() ] ) );
    //}

    ///**
    // * TODO Bei sich ausschliessenden Prädikaten
    // * werden nur die erfüllenden Prädikate
    // * (nicht not) behalten.
    // * 
    // * @param predicateToClean
    // * @return
    // */
    //private static <CT> Or<CT> addExcludeXor(
    //        final Not<CT> notPredicateToClean ,
    //        final Exclude<CT> exclude )
    //{
    //    final Predicate<CT> innerPredicate =
    //            notPredicateToClean.get();
    //
    //    final Predicate<CT> cleanedInnerPredicate =
    //            addExcludeXor( 
    //                    innerPredicate , 
    //                    exclude );
    //    
    //    return Not.instanceOf(
    //            // lineNr ist hier nicht wichtig, nur bei Prädikat-Expressions, welche durch den Benutzer angelegt wurden
    //            0 ,
    //            cleanedInnerPredicate );
    //}

//    private static <CT> Collection<Predicate<CT>> collectAllPrimitivePredicate(
//            final Predicate<CT> predicateToNormalize )
//    {
//        final HashSet<Predicate<CT>> allPrimitivePredicateSet =
//                new HashSet<Predicate<CT>>();
//        
//        collectAllPrimitivePredicate(
//                predicateToNormalize ,
//                allPrimitivePredicateSet );
//        
//        return allPrimitivePredicateSet;
//    }
//
//    private static <CT> void collectAllPrimitivePredicate(
//            final Predicate<CT> predicateToNormalize ,
//            final HashSet<Predicate<CT>> allPrimitivePredicateSet )
//    {
//        if ( predicateToNormalize instanceof And )
//        {
//            collectAllPrimitivePredicate(
//                    (And<CT>) predicateToNormalize ,
//                    allPrimitivePredicateSet );
//        }
//        else if ( predicateToNormalize instanceof Or )
//        {
//            collectAllPrimitivePredicate(
//                    (Or<CT>) predicateToNormalize ,
//                    allPrimitivePredicateSet );
//        }
//        else if ( predicateToNormalize instanceof Not )
//        {
//            collectAllPrimitivePredicate(
//                    (Not<CT>) predicateToNormalize ,
//                    allPrimitivePredicateSet );
//        }
//        else
//        {
//            allPrimitivePredicateSet.add(
//                    predicateToNormalize );
//        }
//    }
//
//    private static <CT> void collectAllPrimitivePredicate(
//            final And<CT> andPredicateToNormalize ,
//            final HashSet<Predicate<CT>> allPrimitivePredicateSet )
//    {
//        for ( final Predicate<CT> subPredicate : andPredicateToNormalize )
//        {
//            collectAllPrimitivePredicate(
//                    subPredicate ,
//                    allPrimitivePredicateSet );
//        }
//    }
//
//    private static <CT> void collectAllPrimitivePredicate(
//            final Or<CT> orPredicateToNormalize ,
//            final HashSet<Predicate<CT>> allPrimitivePredicateSet )
//    {
//        for ( final Predicate<CT> subPredicate : orPredicateToNormalize )
//        {
//            collectAllPrimitivePredicate(
//                    subPredicate ,
//                    allPrimitivePredicateSet );
//        }
//    }
//
//    private static <CT> void collectAllPrimitivePredicate(
//            final Not<CT> notPredicateToNormalize ,
//            final HashSet<Predicate<CT>> allPrimitivePredicateSet )
//    {
//            collectAllPrimitivePredicate(
//                    notPredicateToNormalize.get() ,
//                    allPrimitivePredicateSet );
//    }

    ///*default*/ static <CT> Predicate<CT> simplifyForExclude_nicht_korrekt(
    //        final Predicate<CT> predicateToNormalize ,
    //        final Exclude<CT> exclude )
    //{
    //    final HashSet<Predicate<CT>> trueExcludePredicateSet =
    //            new HashSet<Predicate<CT>>();
    //    
    //    final HashSet<Predicate<CT>> falseExcludePredicateSet =
    //            new HashSet<Predicate<CT>>();
    //    
    //    collectExcludePredicate(
    //            predicateToNormalize ,
    //            exclude ,
    //            trueExcludePredicateSet ,
    //            falseExcludePredicateSet
    //            //false
    //            );
    //
    //    final Predicate<CT> simplifiedPredicate =
    //            removeFalseExcludePredicates(
    //                    predicateToNormalize ,
    //                    exclude ,
    //                    trueExcludePredicateSet ,
    //                    falseExcludePredicateSet );
    //    
    //    return simplifiedPredicate;
    //}

    private static <CT> void collectExcludePredicate(
            final Predicate<CT> predicateToNormalize ,
            final Exclude<CT> exclude ,
            final HashSet<Predicate<CT>> trueExcludePredicateSet ,
            final HashSet<Predicate<CT>> falseExcludePredicateSet
            //final boolean isNegated
            )
    {
        if ( predicateToNormalize instanceof PrimitivPredicate )
        {
            if ( exclude.contains( (PrimitivPredicate<CT>) predicateToNormalize ) )
            {
                trueExcludePredicateSet.add(
                    predicateToNormalize );
            }
        }
        else if ( predicateToNormalize instanceof And )
        {
            collectExcludePredicate(
                    (And<CT>) predicateToNormalize ,
                    exclude ,
                    trueExcludePredicateSet ,
                    falseExcludePredicateSet
                    //isNegated
                    );
        }
        else if ( predicateToNormalize instanceof Or )
        {
            collectExcludePredicate(
                    (Or<CT>) predicateToNormalize ,
                    exclude ,
                    trueExcludePredicateSet ,
                    falseExcludePredicateSet
                    //isNegated
                    );
        }
        else if ( predicateToNormalize instanceof Not )
        {
            collectExcludePredicate(
                    (Not<CT>) predicateToNormalize ,
                    exclude ,
                    trueExcludePredicateSet ,
                    falseExcludePredicateSet
                    //isNegated
                    );
        }
        else
        {
            throw new UnreachableCodeException(
                    String.valueOf( predicateToNormalize ) );
        }
    }

    private static <CT> void collectExcludePredicate(
            final And<CT> andPredicateToNormalize ,
            final Exclude<CT> exclude ,
            final HashSet<Predicate<CT>> trueExcludePredicateSet ,
            final HashSet<Predicate<CT>> falseExcludePredicateSet
            //final boolean isNegated
            )
    {
        for ( final Predicate<CT> subPredicate : andPredicateToNormalize )
        {
            collectExcludePredicate(
                    subPredicate ,
                    exclude ,
                    trueExcludePredicateSet ,
                    falseExcludePredicateSet
                    //isNegated
                    );
        }
    }

    private static <CT> void collectExcludePredicate(
            final Or<CT> orPredicateToNormalize ,
            final Exclude<CT> exclude ,
            final HashSet<Predicate<CT>> trueExcludePredicateSet ,
            final HashSet<Predicate<CT>> falseExcludePredicateSet
            //final boolean isNegated
            )
    {
        //// im Or können zutreffende Prädikate nicht gebunden werden, weil auch die Alternative zutreffen könnte
        ////if ( false == isNegated )
        ////    // nur im negierten Fall sammeln not( A or B ) entspricht not( A ) and not( B )
        ////{
        ////    return;
        ////}
        ////
        //
        //// Sammeln evtl auch mehrerer zutreffender ausschliessender Prädikate ist möglich, weil in der Methode 
        //// Normalizer.removeFalseExcludePredicates(Or<CT>, Exclude<CT>, HashSet<Predicate<CT>>, HashSet<Predicate<CT>>)
        //// erst geprüft wird ob das auszuschliessende Prädikat in trueExcludePredicateSet enthalten ist
        //for ( final Predicate<CT> subPredicate : orPredicateToNormalize )
        //{
        //    collectExcludePredicate(
        //            subPredicate ,
        //            exclude ,
        //            trueExcludePredicateSet ,
        //            falseExcludePredicateSet
        //            //isNegated
        //            );
        //}
    }

    private static <CT> void collectExcludePredicate(
            final Not<CT> notPredicateToNormalize ,
            final Exclude<CT> exclude ,
            final HashSet<Predicate<CT>> trueExcludePredicateSet ,
            final HashSet<Predicate<CT>> falseExcludePredicateSet
            //final boolean isNegated
            )
    {
        collectExcludePredicate(
                    notPredicateToNormalize.get() ,
                    exclude ,
                    // true- und falseExcludePredicateSet werden getauscht
                    falseExcludePredicateSet ,
                    trueExcludePredicateSet );
    }

    private static <CT> Predicate<CT> removeFalseExcludePredicates(
            final Predicate<CT> predicateToNormalize ,
            //final Exclude<CT> exclude
            final ConstraintCodeGeneratorCache<CT> cache ,
            final HashSet<Predicate<CT>> trueExcludePredicateSet ,
            final HashSet<Predicate<CT>> falseExcludePredicateSet
            //final boolean isNegated
            )
    {
        if ( predicateToNormalize instanceof And )
        {
            return
                    removeFalseExcludePredicatesAnd(
                            (And<CT>) predicateToNormalize ,
                            cache ,
                            trueExcludePredicateSet ,
                            falseExcludePredicateSet
                            //isNegated
                            );
        }
        else if ( predicateToNormalize instanceof Or )
        {
            return
                    removeFalseExcludePredicatesOr(
                            (Or<CT>) predicateToNormalize ,
                            cache ,
                            trueExcludePredicateSet ,
                            falseExcludePredicateSet
                            //isNegated
                            );
        }
        else if ( predicateToNormalize instanceof Not )
        {
            return
                    removeFalseExcludePredicatesNot(
                            (Not<CT>) predicateToNormalize ,
                            cache ,
                            trueExcludePredicateSet ,
                            falseExcludePredicateSet
                            //isNegated
                            );
        }
        else
        {
            if ( trueExcludePredicateSet.contains( predicateToNormalize ) )
            {
                return predicateToNormalize;
            }
            
            if ( falseExcludePredicateSet.contains( predicateToNormalize ) )
            {
                //return null;
                return new FalsePredicate<CT>();
            }
            
            for ( Predicate<CT> trueExcludePredicate : trueExcludePredicateSet )
            {
                if ( 
                        trueExcludePredicate instanceof PrimitivPredicate &&
                        predicateToNormalize instanceof PrimitivPredicate &&
                        cache.exclude.contains( 
                                (PrimitivPredicate<CT>) trueExcludePredicate , 
                                (PrimitivPredicate<CT>) predicateToNormalize ) )
                {
                    // null ist Indikator für leeres, nicht zu übernehmendes Prädikat
                    //return null;
                    return new FalsePredicate<CT>();
                }
            }

            return predicateToNormalize;
        }
    }

    private static <CT> Predicate<CT> removeFalseExcludePredicatesAnd(
            final And<CT> andPredicateToNormalize ,
            //final Exclude<CT> exclude ,
            final ConstraintCodeGeneratorCache<CT> cache ,
            final HashSet<Predicate<CT>> trueExcludePredicateSet ,
            final HashSet<Predicate<CT>> falseExcludePredicateSet
            //final boolean isNegated
            )
    {
        final List<Predicate<CT>> simplyfiedSubPredList =
                new ArrayList<Predicate<CT>>();

        for ( final Predicate<CT> subPredicate : andPredicateToNormalize )
        {
            final Predicate<CT> simplyfiedPredicate =
                    removeFalseExcludePredicates(
                            subPredicate ,
                            cache ,
                            trueExcludePredicateSet ,
                            falseExcludePredicateSet
                            //isNegated
                            );
            
            if ( simplyfiedPredicate != null )
                // null ist Indikator für leeres, nicht zu übernehmendes Prädikat
            {
                simplyfiedSubPredList.add(
                        simplyfiedPredicate );
            }
        }

        if ( simplyfiedSubPredList.isEmpty() )
        {
            // null ist Indikator für leeres, nicht zu übernehmendes Prädikat
            //return null;
            // ein leeres And entspricht einem True
            //return new TruePredicate<CT>();
            // ein leeres And entspricht einem False
            return new FalsePredicate<CT>();
        }
        
        if ( simplyfiedSubPredList.size() == 1 )
        {
            // nur ein Element übrig
            return simplyfiedSubPredList.get( 0 );
        }

        return 
                //new And<CT>(
                And.instanceOf(
                        andPredicateToNormalize.lineNr ,
                        //exclude ,
                        cache ,
                        //cleanForImplicits
                        //true ,
                        simplyfiedSubPredList.toArray( 
                                (Predicate<CT>[]) new Predicate[ simplyfiedSubPredList.size() ] ) );
    }

    private static <CT> Predicate<CT> removeFalseExcludePredicatesOr(
            final Or<CT> orPredicateToNormalize ,
            //final Exclude<CT> exclude
            final ConstraintCodeGeneratorCache<CT> cache ,
            final HashSet<Predicate<CT>> trueExcludePredicateSet ,
            final HashSet<Predicate<CT>> falseExcludePredicateSet
            //final boolean isNegated
            )
    {
        final List<Predicate<CT>> simplyfiedSubPredList =
                new ArrayList<Predicate<CT>>();

        for ( final Predicate<CT> subPredicate : orPredicateToNormalize )
        {
            final Predicate<CT> simplyfiedPredicate =
                    removeFalseExcludePredicates(
                            subPredicate ,
                            cache ,
                            trueExcludePredicateSet ,
                            falseExcludePredicateSet
                            //isNegated
                            );
            
            if ( simplyfiedPredicate != null )
                // null ist Indikator für leeres, nicht zu übernehmendes Prädikat
            {
                simplyfiedSubPredList.add(
                        simplyfiedPredicate );
            }
        }

        if ( simplyfiedSubPredList.isEmpty() )
        {
            // null ist Indikator für leeres, nicht zu übernehmendes Prädikat
            //return null;
            // ein leeres Or entspricht einem False
            return new FalsePredicate<CT>();
        }

        if ( simplyfiedSubPredList.size() == 1 )
        {
            // nur ein Element übrig
            return simplyfiedSubPredList.get( 0 );
        }

        return 
                //new Or<CT>(
                Or.instanceOf(
                        orPredicateToNormalize.lineNr ,
                        cache ,
                        simplyfiedSubPredList.toArray( 
                                (Predicate<CT>[]) new Predicate[ simplyfiedSubPredList.size() ] ) );
    }

    private static <CT> Predicate<CT> removeFalseExcludePredicatesNot(
            final Not<CT> notPredicateToNormalize ,
            //final Exclude<CT> exclude
            final ConstraintCodeGeneratorCache<CT> cache ,
            final HashSet<Predicate<CT>> trueExcludePredicateSet ,
            final HashSet<Predicate<CT>> falseExcludePredicateSet
            //final boolean isNegated
            )
    {
        final Predicate<CT> innerNotPredicate =
                notPredicateToNormalize.get();

        final Predicate<CT> simplyfiedPredicate =
                removeFalseExcludePredicates(
                        innerNotPredicate ,
                        cache ,
                        trueExcludePredicateSet ,
                        falseExcludePredicateSet
                        //isNegated
                        );

        if ( innerNotPredicate.equals( simplyfiedPredicate ) )
        {
            return notPredicateToNormalize;
        }

        if ( simplyfiedPredicate == null )
        {
            // null ist Indikator für leeres, nicht zu übernehmendes Prädikat
            return null;
            //return new FalsePredicate<CT>();
        }

        return Not.instanceOf( 
                notPredicateToNormalize.lineNr , 
                simplyfiedPredicate );
    }

    /**
     * de Morgan
     * @param predicateToSimplifyNot
     * @param cache
     * @return
     */
    public static <CT> Predicate<CT> simplifyNot(
            final Predicate<CT> predicateToSimplifyNot , 
            //final Exclude<CT> exclude ,
            final ConstraintCodeGeneratorCache<CT> cache )
    {
        if ( predicateToSimplifyNot instanceof And )
        {
            return simplifyNotAnd(
                    (And<CT>) predicateToSimplifyNot ,
                    //exclude ,
                    cache );
        }
        else if ( predicateToSimplifyNot instanceof Or )
        {
            return simplifyNotOr(
                    (Or<CT>) predicateToSimplifyNot ,
                    //exclude ,
                    cache );
        }
        else if ( predicateToSimplifyNot instanceof Not )
        {
            return simplifyNotNot( 
                    (Not<CT>) predicateToSimplifyNot ,
                    //exclude ,
                    cache );
        }
        else if ( predicateToSimplifyNot instanceof Xor )
        {
            throw new IllegalArgumentException(
                    "xor not converted" );
        }
        else
        {
            return predicateToSimplifyNot;
        }
    }

    private static <CT> Predicate<CT> simplifyNotAnd(
            final And<CT> andPredicateToSimplifyNot , 
            //final Exclude<CT> exclude ,
            final ConstraintCodeGeneratorCache<CT> cache )
    {
        final List<Predicate<CT>> subPredList =
                new ArrayList<Predicate<CT>>();

        for ( Predicate<CT> predicate : andPredicateToSimplifyNot )
        {
            subPredList.add(
                    simplifyNot( 
                            predicate ,
                            //exclude ,
                            cache ) );
        }

        return 
                And.instanceOf(
                        andPredicateToSimplifyNot.lineNr ,
                        //exclude ,
                        cache ,
                        //cleanForImplicits
                        //true ,
                        subPredList.toArray( 
                                (Predicate<CT>[]) new Predicate[ subPredList.size() ] ) );
    }

    private static <CT> Predicate<CT> simplifyNotOr(
            final Or<CT> orPredicateToSimplifyNot , 
            //final Exclude<CT> exclude ,
            final ConstraintCodeGeneratorCache<CT> cache )
    {
        final List<Predicate<CT>> subPredList =
                new ArrayList<Predicate<CT>>();

        for ( Predicate<CT> predicate : orPredicateToSimplifyNot )
        {
            subPredList.add(
                    simplifyNot( 
                            predicate ,
                            //exclude ,
                            cache ) );
        }

        return 
                Or.instanceOf(
                        orPredicateToSimplifyNot.lineNr ,
                        cache ,
                        subPredList.toArray( 
                                (Predicate<CT>[]) new Predicate[ subPredList.size() ] ) );
    }

    private static <CT> Predicate<CT> simplifyNotNot(
            final Not<CT> notPredicateToSimplifyNot , 
            //final Exclude<CT> exclude ,
            final ConstraintCodeGeneratorCache<CT> cache )
    {
        final Predicate<CT> negatedPredicateToSimplifyNot =
                notPredicateToSimplifyNot.get();

        if ( negatedPredicateToSimplifyNot.isPrimitive() )
        {
            return notPredicateToSimplifyNot;
        }

        if ( negatedPredicateToSimplifyNot instanceof And )
        {
            final List<Predicate<CT>> subPredList =
                    new ArrayList<Predicate<CT>>();

            for ( Predicate<CT> predicate : (And<CT>) negatedPredicateToSimplifyNot )
            {
                subPredList.add(
                        Not.instanceOf(
                                predicate.lineNr ,
                                simplifyNot( 
                                        predicate ,
                                        //exclude ,
                                        cache ) ) );
            }

            return 
                    Or.instanceOf(
                            negatedPredicateToSimplifyNot.lineNr ,
                            cache ,
                            subPredList.toArray( 
                                    (Predicate<CT>[]) new Predicate[ subPredList.size() ] ) );
        }
        else if ( negatedPredicateToSimplifyNot instanceof Or )
        {
            final List<Predicate<CT>> subPredList =
                    new ArrayList<Predicate<CT>>();

            for ( Predicate<CT> predicate : (Or<CT>) negatedPredicateToSimplifyNot )
            {
                subPredList.add(
                        Not.instanceOf(
                                predicate.lineNr ,
                                simplifyNot( 
                                        predicate ,
                                        //exclude ,
                                        cache ) ) );
            }

            return 
                    And.instanceOf(
                            negatedPredicateToSimplifyNot.lineNr ,
                            //exclude ,
                            cache ,
                            //cleanForImplicits
                            //true ,
                            subPredList.toArray( 
                                    (Predicate<CT>[]) new Predicate[ subPredList.size() ] ) );
        }

        throw new UnreachableCodeException(
                String.valueOf( 
                        notPredicateToSimplifyNot ) );
    }

    public static <CT> Predicate<CT> replaceSingleNotWithExclude(
            final Predicate<CT> predicateToReplaceSimpleNot ,
            //final Exclude<CT> exclude ,
            final ConstraintCodeGeneratorCache<CT> cache )
    {
        if ( predicateToReplaceSimpleNot instanceof And )
        {
            return replaceSingleNotWithExcludeAnd(
                    (And<CT>) predicateToReplaceSimpleNot ,
                    //exclude ,
                    cache );
        }
        else if ( predicateToReplaceSimpleNot instanceof Or )
        {
            return replaceSingleNotWithExcludeOr(
                    (Or<CT>) predicateToReplaceSimpleNot ,
                    //exclude ,
                    cache );
        }
        else if ( predicateToReplaceSimpleNot instanceof Not )
        {
            return replaceSingleNotWithExcludeNot( 
                    (Not<CT>) predicateToReplaceSimpleNot ,
                    //exclude ,
                    cache );
        }
        else if ( predicateToReplaceSimpleNot instanceof Xor )
        {
            throw new IllegalArgumentException(
                    "xor not converted" );
        }
        else
        {
            return predicateToReplaceSimpleNot;
        }
    }

    private static <CT> Predicate<CT> replaceSingleNotWithExcludeAnd(
            final And<CT> andPredicateToReplaceSimpleNot ,
            //final Exclude<CT> exclude ,
            final ConstraintCodeGeneratorCache<CT> cache )
    {
        final List<Predicate<CT>> subPredList =
                new ArrayList<Predicate<CT>>();

        for ( Predicate<CT> predicate : andPredicateToReplaceSimpleNot )
        {
            subPredList.add(
                    replaceSingleNotWithExclude( 
                            predicate ,
                            //exclude ,
                            cache ) );
        }

        return 
                //melt(
                        //new And<CT>(
                        And.instanceOf(
                                andPredicateToReplaceSimpleNot.lineNr ,
                                //exclude ,
                                cache ,
                                //cleanForImplicits
                                //true ,
                                subPredList.toArray( 
                                        (Predicate<CT>[]) new Predicate[ subPredList.size() ] ) )
                        //cache )
                        ;
    }

    private static <CT> Predicate<CT> replaceSingleNotWithExcludeOr(
            final Or<CT> orPredicateToReplaceSimpleNot ,
            //final Exclude<CT> exclude ,
            final ConstraintCodeGeneratorCache<CT> cache )
    {
        final List<Predicate<CT>> subPredList =
                new ArrayList<Predicate<CT>>();

        for ( Predicate<CT> predicate : orPredicateToReplaceSimpleNot )
        {
            subPredList.add(
                    replaceSingleNotWithExclude( 
                            predicate ,
                            //exclude ,
                            cache ) );
        }

        return 
                Or.instanceOf(
                        orPredicateToReplaceSimpleNot.lineNr ,
                        cache ,
                        subPredList.toArray( 
                                (Predicate<CT>[]) new Predicate[ subPredList.size() ] ) );
    }

    private static <CT> Predicate<CT> replaceSingleNotWithExcludeNot(
            final Not<CT> notPredicateToReplaceSimpleNot ,
            //final Exclude<CT> exclude ,
            final ConstraintCodeGeneratorCache<CT> cache )
    {
        final Exclude<CT> exclude = cache.exclude;

        final Predicate<CT> negatedPredicateToSimplifyNot =
                notPredicateToReplaceSimpleNot.get();
        
        if ( false == negatedPredicateToSimplifyNot.isPrimitive() )
        {
            return
                    Not.instanceOf(
                            notPredicateToReplaceSimpleNot.lineNr ,
                            replaceSingleNotWithExclude( 
                                    notPredicateToReplaceSimpleNot.get() ,
                                    //exclude ,
                                    cache ) );
        }
        
        if ( false == exclude.contains( (PrimitivPredicate<CT>) negatedPredicateToSimplifyNot ) )
        {
            return notPredicateToReplaceSimpleNot;
        }

        final List<Predicate<CT>> orList =
                exclude.getOrListFor( 
                        Arrays.asList(
                                (PrimitivPredicate<CT>) negatedPredicateToSimplifyNot ) ,
                        cache );

        return 
                //new Or<CT>(
                Or.instanceOf(
                        notPredicateToReplaceSimpleNot.lineNr ,
                        cache ,
                        orList.toArray( 
                                (Predicate<CT>[]) new Predicate[ orList.size() ] ) );
    }

}