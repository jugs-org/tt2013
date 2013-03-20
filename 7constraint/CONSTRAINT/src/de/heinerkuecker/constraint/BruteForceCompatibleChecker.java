package de.heinerkuecker.constraint;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import de.heinerkuecker.constraint.util.coll.FirstInsertOrderHashset;
import de.heinerkuecker.constraint.util.coll.HashsetWithReadonly;
import de.heinerkuecker.constraint.util.coll.ReadOnlySet;

/**
 * Klasse zum Feststellen der
 * einfachen Implikation (TODO Wikipedia-Link)
 * zweier �bergebener Constraint-Expressions.
 * 
 * @author Heiner K&uuml;cker
 */
public final class BruteForceCompatibleChecker
{
   /**
    *
    * @param exclude
    * @param thisPredicateExpression
    * @param otherPredicateExpression
    * @return
    */
   public static <CT> boolean isCompatibleTo(
           final ConstraintCodeGeneratorCache<CT> cache ,
           final Predicate<CT> thisPredicate ,
           final Predicate<CT> otherPredicate )
   {
       if ( thisPredicate.equals( otherPredicate ) )
           // literale �bereinstimmung
       {
           return true;
       }
       
       if ( thisPredicate instanceof ParametricalPredicate &&
               otherPredicate instanceof PrimitivPredicate )
       {
           final ParametricalPredicate<CT> thisParametricalPredicate = (ParametricalPredicate<CT>) thisPredicate;

           if ( thisParametricalPredicate.isCompatibleTo(
                   (PrimitivPredicate<CT>) otherPredicate ) )
           {
               return true;
           }
       }

       // Suchen einer bereits gepr�ften Kompatibilit�t anhand der Regel: wenn A kompatibel zu B und B kompatibel zu C, dann A kompatibel zu C
       if ( cache.compatibleTree.isConnected(
               thisPredicate ,
               otherPredicate ) )
       {
           return true;
       }

       if ( cache.incompatibleCache.containsKeyValue(
               thisPredicate ,
               otherPredicate ) )
       {
           return false;
       }

       if ( cache.contradictionPredicateCache.contains(
               thisPredicate ,
               otherPredicate ) )
       {
           //Inkompatibilit�t in Cache vermerken
           cache.incompatibleCache.put(
                   thisPredicate ,
                   otherPredicate );

           //Inkompatibilit�t in Cache vermerken
           cache.incompatibleCache.put(
                   otherPredicate ,
                   thisPredicate );

           return false;
       }

       final boolean result =
               innerIsCompatibleTo(
               //realBruteforceInnerIsCompatibleTo(
                       cache ,
                       thisPredicate ,
                       otherPredicate );
       
       if ( result )
       {
           // Kompatibilit�t f�r bessere Performance vermerken
           cache.compatibleTree.put(
                   thisPredicate ,
                   otherPredicate );
       }
       else
       {
           //Inkompatibilit�t in Cache vermerken
           cache.incompatibleCache.put(
                   thisPredicate ,
                   otherPredicate );
       }
       
       return result;
   }

   /**
    *
    * @param exclude
    * @param thisPredicateExpression
    * @param otherPredicateExpression
    * @return
    */
   private static <CT> boolean innerIsCompatibleTo(
           final ConstraintCodeGeneratorCache<CT> cache ,
           final Predicate<CT> thisPredicate ,
           final Predicate<CT> otherPredicate )
   {
       //System.out.println( "$FileName<.\\src\\de\\heinerkuecker\\constraint\\BruteForceCompatibleChecker.java> $LineNr: 116: isCompatibleTo " + thisPredicate + " to " + otherPredicate );
       
       final Predicate<CT> thisNoXorPredicateExpression =
               XorConverter.convertXor(
                       thisPredicate ,
                       cache );

       final Predicate<CT> otherNoXorPredicateExpression =
               XorConverter.convertXor(
                       otherPredicate ,
                       cache );

       if ( thisNoXorPredicateExpression instanceof Or &&
               otherNoXorPredicateExpression instanceof Or )
           // rechtes Or enth�lt alle Sub-Pr�dikate des linken Or
           // Testfall: isCompatibleTo (A3 or AL3) to (J2 or JL2 or A3 or AL3)
       {
           final Or<CT> thisOrPredicate = (Or<CT>) thisNoXorPredicateExpression;

           final Or<CT> otherOrPredicate = (Or<CT>) otherNoXorPredicateExpression;

           boolean containsOtherOrAllThisOrSubPred = true;
           
           for ( final Predicate<CT> thisOrSubPredicate : thisOrPredicate )
           {
               if ( false == otherOrPredicate.contains( thisOrSubPredicate ) )
               {
                   containsOtherOrAllThisOrSubPred = false;
                   break;
               }
           }

           if ( containsOtherOrAllThisOrSubPred )
           {
               return true;
           }
       }

       final Predicate<CT> thisSimplifiedPredicate;
       {
           final Predicate<CT> thisTmpSimplifiedPredicate = 
                   PredicateSimplifier.simplify(
                           thisNoXorPredicateExpression ,
                           cache );

           if ( thisTmpSimplifiedPredicate instanceof Not &&
                   ( (Not<CT>) thisTmpSimplifiedPredicate ).predicateToNegate instanceof PrimitivPredicate &&
                   ( (PrimitivPredicate<CT>) ( (Not<CT>) thisTmpSimplifiedPredicate ).predicateToNegate ).isMemberOfClosedWorldExcludeGroup( cache.exclude ) )
           {
               // bei NOT auf ein Pr�dikat, welches in einer Exclude-Gruop mit closed wolrd assumption enthalten ist
               thisSimplifiedPredicate =
                       ( (PrimitivPredicate<CT>) ( (Not<CT>) thisTmpSimplifiedPredicate ).predicateToNegate ).getOrForClosedWorldExcludeGroup(
                               cache );
           }
           else
           {
               thisSimplifiedPredicate = thisTmpSimplifiedPredicate;
           }
       }

       final Predicate<CT> otherSimplifiedPredicate;
       {
           final Predicate<CT> otherTmpSimplifiedPredicate = 
                   PredicateSimplifier.simplify(
                           otherNoXorPredicateExpression ,
                           cache );

           if ( otherTmpSimplifiedPredicate instanceof Not &&
                   ( (Not<CT>) otherTmpSimplifiedPredicate ).predicateToNegate instanceof PrimitivPredicate &&
                   ( (PrimitivPredicate<CT>) ( (Not<CT>) otherTmpSimplifiedPredicate ).predicateToNegate ).isMemberOfClosedWorldExcludeGroup( cache.exclude ) )
           {
               // bei NOT auf ein Pr�dikat, welches in einer Exclude-Gruop mit closed wolrd assumption enthalten ist
               otherSimplifiedPredicate =
                       ( (PrimitivPredicate<CT>) ( (Not<CT>) otherTmpSimplifiedPredicate ).predicateToNegate ).getOrForClosedWorldExcludeGroup(
                               cache );
           }
           else
           {
               otherSimplifiedPredicate = otherTmpSimplifiedPredicate;
           }
       }

       if ( thisSimplifiedPredicate.equals( otherSimplifiedPredicate ) )
           // literale �bereinstimmung nach Vereinfachung
       {
           return true;
       }

       if ( //dies sollte auch bei nicht primitiven Pr�dikaten klappen: thisSimplifiedPredicate.isPrimitive() &&
               otherSimplifiedPredicate instanceof Or &&
               ( (Or<CT>) otherSimplifiedPredicate ).contains( thisSimplifiedPredicate ) )
           // A to (A or B)
       {
           return true;
       }

       if ( //dies sollte auch bei nicht primitiven Pr�dikaten klappen: otherSimplifiedPredicate.isPrimitive() &&
               thisSimplifiedPredicate instanceof And &&
               ( (And<CT>) thisSimplifiedPredicate ).contains( otherSimplifiedPredicate ) )
           // (A and B) to A
       {
           return true;
       }
       
       if ( thisSimplifiedPredicate.isPrimitive() && 
               otherSimplifiedPredicate.isPrimitive() )
       {
           if ( otherSimplifiedPredicate.getIncludePredicateSetRecursiv().contains(
                   (PrimitivPredicate<CT>) thisSimplifiedPredicate ) )
           {
               // das rechte Pr�dikat inkludiert das linke Pr�dikat
               return false;
           }
           
           //final ReadOnlySet<ExcludeGroup<CT>> thisExcludeGroupSet  = cache.exclude.getExcludeGroupSet( (PrimitivPredicate<CT>) thisSimplifiedPredicate );
           //final ReadOnlySet<ExcludeGroup<CT>> otherExcludeGroupSet = cache.exclude.getExcludeGroupSet( (PrimitivPredicate<CT>) otherSimplifiedPredicate );
           //   
           //if ( thisExcludeGroupSet != null &&
           //       otherExcludeGroupSet != null )
           //{
           //   if ( thisExcludeGroupSet.containsMinOneOf( otherExcludeGroupSet ) )
           //   {
           //       // beide Pr�dikate tauchen in der selben Exclude-Group auf
           //       return false;
           //   }
           //}
           final MutableBoundedExclude<CT> mutableBoundedExclude = new MutableBoundedExclude<CT>( cache.exclude );
           mutableBoundedExclude.isValid( (PrimitivPredicate<CT>) thisSimplifiedPredicate );
           if ( false == mutableBoundedExclude.isValid( (PrimitivPredicate<CT>) otherSimplifiedPredicate ) )
           {
               // beide Pr�dikate tauchen in der selben Exclude-Group auf
               return false;
           }
           
           final PrimitivPredicate<CT> thisPrimitivPredicate = (PrimitivPredicate<CT>) thisSimplifiedPredicate;
           final PrimitivPredicate<CT> otherPrimitivPredicate = (PrimitivPredicate<CT>) otherSimplifiedPredicate;
           if ( otherPrimitivPredicate.getIncludePredicateSetRecursiv().contains( thisPrimitivPredicate ) )
               // das rechte Pr�dikat includiert das linke Pr�dikat, ist also spezieller als das linke
               // Testfall isCompatibleTo EbeneZe to Z2
           {
               if ( cache.includedPredSetToPredicateMap.get( (HashSet<PrimitivPredicate<CT>>) otherPrimitivPredicate.includePredicateSet ).size() > 1 )
                   // es gibt mehr als 1 Pr�dikat, welches die gleichen Pr�dikate inkludiert wie das aktuelle rechte Pr�dikat, es ist also nicht eindeutig erf�llt, wenn das linke Pr�dikat erf�llt ist
                   // TODO eventuell ist das einfache includePredicateSet nicht eindeutig, sondern es solte die Methode getIncludePredicateSetRecursiv verwendet werden
               {
                   return false;
               }
               else
               {
                   // TODO das ist falsch: return true;
               }
           }
       }

       if ( ( thisSimplifiedPredicate.isPrimitive() ||
               ( thisSimplifiedPredicate instanceof And &&
                       ( (And<CT>) thisSimplifiedPredicate ).isAllSubPredicatesPrimitivAndNotParametrized() ) ) &&
               ( otherSimplifiedPredicate.isPrimitive() ||
               ( otherSimplifiedPredicate instanceof And &&
                       ( (And<CT>) otherSimplifiedPredicate ).isAllSubPredicatesPrimitivAndNotParametrized() ) ) )
       {
           final ReadOnlySet<PrimitivPredicate<CT>> thisIncludePredicateSetRecursivInclusiveSelf =
                   thisSimplifiedPredicate.getIncludePredicateSetRecursivInclusiveSelf();

           final ReadOnlySet<PrimitivPredicate<CT>> otherIncludePredicateSetRecursivInclusiveSelf =
                   otherSimplifiedPredicate.getIncludePredicateSetRecursivInclusiveSelf();

           if ( thisIncludePredicateSetRecursivInclusiveSelf.containsAllFromIterable(
                   otherIncludePredicateSetRecursivInclusiveSelf
                   //contains( (PrimitivPredicate<CT>) otherSimplifiedPredicate
                   ) )
           {
               return true;
           }

           //if ( thisSimplifiedPredicate.isPrimitive() &&
           //        otherSimplifiedPredicate.isPrimitive() &&
           //        //( (PrimitivPredicate<CT>) thisSimplifiedPredicate ).includePredicateSet != null &&
           //        //( (PrimitivPredicate<CT>) thisSimplifiedPredicate ).includePredicateSet.contains( otherSimplifiedPredicate )
           //        ( (PrimitivPredicate<CT>) thisSimplifiedPredicate ).includePredicateSetContainsRecursiv( (PrimitivPredicate<CT>) otherSimplifiedPredicate ) )
           //    // die rechte Expression (other) ist direkt includiert (impliziert) von der linken (this) Expression
           //{
           //    // TODO wird dieser if-zweig �berhaupt noch gebraucht
           //    return true;
           //}

           //if ( ( (PrimitivPredicate<CT>) otherSimplifiedPredicate ).includePredicateSetContainsRecursiv( (PrimitivPredicate<CT>) thisSimplifiedPredicate ) )
           //    // das linke Pr�dikat(this) wird vom rechten Pr�dikat includiert, somit ist das rechte Pr�dikat niemals kompatibel zum linken Pr�dikat
           //{
           //    //Inkompatibilit�t in Cache vermerken
           //    cache.incompatibleCache.put(
           //            thisSimplifiedPredicate ,
           //            otherSimplifiedPredicate );
           //
           //    return false;
           //}

           ///* Pr�fen, ob die beiden �bergebenen Pr�dikate in einem gemeinsamen Exclude enthalten sind
           // * wenn ja: return false
           // */
           //final List<ExcludeGroup<CT>> thisExcludeGroupList =
           //        exclude.getExcludeGroupListClone(
           //                (PrimitivPredicate<CT>) thisSimplifiedPredicate );
           //
           //if ( thisExcludeGroupList != null )
           //{
           //    final List<ExcludeGroup<CT>> otherExcludeGroupList =
           //            exclude.getExcludeGroupListClone(
           //                    (PrimitivPredicate<CT>) otherSimplifiedPredicate );
           //
           //    if ( otherExcludeGroupList != null )
           //    {
           //        for ( final ExcludeGroup<CT> otherExcludeGroup : otherExcludeGroupList )
           //        {
           //            if ( thisExcludeGroupList.contains( otherExcludeGroup ) )
           //            {
           //                //Inkompatibilit�t in Cache vermerken
           //                cache.incompatibleCache.put(
           //                        thisSimplifiedPredicate ,
           //                        otherSimplifiedPredicate );
           //
           //                return false;
           //            }
           //        }
           //    }
           //}

           // da die beiden Pr�dikate primitiv bzw And mit nur primitiven Sub-Pr�dikaten sind (kein Not dabei) spielt ein Exclude keine Rolle und auf Includes wurde bereits gepr�ft
           // TODO ist falsch: return false;
       }

       // Pr�fen auf gemeinsame Pr�dikate oder gemeinsame ExcludeGroup
       final ReadOnlySet<PrimitivPredicate<CT>> thisIncludePredicateSetRecursivInclusiveSelf = thisSimplifiedPredicate.getIncludePredicateSetRecursivInclusiveSelf();
       final ReadOnlySet<PrimitivPredicate<CT>> otherIncludePredicateSetRecursivInclusiveSelf = otherSimplifiedPredicate.getIncludePredicateSetRecursivInclusiveSelf();

       if ( false == thisIncludePredicateSetRecursivInclusiveSelf.containsMinOneOf(
               otherIncludePredicateSetRecursivInclusiveSelf ) &&
               // TODO das ist unsinn, eine �berpr�fung reicht aus
               false == otherIncludePredicateSetRecursivInclusiveSelf.containsMinOneOf(
                       thisIncludePredicateSetRecursivInclusiveSelf ) )
           // es gibt keine gemeinsamen Pr�dikate, auch keine includierten
       {
           if ( false == thisSimplifiedPredicate.hasNotSubPred() &&
                   false == otherSimplifiedPredicate.hasNotSubPred() )
           {
               // die �berlappung von ExcludeGroups wirkt sich nur bei einem vorhandenen Not-Sub-Pr�dikat aus
               return false;
           }

           final ReadOnlySet<ExcludeGroup<CT>> thisExcludeGroupSet  = cache.exclude.getExcludeGroupSet( thisIncludePredicateSetRecursivInclusiveSelf );
           final ReadOnlySet<ExcludeGroup<CT>> otherExcludeGroupSet = cache.exclude.getExcludeGroupSet( otherIncludePredicateSetRecursivInclusiveSelf );

           if ( false == thisExcludeGroupSet.containsMinOneOf(
                   otherExcludeGroupSet ) &&
                   // TODO das ist unsinn, eine �berpr�fung reicht aus
                   false == otherExcludeGroupSet.containsMinOneOf(
                           thisExcludeGroupSet ) )
               // es gibt keine gemeinsame ExcludeGroup
           {
               return false;
           }
       }
       
       // TODO negierte Pr�dikate werden ausgeblendet
       //if ( false == thisSimplifiedPredicate.hasNotSubPred() &&
       //        false == otherSimplifiedPredicate.hasNotSubPred() )
       //    // die �berlappung von ExcludeGroups wirkt sich nur bei einem vorhandenen Not-Sub-Pr�dikat aus
       {
           final MutableBoundedExclude<CT> mutableBoundedExclude = new MutableBoundedExclude<CT>( cache.exclude );

           for ( final PrimitivPredicate<CT> thisMandantorySubPrimitivPred : thisSimplifiedPredicate.getMandantoryPrimitivPredicateSet() )
           {
               if ( false == mutableBoundedExclude.isValid( thisMandantorySubPrimitivPred ) )
               {
                   throw new IllegalStateException(
                           "predicate ist contradiction (not satisfiable): " +
                           thisSimplifiedPredicate + " " +
                           "LineNr " + thisSimplifiedPredicate.lineNr );
               }
           }

           for ( final PrimitivPredicate<CT> otherMandantorySubPrimitivPred : otherSimplifiedPredicate.getMandantoryPrimitivPredicateSet() )
           {
               if ( false == mutableBoundedExclude.isValid( (PrimitivPredicate<CT>) otherMandantorySubPrimitivPred ) )
               {
                   // beide Pr�dikate tauchen in der selben Exclude-Group auf
                   return false;
               }
           }
       }
       
       // Testfall: isCompatibleTo not(EbeneZe) to (Z2 or ZA2 or R2 or Z3 or ZA3 or R3)
       if ( thisSimplifiedPredicate.getNegatedMandantoryPrimitivPredicateSet().containsMinOneOf(
               otherSimplifiedPredicate.getMandantoryPrimitivPredicateSet() ) )
       {
           // in der rechten Pr�dikat-Expression ist ein primitives Pr�dikat erforderlich, welches in der linken Pr�dikat-Expression negiert auftauchen muss
           return false;
       }

       // Testfall: isCompatibleTo not(EbeneZe) to (Z2 or ZA2 or R2 or Z3 or ZA3 or R3)
       if ( thisSimplifiedPredicate.getNegatedMandantoryPrimitivPredicateSet().containsMinOneOf(
               otherSimplifiedPredicate.getNonNegatedPrimitivPredicateSet() ) )
       {
           // in der rechten Pr�dikat-Expression ist ein negiertes primitives Pr�dikat erforderlich, welches in der linken Pr�dikat-Expression nicht-negiert auftauchen kann
           return false;
       }

       //TODO falsch // Testfall: isCompatibleTo not(EbeneZe) to (Z2 or ZA2 or R2 or Z3 or ZA3 or R3)
       //if ( false == thisSimplifiedPredicate.getNegatedMandantoryPrimitivPredicateSet().containsAllFromIterable(
       //        otherSimplifiedPredicate.getNegatedMandantoryPrimitivPredicateSet() ) )
       //{
       //    // in der rechten Pr�dikat-Expression ist ein negiertes primitives Pr�dikat nicht erforderlich, welches in der linken Pr�dikat-Expression negiert auftauchen muss
       //    return false;
       //}

       /* Pr�fen, ob im rechten Or andere Pr�dikate aus der gleichen ExcludeGroup wie das linke Pr�dikat inclusive includierte Pr�dikat, aber unterschiedlich auftauchen
        * Testfall: EbeneBe isCompatibleTo (B2 or B3)
        *
        * Das linke Pr�dikat darf keine Includes haben (TODO sp�ter auf Includes erweitern und und Und-Verkn�pfte Pr�dikate elauben (Includes sind Und-verkn�pfte Pr�dikate)
        *
        * TODO Doku ist f�r das falsche Verfahren, korrigieren
        *
        *
        * F�r das linke Pr�dikate werden alle finalen (hasIncludes == false) Pr�dikate (entweder das Pr�dikat selbst oder seine Includes) gesammelt:
        *
        *  EbeneBe
        *
        * F�r die rechten Pr�dikate werden alle finalen (hasIncludes == false) Pr�dikate (entweder das Pr�dikat selbst oder seine Includes) gesammelt:
        *
        *       B2        |       B3
        * ----------------+-----------------
        * RechtskreisSgb2 | RechtskreisSgb3
        *     EbeneBe     |     EbeneBe
        *
        * Aus dem Set der rechten finalen Pr�dikate werden die linken finalen Pr�dikate gel�scht, es d�rfen keine linken finalen Pr�dikate �brig bleiben
        *
        *       B2        |       B3
        * ----------------+-----------------
        * RechtskreisSgb2 | RechtskreisSgb3
        * weg: EbeneBe    | weg: EbeneBe
        *
        * Aus dem restlichen Set der rechten finalen Pr�dikate werden alle Pr�dikate von closed-world-assumption-ExcludeGroups gel�scht, falls die Pr�dikate der jeweiligen ExcludeGroup vollst�ndig enthalten sind
        * (es d�rfen nur closed-world-assumption-ExcludeGroups sein, weil dieser Algorithmus von einer closed-world-assumption ausgeht):
        *
        *          B2          |         B3
        * ---------------------+---------------------
        * weg: RechtskreisSgb2 |weg: RechtskreisSgb3
        * weg: EbeneBe         |weg: EbeneBe
        *
        * Wenn keine finalen Pr�dikate �brig sind, sind die beiden urspr�nglichen Pr�dikate kompatibel, sonst inkompatibel.
        */
       if ( thisSimplifiedPredicate.isPrimitive() &&
               false == ( (PrimitivPredicate<CT>) thisSimplifiedPredicate ).hasIncludes() &&
               otherSimplifiedPredicate instanceof Or &&
               ( (Or<CT>) otherSimplifiedPredicate ).isAllSubPredicatesPrimitivAndNotParametrized() )
       {
           final PrimitivPredicate<CT> thisPrimitivPredicate = (PrimitivPredicate<CT>) thisSimplifiedPredicate;

           // alle Pr�dikate sammeln, die links erf�llt sein m�ssen
           final ReadOnlySet<PrimitivPredicate<CT>> thisIsIncludedByAndSelfPredSet =
                   cache.isIncludedByMap.getIncludedByAndSelfSet(
                           thisPrimitivPredicate );

           // alle includierten Pr�dikate rechts sammeln
           final HashsetWithReadonly<PrimitivPredicate<CT>> otherIncludedAndSelfPredSet =
                   new HashsetWithReadonly<PrimitivPredicate<CT>>();

           final Or<CT> otherOrPredicate = (Or<CT>) otherSimplifiedPredicate;

           for ( final Predicate<CT> otherSubPredicate : otherOrPredicate )
           {
               otherIncludedAndSelfPredSet.addAll(
                       otherSubPredicate.getIncludePredicateSetRecursivInclusiveSelf() );
           }

           if ( false == otherIncludedAndSelfPredSet.containsAllFromIterable(
                   thisIsIncludedByAndSelfPredSet ) )
           {
               // es kommt mehr als ein Pr�dikat aus einer gemeinsamen ExcludeGroup vor
               // TODO dies ist falsch bei isCompatibleTo (IfbRechtskreisSgb2 and EbeneAa) to (A2 or AL2)
               return false;
           }

           // aus den includierten Pr�dikaten rechts diejeniegen linken Pr�dikate entfernen, die sowieso erf�llt sein m�ssen
           otherIncludedAndSelfPredSet.removeAll(
                   thisIsIncludedByAndSelfPredSet );

           // jetzt d�rfen nur noch komplette ExcludeGroup-Pr�dikat-Sets vorhanden sein
           if ( otherIncludedAndSelfPredSet.isEmpty() )
               // keine rechten finalen included (Sub-)Pr�dikate �brig
           {
               return true;
           }

           for ( final ExcludeGroup<CT> excludeGroup : cache.exclude.excludeGroupSet )
           {
               if ( excludeGroup.isClosedWorldAssumption &&
                       otherIncludedAndSelfPredSet.containsAllFromIterable(
                       excludeGroup.predicateSet ) )
               {
                   otherIncludedAndSelfPredSet.removeAll(
                           excludeGroup.predicateSet );

                   if ( otherIncludedAndSelfPredSet.isEmpty() )
                       // keine rechten finalen included (Sub-)Pr�dikate �brig
                   {
                       return true;
                   }
               }
           }

           // es kommt mehr als ein Pr�dikat aus einer gemeinsamen ExcludeGroup vor
           return false;


           // ----- TODO Pr�fen, ob eines der beteiligten Pr�dikate in einem
           //final PrimitivPredicate<CT> thisPrimitivPredicate = (PrimitivPredicate<CT>) thisSimplifiedPredicate;
           //
           //final ReadOnlySet<PrimitivPredicate<CT>> thisFinalIncludedPredicateSet =
           //        thisPrimitivPredicate.getFinalIncludedPredicateSet();
           //
           //final Or<CT> otherOrPredicate = (Or<CT>) otherSimplifiedPredicate;
           //
           //final HashSet<PrimitivPredicate<CT>> otherFinalIncludedPredicateSet =
           //        new HashSet<PrimitivPredicate<CT>>();
           //
           //for ( final Predicate<CT> otherIncludedPredicate : otherOrPredicate )
           //{
           //    final PrimitivPredicate<CT> otherIncludePrimitivPredicate = (PrimitivPredicate<CT>) otherIncludedPredicate;
           //
           //    otherFinalIncludedPredicateSet.addAll(
           //            otherIncludePrimitivPredicate.getFinalIncludedPredicateSet().toJavaSet() );
           //}
           //
           //if ( false == otherFinalIncludedPredicateSet.containsAll(
           //        thisFinalIncludedPredicateSet.toJavaSet() ) )
           //{
           //    //Inkompatibilit�t in Cache vermerken
           //    cache.incompatibleCache.put(
           //            thisSimplifiedPredicate ,
           //            otherSimplifiedPredicate );
           //
           //    // nicht alle linken finalen included (Sub-)Pr�dikate im Set der rechten finalen included (Sub-)Pr�dikate enthalten
           //    return false;
           //}
           //
           //otherFinalIncludedPredicateSet.removeAll(
           //        thisFinalIncludedPredicateSet.toJavaSet() );
           //
           //final ReadOnlyList<ExcludeGroup<CT>> thisExclGrpList =
           //        exclude.excludeGroupList;
           //
           //if ( otherFinalIncludedPredicateSet.isEmpty() )
           //    // keine rechten finalen included (Sub-)Pr�dikate �brig
           //{
           //    // Kompatibilit�t f�r bessere Performance vermerken
           //    cache.compatibleTree.put(
           //            thisSimplifiedPredicate ,
           //            otherSimplifiedPredicate );
           //
           //    return true;
           //}
           //
           //for ( final ExcludeGroup<CT> excludeGroup : thisExclGrpList )
           //{
           //    if ( excludeGroup.isClosedWorldAssumption &&
           //            otherFinalIncludedPredicateSet.containsAll(
           //            excludeGroup.predicateSet.toJavaSet() ) )
           //    {
           //        otherFinalIncludedPredicateSet.removeAll(
           //                excludeGroup.predicateSet.toJavaSet() );
           //    }
           //
           //    if ( otherFinalIncludedPredicateSet.isEmpty() )
           //        // keine rechten finalen included (Sub-)Pr�dikate �brig
           //    {
           //        // Kompatibilit�t f�r bessere Performance vermerken
           //        cache.compatibleTree.put(
           //                thisSimplifiedPredicate ,
           //                otherSimplifiedPredicate );
           //
           //        return true;
           //    }
           //}
           //
           ////Inkompatibilit�t in Cache vermerken
           //cache.incompatibleCache.put(
           //        thisSimplifiedPredicate ,
           //        otherSimplifiedPredicate );

           // es kommt mehr als ein Pr�dikat aus einer gemeinsamen ExcludeGroup vor
           //return false;
           // --- falsch ---
           //final List<ExcludeGroup<CT>> thisExclGrpList =
           //        exclude.getExcludeGroupListClone(
           //                thisPrimitivPredicate.getIncludePredicateSetRecursivInclusiveSelf() );
           //
           //final Set<PrimitivPredicate<CT>> allIncludePredicateSetRecursivSetInclusiveSelf =
           //        thisPrimitivPredicate.getIncludePredicateSetRecursivInclusiveSelf().toJavaSet();
           //
           //allIncludePredicateSetRecursivSetInclusiveSelf.addAll(
           //        otherOrPredicate.getIncludePredicateSetRecursivInclusiveSelf().toJavaSet() );
           //
           //final MutableBoundedExclude<CT> mutableBoundedExclude = new MutableBoundedExclude<CT>( exclude );
           //
           //for ( final Predicate<CT> includedPredicate : otherOrPredicate )
           //{
           //    for ( final ExcludeGroup<CT> thisExclGrp : thisExclGrpList )
           //    {
           //        if ( false == thisExclGrp.contains( includedPredicate ) )
           //        {
           //            // nur f�r die Pr�dikate pr�fen, die in einer ExcludeGroup enthalten sind, welche das linke Pr�dikat hat
           //            continue;
           //        }
           //    }
           //
           //    if ( false == mutableBoundedExclude.isValid(
           //            (PrimitivPredicate<CT>) includedPredicate ) )
           //    {
           //        //Inkompatibilit�t in Cache vermerken
           //        cache.incompatibleCache.put(
           //                thisSimplifiedPredicate ,
           //                otherSimplifiedPredicate );
           //
           //        // es kommt mehr als ein Pr�dikat aus einer gemeinsamen ExcludeGroup vor
           //        return false;
           //    }
           //}
           //
           //// Kompatibilit�t f�r bessere Performance vermerken
           //cache.compatibleTree.put(
           //        thisSimplifiedPredicate ,
           //        otherSimplifiedPredicate );
           //
           //return true;
       }

// TODO folgender Block ist falsch: Testfall isCompatible EbeneJc to (IfbRechtskreisSgb2 and EbeneBe)
//       if ( thisSimplifiedPredicate.isPrimitive() &&
//               //( (PrimitivPredicate<CT>) thisSimplifiedPredicate ).hasIncludes() &&
//               otherSimplifiedPredicate instanceof And &&
//               ( (And<CT>) otherSimplifiedPredicate ).isAllSubPredicatesPrimitivAndNotParametrized() )
//           // Testfall: isCompatibleTo B2 to (EbeneBe and IfbRechtskreisSgb2)
//       {
//           final PrimitivPredicate<CT> thisPrimitivPredicate = (PrimitivPredicate<CT>) thisSimplifiedPredicate;
//
//           // alle Pr�dikate sammeln, die links erf�llt sein m�ssen
//           final ReadOnlySet<PrimitivPredicate<CT>> thisIsIncludedAndSelfPredSet =
//                   thisPrimitivPredicate.getIncludePredicateSetRecursivInclusiveSelf();
//
//           // alle includierten Pr�dikate rechts sammeln
//           final HashsetWithReadonly<PrimitivPredicate<CT>> otherIncludedAndSelfPredSet =
//                   new HashsetWithReadonly<PrimitivPredicate<CT>>();
//
//           final And<CT> otherAndPredicate = (And<CT>) otherSimplifiedPredicate;
//
//           for ( final Predicate<CT> otherSubPredicate : otherAndPredicate )
//           {
//               otherIncludedAndSelfPredSet.addAll(
//                       otherSubPredicate.getIncludePredicateSetRecursivInclusiveSelf() );
//           }
//
//           if ( otherIncludedAndSelfPredSet.containsOnly( thisIsIncludedAndSelfPredSet ) )
//           {
//               // die rechte Pr�dikat-Expression enth�lt ausschliesslich includierte Pr�dikate, die in der linken Pr�dikat-Expression includiert sind
//               return true;
//           }
//           else
//           {
//               // die rechte Pr�dikat-Expression enth�lt includierte Pr�dikate, die nicht in der linken Pr�dikat-Expression includiert sind
//               return false;
//           }
//       }

       if ( otherSimplifiedPredicate instanceof And )
           // Testfall: isCompatibleTo Z2 to (IfbFehlerStatusGeschlossen and (Z2 or Z3))
       {
           final And<CT> otherAndPredicate = (And<CT>) otherSimplifiedPredicate;

           for ( final Predicate<CT> otherAndSubPredicate : otherAndPredicate )
           {
               if ( false == isCompatibleTo(
                       cache ,
                       //thisSimplifiedPredicate
                       thisSimplifiedPredicate ,
                       //otherSimplifiedPredicate
                       otherAndSubPredicate ) )
               {
                   // die rechte Pr�dikat-Expression enth�lt mindestens ein Sub-Pr�dikate, welches nicht mit dem linken Pr�dikat kompatibel ist
                   return false;
               }
           }

           // die rechte Pr�dikat-Expression enth�lt ausschliesslich includierte Pr�dikate, die in der linken Pr�dikat-Expression includiert sind
           return true;
       }

       if ( otherSimplifiedPredicate instanceof Or )
       {
           final Or<CT> otherOrPredicate = (Or<CT>) otherSimplifiedPredicate;

           if ( thisSimplifiedPredicate instanceof Or )
           {
               boolean containsOtherOrAllThisOrSubPredicates = true;

               final Or<CT> thisOrPredicate = (Or<CT>) thisSimplifiedPredicate;

               for ( final Predicate<CT> thisOrSubPredicate : thisOrPredicate )
               {
                   if ( false == otherOrPredicate.contains( thisOrSubPredicate ) )
                   {
                       containsOtherOrAllThisOrSubPredicates = false;
                       break;
                   }
               }
               if ( containsOtherOrAllThisOrSubPredicates )
               {
                   return true;
               }
           }
       }

       if ( otherSimplifiedPredicate instanceof Or &&
               ( thisSimplifiedPredicate instanceof PrimitivPredicate ||
                       ( thisSimplifiedPredicate instanceof And &&
                               ( (And<CT>) thisSimplifiedPredicate ).isAllSubPredicatesPrimitivAndNotParametrized() ) ) )
       {
           final Or<CT> otherOrPredicate = (Or<CT>) otherSimplifiedPredicate;

           // Testfall: isCompatibleTo R2 to (Z2 or ZA2 or Z3 or ZA3)
           for ( final Predicate<CT> otherOrSubPredicate : otherOrPredicate )
           {
               if ( isCompatibleTo(
                       cache ,
                       //thisSimplifiedPredicate
                       thisSimplifiedPredicate ,
                       //otherSimplifiedPredicate
                       otherOrSubPredicate ) )
               {
                   return true;
               }
           }

           //if ( thisSimplifiedPredicate instanceof PrimitivPredicate ||
           //        ( thisSimplifiedPredicate instanceof And &&
           //                ( (And<CT>) thisSimplifiedPredicate ).isAllSubPredicatesPrimitivAndNotParametrized() ) )
           //{
           //    // TODO das ist falsch, trifft auch bei primitiven Pr�dikat links nicht zu: return false;
           //}

           // Pr�fen, ob alle die linke Bedingung erf�llenden primitiven Pr�dikate im rechten Or enthalten sind (closed world)
           // Testfall: isCompatibleTo (IfbRechtskreisSgb3 and EbeneAa) to (J2 or JL2 or A3 or AL3)
           /*final*/ Set<PrimitivPredicate<CT>> thisIncludedByPredSet = null;
           
           if ( thisSimplifiedPredicate instanceof PrimitivPredicate )
           {
               final PrimitivPredicate<CT> thisPrimitivPredicate = (PrimitivPredicate<CT>) thisSimplifiedPredicate;

               thisIncludedByPredSet = cache.isIncludedByMap.getIncludedBySet( thisPrimitivPredicate ).toJavaSet();
           }
           else
           {
               final And<CT> thisAndPredicate = (And<CT>) thisSimplifiedPredicate;
               
               for ( final PrimitivPredicate<CT> thisAndIncludedPredicate : thisAndPredicate.getIncludePredicateSetRecursivInclusiveSelf() )
               {
                   if ( thisIncludedByPredSet == null )
                       //erster Durchlauf
                   {
                       thisIncludedByPredSet = cache.isIncludedByMap.getIncludedBySet( thisAndIncludedPredicate ).toJavaSet();
                   }
                   else
                       // in Folgedurchl�ufen nur die Vereinigungsmenge aller inkludierenden Pr�dikate behalten
                   {
                       thisIncludedByPredSet.retainAll(
                               cache.isIncludedByMap.getIncludedBySet( thisAndIncludedPredicate ).toJavaSet() );
                   }

               }
           }

           boolean containsOtherOrPredAllThisIncludedByPred = false == thisIncludedByPredSet.isEmpty();
           for ( final PrimitivPredicate<CT> thisIncludedByPred : thisIncludedByPredSet )
           {
               if ( false == otherOrPredicate.contains( thisIncludedByPred ) )
               {
                   containsOtherOrPredAllThisIncludedByPred = false;
                   break;
               }
           }
           
           if ( containsOtherOrPredAllThisIncludedByPred )
           {
               return true;
           }

           // TODO dies ist nicht ganz korrekt, es k�nnte immer noch F�lle geben, bei denen Kompatibilit�t auftritt
           return false;
       }

       //// TODO in der Methode isEquivalent wirkt die closed world assumption nicht korekt
       //final boolean isEquivalent =
       //        isEquivalent(
       //                //exclude ,
       //                cache ,
       //                thisSimplifiedPredicate ,
       //                otherSimplifiedPredicate );
       //
       //if ( isEquivalent )
       //{
       //    // Kompatibilit�t f�r bessere Performance vermerken
       //    cache.compatibleTree.put(
       //            thisSimplifiedPredicate ,
       //            otherSimplifiedPredicate );
       //
       //    // Kompatibilit�t f�r bessere Performance vermerken
       //    cache.compatibleTree.put(
       //            thisSimplifiedPredicate ,
       //            otherSimplifiedPredicate );
       //
       //    return true;
       //}

       // TODO alle folgenden rekursiven Arbeitsweisen k�nnen aufgrund der closed world assumption nicht funktionieren, weil dann nicht mehr alle Pr�dikate in den Expressions enthalten sind und diese ben�tigt der ExcludeIncludeChecker

       // Wenn das linke Pr�dikat(this) primitiv ist und das rechte Pr�dikat ein Oder-Pr�dikat ist,
       // muss das linke Pr�dikat zu mindestens einem Unter-Pr�dikate im rechten Oder-Pr�dikat kompatibel sein
       // Testfall: isCompatibleTo EbeneBe (B2 or Z2 or ZA2 or ZL2 or R2 or RL2 or A2 or AL2 or J2 or JL2 or B3 or Z3 or ZA3 or ZL3 or R3 or RL3 or A3 or AL3)
       //TODO klappte bei OR auf der linken Seite nicht: dies sollte auch bei nicht primitiven Pr�dikaten klappen: 
       if ( thisSimplifiedPredicate.isPrimitive() ||
               ( ( thisSimplifiedPredicate instanceof And ) &&
                       ( (And<CT>) thisSimplifiedPredicate ).isAllSubPredicatesPrimitivAndNotParametrized() ) )
       {
           //dies sollte auch bei nicht primitiven Pr�dikaten klappen: final PrimitivPredicate<CT> thisPrimitivPredicate = (PrimitivPredicate<CT>) thisSimplifiedPredicate;

           // TODO dieser gesamte if-Block ist falsch
           //if ( otherSimplifiedPredicate instanceof Or )
           //{
           //    final Or<CT> otherOrPredicate = (Or<CT>) otherSimplifiedPredicate;
           //
           //    for ( final Predicate<CT> otherOrSubPredicate : otherOrPredicate )
           //    {
           //        if ( isCompatibleTo(
           //                cache ,
           //                //thisSimplifiedPredicate
           //                //dies sollte auch bei nicht primitiven Pr�dikaten klappen: thisPrimitivPredicate
           //                thisSimplifiedPredicate ,
           //                //otherSimplifiedPredicate
           //                otherOrSubPredicate ) )
           //        {
           //            return true;
           //        }
           //    }
           //    //if ( otherOrPredicate.isAllSubPredicatesPrimitivNotParametrizedRespectivlyAnd() )
           //    {
           //        //return true;
           //    }
           //    return false;
           //}

       //if ( otherSimplifiedPredicate instanceof And )
       //    // TODO dieser Code wird nie erreicht, das gleiche if gibt es weiter oben
       //    // wenn das rechte Pr�dikat(other) ein And ist, darf keine And-Verkn�pfung des linken mit einem rechten Unter-Pr�dikat eine Kontradiktion(nicht erf�llbar) sein
       //    // ausserdem muss das linke Pr�dikat zu jedem rechten Pr�dikat kompatibel sein
       //{
       //    final And<CT> otherAndPredicate = (And<CT>) otherSimplifiedPredicate;
       //
       //    for ( final Predicate<CT> otherAndSubPredicate : otherAndPredicate )
       //    {
       //        final Predicate<CT> checkSubAndpredicate =
       //                And.instanceOf(
       //                        //lineNr
       //                        0 ,
       //                        //exclude ,
       //                        cache ,
       //                        //cleanForImplicits
       //                        //false ,
       //                        //andPredicateArr
       //                        new Predicate[]
       //                        {
       //                            thisSimplifiedPredicate ,
       //                            otherAndSubPredicate
       //                        } );
       //        if ( BruteForceContradictionChecker.isContradiction(
       //                cache ,
       //                checkSubAndpredicate ) )
       //        //if ( false == isCompatibleTo(
       //        //        cache ,
       //        //        thisSimplifiedPredicate ,
       //        //        otherAndSubPredicate ) )
       //        {
       //            return false;
       //        }
       //    }
       //    // TODO das ist falsch: return true;
       //}
   }

       if ( thisSimplifiedPredicate instanceof Or )
           // wenn das linke Pr�dikat ein Or ist, muss jedes Or-Sub-Pr�dikat mit dem rechten Pr�dikat kompatibel sein
           // Testfall: isCompatibleTo (Z2 or ZA2 or Z3 or ZA3) to EbeneZe
       {
           final Or<CT> thisOrPredicate = (Or<CT>) thisSimplifiedPredicate;
       
           for ( final Predicate<CT> thisOrSubPredicate : thisOrPredicate )
           {
               if ( false == isCompatibleTo(
                       cache ,
                       //thisSimplifiedPredicate
                       thisOrSubPredicate ,
                       //otherSimplifiedPredicate
                       otherSimplifiedPredicate ) )
               {
                   return false;
               }
           }
           // Kompatibilit�t f�r bessere Performance vermerken
           cache.compatibleTree.put(
                   thisSimplifiedPredicate ,
                   otherSimplifiedPredicate );
       
           return true;
       }

       if ( thisSimplifiedPredicate instanceof Not )
       {
           final boolean isContradictionInNot =
                   BruteForceContradictionChecker.isContradiction(
                           cache ,
                           ( (Not<CT>) thisSimplifiedPredicate ).predicateToNegate ,
                           otherSimplifiedPredicate );

           if ( isContradictionInNot )
           {
               return false;
           }
           else
           {
               // TODO das ist falsch, das klappt nur wenn rechts ein Not ist: return true;
           }
       }

       if ( otherSimplifiedPredicate instanceof Not )
       {
           final boolean isContradictionInNot =
                   BruteForceContradictionChecker.isContradiction(
                           //exclude ,
                           cache ,
                           thisSimplifiedPredicate ,
                           ( (Not<CT>) otherSimplifiedPredicate ).predicateToNegate );

           if ( isContradictionInNot )
           {
               return true;
           }
           else
           {
               return false;
           }
       }

       if ( thisSimplifiedPredicate instanceof And )
           // wenn das linke Pr�dikat ein And ist, muss mindestens ein And-Sub-Pr�dikat mit dem rechten Pr�dikat kompatibel sein
       {
           final And<CT> thisAndPredicate = (And<CT>) thisSimplifiedPredicate;

           for ( final Predicate<CT> thisAndSubPredicate : thisAndPredicate )
           {
               if ( isCompatibleTo(
                       cache ,
                       thisAndSubPredicate ,
                       otherSimplifiedPredicate ) )
               {
                   return true;
               }
               //final Predicate<CT> checkSubAndpredicate =
               //        And.instanceOf(
               //                //lineNr
               //                0 ,
               //                //exclude ,
               //                cache ,
               //                //cleanForImplicits
               //                false ,
               //                //andPredicateArr
               //                new Predicate[]
               //                {
               //                    thisAndSubPredicate ,
               //                    otherSimplifiedPredicate
               //                } );
               //
               //if ( isContradiction(
               //        //exclude ,
               //        cache ,
               //        checkSubAndpredicate ) )
               ////if ( false == isCompatibleTo(
               ////        exclude ,
               ////        cache ,
               ////        thisSimplifiedPredicate ,
               ////        otherAndSubPredicate ) )
               //{
               //    //Inkompatibilit�t in Cache vermerken
               //    cache.incompatibleCache.put(
               //            thisSimplifiedPredicate ,
               //            otherSimplifiedPredicate );
               //
               //    return false;
               //}
           }

           if ( otherSimplifiedPredicate instanceof PrimitivPredicate )
           {
               // TODO das ist falsch, trifft nur bei primitiven Pr�dikat rechts zu
               return false;
           }
       }

       return realBruteforceInnerIsCompatibleTo(
               cache ,
               thisSimplifiedPredicate ,
               otherSimplifiedPredicate );
   }
   
   /**
    * 
    * @param cache
    * @param thisSimplifiedPredicate
    * @param otherSimplifiedPredicate
    * @return
    */
   private static <CT> boolean realBruteforceInnerIsCompatibleTo(
           final ConstraintCodeGeneratorCache<CT> cache ,
           final Predicate<CT> thisPredicate ,
           final Predicate<CT> otherPredicate )
   {
       //System.out.println( "$FileName<.\\src\\de\\heinerkuecker\\constraint\\BruteForceCompatibleChecker.java> $LineNr: 1049: start brute force isCompatibleTo " + thisPredicate + " to " + otherPredicate );

       //final Predicate<CT> excludeCorrectedThisPredicateExpression =
       //        ExcludePredicateCorrector.convert(
       //                noXorThisPredicateExpression ,
       //                cache );

       //final Predicate<CT> excludeCorrectedOtherPredicateExpression =
       //        ExcludePredicateCorrector.convert(
       //                noXorOtherPredicateExpression ,
       //                cache );

       final FirstInsertOrderHashset<PrimitivPredicate<CT>> thisSimplePredicateSet =
               new FirstInsertOrderHashset<PrimitivPredicate<CT>>();

       BruteForceSolver.collectSimplePredicates(
               //excludeCorrectedThisPredicateExpression
               thisPredicate ,
               thisSimplePredicateSet );

       //System.out.println( "$FileName<.\\src\\de\\heinerkuecker\\constraint\\BruteForceCompatibleChecker.java> $LineNr: 1069: thisSimplePredicateSet: " + thisSimplePredicateSet );

       final FirstInsertOrderHashset<PrimitivPredicate<CT>> otherSimplePredicateSet =
               new FirstInsertOrderHashset<PrimitivPredicate<CT>>();

       BruteForceSolver.collectSimplePredicates(
               //excludeCorrectedOtherPredicateExpression
               otherPredicate ,
               otherSimplePredicateSet );

       //System.out.println( "$FileName<.\\src\\de\\heinerkuecker\\constraint\\BruteForceCompatibleChecker.java> $LineNr: 1079: otherSimplePredicateSet: " + otherSimplePredicateSet );

       // aufgrund der Excludes und der Includes k�nnen Expressions kompatibel sein, die keine gemeinsamen Pr�dikate besitzen
       //if ( isDisjoint(
       //        thisSimplePredicateSet.getSet() ,
       //        otherSimplePredicateSet.getSet() ) )
       //  // beide Expressions haben keine gemeinsamen Pr�dikate
       //{
       //    return false;
       //}

       final FirstInsertOrderHashset<PrimitivPredicate<CT>> jointSimplePredicateSet =
               new FirstInsertOrderHashset<PrimitivPredicate<CT>>();

       jointSimplePredicateSet.addAll( thisSimplePredicateSet  );
       jointSimplePredicateSet.addAll( otherSimplePredicateSet );

       //System.out.println( "$FileName<.\\src\\de\\heinerkuecker\\constraint\\BruteForceCompatibleChecker.java> $LineNr: 1096: jointSimplePredicateSet: " + jointSimplePredicateSet );

       final BinCounterWithSkipListeners counter =
               new BinCounterWithSkipListeners(
                       jointSimplePredicateSet.size() );

       //System.out.println( "$FileName<.\\src\\de\\heinerkuecker\\constraint\\BruteForceCompatibleChecker.java> $LineNr: 1102: counter.size(): " + counter.size() );

       final SolvePredicate<CT> thisConvertedSolvePredicate =
               PredicateToSolvepredicateConvert.convertToSolvePredicate(
                       //excludeCorrectedThisPredicateExpression
                       thisPredicate ,
                       counter ,
                       // die Pr�dikat-Positionen der zusammengef�gten Ausdr�cke verwenden
                       jointSimplePredicateSet );

       final SolvePredicate<CT> otherConvertedSolvePredicate =
               PredicateToSolvepredicateConvert.convertToSolvePredicate(
                       //excludeCorrectedOtherPredicateExpression
                       otherPredicate ,
                       // der selbe Counter
                       counter ,
                       // die Pr�dikat-Positionen der zusammengef�gten Ausdr�cke verwenden
                       jointSimplePredicateSet );

       final SolvePredicate<CT> excludeIncludeCheckSolvePredicate =
               PredicateToSolvepredicateConvert.convertToSolvePredicate(
                       //new Or(
                       Or.instanceOf(
                               0 ,
                               cache ,
                               jointSimplePredicateSet.toArray( new Predicate[ jointSimplePredicateSet.size() ] ) ) ,
                               // der selbe Counter
                               counter ,
                               // die Pr�dikat-Positionen der zusammengef�gten Ausdr�cke verwenden
                               jointSimplePredicateSet );

       final Map<PrimitivPredicate<CT>, SolveSimple<CT>> primitivPredToSolvePredMap =
               new HashMap<PrimitivPredicate<CT>, SolveSimple<CT>>();

//       final Map<ExcludeGroup<CT>, ExcludeGroupWithCounterListener<CT>> excludeGroupWithCounterListenerMap =
//               ExcludeGroupWithCounterListener.createGetOrCreateHashmap();

       for ( final SolveSimple<CT> solvePred : excludeIncludeCheckSolvePredicate.getPrimitiveSolvePredicates() )
       {
           primitivPredToSolvePredMap.put(
                   solvePred.predicateToSolve ,
                   solvePred );

//           ExcludeGroupWithCounterListener.saveExcludeGroupsWithCounterListener(
//                   cache ,
//                   jointSimplePredicateSet ,
//                   counter ,
//                   excludeGroupWithCounterListenerMap ,
//                   solvePred );
       }

       // jetzt noch die includierten Pr�dikate vermerken
       thisConvertedSolvePredicate.prepareIncludePredicates(
               jointSimplePredicateSet ,
               primitivPredToSolvePredMap );

       otherConvertedSolvePredicate.prepareIncludePredicates(
               jointSimplePredicateSet ,
               primitivPredToSolvePredMap );

       excludeIncludeCheckSolvePredicate.prepareIncludePredicates(
               jointSimplePredicateSet ,
               primitivPredToSolvePredMap );

       // der Scope dieses Cache ist diese Methode, innerhalb dieser Methode tauchen immer wieder die selben Set-Objekte auf
       final ContainsAllCache<CT> containsAllCache = new ContainsAllCache<CT>();

       while ( counter.isOverflow() == false )
       {
           final boolean thisResult =
                   thisConvertedSolvePredicate.solve();

           if ( thisResult )
           {
               final boolean otherResult =
                       otherConvertedSolvePredicate.solve();

               if ( otherResult == false )
               {
                   if ( new ExcludeIncludeChecker<CT>(
                           cache.exclude ,
                           //jointSimplePredicateSet ,
                           //counter ,
                           primitivPredToSolvePredMap ,
                           containsAllCache
                           //excludeGroupWithCounterListenerMap.values().toArray( new ExcludeGroupWithCounterListener[ excludeGroupWithCounterListenerMap.values().size() ] )
                           ).isValid() )
                       // dieser Check �ber alle primitiven Pr�dikate ist notwendig, weil kleinere Ausdr�cke erf�llbar sein k�nnen aber gr�ssere aufgrund des exclude unerf�llbar sein k�nnen
                   {
                       //System.out.println( "$FileName<.\\src\\de\\heinerkuecker\\constraint\\BruteForceCompatibleChecker.java> $LineNr: 1191: return false isCompatibleTo " + thisPredicate + " to " + otherPredicate );
                       return false;
                   }
               }
           }

           counter.skip();
       }

       //System.out.println( "$FileName<.\\src\\de\\heinerkuecker\\constraint\\BruteForceCompatibleChecker.java> $LineNr: 1200: return true isCompatibleTo " + thisPredicate + " to " + otherPredicate );
       return true;
   }

}