package org.yeastrc.limelight.xml.comet_percolator.reader;

import org.yeastrc.limelight.xml.comet_percolator.objects.CometReportedPeptide;
import org.yeastrc.limelight.xml.comet_percolator.objects.CometResults;
import org.yeastrc.limelight.xml.comet_percolator.objects.PercolatorResults;
import org.yeastrc.limelight.xml.comet_percolator.utils.CometParsingUtils;

public class CometPercolatorValidator {

	/**
	 * Ensure all percolator results have a result in the comet data
	 * 
	 * @param cometResults
	 * @param percolatorResults
	 * @return
	 * @throws Exception
	 */
	public static boolean validateData( CometResults cometResults, PercolatorResults percolatorResults ) throws Exception {
		
		for( String percolatorReportedPeptide : percolatorResults.getReportedPeptideResults().keySet() ) {
			
			CometReportedPeptide cometReportedPeptide = CometParsingUtils.getCometReportedPeptideForString( percolatorReportedPeptide, cometResults );

			if( cometReportedPeptide == null ) {
				System.err.println( "Error: Comet results not found for peptide: " + percolatorReportedPeptide );
				return false;
			}
			
			for( int scanNumber : percolatorResults.getReportedPeptideResults().get( percolatorReportedPeptide ).getPercolatorPSMs().keySet() ) {
				
				if( !cometResults.getPeptidePSMMap().get( cometReportedPeptide ).containsKey( scanNumber ) ) {
					System.err.println( "Error: Could not find PSM data for scan number " + scanNumber + " in percolator results for peptide: " + percolatorReportedPeptide );
					return false;
				}
			}
			
		}
		
		return true;
	}
	
}
