package org.yeastrc.limelight.xml.comet_percolator.utils;

import org.yeastrc.limelight.xml.comet_percolator.objects.*;

import java.io.File;
import java.io.FileNotFoundException;
import org.yeastrc.limelight.xml.comet_percolator.objects.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ConversionUtils {

    /**
     * Add the FASTA file from the comet params to the conversion params if there isn't a FASTA file
     * set in the conversion params
     *
     * @param conversionParams
     * @param cometParams
     * @throws Throwable
     */
    public static void conditionallyAddFastaToConversionParameters(ConversionParameters conversionParams,
                                                      CometParameters cometParams ) throws Throwable {

        if( conversionParams.getFastaFile() != null ) {
            return;
        }

        File fastaFile = new File( cometParams.getFastaFile() );

        if( fastaFile == null ) {
            throw new Exception( "Could not find a FASTA file in the comet.params. Please specify with -f" );
        }

        if( !fastaFile.exists() ) {
            throw new Exception( "Found FASTA file in comet.params: " + fastaFile.getAbsolutePath() + ". However, file could not be found." );
        }

        conversionParams.setFastaFile( fastaFile );
    }

    /**
     *
     * @param conversionParams
     * @param percolatorResults
     * @return
     * @throws Exception
     */
    public static Collection<File> findPepXMLFilesUsingPercolatorResults(ConversionParameters conversionParams,
                                                                         PercolatorResults percolatorResults ) throws Exception {

        Collection<File> pepXMLFiles = new HashSet<>();
        Collection<String> donePrefixes = new HashSet<>();

        for( PercolatorPeptideData ppData : percolatorResults.getReportedPeptideResults().values() ) {
            for( String pepXMLFileName : ppData.getPercolatorPSMs().keySet() ) {
                File pepXMLFile = getPepXMLFileWithPrefix( pepXMLFileName, conversionParams );
                pepXMLFiles.add( pepXMLFile );
            }
        }

        return pepXMLFiles;
    }

    /**
     * Get the pepXML file associated with the given prefix, given the conversion parameters
     *
     * @param pepXMLFileName
     * @param conversionParameters
     * @return
     * @throws FileNotFoundException If the expected file cannot be found.
     */
    public static File getPepXMLFileWithPrefix( String pepXMLFileName, ConversionParameters conversionParameters ) throws FileNotFoundException {

        File directory = null;

        if( conversionParameters.getPepXMLDataDirectory() != null ) {
            directory = conversionParameters.getPepXMLDataDirectory();
        } else {
            directory = conversionParameters.getPercolatorXMLFile().getParentFile();
        }

        File pepXMLFile = new File( directory, pepXMLFileName );

        if( !pepXMLFile.exists() ) {
            throw new FileNotFoundException( "Could not find file: " + pepXMLFile.getAbsolutePath() + ". May need to specify data directory with -d option." );
        }

        return pepXMLFile;
    }

    /**
     * Parse the psm id to get the expected pepXML file prefix (everything before .pep.xml)
     *
     * @param psmId
     * @return The pepXML file prefix
     * @throws Exception If the psm id cannot be parsed
     */
    public static String getPepXMLPrefixFromPsmId( String psmId ) throws Exception {

        // expecting a psmId like: QEP2_2018_1128_AZ_104_az753_AZ_511_3_1
        // last number is index of result for scan
        // second to last number is charge
        // third to list number is scan number
        // rest is what we want

        Pattern p = Pattern.compile( "^(.+)_\\d+_\\d+_\\d+$" );
        Matcher m = p.matcher( psmId );
        if( m.matches() ) {
            return m.group( 1 );
        }

        throw new Exception( "Could not find the pepXML prefix in PSM id: " + psmId );

    }

    /**
     * Get all protein names found for the given reported peptide. Checks every PSM because... you never
     * know.
     *
     * @param cometReportedPeptide
     * @param cometResults
     * @return
     */
    public static Collection<String> getProteinsNamesForCometReportedPeptide(CometReportedPeptide cometReportedPeptide, CometResults cometResults ) {

        Collection<String>  proteinNames = new HashSet<>();

        Map<String, Map<Integer, CometPSM>> cometResultMap = cometResults.getPeptidePSMMap().get( cometReportedPeptide );

        for( Map<Integer, CometPSM> scanNumberPSMMap: cometResultMap.values() ) {
            for( CometPSM psm : scanNumberPSMMap.values() ) {
                proteinNames.addAll( psm.getProteinNames() );
            }
        }

        return proteinNames;
    }

}
