import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.FastVector;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ArffSaver;

public class ArffData {
	private Instances data;
	private ArrayList<Attribute> attrs;
	private int numOfAttributes;
	
	public ArffData() {
		ArrayList<Attribute> attrs = new ArrayList<Attribute>();
		Attribute text_attr = new Attribute("text", (FastVector) null);
		attrs.add(text_attr);
		List<String> class_list = Arrays.asList("neg","pos");
		Attribute class_attrs = new Attribute("class", class_list);
		attrs.add(class_attrs);
		
		Instances data;
		data = new Instances("tweets", attrs, 0);
		this.data = data;
		this.attrs = attrs;
		this.numOfAttributes = data.numAttributes();
	}
	
	public void addData(String text, int emotion) {
		
		
		double [] vals = new double[this.numOfAttributes];
		
		vals[0] = this.data.attribute(0).addStringValue(text);
		vals[1] = emotion;

		this.data.add(new DenseInstance(1.0, vals));
		
	}

	
	public void saveData2File(String file_name) {
		ArffSaver saver = new ArffSaver();
		saver.setInstances(this.data);
		try {
			saver.setFile(new File("./data/"+ file_name + ".arff"));
			saver.writeBatch();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
