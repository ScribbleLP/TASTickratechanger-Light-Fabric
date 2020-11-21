package de.scribble.lp.tastickratechanger;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.Level;


public class Config {
	File location;
	public Config(File file) {
		if (!file.exists()) {
			StringBuilder output= new StringBuilder();
			output.append("DefaultTickrate=20");
			FileStuff.writeThings(output, file);
		}
		location=file;
	}
	
	public float getValue() {
		List<String>values=new ArrayList<String>();
		try {
			values=FileStuff.readThings(location);
		} catch (IOException e) {
			e.printStackTrace();
		}
		if(values.get(0).startsWith("DefaultTickrate=")) {
			String[] split1=values.get(0).split("=");
			return Float.parseFloat(split1[1]);
		}else {
			ModLoader.log(Level.ERROR, "Couldn't parse default Tickrate");
			return 20F;
		}
	}
	public void setValue(float tickrate) {
		if(location.exists()) {
			StringBuilder output= new StringBuilder();
			output.append("DefaultTickrate="+tickrate);
			FileStuff.writeThings(output, location);
		}
	}
}
