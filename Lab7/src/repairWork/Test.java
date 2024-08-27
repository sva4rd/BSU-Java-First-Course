package repairWork;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.io.RandomAccessFile;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;



public class Test {

	static final String filename    = "Bills.dat";
	static final String filenameBak = "Bills.~dat";
	static final String idxname     = "Bills.idx";
	static final String idxnameBak  = "Bills.~idx";

	// input file encoding:
	private static String encoding = "utf8";
	private static PrintStream billOut = System.out;

	public static void main(String[] args) {
		try {
			if ( args.length >= 1 ) {				
				if ( args[0].equals("-?") || args[0].equals("-h")) {
					System.out.println( 
							"Syntax:\n" +
									"\t-a  [file [encoding]] - append data (*)\n" +
									"\t-az [file [encoding]] - append data (*), compress every record\n" +
									"\t-d                    - clear all data\n" +
									"\t-dk  {h|r|n|d} key    - clear data by key\n" +
									"\t-p                    - print data unsorted\n" +
									"\t-ps  {h|r|n|d}        - print data sorted by house/room/name/date\n" +
									"\t-psr {h|r|n|d}        - print data reverse sorted by house/room/name/date\n" +
									"\t-f   {h|r|n|d} key    - find record by key\n"+
									"\t-fr  {h|r|n|d} key    - find records > key\n"+
									"\t-fl  {h|r|n|d} key    - find records < key\n"+
									"\t-?, -h                - command line syntax\n"+
									"   (*) if not specified, encoding for file is utf8\n"
							);
				}

				else if ( args[0].equals( "-a" )) {
					// Append file with new object from System.in
					// -a [file [encoding]]
					appendFile( args, false );
				}
				else if ( args[0].equals( "-az" )) {
					// Append file with compressed new object from System.in
					// -az [file [encoding]]
					appendFile( args, true );
				}
				else if ( args[0].equals( "-d" )) {
					// delete files
					if ( args.length != 1 ) {
						System.err.println("Invalid number of arguments");
						System.exit(1);;
					}
					deleteFile();
				}
				else if ( args[0].equals( "-dk" )) {
					// Delete records by key
					if ( deleteFile( args )== false ) {
						System.exit(1);						
					}
				}
				else if ( args[0].equals( "-p" )) {
					// Prints data file
					printFile();
				}
				else if ( args[0].equals( "-ps" )) {
					// Prints data file sorted by key
					if ( printFile( args, false )== false ) {
						System.exit(1);
					}
				}
				else if ( args[0].equals( "-psr" )) {
					// Prints data file reverse-sorted by key
					if ( printFile( args, true )== false ) {
						System.exit(1);
					}
				}

				else if ( args[0].equals( "-f" )) {
					// Find record(s) by key
					if ( findByKey( args )== false ) {
						System.exit(1);
					}
				}
				else if ( args[0].equals( "-fr" )) {
					// Find record(s) by key large then key 
					Boolean bl = false;
					if (args[1].equals("h") || args[1].equals("r"))
						bl = findByKey( args, new NumCompReverse() );
					else if (args[1].equals("n"))
						bl = findByKey( args, new NameCompReverse() );
					else
						bl = findByKey( args, new DateCompReverse() );
					if ( bl == false ) {
						System.exit(1);
					}
				}
				else if ( args[0].equals( "-fl" )) {
					// Find record(s) by key less then key
					Boolean bl = false;
					if (args[1].equals("h") || args[1].equals("r"))
						bl = findByKey( args, new NumComp() );
					else if (args[1].equals("n"))
						bl = findByKey( args, new NameComp() );
					else
						bl = findByKey( args, new DateComp() );
					if ( bl == false ) {
						System.exit(1);
					}
				}
				else {
					System.err.println( "Option is not realised: " + args[0] );
					System.exit(1);
				}
			}
			else {
				System.err.println( "Nothing to do! Enter -? for options" );
			}
		}
		catch ( Exception e ) {
			System.err.println( "Run/time error: " + e );
			System.exit(1);
		}
		System.exit(0);
	}




	//reading
	private static void appendFile( String[] args, Boolean zipped ) 
			throws FileNotFoundException, IOException, ClassNotFoundException,
			DateNotUniqueException {
		if ( args.length >= 2 ) {
			FileInputStream stdin = new FileInputStream( args[1] );
			System.setIn( stdin );
			if (args.length == 3) {
				encoding = args[2];
			}
			// hide output:
			billOut = new PrintStream("nul");
		}

		Scanner fin = new Scanner( System.in, encoding );
		billOut.println( "Enter bill data: " );
		try ( Index idx = Index.load( idxname ); 
				RandomAccessFile raf = new RandomAccessFile( filename, "rw" )) {
			for(;;) {
				Bill bill = readBill( fin );
				if ( bill == null )
					break;
				idx.test( bill );
				long pos = Buffer.writeObject( raf, bill, zipped );
				idx.put( bill, pos );
			}
		}
	}


	static Bill readBill( Scanner fin ) throws IOException {	
		return Bill.nextRead( fin, billOut ) ? Bill.read( fin, billOut ) : null;
	}






	//deleting
	private static void deleteFile() {
		deleteBackup();
		new File( filename ).delete();
		new File( idxname ).delete();		
	}

	private static FieldIndexes indexByArg( String arg, Index idx ) {
		FieldIndexes pidx = null;
		if ( arg.equals("h")) {
			pidx = idx.houseNumbers;
		} 
		else if ( arg.equals("r")) {
			pidx = idx.roomNumbers;
		} 
		else if ( arg.equals("n")) {
			pidx = idx.names;
		} 
		else if ( arg.equals("d")) {
			pidx = idx.dates;
		} 
		else {
			System.err.println( "Invalid index specified: " + arg );
		}
		return pidx;
	}

	static boolean deleteFile( String[] args ) 
			throws ClassNotFoundException, IOException, DateNotUniqueException {
		//-dk  {h|r|n|d} key    - clear data by key
		if ( args.length != 3 ) {
			System.err.println( "Invalid number of arguments" );
			return false;
		}
		Long[] poss = null;
		try ( Index idx = Index.load( idxname )) {
			FieldIndexes pidx = indexByArg( args[1], idx );
			if ( pidx == null ) {
				return false;
			}
			if ( pidx.contains(args[2])== false ) {
				System.err.println( "Key not found: " + args[2] );
				return false;				
			}
			poss = pidx.get(args[2]);
		}
		backup();
		Arrays.sort( poss );
		try ( Index idx = Index.load( idxname ); 
				RandomAccessFile fileBak= new RandomAccessFile(filenameBak, "rw");
				RandomAccessFile file = new RandomAccessFile( filename, "rw")) {
			boolean[] wasZipped = new boolean[] {false};
			long pos;
			while (( pos = fileBak.getFilePointer()) < fileBak.length() ) {
				Bill bill = (Bill) 
						Buffer.readObject( fileBak, pos, wasZipped );
				if ( Arrays.binarySearch(poss, pos) < 0 ) { // if not found in deleted
					long ptr = Buffer.writeObject( file, bill, wasZipped[0] );
					idx.put( bill, ptr );
				}
			}
		}
		deleteBackup();
		return true;
	}

	private static void backup() {
		deleteBackup();
		new File( filename ).renameTo( new File( filenameBak ));
		new File( idxname ).renameTo( new File( idxnameBak ));
	}

	private static void deleteBackup() {
		new File( filenameBak ).delete();
		new File( idxnameBak ).delete();				
	}






	//printing
	static void printFile() 
			throws FileNotFoundException, IOException, ClassNotFoundException {
		long pos;
		int rec = 0;
		try ( RandomAccessFile raf = new RandomAccessFile( filename, "rw" )) {
			if (!(raf.getFilePointer() < raf.length())) {
				System.out.print("NO DATA\n");
				return;
			}
			while (( pos = raf.getFilePointer()) < raf.length() ) {
				System.out.print( "#" + (++rec ));
				printRecord( raf, pos );
			}
			System.out.flush();
		}		
	}

	private static void printRecord( RandomAccessFile raf, long pos ) 
			throws ClassNotFoundException, IOException {
		boolean[] wasZipped = new boolean[] {false};
		Bill bill = (Bill) Buffer.readObject( raf, pos, wasZipped );
		if ( wasZipped[0] == true ) {
			System.out.print( " compressed" );
		}
		System.out.println( " record at position "+ pos + ": \n" + bill );
		System.out.println();
	}

	static boolean printFile( String[] args, boolean reverse ) 
			throws ClassNotFoundException, IOException {
		if ( args.length != 2 ) {
			System.err.println( "Invalid number of arguments" );
			return false;
		}
		try ( Index idx = Index.load( idxname ); 
				RandomAccessFile raf = new RandomAccessFile( filename, "rw" )) {
			FieldIndexes pidx = indexByArg( args[1], idx );
			if ( pidx == null ) {
				return false;
			}
			String[] keys;
			if (args[1].equals("h") || args[1].equals("r"))
				keys = pidx.getKeys( reverse ? new NumCompReverse() : new NumComp() );
			else if (args[1].equals("n"))
				keys = pidx.getKeys( reverse ? new NameCompReverse() : new NameComp() );
			else
				keys = pidx.getKeys( reverse ? new DateCompReverse() : new DateComp() );
			if (keys.length == 0) {
				System.out.print("NO DATA\n");
			}
			for ( String key : keys ) {
				printRecord( raf, key, pidx );
			}
		}
		return true;
	}

	private static void printRecord( RandomAccessFile raf, String key, FieldIndexes pidx )
			throws ClassNotFoundException, IOException {
		Long[] poss = pidx.get( key );
		for ( long pos : poss ) {
			System.out.print( "*** Key: " +  key + " points to" );
			printRecord( raf, pos );
		}		
	}




	//find by key
	static boolean findByKey( String[] args ) throws ClassNotFoundException, IOException {
		if ( args.length != 3 ) {
			System.err.println( "Invalid number of arguments" );
			return false;
		}
		try ( Index idx = Index.load( idxname ); 
				RandomAccessFile raf = new RandomAccessFile( filename, "rw" )) {
			FieldIndexes pidx = indexByArg( args[1], idx );
			if ( pidx.contains(args[2])== false ) {
				System.err.println( "Key not found: " + args[2] );
				return false;				
			}
			printRecord( raf, args[2], pidx );
		}
		return true;	
	}

	static boolean findByKey( String[] args, Comparator<String> comp ) 
			throws ClassNotFoundException, IOException {
		if ( args.length != 3 ) {
			System.err.println( "Invalid number of arguments" );
			return false;
		}
		try ( Index idx = Index.load( idxname ); 
				RandomAccessFile raf = new RandomAccessFile( filename, "rw" )) {
			FieldIndexes pidx = indexByArg( args[1], idx );
			if ( pidx.contains(args[2])== false ) {
				System.err.println( "Key not found: " + args[2] );
				return false;				
			}
			String[] keys = pidx.getKeys( comp );
			for ( int i = 0; i < keys.length; i++ ) {
				String key = keys[i];
				if ( key.equals( args[2] )) {
					break;
				}
				printRecord( raf, key, pidx );
			}
		}
		return true;
	}
}
