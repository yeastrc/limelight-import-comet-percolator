/*
 * Original author: Michael Riffle <mriffle .at. uw.edu>
 *
 * Copyright 2018 University of Washington - Seattle, WA
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.yeastrc.limelight.xml.comet_percolator.main;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.math.BigDecimal;

import org.yeastrc.limelight.xml.comet_percolator.objects.ConversionParameters;
import org.yeastrc.limelight.xml.comet_percolator.objects.ConversionProgramInfo;

import org.yeastrc.limelight.xml.comet_percolator.constants.Constants;

import picocli.CommandLine;

@CommandLine.Command(name = "java -jar " + Constants.CONVERSION_PROGRAM_NAME,
		mixinStandardHelpOptions = true,
		version = Constants.CONVERSION_PROGRAM_NAME + " " + Constants.CONVERSION_PROGRAM_VERSION,
		sortOptions = false,
		synopsisHeading = "%n",
		descriptionHeading = "%n@|bold,underline Description:|@%n%n",
		optionListHeading = "%n@|bold,underline Options:|@%n",
		description = "Convert the results of a Comet + Percolator analysis to a Limelight XML file suitable for import into Limelight.\n\n" +
				"More info at: " + Constants.CONVERSION_PROGRAM_URI
)

/**
 * @author Michael Riffle
 *
 */
public class MainProgram implements Runnable {

	@CommandLine.Option(names = { "-c", "--comet-params" }, required = true, description = "Full path to the comet params file")
	private File cometParamsFile;

	@CommandLine.Option(names = { "-f", "--fasta-file" }, required = false, description = "(Optional) Full path to FASTA file used in the experiment. E.g., /data/yeast.fa If not supplied, comet.params will be used to find FASTA file.")
	private File fastaFile;

	@CommandLine.Option(names = { "-p", "--percolator-file" }, required = true, description = "Full path to percolator output XML file")
	private File percolatorFile;

	@CommandLine.Option(names = { "-d", "--pepxml-directory" }, required = false, description = "(Optional) By default, this program expects the pepXML file(s) to be in the same directory as the percolator file. If this is not true, use this option to specify the _directory_ in which the pepXML files may be found.")
	private File pepXMLDirectory;

	@CommandLine.Option(names = { "-q", "--q-value" }, required = false, description = "(Optional) Override the default q-value cutoff to this value.")
	private BigDecimal qValueOverride;

	@CommandLine.Option(names = { "-o", "--out-file" }, required = true, description = "Full path to use for the Limelight XML output file. E.g., /data/my_analysis/crux.limelight.xml")
	private File outFile;

	@CommandLine.Option(names = { "-v", "--verbose" }, required = false, description = "If this parameter is present, error messages will include a full stacktrace. Helpful for debugging.")
	private boolean verboseRequested = false;

	@CommandLine.Option(names = { "--open-mod" }, required = false, description = "If this parameter is present, the converter will run in open mod mode. Mass diffs on the PSMs will be treated as an unlocalized modification mass for the peptide.")
	private boolean isOpenMod = false;

	private String[] args;

	public void run() {

		printRuntimeInfo();

		if( !cometParamsFile.exists() ) {
			System.err.println( "Could not find comet params file: " + cometParamsFile.getAbsolutePath() );
			System.exit( 1 );
		}

		if( !percolatorFile.exists() ) {
			System.err.println( "Could not find percolator XML file: " + percolatorFile.getAbsolutePath() );
			System.exit( 1 );
		}

		// if a FASTA file was specified, ensure it exists
		if( fastaFile != null ) {

			if (!fastaFile.exists()) {
				System.err.println("Could not find fasta file: " + fastaFile.getAbsolutePath());
				System.exit(1);
			}
		}

		// if a data directory was specified, ensure it exists
		if( pepXMLDirectory != null ) {

			if( !pepXMLDirectory.exists() ) {
				System.err.println("Could not directory: " + pepXMLDirectory.getAbsolutePath());
				System.exit(1);
			}

			if( !pepXMLDirectory.isDirectory() ) {
				System.err.println( pepXMLDirectory.getAbsolutePath() + " is not a directory.");
				System.exit(1);
			}

		}

		ConversionProgramInfo cpi = ConversionProgramInfo.createInstance( String.join( " ",  args ) );

		ConversionParameters cp = new ConversionParameters();
		cp.setConversionProgramInfo( cpi );
		cp.setFastaFile( fastaFile );
		cp.setPepXMLDataDirectory( pepXMLDirectory );
		cp.setCometParametersFile( cometParamsFile );
		cp.setPercolatorXMLFile( percolatorFile );
		cp.setLimelightXMLOutputFile( outFile );
		cp.setOpenMod(isOpenMod);
		cp.setqValueOverride( qValueOverride );

		try {
			ConverterRunner.createInstance().convertCometPercolatorToLimelightXML(cp);
		} catch( Throwable t ) {
			System.err.println( "Encountered error during conversion: " + t.getMessage() );

			if(verboseRequested) {
				t.printStackTrace();
			}

			System.exit( 1 );
		}

		System.exit( 0 );
	}

	public static void main( String[] args ) {

		MainProgram mp = new MainProgram();
		mp.args = args;

		CommandLine.run(mp, args);
	}

	/**
	 * Print runtime info to STD ERR
	 * @throws Exception
	 */
	public static void printRuntimeInfo() {

		try( BufferedReader br = new BufferedReader( new InputStreamReader( MainProgram.class.getResourceAsStream( "run.txt" ) ) ) ) {

			String line = null;
			while ( ( line = br.readLine() ) != null ) {

				line = line.replace( "{{URL}}", Constants.CONVERSION_PROGRAM_URI );
				line = line.replace( "{{VERSION}}", Constants.CONVERSION_PROGRAM_VERSION );

				System.err.println( line );

			}

			System.err.println( "" );

		} catch ( Exception e ) {
			System.out.println( "Error printing runtime information." );
		}
	}

}
