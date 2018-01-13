package maxent;
import java.io.BufferedReader;
import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import utils.FileLoader;

public class Cues {
	FeatureGenerator featureGenerator = null;
	int numCues = 0;
	
	public Cues() {
		// TODO Auto-generated constructor stub
	}
	public Cues(FeatureGenerator featureGenerator){
		System.out.println("REMOVEME = initialized CUES class");
		this.featureGenerator = featureGenerator;
	}
	
	protected ArrayList<String> getCues(){
		ArrayList<String> pos_cue_strs = new ArrayList<String>();
		
		List<Feature> features = this.featureGenerator.features;
		Data data = this.featureGenerator.data;
		
		ArrayList<Feature> posFeatures = new ArrayList<Feature>();
		for (int i = 0; i < features.size(); i++) {
			Feature f = (Feature) features.get(i);
			if(f.label == 1){
				posFeatures.add(f);
			}
		}
		System.setProperty("java.util.Arrays.useLegacyMergeSort", "true");
		Collections.sort(posFeatures, new Comparator<Feature> () {
			@Override
			public int compare(Feature f1, Feature f2) {
				return f1.weight - f2.weight >= 0 ? -1 : 1;
			}
		});

		for(int i = 0; i < numCues; i++ ){
			Feature f = posFeatures.get(i);
			int cpInt = f.contextPredicate;
			String cpStr = (String) data.getCpInt2Str().get(new Integer(cpInt));
			pos_cue_strs.add(cpStr);
		}
		return pos_cue_strs;
	}
	public static int arrayCount(List<String> array, String item) {
	    int amt = 0;
	    for (int i = 0; i < array.size(); i++) {
	        if (array.get(i).equals(item)) {
	            amt++;
	        }
	    }
	    return amt;
	}

	protected void count_cues(ArrayList<String> cues,String filename) throws Exception{
		
		Map<String, Integer> pos_counts = new HashMap<String, Integer>(); 
		Map<String, Integer> neg_counts = new HashMap<String, Integer>(); 
		for(String cue:cues){
			pos_counts.put(cue, 0);
			neg_counts.put(cue, 0);
		}
		
		System.out.println("====REMOVEME -- reading file " + filename);
		
		String modelDir = this.featureGenerator.option.modelDirectory;
		String trainFilename = modelDir + File.separator + filename;
		BufferedReader bufferedReader = FileLoader.getBufferredReader(trainFilename, "UTF8");
		
		String line;
		while ((line = bufferedReader.readLine()) != null) {
			StringTokenizer strTok = new StringTokenizer(line, " \t\r\n");
			int len = strTok.countTokens();

			if (len <= 1) {
				// skip this invalid line
				continue;
			}

			List<String> strCps = new ArrayList<String>();
			for (int i = 0; i < len - 1; i++) {
				strCps.add(strTok.nextToken());
			}
			String labelStr = strTok.nextToken();
			
			if(labelStr.equals("0")){
				for(String cue:cues){
					neg_counts.put( cue, neg_counts.get(cue) + arrayCount(strCps, cue));
				}
			} else {
				for(String cue:cues){
					pos_counts.put( cue, pos_counts.get(cue) + arrayCount(strCps, cue));
				}
			}
		}
		
		System.out.println("for pos class:");
		System.out.println(Arrays.asList(pos_counts));
		
		System.out.println("for neg class:");
		System.out.println(Arrays.asList(neg_counts));

		return;
	}

	protected void writeCues(PrintWriter fout) throws Exception {
		ArrayList<String> cues = getCues();
		count_cues(cues, "train.tagged");
		count_cues(cues, "test.tagged");
		
		for(String cue: cues){
			fout.write(cue + '\n');
		}
	}
	
}
