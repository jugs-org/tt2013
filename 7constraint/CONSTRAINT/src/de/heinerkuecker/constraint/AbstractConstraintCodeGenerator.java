package de.heinerkuecker.constraint;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import de.heinerkuecker.constraint.util.CcgReflectionUtil;
import de.heinerkuecker.constraint.util.StringUtil;
import de.heinerkuecker.constraint.util.coll.ReadOnlySet;
import de.heinerkuecker.constraint.util.coll.ReadOnlySetAdaptor;
import de.heinerkuecker.constraint.util.exc.UnreachableCodeException;
import de.heinerkuecker.constraint.util.lang.LazyInitializer;
import de.heinerkuecker.constraint.util.lang.SeparatedBuffer;

/**
 * Abstrakte Oberklasse aller Constraint-Code-Genratoren,
 * auch derer, die in einer {@link ConstraintCodeGeneratorSuite}
 * zusammengefasst sind.
 *
 * @param <CT> Type-Parameter, Typ des Context-Objekts der Prädikate und Constraint-Klassen
 * @author Heiner K&uuml;cker
 */
public abstract class AbstractConstraintCodeGenerator<CT>
{
    /**
     * Wurzelverzeichnis (bezogen auf Package-Struktur)
     * des Verzeichnisses, in welches der generierte
     * Code gespeichert werden soll.
     */
    protected final String srcDirPathStr;

    /**
     * in dieses Package sollen die Java-Klassen
     * generiert werden.
     */
    public final String packageName;

    /**
     * In dieses Verzeichnis sollen die
     * Constraint-Klassen-Java-Dateien
     * entsprechend dem Package generiert
     * werden.
     */
    protected final String srcDirPackagePathStr;
            //SRC_DIR_PATH_STR +
            //File.separator +
            //PACKAGE_NAME.replace( '.' , File.separatorChar );

    /**
     * Import-Anweisungen, welche mit
     * generiert werden sollen.
     */
    protected final String[] importStrArr;

    /**
     * Name der Klasse des Kontext-Objekts,
     * anhand wechem alle Prüfungen erfolgen.
     */
    protected final String contextObjClassName;

    /**
     * Name der Instanz des Kontext-Objekts,
     * anhand welchem alle Prüfungen erfolgen.
     */
    protected final String contextObjName;
        //"contextObj";

    /**
     * Schalter, ob das Schreiben der generierten Dateien
     * auf die festplatte erfolgen soll.
     * <br/>
     * Dieser Schalter steht im normalen Betrieb auf
     * true, nur für Unit-Tests sollte false gesetzt
     * werden.
     */
    protected final boolean writeToDisc;

    /**
     * Ob nach dem Generieren unbenutzte Java-Klassen im 
     * Ziel-Verzeichnis gelöscht werden sollen.
     * <br/>
     * Dadurch werden bei schrittweisem veränderten der
     * Constraint-Definition entfallene Constraint-Java-Klassen 
     * gelöscht.
     * <br/>
     * Achtung, wen dieser Schalter auf <code>true</code> gesetzt ist,
     * dürfen sich in dem Ziel-Package/-Verzeichnis keine anderen
     * (nicht generierte) Klassen befinden, da diese gelöscht werden.
     */
    protected final boolean deleteUnusedConstraintJavaFiles;

    /**
     * sich ausschliessende Prädikate
     */
    //befindet sich jetzt im Cache: protected final Exclude<CT> exclude;

    /**
     * Postfix 'Constraint' für Constraint-Klassen, die nur ein Prädikat enthalten
     * und deshalb mit dem Prädikat in Namens-Konflikt geraten würden
     */
    private static final String CONSTRAINT_CLASS_POSTFIX =
        "Constraint";

    /**
     * Trenn-String zwischen den einzelnen Ausprägungs-Namen
     * im Klassen-Namen der generierten Klassen
     */
    private static final String CLASS_NAME_SEPARATOR =
        "_";

    /**
     * Collection mit allen Prädikaten,
     * zu denen Constraint-Klassen
     * generiert werden sollen.
     */
    private final Collection<SwitchablePredicate<CT>> allPredicateExpressionColl =
            new ArrayList<SwitchablePredicate<CT>>();

    /**
     * Collection mit allen Prädikaten,
     * zu denen Set-Constraint-Klassen
     * generiert werden sollen.
     */
    private final Collection<SetConstraintDefinition<CT>> allSetConstraintDefColl =
            new ArrayList<SetConstraintDefinition<CT>>();

    /**
     * Cache
     */
    /*default*/ final ConstraintCodeGeneratorCache<CT> cache;
    
    /**
     * Globaler Schalter zum Wiederholen der Anlyse-Schritte,
     * wenn durch {@link #createDynamicSafeOperations}
     * neue Constraints zur Liste hinzugekommen sind.
     */
    private boolean repeatExecutionForDynamicSafeOperations;
    
    /**
     * ob es nur primitive Praädikat bzw nur Prädikate mit And-Verknüpfung gibt,
     * Performance-Hilfe für die Methode {@link #getEquivalentExpression}.
     */
    private boolean hasOnlyAnd = true;

    /**
     * Konstruktor.
     *
     * @param cacheFolder File-Objekt des Cache-Verzeichnisses,
     *                    kann null sein, wenn der Cache nicht
     *                    genutzt werden soll
     * @param srcDirPathStr
     * @param packageName
     * @param importStrArr
     * @param contextObjClassName
     * @param contextObjName
     * @param writeToDisc ob die generierten Constraint-Java-Dateien auf die Festplatte geschrieben werden sollen, der Wert false ist für Unit-Tests vorgesehen
     * @param deleteUnusedConstraintJavaFiles ob nicht genutzte Java-Klassen im Ziel-Verzeichnis gelöscht werden sollen
     * @param exclude
     */
    public AbstractConstraintCodeGenerator(
            final File cacheFolder ,
            final String srcDirPathStr ,
            final String packageName ,
            final String[] importStrArr ,
            final String contextObjClassName ,
            final String contextObjName ,
            final boolean writeToDisc ,
            final boolean deleteUnusedConstraintJavaFiles ,
            final Exclude<CT> exclude )
    {
        System.out.println( "create " + this.codegeneratorClassname.get() );

        this.cache =
                new ConstraintCodeGeneratorCache<CT>(
                        cacheFolder ,
                        this.codegeneratorClassname.get() ,
                        exclude );

        this.srcDirPathStr = srcDirPathStr;

        this.packageName = packageName;

        this.srcDirPackagePathStr =
                srcDirPathStr +
                File.separator +
                packageName.replace(
                        '.' ,
                        File.separatorChar );

        this.importStrArr = importStrArr;

        this.contextObjClassName = contextObjClassName;

        this.contextObjName = contextObjName;

        this.writeToDisc = writeToDisc;

        this.deleteUnusedConstraintJavaFiles = deleteUnusedConstraintJavaFiles;
    }

    /**
     *
     * @param predicateToAdd
     */
    protected void add(
            //final SwitchablePredicate<CT> predicateToAdd
            final Predicate<CT> predicate ,
            final SwitchDefinition<CT>... switchDefinitionArr )
    {
        System.out.println( "add predicate " + predicate );

        if ( predicate.isContradiction(
                this.cache ) )
        {
            throw new IllegalStateException(
                    "predicate is contradiction(not satisfiable): " +
                    predicate +
                    " LineNr " + callerLineNr() );
        }

        checkConstraint( predicate );

        if ( predicate.hasOr() ||
                predicate.hasNot() )
        {
            //TODO includes beachten
            this.hasOnlyAnd = false;
        }

        this.cache.isIncludedByMap.addAll(
                predicate.getPrimitivPredicateSet() );

        this.cache.addPrimitivPredicates(
                predicate.getPrimitivPredicateSet() );

        this.allPredicateExpressionColl.add(
                //predicateToAdd
                switchablePredicate(
                        callerLineNr() ,
                        predicate ,
                        // safeSetArr
                        (SafeSet[]) null ,
                        switchDefinitionArr ) );
    }

    /**
     *
     * @param predicateToAdd
     */
    protected void add(
            //final SwitchablePredicate<CT> predicateToAdd
            final Predicate<CT> predicate ,
            final SafeSet<CT, ?> safeSet ,
            final SwitchDefinition<CT>... switchDefinitionArr )
    {
        System.out.println( "add predicate " + predicate );

        if ( predicate.isContradiction(
                //exclude ,
                cache ) )
        {
            throw new IllegalStateException(
                    "predicate is contradiction(not satisfiable): " +
                    predicate +
                    " LineNr " + callerLineNr() );
        }

        checkConstraint( predicate );

        if ( predicate.hasOr() ||
                predicate.hasNot() )
        {
            //TODO includes beachten
            this.hasOnlyAnd = false;
        }

        this.cache.isIncludedByMap.addAll(
                predicate.getPrimitivPredicateSet() );

        this.cache.addPrimitivPredicates(
                predicate.getPrimitivPredicateSet() );

        this.allPredicateExpressionColl.add(
                //predicateToAdd
                switchablePredicate(
                        callerLineNr() ,
                        predicate ,
                        new SafeSet[]{ safeSet } ,
                        switchDefinitionArr ) );
    }

    /**
     *
     * @param predicateToAdd
     */
    protected void add(
            final Predicate<CT> predicate ,
            //final SafeSet<CT, ?> safeSet ,
            final AbstractSafeOperation<CT>[] safeOperationArr ,
            final SwitchDefinition<CT>... switchDefinitionArr )
    {
        System.out.println( "add predicate " + predicate );

        if ( predicate.isContradiction(
                this.cache ) )
        {
            throw new IllegalStateException(
                    "predicate is contradiction(not satisfiable): " +
                    predicate +
                    " LineNr " + callerLineNr() );
        }
        
        checkConstraint( predicate );
        
        if ( predicate.hasOr() ||
                predicate.hasNot() )
        {
            //TODO includes beachten
            this.hasOnlyAnd = false;
        }

        this.cache.isIncludedByMap.addAll(
                predicate.getPrimitivPredicateSet() );

        this.cache.addPrimitivPredicates(
                predicate.getPrimitivPredicateSet() );

        this.allPredicateExpressionColl.add(
                //predicateToAdd
                switchablePredicate(
                        callerLineNr() ,
                        predicate ,
                        (SafeSet<CT, ?>[]) null ,
                        safeOperationArr ,
                        switchDefinitionArr ) );
    }

    /**
     *
     * @param predicateToAdd
     */
    protected void add(
            //final SwitchablePredicate<CT> predicateToAdd
            final Predicate<CT> predicate ,
            final SafeSet<CT, ?> safeSet ,
            final AbstractSafeOperation<CT>[] safeOperationArr ,
            final SwitchDefinition<CT>... switchDefinitionArr )
    {
        System.out.println( "add predicate " + predicate );

        if ( predicate.isContradiction(
                //exclude ,
                cache ) )
        {
            throw new IllegalStateException(
                    "predicate is contradiction(not satisfiable): " +
                    predicate +
                    " LineNr " + callerLineNr() );
        }

        checkConstraint( predicate );

        if ( predicate.hasOr() ||
                predicate.hasNot() )
        {
            //TODO includes beachten
            this.hasOnlyAnd = false;
        }

        this.cache.isIncludedByMap.addAll(
                predicate.getPrimitivPredicateSet() );

        this.cache.addPrimitivPredicates(
                predicate.getPrimitivPredicateSet() );

        this.allPredicateExpressionColl.add(
                //predicateToAdd
                switchablePredicate(
                        callerLineNr() ,
                        predicate ,
                        new SafeSet[]{ safeSet } ,
                        safeOperationArr ,
                        switchDefinitionArr ) );
    }

    /**
     * Hilfsmethode zum Aufbau einer spezialisierbaren Prädikat-Expression
     *
     * @param predicate Prädikat-Expression
     * @param switchDefinitionArr Spezialisierungs-Definitionen
     * @return SwitchablePredicate
     */
    private SwitchablePredicate<CT> switchablePredicate(
            final int callerLineNr ,
            final Predicate<CT> predicate ,
            final SafeSet<CT, ?>[] safeSetArr ,
            final SwitchDefinition<CT>... switchDefinitionArr )
    {
        return new SwitchablePredicate<CT>(
                predicate.lineNr > 0
                    ? predicate.lineNr
                    : callerLineNr ,
                predicate ,
                safeSetArr ,
                switchDefinitionArr );
    }

    /**
     * Hilfsmethode zum Aufbau einer spezialisierbaren Prädikat-Expression
     *
     * @param predicate Prädikat-Expression
     * @param safeOperationArr Array mit Definitionen sicherer Operationen
     * @param switchDefinitionArr Spezialisierungs-Definitionen
     * @return SwitchablePredicate
     */
    private SwitchablePredicate<CT> switchablePredicate(
            final int callerLineNr ,
            final Predicate<CT> predicate ,
            final AbstractSafeOperation<CT>[] safeOperationArr ,
            final SwitchDefinition<CT>... switchDefinitionArr )
    {
        return new SwitchablePredicate<CT>(
                predicate.lineNr > 0
                    ? predicate.lineNr
                    : callerLineNr ,
                predicate ,
                // safeSetArr
                null ,
                safeOperationArr ,
                switchDefinitionArr );
    }

    /**
     * Hilfsmethode zum Aufbau einer spezialisierbaren Prädikat-Expression
     *
     * @param predicate Prädikat-Expression
     * @param safeOperationArr Array mit Definitionen sicherer Operationen
     * @param switchDefinitionArr Spezialisierungs-Definitionen
     * @return SwitchablePredicate
     */
    private SwitchablePredicate<CT> switchablePredicate(
            final int callerLineNr ,
            final Predicate<CT> predicate ,
            final SafeSet<CT, ?>[] safeSetArr ,
            final AbstractSafeOperation<CT>[] safeOperationArr ,
            final SwitchDefinition<CT>... switchDefinitionArr )
    {
        return new SwitchablePredicate<CT>(
                predicate.lineNr > 0
                    ? predicate.lineNr
                    : callerLineNr ,
                predicate ,
                safeSetArr ,
                safeOperationArr ,
                switchDefinitionArr );
    }

    /**
     * Hilfsmethode zum Aufbau einer spezialisierbaren Prädikat-Expression
     *
     * @param predicate Prädikat-Expression
     * @param safeOperationArr Array mit Definitionen sicherer Operationen
     * @param switchDefinitionArr Spezialisierungs-Definitionen
     * @return SwitchablePredicate
     */
    private SwitchablePredicate<CT> switchablePredicate(
            final int callerLineNr ,
            final Predicate<CT> predicate ,
            final SafeSet<CT, ? extends ToJavaStringable> safeSet ,
            final AbstractSafeOperation<CT>[] safeOperationArr ,
            final SwitchDefinition<CT>... switchDefinitionArr )
    {
        return new SwitchablePredicate<CT>(
                predicate.lineNr > 0
                    ? predicate.lineNr
                    : callerLineNr ,
                predicate ,
                new SafeSet[]{ safeSet } ,
                safeOperationArr ,
                switchDefinitionArr );
    }

    /**
     * Hilfsmethode zum Aufbau einer spezialisierbaren Prädikat-Expression
     *
     * @param predicate Prädikat-Expression
     * @param safeOperationArr Array mit Definitionen sicherer Operationen
     * @param switchDefinitionArr Spezialisierungs-Definitionen
     * @return SwitchablePredicate
     */
    private SwitchablePredicate<CT> switchablePredicate(
            final int callerLineNr ,
            final Predicate<CT> predicate ,
            final SafeSet<CT, ? extends ToJavaStringable> safeSet ,
            final AbstractSafeOperation<CT>[] safeOperationArr )
    {
        return new SwitchablePredicate<CT>(
                predicate.lineNr > 0
                    ? predicate.lineNr
                    : callerLineNr ,
                predicate ,
                new SafeSet[]{ safeSet } ,
                safeOperationArr ,
                //switchDefinitionArr
                null );
    }

    /**
     * Hilfsmethode zum Aufbau von Und-verknüpften Prädikat-Expressions.
     *
     * @param predicateArr vararg Und-zu-verknüpfende Prädikate
     * @return verknüpfte Prädikate
     */
    protected /*Predicate*//*And*/Predicate<CT> and(
            final Predicate<CT>... predicateArr )
    {
        return
                //new And<CT>(
                And.instanceOf(
                        callerLineNr() ,
                        //this.exclude ,
                        this.cache ,
                        //cleanForImplicits
                        //false ,
                        predicateArr );
    }

    /**
     * Hilfsmethode zum Aufbau von Oder-verknüpften Prädikat-Expressions.
     *
     * @param predicateArr vararg Oder-zu-verknüpfende Prädikate
     * @return verknüpfte Prädikate
     */
    protected /*Predicate*//*Or*/Predicate<CT> or(
            final Predicate<CT>... predicateArr )
    {
        return
                //new Or<CT>(
                Or.instanceOf(
                        callerLineNr() ,
                        this.cache ,
                        predicateArr );
    }

    /**
     * Hilfsmethode zum Aufbau von negierten Prädikat-Expressions.
     *
     * @param predicateToNegate zu negierendes Prädikate
     * @return negiertes Prädikate
     */
    protected Predicate<CT> not(
            final Predicate<CT> predicateToNegate )
    {
        return Not.instanceOf(
                callerLineNr() ,
                predicateToNegate );
    }

    /**
     * Hilfsmethode zum Aufbau von Entweder-Oder-verknüpften Prädikat-Expressions.
     *
     * @param predicateArr vararg Oder-zu-verknüpfende Prädikate
     * @return verknüpfte Prädikate
     */
    protected /*Predicate*/Xor<CT> xor(
            final Predicate<CT>... predicateArr )
    {
        return new Xor<CT>(
                callerLineNr() ,
                predicateArr );
    }

    protected void add(
            final SetOr<CT> setOr ,
            final AbstractSafeOperation<CT>... safeOperationArr )
    {
        System.out.println( "add set predicate " + setOr.predicate );

        if ( setOr.predicate.isContradiction(
                //this.exclude ,
                cache ) )
        {
            throw new IllegalStateException(
                    "predicate is contradiction(not satisfiable): " +
                            setOr.predicate +
                    " LineNr " + callerLineNr() );
        }

        checkConstraint( setOr.predicate );

        if ( setOr.predicate.hasOr() ||
                setOr.predicate.hasNot() )
        {
            //TODO includes beachten
            this.hasOnlyAnd = false;
        }

        this.cache.isIncludedByMap.addAll(
                setOr.predicate.getPrimitivPredicateSet() );

        this.cache.addPrimitivPredicates(
                setOr.predicate.getPrimitivPredicateSet() );

        allSetConstraintDefColl.add(
                new SetConstraintDefinition<CT>(
                        setOr.predicate ,
                        safeOperationArr ) );
    }

    /**
     * Diese Methode erzwingt das Generieren eines
     * Set-Constraint {@link SetConstraintDefinition},
     * welches als Parameter ein {@link HashSet} von
     * Kontext-Objekten verlangt, das genau die
     * übergebenen Kontext-Objekte enthält.
     * (also kein Constraint auf ein Kontext-Objekt)
     *
     * @param predicateArr Prädikate mit den Kontext-Objekt-Werten/-Instanzen, welche im zu prüfenden Set enthalten sein müssen
     * @return Hilfs-Objekt zum Erzeugen des Set-Constraint
     */
    protected <T extends ToJavaStringable> SetOr<CT> equalHashset(
            final SafesetPrimitivPredicate<CT, T>... predicateArr )
    {
        return
                new SetOr<CT>(
                        (Or<CT>) Or.instanceOf(
                                callerLineNr() ,
                                this.cache ,
                                predicateArr ) );
    }

    /**
     * Ermitteln Zeilen-Nummer im aufrufenden Code
     * @return Zeilen-Nummer im aufrufenden Code
     */
    public static int callerLineNr()
    {
        final Exception exc = new Exception();
        final StackTraceElement[] stckTrcArr = exc.getStackTrace();
        final int callerLineNr = stckTrcArr[ 2 ].getLineNumber();

        return callerLineNr;
    }

    /**
     * Hilfsmethode zum Aufbau von AbstractSwitches
     * für Prädikat-Expressions.
     *
     * @param predicateArr vararg Oder-zu-verknüpfende Prädikate
     * @return verknüpfte Prädikate
     */
    protected SwitchDefinition<CT> switchDef(
            final String name ,
            final String caseMethodReturnTypeName ,
            final String caseMethodExceptionsStr ,
            final Predicate<CT>... switchPredicateArr )
    {
        return new SwitchDefinition<CT>(
                name ,
                caseMethodReturnTypeName ,
                caseMethodExceptionsStr ,
                this.cache.exclude ,
                switchPredicateArr );
    }

    /**
     * Liste zum Sammeln der ursprünglichen Constraint-Expressions und 
     * der für die AbstractSwitch-Klassen zu generierenden 
     * Constraint-Expressions.
     * 
     * Hier sind noch die Constraint-Expressions für sichere
     * Operationen hinzugekommen.
     */
    //private final FirstInsertOrderHashset<SwitchablePredicate<CT>> allAndSwitchCasePredicateExpressionColl = new FirstInsertOrderHashset<SwitchablePredicate<CT>>();
    // Map für beschleunigten Zugriff in methode getEquivalentPredicate
    private final HashMap<Predicate<CT>, SwitchablePredicate<CT>> allAndSwitchCasePredicateExpressionMap = new HashMap<Predicate<CT>, SwitchablePredicate<CT>>();

    /**
     * Methode für den Aufruf aus {@link ConstraintCodeGeneratorSuite}
     */
    /*default*/ void prepareBeforeSafeoperations()
    {
        System.out.println( "prepare before safe operations " + this.codegeneratorClassname.get() );

        // Liste zum Sammeln der ursprünglichen Constraint-Expressions und der für die AbstractSwitch-Klassen zu generierenden Constraint-Expressions
        //this.allAndSwitchCasePredicateExpressionColl = new FirstInsertOrderHashset<SwitchablePredicate<CT>>( this.allPredicateExpressionColl );
        //this.allAndSwitchCasePredicateExpressionColl.addAll( this.allPredicateExpressionColl );
        for ( final SwitchablePredicate<CT> switchablePredticateToAdd : this.allPredicateExpressionColl )
        {
            this.allAndSwitchCasePredicateExpressionMap.put(
                    switchablePredticateToAdd.predicate ,
                    switchablePredticateToAdd );
        }

        //for ( final SetConstraintDefinition<CT> setConstraintDefinition : allSetConstraintDefColl )
        //    // für jedes Set-Constraint die Generierung eines entsprechenden Or-Constraints erzwingen
        //{
        //    reuseOrCreatePredicate(
        //            setConstraintDefinition.predicate ,
        //            //parentPredicate
        //            null ,
        //            allAndSwitchCasePredicateExpressionColl );
        //}

        for ( final SwitchablePredicate<CT> switchablePredicate : this./*allAndSwitchCasePredicateExpressionColl*/allAndSwitchCasePredicateExpressionMap.values() )
        {
            if ( switchablePredicate.predicate.isContradiction(
                    this.cache ) )
            {
                throw new IllegalStateException(
                        "predicate is contradiction(not satisfiable): " +
                        switchablePredicate.predicate +
                        " LineNr " + switchablePredicate.predicate.lineNr );
            }

            collectConstraintExpressionsForAbstractSwitch(
                    switchablePredicate
                    //this.allAndSwitchCasePredicateExpressionColl
                    );
            
            if ( this.repeatExecutionForDynamicSafeOperations )
            {
                // neue Runde, sonst tritt eine ConcurrentModificationException auf
                return;
            }
        }
    }

    /**
     * Methode für den Aufruf aus {@link ConstraintCodeGeneratorSuite}
     */
    /*default*/ void addDynamicSafeOperations()
    {
        System.out.println( "add dynamic safe operations " + this.codegeneratorClassname.get() );

        // Schleife über alle Switch-Definitionen zum Einsammeln dynamischer sicherer Operationen
        // aus entstandenen dynamischen sicheren Operationen können wiederum neue dynamische sichere Operationen entstehen
        // Testfall: _15_dynamic_transform_safeoperation_tic_tac_toe.DynamicTransformSafeOperationTicTacToeConstraintCodeGenerator
        //boolean hasPredicatesNotYetCheckedForDynamicSafeOperations = true;
        
        //while ( hasPredicatesNotYetCheckedForDynamicSafeOperations )
        {
            // wenn keine dynamischen sichere Operationen auftauchen, kann die Schleife beendet werden
            //hasPredicatesNotYetCheckedForDynamicSafeOperations = false;

            for ( final SwitchablePredicate<CT> switchablePredicate : this./*allAndSwitchCasePredicateExpressionColl*/allAndSwitchCasePredicateExpressionMap.values() )
            {
                if ( false == switchablePredicate.isDynamicSafeOperationsAdded )
                {
                    collectConstraintExpressionsForDynamicSafeOperations(
                            switchablePredicate );

                    //hasPredicatesNotYetCheckedForDynamicSafeOperations = true;
                    //neue Runde
                    //break;
                }
            }
        }
    }

    /**
     * Methode für den Aufruf aus {@link ConstraintGeneratorSuite}
     */
    /*default*/ void prepareSafeoperations(
            final ConstraintCodeGeneratorSuite suite )
    {
        System.out.println( "prepare safe operations for " + this.codegeneratorClassname.get() );

        for ( final SwitchablePredicate<CT> switchablePredicate : this./*allAndSwitchCasePredicateExpressionColl*/allAndSwitchCasePredicateExpressionMap.values() )
        {
            if ( false == switchablePredicate.isDynamicSafeOperationsAdded )
            {
                collectConstraintExpressionsForDynamicSafeOperations(
                        switchablePredicate );
            }

            collectConstraintExpressionsForSafeOperations(
                    switchablePredicate.lineNr ,
                    switchablePredicate.predicate ,
                    switchablePredicate.safeOperationArr ,
                    //this.allAndSwitchCasePredicateExpressionColl ,
                    suite );
            
            if ( this.repeatExecutionForDynamicSafeOperations )
            {
                // neue Runde, sonst tritt eine ConcurrentModificationException auf
                return;
            }
        }

        for ( final SetConstraintDefinition<CT> setConstraintDefinition : this.allSetConstraintDefColl )
            // Set-Constraints generieren
        {
            collectConstraintExpressionsForSafeOperations(
                    setConstraintDefinition.predicate.lineNr ,
                    setConstraintDefinition.predicate ,
                    setConstraintDefinition.safeOperationArr ,
                    //this.allAndSwitchCasePredicateExpressionColl ,
                    suite );
        }
    }

    /**
     * Methode für den Aufruf aus {@link ConstraintGeneratorSuite}
     */
    /*default*/ void generate()
    throws IOException
    {
        System.out.println( "generate constraint files for " + this.codegeneratorClassname.get() );

        // es wird davon ausgegangen, dass alle vorhandenen Java-Klassen im Ziel-Verzeichnis nicht mehr benöigt werden
        // falls dort noch andere Java-Dateien sind (keine Constraints) muss der Parameter deleteUnusedConstraintJavaFiles auf false stehen
        final HashSet<String> unusedConstraintSrcFileNameSet =
                collectExistingConstraintSrcFiles();

        // Map  mit Zeilen-Nummer zum Vermeiden des doppelten/mehrfachen Generierens des gleichen Constraints
        final HashMap<String, Integer> generatedConstraintFileNameToLinenrMap =
                new HashMap<String, Integer>();

        for ( final SwitchablePredicate<CT> switchablePredicate : this./*allAndSwitchCasePredicateExpressionColl*/allAndSwitchCasePredicateExpressionMap.values() )
        {
            System.out.println( "generate constraint class for predicate: " + switchablePredicate.predicate );
            
            if ( false == switchablePredicate.isDynamicSafeOperationsAdded )
            {
                throw new IllegalStateException(
                        "dynamic safe operations not added: " +
                        switchablePredicate.predicate +
                        " LineNr " + switchablePredicate.lineNr );
            }

            if ( switchablePredicate.predicate.isContradiction(
                    //this.exclude ,
                    this.cache ) )
            {
                throw new IllegalStateException(
                        "predicate is contradiction(not satisfiable): " +
                        switchablePredicate.predicate +
                        " LineNr " + switchablePredicate.predicate.lineNr );
            }

            final String generatedConstraintFileName =
                    generateConstraintFile(
                            //this.allAndSwitchCasePredicateExpressionColl ,
                            switchablePredicate ,
                            this.codegeneratorClassname.get() );

            if ( generatedConstraintFileNameToLinenrMap.containsKey(
                    generatedConstraintFileName ) )
                // mehrfache Generierung des gleichen Constraints verhindern
            {
                throw new IllegalStateException(
                        "constraint class already generated: " +
                        switchablePredicate.predicate +
                        " LineNr " + switchablePredicate.predicate.lineNr + " " +
                        " LineNr " + generatedConstraintFileNameToLinenrMap.get(
                                generatedConstraintFileName ) );
            }

            generatedConstraintFileNameToLinenrMap.put(
                    generatedConstraintFileName ,
                    switchablePredicate.predicate.lineNr );

            unusedConstraintSrcFileNameSet.remove(
                    generatedConstraintFileName + ".java" );
        }

        for ( final SetConstraintDefinition<CT> setConstraintDefinition : this.allSetConstraintDefColl )
            // Set-Constraints generieren
        {
            System.out.println( "generate set constraint class for predicate: " + setConstraintDefinition.predicate );

            if ( setConstraintDefinition.predicate.isContradiction(
                    //exclude ,
                    this.cache ) )
            {
                throw new IllegalStateException(
                        "predicate is contradiction(not satisfiable): " +
                        setConstraintDefinition.predicate +
                        " LineNr " + setConstraintDefinition.predicate.lineNr );
            }

            final String generatedConstraintFileName =
                    SetConstraintCodeGenerator.generateSetConstraintFile(
                            this.writeToDisc ,
                            setConstraintDefinition ,
                            packageName ,
                            importStrArr ,
                            contextObjClassName ,
                            contextObjName ,
                            codegeneratorClassname.get() ,
                            srcDirPackagePathStr );

            unusedConstraintSrcFileNameSet.remove(
                    generatedConstraintFileName + ".java" );
        }

        // Löschen nicht mehr benötigter Constraint-Klassen
        for ( final String existingConstraintFileName : unusedConstraintSrcFileNameSet )
        {
            if ( this.deleteUnusedConstraintJavaFiles )
            {
                System.out.println( "delete unused constraint java files: " + existingConstraintFileName );
                new File(
                        this.srcDirPackagePathStr +
                        File.separator +
                        existingConstraintFileName ).delete();
            }
            else
            {
                System.out.println( "unused constraint class: " + existingConstraintFileName );
            }
        }

        this.cache.save();

        System.out.println( "end of generation " + codegeneratorClassname.get() );
    }

    /**
     * Start Generierung.
     *
     * @throws IOException rethrown
     */
    public final void execute()
    throws IOException
    {
        final long startTime = System.currentTimeMillis();

        System.out.println( "start " + this.codegeneratorClassname.get() );

        checkForAlreadyDefinedConstraints();

        this.repeatExecutionForDynamicSafeOperations = true;
        
        while ( this.repeatExecutionForDynamicSafeOperations )
        {
            this.repeatExecutionForDynamicSafeOperations = false;

            prepareBeforeSafeoperations();

            // Vorbereiten dynamische SafeOperations
            addDynamicSafeOperations();
            
            prepareSafeoperations(
                    //suite in diesem Fall nicht vorhanden
                    null );
        }

        generate();

        System.out.println(
                "Duration " +
                ( ( System.currentTimeMillis() - startTime ) / 1000 ) +
                " seconds" );
    }

    /**
     * Klassen-Name(inklusive Package) des aktuellen Code-Generators
     */
    /*default*/ final LazyInitializer<String> codegeneratorClassname =
            new LazyInitializer<String>()
            {
                @Override
                protected String init()
                {
                    final String codegeneratorClassRawName =
                            AbstractConstraintCodeGenerator.this.getClass().getName();

                    return codegeneratorClassRawName.contains( "$" )
                            ? codegeneratorClassRawName.substring(
                                    0 ,
                                    // Abschneiden des Namens-Teiles hinter dem $-Zeichen wegen anonymer innerer Klasse
                                    codegeneratorClassRawName.indexOf( "$" ) )
                            : codegeneratorClassRawName;
                }
            };

    /**
     * Prüfen auf doppelt/mehrfach definierte Constraint-Expressions
     */
    private void checkForAlreadyDefinedConstraints()
    {
        final HashMap<String, Integer> definedConstraintexpressionstrToLineNrMap =
                new HashMap<String, Integer>();

        for ( final SwitchablePredicate<CT> switchablePredicate : this.allPredicateExpressionColl )
        {
            final String constraintExpressionStr =
                    switchablePredicate.predicate.toString();

            if ( definedConstraintexpressionstrToLineNrMap.containsKey( constraintExpressionStr ) )
            {
                throw new IllegalStateException(
                        "constraint class already defined: " +
                        switchablePredicate.predicate +
                        " LineNr " + switchablePredicate.predicate.lineNr + " " +
                        " LineNr " + definedConstraintexpressionstrToLineNrMap.get(
                                constraintExpressionStr ) );
            }

            definedConstraintexpressionstrToLineNrMap.put(
                    constraintExpressionStr ,
                    switchablePredicate.predicate.lineNr );
        }
    }

    /**
     * Sammeln bereits vorhandener Constraint-Files.
     * @return
     */
    private HashSet<String> collectExistingConstraintSrcFiles()
    {
        final File srcDirPackageDirFile =
                new File(
                        srcDirPackagePathStr );

        if ( false == srcDirPackageDirFile.exists() )
        {
        	throw new IllegalStateException(
        			"source folder not exists: " + srcDirPackagePathStr );
        }

        final File[] existingConstraintFileArr =
                srcDirPackageDirFile.listFiles();

        final HashSet<String> existingConstraintFileNameSet =
                new HashSet<String>();

        if ( existingConstraintFileArr == null )
        {
            return existingConstraintFileNameSet;
        }

        for ( final File existingConstraintFile : existingConstraintFileArr )
        {
            if ( existingConstraintFile.getName().endsWith( ".java" ) )
            {
                existingConstraintFileNameSet.add(
                        existingConstraintFile.getName() );
            }
        }

        return existingConstraintFileNameSet;
    }

    /**
     * Diese Methode sucht für die AbstractSwitch-Klassen passende Constraint-Expressions
     * in der übergebenen Liste und fügt diese bei Fehlen hinzu.
     *
     * @param switchablePredicate
     * @param allAndSwitchCasePredicateExpressionColl
     */
    private void collectConstraintExpressionsForAbstractSwitch(
            final SwitchablePredicate<CT> switchablePredicate
            //final Collection<SwitchablePredicate<CT>> allAndSwitchCasePredicateExpressionColl
            )
    {
        //System.out.println( "$FileName<.\\src\\de\\heinerkuecker\\constraint\\AbstractConstraintCodeGenerator.java> $LineNr: 1058: " + switchablePredicate.predicate + " LineNr " + switchablePredicate.predicate.lineNr );
        if ( switchablePredicate.switchDefinitionArr == null ||
                switchablePredicate.switchDefinitionArr.length < 1 )
            // kein AbstractSwitch
        {
            return;
        }
        
        //System.out.println( "collectConstraintExpressionsForAbstractSwitch " + switchablePredicate.predicate );

        // verknüpfe das allgemeine (zu spezialisierende) Prädikat mit dem Spezialisierungs-Prädikat zum spezielleren Prädikat
        // suche in den vorhandenen Constraint-Expressions nach einem äquivalenten Prädikat
        // wenn vorhanden, verwenden, wenn nicht vorhanden, generieren
        for ( final SwitchDefinition<CT> switchDefinition : switchablePredicate.switchDefinitionArr )
        {
            // Durchlaufen der Switch-Case-Prädikate
            if ( switchDefinition.casePredicateTupleArr != null )
            {
                for ( final CasePredicateSpecializedPredicateTuple<CT> casePredicateTuple : switchDefinition.casePredicateTupleArr )
                {
                    reuseOrCreateSwitchPredicate(
                            switchablePredicate ,
                            //allAndSwitchCasePredicateExpressionColl ,
                            switchDefinition ,
                            casePredicateTuple );
                }
            }

            switchDefinition.isCovering =
                    BruteForceSolver.checkSwitchPredicateForCoveringAndDisjointness(
                            switchablePredicate.predicate ,
                            switchablePredicate.lineNr ,
                            //CasePredicateSpecializedPredicateTuple.mapToParamPredicateNotSimplifiedForExcludeArr(
                            CasePredicateSpecializedPredicateTuple.mapToParamPredicateSimplifiedForExcludeArr(
                                    switchDefinition.casePredicateTupleArr ) ,
                            //this.exclude ,
                            this.cache ,
                            switchDefinition.casePredicateTupleArr ,
                            this.codegeneratorClassname.get() );
        }
    }
    
    /**
     * check for develop
     */
    //private final HashSet<String> alreadyDynamicSafeOperationsExpressionStrSet = new HashSet<String>();

    /**
     * Diese Methode sucht für die dynamisch hinzuzufügenden
     * sicheren Operationen passende Constraint-Expressions
     * in der übergebenen Liste und fügt diese bei Fehlen hinzu.
     *
     * @param switchablePredicate
     */
    private void collectConstraintExpressionsForDynamicSafeOperations(
            final SwitchablePredicate<CT> switchablePredicate )
    {
        if ( switchablePredicate.isDynamicSafeOperationsAdded )
        {
            return;
        }

        final Iterable<AbstractSafeOperation<CT>> dynamicSafeOperations =
                createDynamicSafeOperations(
                        switchablePredicate.predicate );
        
        if ( dynamicSafeOperations == null )
            // keine dynamischen sicheren Operationen hinzuzufügen
        {
            switchablePredicate.isDynamicSafeOperationsAdded = true;
            return;
        }
        
        System.out.println(
                "add dynamic safe operations to " +
                switchablePredicate.predicate + 
                " LineNr " + switchablePredicate.lineNr );

        //if ( this.alreadyDynamicSafeOperationsExpressionStrSet.contains( switchablePredicate.predicate.toString() ) )
        //{
        //    throw new RuntimeException();
        //}
        //this.alreadyDynamicSafeOperationsExpressionStrSet.add( switchablePredicate.predicate.toString() );
        //System.out.println( this.alreadyDynamicSafeOperationsExpressionStrSet.size() );
        
        final List<AbstractSafeOperation<CT>> safeOperationList = new ArrayList<AbstractSafeOperation<CT>>();
        if ( switchablePredicate.safeOperationArr != null )
        {
            safeOperationList.addAll(
                    Arrays.asList(
                            switchablePredicate.safeOperationArr ) );
        }
        
        for ( final AbstractSafeOperation<CT> safeOperationToAdd : dynamicSafeOperations )
        {
            safeOperationList.add(
                    safeOperationToAdd );
        }
        
        switchablePredicate.safeOperationArr =
                safeOperationList.toArray( new AbstractSafeOperation[ safeOperationList.size() ] );
        
        switchablePredicate.isDynamicSafeOperationsAdded = true;
        
        this.repeatExecutionForDynamicSafeOperations = true;
    }

    /**
     *
     * @param switchablePredicate
     * @param allAndSwitchCasePredicateExpressionColl
     * @param switchDefinition
     * @param casePredicateTuple
     */
    private void reuseOrCreateSwitchPredicate(
            final SwitchablePredicate<CT> switchablePredicate ,
            //final Collection<SwitchablePredicate<CT>> allAndSwitchCasePredicateExpressionColl ,
            final SwitchDefinition<CT> switchDefinition ,
            final CasePredicateSpecializedPredicateTuple<CT> casePredicateTuple )
    {
        if ( casePredicateTuple.paramPredicateSimplifiedForExclude != null )
        {
            // bereits erledigt
            return;
        }

        final Predicate<CT> predicateToSwitch =
                switchablePredicate.predicate;

        final Predicate<CT> specializedPredicate =
                specializePredicate(
                        //allAndSwitchCasePredicateExpressionColl ,
                        predicateToSwitch ,
                        casePredicateTuple.casePredicate ,
                        switchDefinition.casePredicateTupleArr );

        if ( specializedPredicate == null )
        {
            throw new IllegalArgumentException(
                    "specializedPredicate is null" );
        }

        //System.out.println( "specializedPredicate: " + specializedPredicate );

        final Predicate<CT> reusedOrCreatedPredicate =
                reuseOrCreatePredicate(
                        specializedPredicate ,
                        predicateToSwitch
                        //allAndSwitchCasePredicateExpressionColl
                        );

        casePredicateTuple.paramPredicateSimplifiedForExclude =
                reusedOrCreatedPredicate;
    }

    /**
     * Für den Aufruf von {@link #collectConstraintExpressionsForSafeOperations},
     * aber aus einem anderen Constraint-Code-Generator
     *
     * @param searchedPredicate
     * @return
     */
    private Predicate<CT> reuseOrCreatePredicate(
            final Predicate<?> searchedPredicate )
    {
        if ( CcgReflectionUtil.typeParamArrOfObj( searchedPredicate ).equals( this.getCtxObjType() ) )
        {
            throw new IllegalArgumentException(
                    "predicate has wrong context object type " +
                            CcgReflectionUtil.typeParamArrOfObj( searchedPredicate ) + " " +
                            "for constraint code generator " +
                            this.codegeneratorClassname.get() + " " +
                            "of type " + this.getCtxObjType() );
        }

        return
                reuseOrCreatePredicate(
                        (Predicate<CT>) searchedPredicate ,
                        //parentPredicate
                        null
                        //allAndSwitchCasePredicateExpressionColl
                        //this.allAndSwitchCasePredicateExpressionColl
                        );
    }

    /**
     *
     * @param searchedPredicate
     * @param parentPredicate
     * @param allAndSwitchCasePredicateExpressionColl
     * @return
     */
    private Predicate<CT> reuseOrCreatePredicate(
            final Predicate<CT> searchedPredicate ,
            final Predicate<CT> parentPredicate
            //final Collection<SwitchablePredicate<CT>> allAndSwitchCasePredicateExpressionColl
            )
    {
        final Predicate<CT> reusedOrCreatedPredicate;

        final Predicate<CT> equivalentPredicate =
                getEquivalentExpression(
                        searchedPredicate
                        //allAndSwitchCasePredicateExpressionColl
                        );

        if ( equivalentPredicate != null )
            // äquivalentes Prädikat vorhanden, dieses wird verwendet, damit keine unnötigen äquivalenten Constraint-Klassen erzeugt werden
        {
            reusedOrCreatedPredicate =
                    equivalentPredicate;
        }
        else
            // kein äquivalentes Prädikat vorhanden
        {
            //System.out.println( "new SwitchablePredicate for predicate: " + specializedPredicate );
            
            final Predicate<CT> simplifiedSearchedPredicate =
                    PredicateSimplifier.simplify(
                            searchedPredicate ,
                            this.cache );

            reusedOrCreatedPredicate =
                    simplifiedSearchedPredicate;

            final SwitchablePredicate<CT> createdPredicateSwitch =
                    new SwitchablePredicate<CT>(
                            searchedPredicate.lineNr ,
                            searchedPredicate ,
                            // safeSetArr
                            null ,
                            null );

            // aktuelles zu switchendes Predicate als Parent für bessere Performance in generateConvertMethodsStr vermerken
            createdPredicateSwitch.parentPredicate =
                    parentPredicate;

            if ( createdPredicateSwitch.predicate.hasOr() ||
                    createdPredicateSwitch.predicate.hasNot() )
                // bei jedem Hinzufügen Prüfen, ob ein Or bzw Not auftaucht, TODO besser in extra-Klasse kapseln
            {
                //TODO includes beachten
                this.hasOnlyAnd = false;
            }

            //allAndSwitchCasePredicateExpressionColl.add( createdPredicateSwitch );
            this.allAndSwitchCasePredicateExpressionMap.put(
                    createdPredicateSwitch.predicate ,
                    createdPredicateSwitch );
            
            //ConcurrentModificationException vermeiden
            this.repeatExecutionForDynamicSafeOperations = true;

            if ( false == createdPredicateSwitch.isDynamicSafeOperationsAdded )
            {
                collectConstraintExpressionsForDynamicSafeOperations(
                        createdPredicateSwitch );
            }
        }
        
        return reusedOrCreatedPredicate;
    }

    /**
     * TODO diese Methode ersetzen durch false == Predicate.isPrimitive
     * @param <CT>
     * @param predicateExpression
     * @return
     */
    private static <CT> boolean isNotAtom(
            final Predicate<CT> predicateExpression )
    {
        return predicateExpression instanceof And ||
                predicateExpression instanceof Or ||
                predicateExpression instanceof Not ||
                predicateExpression instanceof Xor ||
                predicateExpression instanceof ParametricalPredicate;
    }

    /**
     * Speichern eines String in einer Datei, aber nur wenn eine Änderung vorliegt.
     *
     * @param outStr auszugebender String
     * @param fileName Name (mit Pfad) der Datei, in welcher der String gespeichert werden soll
     * @throws IOException rethrown
     */
    /*default*/ static boolean saveFileIfChanged(
            final boolean writeToDisc ,
            final String outStr ,
            final String fileName ,
            final int lineNr )
    throws IOException
    {
        if ( false == writeToDisc )
        {
            System.out.println( "write to disc is off" );
            return false;
        }

        final boolean isSaved;
        if ( false == new File( fileName ).exists() )
        {
            strToFile( outStr , fileName );
            isSaved = true;
        }
        else
        {
            final String preFileContentStr =
                    fileToStr(
                            fileName );

            if ( false == preFileContentStr.equals( outStr ) )
            {
                strToFile( outStr , fileName );
                isSaved = true;
            }
            else
            {
                System.out.println( "unchanged" );
                isSaved = false;
            }
        }

        if ( isSaved )
        {
            System.out.println( "Line-Number " + lineNr );
        }

        return isSaved;
    }

    /**
     * Speichern eines String in einer Datei ohne weitere Informationen
     *
     * @param outStr auszugebender String
     * @param fileName Name (mit Pfad) der Datei, in welcher der String gespeichert werden soll
     * @throws IOException rethrown
     */
    private static void strToFile(
            final String outStr ,
            final String fileName )
    throws IOException
    {
        strToFile( outStr , new File( fileName ) );
    } // end method strToFile

    /**
     * Speichern eines String in einer Datei ohne weitere Informationen
     *
     * @param outStr auszugebender String
     * @param outFile Datei, in welcher der String gespeichert werden soll
     * @throws IOException rethrown
     */
    private static void strToFile(
            final String outStr ,
            final File outFile )
    throws IOException
    {
        System.out.println( outFile );

        // TODO try-catch-Block wieder entfernen
        //try{
            if ( outFile.exists() )
            {
                outFile.delete();
            }

            createNewFile( outFile );

            FileWriter outFileWriter = new FileWriter( outFile );

            outFileWriter.write( outStr );
            outFileWriter.close();
        //}catch ( Exception e ){
        //    System.err.println( e.getMessage() );
        //    e.printStackTrace();
        //}
    } // end method strToFile

    /**
     * Erzeugen einer neuen Datei . <br/>
     * Eine eventuell vorhandene gleichnamige Datei wird vorher gelöscht.<br/>
     * Falls notwendig, wird das Verzeichnis für die Datei angelegt.<br/>
     *
     * @param filePa anzulegende Datei
     * @throws IOException weiterwerfen Exception vom Erzeugen der Datei
     */
    private static void createNewFile(
            final File filePa )
    throws IOException
    {
        if ( filePa.exists() )
        {
            filePa.delete();
        }

        try
        {
            filePa.createNewFile();
        }
        catch ( IOException ioe )
        {
            // Versuch Verzeichnis anzulegen
            ( new File( filePa.getParent() ) ).mkdirs();
            // zweiter Versuch
            filePa.createNewFile();
        }// end catch
    }// end method createNewFile

    /**
     * Einlesen einer Datei in einen String
     *
     * @param  pInputFileNameWithPath Name der einzulesenden Datei
     * @return String mit Datei-Inhalt
     */
    public static String fileToStr(
            final String pInputFileNameWithPath )
    throws IOException
    {
  //System.out.println( "fileName: " + fileName );
      return fileToStr(new File(pInputFileNameWithPath));
    } // end method file2str

    /**
     * Einlesen einer Datei in einen String
     *
     * @param pInputFile einzulesende Datei
     * @return String mit Datei-Inhalt
     * @throws IOException rethrown
     */
    public static String fileToStr(
          final File pInputFile )
    throws IOException
    {
//      //System.out.println( "inFile: " + inFile );
//      String  strReturn = null;
//
//      try {
//        StringBuilder    strBuff = new StringBuilder(1024 * 16);
//
//        // BufferedReader zum zeilenweisen Lesen
//        //FileReader      defFileReader = new FileReader(inFile);
//        //BufferedReader  defBufferedReader = new BufferedReader(defFileReader);
//
//        FileInputStream   fin = new FileInputStream(pInputFile);
//        InputStreamReader isr = new InputStreamReader( fin );
//
//        BufferedReader    defBufferedReader = new BufferedReader( isr );
//
//        // TODO bei dieser Schleife wird am Ende der Datei immer eine Leerzeile angehängt, dies führt zu ständigem Speichern der generierten Dateien wegen Differenz
//        //String          readLine = null;
//        //while ((readLine = defBufferedReader.readLine()) != null) { // bis zum Ende der Datei
//        //  strBuff.append(readLine + "\n");
//        //} // end while
//
//        String readLine = defBufferedReader.readLine();
//        do
//        {
//            if ( readLine != null )
//            {
//                strBuff.append( readLine );
//            }
//            readLine = defBufferedReader.readLine();
//            if ( readLine != null )
//                // Zeilenumbruch nur anhängen, wenn es eine nächste Zeile gibt
//            {
//                strBuff.append( "\n" );
//            }
//        }
//        while ( readLine != null );
//
//        strReturn = strBuff.toString();
//      }
//      catch (Exception e)
//      {
//        System.err.println( e.getMessage() ); e.printStackTrace();
//      }
//      return strReturn;
        try
        // TODO try-catch-Block wieder entfernen
        {
            final FileReader fileReader = new FileReader( pInputFile );

            final char[] buffChrArr =
                    new char[
                             // TODO unchecked conversion
                             (int) pInputFile.length() ];

            if ( fileReader.read( buffChrArr ) != buffChrArr.length )
            {
                throw new RuntimeException();
            }

            final String fileStr = new String( buffChrArr );

            return fileStr;
        }
        catch ( Exception e )
        {
            System.err.println( e.getMessage() );
            e.printStackTrace();
        }
        return "";
    }// end method file2str

    /**
     * Methode zum Erzeugen des Java-Codes
     * für eine Java-Klasse für
     * die übergebene Constraint-Expression.
     *
     * @param constraintFileName Name des aktuell generierten Typs erlaubter Enum-Ausprägungen
     * @param allPredicateExpressionIterable Iterable über alle zu generierenden Constraint-Expression-Klassen
     * @param predicateExpression aktuell zu generierende Constraint-Expression
     * @param contextObjClassName Name der Klasse des Kontext-Objekts
     * @param <CT> Type-Parameter Kontext-Objekt-Typ
     * @throws IOException wenn ein Fehler auftritt
     */
    private String generateConstraintFile(
            //final Iterable<SwitchablePredicate<CT>> allSwitchablePredicateExpressionIterable ,
            final SwitchablePredicate<CT> switchablePredicate ,
            final String codegeneratorClassName )
    throws IOException
    {
        //if ( switchablePredicate.predicate.lineNr >= 830 )
        //{
        //    //System.out.println( "debug break" );
        //}
        final Predicate<CT> predicateExpression = switchablePredicate.predicate;
        final Predicate<CT> parentPredicate     = switchablePredicate.parentPredicate;

        // Name der zu generierenden Java-Klasse
        final String constraintFileName =
                constraintFileName(
                        predicateExpression );

        final String contextObjMemberStr =
                "\t/**\n" +
                "\t * Context object of type " +
                // eventuell vorhandene Grösser- bzw Kleiner-als-Zeichen von Typ-Parametern für Javadoc encoden
                StringUtil.encodeHtml(
                        this.contextObjClassName ) + ".\n" +
                        "\t */\n" +
                        "\tpublic final " + this.contextObjClassName + " " + this.contextObjName + ";\n\n";

        final HashSet<SafeMember> safememberSet =
                BruteForceSolver.computeValidSafemember(
                        //this.exclude ,
                        this.cache ,
                        predicateExpression );

        final String safememberStr =
                generateSafememberStr(
                        safememberSet );

        final List<SafesetTojavastrbleTupel<CT, ?>> safesetmemberTplList =
                computeValidSafesetList(
                        switchablePredicate );

        final String safesetStr =
                generateSafesetStr(
                        safesetmemberTplList );

        final String constructorStr =
                generateConstructorStr(
                        predicateExpression ,
                        safememberSet );

        final String testMethodStr =
                generateTestMethodStr(
                        predicateExpression );

        final String convertMethodsStr =
// TODO temporär ausgeschalten für DynamicTransformSafeOperationTicTacToeConstraintCodeGenerator, da gibt es keine kompatiblen Constraints
                generateConvertMethodsStr(
                        switchablePredicate.lineNr ,
                        predicateExpression ,
                        constraintFileName ,
                        //allSwitchablePredicateExpressionIterable ,
                        parentPredicate );

        final String constraintSafeSwitchClassStr =
                generateConstraintSafeSwitchClassesStr(
                        constraintFileName ,
                        switchablePredicate
                        //allSwitchablePredicateExpressionIterable
                        );

        final String safeOperationsStr =
                generateSafeOperationsStr(
                        //switchablePredicate
                        switchablePredicate.lineNr ,
                        switchablePredicate.predicate ,
                        switchablePredicate.safeOperationArr ,
                        this.contextObjName );

        final String toStringMethodStr =
                generateToStringMethodStr(
                        switchablePredicate.predicate );

        final StringBuilder fileStrBldr = new StringBuilder();
        fileStrBldr.append( "package " + packageName + ";\n\n" );

        for ( int i = 0 ; importStrArr != null && i < importStrArr.length ; i++ )
        {
            final String importStr = importStrArr[i];
            fileStrBldr.append( "import " + importStr + ";\n" );
        }
        if ( switchablePredicate.switchDefinitionArr != null &&
                switchablePredicate.switchDefinitionArr.length > 0 )
        {
            // Exception für Constraint-Safe-Switch
            fileStrBldr.append( "import " + UnreachableCodeException.class.getName() + ";\n" );
        }


        fileStrBldr.append( "\n" );

        fileStrBldr.append( "/**\n" );
        fileStrBldr.append( " * Constraint class of " + contextObjClassName + ".\n" );
        fileStrBldr.append( " * <pre>" + predicateExpression.toString() + ".</pre>\n" );
        fileStrBldr.append( " * Generated by {@link " + codegeneratorClassName + "}.\n" );

        fileStrBldr.append( " * Line-Number " + /*predicateExpression*/switchablePredicate.lineNr + ".\n" );

        fileStrBldr.append( " */\n" );

        fileStrBldr.append( "public final class " + constraintFileName + "\n" );
        fileStrBldr.append( "{\n" );
        fileStrBldr.append( contextObjMemberStr );
        fileStrBldr.append( safememberStr );
        fileStrBldr.append( safesetStr );
        fileStrBldr.append( constructorStr );
        fileStrBldr.append( testMethodStr );
        fileStrBldr.append( convertMethodsStr );
        fileStrBldr.append( constraintSafeSwitchClassStr );
        fileStrBldr.append( safeOperationsStr );
        fileStrBldr.append( toStringMethodStr );
        fileStrBldr.append( "}" );

        final boolean isSaved =
                saveFileIfChanged(
                        this.writeToDisc ,
                        fileStrBldr.toString() ,
                        srcDirPackagePathStr +
                        File.separator +
                        constraintFileName +
                        ".java" ,
                        switchablePredicate.lineNr );

        return constraintFileName;
    }

    /**
     *
     * @param allAndSwitchCasePredicateExpressionColl
     * @param predicateToSpecialize
     * @param casePredicate
     * @param casePredicateTupleArr
     * @return
     */
    private /*SpecializePredicateTuple<CT>*/Predicate<CT> specializePredicate(
            //final Collection<SwitchablePredicate<CT>> allAndSwitchCasePredicateExpressionColl ,
            final Predicate<CT> predicateToSpecialize ,
            final Predicate<CT> casePredicate ,
            final CasePredicateSpecializedPredicateTuple<CT>[] casePredicateTupleArr )
    {
        //System.out.println( "specializePredicate " + predicateToSpecialize + " for " + casePredicate );
        
        // TODO dies kann man nicht machen, weil es dann zu nicht erfüllbaren Constraints kommen kann, wenn in den einzelnen Zweigen gleiche Nebenbedingungen auftreten
        //// alle anderen Switch-Prädikate müssen negiert werden
        //final List<Predicate<CT>> notPredicateList = new ArrayList<Predicate<CT>>();
        //
        //for ( final CasePredicateSpecializedPredicateTuple<CT> runCasePredicateTuple : casePredicateTupleArr )
        //{
        //    if ( runCasePredicateTuple.casePredicate != casePredicate )
        //        // es ist nicht das aktuelle Switch-Prädikat
        //    {
        //        notPredicateList.add(
        //                // negieren
        //                not(
        //                        runCasePredicateTuple.casePredicate ) );
        //    }
        //}

        // das originale, das zu spezialisierende /*kann man nicht machen: und alle anderen spezialisierenden Prädikate negiert*/ zu einer neuen Prädikat-Expression zusammenfassen
        //final List<Predicate<CT>> andPredicateList = new ArrayList<Predicate<CT>>();

        //andPredicateList.add(
        //        // das zu spezialisierende Prädikat
        //        predicateToSpecialize );
        //
        //andPredicateList.add(
        //        // das Spezialisierungs-Prädikat
        //        casePredicate );

        // TODO dies kann man nicht machen, weil es dann zu nicht erfüllbaren Constraints kommen kann, wenn in den einzelnen Zweigen gleiche Nebenbedingungen auftreten
        //andPredicateList.addAll(
        //        // alle anderen Spezialisierungs-Prädikate negiert
        //        notPredicateList );

        final Predicate<CT> specializedPredicate =
                //and(
                //        //exclude ,
                //        (Predicate<CT>[]) andPredicateList.toArray( new Predicate<?>[ andPredicateList.size() ] ) );
                //new And<CT>(
                And.instanceOf(
                        casePredicate.lineNr > 0
                            ? casePredicate.lineNr
                            : predicateToSpecialize.lineNr ,
                        //this.exclude ,
                        this.cache ,
                        //cleanForImplicits
                        //true ,
                        //(Predicate<CT>[]) andPredicateList.toArray( new Predicate<?>[ andPredicateList.size() ] )
                        // das zu spezialisierende Prädikat
                        predicateToSpecialize ,
                        // das Spezialisierungs-Prädikat
                        casePredicate );
        
        if ( specializedPredicate.isContradiction( cache ) )
        {
            throw new SpecializedPredicateInSwitchDefinitionIsContradiction(
                    "specialized predicate " +
                    specializedPredicate +
                    " for case predicate " +
                    casePredicate +
                    "in switch is contradiction(not satisfiable): " +
                    predicateToSpecialize + 
                    " LineNr " + specializedPredicate.lineNr );
        }

        if ( cache.specializedPredicateCache.containsKey( specializedPredicate ) )
        {
            return cache.specializedPredicateCache.get( specializedPredicate );
        }

        //final Predicate<CT> simplifiedForExcludeSpecializedPredicate =
        //        BruteForceSolver.simplifyForExclude(
        //                specializedPredicate ,
        //                cache );

        //if ( false == BruteForceSolver.isEquivalent(
        //        cache ,
        //        specializedPredicate ,
        //        simplifiedForExcludeSpecializedPredicate ) )
        //    // check for develop
        //{
        //    throw new IllegalStateException(
        //            specializedPredicate.toString() +
        //            " not equal to " +
        //            simplifiedForExcludeSpecializedPredicate );
        //}

        //final Predicate<CT> simplifiedForIncludeExcludeSpecializedPredicate =
        //        //Normalizer.simplifyForInclude(
        //        BruteForceSolver.simplifyForInclude(
        //                simplifiedForExcludeSpecializedPredicate ,
        //                allAndSwitchCasePredicateExpressionColl ,
        //                //this.exclude ,
        //                this.cache );

        //if ( false == BruteForceSolver.isEquivalent(
        //        cache ,
        //        specializedPredicate ,
        //        simplifiedForIncludeExcludeSpecializedPredicate ) )
        //    // check for develop
        //{
        //    throw new IllegalStateException(
        //            specializedPredicate +
        //            " not equal to " +
        //            simplifiedForIncludeExcludeSpecializedPredicate );
        //}

        //cache.specializedPredicateCache.put(
        //        specializedPredicate ,
        //        simplifiedForIncludeExcludeSpecializedPredicate );

        //return simplifiedForIncludeExcludeSpecializedPredicate;

        final Predicate<CT> simplifiedSpecializedPredicate =
                PredicateSimplifier.simplify(
                        specializedPredicate ,
                        cache );
        
        if ( false == BruteForceSolver.isEquivalent(
                cache ,
                specializedPredicate ,
                simplifiedSpecializedPredicate ) )
            // check for develop
        {
            throw new IllegalStateException(
                    specializedPredicate +
                    " not equal to " +
                    simplifiedSpecializedPredicate );
        }

        cache.specializedPredicateCache.put(
                specializedPredicate ,
                simplifiedSpecializedPredicate );

        return simplifiedSpecializedPredicate;
    }

    /**
     * 
     * @param searchedPredicate
     * @param allAndSwitchCasePredicateExpressionColl
     * @return
     */
    private Predicate<CT> getEquivalentExpression(
            final Predicate<CT> searchedPredicate
            //final Collection<SwitchablePredicate<CT>> allAndSwitchCasePredicateExpressionColl
            )
    {
        // TODO folgendes geht nicht, dazu müsste es eine Map vom Predicate zum SwitchablePredicate geben
        // Performance-Hint, eventuell gibt es die identische Expression im Set
        final SwitchablePredicate<CT> equivalentSwblPredicateFromSet =
                this.allAndSwitchCasePredicateExpressionMap.get(
                        searchedPredicate );
        if ( equivalentSwblPredicateFromSet != null )
        {
            return equivalentSwblPredicateFromSet.predicate;
        }
        
        if ( this.hasOnlyAnd )
        {
            //System.out.println( "$FileName<.\\src\\de\\heinerkuecker\\constraint\\AbstractConstraintCodeGenerator.java> $LineNr: 1879: getEquivalentExpression return for hasOnlyAnd" );
            // wenn es keine Prädikate mit Or bzw Not gibt, kann hier abgebrochen werden
            return null;
        }
                
        for ( final SwitchablePredicate<CT> switchablePredicate : this./*allAndSwitchCasePredicateExpressionColl*/allAndSwitchCasePredicateExpressionMap.values() )
        {
            final Predicate<CT> predicateToCheck =
                    switchablePredicate.predicate;

            if ( BruteForceSolver.isEquivalent(
                    this.cache ,
                    searchedPredicate ,
                    predicateToCheck ) )
            {
                return predicateToCheck;
            }
        }

        // kein äquivalentes Prädikat gefunden
        return null;
    }

    private List<SafesetTojavastrbleTupel<CT, ?>> computeValidSafesetList(
            final SwitchablePredicate<CT> switchablePredicate )
    {
        final ArrayList<SafesetTojavastrbleTupel<CT, ?>> resultList = new ArrayList<SafesetTojavastrbleTupel<CT,?>>();

        if ( switchablePredicate.safeSetArr != null )
        {
            for ( final SafeSet<CT, ? extends ToJavaStringable> safesetDefinition : switchablePredicate.safeSetArr )
            {
                final SafesetTojavastrbleTupel<CT, ?> safesetMemberTupel =
                        BruteForceSolver.computeValidSafeset(
                                this.cache.exclude ,
                                switchablePredicate.predicate ,
                                safesetDefinition );

                resultList.add(
                        safesetMemberTupel );
            }
        }

        return resultList;
    }

    /**
     * Methode zum Erzeugen der
     * constraint-sicheren Member für
     * eine Constraint-Klasse.
     *
     * @param safememberSet Set mit den zu generierenden Safe-Membern
     * @return String mit dem Java-Code der erzeugten Safe-Member
     */
    private String generateSafememberStr(
            final HashSet<SafeMember> safememberSet )
    {
        final StringBuilder buff = new StringBuilder();

        for ( final SafeMember safeMember : safememberSet )
        {
            buff.append( "\t/**\n" );
            buff.append( "\t * Constraintsafe member " + safeMember.typStr + " " + safeMember.name + ".\n" );
            buff.append( "\t */\n" );

            buff.append( "\tpublic final " + safeMember.typStr + " " + safeMember.name + ";\n\n" );
        }

        return buff.toString();
    }

    /**
     * Methode zum Erzeugen der
     * constraint-sicheren Set für
     * eine Constraint-Java-Klasse.
     *
     * @param safesetMemberTplList Set mit den zu generierenden Safe-Sets
     * @return String mit dem Java-Code der erzeugten Safe-Sets
     */
    private String generateSafesetStr(
            final List<SafesetTojavastrbleTupel<CT, ?>> safesetMemberTplList )
    {
        final StringBuilder buff = new StringBuilder();

        for ( final SafesetTojavastrbleTupel<CT, ?> safesetMemberTpl : safesetMemberTplList )
        {
            buff.append( "\t/**\n" );
            buff.append(
                    "\t * Constraintsafe Set " +
                    // eventuell vorhandene Grösser- bzw Kleiner-als-Zeichen von Typ-Parametern für Javadoc encoden
                    StringUtil.encodeHtml(
                            safesetMemberTpl.safeSetDefinition.safeSetElementClassName ) + " " +
                    safesetMemberTpl.safeSetDefinition.name + ".\n" );
            buff.append( "\t */\n" );

            final SeparatedBuffer setElemJavastrBuff =
                    new SeparatedBuffer( " , " );

            for ( final ToJavaStringable toJavaStringable : safesetMemberTpl.tojavastrbleSet )
            {
                setElemJavastrBuff.add(
                        toJavaStringable.toJavaString() );
            }

            buff.append(
                    "\tpublic static final " + ReadOnlySet.class.getName() + "<" + safesetMemberTpl.safeSetDefinition.safeSetElementClassName + "> " +
                    safesetMemberTpl.safeSetDefinition.name + " = " +
                    "new " + ReadOnlySetAdaptor.class.getName() + "<" + safesetMemberTpl.safeSetDefinition.safeSetElementClassName + ">( " +
                    setElemJavastrBuff.toString() +
                    " );\n\n" );
        }

        return buff.toString();
    }

    /**
     * Methode zum Erzeugen des Konstruktors für
     * eine Constraint-Klasse.
     *
     * @param predicateExpression aktuell zu generierende Constraint-Expression
     * @param safememberSet SafeMember
     * @return String mit dem Java-Code der erzeugten Konstruktoren
     */
    private String generateConstructorStr(
            final Predicate<CT> predicateExpression ,
            final HashSet<SafeMember> safememberSet )
    {
        final StringBuilder buff = new StringBuilder();

        buff.append( "\t/**\n" );
        buff.append( "\t * Constructor.\n" );
        buff.append( "\t * \n" );
        buff.append( "\t * @param " + contextObjName + " current context object\n" );
        buff.append( "\t * @throws IllegalArgumentException if constraint violated\n" );
        //buff.append( "\t * @throws Exception if an error occurs\n" );
        buff.append( "\t */\n" );

        buff.append(
                "\tpublic " +
                constraintFileName(
                        predicateExpression ) +
                "(\n" );
        buff.append( "\t\t\tfinal " + contextObjClassName + " " + contextObjName + " )\n" );
        //buff.append( "\tthrows Exception\n" );
        buff.append( "\t{\n" );

        /*
         * Prüfen, ob der übergebene Parameter zulässig ist
         */
        buff.append( "\t\tif ( ! " );

        //buff.append(
        //        generateTestConstraintExpressionStr(
        //                predicateExpression ,
        //                "\t\t\t\t" ) );
        // statt Wiederholung aller Sub-Prädikate einfach Aufruf der statischen test-Methode
        buff.append(
                "test( " +
                contextObjName +
                " )" );

        buff.append( " )\n" );
        buff.append( "\t\t{\n" );
        //buff.append( "\t\t\tthrow new IllegalArgumentException( \"constraint violation: \" + String.valueOf( " + CONTEXT_OBJ_NAME + " ) );\n" );
        buff.append( "\t\t\tthrow new IllegalArgumentException( \"constraint violation\" );\n" );
        buff.append( "\t\t}\n" );

        buff.append( "\t\tthis." + contextObjName + " = " + contextObjName + ";\n" );

        for ( final SafeMember safeMember : safememberSet )
        {
            buff.append( 
                    "\t\tthis." + safeMember.name + " = (" + safeMember.typStr + ") " + 
                    //contextObjName +  safeMember.contextObjMemberOrGetterStr
                    safeMember.createContextObjMemberOrGetterStr( contextObjName ) + 
                    ";\n" );
        }

        buff.append( "\t}\n\n" );

        return buff.toString();
    }

    /**
     * Methode zum Erzeugen der test-Methode für
     * eine Constraint-Klasse.
     *
     * @param predicateExpression aktuell zu generierende Constraint-Expression
     * @param <CT> Type-Parameter Kontext-Objekt-Typ
     * @return String mit dem Java-Code der erzeugten test-Methode
     */
    private String generateTestMethodStr(
            final Predicate<CT> predicateExpression )
    {
        final StringBuilder buff = new StringBuilder();

        buff.append( "\t/**\n" );
        buff.append( "\t * Test method.\n" );
        buff.append( "\t * \n" );
        buff.append( "\t * @param " + contextObjName + " current context object\n" );
        buff.append( "\t * @return constraint expression satisfied or not\n" );
        //buff.append( "\t * @throws Exception if an error occurs\n" );
        buff.append( "\t */\n" );

        buff.append( "\tpublic static boolean test(\n" );
        buff.append( "\t\t\tfinal " + contextObjClassName + " " + contextObjName + " )\n" );
        //buff.append( "\tthrows Exception\n" );
        buff.append( "\t{\n" );

        /*
         * Prüfen, ob der übergebene Parameter die Constraint-Expression erfüllt
         */
        buff.append( "\t\treturn " );

        buff.append(
                generateTestConstraintExpressionStr(
                        predicateExpression ,
                        "\t\t\t\t" ) );

        buff.append( ";\n" );

        buff.append( "\t}\n\n" );

        return buff.toString();
    }

    /**
     * @param predicateExpression aktuell zu generierende Constraint-Expression
     * @param tabStr Tabulator(Einrückung)-String, wird rekursiv vergrössert
     * @return String mit dem Java-Code der erzeugten Test-Constraint-Expression
     */
    private String generateTestConstraintExpressionStr(
            final Predicate<CT> predicateExpression ,
            final String tabStr )
    {
        if ( predicateExpression instanceof And )
        {
            return generateAndTestConstraintExpressionStr(
                    (And<CT>) predicateExpression ,
                    tabStr + "\t" );
        }
        else if ( predicateExpression instanceof Or )
        {
            return generateOrTestConstraintExpressionStr(
                    (Or<CT>) predicateExpression ,
                    tabStr + "\t" );
        }
        else if ( predicateExpression instanceof Not )
        {
            return generateNotTestConstraintExpressionStr(
                    (Not<CT>) predicateExpression ,
                    tabStr + "\t" );
        }
        else if ( predicateExpression instanceof Xor )
        {
            return generateXorTestConstraintExpressionStr(
                    (Xor<CT>) predicateExpression ,
                    tabStr + "\t" );
        }
        else if ( predicateExpression instanceof ParametricalPredicate )
            // parametrisiertes Prädikat
        {
            final ParametricalPredicate<CT> parametricalPredicate = (ParametricalPredicate<CT>) predicateExpression;
            //return "new " + parametricalPredicate.toJavaConstructorStr() + ".test( " + contextObjName + " )";
            return parametricalPredicate.toJavaInstanceOfStr() + ".test( " + contextObjName + " )";
            // TODO Achtung, ein parametrisiertes Prädikat kann auch includierte Prädikate besitzen
        }
        else if ( predicateExpression instanceof PrimitivPredicate )
            // elementares (atomares) Prädikat
        {
            return "new " + predicateExpression + "().test( " + contextObjName + " )";
            // TODO eventuell auch Prüfung für die includierten Prädikate generieren
        }
        else
        {
            throw new UnreachableCodeException(
                    String.valueOf( predicateExpression ) );
        }
    }

    /**
     * @param predicateExpression aktuell zu generierende Constraint-Expression
     * @param <CT> Type-Parameter Kontext-Objekt-Typ
     * @param tabStr Tabulator(Einrückung)-String, wird rekursiv vergrössert
     * @return String mit dem Java-Code der erzeugten Test-Constraint-Expression
     */
    private String generateAndTestConstraintExpressionStr(
            final And<CT> predicateExpression ,
            final String tabStr )
    {
        final SeparatedBuffer sepBuff = new SeparatedBuffer( " &&\n" + tabStr );

        for ( final Predicate<CT> andPartPredicateExpression : predicateExpression )
        {
            sepBuff.add(
                    generateTestConstraintExpressionStr(
                            andPartPredicateExpression ,
                            tabStr ) );
        }

        return "( " + sepBuff + " )";
    }

    /**
     * @param predicateExpression aktuell zu generierende Constraint-Expression
     * @param <CT> Type-Parameter Kontext-Objekt-Typ
     * @param tabStr Tabulator(Einrückung)-String, wird rekursiv vergrössert
     * @return String mit dem Java-Code der erzeugten Test-Constraint-Expression
     */
    private String generateOrTestConstraintExpressionStr(
            final Or<CT> predicateExpression ,
            final String tabStr )
    {
        final SeparatedBuffer sepBuff = new SeparatedBuffer( " ||\n" + tabStr );

        for ( final Predicate<CT> orPartPredicateExpression : predicateExpression )
        {
            sepBuff.add(
                    generateTestConstraintExpressionStr(
                            orPartPredicateExpression ,
                            tabStr ) );
        }

        return "( " + sepBuff + " )";
    }

    /**
     * @param predicateExpression aktuell zu generierende Constraint-Expression
     * @param <CT> Type-Parameter Kontext-Objekt-Typ
     * @param tabStr Tabulator(Einrückung)-String, wird rekursiv vergrössert
     * @return String mit dem Java-Code der erzeugten Test-Constraint-Expression
     */
    private String generateNotTestConstraintExpressionStr(
            final Not<CT> predicateExpression ,
            final String tabStr )
    {
        return
            "( ! " +
            generateTestConstraintExpressionStr(
                predicateExpression.get() ,
                tabStr ) +
            " )";
    }

    /**
     * @param predicateExpression aktuell zu generierende Constraint-Expression
     * @param <CT> Type-Parameter Kontext-Objekt-Typ
     * @param tabStr Tabulator(Einrückung)-String, wird rekursiv vergrössert
     * @return String mit dem Java-Code der erzeugten Test-Constraint-Expression
     */
    private String generateXorTestConstraintExpressionStr(
            final Xor<CT> predicateExpression ,
            final String tabStr )
    {
        final SeparatedBuffer sepBuff = new SeparatedBuffer( " +\n" + tabStr );

        for ( final Predicate<CT> orPartPredicateExpression : predicateExpression )
        {
            sepBuff.add(
                    "( " +
                    generateTestConstraintExpressionStr(
                            orPartPredicateExpression ,
                            tabStr ) +
                    " ? 1 : 0 )");
        }

        return "( ( " + sepBuff + " ) == 1 )";
    }

    /**
     * Methode zum Erzeugen der Konvertierungs-Methoden zu
     * einem zum aktuellen Typ kompatiblen Typ.
     *
     * @param predicateExpression aktuell zu generierende Constraint-Expression
     * @param constraintFileName Name der zu generierenden Java-Klasse
     * @param allSwitchablePredicateExpressionIterable Iterable über alle zu generierenden Constraint-Expression-Klassen
     * @param <CT> Type-Parameter Kontext-Objekt-Typ
     * @return String mit dem Java-Code der erzeugten Konvertierungs-Methoden
     */
    private String generateConvertMethodsStr(
            final int lineNr ,
            final Predicate<CT> predicateExpression ,
            final String constraintFileName ,
            //final Iterable<SwitchablePredicate<CT>> allSwitchablePredicateExpressionIterable ,
            final Predicate<CT> parentPredicate )
    {
        final HashSet<String> alreadyConvertmethodGeneratedClassnameSet =
                new HashSet<String>();

        final String parentPredicateClassName;
        if ( parentPredicate != null )
        {
            parentPredicateClassName =
                    constraintFileName(
                            parentPredicate );
        }
        else
        {
            parentPredicateClassName = null;
        }

        //System.out.println( "$FileName<.\\src\\de\\heinerkuecker\\constraint\\AbstractConstraintCodeGenerator.java> $LineNr: 2279: " + predicateExpression + " " + ConstraintCodeGenerator.lineNrMsgStr( predicateExpression ) );
        final StringBuilder buff = new StringBuilder();

        for ( final SwitchablePredicate<CT> otherSwitchablePredicate : this./*allAndSwitchCasePredicateExpressionColl*/allAndSwitchCasePredicateExpressionMap.values() )
        {
// Code-Pattern als Hilfsnotiz
//          public RoleArbeiterLeiterInhaber convertToRoleArbeiterLeiterInhaber()
//          {
//              return new RoleArbeiterLeiterInhaber(
//                      this.value );
//          }
            final String otherConstraintFileName =
                otherSwitchablePredicate.predicate.toFileName(
                        CLASS_NAME_SEPARATOR ) +
                ( isNotAtom(
                        otherSwitchablePredicate.predicate )
                            ? ""
                            // Postfix nur bei Single-Constrainst an den Namen anhängen
                            : CONSTRAINT_CLASS_POSTFIX );

            if ( otherConstraintFileName.equals( constraintFileName ) )
                // convert auf das Gleiche/Selbe vermeiden
            {
                continue;
            }

            if ( alreadyConvertmethodGeneratedClassnameSet.contains( otherConstraintFileName ) )
            {
                // TODO hier Exception werfen, klären, warum dies auftritt
                continue;
            }

            alreadyConvertmethodGeneratedClassnameSet.add( otherConstraintFileName );

            //System.out.println( "$FileName<.\\src\\de\\heinerkuecker\\constraint\\AbstractConstraintCodeGenerator.java> $LineNr: 2313: isCompatibleTo: " + otherSwitchablePredicate.predicate + " " + ConstraintCodeGenerator.lineNrMsgStr( otherSwitchablePredicate.predicate ) );
            //if ( false == ( isCompatibleTo( predicateExpression , otherSwitchablePredicate.predicate , exclude ) ) )
            if (
                    // eine spezialisierende Expression im Switch ist immer kompatibel zu ihrer Parent-Expression (Performance-Verbesserung)
                    false == ( parentPredicateClassName != null &&
                               parentPredicateClassName.equals( constraintFileName ) ) &&
                    //// Spezial-Logik parametrisierbares Prädikat TODO ist auch im BruteForceCompatibleChecker eingebaut
                    //false == ( predicateExpression instanceof ParametricalPredicate &&
                    //        // dynamische Kompatibilität
                    //        ( (ParametricalPredicate) predicateExpression ).isCompatibleTo(
                    //                otherSwitchablePredicate.predicate ) ) &&
                    // nur wenn die aktuelle Expression kompatibel zur anderen Expression ist kann eine Konvertierung erfolgen
                    false == ( BruteForceCompatibleChecker.isCompatibleTo(
                            this.cache ,
                            predicateExpression ,
                            otherSwitchablePredicate.predicate ) ) )
            {
                continue;
            }

            // Kompatibilität für bessere Performance vermerken
            cache.compatibleTree.put(
                    predicateExpression ,
                    otherSwitchablePredicate.predicate );

            buff.append( "\t/**\n" );
            buff.append( "\t * Convert this constraint type to a compatible type of this type.\n" );
            buff.append( "\t * <pre>" + otherSwitchablePredicate.predicate.toString() + ".</pre>\n" );
            buff.append( "\t * \n" );
            buff.append( "\t * @return compatible type of this type\n" );
            //buff.append( "\t * @throws Exception if an error occurs\n" );
            buff.append( "\t */\n" );
            buff.append( "\tpublic " + otherConstraintFileName + " convertTo" + otherConstraintFileName + "()\n" );
            //buff.append( "\tthrows Exception\n" );
            buff.append( "\t{\n" );
            buff.append( "\t\treturn new " + otherConstraintFileName + "(\n" );
            buff.append( "\t\t\t\tthis." + contextObjName + " );\n" );
            buff.append( "\t}\n\n" );

            // Prüfung wegen Performance zur Zeit ausser Betrieb
            //if ( isCompatibleTo( otherSwitchablePredicate.predicate , predicateExpression , exclude ) )
            //if ( BruteForceSolver.isCompatibleTo(
            //        exclude ,
            //        otherSwitchablePredicate.predicate ,
            //        predicateExpression ) )
            //    // Test Kompatibilität umgekehrt, wenn ja, dann sind die beiden Prädikate-Expression äquivalent
            //if ( BruteForceSolver.isEquivalent(
            //        //this.exclude ,
            //        this.cache ,
            //        otherSwitchablePredicate.predicate ,
            //        predicateExpression ) )
            //    // Test Equivalenz, wenn ja, dann sind die beiden Prädikate-Expression äquivalent
            //{
            //    // TODO solche Äquivalenzen können aufgrund der closed world assumption auftauchen, können ignoriert werden
            //    //throw new IllegalStateException(
            //    //        predicateExpression.toString() + " " +
            //    //                "Line-Number " + /*predicateExpression.*/lineNr + " " +
            //    //                "and " +
            //    //                otherSwitchablePredicate.predicate.toString() + " " +
            //    //                "Line-Number " + otherSwitchablePredicate.lineNr +
            //    //        " are equal" );
            //}
        }

        return buff.toString();
    }

    /**
     * Generieren aller Constraint-Safe-Switches einer Constraint-Expression.
     *
     * @param constraintFileName
     * @param switchablePredicate
     * @param allSwitchablePredicateExpressionIterable
     * @return
     */
    private String generateConstraintSafeSwitchClassesStr(
            final String constraintFileName ,
            final SwitchablePredicate<CT> switchablePredicate
            //final Iterable<SwitchablePredicate<CT>> allSwitchablePredicateExpressionIterable
            )
    {
        final StringBuilder buff = new StringBuilder();

        final SwitchDefinition<CT>[] switchablePredicateArr = switchablePredicate.switchDefinitionArr;

        if ( switchablePredicateArr == null )
        {
            return "";
        }

        for ( int i = 0 ; i < switchablePredicateArr.length ; i++ )
        {
            final SwitchDefinition<CT> switchDefinition = switchablePredicateArr[ i ];

            final String constraintSafeSwitchClassString =
                    generateConstraintSafeSwitchClassStr(
                            constraintFileName ,
                            switchablePredicate ,
                            switchDefinition
                            //allSwitchablePredicateExpressionIterable
                            );

            buff.append( constraintSafeSwitchClassString );
        }

        return buff.toString();
    }

    /**
     * Generieren eines Constraint-Safe-Switch einer Constraint-Expression.
     *
     * @param constraintFileName
     * @param switchablePredicate
     * @param switchDefinition
     * @param allSwitchablePredicateExpressionIterable
     * @return
     */
    private String generateConstraintSafeSwitchClassStr(
            final String constraintFileName ,
            final SwitchablePredicate<CT> switchablePredicate ,
            final SwitchDefinition<CT> switchDefinition
            //final Iterable<SwitchablePredicate<CT>> allSwitchablePredicateExpressionIterable
            )
    {
        final StringBuilder buff = new StringBuilder();

        // Erzeugen der abstrakten Switch-Case-Methoden
        for ( final CasePredicateSpecializedPredicateTuple<CT> casePredicateTuple : switchDefinition.casePredicateTupleArr )
        {
            final String constraintSafeSwitchMethodString =
                    generateConstraintSafeSwitchCaseAbstractMethodStr(
                            casePredicateTuple ,
                            constraintFileName ,
                            switchablePredicate ,
                            switchDefinition
                            //allSwitchablePredicateExpressionIterable
                            );

            buff.append( constraintSafeSwitchMethodString );
        }

        if ( false == switchDefinition.isCovering )
        {
            final String constraintSafeSwitchDefaultMethodString =
                    generateConstraintSafeSwitchDefaultCaseAbstractMethodStr(
                            constraintFileName ,
                            switchablePredicate ,
                            switchDefinition
                            //allSwitchablePredicateExpressionIterable
                            );

            buff.append( constraintSafeSwitchDefaultMethodString );
        }

        // Erzeugen der finalen doSwitch-Methode
        buff.append(
                "\t\t/**\n" +
                "\t\t * Final do method for switch " + switchDefinition.name + '\n' +
                //"\t\t * @param constraint Constraint\n" +
                ( "void".equals( switchDefinition.caseMethodReturnTypeName() ) ? "" : "\t\t * @return result value\n" ) +
                ( StringUtil.isEmpty( switchDefinition.caseMethodExceptionsStr ) ? "" : "\t\t * @throws " + switchDefinition.caseMethodExceptionsStr + " rethrown\n" ) +
                "\t\t */\n" +
                "\t\tpublic final " + switchDefinition.caseMethodReturnTypeName() + " doSwitch()\n" +
                //"\t\t\t\tfinal " + constraintFileName + " constraint )\n" +
                ( StringUtil.isEmpty( switchDefinition.caseMethodExceptionsStr ) ? "" : "\t\tthrows " + switchDefinition.caseMethodExceptionsStr + '\n' ) +
                "\t\t{\n");

        final String returnCodeStr;
        if ( "void".equals( switchDefinition.caseMethodReturnTypeName() ) )
        {
            returnCodeStr = "";
        }
        else
        {
            returnCodeStr = "return ";
        }

        boolean noFirstIfBranch = false;

        for ( final CasePredicateSpecializedPredicateTuple<CT> casePredicateTuple : switchDefinition.casePredicateTupleArr )
        {
            final String paramPredicateClassName =
                    constraintFileName(
                            casePredicateTuple.paramPredicateSimplifiedForExclude );

            buff.append(
                    "\t\t\t" +
                    ( noFirstIfBranch ? "else " : "" ) +
                    "if ( " + paramPredicateClassName + ".test( " + constraintFileName + ".this." + contextObjName + " ) )\n" +
                    "\t\t\t{\n" +
                    "\t\t\t\t" + returnCodeStr + "case" + casePredicateTuple.casePredicate.toFileName( CLASS_NAME_SEPARATOR ) + "(\n" +
                    "\t\t\t\t\t\tnew " + paramPredicateClassName + "(\n" +
                    "\t\t\t\t\t\t\t\t" + constraintFileName + ".this." + contextObjName + " ) );\n" +
                    "\t\t\t}\n" );

            noFirstIfBranch = true;
        }

        if ( false == switchDefinition.isCovering )
        {
            buff.append(
                    "\t\t\telse\n" +
                    "\t\t\t{\n" +
                    "\t\t\t\t" + returnCodeStr + "caseDefault(" +
                    //"\n" +
                    //"\t\t\t\t\t\tnew " + switchDefinition.defaultParamPredicateClassName + "(\n" +
                    //"\t\t\t\t\t\t\t\t" + constraintFileName + ".this." + contextObjName + " ) " +
                    ");\n" +
                    "\t\t\t}\n" );
        }
        else
        {
            buff.append(
                    "\t\t\telse\n" +
                    "\t\t\t{\n" +
                    "\t\t\t\tthrow new UnreachableCodeException();\n" +
                    "\t\t\t}\n" );
        }

        buff.append(
                "\t\t}\n\n" );

        final String constraintSafeSwitchClassString =
                "\t/**\n" +
                "\t * Abstract class for constraint safe switch " + switchDefinition.name + '\n' +
                "\t */\n" +
                "\tabstract public class " + switchDefinition.name + "Switch\n" +
                "\t{\n" +
                buff.toString() +
                "\t}\n\n";

        return constraintSafeSwitchClassString;
    }

    private String generateConstraintSafeSwitchCaseAbstractMethodStr(
            final CasePredicateSpecializedPredicateTuple<CT> casePredicateTuple ,
            final String constraintFileName ,
            final SwitchablePredicate<CT> switchablePredicate ,
            final SwitchDefinition<CT> switchDefinition
            //final Iterable<SwitchablePredicate<CT>> allSwitchablePredicateExpressionIterable
            )
    {
        final String caseConstraintFileName =
                constraintFileName(
                        casePredicateTuple.paramPredicateSimplifiedForExclude );

        final String constraintSafeSwitchMethodString =
                "\t\t/**\n" +
                "\t\t * Abstract method for case " + casePredicateTuple.casePredicate + '\n' +
                "\t\t * @param constraint specialized case constraint\n" +
                ( "void".equals( switchDefinition.caseMethodReturnTypeName() ) ? "" : "\t\t * @return result value\n" ) +
                ( StringUtil.isEmpty( switchDefinition.caseMethodExceptionsStr ) ? "" : "\n\t\t * @throws " + switchDefinition.caseMethodExceptionsStr + " rethrown\n" ) +
                "\t\t */\n" +
                "\t\tabstract protected " + switchDefinition.caseMethodReturnTypeName() + " case" + casePredicateTuple.casePredicate.toFileName( CLASS_NAME_SEPARATOR ) + "(\n" +
                "\t\t\t\tfinal " + caseConstraintFileName + " constraint )" +
                ( StringUtil.isEmpty( switchDefinition.caseMethodExceptionsStr ) ? "" : "\n\t\tthrows " + switchDefinition.caseMethodExceptionsStr ) +
                ";\n\n";

        return constraintSafeSwitchMethodString;
    }

    private String generateConstraintSafeSwitchDefaultCaseAbstractMethodStr(
            //final CasePredicateSpecializedPredicateTuple casePredicateTuple ,
            final String constraintFileName ,
            final SwitchablePredicate<CT> switchablePredicate ,
            final SwitchDefinition<CT> switchDefinition
            //final Iterable<SwitchablePredicate<CT>> allSwitchablePredicateExpressionIterable
            )
    {
        final String constraintSafeSwitchMethodString =
                "\t\t/**\n" +
                "\t\t * Abstract method for default case " /*+ casePredicateTuple.casePredicate*/ + '\n' +
                //zur Zeit wird bei der caseDefault-Methode kein constraint-parameter übergeben: "\t\t * @param constraint specialized case constraint\n" +
                ( "void".equals( switchDefinition.caseMethodReturnTypeName() ) ? "" : "\t\t * @return result value\n" ) +
                ( StringUtil.isEmpty( switchDefinition.caseMethodExceptionsStr ) ? "" : "\n\t\t * @throws " + switchDefinition.caseMethodExceptionsStr + " rethrown\n" ) +
                "\t\t */\n" +
                "\t\tabstract protected " + switchDefinition.caseMethodReturnTypeName() + " caseDefault(" +
                //"\n" +
                //"\t\t\t\tfinal " + caseConstraintFileName + " constraint " +
                ")" +
                ( StringUtil.isEmpty( switchDefinition.caseMethodExceptionsStr ) ? "" : "\n\t\tthrows " + switchDefinition.caseMethodExceptionsStr ) +
                ";\n\n";

        return constraintSafeSwitchMethodString;
    }

    /**
     * Diese Methode sucht für die {@link DestinationConstraintSafeOperation}s
     * passende Constraint-Expressions in der übergebenen Liste und fügt
     * diese bei Fehlen hinzu.
     *
     * @param switchablePredicate
     * @param allAndSwitchCasePredicateExpressionColl
     */
    private void collectConstraintExpressionsForSafeOperations(
            //final SwitchablePredicate<CT> switchablePredicate
            final int lineNr ,
            final Predicate<CT> predicate ,
            final AbstractSafeOperation<CT>[] safeOperationArr ,
            //final Collection<SwitchablePredicate<CT>> allAndSwitchCasePredicateExpressionColl ,
            final ConstraintCodeGeneratorSuite suite )
    {
        if ( /*switchablePredicate.*/safeOperationArr == null ||
                /*switchablePredicate.*/safeOperationArr.length < 1 )
            // keine SafeOperations
        {
            return;
        }

        for ( final AbstractSafeOperation<CT> safeOperation : /*switchablePredicate.*/safeOperationArr )
        {
            if ( safeOperation.reusedOrCreatedOtherPredicate == null )
            {
// TODO temporär ausgeschalten für DynamicTransformSafeOperationTicTacToeConstraintCodeGenerator, alle erzeugten sicheren Operationen sind erlaubt (in Methode _15_dynamic_transform_safeoperation_tic_tac_toe.DynamicTransformSafeOperationTicTacToeConstraintCodeGenerator.main(...).new AbstractConstraintCodeGenerator() {...}.createDynamicSafeOperations(Predicate<TicTacToe>) geprüft)
                // Prüfen Einhaltung Voraussetzung
                final Predicate<CT> premisePredicate =
                        safeOperation.getPremise(
                                this.cache );

                checkPremise(
                        /*switchablePredicate.*/lineNr ,
                        /*switchablePredicate.*/predicate ,
                        safeOperation ,
                        premisePredicate );

                reuseOrCreateDestinationPredicateAndOtherPredicateForSafeOperation(
                        /*switchablePredicate.*//*predicate.*/lineNr ,
                        /*switchablePredicate.*/predicate ,
                        //allAndSwitchCasePredicateExpressionColl ,
                        suite ,
                        safeOperation );
            }
        }
    }

    /**
     * Prüfen zusätzliche Voraussetzung für sichere Opration.
     *
     * @param switchablePredicate
     * @param safeOperation
     * @param premisePredicate
     */
    private void checkPremise(
            final int lineNr ,
            //final SwitchablePredicate<CT> switchablePredicate
            final Predicate<CT> predicate ,
            final AbstractSafeOperation<CT> safeOperation ,
            final Predicate<CT> premisePredicate )
    {
        if ( premisePredicate != null )
            // Falls keine Voraussetzung erfoderlich ist, darf null zurückgegeben werden
        {
            if ( false == BruteForceCompatibleChecker.isCompatibleTo(
                    this.cache ,
                    //thisPredicateExpression
                    //switchablePredicate.predicate
                    predicate ,
                    //otherPredicateExpression
                    premisePredicate ) )
            {
                throw new IllegalArgumentException(
                        //switchablePredicate.predicate
                        predicate +
                        " line nr " +
                        //switchablePredicate/*.predicate*/.lineNr
                        lineNr + ": " +
                        "premise for safe operation " +
                        safeOperation +
                        " " +
                        premisePredicate +
                        " not satisfied" );
            }
        }
    }

    /**
     * Prädikate
     * {@link AbstractSafeOperation#reusedOrCreatedOtherPredicate} und
     * {@link DestinationConstraintSafeOperation#reusedOrCreatedDestinationPredicate}
     * für die übergebene sichere Operation
     * ermitteln und in den {@link AbstractSafeOperation}-Objekten vermerken.
     *
     * @param switchablePredicate
     * @param allAndSwitchCasePredicateExpressionColl
     * @param suite
     * @param safeOperation
     */
    private void reuseOrCreateDestinationPredicateAndOtherPredicateForSafeOperation(
            final int lineNr ,
            //final SwitchablePredicate<CT> switchablePredicate
            final Predicate<CT> predicate ,
            //final Collection<SwitchablePredicate<CT>> allAndSwitchCasePredicateExpressionColl ,
            final ConstraintCodeGeneratorSuite suite ,
            final AbstractSafeOperation<CT> safeOperation )
    {
        if ( safeOperation instanceof NewDestinationConstraintSafeOperation )
        {
            final NewDestinationConstraintSafeOperation<CT> newDstCnstrSafeOpr = (NewDestinationConstraintSafeOperation<CT>) safeOperation;

            if ( newDstCnstrSafeOpr.reusedOrCreatedDestinationPredicate != null )
            {
                // bereits erledigt
                return;
            }

            final Predicate<CT> destinationConstraintPredicate =
                    newDstCnstrSafeOpr.getDestinationConstraintExpression(
                            //switchablePredicate/*.predicate*/.lineNr
                            lineNr ,
                            //switchablePredicate.predicate
                            predicate ,
                            this.cache.exclude );

            newDstCnstrSafeOpr.reusedOrCreatedDestinationPredicate =
                    reuseOrCreatePredicate(
                            destinationConstraintPredicate ,
                            // parentPredicate
                            null
                            //allAndSwitchCasePredicateExpressionColl
                            );
        }
        else if ( safeOperation instanceof TransformDestinationConstraintSafeOperation )
        {
            final TransformDestinationConstraintSafeOperation<CT> trnsfDstCnstrSafeOpr = (TransformDestinationConstraintSafeOperation<CT>) safeOperation;

            if ( trnsfDstCnstrSafeOpr.reusedOrCreatedDestinationPredicate != null )
            {
                // bereits erledigt
                return;
            }

            final Predicate<CT> destinationConstraintPredicate =
                    PredicateTransformerForSafeOperation.tranformAndAdd(
                            //switchablePredicate.predicate
                            predicate ,
                            trnsfDstCnstrSafeOpr ,
                            //this.cache.exclude ,
                            this.cache );

            trnsfDstCnstrSafeOpr.reusedOrCreatedDestinationPredicate =
                    reuseOrCreatePredicate(
                            destinationConstraintPredicate ,
                            // parentPredicate
                            null
                            //allAndSwitchCasePredicateExpressionColl
                            );
        }
        else if ( safeOperation instanceof OtherContextTypeDestinationConstraintSafeOperation )
        {
            if ( suite == null )
            {
                throw new IllegalStateException(
                        OtherContextTypeDestinationConstraintSafeOperation.class.getSimpleName() + " " +
                        "required an constraint code genrator suite" );
            }

            final OtherContextTypeDestinationConstraintSafeOperation octDstCnstrSafeOp = (OtherContextTypeDestinationConstraintSafeOperation) safeOperation;

            if ( octDstCnstrSafeOp.reusedOrCreatedDestinationPredicate != null )
            {
                // bereits erledigt
                return;
            }

            // Ziel-Kontext-Objekt-Type besorgen
            final Type dstCtxObjType = octDstCnstrSafeOp.getDestinationContextObjType();

            // Bridge das Ziel-Constraint erzeugen lassen
            final Predicate<?> destinationPredicate =
                    suite.bridge.convert(
                            //predToConvert
                            //switchablePredicate.predicate
                            predicate ,
                            dstCtxObjType ,
                            //dstExclude
                            //suite.getExcludeForCtxobjtype( dstCtxObjType ) ,
                            //dstCache
                            suite.getCacheForCtxobjtype(
                                    dstCtxObjType ) );

            // den für das Ziel-Constraint verantwortlichen Code-Generator besorgen
            final AbstractConstraintCodeGenerator<?> destCnstrcdgnrt =
                    suite.getCnstrcdgnrtForCtxobjtype(
                            dstCtxObjType );

            // den verantwortlichen Code-Generator darüber informieren, dass das Ziel-Constraint benötigt wird und dieses Constraint vermerken
            octDstCnstrSafeOp.reusedOrCreatedDestinationPredicate =
                    destCnstrcdgnrt.reuseOrCreatePredicate(
                            destinationPredicate );
        }
        else if ( safeOperation instanceof ValueSafeOperation )
        {
            // nix zu tun
        }
        else
        {
            throw new UnreachableCodeException(
                    String.valueOf( safeOperation ) );
        }

        final Predicate<CT> otherConstraintPredicate =
                safeOperation.getOtherParameterPredicate(
                        //switchablePredicate.lineNr
                        lineNr ,
                        this.cache );

        if ( otherConstraintPredicate != null )
        {
            safeOperation.reusedOrCreatedOtherPredicate =
                    reuseOrCreatePredicate(
                            otherConstraintPredicate ,
                            // parentPredicate
                            null
                            //allAndSwitchCasePredicateExpressionColl
                            );
        }
    }

    /**
     * @param switchablePredicate
     * @return Java-Code als String für SafeOperation
     */
    /*default*/ static <CT> String generateSafeOperationsStr(
            //final SwitchablePredicate<CT> switchablePredicate
            final int lineNr ,
            final Predicate<CT> predicate ,
            final AbstractSafeOperation<CT>[] safeOperationArr ,
            final String contextObjName )
    {
        if ( /*switchablePredicate.*/safeOperationArr == null ||
                /*switchablePredicate.*/safeOperationArr.length < 1 )
            // keine SafeOperations
        {
            return "";
        }

        final StringBuilder buff = new StringBuilder();

        for ( final AbstractSafeOperation<CT> safeOperation : /*switchablePredicate.*/safeOperationArr )
        {
            if ( safeOperation instanceof DestinationConstraintSafeOperation )
            {
                final DestinationConstraintSafeOperation<CT, ?> dstSafeOpr = (DestinationConstraintSafeOperation<CT, ?>) safeOperation;

                buff.append(
                        generateDestinationConstraintSafeOperationStr(
                                /*switchablePredicate.*//*.predicate.*/lineNr ,
                                dstSafeOpr ,
                                contextObjName ) );
            }
            else if ( safeOperation instanceof ValueSafeOperation )
            {
                final ValueSafeOperation<CT> valueSafeOpr = (ValueSafeOperation<CT>) safeOperation;

                final Predicate<CT> otherConstraintExpression =
                        valueSafeOpr.reusedOrCreatedOtherPredicate;

                final String otherConstraintFileName;
                if ( otherConstraintExpression != null )
                {
                    otherConstraintFileName =
                            otherConstraintExpression.toFileName(
                                    CLASS_NAME_SEPARATOR );
                }
                else
                {
                    otherConstraintFileName = null;
                }

                buff.append(
                        valueSafeOpr.toJavaCode(
                                otherConstraintFileName ,
                                contextObjName ) );
            }
            else
            {
                throw new UnreachableCodeException(
                        String.valueOf(
                                safeOperation ) );
            }
        }

        return buff.toString();
    }

    /**
     * 
     * @param lineNr
     * @param dstSafeOpr
     * @param contextObjName
     * @return
     */
    private static <CT> String generateDestinationConstraintSafeOperationStr(
            final int lineNr ,
            final DestinationConstraintSafeOperation<CT, ?> dstSafeOpr ,
            final String contextObjName )
    {
        final Predicate<?> destinationConstraintExpression =
                dstSafeOpr.reusedOrCreatedDestinationPredicate;

        final String destinationConstraintFileName =
                destinationConstraintExpression.toFileName(
                        CLASS_NAME_SEPARATOR );

        final Predicate<CT> otherConstraintExpression =
                dstSafeOpr.reusedOrCreatedOtherPredicate;

        final String otherConstraintFileName;
        if ( otherConstraintExpression != null )
        {
            otherConstraintFileName =
                    otherConstraintExpression.toFileName(
                            CLASS_NAME_SEPARATOR );
        }
        else
        {
            otherConstraintFileName = null;
        }

        final String dstSafeOprJavaCode =
                dstSafeOpr.toJavaCode(
                destinationConstraintFileName ,
                otherConstraintFileName ,
                contextObjName );

        return dstSafeOprJavaCode;
    }
    
    private String generateToStringMethodStr(
            final Predicate<CT> predicate )
    {
        final StringBuilder toStringMethodStrBldr = new StringBuilder();
        
        toStringMethodStrBldr.append( "\t/**\n" );
        toStringMethodStrBldr.append( "\t * Debug output.\n" );
        toStringMethodStrBldr.append( "\t *\n" );
        toStringMethodStrBldr.append( "\t * @see java.lang.Object#toString()\n" );
        toStringMethodStrBldr.append( "\t */\n" );
        toStringMethodStrBldr.append( "\t@Override\n" );
        toStringMethodStrBldr.append( "\tpublic String toString()\n" );
        toStringMethodStrBldr.append( "\t{\n" );
        toStringMethodStrBldr.append( "\t\treturn \"" + predicate.toString() + ": \" + this." + contextObjName + ";\n" );
        toStringMethodStrBldr.append( "\t}\n\n" );

        return toStringMethodStrBldr.toString();
    }

    /*default*/ static <CT> String constraintFileName(
            final Predicate<CT> predicate )
    {
        return
                predicate.toFileName(
                        CLASS_NAME_SEPARATOR ) +
                ( isNotAtom(
                        predicate )
                            ? ""
                            // Postfix nur bei Single-Constrainst an den Namen anhängen
                            : CONSTRAINT_CLASS_POSTFIX );
    }

    /**
     * @return Type des Kontext-Objektes des implementierenden (von dieser Klasse abgeleiteten) Constraint-Code-Generators
     */
    public final Type getCtxObjType()
    {
        return CcgReflectionUtil.typeParamArrOfObj( this )[ 0 ];
    }
    
    /**
     * Überschreibbare Methode
     * zum Prüfen per 
     * {@link #add}-Methode
     * hinzugefügter Constraint-Expressions.
     * 
     * @param predicateToCheck zu prüfende Constraint-Expression
     */
    protected void checkConstraint(
            final Predicate<CT> predicateToCheck )
    {
        // leere Default-Implementierung
    }
    
    /**
     * Überschreibbare Methode
     * zum dynamischen Hinzufügen
     * von sicheren Operationen
     * zur übergebenen 
     * Constraint-Expression.
     * 
     * @param predicateToGenerate
     * @return
     */
    protected Iterable<AbstractSafeOperation<CT>> createDynamicSafeOperations(
            final Predicate<CT> predicateToGenerate )
    {
        // leere Default-Implementierung
        return null;
    }

}