package de.heinerkuecker.constraint;

import java.util.ArrayList;
import java.util.List;

import de.heinerkuecker.constraint.util.coll.FirstInsertOrderHashset;
import de.heinerkuecker.constraint.util.exc.UnreachableCodeException;

/**
 * @author Heiner K&uuml;cker
 */
public class PredicateToSolvepredicateConvert
{
    /*default*/ static <CT> SolvePredicate<CT> convertToSolvePredicate(
            final Predicate<CT> predicateToConvert ,
            final BinCounterWithSkipListeners counter ,
            final FirstInsertOrderHashset<PrimitivPredicate<CT>> simplePredicateSet )
    {
        final SolvePredicate<CT> tmpSolvePredicate =
                innerConvertToSolvePredicate(
                        predicateToConvert ,
                        counter ,
                        simplePredicateSet );

        return tmpSolvePredicate;
    }

    private static <CT> SolvepredicateCounterTuple convert(
            final Predicate<CT> predicateToConvert )
    {
        final FirstInsertOrderHashset<PrimitivPredicate<CT>> simplePredicateSet =
                new FirstInsertOrderHashset<PrimitivPredicate<CT>>();

        BruteForceSolver.collectSimplePredicates(
                predicateToConvert ,
                simplePredicateSet );

        final BinCounterWithSkipListeners counter =
                new BinCounterWithSkipListeners(
                        simplePredicateSet.size() );

        final SolvePredicate<CT> convertedSolvePredicate =
                convertToSolvePredicate(
                        predicateToConvert ,
                        counter ,
                        simplePredicateSet );

        return
                new SolvepredicateCounterTuple(
                        convertedSolvePredicate ,
                        simplePredicateSet ,
                        counter );
    }

    private static <CT> SolvePredicate<CT> innerConvertToSolvePredicate(
            final Predicate<CT> predicateToConvert ,
            final BinCounterWithSkipListeners counter ,
            final FirstInsertOrderHashset<PrimitivPredicate<CT>> simplePredicateSet )
    {
        if ( predicateToConvert instanceof Xor )
        {
            throw new IllegalArgumentException(
                    "convert Xor to Not And predicates before calling this method" );
        }

        //if ( predicateToConvert instanceof ParametricalPredicate )
        //{
        //    throw new IllegalArgumentException(
        //            "code not implemented for ParametricalPredicate" +
        //                    predicateToConvert );
        //}

        if ( predicateToConvert instanceof PrimitivPredicate )
        {
            final BooleanSource booleanSource =
                    new CounterBooleanSource(
                            counter ,
                            simplePredicateSet.indexOf(
                                    predicateToConvert ) );

            return
                    new SolveSimple<CT>(
                            (PrimitivPredicate<CT>) predicateToConvert ,
                            booleanSource );
        }
        else if ( predicateToConvert instanceof And )
        {
            return convertToSolvePredicateAnd(
                    (And<CT>) predicateToConvert ,
                    counter ,
                    simplePredicateSet );
        }
        else if ( predicateToConvert instanceof Or )
        {
            return convertToSolvePredicateOr(
                    (Or<CT>) predicateToConvert ,
                    counter ,
                    simplePredicateSet );
        }
        else if ( predicateToConvert instanceof Not )
        {
            return convertToSolvePredicateNot(
                    (Not<CT>) predicateToConvert ,
                    counter ,
                    simplePredicateSet );
        }
        else
        {
            throw new UnreachableCodeException(
                    String.valueOf( predicateToConvert ) );
        }
    }

    private static <CT> SolvePredicate<CT> convertToSolvePredicateAnd(
            final And<CT> andPredicateToConvert ,
            final BinCounterWithSkipListeners counter ,
            final FirstInsertOrderHashset<PrimitivPredicate<CT>> simplePredicateSet )
    {
        final List<SolvePredicate> andSolvepredicateList =
                new ArrayList<SolvePredicate>();

        for ( Predicate<CT> andSubPredicate : andPredicateToConvert )
        {
            andSolvepredicateList.add(
                    innerConvertToSolvePredicate(
                            andSubPredicate ,
                            counter ,
                            simplePredicateSet ) );
        }

        return
                new SolveAnd(
                        andPredicateToConvert ,
                        andSolvepredicateList.toArray(
                                new SolvePredicate[ andSolvepredicateList.size() ] ) );
    }

    private static <CT> SolvePredicate<CT> convertToSolvePredicateOr(
            final Or<CT> orPredicateToConvert ,
            final BinCounterWithSkipListeners counter ,
            final FirstInsertOrderHashset<PrimitivPredicate<CT>> simplePredicateSet )
    {
        final List<SolvePredicate> orSolvepredicateList =
                new ArrayList<SolvePredicate>();

        for ( Predicate<CT> orSubPredicate : orPredicateToConvert )
        {
            orSolvepredicateList.add(
                    innerConvertToSolvePredicate(
                            orSubPredicate ,
                            counter ,
                            simplePredicateSet ) );
        }

        return
                new SolveOr(
                        orPredicateToConvert ,
                        orSolvepredicateList.toArray(
                                new SolvePredicate[ orSolvepredicateList.size() ] ) );
    }

    private static <CT> SolvePredicate<CT> convertToSolvePredicateNot(
            final Not<CT> notPredicateToConvert ,
            final BinCounterWithSkipListeners counter ,
            final FirstInsertOrderHashset<PrimitivPredicate<CT>> simplePredicateSet )
    {
        return
                new SolveNot<CT>(
                        notPredicateToConvert ,
                        convertToSolvePredicate(
                                notPredicateToConvert.get() ,
                                counter ,
                                simplePredicateSet ) );
    }

}
