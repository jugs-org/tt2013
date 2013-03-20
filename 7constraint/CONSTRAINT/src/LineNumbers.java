// package dein.package ;

import java.io.*;
import java.text.*;
import java.util.*;


/**
 * Programm zum Eintragen von Zeilen-Nummern, Datei-Name, Klassen-Name, Methoden-Name
 * Package-Name und TimeStamp (Datum, Uhrzeit) in Java-Quell-Dateien . <br/>
 * <br/>
 * Es durchläuft rekursiv das aktuelle Verzeichnis und alle Unterverzeichnisse und
 * prüft alle enthaltenen <b>*.java</b>- und <b>*.jsp</b>-Dateien.<br/>
 * 
 * <br/>
 * In den <b>*.java</b>- und <b>*.jsp</b>-Dateien wird die Escape-Sequence
 * {@link #LINE_NR_ESC_SEQ}
 * gelesen und an diesen Stellen die Zeilen-Nummer eingefügt.<br/>
 * 
 * Schon vorhandene Zeilen-Nummern werden aktualisiert.<br/>
 * 
 * Ausserdem kann der Dateiname und Pfad mit <b>&#36;FileName&lt;&gt;</b> und das
 * aktuelle Datum und Zeit mit <b>&#36;DateTime&lt;&gt;</b> eingef&uuml;gt werden.<br/>
 * 
 * Das Einf&uuml;gen des TimeStamps erm&ouml;glicht eine Art touch auf die
 * entsprechende Datei, das heisst das Erzwingen von ständigem Kompilieren,
 * Deployen oder ähnlichem.<br/>
 * <pre>
 * Beispiel:
 * System.out.println("&#36;FileName&lt;&gt; &#36;LineNr: 30");
 * </pre>
 * <br/>
 * Ab Februar 2004 ist es möglich mit <b>&#36;ClassName:</b> den Klassen-Namen,
 * mit <b>&#36;MethodName:</b> den Methoden-Namen
 * und mit <b>&#36;PackageName:</b> den Package-Namen einzufügen.
 * <pre>
 * Beispiel:
 * System.out.println("&#36;ClassName:  &#36;MethodName: TimeStamp");
 * </pre>
 * <br/>
 * Von veränderten Dateien wird eine Bakup-Datei <b>*.bak</b> angelegt.<br/>
 * Dies kann mit dem Parameter <b>-nobackup</b> unterdrückt werden.
 * <pre>
 * Aufruf:
 * 
 * java -cp &lt;pfad_zu_LineNumbers.class&gt; LineNumbers &lt;start_directory&gt;
 *
 * ohne Erstellen von *.bak-Dateien:
 * 
 * java -cp &lt;pfad_zu_LineNumbers.class&gt; LineNumbers -nobackup &lt;start_directory&gt;
 * </pre>
 * Dieses Programm sollte über den Parameter &lt;start_directory&gt;
 * in dem Verzeichnis gestartet werden,
 * welches die Root der zu instrumentierenden Java-Dateien darstellt.
 * <br/><br/>
 * <B><U>Lizenzbedingungen:</U></B>
 * <br/><br/>
 * Die Programme, Quelltexte und Dokumentationen k&ouml;nnen ohne irgendwelche 
 * Bedingungen kostenlos verwendet werden.
 * Sie sind Freeware und Open Source. F&uuml;r Fehler und Folgen wird keinerlei 
 * Haftung &uuml;bernommen.
 * <br/><br/>
 * <b>Author:</b> <a href="http://www.heinerkuecker.de" target="_blank">Heiner Kücker</a>
 * <br/>
 * 
 * @author Heiner Kücker
 */
public class LineNumbers
{
    /**
     * Escape-Sequence (Trenn-Symbol) für Zeilen-Nummer: &#36;Line. <br/>
     * in zwei Teile geteilt, damit die Konstante beim Ausführen LineNumbers
     * über dieses Programm nicht geändert wird.
     */
    public static final String LINE_NR_ESC_SEQ = "$Line" + "Nr: ";

    /**
     * Escape-Sequence (Trenn-Symbol) für den Dateinamen . <br/>
     * in zwei Teile geteilt, damit die Konstante beim Ausführen LineNumbers
     * über dieses Programm nicht geändert wird.
     */
    public static final String FILE_NAME_ESC_SEQ = "$File" + "Name";

    /**
     * Escape-Sequence (Trenn-Symbol) für Datum + Uhrzeit . <br/>
     * in zwei Teile geteilt, damit die Konstante beim Ausführen LineNumbers
     * über dieses Programm nicht geändert wird.
     */
    public static final String DATE_TIME_ESC_SEQ = "$Date" + "Time";

    /**
     * Escape-Sequence (Trenn-Symbol) für den Klassen-Namen . <br/>
     * in zwei Teile geteilt, damit die Konstante beim Ausführen LineNumbers
     * über dieses Programm nicht geändert wird.
     */
    public static final String CLASS_NAME_ESC_SEQ = "$Class" + "Name: ";

    /**
     * Escape-Sequence (Trenn-Symbol) für den Methoden-Namen . <br/>
     * in zwei Teile geteilt, damit die Konstante beim Ausführen LineNumbers
     * über dieses Programm nicht geändert wird.
     */
    public static final String METHOD_NAME_ESC_SEQ = "$Method" + "Name: ";

    /**
     * Escape-Sequence (Trenn-Symbol) für den Package-Namen . <br/>
     * in zwei Teile geteilt, damit die Konstante beim Ausführen LineNumbers
     * über dieses Programm nicht geändert wird.
     */
    public static final String PACKAGE_NAME_ESC_SEQ = "$Package" + "Name: ";

    /**
     * anzuhängende Endung für Bakup-Dateien
     */
    private static final String BAKUP_FILE_SUFFIX = ".bak";

    /**
     * Schalter, ob Bakup-Dateien angelegt werden sollen
     */
    private static boolean bClCreateBackupFiles = true;

    /**
     * Merker für Exit-Status ( 0 - keine Fehler )
     */
    private static int iExitStatus = 0;

    /**
     * Timestamp-Datumsformat für {@link #strTimeStamp}
     */
    private static SimpleDateFormat formatTimestamp = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss", Locale.GERMAN);

    /**
     * Timestamp für aktuellen Zeitpunkt
     */
    private static String strTimeStamp = formatTimestamp.format(new Date());

    /**
     * Main-Methode dieser Klasse. <br/>
     * Beginnt im aktuellen Verzeichnis mit der Suche nach <b>*.java</b>-Dateien.<br/>
     * 
     * @param args Command line options
     */
    public static void main(
            final String[] args )
    throws IOException
    {
//        if (args.length < 1)
//        {
//            System.out.println( "Start LineNumbers <start_directory>" );
//            System.out.println( "or" );
//            System.out.println( "Start LineNumbers -nobackup <start_directory>" );
//            //return;
//            // Rückgabe Fehlerstatus und Programmende
//            // Bei Start aus ANT im Tag javac muss mit dem Parameter fork="true" eine neue JVM gestartet werden
//            // sonst System.exit(int) auskommentieren, weil System.exit(int) ANT komplett abbricht
//            System.exit( 1 ); // Fehler
//        }
        String startDir = ".";

        if (args.length > 0)
        {
            if ( "-nobackup".equals( args[ 0 ] ) )
            {
                // Ausschalten Anlegen Backup-Dateien
                bClCreateBackupFiles = false;
                if (args.length > 1)
                {
                    //zweiter Parameter
                    startDir = args[ 1 ];
                }
            }
            else
            {
                startDir = args[ 0 ];
            }
        }
        System.out.println("LineNumbers Start in " + startDir + " \"" + new File( startDir ).getCanonicalPath() + "\"" );
        scanDir( startDir );

        // Rückgabe Fehlerstatus und Programmende
        // Bei Start aus ANT im Tag javac muss mit dem Parameter fork="true" eine neue JVM gestartet werden
        // sonst System.exit(int) auskommentieren, weil System.exit(int) ANT komplett abbricht
        System.exit(iExitStatus);
    }// end method main

    /**
     * Methode zum Durchlaufen des aktuellen Verzeichnisses und
     * aller Unterverzeichnisse. <br/>
     * Ruft für alle Dateien die Methode {@link #processFile} auf.
     * 
     * @param dirPath Pfad des zu durchsuchenden Verzeichnisses
     */
    public static void scanDir(
            final String dirPath )
    {
        // System.out.println( "scanDir: " + dirPath ) ;

        // Ermittle Liste Dateien
        File[] fileArr = new File(dirPath).listFiles();

        for (int i = 0; i < fileArr.length; i++)
        {
            if (fileArr[i].isDirectory())
            {
                scanDir(fileArr[i].getParent() + File.separator + fileArr[i].getName());
            }
            else
            { // ein File
                processFile(fileArr[i].getParent() + File.separator + fileArr[i].getName());
            }
        } // end for
    }// end method scanDir

    /**
     * Methode zum Abarbeiten aller Dateien, ausser Verzeichnisse. <br/>
     * Diese Methode wird von {@link #scanDir} für jede Datei<br/>
     * aufgerufen.<br/>
     * Sie ruft für jede <b>*.java</b>- und <b>*.jsp</b>-Dateien die Methode
     * {@link #processJavaFile} auf.
     * 
     * @param fileNameWithPath Dateiname mit Pfad und Endung
     */
    public static void processFile(
            final String fileNameWithPath )
    {
        // System.out.println( "processFile: " + fileName ) ;
        if ( ( fileNameWithPath.endsWith( ".java" ) || fileNameWithPath.endsWith( ".jsp" ) ) )
        {
            processJavaFile(fileNameWithPath);
        }// end if
    }// end method processFile

    /**
     * Methode zum Abarbeiten aller <b>*.java</b>-Dateien. <br/>
     * Diese Methode wird von {@link #processFile} für jede
     * <b>*.java</b>-Datei aufgerufen.<br/>
     * Sie benutzt die Methode {@link #replaceLineNumbers}.<br/>
     * 
     * @param fileName Dateiname mit Pfad und Endung
     */
    public static void processJavaFile(String fileName)
    {
        // System.out.println( "processJavaFile: " + fileName ) ;

        // Datei-Inhalt in String-Array einziehen
        String[] fileLines = file2StrArr(fileName);

        boolean bChanged = replaceLineNumbers(fileLines); // return true if changed

        if (replaceFileNames(fileLines, fileName))
        { // return true if changed
            bChanged = true;
        }

        if (replaceDateTime(fileLines))
        { // return true if changed
            bChanged = true;
        }

        if (replaceClassMethod(fileLines))
        { // return true if changed
            bChanged = true;
        }

        if (replacePackage(fileLines))
        { // return true if changed
            bChanged = true;
        }

        if (bChanged)
        {
            System.out.println("LineNumbers replaced in File " + fileName);
            // Bak-File anlegen
            if (!writeBakFile(fileName))
            {
                System.err.println("cant create Backup-File " + fileName + BAKUP_FILE_SUFFIX);
            }
            if (!strArr2file(fileLines, fileName))
            {
                // wenn speichern nicht klappte
                iExitStatus = 1; // Rückgabe: nicht ok
            }
        }// end if

    }// end method processJavaFile

    // *********************************************************************************************************

    /**
     * Setzen Zeilen-Nummern für alle Zeilen
     * 
     * @param fileLinesPa Array mit den Zeilen der Java-Datei als Strings
     * @return geänderten String oder null wenn nicht geändert(für  
     *     Update-Merker)
     */
    public static boolean replaceLineNumbers(String[] fileLinesPa)
    {
        boolean bReturn = false;

        for (int i = 0; i < fileLinesPa.length; i++)
        {
            if (fileLinesPa[ i ].indexOf(LINE_NR_ESC_SEQ) >= 0)
            {
                // eine Zeile überarbeiten
                // Rückgabe null bedeutet, Zeilen-Nummern waren schon ok, update nicht nötig
                String strTmp = singleReplaceLineNumbers(fileLinesPa[ i ], i + 1);

                if (strTmp != null)
                {
                    bReturn = true;
                    fileLinesPa[ i ] = strTmp;
                }
            }
        }
        return bReturn;
    } // end method replaceLineNumbers

    /**
     * Setzen Zeilen-Nummern für eine Zeile
     * 
     * @param strPa Source-Zeile als String
     * @param iPaLineNr Zeilen-Nummer
     * @return geänderten String oder null wenn nicht geändert(für  
     *     Update-Merker)
     */
    public static String singleReplaceLineNumbers( //--
        final String strPa, //--
        final int iPaLineNr)
    {
        return singleReplaceNumForEscSeqColon(strPa, LINE_NR_ESC_SEQ, "" + iPaLineNr);
    }// end method singleReplaceLineNumbers

    /**
     * Aktualisieren einer Zeile mit dem übergebenen Inhalt (numerischer String)
     * für die übergeben Escape-Sequenz mit less than und greater than
     * Begrenzern.
     * 
     * @param pStr Source-Zeile als String
     * @param pEscSeqStr Escape-Sequenz
     * @param pNewContent einzufügender Inhalt
     * @return aktualisierter String oder null, wenn nichts zu aktualisieren 
     *     war
     */
    private static String singleReplaceNumForEscSeqColon(
        final String pStr, //--
        final String pEscSeqStr, //--
        final String pLineNrStr)
    {
        boolean bUpdate = false;
        String[] strArr = (String[]) tokenize(pStr, pEscSeqStr, true).toArray(new String[0]);
        StringBuffer strBuffRet = new StringBuffer();
        
        for (int i = 0; i < strArr.length; i++)
        {
            if (strArr[ i ].equals(pEscSeqStr))
            {
                // Zeilen-Nummer eintragen
                strBuffRet.append(pEscSeqStr + pLineNrStr);
                // alte Zeilen-Nummer entfernen
                i++;
                if (i < strArr.length)
                {
                    // zur Optimierung einlesen vorhandene Nummer
                    // wenn gleich, dann Signal zum nicht update
                    strBuffRet.append(erasePreNumbers(strArr[ i ]));
                    if ( ! pLineNrStr.equals( getNumberStr(strArr[ i ] ) ) )
                    {
                        bUpdate = true;
                    }
                }
                else
                {
                    // anhängen der Zeilen-Nummer ans Ende ist immer update
                    bUpdate = true;
                }
            }
            else
            {
                strBuffRet.append(strArr[ i ]);
            }
        }// end for
        
        if (!bUpdate)
        {
            return null;
        }
        return strBuffRet.toString();
    }

    // *********************************************************************************************************

    /**
     * Setzen Datei-Namen für alle Zeilen
     * 
     * @param fileLinesPa Array mit den Zeilen der Java-Datei als Strings
     * @param fileNamePa Dateiname
     * @return geänderten String oder null wenn nicht geändert(für  
     *     Update-Merker)
     */
    public static boolean replaceFileNames(//--
        final String[] fileLinesPa, //--
        String fileNamePa)
    {
        fileNamePa = replaceBackSlash(fileNamePa);
        boolean bReturn = false;

        for (int i = 0; i < fileLinesPa.length; i++)
        {
            // if (fileLinesPa[ i ].indexOf("$FileName<.\\src\\LineNumbers.java>= 0) 
            if (fileLinesPa[ i ].indexOf(FILE_NAME_ESC_SEQ + "<") >= 0)
            {
                // eine Zeile überarbeiten
                // Rückgabe null bedeutet, Zeilen-Nummern waren schon ok, update nicht nötig
                String strTmp = singleReplaceFileNames(fileLinesPa[ i ], fileNamePa);

                if (strTmp != null)
                {
                    bReturn = true;
                    fileLinesPa[ i ] = strTmp;
                }
            }
        }
        return bReturn;
    } // end method replaceFileNames

    /**
     * Setzen Datei-Namen für eine Zeile
     * 
     * @param strPa Source-Zeile als String
     * @param fileNamePa Dateiname
     * @return geänderten String oder null wenn nicht geändert(für  
     *     Update-Merker)
     */
    public static String singleReplaceFileNames(String strPa, String fileNamePa)
    {
        return singleReplaceForEscSeqLtGt(strPa, FILE_NAME_ESC_SEQ, fileNamePa);
    }// end method singleReplaceFileNames

    /**
     * Aktualisieren einer Zeile mit dem übergebenen Inhalt
     * für die übergeben Escape-Sequenz mit less than und greater than
     * Begrenzern.
     * 
     * @param pStr Source-Zeile als String
     * @param pEscSeqStr Escape-Sequenz
     * @param pNewContent einzufügender Inhalt
     * @return aktualisierter String oder null, wenn nichts zu aktualisieren 
     *     war
     */
    private static String singleReplaceForEscSeqLtGt( //--
        final String pStr, //--
        final String pEscSeqStr, //--
        final String pNewContent)
    {
        boolean bUpdate = false;
        // String[] strArr = (String[]) tokenize(strPa, "$FileName<.\\src\\LineNumbers.java>>
        String[] strArr = (String[]) tokenize(pStr, pEscSeqStr + "<", true).toArray(new String[0]);
        StringBuffer strBuffRet = new StringBuffer();
        
        for (int i = 0; i < strArr.length; i++)
        {
            // if (strArr[ i ].equals("$FileName<.\\src\\LineNumbers.java>>
            if (strArr[ i ].equals(pEscSeqStr + "<"))
            {
                // Zeilen-Nummer eintragen
                // strBuffRet.append("$FileName<.\\src\\LineNumbers.java>");
                strBuffRet.append(pEscSeqStr + "<" + pNewContent + ">");
                // alte Zeilen-Nummer entfernen
                i++;
                if (i < strArr.length)
                {
                    // zur Optimierung einlesen vorhandene Nummer
                    // wenn gleich, dann Signal zum nicht update
                    strBuffRet.append(eraseToGT(strArr[ i ]));
                    if (!pNewContent.equals(getTextToGt(strArr[ i ])))
                    {
                        bUpdate = true;
                    }
                }
                else
                {
                    // anhängen der Zeilen-Nummer ans Ende ist immer update
                    bUpdate = true;
                }
            }
            else
            {
                strBuffRet.append(strArr[ i ]);
            }
        }// end for
        
        if (!bUpdate)
        {
            return null;
        }
        return strBuffRet.toString();
    }

    // ****************************************************************************************************************

    /**
     * Setzen Datum-Zeit für alle Zeilen
     * 
     * @param fileLinesPa Array mit den Zeilen der Java-Datei als Strings
     * @return geänderten String oder null wenn nicht geändert(für  
     *     Update-Merker)
     */
    public static boolean replaceDateTime(String[] fileLinesPa)
    {
        // System.out.println( "replaceDateTime" ) ;
        boolean bReturn = false;

        for (int i = 0; i < fileLinesPa.length; i++)
        {
            // if (fileLinesPa[ i ].indexOf("$Date" + "Time<31.08.2012 15:20:24>= 0) 
            if (fileLinesPa[ i ].indexOf(DATE_TIME_ESC_SEQ + "<") >= 0)
            {
                // eine Zeile überarbeiten
                // Rückgabe null bedeutet, Inhalt war schon ok, update nicht nötig
                String strTmp = singleReplaceDateTime(fileLinesPa[ i ]);

                if (strTmp != null)
                {
                    bReturn = true;
                    fileLinesPa[ i ] = strTmp;
                }
            }
        }
        return bReturn;
    } // end method replaceDateTime

    /**
     * Setzen Datum-Zeit für eine Zeile
     * 
     * @param strPa Source-Zeile als String
     * @return geänderten String oder null wenn nicht geändert(für  
     *     Update-Merker)
     */
    public static String singleReplaceDateTime(String strPa)
    {
        // System.out.println( "singleReplaceDateTime " + strPa ) ;
        return singleReplaceForEscSeqLtGt(strPa, DATE_TIME_ESC_SEQ, strTimeStamp);
    }// end method singleReplaceDateTime

    // ****************************************************************************************************************

    /**
     * Setzen Klassen- und Methoden-Namen für alle Zeilen
     * 
     * @param fileLinesPa Array mit den Zeilen der Java-Datei als Strings
     * @return geänderten String oder null wenn nicht geändert(für  
     *     Update-Merker)
     */
    private static boolean replaceClassMethod(String[] fileLinesPa)
    {
        // System.out.println( "replaceDateTime" ) ;

        String strClassName = "";
        String strMethodName = "";

        boolean bReturn = false;

        for (int i = 0; i < fileLinesPa.length; i++)
        {
            {
                // Ermitteln Klassen-Name
                final String strLineClassName = getClassNameFromLine(fileLinesPa[ i ]);

                if (strLineClassName != null)
                {
                    strClassName = strLineClassName;
                }
            }
            {
                // Ermitteln Methoden-Name
                final String strLineMethodName = getMethodNameFromLine(fileLinesPa[ i ]);

                if (strLineMethodName != null)
                {
                    strMethodName = strLineMethodName;
                }
            }
      
            // Eintragen Klassen-Name
            if (fileLinesPa[ i ].indexOf(CLASS_NAME_ESC_SEQ) >= 0)
            {
                // eine Zeile überarbeiten
                // Rückgabe null bedeutet, Inhalt war schon ok, update nicht nötig
                String strTmp = singleReplaceClass(fileLinesPa[ i ], strClassName);

                if (strTmp != null)
                {
                    bReturn = true;
                    fileLinesPa[ i ] = strTmp;
                }
            }
            if (fileLinesPa[ i ].indexOf(METHOD_NAME_ESC_SEQ) >= 0)
            {
                // eine Zeile überarbeiten
                // Rückgabe null bedeutet, Inhalt war schon ok, update nicht nötig
                String strTmp = singleReplaceMethod(fileLinesPa[ i ], strMethodName);

                if (strTmp != null)
                {
                    bReturn = true;
                    fileLinesPa[ i ] = strTmp;
                }
            }
        }
        return bReturn;
    } // end method replaceClassMethod

    /**
     * Setzen Klassen-Namen für eine Zeile
     * 
     * @param pLineStr Source-Zeile als String
     * @param pClassName Klassenname
     * @return geänderten String oder null wenn nicht geändert(für  
     *     Update-Merker)
     */
    public static String singleReplaceClass(// --
            final String pLineStr, // --
            final String pClassName) 
    {
//System.out.println( "singleReplaceClass " + pLineStr + " -> " + pClassName ) ;
          return singleReplaceIdentifierForEscSeqColon(pLineStr, CLASS_NAME_ESC_SEQ, pClassName);
    }// end method singleReplaceClass

    /**
     * Setzen Methoden-Namen für eine Zeile
     * 
     * @param pLineStr Source-Zeile als String
     * @param pMethodName Name der aktuellen Methode
     * @return geänderten String oder null wenn nicht geändert(für  
     *     Update-Merker)
     */
    public static String singleReplaceMethod(// --
            final String pLineStr, // --
            final String pMethodName) 
    {
//System.out.println( "singleReplaceMethod " + pLineStr + " -> " + pMethodName ) ;
  return singleReplaceIdentifierForEscSeqColon(pLineStr, METHOD_NAME_ESC_SEQ, pMethodName);
    }// end method singleReplaceMethod

    /**
     * Setzen Package-Namen für eine Zeile
     * 
     * @param pLineStr Source-Zeile als String
     * @param pMethodName Name der aktuellen Methode
     * @return geänderten String oder null wenn nicht geändert(für  
     *     Update-Merker)
     */
    public static String singleReplacePackage(// --
            final String pLineStr, // --
            final String pMethodName) 
    {
//System.out.println( "singleReplaceMethod " + pLineStr + " -> " + pMethodName ) ;
  return singleReplaceIdentifierWithPointsForEscSeqColon(pLineStr, PACKAGE_NAME_ESC_SEQ, pMethodName);
    }// end method singleReplaceMethod

    /**
     * Aktualisieren einer Zeile mit dem übergebenen Inhalt (Java-Bezeichner)
     * für die übergeben Escape-Sequenz mit less than und greater than
     * Begrenzern.
     * 
     * @param pStr Source-Zeile als String
     * @param pEscSeqStr Escape-Sequenz
     * @param pNewContentStr einzufügender Inhalt
     * @return aktualisierter String oder null, wenn nichts zu aktualisieren 
     *     war
     */
    private static String singleReplaceIdentifierForEscSeqColon(
        final String pStr, //--
        final String pEscSeqStr, //--
        final String pNewContentStr)
    {
        boolean bUpdate = false;
        String[] strArr = (String[]) tokenize(pStr, pEscSeqStr, true).toArray(new String[0]);
        StringBuffer strBuffRet = new StringBuffer();
        
        for (int i = 0; i < strArr.length; i++)
        {
            if (strArr[ i ].equals(pEscSeqStr))
            {
                // Zeilen-Nummer eintragen
                strBuffRet.append(pEscSeqStr + pNewContentStr);
                // alte Zeilen-Nummer entfernen
                i++;
                if (i < strArr.length)
                {
                    // zur Optimierung einlesen vorhandenen Inhalt
                    // wenn gleich, dann Signal zum nicht update
                    strBuffRet.append(eraseIdentifier(strArr[ i ]));
                    if ( ! pNewContentStr.equals( getIdentifier(strArr[ i ] ) ) )
                    {
                        bUpdate = true;
                    }
                }
                else
                {
                    // anhängen der Zeilen-Nummer ans Ende ist immer update
                    bUpdate = true;
                }
            }
            else
            {
                strBuffRet.append(strArr[ i ]);
            }
        }// end for
        
        if (!bUpdate)
        {
            return null;
        }
        return strBuffRet.toString();
    }

    /**
     * Aktualisieren einer Zeile mit dem übergebenen Inhalt (Java-Bezeichner
     * mit Punkten)
     * für die übergeben Escape-Sequenz mit less than und greater than
     * Begrenzern.
     * 
     * @param pStr Source-Zeile als String
     * @param pEscSeqStr Escape-Sequenz
     * @param pNewContentStr einzufügender Inhalt
     * @return aktualisierter String oder null, wenn nichts zu aktualisieren 
     *     war
     */
    private static String singleReplaceIdentifierWithPointsForEscSeqColon(
        final String pStr, //--
        final String pEscSeqStr, //--
        final String pNewContentStr)
    {
        boolean bUpdate = false;
        String[] strArr = (String[]) tokenize(pStr, pEscSeqStr, true).toArray(new String[0]);
        StringBuffer strBuffRet = new StringBuffer();
        
        for (int i = 0; i < strArr.length; i++)
        {
            if (strArr[ i ].equals(pEscSeqStr))
            {
                // Zeilen-Nummer eintragen
                strBuffRet.append(pEscSeqStr + pNewContentStr);
                // alte Zeilen-Nummer entfernen
                i++;
                if (i < strArr.length)
                {
                    // zur Optimierung einlesen vorhandenen Inhalt
                    // wenn gleich, dann Signal zum nicht update
                    strBuffRet.append(eraseIdentifierWithPoints(strArr[ i ]));
                    if ( ! pNewContentStr.equals( getIdentifierWithPoints(strArr[ i ] ) ) )
                    {
                        bUpdate = true;
                    }
                }
                else
                {
                    // anhängen der Zeilen-Nummer ans Ende ist immer update
                    bUpdate = true;
                }
            }
            else
            {
                strBuffRet.append(strArr[ i ]);
            }
        }// end for
        
        if (!bUpdate)
        {
            return null;
        }
        return strBuffRet.toString();
    }

    // ****************************************************************************************************************

    /**
     * Setzen Package-Namen für alle Zeilen
     * 
     * @param fileLinesPa Array mit den Zeilen der Java-Datei als Strings
     * @return geänderten String oder null wenn nicht geändert(für  
     *     Update-Merker)
     */
    private static boolean replacePackage(String[] fileLinesPa)
    {
        // System.out.println( "replacePackage" ) ;

        String strPackageName = "";

        boolean bReturn = false;

        for (int i = 0; i < fileLinesPa.length; i++)
        {
                // Ermitteln Package-Name
                final String strLinePackageName = getPackageNameFromLine(fileLinesPa[ i ]);

                if (strLinePackageName != null)
                {
                    strPackageName = strLinePackageName;
                }

            // Eintragen Package-Name
            if (fileLinesPa[ i ].indexOf(PACKAGE_NAME_ESC_SEQ) >= 0)
            {
                // eine Zeile überarbeiten
                // Rückgabe null bedeutet, Inhalt war schon ok, update nicht nötig
                String strTmp = singleReplacePackage(fileLinesPa[ i ], strPackageName);

                if (strTmp != null)
                {
                    bReturn = true;
                    fileLinesPa[ i ] = strTmp;
                }
            }
        }
        return bReturn;
    }

    // ****************************************************************************************************************

    /**
     * Lesen eines Klassen-Namens von einer Zeile
     * 
     * @param pLineStr Source-Zeile als String
     * @return Klassen-Name als String oder null, wenn kein Klassen-Name in  
     *     dieser Zeile
     */
    private static String getClassNameFromLine(//--
        final String pLineStr)
    {
        final String noCommentStr = stripComments( pLineStr );
        if ( noCommentStr.indexOf( '"') >= 0 )
        {
            //eine Zeile mit einem String enthält gewöhnlich keine Klassen-Deklaration
            return null;
        }
        if ( noCommentStr.startsWith( "class ") )
        {
            return getIdentifier( noCommentStr.substring( 6 ) );
        }
        int classWordPos = noCommentStr.indexOf( " class " );
        if ( classWordPos > -1 )
        {
            return getIdentifier( noCommentStr.substring( classWordPos + 7 ) );
        }
        return null;
    }

    /**
     * Lesen eines Methoden-Namens von einer Zeile.
     * 
     * @param pLineStr Source-Zeile als String
     * @return Methoden-Name oder null, wenn kein Methoden-Name gefunden
     */
    private static String getMethodNameFromLine(//--
    final String pLineStr)
    {
        final String noCommentStr = stripComments( pLineStr );
        //Regel: ein Identifier, ein oder mehrere WhiteSpaces, Methoden-Name (Identifier), null oder mehrere WhiteSpaces, eine öffnende Klammer
        final int openParenPos = noCommentStr.indexOf( '(' );
        if ( noCommentStr.indexOf( '"') >= 0 )
        {
            //eine Zeile mit einem String enthält gewöhnlich keine Methoden-Deklaration
            return null;
        }
        if ( openParenPos < 3 )
        {
            //keine öffnende Klammer vorhanden
            return null;
        }
        
        //String bis vor öffnende Klammer
        final String preOpenParenStr = noCommentStr.substring( 0 , openParenPos);

        final String[] tokenArr = (String[]) tokenize( preOpenParenStr , " " , false ).toArray(new String[0]);
        if ( tokenArr.length < 2 )
        {
            //es muss mindestens einen Rückgabetyp und einen Methoden-Namen geben
            return null;
        }

        final String retTypeStr = tokenArr[ tokenArr.length - 2 ];
        final String methodNameStr = tokenArr[ tokenArr.length - 1 ];

        if ( "new".equals(retTypeStr) )
        {
            //Rückgabetyp ist ein 'new', also keine Methodendeklaration
            return null;
        }

        if ( "if".equals(methodNameStr) || "for".equals(methodNameStr) ||  "while".equals(methodNameStr) ||  "do".equals(methodNameStr) )
        {
            //Beginn if, for, while, do
            return null;
        }

        if ( isIdentifier( retTypeStr ) && isIdentifier( methodNameStr ) )
        {
            return methodNameStr;
        }
        return null;
    }

    /**
     * Lesen eines Package-Namens von einer Zeile
     * 
     * @param pLineStr Source-Zeile als String
     * @return Klassen-Name als String oder null, wenn kein Package-Name in  
     *     dieser Zeile
     */
    private static String getPackageNameFromLine(//--
        final String pLineStr)
    {
        final String noCommentStr = stripComments( pLineStr ).trim();
        if ( noCommentStr.startsWith( "package " ) && noCommentStr.endsWith(";") )
        {
            String packageStr = cutLeft( noCommentStr , "package " ).trim();
            packageStr = cutRight(packageStr, ";").trim();
            return packageStr;
        }
        return null;
    }

    /**
     * Entfernen Kommentare
     * 
     * @param pLineStr Source-Zeile als String
     * @return Zeile ohne Kommentare
     */
    private static String stripComments(String pLineStr)
    {
        final StringBuffer strBuff = new StringBuffer();
        boolean bInComment = false;
        for (int i = 0; i < pLineStr.length(); i++)
        {
            if ( pLineStr.charAt( i ) == '/' && charAt(pLineStr, i + 1 ) == '/' )
            {
                //der Kommentar geht bis zum Zeilenende
                break;
            }
            if ( pLineStr.charAt( i ) == '/' && charAt(pLineStr, i + 1 ) == '*' )
            {
                //Beginn Kommentar
                bInComment = true;
            }
            if ( charAt( pLineStr , i - 2 ) == '*' && charAt(pLineStr, i - 1 ) == '/' )
            {
                //Ende Kommentar
                bInComment = false;
            }
            if ( ! bInComment )
            {
                strBuff.append( pLineStr.charAt(i) );
            }
        }
                
        return strBuff.toString() ;
    }

    /**
     * Lesen eines Java-Bezeichners aus einem String
     * 
     * @param pStr zu lesender String
     * @return Bezeichner als String oder null, wenn kein gültiger  
     *     Java-Bezeichner
     */
    private static String getIdentifier(String pStr)
    {
        if ( ! Character.isJavaIdentifierStart( charAt( pStr , 0 ) ) )
        {
            //throw new IllegalArgumentException( pStr );
            return null;
        }
        final StringBuffer strBuff = new StringBuffer();
        for (int i = 0; i < pStr.length(); i++)
        {
            if ( ! Character.isJavaIdentifierPart( charAt( pStr , i ) ) )
            {
                break;
            }
            strBuff.append( pStr.charAt( i ) );
        }
        return strBuff.toString() ;
    }

    /**
     * Lesen eines Java-Bezeichners mit Punkten aus einem String
     * 
     * @param pStr zu lesender String
     * @return Bezeichner als String oder null, wenn kein gültiger  
     *     Java-Bezeichner
     */
    private static String getIdentifierWithPoints(String pStr)
    {
        if ( ! Character.isJavaIdentifierStart( charAt( pStr , 0 ) ) )
        {
            //throw new IllegalArgumentException( pStr );
            return null;
        }
        final StringBuffer strBuff = new StringBuffer();
        for (int i = 0; i < pStr.length(); i++)
        {
            final char runChar = pStr.charAt(i);
            if ( ( runChar != '.' ) && ( ! Character.isJavaIdentifierPart( runChar ) ) )
            {
                break;
            }
            strBuff.append( runChar );
        }
        return strBuff.toString() ;
    }

    /**
     * Löschen Java-Bezeichner in einem String
     * 
     * @param strPa String mit zu löschenden Ziffern
     * @return String mit gelöscheten Ziffern
     */
    private static String eraseIdentifier(String strPa)
    {
        if (strPa == null)
        {
            return null;
        }

        int iPos;

        for (iPos = 0;
                iPos < strPa.length() && Character.isJavaIdentifierPart( strPa.charAt(iPos) );
                iPos++)
        {
            ;
        }
        if (iPos >= strPa.length())
        {
            return "";
        }
        else
        {
            return strPa.substring(iPos);
        }
    }

    /**
     * Löschen Java-Bezeichner mit Punkten in einem String
     * 
     * @param strPa String mit zu löschenden Ziffern
     * @return String mit gelöscheten Ziffern
     */
    private static String eraseIdentifierWithPoints(String strPa)
    {
        if (strPa == null)
        {
            return null;
        }

        int iPos;

        for (iPos = 0;
                iPos < strPa.length() 
                && ( Character.isJavaIdentifierPart( strPa.charAt(iPos) )
                    || strPa.charAt(iPos) == '.' );
                iPos++)
        {
            ;
        }
        if (iPos >= strPa.length())
        {
            return "";
        }
        else
        {
            return strPa.substring(iPos);
        }
    }

    /**
     * Prüfen eines Strings auf gültigen Java-Bezeichner
     * 
     * @param pStr zu prüfender String
     * @return ob gültiger Java-Bezeichner
     */
    private static boolean isIdentifier(String pStr)
    {
        if ( ! Character.isJavaIdentifierStart( charAt( pStr , 0 ) ) )
        {
            return false;
        }
        for (int i = 0; i < pStr.length(); i++)
        {
            if ( ! Character.isJavaIdentifierPart( charAt( pStr , i ) ) )
            {
                return false;
            }
        }
        return true;
    }

    // ****************************************************************************************************************

    /**
     * Löschen bis zum Grösser-Als-Zeichen oder Zeilenende
     * 
     * @param strPa Parameter-String
     * @return String mit gelöscheten Ziffern
     */
    private static String eraseToGT(String strPa)
    {
        if (strPa == null)
        {
            return null;
        }

        int iPos;

        for (iPos = 0;
                iPos < strPa.length() && strPa.charAt(iPos) != '>';
                iPos++)
        {
            ;
        }
        if (iPos < strPa.length() - 1)
        {
            iPos++; // Grösser-als-Zeichen überspringen
        }

        if (iPos >= strPa.length())
        {
            return "";
        }
        else
        {
            return strPa.substring(iPos);
        }
    }// end method erasePreNumbers

    /**
     * Ermitteln des Textteiles bis vor das endende Gr&ouml;sser-als-Zeichen.
     * 
     * @param strPa zu parsender String
     * @return ermittelter Text oder null bei Fehler
     */
    private static String getTextToGt(String strPa)
    {
        if (strPa == null)
        {
            return null;
        }
        StringBuffer strBuffRet = new StringBuffer();
        int iPos;

        for (iPos = 0;
                iPos < strPa.length() && strPa.charAt(iPos) != '>';
                iPos++)
        {
            strBuffRet.append(strPa.charAt(iPos));
        }// end for
        return strBuffRet.toString();
    }// end method getTextToGt

    // ************************************************************************************************************************

    /**
     * Löschen führende Ziffern in einem String
     * 
     * @param strPa String mit zu löschenden Ziffern
     * @return String mit gelöscheten Ziffern
     */
    private static String erasePreNumbers(String strPa)
    {
        if (strPa == null)
        {
            return null;
        }

        int iPos;

        for (iPos = 0;
                iPos < strPa.length() && strPa.charAt(iPos) >= '0' && strPa.charAt(iPos) <= '9';
                iPos++)
        {
            ;
        }
        if (iPos >= strPa.length())
        {
            return "";
        }
        else
        {
            return strPa.substring(iPos);
        }
    }// end method erasePreNumbers

    /**
     * Ermitteln führende Nummer durch Parsen bis zum ersten
     * Auftreten eines nichtnumerischen Zeichens oder Stringende.
     * 
     * @param strPa zu parsender String
     * @return ermittelte Nummer oder -1 bei Fehler
     */
    public static int getNumber(String strPa)
    {
        if (strPa == null)
        {
            return -1;
        }
        int iRet = 0;
        int iPos;

        for (iPos = 0;
                iPos < strPa.length() && strPa.charAt(iPos) >= '0' && strPa.charAt(iPos) <= '9';
                iPos++)
        {
            iRet *= 10;
            iRet += (Integer.valueOf("" + strPa.charAt(iPos))).intValue();
        }// end for
        return iRet;
    }// end method getNumber

    /**
     * Ermitteln führende Nummer durch Parsen bis zum ersten
     * Auftreten eines nichtnumerischen Zeichens oder Stringende.
     * 
     * @param strPa zu parsender String
     * @return ermittelte Nummer oder -1 bei Fehler
     */
    private static String getNumberStr(String strPa)
    {
        if (strPa == null)
        {
            return null;
        }
        int iPos;
        StringBuffer strBuff = new StringBuffer();

        for (iPos = 0;
                iPos < strPa.length() && strPa.charAt(iPos) >= '0' && strPa.charAt(iPos) <= '9';
                iPos++)
        {
            strBuff.append( strPa.charAt( iPos ) );
        }// end for
        return strBuff.toString();
    }// end method getNumberStr

    /**
     * Zerlegen eines Strings in Token . <br/>
     * Ersatz für StringTokenizer, da dieser bei Delimitern, die aus mehreren Zeichen
     * bestehen, Probleme hat.
     * 
     * @param strPaValue zu zerteilender String
     * @param strPaDelim Trenn-String
     * @param bPaInclDelim ob Trenn-Strings mit in die Ausgabe  
     *     aufgenommen werden sollen
     * @return Arraylist mit den String-Teilen (Token) oder null
     */
    public static ArrayList<String> tokenize(
            final String strPaValue , 
            final String strPaDelim , 
            final boolean bPaInclDelim )
    {
        if (strPaValue == null)
        {
            return null;
        }

        final ArrayList<String> arrList = new ArrayList<String>();

        if (strPaDelim == null)
        {
            arrList.add(strPaValue);
            return arrList;
        }

        int iPos = 0;

        while (iPos < strPaValue.length())
        {
            if (strPaValue.indexOf(strPaDelim, iPos) < 0)
            {
                // Delimiter nicht mehr enthalten
                // Rückgabe des Restes vom String
                arrList.add(strPaValue.substring(iPos));
                iPos = strPaValue.length(); // ans Ende setzen für evtl. weitere Auswertungen
                break;
            }
            else
            {
                // Delimiter enthalten
                if (strPaValue.startsWith(strPaDelim, iPos))
                { // ab Position
                    // Beginn mit Delimiter
                    if (bPaInclDelim)
                    {
                        arrList.add(strPaDelim);
                    }
                    iPos += strPaDelim.length();
                }
                else
                {
                    // Beginn mit Token (Teilstück)
                    int iEndPos = strPaValue.indexOf(strPaDelim, iPos); // evtl. - 1 ;
                    String strTmp = strPaValue.substring(iPos, iEndPos);

                    arrList.add(strTmp);
                    iPos += strTmp.length(); // = iEndPos ; // oder += iEndPos
                }
            }// end if
        }// end while
        return arrList;
    }// end method tokenize

    /**
     * Bak-File anlegen . <br/>
     * Als Endung wird <b>.bak</b> angehängt.<br/>
     * Siehe {@link #BAKUP_FILE_SUFFIX}
     * 
     * @param strPaFileName von dieser Datei ein Bak-File anlegen
     * @return true wenn erflgreich
     */
    public static boolean writeBakFile(String strPaFileName)
    {
        if ( ! bClCreateBackupFiles )
        {
            return true;
        }
        try
        {
            String strBakFileName = strPaFileName + BAKUP_FILE_SUFFIX;
            File fileBak = new File(strBakFileName);

            if (fileBak.exists())
            {
                fileBak.delete();
            }
            File fileOriginal = new File(strPaFileName);
            boolean bOk = fileOriginal.renameTo(fileBak);

            return bOk;
        } catch (Exception e)
        {
            System.err.println("" + e);
            e.printStackTrace();
        }
        return false;
    }// end method writeBakFile

    /**
     * Textdatei zeilenweise in ein String-Array einziehen . <br/>
     * Jede Zeile bildet einen String im String-Array.<br/>
     * 
     * @param fileName Dateiname
     * @return String-Array
     */
    public static String[] file2StrArr(String fileName)
    {
        if (fileName == null)
        {
            return null;
        }
        return file2StrArr(new File(fileName));
    } // end method fileToStrArr

    /**
     * Textdatei zeilenweise in ein String-Array einziehen . <br/>
     * Jede Zeile bildet einen String im String-Array.<br/>
     * 
     * @param inFile File-Object
     * @return String-Array
     */
    public static String[] file2StrArr(
            final File inFile )
    {
        if (inFile == null)
        {
            return null;
        }

        try
        {
            // BufferedReader zum zeilenweisen Lesen
            FileReader      defFileReader = new FileReader(inFile);
            BufferedReader  defBufferedReader = new BufferedReader(defFileReader);
            ArrayList<String> strArrayList = new ArrayList<String>();
            String          readLine = null;

            while ((readLine = defBufferedReader.readLine()) != null) // bis zum Ende der Datei
            {
                // readLine = readLine.trim();
                strArrayList.add(readLine);
            } // end while

            defBufferedReader.close();

            String[]  retArr = arrayListToStrArr(strArrayList);

            return retArr;
        } catch (Exception e)
        {// Log.fatal( e ) ;
            // throw new RuntimeException( Log.getMessageAndStackTrace( e ) ) ;
        }
        return null;
    } // end method file2StrArr

    /**
     * Speichern eines String in einer Datei ohne weitere Informationen
     * 
     * @param outStrArr auszugebendes String-Array
     * @param fileName Name der Datei, in welcher der String  
     *     gespeichert werden soll
     * @return ob erfolgreich
     */
    public static boolean strArr2file(String[] outStrArr, String fileName)
    {
        return strArr2file(outStrArr, new File(fileName));
    } // end method strArr2file

    /**
     * Speichern eines String-Array in einer Datei ohne weitere Informationen
     * 
     * @param outStrArr auszugebendes String-Array
     * @param outFile Datei, in welcher der String gespeichert werden  
     *     soll
     * @return ob erfolgreich
     */
    public static boolean strArr2file(String[] outStrArr, File outFile)
    {
        StringBuffer sb = new StringBuffer();

        for (int i = 0; i < outStrArr.length; i++)
        {
            sb.append(outStrArr[ i ]);
            if (i < outStrArr.length - 1)
            {
                sb.append('\n');
            }
        }
        return str2file(sb.toString(), outFile);
    } // end method strArr2file

    /**
     * Speichern eines String in einer Datei ohne weitere Informationen
     * 
     * @param outStr auszugebender String
     * @param fileName Name der Datei, in welcher der String  
     *     gespeichert werden soll
     * @return ob erfolgreich
     */
    public static boolean str2file(String outStr, String fileName)
    {
        return str2file(outStr, new File(fileName));
    } // end method str2file

    /**
     * Speichern eines String in einer Datei ohne weitere Informationen
     * 
     * @param outStr auszugebender String
     * @param outFile Datei, in welcher der String gespeichert werden  
     *     soll
     * @return ob erfolgreich
     */
    public static boolean str2file(String outStr, File outFile)
    {
        boolean bReturn = false;

        try
        {
            if (outFile.exists())
            {
                outFile.delete();
            }

            outFile.createNewFile();

            FileWriter  outFileWriter = new FileWriter(outFile);

            outFileWriter.write(outStr);
            outFileWriter.close();

            bReturn = true;
        } catch (Exception e)
        {// Log.fatal(e);
        }

        return bReturn;
    } // end method str2file

    /**
     * ArrayList mit Strings in String[] umwandeln . <br/>
     * Verwendet in {@link #file2StrArr}.<br/>
     * 
     * @param strArrayList ArrayList mit Strings
     * @return String[]
     */
    public static String[] arrayListToStrArr(
            final ArrayList<String> strArrayList)
    {
        return strArrayList.toArray( new String[ strArrayList.size() ] );
    } // end method arrayListToStrArr

    /**
     * Filter the specified string for characters that are senstive to
     * HTML interpreters, returning the string with these characters replaced
     * by the corresponding character entities.
     * 
     * @param value The string to be filtered and returned
     * @return the filtered String
     */
    public static final String replaceBackSlash(String value)
    {
        if (value == null)
        {
            return (null);
        }

        char  content[] = new char[value.length()];

        value.getChars(0, value.length(), content, 0);

        StringBuffer  result = new StringBuffer(content.length + 50);

        for (int i = 0; i < content.length; i++)
        {
            switch (content[i])
            {

            case '\\':
                result.append("\\" + "\\");

                break;

            default:
                result.append(content[i]);
            }
        }

        return (result.toString());
    } // end method replaceBackSlash

    /**
     * Abfrage eines Zeichen von einem String mit Abfangung
     * Exception bei falscher Position.
     * 
     * @param pStr abzufragender String
     * @param pPos Zeichenposition
     * @return abgefragtes Zeichen oder chr(0), wenn Position nicht ok
     */
    public static char charAt(String pStr , int pPos)
    {
        try
        {
            return pStr.charAt(pPos);
        }
        catch (IndexOutOfBoundsException e)
        {
            return (char) 0;
        }
    }
        
    /**
     * Teilstring von String links abschneiden, wenn möglich
     * 
     * @param srcStr Original-String
     * @param cutStr abzuschneidender String
     * @return abgeschnittener String
     */
    public static String cutLeft( String srcStr , String cutStr ) {
      if ( srcStr == null || cutStr == null ) {
        return srcStr;
      }

      String  retStr = srcStr;

      if (retStr.length() >= cutStr.length() && retStr.startsWith( cutStr ) ) {
        retStr = retStr.substring(cutStr.length()); // cutStr abschneiden
      }

      return retStr;
    }//end method cutLeft

    /**
     * Teilstring von String rechts abschneiden, wenn möglich
     * 
     * @param srcStr Original-String
     * @param cutStr abzuschneidender String
     * @return abgeschnittener String
     */
    public static String cutRight( String srcStr , String cutStr ) {
      if (srcStr == null || cutStr == null) {
        return srcStr;
      }

      String  retStr = srcStr;

      if ( retStr.length() >= cutStr.length() && retStr.endsWith( cutStr ) ) {
        retStr = retStr.substring(0, retStr.length() - cutStr.length());  // cutStr abschneiden
      }

      return retStr;
    }//end method cutRight

}// end class LineNumbers