package maxent;


import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;

/**
 * @author Xuan-Hieu Phan (pxhieu@gmail.com)
 * @version 1.1
 * @since 25-08-2014
 */
public class TrainWithNewFeature {
	/**
	 * The main method for training and testing.
	 * 
	 * @param args
	 *            the argument list (see help)
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		if (!checkArgs(args)) {
			displayHelp();
			return;
		}

		String modelDir = args[2];

		Option old_option = new Option(modelDir + File.separator + "past_knowledge");
		old_option.readOptions();
		Data old_data = new Data(old_option);
		old_data.readTrnData(old_option.modelDirectory + File.separator + old_option.trainDataFile);
		old_data.readTstData(old_option.modelDirectory + File.separator + old_option.testDataFile);

		Dictionary old_dictionary = new Dictionary(old_option, old_data);
		old_dictionary.generateDictionary();

		
		
		
		
		
		Option option = new Option(modelDir);
		option.readOptions();

		Data data;
		Dictionary dictionary;
		FeatureGenerator featureGenerator;
		Train train;
		Inference inference;
		Evaluation evaluation;
		Model model;

		PrintWriter foutModel;

		PrintWriter flog = option.openTrainLogFile();
		if (flog == null) {
			System.out.println("Couldn't create training log file!");
			return ;
		}

		foutModel = option.createModelFile();
		if (foutModel == null) {
			System.out.println("Couldn't create model file!");
			return ;
		}

		data = new Data(option);
		data.readTrnData(option.modelDirectory + File.separator + option.trainDataFile);
		data.readTstData(option.modelDirectory + File.separator + option.testDataFile);

		dictionary = new Dictionary(option, data);
		dictionary.generateDictionary();

		featureGenerator = new FeatureGenerator(option, data, dictionary);
		featureGenerator.generateFeatures();

		data.writeCpMaps(dictionary, foutModel);
		data.writeLbMaps(foutModel);

		train = new Train();
		inference = new Inference();
		evaluation = new Evaluation();

		model = new Model(option, data, dictionary, featureGenerator, train, inference, evaluation, old_dictionary);
		model.doTrain(flog);

		model.doInference(model.data.tstData);
		model.evaluation.evaluate(flog);
		
		System.out.println("Writing result.txt");
		String resultFilename = option.modelDirectory + File.separator + "result.txt";
		PrintWriter fResult = new PrintWriter(new FileWriter(resultFilename));
		for (int i = 0; i < model.data.tstData.size(); i++) {
			Observation obsr = (Observation) model.data.tstData.get(i);
			String s = obsr.toString(model.data.getCpInt2Str(), model.data.getLbInt2Str());
			System.out.println(s);
			fResult.write(s + "\n");
		}
		fResult.close();
		
		
		
		flog.close();
		
		dictionary.writeDictionary(foutModel);
		featureGenerator.writeFeatures(foutModel);

		foutModel.close();
		
	}

	/**
	 * Checking valid arguments.
	 * 
	 * @param args
	 *            the list of input arguments
	 * @return True if the arguments are valid, false otherwise
	 */
	private static boolean checkArgs(String[] args) {
		if (args.length < 3) {
			return false;
		}

		if (!(args[0].compareToIgnoreCase("-all") == 0 || args[0].compareToIgnoreCase("-trn") == 0
				|| args[0].compareToIgnoreCase("-tst") == 0)) {
			return false;
		}

		if (args[1].compareToIgnoreCase("-d") != 0) {
			return false;
		}

		return true;
	}

	/**
	 * Displaying help information.
	 */
	private static void displayHelp() {
		System.out.println("Usage:");
		System.out.println("\tTrainer -all/-trn/-tst -d <model directory>");
	}
}
