package uk.ac.ebi.ontocat.examples;

import jargs.gnu.CmdLineParser;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.io.Writer;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.Stack;
import java.util.UUID;

import org.apache.log4j.Logger;

import uk.ac.ebi.ontocat.OntologyService;
import uk.ac.ebi.ontocat.OntologyServiceException;
import uk.ac.ebi.ontocat.OntologyTerm;
import uk.ac.ebi.ontocat.file.ReasonedFileOntologyService;

/**
 * Example 15
 * 
 * Shows how to compute all relationships in a multi process fashion. Note that
 * OWL API stalls when accessed by multiple threads, so process level separation
 * is required. This approach effectively implements a CyclicBarrier in a disk
 * store to sync up process while they wait for new data to become available. <br>
 * A typical scenario involves running the script with a start node set to
 * initalise the queue. Then running multiple process (note that number of
 * actually running processes has to be set, so that everything finishes once
 * all the process hit the waiting queue, i.e. no more new data is being
 * generated). And finally running generate to collate results.
 */
public class Example15 {
	private static OntologyService os;
	private static URI uOntology;
	private static Stack<OntologyTerm> processQueue;
	private static File qFile;
	private static int maxTasks;
	private static File bFile;
	// in lieu of an actual process id
	private static UUID myPID = UUID.randomUUID();
	private static File dFile;
	private static final Logger log = Logger.getLogger(Example15.class);

	/**
	 * The main method.
	 * 
	 * @param args
	 *            the arguments
	 * @throws Exception
	 *             the exception
	 */
	public static void main(String[] args) throws Exception {

		CmdLineParser parser = new CmdLineParser();
		CmdLineParser.Option oUri = parser.addStringOption('o', "ontology");
		CmdLineParser.Option oStartNode = parser.addStringOption('s',
		"startnode");
		CmdLineParser.Option oThreads = parser.addIntegerOption('t', "threads");
		CmdLineParser.Option oGenerate = parser.addBooleanOption('g',
		"generate");

		String startNode = null;
		Boolean bGenerate = false;
		try {
			parser.parse(args);
			bGenerate = (Boolean) parser.getOptionValue(oGenerate,
					Boolean.FALSE);
			uOntology = new URI((String) parser.getOptionValue(oUri));
			startNode = (String) parser.getOptionValue(oStartNode);
			maxTasks = (Integer) parser.getOptionValue(oThreads);
		} catch (CmdLineParser.OptionException e) {
		} catch (NullPointerException e) {
		}

		// set up
		if (!bGenerate) {
			do {
				try {
					os = new ReasonedFileOntologyService(uOntology, "efo");
				} catch (Exception e) {
					// this is bound to fail if >10 processes will try
					// to access the same resource, catch the exception
					// and retry
					log.debug("Service creation failed. Retrying. " + e
							.getMessage());
					Thread.sleep(500);
				}
			} while (os == null);
		}
		System.out.println(getPID());

		// Get a file channel for the file
		qFile = new File("queue.txt");
		bFile = new File("barrier.txt");
		dFile = new File("dot.txt");

		// initialise phase
		if (startNode != null) {

			OntologyTerm ot = os.getTerm(startNode);
			processQueue = new Stack<OntologyTerm>();
			processQueue.push(ot);
			serializeQueue();
			resetBarrier();
		} else if (bGenerate)
		{
			FileInputStream fstream = new FileInputStream(dFile);
			// Get the object of DataInputStream
			BufferedReader br = new BufferedReader(new InputStreamReader(
					fstream));
			String strLine;
			String dot = "";
			// Read File Line By Line
			while ((strLine = br.readLine()) != null) {
				dot += strLine;
			}
			fstream.close();

			System.out.println(dot);
			downloadFile(dot, "dot");
			downloadFile(dot, "neato");
			downloadFile(dot, "twopi");
		}
		else{
			processNodes();
		}

	}

	public static void downloadFile(String dot, String format)
	throws IOException {
		String postData = "cht=gv:" + format
		+ "&chl=digraph{"
		+ dot
		+ "}";

		URL url = new URL("https://chart.googleapis.com/chart");
		URLConnection conn = url.openConnection();
		conn.setDoOutput(true);
		OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
		wr.write(postData);
		wr.flush();
		wr.close();

		java.io.BufferedInputStream in = new java.io.BufferedInputStream(
				conn.getInputStream());
		java.io.FileOutputStream fos = new java.io.FileOutputStream(
				"tree_" + format
				+ ".png");
		java.io.BufferedOutputStream bout = new BufferedOutputStream(fos, 1024);
		byte[] data = new byte[1024];
		int x = 0;
		while ((x = in.read(data, 0, 1024)) >= 0) {
			bout.write(data, 0, x);
		}
		bout.close();
		in.close();
	}

	private static void serializeQueue() throws IOException {
		RandomAccessFile ras = new RandomAccessFile(qFile, "rw");
		ras.seek(0);
		FileChannel channel = ras.getChannel();
		FileLock lock = channel.lock();

		ObjectOutputStream oos = new ObjectOutputStream(
				Channels.newOutputStream(channel));
		oos.writeObject(processQueue);
		lock.release();
		ras.close();
		log.info("Serialized the queue successfully " + processQueue.size());
	}

	@SuppressWarnings("unchecked")
	private static OntologyTerm getNextAvailableTerm() throws IOException,
	ClassNotFoundException {
		// deserialize queue
		OntologyTerm result = null;

		// lock
		RandomAccessFile ras = new RandomAccessFile(qFile, "rw");
		ras.seek(0);
		FileChannel channel = ras.getChannel();
		FileLock lock = channel.lock();

		// deserialise
		ObjectInputStream ois = new ObjectInputStream(
				Channels.newInputStream(channel));
		processQueue = (Stack<OntologyTerm>) ois.readObject();
		ras.seek(0);
		// pop
		if (!processQueue.isEmpty()) {
			result = processQueue.pop();
		}

		// serialise back
		// might want to do an else
		ObjectOutputStream oos = new ObjectOutputStream(
				Channels.newOutputStream(channel));
		oos.writeObject(processQueue);
		oos.flush();
		// log.info("Serialized the queue successfully " + processQueue.size());

		lock.release();
		ras.close();
		return result;
	}

	private static void writeDOT(String s) throws IOException {
		// lock
		RandomAccessFile ras = new RandomAccessFile(dFile, "rw");
		FileChannel channel = ras.getChannel();
		FileLock lock = channel.lock();
		ras.seek(ras.length());

		// deserialise
		Writer writer = Channels.newWriter(channel, "UTF-8");
		PrintWriter pw = new PrintWriter(writer, true);

		pw.println(s);

		lock.release();
		ras.close();
	}

	private static void addTermsToQueue(Set<OntologyTerm> terms)
	throws IOException, ClassNotFoundException, SecurityException
	{
		if (terms.isEmpty()) {
			return;
		}
		// deserialize queue
		OntologyTerm result = null;

		// lock
		RandomAccessFile ras = new RandomAccessFile(qFile, "rw");
		ras.seek(0);
		FileChannel channel = ras.getChannel();
		FileLock lock = channel.lock();

		// deserialise
		ObjectInputStream ois = new ObjectInputStream(
				Channels.newInputStream(channel));
		processQueue = (Stack<OntologyTerm>) ois.readObject();
		// log.info("Deserialized the queue successfully " +
		// processQueue.size());

		ras.seek(0);
		// add new
		processQueue.addAll(terms);

		// serialise back
		// might want to do an else
		ObjectOutputStream oos = new ObjectOutputStream(
				Channels.newOutputStream(channel));
		oos.writeObject(processQueue);
		oos.flush();
		// log.info("Serialized the queue successfully " + processQueue.size());

		lock.release();
		ras.close();
	}

	static UUID getPID() {
		return myPID;
	}

	private static int getNumberWaiting() throws IOException,
	ClassNotFoundException {
		// deserialize queue
		Set<UUID> result = null;

		// lock
		RandomAccessFile ras = new RandomAccessFile(bFile, "rw");
		ras.seek(0);
		FileChannel channel = ras.getChannel();
		FileLock lock = channel.lock();

		// deserialise
		ObjectInputStream ois = new ObjectInputStream(
				Channels.newInputStream(channel));
		result = (Set<UUID>) ois.readObject();

		lock.release();
		ras.close();
		return result.size();
	}

	private static boolean isAtBarrier(UUID uuid) throws IOException,
	ClassNotFoundException {
		// deserialize queue
		Set<UUID> result = null;

		// lock
		RandomAccessFile ras = new RandomAccessFile(bFile, "rw");
		ras.seek(0);
		FileChannel channel = ras.getChannel();
		FileLock lock = channel.lock();

		// deserialise
		ObjectInputStream ois = new ObjectInputStream(
				Channels.newInputStream(channel));
		result = (Set<UUID>) ois.readObject();

		lock.release();
		ras.close();

		return result.contains(uuid);
	}

	private static void addToBarrier(UUID uuid) throws IOException,
	ClassNotFoundException {
		// deserialize queue
		// lock
		RandomAccessFile ras = new RandomAccessFile(bFile, "rw");
		ras.seek(0);
		FileChannel channel = ras.getChannel();
		FileLock lock = channel.lock();

		// deserialise
		ObjectInputStream ois = new ObjectInputStream(
				Channels.newInputStream(channel));
		Set<UUID> set = (Set<UUID>) ois.readObject();
		// log.info("Deserialized the queue successfully " +
		// processQueue.size());

		ras.seek(0);
		// add new
		set.add(uuid);

		// serialise back
		// might want to do an else
		ObjectOutputStream oos = new ObjectOutputStream(
				Channels.newOutputStream(channel));
		oos.writeObject(set);
		oos.flush();
		// log.info("Serialized the queue successfully " + processQueue.size());

		lock.release();
		ras.close();
	}

	private static void resetBarrier() throws IOException,
	ClassNotFoundException {

		// lock
		RandomAccessFile ras = new RandomAccessFile(bFile, "rw");
		ras.seek(0);
		FileChannel channel = ras.getChannel();
		FileLock lock = channel.lock();

		ObjectOutputStream oos = new ObjectOutputStream(
				Channels.newOutputStream(channel));
		oos.writeObject(new HashSet<UUID>());
		oos.flush();
		// log.info("Serialized the queue successfully " + processQueue.size());

		lock.release();
		ras.close();
	}

	private static Integer getQueueSize() throws IOException,
	ClassNotFoundException {
		// deserialize queue
		Integer result = null;

		// lock
		RandomAccessFile ras = new RandomAccessFile(qFile, "rw");
		ras.seek(0);
		FileChannel channel = ras.getChannel();
		FileLock lock = channel.lock();

		// deserialise
		ObjectInputStream ois = new ObjectInputStream(
				Channels.newInputStream(channel));
		processQueue = (Stack<OntologyTerm>) ois.readObject();
		// log.info("Deserialized the queue successfully " +
		// processQueue.size());

		lock.release();
		ras.close();
		return processQueue.size();

	}

	private static void processNodes() throws IOException,
	ClassNotFoundException, InterruptedException {

		do {
			OntologyTerm currentNode;
			while (getNumberWaiting() == 0 && (currentNode = getNextAvailableTerm()) != null) {
				log.debug(" processing " + currentNode.getLabel()
						+ " (queue size: "
						+ processQueue.size()
						+ ")");

				Set<OntologyTerm> isaChildren = null;
				Map<String, Set<OntologyTerm>> mOtherChildren = null;

				try {
					isaChildren = new HashSet<OntologyTerm>(
							os.getChildren(currentNode));

					mOtherChildren = os.getRelations(
							currentNode.getOntologyAccession(),
							currentNode.getAccession());
				} catch (OntologyServiceException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				// remove isa children inferred by the reasoner
				for (Set<OntologyTerm> sOtherChildren : mOtherChildren.values()) {
					isaChildren.removeAll(sOtherChildren);
				}

				// print isa children
				for (OntologyTerm term : isaChildren) {
					String dotPart = "\"" + term.getLabel()
					+ "\"->\""
					+ currentNode.getLabel()
					+ "\""
					+ "[label=\"isa\",len=\"2.5\"];";

					// dot += dotPart;
					System.out.println(dotPart);
					writeDOT(dotPart);
				}
				addTermsToQueue(isaChildren);

				// print other children
				for (Entry<String, Set<OntologyTerm>> e : mOtherChildren
						.entrySet()) {
					for (OntologyTerm term : e.getValue()) {
						String dotPart = "\"" + currentNode.getLabel()
						+ "\"->\""
						+ term.getLabel()
						+ "\""
						+ "[label=\""
						+ e.getKey()
						+ "\",style=\"dotted\",len=\"2.5\"];";
						// dot += dotPart;
						System.out.println(dotPart);
						writeDOT(dotPart);
					}
					addTermsToQueue(e.getValue());
				}

				log.debug(" finished " + currentNode.getLabel()
						+ " (queue size: "
						+ processQueue.size()
						+ ")");
			}

			// if new data available break the barrier and
			// release other tasks
			if (getQueueSize() > 0) {
				log.debug(getPID() + " New data available. Releasing "
						+ getNumberWaiting()
						+ " task(s) waiting");
				resetBarrier();
				// wait at the barrier for new data to arrive
			} else {
				int taskNo = getNumberWaiting() + 1;
				log.debug(String.format(
						getPID() + " %d task(s) waiting for new data",
						taskNo));

				addToBarrier(getPID());
				while (isAtBarrier(getPID()) && (getNumberWaiting() != maxTasks)) {
					Thread.sleep(500);
				}

				log.debug(String.format(
						getPID() + " Task %d released for new data",
						taskNo));

			}

		} while (getNumberWaiting() != maxTasks);
		log.debug(String.format(
				getPID() + " Task finished. Process queue size %d",
				processQueue.size()));
	}
}