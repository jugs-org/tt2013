package de.heinerkuecker.constraint;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;

import de.heinerkuecker.constraint.util.coll.BidiPairHashmap;
import de.heinerkuecker.constraint.util.coll.HashsetHashmap;
import de.heinerkuecker.constraint.util.coll.HashsetWithReadonly;
import de.heinerkuecker.constraint.util.coll.ReadOnlyMap;

/**
 * @author Heiner K&uuml;cker
 */
public final class ConstraintCodeGeneratorCache<CT>
implements Serializable
{
    public final File cacheFolder;
    
    public final String codegeneratorClassName;
    
    /*default*/ final Exclude<CT> exclude;
    
    /**
     * Map zum Vermerken von welchen Prädikaten
     * ein bestimmtes Prädikat includiert wird.
     * 
     * TODO mit in den Cache einbauen
     */
    ///*default*/ final HashsetHashmap<PrimitivPredicate<CT>, PrimitivPredicate<CT>> isIncludeByMap = new HashsetHashmap<PrimitivPredicate<CT>, PrimitivPredicate<CT>>();
    /*default*/ final IsIncludedByMap<CT> isIncludedByMap =
            new IsIncludedByMap<CT>();

    /**
     * Map zum Vermerken der als kompatibel geprüften
     * Prädikat-Expressions für bessere Performance.
     */
    /*default*/ final HashsetHashmap<Predicate<CT>, Predicate<CT>> compatibleTree;
    
    /**
     * 
     */
    /*default*/ final HashsetHashmap<Predicate<CT>, Predicate<CT>> incompatibleCache;


    /*default*/ final BidiPairHashmap<Predicate<CT>> equivalentPredicateCache;

    /*default*/ final BidiPairHashmap<Predicate<CT>> inequivalentPredicateCache;
    
    /*default*/ final HashMap<Predicate<CT>, Predicate<CT>> specializedPredicateCache;

    /**
     * Prädikate, die nicht Und-Verknüpft werden können
     * ohne eine Kontradiktion(nich erfüllbar) zu erzeugen.
     */
    // TODO noch von/auf Platte lesen/schreiben
    /*default*/ final BidiPairHashmap<Predicate<CT>> contradictionPredicateCache = new BidiPairHashmap<Predicate<CT>>();

    /**
     * Set für alle primitiven Prädikate zur Ermittlung closed-world-assumption.
     * Muss leider veränderlich sein.
     */
    /*default*/ final HashsetWithReadonly<PrimitivPredicate<CT>> allPrimitivPredicateSet = new HashsetWithReadonly<PrimitivPredicate<CT>>();
    
    /**
     * Map von den inkludierten Prädikaten (Set) zu den inkludierenden Prädiaten
     */
    /*default*/ final HashsetHashmap<HashSet<PrimitivPredicate<CT>>, PrimitivPredicate<CT>> includedPredSetToPredicateMap = new HashsetHashmap<HashSet<PrimitivPredicate<CT>>, PrimitivPredicate<CT>>();
    
    
    /**
     * Map mit der Information, ob das jeweilige primitive Prädikat durch seine inkludierten Prädikate abgebildet werden kann.
     * @see PredicateClosedIncludeChecker
     */
    /*default*/ /*final*/ ReadOnlyMap<PrimitivPredicate<CT>, Boolean> predicateToIsIncludeClosedMap;

    /**
     * Konstruktor.
     * 
     * @param cacheFolder
     */
    @SuppressWarnings( "rawtypes" )
    public ConstraintCodeGeneratorCache( 
            final File cacheFolder ,
            final String codegeneratorClassName ,
            final Exclude<CT> exclude )
    {
        this.cacheFolder = cacheFolder;
        this.codegeneratorClassName = codegeneratorClassName;
        this.exclude = exclude;

        //Prädikate aus dem Exclude in die IsIncludedByMap übertragen
        exclude.addAllPredicatesToIsIncludedByMap(
                this.isIncludedByMap );

        // alle im Exclude aufgetauchten primitven Prädikate vermerken
        exclude.addAllPredicatesToPrimitivPredicateSet(
                this );

        final File cacheFile;

        if ( 
                // es wird kein Cache benutzt
                cacheFolder == null ||
                // oder Cache-Datei existiert noch nicht
                false == ( cacheFile =
                        cacheFile( 
                                cacheFolder , 
                                codegeneratorClassName ) ).exists() )
        {
            compatibleTree =
                    new HashsetHashmap<Predicate<CT>, Predicate<CT>>();

            incompatibleCache =
                    new HashsetHashmap<Predicate<CT>, Predicate<CT>>();

            equivalentPredicateCache =
                    new BidiPairHashmap<Predicate<CT>>();

            inequivalentPredicateCache =
                    new BidiPairHashmap<Predicate<CT>>();

            specializedPredicateCache =
                    new HashMap<Predicate<CT>, Predicate<CT>>();
        }
        else
        {
            System.out.println( "read cache file " + cacheFile );

            ObjectInputStream inStream = null;

            HashsetHashmap<Predicate<CT>, Predicate<CT>> tmpCompatibleTree = null;

            BidiPairHashmap<Predicate<CT>> tmpEquivalentPredicateCache = null;

            HashsetHashmap<Predicate<CT>, Predicate<CT>> tmpIncompatibleCache = null;

            BidiPairHashmap<Predicate<CT>> tmpInequivalentPredicateCache = null;
            
            HashMap<Predicate<CT>, Predicate<CT>> tmpSpecializedPredicateCache = null;

            try
            {
                inStream =
                        new ObjectInputStream(
                                new FileInputStream(
                                        cacheFile ) );

                tmpCompatibleTree =
                        (HashsetHashmap) inStream.readObject();
                
                tmpIncompatibleCache =
                        (HashsetHashmap) inStream.readObject();

                tmpEquivalentPredicateCache =
                        (BidiPairHashmap) inStream.readObject();

                tmpInequivalentPredicateCache =
                        (BidiPairHashmap) inStream.readObject();
                
                tmpSpecializedPredicateCache =
                        (HashMap) inStream.readObject();
            }
            catch ( Exception ex )
            { 
                System.err.println( "ignored error on reading cache file: " + ex.getMessage() );
                ex.printStackTrace();
            }
            finally
            {
                if ( inStream != null )
                {
                    try
                    {
                        inStream.close();
                    }
                    catch ( Exception excOnClose )
                    {
                        System.err.println( "ignored error on closing cache file: " + excOnClose.getMessage() );
                        excOnClose.printStackTrace();
                    }
                }
            }

            if ( tmpCompatibleTree != null )
                // Einlesen ist gelungen
            {
                this.compatibleTree = 
                        tmpCompatibleTree;
            }
            else
            {
                compatibleTree =
                        new HashsetHashmap<Predicate<CT>, Predicate<CT>>();
            }

            if ( tmpIncompatibleCache != null )
                // Einlesen ist gelungen
            {
                this.incompatibleCache = 
                        tmpIncompatibleCache;
            }
            else
            {
                this.incompatibleCache =
                        new HashsetHashmap<Predicate<CT>, Predicate<CT>>();
            }

            if ( tmpEquivalentPredicateCache != null )
                // Einlesen ist gelungen
            {
                equivalentPredicateCache = 
                        tmpEquivalentPredicateCache;
            }
            else
            {
                equivalentPredicateCache =
                        new BidiPairHashmap<Predicate<CT>>();
            }

            if ( tmpInequivalentPredicateCache != null )
                // Einlesen ist gelungen
            {
                inequivalentPredicateCache = 
                        tmpInequivalentPredicateCache;
            }
            else
            {
                inequivalentPredicateCache =
                        new BidiPairHashmap<Predicate<CT>>();
            }

            if ( tmpSpecializedPredicateCache != null )
                // Einlesen ist gelungen
            {
                specializedPredicateCache =
                        tmpSpecializedPredicateCache;
            }
            else
            {
                specializedPredicateCache =
                        new HashMap<Predicate<CT>, Predicate<CT>>();
            }
        }
    }

    /**
     * 
     */
    public void save()
    {
        if ( this.cacheFolder == null )
            // es wird kein Cache benutzt
        {
            return;
        }

        final File cacheFile =
                cacheFile( 
                        cacheFolder , 
                        codegeneratorClassName );

        System.out.println( "save " + cacheFile );

        try
        {
            final ObjectOutputStream outStream = 
                    new ObjectOutputStream(
                            new FileOutputStream(
                                    cacheFile ) );

            outStream.writeObject(
                    this.compatibleTree );

            outStream.writeObject(
                    this.incompatibleCache );

            outStream.writeObject(
                    this.equivalentPredicateCache );

            outStream.writeObject(
                    this.inequivalentPredicateCache );

            outStream.writeObject(
                    this.specializedPredicateCache );

            outStream.flush(); 
            outStream.close(); 
        }
        catch ( IOException ex )
        { 
            System.err.println( ex.getMessage() );
            ex.printStackTrace();
        }
    }

    /**
     * This method creates the file object
     * for the cache file.
     * 
     * @param cacheFolder Folder
     * @param codegeneratorClassName file name without postfix
     * @return cache file object
     */
    private File cacheFile(
            final File cacheFolder ,
            final String codegeneratorClassName )
    {
        final String codegeneratorClassNameWithoutPackage;
        if ( codegeneratorClassName.contains( "." ) )
        {
            codegeneratorClassNameWithoutPackage =
                    codegeneratorClassName.substring(
                            codegeneratorClassName.lastIndexOf( "." ) + 1 );
        }
        else
        {
            codegeneratorClassNameWithoutPackage =
                    codegeneratorClassName;
        }
        
        return new File(
                cacheFolder.getPath() +
                File.separator +
                codegeneratorClassNameWithoutPackage +
                ".cache" );
    }
    
    /*default*/ void addPrimitivPredicate(
            final PrimitivPredicate<CT> predToAdd )
    {
        this.allPrimitivPredicateSet.add( predToAdd );
        
        if ( predToAdd.includePredicateSet != null &&
                false == predToAdd.includePredicateSet.isEmpty() )
        {
            this.includedPredSetToPredicateMap.put(
                    // TODO eventuell ist das einfache includePredicateSet nicht eindeutig, sondern es solte die Methode getIncludePredicateSetRecursiv verwendet werden
                    (HashSet<PrimitivPredicate<CT>>) predToAdd.includePredicateSet ,
                    predToAdd );
        }
    }

    /*default*/ void addPrimitivPredicates(
            final Iterable<PrimitivPredicate<CT>> predToAddIbl )
    {
        for ( final PrimitivPredicate<CT> predToAdd : predToAddIbl )
        {
            addPrimitivPredicate( predToAdd );
        }
    }
}
