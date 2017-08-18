import java.io.*;
import java.util.*;
import org.uncommons.maths.random.ExponentialGenerator;
import org.uncommons.maths.random.MersenneTwisterRNG;

public class Main {


	public static void main(String[] args) {
		
		double eta = 0.1;
		String line;
		String ky_orig;
		String ky_lr;
		String uname = "l0";
		String pword = "bkajblankl1323^32";
		double lapNoise;
		boolean needAggregated = false;
		
		HashMap<String, Integer> hmap_orig = new HashMap<String, Integer>(); //hmap for aggregated data
		HashMap<String, Integer> hmap_lr = new HashMap<String, Integer>(); //hmap for low risk hr managers
		HashMap<String, Double> hmap_mod = new HashMap<String, Double>(); //modified hmap for output display
		
		if(args.length != 2){
			
			if(args.length == 3 && args[2].equals("yes")){
				needAggregated = true;
				uname = args[0];
				pword = args[1];
			}
			else{
				System.out.println("Incorrect parameters");
				System.exit(0);
			}
		}else{
			System.out.println("Enter the username:");
			uname = args[0];
			
			System.out.println("Enter the password:");
			pword = args[1];
			
		}
		
		try{
			
			BufferedReader br = new BufferedReader(new FileReader("adult.csv"));
			while((line = br.readLine()) != null){
				String[] cols = line.split(",");
				
				ky_lr = line;
				ky_orig = cols[4].toLowerCase()+" , "+cols[15].toLowerCase();
				
				//populate the aggregate table
				if(hmap_orig.containsKey(ky_orig)==false){
					hmap_orig.put(ky_orig, 1);
				}
				else{
					hmap_orig.put(ky_orig, hmap_orig.get(ky_orig)+1);
				}
				
				//populate the lr table
				if(hmap_lr.containsKey(ky_lr)==false){
					hmap_lr.put(ky_lr, 1);
				}
				else{
					hmap_lr.put(ky_lr, hmap_lr.get(ky_lr)+1);
				}
			}
			br.close();
		}catch(IOException e){
			e.printStackTrace();
		}
		
		
		//HR manager (medium risk) aggregated access with data anonymization
		if(uname.equals("mr") && pword.equals("cjsn193849dsn@#")){
			eta = 1.0;
		}
		//HR developer (medium high risk) aggregated access with data anonymization
		else if(uname.equals("mhr") && pword.equals("w3@kpdm1882n@$$w0^5")){
			eta = 0.1;
		}
		//HR manager (low risk) full access no data anonymization
		else if(uname.equals("lr") && pword.equals("1jwh8081y317rgejnskdmd@$#%"))
		{
			eta = 0.0;
		}
		//HR Benchmarking (high risk) aggregated access with data anonymization
		else if(uname.equals("hr") && pword.equals("jhsouuq7329893dksand$"))
		{
			eta = 0.05;
		}
		//no access
		else{
			System.out.println("Exiting the program: Username and password credentials don't match");
			System.exit(0);
		}

		System.out.println("Education," + "\t" + "Income," +"\t" + "Number of records");
		
		if(eta == 0.0){
			if(needAggregated){
				for (HashMap.Entry<String,Integer> entry : hmap_orig.entrySet()){
					  String key = entry.getKey();
					  Integer value = entry.getValue();
					  System.out.println(key+" , "+value);
					}
			}
			else{
				for (HashMap.Entry<String,Integer> entry : hmap_lr.entrySet()){
				  String key = entry.getKey();
				  System.out.println(key);
				}	
			}
			System.out.println("Total number of records are:"+hmap_lr.size());
			lapNoise = 0.0;
		}else
			lapNoise = lapError(hmap_orig, eta, hmap_mod);
		
		System.out.println("Normalized error in the num. records is: "+lapNoise);
		
	}
	
		public static double lapError (HashMap<String, Integer> hmap_old, double e, HashMap<String, Double> hmap_new){
			
			double lapNoise = 0.0;
			double normalizedError = 0.0;
			double globalSens = 2.0;
			double newVal = 0.0;
			double lambda = e/globalSens;
			Random rng = new MersenneTwisterRNG();
			ExponentialGenerator gen = new ExponentialGenerator(lambda, rng);
			
			for(String key: hmap_old.keySet()){
				
				lapNoise = gen.nextValue() - gen.nextValue();
					
				newVal = lapNoise + (double)hmap_old.get(key);
				if(lapNoise < 0)
					normalizedError = normalizedError - lapNoise;
				else
					normalizedError = normalizedError + lapNoise;
				hmap_new.put(key, newVal);
				System.out.println(key+" , "+hmap_new.get(key));
			}
			
			System.out.println("Total number of aggregated records are: "+hmap_new.size());
			normalizedError = normalizedError/(double)hmap_old.size();
			return (normalizedError);
		}
		
	}
