package de.heinerkuecker.constraint;

import java.util.Collection;
import java.util.Set;

import de.heinerkuecker.constraint.util.coll.ReadOnlySet;
import de.heinerkuecker.constraint.util.coll.ReadOnlySetAdaptor;

/**
 * Eine Gruppe sich ausschliessender Pr�dikate.
 * 
 * @author Heiner K&uuml;cker
 */
public /*aua, aua, aua wieder eine nicht finale konkrete Klasse TODO evtl mal besser l�sen: final*/ class ExcludeGroup<CT>
extends AbstractExcludeGroup<CT>
{
    /**
     * Set mit den sich ausschliessenden Pr�dikaten
     * 
     * TODO unver�nderlich
     */
    /*default*/ final /*HashSet*/ReadOnlySet<PrimitivPredicate<CT>> predicateSet;

    /**
     * Bei closed world assumption wird davon ausgegangen,
     * dass beim Vorliegen aller Pr�dikate einer Exclude-Group
     * und bei keinem erf�llten Pr�dikat das gesamte Pr�dikat
     * nicht erf�llt ist.
     * <br/>
     * Also ein Pr�dikat der Exclude-Gruppe muss erf�llt sein.
     * <br/>
     * <pre>
     * Testfall:
     *   {@link _03_excludes.ExcludesConstraintCodeGenerator}
     *  
     *   Constraints:
     *     {@link _03_excludes.NOT_RolleFiliale}
     *     {@link _03_excludes.ORB_RolleZentrale_RolleNiederlassung_ORE
     *  
     *  Die Methode
     *  
     *  {@link _03_excludes.NOT_RolleFiliale#convertToORB_RolleZentrale_RolleNiederlassung_ORE}
     *  
     *  wird nut bei vorliegender closed world assumption generiert.
     * </pre>
     * 
     * Falls eine Exclude-Group mit closed world assumption und alternatic
     * mit open world assumption ben�tigt wird, muss dies jeweils �ber ein
     * anderes Kontext-Objekt geregelt werden:
     * <pre>
     * Beispiel:
     *   Kontext-Objekt-Type Rolle
     *      die Ebene des Benutzers muss immer vorhanden sein: closed world assumption
     *      
     *   Kontext-Objekt-Type fachliche Entit�t optional
     *      die fachliche Entit�t kann fehlen,
     *      der Status der fachlichen Entit�t unterliegt der open world assumption
     * </pre>
     */
    /*default*/ final boolean isClosedWorldAssumption;

    /**
     * Konstruktor.
     *
     * @param name Name der Gruppe sich ausschliessender Pr�dikaten
     * @param predicateArr Array mit den sich ausschliessenden Pr�dikaten
     */
    public ExcludeGroup( 
            final String name , 
            final boolean isClosedWorldAssumption ,
            final PrimitivPredicate<CT>... predicateArr )
    {
        super( name );

        this.isClosedWorldAssumption = isClosedWorldAssumption;

        // das verbieten von Pr�dikaten mit includierten Pr�dikaten in closed-world-assumption-ExcludeGroup's war mal zum L�sen des closed-world-assumption-Problems f�r Kompatibilit�ts-Feststellung gedacht, nicht mehr notwendig, anders gel�st
        //if ( isClosedWorldAssumption )
        //{
        //    for ( final PrimitivPredicate<CT> subPredicate : predicateArr )
        //    {
        //        if ( subPredicate.hasIncludes() )
        //        {
        //            throw new IllegalArgumentException(
        //                    "closed world assumption is only usefuel for predicates without inluded predicates " +
        //                    subPredicate );
        //        }
        //    }
        //}

        this.predicateSet =
                new ReadOnlySetAdaptor<PrimitivPredicate<CT>>(
                //new HashSet<PrimitivPredicate<CT>>(
                        //Arrays.asList(
                                predicateArr );
    }
    
    ///**
    // * Erzeugen der Xor-Klausel f�r die sich ausschliessenden Pr�dikate,
    // * 
    // * @param includePredicateColl nur Pr�dikate, die in dieser Collection enthalten sind, sollen in die XOR-Klausel aufgenommen werden
    // * @return Xor-Klausel
    // */
    //public Xor<CT> getXor(
    //        //final Collection<Predicate<CT>> includePredicateColl
    //        )
    //{
    //    final HashSet<Predicate<CT>> resultSet =
    //            new HashSet<Predicate<CT>>(
    //                    this.predicateSet );
    //    
    //    //resultSet.retainAll( 
    //    //        includePredicateColl );
    //
    //    return new Xor<CT>(
    //            // lineNr ist hier nicht wichtig, nur bei Pr�dikat-Expressions, welche durch den Benutzer angelegt wurden
    //            0 ,
    //            (Predicate<CT>[]) resultSet.toArray( new Predicate<?>[ resultSet.size() ] ) );
    //}
    
    ///**
    // * Erzeugen der Or-Klausel f�r die sich ausschliessenden Pr�dikate,
    // * 
    // * @param includePredicateColl nur Pr�dikate, die in dieser Collection enthalten sind, sollen in die XOR-Klausel aufgenommen werden
    // * @return Xor-Klausel
    // */
    //public /*Or*/Predicate<CT> getOr(
    //        //final Collection<Predicate<CT>> includePredicateColl
    //        final Exclude<CT> exclude )
    //{
    //    final HashSet<Predicate<CT>> resultSet =
    //            new HashSet<Predicate<CT>>(
    //                    this.predicateSet );
    //
    //    //resultSet.retainAll( includePredicateColl );
    //
    //    return 
    //            //new Or<CT>(
    //            Or.instanceOf(
    //                    // lineNr ist hier nicht wichtig, nur bei Pr�dikat-Expressions, welche durch den Benutzer angelegt wurden
    //                    0 ,
    //                    exclude ,
    //                    (Predicate<CT>[]) resultSet.toArray( new Predicate<?>[ resultSet.size() ] ) );
    //}

    /**
     * Erzeugen der Or-Klausel f�r die sich ausschliessenden Pr�dikate,
     * 
     * @param includePredicateColl nur Pr�dikate, die in dieser Collection enthalten sind, sollen in die XOR-Klausel aufgenommen werden
     * @return Xor-Klausel
     */
    public /*Or*/Predicate<CT> getOr(
            final Collection<PrimitivPredicate<CT>> includePredicateColl ,
            //final Exclude<CT> exclude 
            final ConstraintCodeGeneratorCache<CT> cache )
    {
        final Set<PrimitivPredicate<CT>> resultSet =
                //new HashSet<Predicate<CT>>(
                //        this.predicateSet )
                this.predicateSet.toJavaSet();
        
        resultSet.removeAll( 
                includePredicateColl );

        return 
                //new Or<CT>(
                Or.instanceOf(
                        // lineNr ist hier nicht wichtig, nur bei Pr�dikat-Expressions, welche durch den Benutzer angelegt wurden
                        0 ,
                        cache ,
                        (Predicate<CT>[]) resultSet.toArray( new Predicate<?>[ resultSet.size() ] ) );
    }

//    /**
//     * Erzeugen der Or-Klausel f�r die sich ausschliessenden Pr�dikate,
//     * 
//     * @param excludePredicateColl Pr�dikate, die in dieser Collection enthalten sind, sollen nicht in die OR-Klausel aufgenommen werden
//     * @return Xor-Klausel
//     */
//    public /*Or*/Predicate<CT> getOneOr(
//            final Collection<PrimitivPredicate<CT>> excludePredicateColl ,
//            //final Exclude<CT> exclude
//            final ConstraintCodeGeneratorCache<CT> cache )
//    {
//        final Set<PrimitivPredicate<CT>> resultSet =
//                //new HashSet<Predicate<CT>>(
//                //        this.predicateSet )
//                this.predicateSet.toJavaSet();
//        
//        resultSet.removeAll(
//                excludePredicateColl );
//
//        if ( resultSet.size() > 0 )
//        {
//            return 
//                    //new Or<CT>(
//                    Or.instanceOf(
//                            // lineNr ist hier nicht wichtig, nur bei Pr�dikat-Expressions, welche durch den Benutzer angelegt wurden
//                            0 ,
//                            cache ,
//                            //(Predicate<CT>[]) resultSet.toArray( new Predicate<?>[ resultSet.size() ] )
//                            // f�r bessere Performance nur ein Pr�dikat der Exclude-Gruppe verwenden
//                            (Predicate<CT>[]) new Predicate[ resultSet.size() ] );
//        }
//        else
//        {
//            //return 
//            //        //new Or<CT>(
//            //        Or.instanceOf(
//            //                // lineNr ist hier nicht wichtig, nur bei Pr�dikat-Expressions, welche durch den Benutzer angelegt wurden
//            //                0 ,
//            //                cache );
//            throw new IllegalArgumentException();
//        }
//    }

	/**
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
        return 
                "ExcludeGroup[" +
                "name=" + name + ", " +
                "predicateSet=" + predicateSet + 
                "]";
    }

    @Override
    public final boolean contains( 
            final PrimitivPredicate<CT> predicateToCheck )
    {
        //if ( predicateToCheck instanceof PrimitivPredicate )
        {
            final boolean result =
                    this.predicateSet.contains( 
                            /*(PrimitivPredicate<CT>)*/ predicateToCheck );

            //return result;
            if ( result )
            {
                return true;
            }
            else
            {
                return false;
            }
        }

        //return false;
    }

}
