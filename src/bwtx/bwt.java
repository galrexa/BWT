package bwtx;

/**
*  SCHILLER LAB SOFTWARE
*  PROJECT: Burrows Wheeler transform of a DNA sequence
*  TOPIC: This algorithm is use by Bowtie and Soap2 to align DNA sequences.
*  DATE: 7/13/14
*  @author Martin Schiller, rookie code
*  @author Jay Vyas, advisor to rookie
*  WEBSITE: http://docs.oracle.com/javase/6/docs/api/   
*  			http://www.cs.utexas.edu/~scottm/cs307/codingSamples.htm*
*  COPYRIGHT: Copyright (c) 2013, SCHILLER LAB, UNIVERSITY OF NEVADA LAS VEGAS All rights reserved 
*
*  CLASS: BWTCalc This class takes seq object from inputDNA class, performs a bwt transform
*  and outputs it to the screen. The transformations steps (methods) are: 1) seqAppend to add < and >
	*  as a grammar to indicate the ends of the input sequence 2) to generate
	*  an array of the sequence where each member is offset by on character (call sequenceRotateArray
	*   method), 3) to sort the seqRotArr into alphabetical order, 4) to create a string from the last 
	*   column of the seqRotArr call seqbtw.  The main method also calls another class BWTCompressor to 
	*   compress the bwt string.
*/
public class bwt
{
	/**
	*  Variables
	*  String seq is an input DNA sequence
	*  String seqapp is a seq appended with a at the beginning and z at end
	*  Integer seqlength is the length of the sequence 
	*  Integer aseqlength is the length of the appended sequence
	*  Array seqrotarr[] is an array with rotated sequence offset by one
	*  Array sortseqarr [ j ] is an alphabetically sorted array of seqrotarr[]
	*  String sorttemp is a temporary holder for the sorting method
	*  btwseq is the burrows wheeler transform return value of class
	*/
	//private static String seq = "AGTCACAGTCATCAGTCTAGTCAGTCAGTCAGTCTACATAGCGATTACGGCATCAGTCAGTCATCGA";
	public static String seq = "GGATCCTTT";  // junit unit testing sequence
	
	/**
	* The main method prints the sequence, calls a series of methods for steps in the transformation,
	*  and outputs the burrows wheeler transform (bwt). The transformations steps are: 1) to append 
	*  characters to the ends of the input sequence to indicate start and stop positions, 2) to generate
	*  an array of the sequence where each member is offset by on character (call sequenceRotateArray
	*   method), 3) to sort the seqRotArr into alphabetical order, 4) to create a string from the last 
	*   column of the seqRotArr call seqbtw.  The main method also calls another class BWTCompressor to 
	*   compress the bwt string.
	*/
	public static void main(String[] args)// add class for scanner input from website.  Convert to class later
	{
		int seqlength = sequenceLength();
		String seqapp = sequenceAppend(seq); 
		int aseqlength = appendedSequenceLength(seqlength);
		String[] seqrotarr= sequenceRotateArray(seqapp, aseqlength);
		String[] sortseqarr = sortArray(seqrotarr, aseqlength);
		String seqbwt = sequenceBWT(sortseqarr, aseqlength);	
		String bwtcompress = BWTCompressor.compress(seqbwt, aseqlength);  // This calls other class
		printResults(seqlength, aseqlength, (seqrotarr), (sortseqarr), seq, seqbwt, bwtcompress);
	}
	//-------------------end of main method -----------------------------------------------------------
	/**
	 * sequenceLength method calculates and returns sequence length seqlength (converts case also)
	 * @param seq
	 * @return seqlength
	 */
	private static int sequenceLength() 
	{
		seq = seq.toUpperCase(); //String converted to uppercase letters 
		int seqlength = seq.length(); // calcs sequence length  
		return seqlength;
	}
	
	/**
	 * appendSeq method add a to start, and z to stop of sequence.
	 * @param seq
	 * @return seqapp
	 */
	private static String sequenceAppend(String seq) 
	{
		String seqapp = "a" + seq + "z";
		return seqapp;
	}
		
	/**
	 * appendedSequenceLength method calculates the length of the appended sequence
	 * @param seqlength
	 * @return aseqlength

	 */
	private static int appendedSequenceLength(int seqlength) 
	{
		int aseqlength = seqlength+2;
		return aseqlength;
	}
	
	/**
	 * sequenceRotateArray method takes an input sequence seqapp and generates a rotated array seqrotarray
	 * rotateSeq in a loop by remove character off end and put it at the beginning, then add to array
	 * @param seqapp
	 * @param aseqlength
	 * @return seqrotarr[];
	 */
	private static String[] sequenceRotateArray(String seqapp, int aseqlength) 
	{
		int i = 0;
		String[] seqrotarr = new String[aseqlength]; //  JAY SAYS no side affects 
		for(i = 0; i < aseqlength; i++)
		{
			seqrotarr[i] = seqapp;
			char end = seqapp.charAt(seqapp.length() - 1); // identify character at end
			String seqtemp = seqapp.substring(0, seqapp.length() - 1); // remove end
			seqapp = end + seqtemp; // append begin
		}
		return (seqrotarr);
	}
	
	/**
	 * sortArray method sorts the rotated seqrotarr array alphabetically and generates sortseqarr[]
	 * @param seqrotarr[] 
	 * @param aseqlength
	 * @return sortseqarr[];
	 */
	private static String[] sortArray(String[] seqrotarr, int aseqlength) 
	{
		String[] sortseqarr = new String[aseqlength];
		System.arraycopy(seqrotarr, 0, sortseqarr, 0, aseqlength );
		for(int j = 0; j < aseqlength; j++)	// out of bounds 11 and this line
		{
			for (int k = j + 1;  k < aseqlength;  k++ )
            {  
				if ( sortseqarr [ j ].compareToIgnoreCase(sortseqarr [ k ] ) > 0 )
                {                                             
					String sorttemp = sortseqarr [ j ];     // ascending sort
					sortseqarr [ j ] = sortseqarr [ k ];    // swapping
					sortseqarr [ k ] = sorttemp;  
                } 
            }
		}
		return (sortseqarr);
	}
	
		/**
		 * SequenceBWT method takes the last column of sortseqarr sorted array and returns a 
		 * concatenated string seqbwt of the last column of the array
		 *  generate the bwtseq string from the last character of each line in the array, the burrows wheeler transform
		 * @param sortseqarr
		 * @param aseqlength
		 * @return seqbwt
		 */
		private static String sequenceBWT(String[] sortseqarr, int aseqlength) 
		{
			String seqbwt = "";
			for(int l = 0; l < aseqlength; l++)
			{
				char seqend =sortseqarr[l].charAt(aseqlength -1);
				seqbwt = seqbwt + seqend;
			}
			return seqbwt;
		}
		
		/**
		 * printResults method Print out results from class. Called from main.
		 * // could write to file or database in another class.
		 * 
		 * @param seqlength
		 * @param aseqlength
		 * @param seqrotarr[i]
		 * @param sortseqarr[j]
		 * @param seq
		 * @param seqbwt
		 */
		private static void printResults(int seqlength, int aseqlength,
				String [] seqrotarr, String [] sortseqarr, String seq2, String seqbwt, String bwtcompress) 
		{
			System.out.println("The sequence length is " + seqlength);
			System.out.println("The appended sequence length is " + aseqlength);
			System.out.println();
			System.out.println("The rotated array is:");
			System.out.println(seqrotarr);
			System.out.println();
			System.out.println("The sorted array is:");
			System.out.println(sortseqarr);
			System.out.println();
			System.out.println("The bwt string is:");
			System.out.println();
			System.out.println("The input sequence is " + seq);
			System.out.println("The Burrows Wheeler transform is " + seqbwt);
			System.out.println();
			System.out.println("A compression of the BWT is " + bwtcompress);
		}
}
		
/**
 * CURRRENT OUTPUT
 * The sequence length is 67
The appended sequence is aAGTCACAGTCATCAGTCTAGTCAGTCAGTCAGTCTACATAGCGATTACGGCATCAGTCAGTCATCGAz
The appended sequence length is 69

The rotated array is:
aAGTCACAGTCATCAGTCTAGTCAGTCAGTCAGTCTACATAGCGATTACGGCATCAGTCAGTCATCGAz
zaAGTCACAGTCATCAGTCTAGTCAGTCAGTCAGTCTACATAGCGATTACGGCATCAGTCAGTCATCGA
AzaAGTCACAGTCATCAGTCTAGTCAGTCAGTCAGTCTACATAGCGATTACGGCATCAGTCAGTCATCG
GAzaAGTCACAGTCATCAGTCTAGTCAGTCAGTCAGTCTACATAGCGATTACGGCATCAGTCAGTCATC
CGAzaAGTCACAGTCATCAGTCTAGTCAGTCAGTCAGTCTACATAGCGATTACGGCATCAGTCAGTCAT
TCGAzaAGTCACAGTCATCAGTCTAGTCAGTCAGTCAGTCTACATAGCGATTACGGCATCAGTCAGTCA
ATCGAzaAGTCACAGTCATCAGTCTAGTCAGTCAGTCAGTCTACATAGCGATTACGGCATCAGTCAGTC
CATCGAzaAGTCACAGTCATCAGTCTAGTCAGTCAGTCAGTCTACATAGCGATTACGGCATCAGTCAGT
TCATCGAzaAGTCACAGTCATCAGTCTAGTCAGTCAGTCAGTCTACATAGCGATTACGGCATCAGTCAG
GTCATCGAzaAGTCACAGTCATCAGTCTAGTCAGTCAGTCAGTCTACATAGCGATTACGGCATCAGTCA
AGTCATCGAzaAGTCACAGTCATCAGTCTAGTCAGTCAGTCAGTCTACATAGCGATTACGGCATCAGTC
CAGTCATCGAzaAGTCACAGTCATCAGTCTAGTCAGTCAGTCAGTCTACATAGCGATTACGGCATCAGT
TCAGTCATCGAzaAGTCACAGTCATCAGTCTAGTCAGTCAGTCAGTCTACATAGCGATTACGGCATCAG
GTCAGTCATCGAzaAGTCACAGTCATCAGTCTAGTCAGTCAGTCAGTCTACATAGCGATTACGGCATCA
AGTCAGTCATCGAzaAGTCACAGTCATCAGTCTAGTCAGTCAGTCAGTCTACATAGCGATTACGGCATC
CAGTCAGTCATCGAzaAGTCACAGTCATCAGTCTAGTCAGTCAGTCAGTCTACATAGCGATTACGGCAT
TCAGTCAGTCATCGAzaAGTCACAGTCATCAGTCTAGTCAGTCAGTCAGTCTACATAGCGATTACGGCA
ATCAGTCAGTCATCGAzaAGTCACAGTCATCAGTCTAGTCAGTCAGTCAGTCTACATAGCGATTACGGC
CATCAGTCAGTCATCGAzaAGTCACAGTCATCAGTCTAGTCAGTCAGTCAGTCTACATAGCGATTACGG
GCATCAGTCAGTCATCGAzaAGTCACAGTCATCAGTCTAGTCAGTCAGTCAGTCTACATAGCGATTACG
GGCATCAGTCAGTCATCGAzaAGTCACAGTCATCAGTCTAGTCAGTCAGTCAGTCTACATAGCGATTAC
CGGCATCAGTCAGTCATCGAzaAGTCACAGTCATCAGTCTAGTCAGTCAGTCAGTCTACATAGCGATTA
ACGGCATCAGTCAGTCATCGAzaAGTCACAGTCATCAGTCTAGTCAGTCAGTCAGTCTACATAGCGATT
TACGGCATCAGTCAGTCATCGAzaAGTCACAGTCATCAGTCTAGTCAGTCAGTCAGTCTACATAGCGAT
TTACGGCATCAGTCAGTCATCGAzaAGTCACAGTCATCAGTCTAGTCAGTCAGTCAGTCTACATAGCGA
ATTACGGCATCAGTCAGTCATCGAzaAGTCACAGTCATCAGTCTAGTCAGTCAGTCAGTCTACATAGCG
GATTACGGCATCAGTCAGTCATCGAzaAGTCACAGTCATCAGTCTAGTCAGTCAGTCAGTCTACATAGC
CGATTACGGCATCAGTCAGTCATCGAzaAGTCACAGTCATCAGTCTAGTCAGTCAGTCAGTCTACATAG
GCGATTACGGCATCAGTCAGTCATCGAzaAGTCACAGTCATCAGTCTAGTCAGTCAGTCAGTCTACATA
AGCGATTACGGCATCAGTCAGTCATCGAzaAGTCACAGTCATCAGTCTAGTCAGTCAGTCAGTCTACAT
TAGCGATTACGGCATCAGTCAGTCATCGAzaAGTCACAGTCATCAGTCTAGTCAGTCAGTCAGTCTACA
ATAGCGATTACGGCATCAGTCAGTCATCGAzaAGTCACAGTCATCAGTCTAGTCAGTCAGTCAGTCTAC
CATAGCGATTACGGCATCAGTCAGTCATCGAzaAGTCACAGTCATCAGTCTAGTCAGTCAGTCAGTCTA
ACATAGCGATTACGGCATCAGTCAGTCATCGAzaAGTCACAGTCATCAGTCTAGTCAGTCAGTCAGTCT
TACATAGCGATTACGGCATCAGTCAGTCATCGAzaAGTCACAGTCATCAGTCTAGTCAGTCAGTCAGTC
CTACATAGCGATTACGGCATCAGTCAGTCATCGAzaAGTCACAGTCATCAGTCTAGTCAGTCAGTCAGT
TCTACATAGCGATTACGGCATCAGTCAGTCATCGAzaAGTCACAGTCATCAGTCTAGTCAGTCAGTCAG
GTCTACATAGCGATTACGGCATCAGTCAGTCATCGAzaAGTCACAGTCATCAGTCTAGTCAGTCAGTCA
AGTCTACATAGCGATTACGGCATCAGTCAGTCATCGAzaAGTCACAGTCATCAGTCTAGTCAGTCAGTC
CAGTCTACATAGCGATTACGGCATCAGTCAGTCATCGAzaAGTCACAGTCATCAGTCTAGTCAGTCAGT
TCAGTCTACATAGCGATTACGGCATCAGTCAGTCATCGAzaAGTCACAGTCATCAGTCTAGTCAGTCAG
GTCAGTCTACATAGCGATTACGGCATCAGTCAGTCATCGAzaAGTCACAGTCATCAGTCTAGTCAGTCA
AGTCAGTCTACATAGCGATTACGGCATCAGTCAGTCATCGAzaAGTCACAGTCATCAGTCTAGTCAGTC
CAGTCAGTCTACATAGCGATTACGGCATCAGTCAGTCATCGAzaAGTCACAGTCATCAGTCTAGTCAGT
TCAGTCAGTCTACATAGCGATTACGGCATCAGTCAGTCATCGAzaAGTCACAGTCATCAGTCTAGTCAG
GTCAGTCAGTCTACATAGCGATTACGGCATCAGTCAGTCATCGAzaAGTCACAGTCATCAGTCTAGTCA
AGTCAGTCAGTCTACATAGCGATTACGGCATCAGTCAGTCATCGAzaAGTCACAGTCATCAGTCTAGTC
CAGTCAGTCAGTCTACATAGCGATTACGGCATCAGTCAGTCATCGAzaAGTCACAGTCATCAGTCTAGT
TCAGTCAGTCAGTCTACATAGCGATTACGGCATCAGTCAGTCATCGAzaAGTCACAGTCATCAGTCTAG
GTCAGTCAGTCAGTCTACATAGCGATTACGGCATCAGTCAGTCATCGAzaAGTCACAGTCATCAGTCTA
AGTCAGTCAGTCAGTCTACATAGCGATTACGGCATCAGTCAGTCATCGAzaAGTCACAGTCATCAGTCT
TAGTCAGTCAGTCAGTCTACATAGCGATTACGGCATCAGTCAGTCATCGAzaAGTCACAGTCATCAGTC
CTAGTCAGTCAGTCAGTCTACATAGCGATTACGGCATCAGTCAGTCATCGAzaAGTCACAGTCATCAGT
TCTAGTCAGTCAGTCAGTCTACATAGCGATTACGGCATCAGTCAGTCATCGAzaAGTCACAGTCATCAG
GTCTAGTCAGTCAGTCAGTCTACATAGCGATTACGGCATCAGTCAGTCATCGAzaAGTCACAGTCATCA
AGTCTAGTCAGTCAGTCAGTCTACATAGCGATTACGGCATCAGTCAGTCATCGAzaAGTCACAGTCATC
CAGTCTAGTCAGTCAGTCAGTCTACATAGCGATTACGGCATCAGTCAGTCATCGAzaAGTCACAGTCAT
TCAGTCTAGTCAGTCAGTCAGTCTACATAGCGATTACGGCATCAGTCAGTCATCGAzaAGTCACAGTCA
ATCAGTCTAGTCAGTCAGTCAGTCTACATAGCGATTACGGCATCAGTCAGTCATCGAzaAGTCACAGTC
CATCAGTCTAGTCAGTCAGTCAGTCTACATAGCGATTACGGCATCAGTCAGTCATCGAzaAGTCACAGT
TCATCAGTCTAGTCAGTCAGTCAGTCTACATAGCGATTACGGCATCAGTCAGTCATCGAzaAGTCACAG
GTCATCAGTCTAGTCAGTCAGTCAGTCTACATAGCGATTACGGCATCAGTCAGTCATCGAzaAGTCACA
AGTCATCAGTCTAGTCAGTCAGTCAGTCTACATAGCGATTACGGCATCAGTCAGTCATCGAzaAGTCAC
CAGTCATCAGTCTAGTCAGTCAGTCAGTCTACATAGCGATTACGGCATCAGTCAGTCATCGAzaAGTCA
ACAGTCATCAGTCTAGTCAGTCAGTCAGTCTACATAGCGATTACGGCATCAGTCAGTCATCGAzaAGTC
CACAGTCATCAGTCTAGTCAGTCAGTCAGTCTACATAGCGATTACGGCATCAGTCAGTCATCGAzaAGT
TCACAGTCATCAGTCTAGTCAGTCAGTCAGTCTACATAGCGATTACGGCATCAGTCAGTCATCGAzaAG
GTCACAGTCATCAGTCTAGTCAGTCAGTCAGTCTACATAGCGATTACGGCATCAGTCAGTCATCGAzaA
AGTCACAGTCATCAGTCTAGTCAGTCAGTCAGTCTACATAGCGATTACGGCATCAGTCAGTCATCGAza

The sorted array is:
aAGTCACAGTCATCAGTCTAGTCAGTCAGTCAGTCTACATAGCGATTACGGCATCAGTCAGTCATCGAz
ACAGTCATCAGTCTAGTCAGTCAGTCAGTCTACATAGCGATTACGGCATCAGTCAGTCATCGAzaAGTC
ACATAGCGATTACGGCATCAGTCAGTCATCGAzaAGTCACAGTCATCAGTCTAGTCAGTCAGTCAGTCT
ACGGCATCAGTCAGTCATCGAzaAGTCACAGTCATCAGTCTAGTCAGTCAGTCAGTCTACATAGCGATT
AGCGATTACGGCATCAGTCAGTCATCGAzaAGTCACAGTCATCAGTCTAGTCAGTCAGTCAGTCTACAT
AGTCACAGTCATCAGTCTAGTCAGTCAGTCAGTCTACATAGCGATTACGGCATCAGTCAGTCATCGAza
AGTCAGTCAGTCAGTCTACATAGCGATTACGGCATCAGTCAGTCATCGAzaAGTCACAGTCATCAGTCT
AGTCAGTCAGTCTACATAGCGATTACGGCATCAGTCAGTCATCGAzaAGTCACAGTCATCAGTCTAGTC
AGTCAGTCATCGAzaAGTCACAGTCATCAGTCTAGTCAGTCAGTCAGTCTACATAGCGATTACGGCATC
AGTCAGTCTACATAGCGATTACGGCATCAGTCAGTCATCGAzaAGTCACAGTCATCAGTCTAGTCAGTC
AGTCATCAGTCTAGTCAGTCAGTCAGTCTACATAGCGATTACGGCATCAGTCAGTCATCGAzaAGTCAC
AGTCATCGAzaAGTCACAGTCATCAGTCTAGTCAGTCAGTCAGTCTACATAGCGATTACGGCATCAGTC
AGTCTACATAGCGATTACGGCATCAGTCAGTCATCGAzaAGTCACAGTCATCAGTCTAGTCAGTCAGTC
AGTCTAGTCAGTCAGTCAGTCTACATAGCGATTACGGCATCAGTCAGTCATCGAzaAGTCACAGTCATC
ATAGCGATTACGGCATCAGTCAGTCATCGAzaAGTCACAGTCATCAGTCTAGTCAGTCAGTCAGTCTAC
ATCAGTCAGTCATCGAzaAGTCACAGTCATCAGTCTAGTCAGTCAGTCAGTCTACATAGCGATTACGGC
ATCAGTCTAGTCAGTCAGTCAGTCTACATAGCGATTACGGCATCAGTCAGTCATCGAzaAGTCACAGTC
ATCGAzaAGTCACAGTCATCAGTCTAGTCAGTCAGTCAGTCTACATAGCGATTACGGCATCAGTCAGTC
ATTACGGCATCAGTCAGTCATCGAzaAGTCACAGTCATCAGTCTAGTCAGTCAGTCAGTCTACATAGCG
AzaAGTCACAGTCATCAGTCTAGTCAGTCAGTCAGTCTACATAGCGATTACGGCATCAGTCAGTCATCG
CACAGTCATCAGTCTAGTCAGTCAGTCAGTCTACATAGCGATTACGGCATCAGTCAGTCATCGAzaAGT
CAGTCAGTCAGTCTACATAGCGATTACGGCATCAGTCAGTCATCGAzaAGTCACAGTCATCAGTCTAGT
CAGTCAGTCATCGAzaAGTCACAGTCATCAGTCTAGTCAGTCAGTCAGTCTACATAGCGATTACGGCAT
CAGTCAGTCTACATAGCGATTACGGCATCAGTCAGTCATCGAzaAGTCACAGTCATCAGTCTAGTCAGT
CAGTCATCAGTCTAGTCAGTCAGTCAGTCTACATAGCGATTACGGCATCAGTCAGTCATCGAzaAGTCA
CAGTCATCGAzaAGTCACAGTCATCAGTCTAGTCAGTCAGTCAGTCTACATAGCGATTACGGCATCAGT
CAGTCTACATAGCGATTACGGCATCAGTCAGTCATCGAzaAGTCACAGTCATCAGTCTAGTCAGTCAGT
CAGTCTAGTCAGTCAGTCAGTCTACATAGCGATTACGGCATCAGTCAGTCATCGAzaAGTCACAGTCAT
CATAGCGATTACGGCATCAGTCAGTCATCGAzaAGTCACAGTCATCAGTCTAGTCAGTCAGTCAGTCTA
CATCAGTCAGTCATCGAzaAGTCACAGTCATCAGTCTAGTCAGTCAGTCAGTCTACATAGCGATTACGG
CATCAGTCTAGTCAGTCAGTCAGTCTACATAGCGATTACGGCATCAGTCAGTCATCGAzaAGTCACAGT
CATCGAzaAGTCACAGTCATCAGTCTAGTCAGTCAGTCAGTCTACATAGCGATTACGGCATCAGTCAGT
CGATTACGGCATCAGTCAGTCATCGAzaAGTCACAGTCATCAGTCTAGTCAGTCAGTCAGTCTACATAG
CGAzaAGTCACAGTCATCAGTCTAGTCAGTCAGTCAGTCTACATAGCGATTACGGCATCAGTCAGTCAT
CGGCATCAGTCAGTCATCGAzaAGTCACAGTCATCAGTCTAGTCAGTCAGTCAGTCTACATAGCGATTA
CTACATAGCGATTACGGCATCAGTCAGTCATCGAzaAGTCACAGTCATCAGTCTAGTCAGTCAGTCAGT
CTAGTCAGTCAGTCAGTCTACATAGCGATTACGGCATCAGTCAGTCATCGAzaAGTCACAGTCATCAGT
GATTACGGCATCAGTCAGTCATCGAzaAGTCACAGTCATCAGTCTAGTCAGTCAGTCAGTCTACATAGC
GAzaAGTCACAGTCATCAGTCTAGTCAGTCAGTCAGTCTACATAGCGATTACGGCATCAGTCAGTCATC
GCATCAGTCAGTCATCGAzaAGTCACAGTCATCAGTCTAGTCAGTCAGTCAGTCTACATAGCGATTACG
GCGATTACGGCATCAGTCAGTCATCGAzaAGTCACAGTCATCAGTCTAGTCAGTCAGTCAGTCTACATA
GGCATCAGTCAGTCATCGAzaAGTCACAGTCATCAGTCTAGTCAGTCAGTCAGTCTACATAGCGATTAC
GTCACAGTCATCAGTCTAGTCAGTCAGTCAGTCTACATAGCGATTACGGCATCAGTCAGTCATCGAzaA
GTCAGTCAGTCAGTCTACATAGCGATTACGGCATCAGTCAGTCATCGAzaAGTCACAGTCATCAGTCTA
GTCAGTCAGTCTACATAGCGATTACGGCATCAGTCAGTCATCGAzaAGTCACAGTCATCAGTCTAGTCA
GTCAGTCATCGAzaAGTCACAGTCATCAGTCTAGTCAGTCAGTCAGTCTACATAGCGATTACGGCATCA
GTCAGTCTACATAGCGATTACGGCATCAGTCAGTCATCGAzaAGTCACAGTCATCAGTCTAGTCAGTCA
GTCATCAGTCTAGTCAGTCAGTCAGTCTACATAGCGATTACGGCATCAGTCAGTCATCGAzaAGTCACA
GTCATCGAzaAGTCACAGTCATCAGTCTAGTCAGTCAGTCAGTCTACATAGCGATTACGGCATCAGTCA
GTCTACATAGCGATTACGGCATCAGTCAGTCATCGAzaAGTCACAGTCATCAGTCTAGTCAGTCAGTCA
GTCTAGTCAGTCAGTCAGTCTACATAGCGATTACGGCATCAGTCAGTCATCGAzaAGTCACAGTCATCA
TACATAGCGATTACGGCATCAGTCAGTCATCGAzaAGTCACAGTCATCAGTCTAGTCAGTCAGTCAGTC
TACGGCATCAGTCAGTCATCGAzaAGTCACAGTCATCAGTCTAGTCAGTCAGTCAGTCTACATAGCGAT
TAGCGATTACGGCATCAGTCAGTCATCGAzaAGTCACAGTCATCAGTCTAGTCAGTCAGTCAGTCTACA
TAGTCAGTCAGTCAGTCTACATAGCGATTACGGCATCAGTCAGTCATCGAzaAGTCACAGTCATCAGTC
TCACAGTCATCAGTCTAGTCAGTCAGTCAGTCTACATAGCGATTACGGCATCAGTCAGTCATCGAzaAG
TCAGTCAGTCAGTCTACATAGCGATTACGGCATCAGTCAGTCATCGAzaAGTCACAGTCATCAGTCTAG
TCAGTCAGTCATCGAzaAGTCACAGTCATCAGTCTAGTCAGTCAGTCAGTCTACATAGCGATTACGGCA
TCAGTCAGTCTACATAGCGATTACGGCATCAGTCAGTCATCGAzaAGTCACAGTCATCAGTCTAGTCAG
TCAGTCATCGAzaAGTCACAGTCATCAGTCTAGTCAGTCAGTCAGTCTACATAGCGATTACGGCATCAG
TCAGTCTACATAGCGATTACGGCATCAGTCAGTCATCGAzaAGTCACAGTCATCAGTCTAGTCAGTCAG
TCAGTCTAGTCAGTCAGTCAGTCTACATAGCGATTACGGCATCAGTCAGTCATCGAzaAGTCACAGTCA
TCATCAGTCTAGTCAGTCAGTCAGTCTACATAGCGATTACGGCATCAGTCAGTCATCGAzaAGTCACAG
TCATCGAzaAGTCACAGTCATCAGTCTAGTCAGTCAGTCAGTCTACATAGCGATTACGGCATCAGTCAG
TCGAzaAGTCACAGTCATCAGTCTAGTCAGTCAGTCAGTCTACATAGCGATTACGGCATCAGTCAGTCA
TCTACATAGCGATTACGGCATCAGTCAGTCATCGAzaAGTCACAGTCATCAGTCTAGTCAGTCAGTCAG
TCTAGTCAGTCAGTCAGTCTACATAGCGATTACGGCATCAGTCAGTCATCGAzaAGTCACAGTCATCAG
TTACGGCATCAGTCAGTCATCGAzaAGTCACAGTCATCAGTCTAGTCAGTCAGTCAGTCTACATAGCGA
zaAGTCACAGTCATCAGTCTAGTCAGTCAGTCAGTCTACATAGCGATTACGGCATCAGTCAGTCATCGA

The bwt string is:

The input sequence is AGTCACAGTCATCAGTCTAGTCAGTCAGTCAGTCTACATAGCGATTACGGCATCAGTCAGTCATCGA
The Burrows Wheeler transform is zCTTTaTCCCCCCCCCCCGGTTTTATTTAGTTGTATTCCGACAAAAAAAAACTACGGAGGGAGGAGGAA

*return value zGTCGaACT3  //  WHY am i getting this.
The sequence length is 9
The appended sequence length is 11

The rotated array is:
[Ljava.lang.String;@5e8fce95 //  WHY am i getting this.

The sorted array is:
[Ljava.lang.String;@3343c8b3

The bwt string is:

The input sequence is GGATCCTTT
The Burrows Wheeler transform is zGTCGaACTTT

A compression of the BWT is zGTCGaACT3
*
*
*/


/**
* next steps 
* 1. read about java collections lists arraylists, vectors, hashmaps,
*/
