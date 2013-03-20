package _03_excludes;

import java.io.File;
import java.io.IOException;

import _03_excludes.enums.Rolle;
import _03_excludes.predicates.RolleEbeneFiliale;
import _03_excludes.predicates.RolleEbeneNiederlassung;
import _03_excludes.predicates.RolleEbeneZentrale;
import _03_excludes.predicates.RolleFiliale;
import _03_excludes.predicates.RolleFilialeNord;
import _03_excludes.predicates.RolleNiederlassung;
import _03_excludes.predicates.RolleNiederlassungNord;
import _03_excludes.predicates.RolleRegionNord;
import _03_excludes.predicates.RolleRegionOst;
import _03_excludes.predicates.RolleRegionSued;
import _03_excludes.predicates.RolleRegionWest;
import _03_excludes.predicates.RolleZentrale;
import de.heinerkuecker.constraint.AbstractConstraintCodeGenerator;
import de.heinerkuecker.constraint.Exclude;
import de.heinerkuecker.constraint.ExcludeGroup;

/**
 * Code-Generator für 
 * Constraint-Expression-Java-Klassen
 * zur Prüfung von Constraints
 * zur Compile-Zeit.
 *
 * Achtung Achtung Achtung:
 * die main-Methode dieser Klasse schreibt auf Deine Platte,
 * also sichere Deine Daten und schaue Dir die Pfade genau an !!!
 * 
 * @author Heiner K&uuml;cker
 */
public final class ExcludesConstraintCodeGenerator
{
    /**
     * sich ausschliessende Prädikate
     */
    private static final Exclude<Rolle> exclude =
// TODO Übungsaufgabe Stuttgarter Testtage 2013
//            new Exclude<Rolle>(
//                    new ExcludeGroup<Rolle>(
//                            "Ebene" ,
//                            //isClosedWorldAssumption
//                            true ,
//                            new RolleEbeneZentrale() ,
//                            new RolleEbeneNiederlassung() ,
//                            new RolleEbeneFiliale() ) ,
//                    new ExcludeGroup<Rolle>(
//                            "Region" ,
//                            //isClosedWorldAssumption
//                            true ,
//                            new RolleRegionNord() ,
//                            new RolleRegionSued() ,
//                            new RolleRegionWest() ,
//                            new RolleRegionOst() ) );
            null;
    
    /**
     * Start Generierung.
     * 
     * @param args unused
     * @throws IOException rethrown
     */
    public static void main(
            final String [] args )
    throws IOException
    {
        new AbstractConstraintCodeGenerator<Rolle>(
                //cacheFolder
                null ,
                //srcDirPathStr
                System.getProperty(
                        "user.dir" ) +
                File.separator +
                "src" , 
                //packageName
                "_03_excludes.constraints" ,
                //importStrArr ,
                new String[]{ 
                    "_03_excludes.enums.*" ,
                    "_03_excludes.predicates.*" } ,
                //contextObjClassName
                "Rolle" , 
                //contextObjName
                "rolle" ,
                //writeToDisc
                true ,
                //deleteUnusedConstraintJavaFiles
                true , 
                exclude )
        {{
// TODO Übungsaufgabe Stuttgarter Testtage 2013
//            add(
//                    new RolleZentrale() );

// TODO Übungsaufgabe Stuttgarter Testtage 2013
//            add(
//                    new RolleEbeneZentrale() );

// TODO Übungsaufgabe Stuttgarter Testtage 2013
//            add(
//                    new RolleNiederlassung() );

// TODO Übungsaufgabe Stuttgarter Testtage 2013
//            add(
//                    new RolleEbeneNiederlassung() );

// TODO Übungsaufgabe Stuttgarter Testtage 2013
//            add(
//                    new RolleNiederlassungNord() );

            add(
                    new RolleFiliale() );

            add(
                    new RolleEbeneFiliale() );

            add(
                    new RolleFilialeNord() );

// TODO Übungsaufgabe Stuttgarter Testtage 2013
//            add(
//                    new RolleRegionNord() );

// TODO Übungsaufgabe Stuttgarter Testtage 2013
//            add(
//                    or(
//                            new RolleZentrale() ,
//                            new RolleNiederlassung() ) );

            add(
                    not(
                            new RolleFiliale() ) );

            add(
                    and(
                            not( new RolleZentrale() ) ,
                            not( new RolleNiederlassung() ) ) );

            add(
                    or(
                            new RolleEbeneZentrale() ,
                            new RolleEbeneNiederlassung() ) );

// TODO Übungsaufgabe Stuttgarter Testtage 2013
//            add(
//                    not(
//                            new RolleEbeneFiliale() ) );
        }}.execute();
    }

}