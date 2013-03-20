package _04_safemember;

import java.io.File;
import java.io.IOException;

import _04_safemember.enums.Rolle;
import _04_safemember.predicates.RolleEbeneFiliale;
import _04_safemember.predicates.RolleEbeneNiederlassung;
import _04_safemember.predicates.RolleEbeneZentrale;
import _04_safemember.predicates.RolleFiliale;
import _04_safemember.predicates.RolleFilialeNord;
import _04_safemember.predicates.RolleNiederlassung;
import _04_safemember.predicates.RolleNiederlassungNord;
import _04_safemember.predicates.RolleRegionNord;
import _04_safemember.predicates.RolleRegionOst;
import _04_safemember.predicates.RolleRegionSued;
import _04_safemember.predicates.RolleRegionWest;
import _04_safemember.predicates.RolleZentrale;
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
public final class SafeMememberConstraintCodeGenerator
{
    /**
     * sich ausschliessende Prädikate
     */
    private static final Exclude<Rolle> exclude =
            new Exclude<Rolle>(
                    new ExcludeGroup<Rolle>(
                            "Ebene" ,
                            //isClosedWorldAssumption
                            true ,
                            new RolleEbeneZentrale() ,
                            new RolleEbeneNiederlassung() ,
                            new RolleEbeneFiliale() ) ,
                    new ExcludeGroup<Rolle>(
                            "Region" ,
                            //isClosedWorldAssumption
                            true ,
                            new RolleRegionNord() ,
                            new RolleRegionSued() ,
                            new RolleRegionWest() ,
                            new RolleRegionOst() ) );
    
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
                "_04_safemember.constraints" ,
                //importStrArr ,
                new String[]{ 
                    "_04_safemember.enums.*" ,
                    "_04_safemember.predicates.*" } ,
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
            add(
                    new RolleZentrale() );

// TODO Übungsaufgabe Stuttgarter Testtage 2013
//            add(
//                    new RolleEbeneZentrale() );

            add(
                    new RolleNiederlassung() );

// TODO Übungsaufgabe Stuttgarter Testtage 2013
//            add(
//                    new RolleEbeneNiederlassung() );

// TODO Übungsaufgabe Stuttgarter Testtage 2013
//            add(
//                    new RolleNiederlassungNord() );

            add(
                    new RolleFiliale() );

// TODO Übungsaufgabe Stuttgarter Testtage 2013
//            add(
//                    new RolleEbeneFiliale() );

            add(
                    new RolleFilialeNord() );

// TODO Übungsaufgabe Stuttgarter Testtage 2013
//            add(
//                    new RolleRegionNord() );

// TODO Übungsaufgabe Stuttgarter Testtage 2013
//            add(
//                    new RolleRegionSued() );
        }}.execute();
    }

}