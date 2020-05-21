Comet/Percolator to limelight XML Converter
=======================================

Use this program to convert the results of a Comet + Percolator analysis to
limelight XML suitable for import into the limelight web application. Requires
that the Percolator output be represented as XML (see -X option in Percolator).
Also requires that comet output be present as pepXML files.

How To Run
-------------
1. Download the [latest release](https://github.com/yeastrc/limelight-import-comet-percolator/releases).
2. Run the program ``java -jar cometPercolator2LimelightXML.jar`` with no arguments to see the possible parameters. Requires Java 8 or higher.

Command line documentation
---------------------------

```
java -jar cometPercolator2LimelightXML.jar [-hvV] [--open-mod]
                                           -c=<cometParamsFile>
                                           [-d=<pepXMLDirectory>]
                                           [-f=<fastaFile>] -o=<outFile>
                                           -p=<percolatorFile>

Description:

Convert the results of a Comet + Percolator analysis to a Limelight XML file
suitable for import into Limelight.

More info at: https://github.com/yeastrc/limelight-import-comet-percolator

Options:
  -c, --comet-params=<cometParamsFile>
                             Full path to the comet params file
  -f, --fasta-file=<fastaFile>
                             (Optional) Full path to FASTA file used in the
                               experiment. E.g., /data/yeast.fa If not supplied,
                               comet.params will be used to find FASTA file.
  -p, --percolator-file=<percolatorFile>
                             Full path to percolator output XML file
  -d, --pepxml-directory=<pepXMLDirectory>
                             (Optional) By default, this program expects the pepXML
                               file(s) to be in the same directory as the percolator
                               file. If this is not true, use this option to specify
                               the _directory_ in which the pepXML files may be
                               found.
  -o, --out-file=<outFile>   Full path to use for the Limelight XML output file. E.
                               g., /data/my_analysis/crux.limelight.xml
  -v, --verbose              If this parameter is present, error messages will
                               include a full stacktrace. Helpful for debugging.
      --open-mod             If this parameter is present, the converter will run in
                               open mod mode. Mass diffs on the PSMs will be treated
                               as an unlocalized modification mass for the peptide.
  -h, --help                 Show this help message and exit.
  -V, --version              Print version information and exit.

```
