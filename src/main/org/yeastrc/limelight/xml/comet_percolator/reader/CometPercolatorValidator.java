package org.yeastrc.limelight.xml.comet_percolator.reader;

import org.yeastrc.limelight.xml.comet_percolator.objects.CometReportedPeptide;
import org.yeastrc.limelight.xml.comet_percolator.objects.CometResults;
import org.yeastrc.limelight.xml.comet_percolator.objects.PercolatorResults;
import org.yeastrc.limelight.xml.comet_percolator.utils.CometPercolatorUtils;

public class CometPercolatorValidator {

	public static boolean validateData( CometResults cometResults, PercolatorResults percolatorResults ) throws Exception {
		
		for( CometReportedPeptide cometPeptide : cometResults.getPeptidePSMMap().keySet() ) {
			
			String percolatorReportedPeptide = CometPercolatorUtils.getPercolatorReportedPeptideForCometPeptide( cometPeptide );

			// ensure all peptides found by comet are in percolator results
			if( !percolatorResults.getReportedPeptideResults().containsKey( percolatorReportedPeptide ) ) {
				System.err.println( "Error: Percolator results not found for peptide: " + percolatorReportedPeptide );
				return false;
			}
			
			// ensure all scans found by comet for each peptide are scored by percolator
			for( int scanNumber : cometResults.getPeptidePSMMap().get( cometPeptide ).keySet() ) {
				
				if( !percolatorResults.getReportedPeptideResults().get( percolatorReportedPeptide ).getPercolatorPSMs().containsKey( scanNumber ) ) {
					System.err.println( "Error: Could not find PSM data for scan number " + scanNumber + " in percolator results for peptide: " + percolatorReportedPeptide );
					return false;
				}				
			}
		}
		
		return true;
	}
}
